package com.rslover521.createtransittickets.network;

import com.rslover521.createtransittickets.CreateTransitTickets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ModNetworking {
    private ModNetworking() {
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(ModNetworking::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToServer(ConfigureBlueprintPacket.TYPE,
                ConfigureBlueprintPacket.STREAM_CODEC, ConfigureBlueprintPacket::handle);
        registrar.playToServer(ConfigureTicketGatePacket.TYPE,
                ConfigureTicketGatePacket.STREAM_CODEC, ConfigureTicketGatePacket::handle);
    }
}
