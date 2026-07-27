package com.rslover521.createtransittickets.network;

import com.rslover521.createtransittickets.CreateTransitTickets;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class ModNetworking {
    private static final String VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(CreateTransitTickets.MOD_ID, "main"),
            () -> VERSION, VERSION::equals, VERSION::equals);

    private ModNetworking() {
    }

    public static void register() {
        CHANNEL.registerMessage(0, ConfigureBlueprintPacket.class,
                ConfigureBlueprintPacket::encode,
                ConfigureBlueprintPacket::decode,
                ConfigureBlueprintPacket::handle);
        CHANNEL.registerMessage(1, ConfigureTicketGatePacket.class,
                ConfigureTicketGatePacket::encode,
                ConfigureTicketGatePacket::decode,
                ConfigureTicketGatePacket::handle);
    }
}
