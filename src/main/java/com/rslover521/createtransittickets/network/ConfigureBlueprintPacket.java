package com.rslover521.createtransittickets.network;

import com.rslover521.createtransittickets.registry.ModItems;
import com.rslover521.createtransittickets.util.TicketData;
import com.rslover521.createtransittickets.util.TicketServices;
import com.rslover521.createtransittickets.util.TicketTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public record ConfigureBlueprintPacket(InteractionHand hand, String name, TicketTypes type,
                       TicketServices service, long value) implements CustomPacketPayload {
    public static final Type<ConfigureBlueprintPacket> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath("create_transit_tickets", "configure_blueprint"));
    public static final StreamCodec<ByteBuf, ConfigureBlueprintPacket> STREAM_CODEC =
        StreamCodec.of(ConfigureBlueprintPacket::encode, ConfigureBlueprintPacket::decode);

    private static void encode(ByteBuf byteBuf, ConfigureBlueprintPacket packet) {
    FriendlyByteBuf buffer = new FriendlyByteBuf(byteBuf);
        buffer.writeEnum(packet.hand);
        buffer.writeUtf(packet.name, 64);
        buffer.writeEnum(packet.type);
        buffer.writeEnum(packet.service);
        buffer.writeVarLong(packet.value);
    }

    private static ConfigureBlueprintPacket decode(ByteBuf byteBuf) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(byteBuf);
        return new ConfigureBlueprintPacket(buffer.readEnum(InteractionHand.class), buffer.readUtf(64),
                buffer.readEnum(TicketTypes.class), buffer.readEnum(TicketServices.class), buffer.readVarLong());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ConfigureBlueprintPacket packet,
                              net.neoforged.neoforge.network.handling.IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            ItemStack stack = player.getItemInHand(packet.hand);
            if (!stack.is(ModItems.TICKET_BLUEPRINT.get()) || !isValidValue(packet.type, packet.value)) return;
            TicketData.configureBlueprint(stack, packet.name, packet.type, packet.service, packet.value);
        });
    }

    private static boolean isValidValue(TicketTypes type, long value) {
        return switch (type) {
            case MULTIPLE_USE -> value >= 1L && value <= Integer.MAX_VALUE;
            case LIMITED_TIME -> value >= 1L;
            case SINGLE_USE, UNLIMITED_TIME -> true;
        };
    }
}
