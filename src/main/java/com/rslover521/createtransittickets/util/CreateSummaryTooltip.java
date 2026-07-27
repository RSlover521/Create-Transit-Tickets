package com.rslover521.createtransittickets.util;

import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.List;

public final class CreateSummaryTooltip {
    private CreateSummaryTooltip() {
    }

    public static void append(Item item, List<Component> tooltip) {
        ItemDescription description = ItemDescription.create(item, TooltipHelper.Palette.STANDARD_CREATE);
        if (description != null) {
            tooltip.addAll(description.getCurrentLines());
        }
    }
}
