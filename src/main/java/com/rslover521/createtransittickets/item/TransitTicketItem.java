package com.rslover521.createtransittickets.item;

import com.rslover521.createtransittickets.util.TicketData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class TransitTicketItem extends Item {
    public TransitTicketItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        String ticketName = TicketData.getTicketName(stack);
        return ticketName.isBlank() ? super.getName(stack) : Component.literal(ticketName);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (!TicketData.isIssued(stack)) {
            tooltip.add(Component.translatable("tooltip.create_transit_tickets.unissued")
                    .withStyle(ChatFormatting.YELLOW));
            return;
        }

        long now = level == null ? TicketData.getIssuedTime(stack) : level.getGameTime();
        long remaining = TicketData.getValidUntil(stack) - now;
        if (remaining > 0) {
            tooltip.add(Component.translatable("tooltip.create_transit_tickets.valid")
                    .withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.translatable("tooltip.create_transit_tickets.remaining",
                    TicketData.formatDuration(remaining)).withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("tooltip.create_transit_tickets.expired")
                    .withStyle(ChatFormatting.RED));
        }
    }
}
