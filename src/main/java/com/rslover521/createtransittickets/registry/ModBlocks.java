package com.rslover521.createtransittickets.registry;

import com.rslover521.createtransittickets.CreateTransitTickets;
import com.rslover521.createtransittickets.customBlocks.TicketGateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(NeoForgeRegistries.BLOCKS, CreateTransitTickets.MOD_ID);

    public static final RegistryObject<Block> TICKET_GATE = BLOCKS.register("ticket_gate",
            () -> new TicketGateBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()));

    private ModBlocks() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }
}
