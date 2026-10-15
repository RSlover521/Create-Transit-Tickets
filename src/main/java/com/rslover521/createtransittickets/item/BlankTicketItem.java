package com.rslover521.createtransittickets.item;

import com.rslover521.createtransittickets.util.CreateSummaryTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public final class BlankTicketItem extends Item {
    public BlankTicketItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CreateSummaryTooltip.append(this, tooltip);
    }
}
