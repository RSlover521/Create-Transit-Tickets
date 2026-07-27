package com.rslover521.createtransittickets.network;

import com.rslover521.createtransittickets.customBlocks.TicketGateBlock;
import com.rslover521.createtransittickets.customBlocks.TicketGateBlockEntity;
import com.rslover521.createtransittickets.util.GateServiceRequirement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ConfigureTicketGatePacket(BlockPos pos, GateServiceRequirement requirement) {
    private static final double MAX_DISTANCE_SQUARED = 64.0D;

    public static void encode(ConfigureTicketGatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeEnum(packet.requirement);
    }

    public static ConfigureTicketGatePacket decode(FriendlyByteBuf buffer) {
        return new ConfigureTicketGatePacket(buffer.readBlockPos(), buffer.readEnum(GateServiceRequirement.class));
    }

    public static void handle(ConfigureTicketGatePacket packet,
                              Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null || !player.hasPermissions(2)
                    || player.distanceToSqr(packet.pos.getX() + 0.5D,
                    packet.pos.getY() + 0.5D, packet.pos.getZ() + 0.5D) > MAX_DISTANCE_SQUARED) return;
            if (!TicketGateBlock.isCreateWrench(player.getMainHandItem())
                    && !TicketGateBlock.isCreateWrench(player.getOffhandItem())) return;
            if (player.level().getBlockEntity(packet.pos) instanceof TicketGateBlockEntity gate) {
                gate.setRequiredService(packet.requirement);
            }
        });
        context.setPacketHandled(true);
    }
}
