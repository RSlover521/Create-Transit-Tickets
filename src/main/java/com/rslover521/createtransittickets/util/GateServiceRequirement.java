package com.rslover521.createtransittickets.util;

import net.minecraft.world.item.ItemStack;

public enum GateServiceRequirement {
    ANY,
    LOCAL,
    SEMI_FAST,
    EXPRESS;

    public boolean matches(ItemStack ticket) {
        return this == ANY || name().equals(TicketData.getTicketService(ticket).name());
    }
}
