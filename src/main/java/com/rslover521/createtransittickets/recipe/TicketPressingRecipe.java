package com.rslover521.createtransittickets.recipe;

import com.rslover521.createtransittickets.registry.ModRecipeSerializers;
import com.rslover521.createtransittickets.util.TicketData;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public final class TicketPressingRecipe extends PressingRecipe {
    public TicketPressingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(params);
    }

    @Override
    public boolean matches(RecipeWrapper inventory, Level level) {
        if (!super.matches(inventory, level)) {
            return false;
        }

        ItemStack incompleteTicket = inventory.getItem(0).copy();
        long issuedTime = level.getGameTime();
        enforceNextResult(() -> TicketData.issueTicket(incompleteTicket, issuedTime));
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.TICKET_PRESSING.get();
    }
}
