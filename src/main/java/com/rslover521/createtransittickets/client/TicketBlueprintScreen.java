package com.rslover521.createtransittickets.client;

import com.rslover521.createtransittickets.network.ConfigureBlueprintPacket;
import com.rslover521.createtransittickets.network.ModNetworking;
import com.rslover521.createtransittickets.util.TicketData;
import com.rslover521.createtransittickets.util.TicketServices;
import com.rslover521.createtransittickets.util.TicketTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;

public final class TicketBlueprintScreen extends Screen {
    private static final int WIDTH = 220;
    private final InteractionHand hand;
    private EditBox nameBox;
    private EditBox valueBox;
    private Button doneButton;
    private TicketTypes ticketType;
    private TicketServices ticketService;

    public TicketBlueprintScreen(InteractionHand hand) {
        super(Component.translatable("screen.create_transit_tickets.blueprint.title"));
        this.hand = hand;
    }

    @Override
    protected void init() {
        ItemStack blueprint = minecraft.player.getItemInHand(hand);
        ticketType = TicketData.getTicketType(blueprint);
        ticketService = TicketData.getTicketService(blueprint);
        int left = (width - WIDTH) / 2;
        int top = height / 2 - 85;

        nameBox = addRenderableWidget(new EditBox(font, left, top + 22, WIDTH, 20,
                Component.translatable("screen.create_transit_tickets.blueprint.name")));
        nameBox.setMaxLength(64);
        nameBox.setHint(Component.translatable("screen.create_transit_tickets.blueprint.name"));
        nameBox.setValue(TicketData.getTicketName(blueprint));

        addRenderableWidget(CycleButton.<TicketTypes>builder(this::typeName)
                .withValues(TicketTypes.values()).withInitialValue(ticketType)
                .create(left, top + 50, WIDTH, 20,
                        Component.translatable("screen.create_transit_tickets.blueprint.type"),
                        (button, value) -> {
                            ticketType = value;
                            rebuildValueBox();
                        }));

        addRenderableWidget(CycleButton.<TicketServices>builder(this::serviceName)
                .withValues(TicketServices.values()).withInitialValue(ticketService)
                .create(left, top + 78, WIDTH, 20,
                        Component.translatable("screen.create_transit_tickets.blueprint.service"),
                        (button, value) -> ticketService = value));

        doneButton = addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> save())
                .bounds(left, top + 134, 106, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> onClose())
                .bounds(left + 114, top + 134, 106, 20).build());
        rebuildValueBox();
    }

    private void rebuildValueBox() {
        if (valueBox != null) removeWidget(valueBox);
        valueBox = null;
        if (ticketType != TicketTypes.MULTIPLE_USE && ticketType != TicketTypes.LIMITED_TIME) {
            updateDoneButton();
            return;
        }

        int left = (width - WIDTH) / 2;
        int top = height / 2 - 85;
        Component label = Component.translatable(ticketType == TicketTypes.MULTIPLE_USE
                ? "screen.create_transit_tickets.blueprint.passages"
                : "screen.create_transit_tickets.blueprint.duration");
        valueBox = addRenderableWidget(new EditBox(font, left, top + 106, WIDTH, 20, label));
        valueBox.setHint(label);
        ItemStack blueprint = minecraft.player.getItemInHand(hand);
        valueBox.setValue(ticketType == TicketTypes.MULTIPLE_USE
                ? Integer.toString(Math.max(1, TicketData.getAllowedPassages(blueprint)))
                : TicketData.getDuration(blueprint) + "t");
        valueBox.setResponder(value -> updateDoneButton());
        updateDoneButton();
    }

    private void updateDoneButton() {
        if (doneButton != null) doneButton.active = getValue() >= 1L;
    }

    private long getValue() {
        if (ticketType == TicketTypes.SINGLE_USE || ticketType == TicketTypes.UNLIMITED_TIME) return 1L;
        if (valueBox == null) return -1L;
        if (ticketType == TicketTypes.LIMITED_TIME) return TicketData.parseDuration(valueBox.getValue());
        try {
            int passages = Integer.parseInt(valueBox.getValue());
            return passages >= 1 ? passages : -1L;
        } catch (NumberFormatException ignored) {
            return -1L;
        }
    }

    private void save() {
        long value = getValue();
        if (value < 1L) return;
        ModNetworking.CHANNEL.sendToServer(new ConfigureBlueprintPacket(hand,
                TicketData.normalizeName(nameBox.getValue()), ticketType, ticketService, value));
        onClose();
    }

    private Component typeName(TicketTypes type) {
        return Component.translatable("ticket_type.create_transit_tickets." + type.name().toLowerCase(Locale.ROOT));
    }

    private Component serviceName(TicketServices service) {
        return Component.translatable("ticket_service.create_transit_tickets." + service.name().toLowerCase(Locale.ROOT));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, height / 2 - 100, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
