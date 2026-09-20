package com.rslover521.createtransittickets.client.ponder;

import com.rslover521.createtransittickets.customBlocks.TicketGateBlock;
import com.rslover521.createtransittickets.registry.ModBlocks;
import com.rslover521.createtransittickets.registry.ModItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public final class TicketGatePonderScenes {
    private static final BlockPos GATE_POS = new BlockPos(2, 1, 2);

    private TicketGatePonderScenes() {
    }

    public static void usingTicketGates(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("ticket_gate", "Using Ticket Gates");
        prepareGate(scene, util);

        Vec3 gateTop = util.vector().topOf(GATE_POS);
        Vec3 gateFront = util.vector().blockSurface(GATE_POS, Direction.NORTH);

        scene.overlay().showText(100)
                .text("Ticket Gates validate issued Transit Tickets before allowing a passenger through")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(110);

        scene.addKeyframe();
        scene.overlay().showControls(gateFront, Pointing.DOWN, 60)
                .rightClick()
                .withItem(new ItemStack(ModItems.TRANSIT_TICKET.get()));
        scene.overlay().showText(120)
                .colored(PonderPalette.RED)
                .text("Empty hands and unissued, expired, or used-up tickets are denied with an error message and sound")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(130);

        scene.addKeyframe();
        scene.overlay().showText(120)
                .colored(PonderPalette.RED)
                .text("A gate configured for Local, Semi-Fast, or Express service also rejects tickets from a different service")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(130);

        scene.addKeyframe();
        scene.overlay().showControls(gateFront, Pointing.DOWN, 60)
                .rightClick()
                .withItem(new ItemStack(ModItems.TRANSIT_TICKET.get()));
        scene.effects().indicateSuccess(GATE_POS);
        scene.world().modifyBlock(GATE_POS, state -> state.setValue(TicketGateBlock.OPEN, true), false);
        scene.overlay().showText(120)
                .colored(PonderPalette.GREEN)
                .text("A valid ticket matching the required service plays an acceptance sound and opens the gate")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(130);

        scene.addKeyframe();
        scene.overlay().showText(110)
                .text("Single-use and multiple-use tickets consume one passage; time-based tickets remain valid until they expire")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(120);

        scene.addKeyframe();
        scene.overlay().showText(130)
                .text("The open model is passable. Once a passenger enters, the gate waits for them to leave before closing")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(140);
        scene.world().modifyBlock(GATE_POS, state -> state
                .setValue(TicketGateBlock.OPEN, false)
                .setValue(TicketGateBlock.PASSAGE_STARTED, false), false);
        scene.idle(20);

        scene.addKeyframe();
        scene.overlay().showText(100)
                .text("If nobody enters, the gate closes automatically after five seconds")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(110);
    }

    public static void configuringTicketGates(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("ticket_gate_configuration", "Configuring Ticket Gates");
        prepareGate(scene, util);

        Vec3 gateTop = util.vector().topOf(GATE_POS);
        Vec3 gateFront = util.vector().blockSurface(GATE_POS, Direction.NORTH);
        ItemStack wrench = new ItemStack(ForgeRegistries.ITEMS.getValue(
                ResourceLocation.fromNamespaceAndPath("create", "wrench")));

        scene.overlay().showControls(gateFront, Pointing.DOWN, 70)
                .rightClick()
                .withItem(wrench);
        scene.overlay().showText(120)
                .text("Server operators can right-click a Ticket Gate with a Wrench to open its configuration screen")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(130);

        scene.addKeyframe();
        scene.overlay().showText(140)
                .text("The required service can be Any Service, Local, Semi-Fast, or Express; the setting is saved with the gate")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(150);

        scene.addKeyframe();
        scene.overlay().showText(110)
                .colored(PonderPalette.RED)
                .text("Players without operator permission cannot configure or dismantle Ticket Gates")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(120);

        scene.addKeyframe();
        scene.overlay().showControls(gateFront, Pointing.DOWN, 70)
                .rightClick()
                .whileSneaking()
                .withItem(wrench);
        scene.overlay().showText(110)
                .text("An operator can sneak-right-click with a Wrench to dismantle the gate and recover it as an item")
                .pointAt(gateTop)
                .placeNearTarget();
        scene.idle(80);
        scene.world().destroyBlock(GATE_POS);
        scene.world().createItemEntity(gateTop, new Vec3(0, 0.15, 0),
                new ItemStack(ModItems.TICKET_GATE.get()));
        scene.idle(40);
    }

    private static void prepareGate(SceneBuilder scene, SceneBuildingUtil util) {
        scene.configureBasePlate(0, 0, 5);
        scene.scaleSceneView(0.9F);
        scene.world().setBlocks(util.select().layersFrom(1), Blocks.AIR.defaultBlockState(), false);

        BlockState gate = ModBlocks.TICKET_GATE.get().defaultBlockState()
                .setValue(TicketGateBlock.FACING, Direction.SOUTH)
                .setValue(TicketGateBlock.OPEN, false)
                .setValue(TicketGateBlock.PASSAGE_STARTED, false);
        scene.world().setBlock(GATE_POS, gate, false);
        scene.showBasePlate();
        scene.world().showSection(util.select().position(GATE_POS), Direction.DOWN);
        scene.idle(40);
    }
}
