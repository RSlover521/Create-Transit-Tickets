package com.rslover521.createtransittickets.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.rslover521.createtransittickets.util.TicketData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public final class TicketCommands {
    private TicketCommands() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("transittickets")
                .requires(TicketCommands::mayCreateBlueprint)
                .then(Commands.literal("blueprint")
                        .then(Commands.literal("passages")
                                .then(Commands.argument("count", IntegerArgumentType.integer(1))
                                        .executes(context -> givePassageBlueprint(context.getSource(),
                                                IntegerArgumentType.getInteger(context, "count"), "Transit Ticket"))
                                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                                .executes(context -> givePassageBlueprint(context.getSource(),
                                                        IntegerArgumentType.getInteger(context, "count"),
                                                        StringArgumentType.getString(context, "name"))))))
                        .then(Commands.argument("duration_ticks", LongArgumentType.longArg(1L))
                                .executes(context -> giveBlueprint(context.getSource(),
                                        LongArgumentType.getLong(context, "duration_ticks"), "Transit Ticket"))
                                .then(Commands.argument("name", StringArgumentType.greedyString())
                                        .executes(context -> giveBlueprint(context.getSource(),
                                                LongArgumentType.getLong(context, "duration_ticks"),
                                                StringArgumentType.getString(context, "name")))))));
    }

    private static boolean mayCreateBlueprint(CommandSourceStack source) {
        return source.hasPermission(2)
                || source.getEntity() instanceof ServerPlayer player && player.isCreative();
    }

    private static int giveBlueprint(CommandSourceStack source, long durationTicks, String name) {
        String safeName = normalizeName(name);
        return giveBlueprint(source, TicketData.createBlueprint(safeName, durationTicks), safeName,
                TicketData.formatDuration(durationTicks));
    }

    private static int givePassageBlueprint(CommandSourceStack source, int passages, String name) {
        String safeName = normalizeName(name);
        return giveBlueprint(source, TicketData.createPassageBlueprint(safeName, passages), safeName,
                Component.translatable("command.create_transit_tickets.passages", passages));
    }

    private static int giveBlueprint(CommandSourceStack source, ItemStack blueprint, String safeName, Object details) {
        ServerPlayer player;
        try {
            player = source.getPlayerOrException();
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException exception) {
            return 0;
        }

        if (!player.addItem(blueprint)) {
            player.drop(blueprint, false);
        }
        source.sendSuccess(() -> Component.translatable("command.create_transit_tickets.blueprint_created",
                safeName, details), false);
        return 1;
    }

    private static String normalizeName(String name) {
        String normalizedName = name.strip();
        if (normalizedName.isEmpty()) normalizedName = "Transit Ticket";
        return normalizedName.length() > 64 ? normalizedName.substring(0, 64) : normalizedName;
    }
}
