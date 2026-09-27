package com.rslover521.createtransittickets.registry;

import com.rslover521.createtransittickets.CreateTransitTickets;
import com.rslover521.createtransittickets.recipe.TicketDeployingRecipe;
import com.rslover521.createtransittickets.recipe.TicketPressingRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CreateTransitTickets.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TicketDeployingRecipe>> TICKET_DEPLOYING =
            RECIPE_SERIALIZERS.register("ticket_deploying",
                    () -> new ItemApplicationRecipe.Serializer<>(TicketDeployingRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TicketPressingRecipe>> TICKET_PRESSING =
            RECIPE_SERIALIZERS.register("ticket_pressing",
                    () -> new StandardProcessingRecipe.Serializer<>(TicketPressingRecipe::new));

    private ModRecipeSerializers() {
    }

    public static void register(IEventBus modBus) {
        RECIPE_SERIALIZERS.register(modBus);
    }
}
