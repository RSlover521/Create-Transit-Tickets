package com.rslover521.createtransittickets.registry;

import com.rslover521.createtransittickets.CreateTransitTickets;
import com.rslover521.createtransittickets.customBlocks.TicketGateBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CreateTransitTickets.MOD_ID);

    public static final RegistryObject<BlockEntityType<TicketGateBlockEntity>> TICKET_GATE =
            BLOCK_ENTITIES.register("ticket_gate", () -> BlockEntityType.Builder
                    .of(TicketGateBlockEntity::new, ModBlocks.TICKET_GATE.get())
                    .build(null));

    private ModBlockEntities() {
    }

    public static void register(IEventBus modBus) {
        BLOCK_ENTITIES.register(modBus);
    }
}
