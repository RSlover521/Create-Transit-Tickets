# Create: Transit Tickets Changelog

### Ticket production

- Replaced direct ticket issuance from a Ticket Blueprint and an offhand Blank Ticket with a two-stage Create machinery workflow.
- Added the Incomplete Transit Ticket intermediate item.
- Added a Deployer recipe that applies a Ticket Blueprint to a Blank Ticket and produces an Incomplete Transit Ticket.
- Ticket Blueprints are retained by the Deployer and can be reused to produce additional tickets.
- Added a Mechanical Press recipe that turns an Incomplete Transit Ticket into a Transit Ticket.
- Removed the old opposite-hands shortcut; right-clicking a Ticket Blueprint now opens its configuration screen.

### Ticket data

- Added custom deploying and pressing recipe serializers for ticket production.
- Configured blueprint NBT is copied to the Incomplete Transit Ticket during deployment.
- Pressing issues the finished Transit Ticket using the stored blueprint settings.
- Finished tickets retain their configured name, ticket type, service, route, zone, duration, or passage count as applicable.
- Limited Time tickets receive their issue and expiration times when pressing completes.
- Single Use and Multiple Use tickets receive their allowed and remaining passage counts when pressing completes.
- Unlimited Time tickets are issued without an expiration.
- Fixed finished tickets from the machinery workflow appearing as unissued or invalid at Ticket Gates.

### User interface and resources

- Added the Incomplete Transit Ticket to the mod creative tab.
- Added its English display name and a temporary item model that reuses the Blank Ticket texture.
- Updated Create-style Ticket Blueprint and Blank Ticket tooltips to explain deployment and mechanical pressing.
- Added separate documentation explaining how to produce a valid ticket with Create machinery.

### Compatibility

- Built for Minecraft 1.20.1, Forge 47.x, and Create 6.0.8.
