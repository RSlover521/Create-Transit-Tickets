package com.rslover521.createtransittickets.client;

import com.rslover521.createtransittickets.customBlocks.TicketGateBlockEntity;
import com.rslover521.createtransittickets.network.ConfigureTicketGatePacket;
import com.rslover521.createtransittickets.network.ModNetworking;
import com.rslover521.createtransittickets.util.GateServiceRequirement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public final class TicketGateScreen extends Screen {
    private static final int WIDTH = 220;
    private final BlockPos pos;
    private GateServiceRequirement requirement = GateServiceRequirement.ANY;

    public TicketGateScreen(BlockPos pos) {
        super(Component.translatable("screen.create_transit_tickets.ticket_gate.title"));
        this.pos = pos.immutable();
    }

    @Override
    protected void init() {
        if (minecraft.level != null
                && minecraft.level.getBlockEntity(pos) instanceof TicketGateBlockEntity gate) {
            requirement = gate.getRequiredService();
        }

        int left = (width - WIDTH) / 2;
        int top = height / 2 - 40;
        addRenderableWidget(CycleButton.<GateServiceRequirement>builder(this::requirementName)
                .withValues(GateServiceRequirement.values())
                .withInitialValue(requirement)
                .create(left, top, WIDTH, 20,
                        Component.translatable("screen.create_transit_tickets.ticket_gate.required_service"),
                        (button, value) -> requirement = value));
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> save())
                .bounds(left, top + 34, 106, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> onClose())
                .bounds(left + 114, top + 34, 106, 20).build());
    }

    private Component requirementName(GateServiceRequirement value) {
        return Component.translatable("gate_service_requirement.create_transit_tickets."
                + value.name().toLowerCase(Locale.ROOT));
    }

    private void save() {
        ModNetworking.CHANNEL.sendToServer(new ConfigureTicketGatePacket(pos, requirement));
        onClose();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, height / 2 - 65, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
