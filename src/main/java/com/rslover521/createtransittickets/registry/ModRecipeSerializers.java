package com.rslover521.createtransittickets.registry;

import com.rslover521.createtransittickets.CreateTransitTickets;
import com.rslover521.createtransittickets.recipe.TicketDeployingRecipe;
import com.rslover521.createtransittickets.recipe.TicketPressingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CreateTransitTickets.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> TICKET_DEPLOYING =
            RECIPE_SERIALIZERS.register("ticket_deploying",
                    () -> new ProcessingRecipeSerializer<>(TicketDeployingRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> TICKET_PRESSING =
            RECIPE_SERIALIZERS.register("ticket_pressing",
                    () -> new ProcessingRecipeSerializer<>(TicketPressingRecipe::new));

    private ModRecipeSerializers() {
    }

    public static void register(IEventBus modBus) {
        RECIPE_SERIALIZERS.register(modBus);
    }
}
