package com.rslover521.createtransittickets.item;

import com.rslover521.createtransittickets.registry.ModItems;
import com.rslover521.createtransittickets.util.TicketData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class TicketBlueprintItem extends Item {
    public TicketBlueprintItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack blueprint = player.getItemInHand(hand);
        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack blank = player.getItemInHand(otherHand);

        if (!blank.is(ModItems.BLANK_TICKET.get())) {
            return InteractionResultHolder.pass(blueprint);
        }

        if (!level.isClientSide) {
            ItemStack ticket = TicketData.issueTicket(blueprint, level.getGameTime());
            if (!player.getAbilities().instabuild) {
                blank.shrink(1);
            }
            if (!player.addItem(ticket)) {
                player.drop(ticket, false);
            }
        }

        return InteractionResultHolder.sidedSuccess(blueprint, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.create_transit_tickets.duration",
                TicketData.formatDuration(TicketData.getDuration(stack))).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.create_transit_tickets.blueprint_usage")
                .withStyle(ChatFormatting.DARK_GRAY));
    }
}
