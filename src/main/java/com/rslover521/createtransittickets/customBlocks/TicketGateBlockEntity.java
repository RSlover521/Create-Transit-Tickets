package com.rslover521.createtransittickets.customBlocks;

import com.rslover521.createtransittickets.registry.ModBlockEntities;
import com.rslover521.createtransittickets.util.GateServiceRequirement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class TicketGateBlockEntity extends BlockEntity {
    private static final String OPENED_AT_TAG = "OpenedAt";
    private static final String REQUIRED_SERVICE_TAG = "RequiredService";

    private long openedAt;
    private GateServiceRequirement requiredService = GateServiceRequirement.ANY;

    public TicketGateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TICKET_GATE.get(), pos, state);
    }

    public long getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(long openedAt) {
        this.openedAt = openedAt;
        setChanged();
    }

    public GateServiceRequirement getRequiredService() {
        return requiredService;
    }

    public void setRequiredService(GateServiceRequirement requiredService) {
        this.requiredService = requiredService;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong(OPENED_AT_TAG, openedAt);
        tag.putString(REQUIRED_SERVICE_TAG, requiredService.name());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        openedAt = tag.getLong(OPENED_AT_TAG);
        try {
            requiredService = GateServiceRequirement.valueOf(tag.getString(REQUIRED_SERVICE_TAG));
        } catch (IllegalArgumentException ignored) {
            requiredService = GateServiceRequirement.ANY;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tag = packet.getTag();
        if (tag != null) load(tag);
    }
}
