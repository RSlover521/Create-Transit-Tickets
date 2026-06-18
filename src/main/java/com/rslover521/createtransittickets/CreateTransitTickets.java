package com.rslover521.createtransittickets;

import com.rslover521.createtransittickets.command.TicketCommands;
import com.rslover521.createtransittickets.registry.ModCreativeTabs;
import com.rslover521.createtransittickets.registry.ModItems;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateTransitTickets.MOD_ID)
public final class CreateTransitTickets {
    public static final String MOD_ID = "create_transit_tickets";

    public CreateTransitTickets(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static final class ForgeEvents {
        private ForgeEvents() {
        }

        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            TicketCommands.register(event.getDispatcher());
        }
    }
}
