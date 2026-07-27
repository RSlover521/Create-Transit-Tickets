package com.rslover521.createtransittickets.network;

import com.rslover521.createtransittickets.registry.ModItems;
import com.rslover521.createtransittickets.util.TicketData;
import com.rslover521.createtransittickets.util.TicketServices;
import com.rslover521.createtransittickets.util.TicketTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ConfigureBlueprintPacket(InteractionHand hand, String name, TicketTypes type,
                                       TicketServices service, long value) {
    public static void encode(ConfigureBlueprintPacket packet, FriendlyByteBuf buffer) {
        buffer.writeEnum(packet.hand);
        buffer.writeUtf(packet.name, 64);
        buffer.writeEnum(packet.type);
        buffer.writeEnum(packet.service);
        buffer.writeVarLong(packet.value);
    }

    public static ConfigureBlueprintPacket decode(FriendlyByteBuf buffer) {
        return new ConfigureBlueprintPacket(buffer.readEnum(InteractionHand.class), buffer.readUtf(64),
                buffer.readEnum(TicketTypes.class), buffer.readEnum(TicketServices.class), buffer.readVarLong());
    }

    public static void handle(ConfigureBlueprintPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ItemStack stack = player.getItemInHand(packet.hand);
            if (!stack.is(ModItems.TICKET_BLUEPRINT.get()) || !isValidValue(packet.type, packet.value)) return;
            TicketData.configureBlueprint(stack, packet.name, packet.type, packet.service, packet.value);
        });
        context.setPacketHandled(true);
    }

    private static boolean isValidValue(TicketTypes type, long value) {
        return switch (type) {
            case MULTIPLE_USE -> value >= 1L && value <= Integer.MAX_VALUE;
            case LIMITED_TIME -> value >= 1L;
            case SINGLE_USE, UNLIMITED_TIME -> true;
        };
    }
}
