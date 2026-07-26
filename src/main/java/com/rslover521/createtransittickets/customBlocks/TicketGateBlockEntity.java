package com.rslover521.createtransittickets.customBlocks;

import com.rslover521.createtransittickets.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class TicketGateBlockEntity extends BlockEntity {
    private static final String OPENED_AT_TAG = "OpenedAt";

    private long openedAt;

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

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong(OPENED_AT_TAG, openedAt);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        openedAt = tag.getLong(OPENED_AT_TAG);
    }
}
