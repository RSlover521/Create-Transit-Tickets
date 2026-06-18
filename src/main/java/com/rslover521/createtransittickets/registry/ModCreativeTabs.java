package com.rslover521.createtransittickets.registry;

import com.rslover521.createtransittickets.CreateTransitTickets;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateTransitTickets.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATE_TRANSIT_TICKET_TAB =
            CREATIVE_MODE_TABS.register("create_transit_ticket", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.create_transit_ticket.create_transit_ticket_tab"))
                            .icon(() -> new ItemStack(ModItems.TRANSIT_TICKET.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(ModItems.TRANSIT_TICKET.get());
                                output.accept(ModItems.BLANK_TICKET.get());
                                output.accept(ModItems.TICKET_BLUEPRINT.get());
                            })
                            .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
