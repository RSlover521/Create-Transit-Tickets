package com.rslover521.createtransittickets.item;

import net.createmod.catnip.theme.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class IncompleteTransitTicketItem extends Item {
    private static final float PROGRESS = 0.5F;

    public IncompleteTransitTicketItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(PROGRESS * 13.0F);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Color.mixColors(-16268, -12124192, PROGRESS);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(CommonComponents.EMPTY);
        tooltip.add(Component.translatable("create.recipe.sequenced_assembly")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("create.recipe.assembly.progress", 1, 2)
                .withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("create.recipe.assembly.next",
                        Component.translatable("create.recipe.assembly.pressing"))
                .withStyle(ChatFormatting.AQUA));
    }
}
