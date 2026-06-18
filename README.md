# Create: Transit Tickets

A Forge 1.20.1 mod for survival-friendly, expiring tickets used with Create transit systems.

## Current MVP

- Blank Tickets, reusable Ticket Blueprints, and issued Transit Tickets
- Game-time-based issuance and expiration stored in item NBT
- Valid/expired and remaining-time tooltips
- A stable hand-printing fallback: hold a blueprint and a blank ticket, then use the blueprint
- Creative/operator helper: `/transittickets blueprint <duration_ticks> [name]`

Create Deployer automation is intentionally left as a thin follow-up integration so the core mod remains usable without Create installed.
