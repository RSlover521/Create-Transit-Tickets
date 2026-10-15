package com.rslover521.createtransittickets.item;

import com.rslover521.createtransittickets.util.CreateSummaryTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public final class TicketGateItem extends BlockItem {
    public TicketGateItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CreateSummaryTooltip.append(this, tooltip);
    }
}
