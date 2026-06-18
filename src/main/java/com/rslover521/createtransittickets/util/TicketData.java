package com.rslover521.createtransittickets.util;

import com.rslover521.createtransittickets.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public final class TicketData {
    public static final String TICKET_NAME = "ticket_name";
    public static final String DURATION_TICKS = "duration_ticks";
    public static final String ROUTE_ID = "route_id";
    public static final String ZONE_ID = "zone_id";
    public static final String ISSUED_TIME = "issued_time";
    public static final String VALID_UNTIL = "valid_until";
    public static final long DEFAULT_DURATION_TICKS = 12_000L;

    private TicketData() {
    }

    public static ItemStack createBlueprint(String name, long durationTicks) {
        ItemStack blueprint = new ItemStack(ModItems.TICKET_BLUEPRINT.get());
        CompoundTag tag = blueprint.getOrCreateTag();
        tag.putString(TICKET_NAME, name);
        tag.putLong(DURATION_TICKS, Math.max(1L, durationTicks));
        return blueprint;
    }

    public static ItemStack issueTicket(ItemStack blueprint, long issuedTime) {
        ItemStack ticket = new ItemStack(ModItems.TRANSIT_TICKET.get());
        CompoundTag source = blueprint.getTag();
        CompoundTag target = ticket.getOrCreateTag();

        copyString(source, target, TICKET_NAME);
        copyString(source, target, ROUTE_ID);
        copyString(source, target, ZONE_ID);

        long duration = getDuration(blueprint);
        target.putLong(DURATION_TICKS, duration);
        target.putLong(ISSUED_TIME, issuedTime);
        target.putLong(VALID_UNTIL, saturatingAdd(issuedTime, duration));
        return ticket;
    }

    public static String getTicketName(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(TICKET_NAME, Tag.TAG_STRING) ? tag.getString(TICKET_NAME) : "";
    }

    public static long getDuration(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(DURATION_TICKS, Tag.TAG_ANY_NUMERIC)) {
            return DEFAULT_DURATION_TICKS;
        }
        return Math.max(1L, tag.getLong(DURATION_TICKS));
    }

    public static boolean isIssued(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(ISSUED_TIME, Tag.TAG_ANY_NUMERIC)
                && tag.contains(VALID_UNTIL, Tag.TAG_ANY_NUMERIC);
    }

    public static long getIssuedTime(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0L : tag.getLong(ISSUED_TIME);
    }

    public static long getValidUntil(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0L : tag.getLong(VALID_UNTIL);
    }

    public static String formatDuration(long ticks) {
        long totalSeconds = Math.max(0L, ticks) / 20L;
        long days = totalSeconds / 86_400L;
        long hours = totalSeconds % 86_400L / 3_600L;
        long minutes = totalSeconds % 3_600L / 60L;
        long seconds = totalSeconds % 60L;

        if (days > 0) return days + "d " + hours + "h";
        if (hours > 0) return hours + "h " + minutes + "m";
        if (minutes > 0) return minutes + "m " + seconds + "s";
        return seconds + "s";
    }

    private static void copyString(CompoundTag source, CompoundTag target, String key) {
        if (source != null && source.contains(key, Tag.TAG_STRING)) {
            target.putString(key, source.getString(key));
        }
    }

    private static long saturatingAdd(long left, long right) {
        return Long.MAX_VALUE - left < right ? Long.MAX_VALUE : left + right;
    }
}
