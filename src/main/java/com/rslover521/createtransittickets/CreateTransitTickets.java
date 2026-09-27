package com.rslover521.createtransittickets;

import com.rslover521.createtransittickets.command.TicketCommands;
import com.rslover521.createtransittickets.network.ModNetworking;
import com.rslover521.createtransittickets.registry.ModBlockEntities;
import com.rslover521.createtransittickets.registry.ModBlocks;
import com.rslover521.createtransittickets.registry.ModCreativeTabs;
import com.rslover521.createtransittickets.registry.ModItems;
import com.rslover521.createtransittickets.registry.ModRecipeSerializers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(CreateTransitTickets.MOD_ID)
public final class CreateTransitTickets {
    public static final String MOD_ID = "create_transit_tickets";

    public CreateTransitTickets(FMLJavaModLoadingContext context, ModContainer modContainer) {
        IEventBus modEventBus = context.getModEventBus();
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModNetworking.register(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.GAME)
    public static final class ForgeEvents {
        private ForgeEvents() {
        }

        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            TicketCommands.register(event.getDispatcher());
        }
    }
}
