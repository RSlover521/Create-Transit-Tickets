package com.rslover521.createtransittickets.client.ponder;

import com.rslover521.createtransittickets.registry.ModItems;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public final class BlankTicketPonderScenes {
    private BlankTicketPonderScenes() {
    }

    public static void usingBlankTickets(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("blank_ticket", "Using Blank Tickets");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.world.showSection(util.select.everywhere(), Direction.DOWN);
        scene.idle(20);

        Vec3 itemDisplay = util.vector.of(2.5, 2.75, 2.5);
        scene.overlay.showControls(new InputWindowElement(itemDisplay, Pointing.DOWN)
                .withItem(new ItemStack(ModItems.BLANK_TICKET.get())), 50);
        scene.overlay.showText(50)
                .text("Blank Tickets are the starting point for every Transit Ticket")
                .independent()
                .placeNearTarget();
        scene.idle(60);

        scene.addKeyframe();
        scene.overlay.showControls(new InputWindowElement(itemDisplay, Pointing.DOWN)
                .withItem(new ItemStack(ModItems.TICKET_BLUEPRINT.get())), 60);
        scene.overlay.showText(60)
                .text("Configure a Ticket Blueprint, then supply it to a Deployer above the Blank Ticket")
                .independent()
                .placeNearTarget();
        scene.idle(70);

        scene.addKeyframe();
        scene.overlay.showControls(new InputWindowElement(itemDisplay, Pointing.DOWN)
                .withItem(new ItemStack(ModItems.INCOMPLETE_TRANSIT_TICKET.get())), 50);
        scene.overlay.showText(50)
                .text("The Deployer copies the blueprint settings and creates an Incomplete Transit Ticket")
                .independent()
                .placeNearTarget();
        scene.idle(60);

        scene.addKeyframe();
        scene.overlay.showControls(new InputWindowElement(itemDisplay, Pointing.DOWN)
                .withItem(new ItemStack(ModItems.TRANSIT_TICKET.get())), 60);
        scene.overlay.showText(60)
                .text("Finish the Incomplete Ticket with a Mechanical Press before using it at a Ticket Gate")
                .independent()
                .placeNearTarget();
        scene.idle(70);
    }
}
