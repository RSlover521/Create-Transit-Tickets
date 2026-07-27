package com.rslover521.createtransittickets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

public final class ClientHooks {
    private ClientHooks() {
    }

    public static void openBlueprintScreen(InteractionHand hand) {
        Minecraft.getInstance().setScreen(new TicketBlueprintScreen(hand));
    }
}
