package com.rslover521.createtransittickets.util;

import com.rslover521.createtransittickets.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public final class TicketData {
    public static final String TICKET_NAME = "ticket_name";
    public static final String TICKET_TYPE = "ticket_type";
    public static final String TICKET_SERVICE = "ticket_service";
    public static final String DURATION_TICKS = "duration_ticks";
    public static final String ROUTE_ID = "route_id";
    public static final String ZONE_ID = "zone_id";
    public static final String ISSUED_TIME = "issued_time";
    public static final String VALID_UNTIL = "valid_until";
    public static final String ALLOWED_PASSAGES = "allowed_passages";
    public static final String REMAINING_PASSAGES = "remaining_passages";
    public static final long DEFAULT_DURATION_TICKS = 12_000L;

    private TicketData() {
    }

    public static ItemStack createBlueprint(String name, long durationTicks) {
        ItemStack blueprint = new ItemStack(ModItems.TICKET_BLUEPRINT.get());
        CompoundTag tag = blueprint.getOrCreateTag();
        tag.putString(TICKET_NAME, name);
        tag.putString(TICKET_TYPE, TicketTypes.LIMITED_TIME.name());
        tag.putString(TICKET_SERVICE, TicketServices.LOCAL.name());
        tag.putLong(DURATION_TICKS, Math.max(1L, durationTicks));
        return blueprint;
    }

    public static ItemStack createPassageBlueprint(String name, int allowedPassages) {
        ItemStack blueprint = new ItemStack(ModItems.TICKET_BLUEPRINT.get());
        CompoundTag tag = blueprint.getOrCreateTag();
        tag.putString(TICKET_NAME, name);
        tag.putString(TICKET_TYPE, allowedPassages == 1
                ? TicketTypes.SINGLE_USE.name() : TicketTypes.MULTIPLE_USE.name());
        tag.putString(TICKET_SERVICE, TicketServices.LOCAL.name());
        tag.putInt(ALLOWED_PASSAGES, Math.max(1, allowedPassages));
        return blueprint;
    }

    public static ItemStack issueTicket(ItemStack blueprint, long issuedTime) {
        ItemStack ticket = new ItemStack(ModItems.TRANSIT_TICKET.get());
        CompoundTag source = blueprint.getTag();
        CompoundTag target = ticket.getOrCreateTag();

        copyString(source, target, TICKET_NAME);
        copyString(source, target, TICKET_TYPE);
        copyString(source, target, TICKET_SERVICE);
        copyString(source, target, ROUTE_ID);
        copyString(source, target, ZONE_ID);

        target.putLong(ISSUED_TIME, issuedTime);
        TicketTypes type = getTicketType(blueprint);
        if (type == TicketTypes.SINGLE_USE || type == TicketTypes.MULTIPLE_USE) {
            int passages = getAllowedPassages(blueprint);
            target.putInt(ALLOWED_PASSAGES, passages);
            target.putInt(REMAINING_PASSAGES, passages);
        } else if (type == TicketTypes.LIMITED_TIME) {
            long duration = getDuration(blueprint);
            target.putLong(DURATION_TICKS, duration);
            target.putLong(VALID_UNTIL, saturatingAdd(issuedTime, duration));
        } else {
            target.putLong(VALID_UNTIL, Long.MAX_VALUE);
        }
        return ticket;
    }

    public static String getTicketName(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(TICKET_NAME, Tag.TAG_STRING) ? tag.getString(TICKET_NAME) : "";
    }

    public static TicketTypes getTicketType(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TICKET_TYPE, Tag.TAG_STRING)) {
            String value = tag.getString(TICKET_TYPE);
            if ("UNLIMITED_PASS".equals(value)) return TicketTypes.UNLIMITED_TIME;
            try {
                return TicketTypes.valueOf(value);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return isPassageLimited(stack)
                ? (getAllowedPassages(stack) == 1 ? TicketTypes.SINGLE_USE : TicketTypes.MULTIPLE_USE)
                : TicketTypes.LIMITED_TIME;
    }

    public static TicketServices getTicketService(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TICKET_SERVICE, Tag.TAG_STRING)) {
            try {
                return TicketServices.valueOf(tag.getString(TICKET_SERVICE));
            } catch (IllegalArgumentException ignored) {
            }
        }
        return TicketServices.LOCAL;
    }

    public static void configureBlueprint(ItemStack stack, String name, TicketTypes type,
                                          TicketServices service, long value) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(TICKET_NAME, normalizeName(name));
        tag.putString(TICKET_TYPE, type.name());
        tag.putString(TICKET_SERVICE, service.name());
        tag.remove(ALLOWED_PASSAGES);
        tag.remove(DURATION_TICKS);
        if (type == TicketTypes.SINGLE_USE) {
            tag.putInt(ALLOWED_PASSAGES, 1);
        } else if (type == TicketTypes.MULTIPLE_USE) {
            tag.putInt(ALLOWED_PASSAGES, (int) Math.max(1L, Math.min(Integer.MAX_VALUE, value)));
        } else if (type == TicketTypes.LIMITED_TIME) {
            tag.putLong(DURATION_TICKS, Math.max(1L, value));
        }
    }

    public static String normalizeName(String name) {
        String normalized = name.strip();
        if (normalized.isEmpty()) normalized = "Transit Ticket";
        return normalized.length() > 64 ? normalized.substring(0, 64) : normalized;
    }

    public static long parseDuration(String input) {
        if (input == null || input.length() < 2) return -1L;
        String normalized = input.strip().toLowerCase(java.util.Locale.ROOT);
        if (normalized.length() < 2) return -1L;
        long multiplier = switch (normalized.charAt(normalized.length() - 1)) {
            case 't' -> 1L;
            case 's' -> 20L;
            case 'm' -> 1_200L;
            case 'h' -> 72_000L;
            case 'd' -> 24_000L;
            default -> -1L;
        };
        if (multiplier < 0L) return -1L;
        try {
            long amount = Long.parseLong(normalized.substring(0, normalized.length() - 1));
            if (amount < 1L || amount > Long.MAX_VALUE / multiplier) return -1L;
            return amount * multiplier;
        } catch (NumberFormatException ignored) {
            return -1L;
        }
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
                && (tag.contains(VALID_UNTIL, Tag.TAG_ANY_NUMERIC)
                || tag.contains(REMAINING_PASSAGES, Tag.TAG_ANY_NUMERIC));
    }

    public static boolean isPassageLimited(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(ALLOWED_PASSAGES, Tag.TAG_ANY_NUMERIC);
    }

    public static int getAllowedPassages(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0 : Math.max(0, tag.getInt(ALLOWED_PASSAGES));
    }

    public static int getRemainingPassages(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0 : Math.max(0, tag.getInt(REMAINING_PASSAGES));
    }

    public static boolean consumePassage(ItemStack stack) {
        if (!isPassageLimited(stack)) return true;
        int remaining = getRemainingPassages(stack);
        if (remaining <= 0) return false;
        stack.getOrCreateTag().putInt(REMAINING_PASSAGES, remaining - 1);
        return true;
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
