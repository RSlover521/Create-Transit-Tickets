package com.rslover521.createtransittickets.recipe;

import com.rslover521.createtransittickets.registry.ModRecipeSerializers;
import com.rslover521.createtransittickets.util.TicketData;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public final class TicketDeployingRecipe extends ItemApplicationRecipe {
    public TicketDeployingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(AllRecipeTypes.DEPLOYING, params);
    }

    @Override
    public boolean matches(RecipeWrapper inventory, Level level) {
        if (!super.matches(inventory, level)) {
            return false;
        }

        ItemStack incompleteTicket = TicketData.createIncompleteTicket(inventory.getItem(1));
        enforceNextResult(incompleteTicket::copy);
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.TICKET_DEPLOYING.get();
    }
}
