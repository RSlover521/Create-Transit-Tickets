package com.rslover521.createtransittickets.customBlocks;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.rslover521.createtransittickets.registry.ModItems;
import com.rslover521.createtransittickets.util.GateServiceRequirement;
import com.rslover521.createtransittickets.util.TicketData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

public final class TicketGateBlock extends BaseEntityBlock implements IWrenchable {
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty PASSAGE_STARTED = BooleanProperty.create("passage_started");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final int OPEN_TIMEOUT_TICKS = 100;
    private static final int PASSAGE_CHECK_TICKS = 1;
    private static final ResourceLocation CREATE_WRENCH_ID =
            ResourceLocation.fromNamespaceAndPath("create", "wrench");
    private static final VoxelShape CLOSED_NORTH_SOUTH_SHAPE = Shapes.or(
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D),
            Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
            Block.box(6.0D, 4.0D, 2.0D, 10.0D, 16.0D, 3.0D),
            Block.box(6.0D, 7.0D, 3.0D, 10.0D, 16.0D, 4.0D),
            Block.box(6.0D, 10.0D, 4.0D, 10.0D, 16.0D, 5.0D),
            Block.box(6.0D, 13.0D, 5.0D, 10.0D, 15.0D, 6.0D),
            Block.box(6.0D, 14.0D, 6.0D, 10.0D, 15.0D, 7.0D),
            Block.box(6.0D, 14.0D, 9.0D, 10.0D, 15.0D, 10.0D),
            Block.box(6.0D, 13.0D, 10.0D, 10.0D, 15.0D, 11.0D),
            Block.box(6.0D, 10.0D, 11.0D, 10.0D, 16.0D, 12.0D),
            Block.box(6.0D, 7.0D, 12.0D, 10.0D, 16.0D, 13.0D),
            Block.box(6.0D, 4.0D, 13.0D, 10.0D, 16.0D, 14.0D),
            Block.box(8.0D, 15.0D, 13.0D, 9.0D, 16.0D, 14.0D)
    ).optimize();
    private static final VoxelShape CLOSED_EAST_WEST_SHAPE = Shapes.or(
            Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D),
            Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(13.0D, 4.0D, 6.0D, 14.0D, 16.0D, 10.0D),
            Block.box(12.0D, 7.0D, 6.0D, 13.0D, 16.0D, 10.0D),
            Block.box(11.0D, 10.0D, 6.0D, 12.0D, 16.0D, 10.0D),
            Block.box(10.0D, 13.0D, 6.0D, 11.0D, 15.0D, 10.0D),
            Block.box(9.0D, 14.0D, 6.0D, 10.0D, 15.0D, 10.0D),
            Block.box(6.0D, 14.0D, 6.0D, 7.0D, 15.0D, 10.0D),
            Block.box(5.0D, 13.0D, 6.0D, 6.0D, 15.0D, 10.0D),
            Block.box(4.0D, 10.0D, 6.0D, 5.0D, 16.0D, 10.0D),
            Block.box(3.0D, 7.0D, 6.0D, 4.0D, 16.0D, 10.0D),
            Block.box(2.0D, 4.0D, 6.0D, 3.0D, 16.0D, 10.0D),
            Block.box(2.0D, 15.0D, 8.0D, 3.0D, 16.0D, 9.0D)
    ).optimize();
    private static final VoxelShape OPEN_NORTH_SOUTH_SHAPE = Shapes.or(
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D),
            Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D)
    ).optimize();
    private static final VoxelShape OPEN_EAST_WEST_SHAPE = Shapes.or(
            Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D),
            Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    ).optimize();

    public TicketGateBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.METAL).noOcclusion());
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(PASSAGE_STARTED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);

        if (isCreateWrench(heldStack)) {
            return openWrenchConfiguration(level, pos, player);
        }

        if (heldStack.isEmpty()) {
            if (!level.isClientSide) {
                playDeniedSound(level, pos);
                showError(player, Component.translatable("message.create_transit_tickets.ticket_gate.no_ticket"));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!isValidTicket(heldStack, level)) {
            if (!level.isClientSide) {
                playDeniedSound(level, pos);
                showError(player, Component.translatable("message.create_transit_tickets.ticket_gate.invalid_ticket"));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        GateServiceRequirement requirement = level.getBlockEntity(pos) instanceof TicketGateBlockEntity gate
                ? gate.getRequiredService() : GateServiceRequirement.ANY;
        if (!requirement.matches(heldStack)) {
            if (!level.isClientSide) {
                playDeniedSound(level, pos);
                showError(player, Component.translatable(
                        "message.create_transit_tickets.ticket_gate.wrong_service"));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!level.isClientSide) {
            TicketData.consumePassage(heldStack);
            playAcceptedSound(level, pos);
            openGate(state, level, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Player player = context.getPlayer();
        return player == null ? InteractionResult.PASS
                : openWrenchConfiguration(context.getLevel(), context.getClickedPos(), player);
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!player.hasPermissions(2)) {
            if (!level.isClientSide) {
                playDeniedSound(level, pos);
                showError(player, Component.translatable(
                        "message.create_transit_tickets.ticket_gate.operator_only"));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!level.isClientSide) {
            Block.popResource(level, pos, new ItemStack(ModItems.TICKET_GATE.get()));
            level.removeBlock(pos, false);
            IWrenchable.playRemoveSound(level, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(OPEN)) {
            return;
        }

        if (!(level.getBlockEntity(pos) instanceof TicketGateBlockEntity gate)
                || level.getGameTime() - gate.getOpenedAt() >= OPEN_TIMEOUT_TICKS) {
            closeGate(state, level, pos);
            return;
        }

        if (!state.getValue(PASSAGE_STARTED)) {
            level.scheduleTick(pos, this, PASSAGE_CHECK_TICKS);
            return;
        }

        boolean playerStillInGate = !level.getEntitiesOfClass(
                Player.class,
                new AABB(pos),
                player -> player.isAlive() && !player.isSpectator()
        ).isEmpty();

        if (playerStillInGate) {
            level.scheduleTick(pos, this, PASSAGE_CHECK_TICKS);
        } else {
            closeGate(state, level, pos);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide
                && state.getValue(OPEN)
                && !state.getValue(PASSAGE_STARTED)
                && entity instanceof Player player
                && player.isAlive()
                && !player.isSpectator()) {
            level.setBlock(pos, state.setValue(PASSAGE_STARTED, true), Block.UPDATE_ALL);
        }

        super.entityInside(state, level, pos, entity);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getGateShape(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getGateShape(state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TicketGateBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return rotate(state, mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, PASSAGE_STARTED);
    }

    private static boolean isValidTicket(ItemStack stack, Level level) {
        return stack.is(ModItems.TRANSIT_TICKET.get())
                && TicketData.isIssued(stack)
                && (TicketData.isPassageLimited(stack)
                ? TicketData.getRemainingPassages(stack) > 0
                : TicketData.getValidUntil(stack) > level.getGameTime());
    }

    public static boolean isCreateWrench(ItemStack stack) {
        return stack.getItem() == ForgeRegistries.ITEMS.getValue(CREATE_WRENCH_ID);
    }

    private static InteractionResult openWrenchConfiguration(Level level, BlockPos pos, Player player) {
        if (!player.hasPermissions(2)) {
            if (!level.isClientSide) {
                playDeniedSound(level, pos);
                showError(player, Component.translatable(
                        "message.create_transit_tickets.ticket_gate.operator_only"));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (level.isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                    () -> () -> com.rslover521.createtransittickets.client.ClientHooks
                            .openTicketGateScreen(pos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static void openGate(BlockState state, Level level, BlockPos pos) {
        BlockState openState = state
                .setValue(OPEN, true)
                .setValue(PASSAGE_STARTED, false);
        level.setBlock(pos, openState, Block.UPDATE_ALL);
        if (level.getBlockEntity(pos) instanceof TicketGateBlockEntity gate) {
            gate.setOpenedAt(level.getGameTime());
        }
        level.scheduleTick(pos, state.getBlock(), PASSAGE_CHECK_TICKS);
    }

    private static void closeGate(BlockState state, Level level, BlockPos pos) {
        level.setBlock(pos, state
                .setValue(OPEN, false)
                .setValue(PASSAGE_STARTED, false), Block.UPDATE_ALL);
    }

    private static void playAcceptedSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.NOTE_BLOCK_BELL.get(), SoundSource.BLOCKS, 0.8F, 1.6F);
    }

    private static void playDeniedSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.NOTE_BLOCK_BASS.get(), SoundSource.BLOCKS, 0.8F, 0.55F);
    }

    private static void showError(Player player, Component message) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(5, 40, 5));
            serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(message.copy().withStyle(ChatFormatting.RED)));
            serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.empty()));
        }
    }

    private static VoxelShape getGateShape(BlockState state) {
        Direction.Axis axis = state.getValue(FACING).getAxis();
        if (state.getValue(OPEN)) {
            return axis == Direction.Axis.X ? OPEN_EAST_WEST_SHAPE : OPEN_NORTH_SOUTH_SHAPE;
        }

        return axis == Direction.Axis.X ? CLOSED_EAST_WEST_SHAPE : CLOSED_NORTH_SOUTH_SHAPE;
    }
}
