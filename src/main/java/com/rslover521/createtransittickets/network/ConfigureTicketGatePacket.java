package com.rslover521.createtransittickets.network;

import com.rslover521.createtransittickets.customBlocks.TicketGateBlock;
import com.rslover521.createtransittickets.customBlocks.TicketGateBlockEntity;
import com.rslover521.createtransittickets.util.GateServiceRequirement;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record ConfigureTicketGatePacket(BlockPos pos, GateServiceRequirement requirement)
    implements CustomPacketPayload {
    private static final double MAX_DISTANCE_SQUARED = 64.0D;
    public static final Type<ConfigureTicketGatePacket> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath("create_transit_tickets", "configure_ticket_gate"));
    public static final StreamCodec<ByteBuf, ConfigureTicketGatePacket> STREAM_CODEC =
        StreamCodec.of(ConfigureTicketGatePacket::encode, ConfigureTicketGatePacket::decode);

    private static void encode(ByteBuf byteBuf, ConfigureTicketGatePacket packet) {
    FriendlyByteBuf buffer = new FriendlyByteBuf(byteBuf);
        buffer.writeBlockPos(packet.pos);
        buffer.writeEnum(packet.requirement);
    }

    private static ConfigureTicketGatePacket decode(ByteBuf byteBuf) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(byteBuf);
        return new ConfigureTicketGatePacket(buffer.readBlockPos(), buffer.readEnum(GateServiceRequirement.class));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ConfigureTicketGatePacket packet,
                              net.neoforged.neoforge.network.handling.IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)
                    || !player.hasPermissions(2)
                    || player.distanceToSqr(packet.pos.getX() + 0.5D,
                    packet.pos.getY() + 0.5D, packet.pos.getZ() + 0.5D) > MAX_DISTANCE_SQUARED) return;
            if (!TicketGateBlock.isCreateWrench(player.getMainHandItem())
                    && !TicketGateBlock.isCreateWrench(player.getOffhandItem())) return;
            if (player.level().getBlockEntity(packet.pos) instanceof TicketGateBlockEntity gate) {
                gate.setRequiredService(packet.requirement);
            }
        });
    }
}
