# Create: Transit Tickets

![Create: Transit Tickets mod icon](https://raw.githubusercontent.com/RSlover521/Create-Transit-Tickets/main/src/main/resources/create_transit_tickets.png)

Survival-friendly, configurable transit tickets and ticket gates for Create train networks.

[![GitHub Repository](https://img.shields.io/badge/GitHub-Repository-181717?style=for-the-badge&logo=github&color=darkcyan)](https://github.com/RSlover521/Create-Transit-Tickets)
[![Latest Release](https://img.shields.io/github/v/release/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=lime)](https://github.com/RSlover521/Create-Transit-Tickets/releases)
[![License](https://img.shields.io/github/license/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=red)](https://github.com/RSlover521/Create-Transit-Tickets/blob/main/LICENSE)
[![Discussions](https://img.shields.io/github/discussions/RSlover521/Create-Transit-Tickets?style=for-the-badge&color=blue&logo=github)](https://github.com/RSlover521/Create-Transit-Tickets/discussions)
[![Open Issues](https://img.shields.io/github/issues/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=limegreen)](https://github.com/RSlover521/Create-Transit-Tickets/issues)
[![Total Downloads](https://img.shields.io/github/downloads/RSlover521/Create-Transit-Tickets/total?style=for-the-badge&logo=github&color=white)](https://github.com/RSlover521/Create-Transit-Tickets/releases)

---

## About

Create: Transit Tickets adds reusable ticket blueprints, issued transit tickets, and working ticket gates for public transportation systems built with the **Create** mod.

Tickets can be configured in either of two ways:

- **Time-limited tickets** remain valid for a configured number of game ticks.
- **Passage-limited tickets** remain valid for a configured number of ticket-gate uses.

Both types display their current status in the item tooltip. Time-limited tickets show their remaining duration or that they have expired; passage-limited tickets show their remaining and total passages.

---

## Ticket Workflow

1. Craft a **Blank Ticket**.
2. Obtain a configured **Ticket Blueprint** using the helper command below.
3. Hold the blueprint and a blank ticket in opposite hands.
4. Use the blueprint to issue a **Transit Ticket**. One blank ticket is consumed in Survival mode; the blueprint is reusable.
5. Hold the issued ticket and use it on a **Ticket Gate** to enter.

### Blank Ticket

- The material consumed when a ticket is issued.
- Contains no validity or expiration data by itself.
- Two sheets of paper craft four blank tickets.

### Ticket Blueprint

- A reusable template that issues tickets.
- Stores the ticket name and either its duration or allowed passage count.
- Displays its configured duration or passage count in the tooltip.
- Can also carry route and zone metadata for future features.

### Transit Ticket

- Uses the name configured on its blueprint.
- A time-limited ticket stores its issue time, duration, and expiration time using the world's game time.
- A passage-limited ticket stores its original and remaining passage counts.
- Clearly displays **Valid**, **Expired**, or **Used Up** in the tooltip.

---

## Blueprint Commands

Configured blueprints can be created by players in Creative mode and by command sources with permission level 2 or higher. The command gives the blueprint to the player, or drops it nearby if their inventory is full.

### Time-limited tickets

```text
/transittickets blueprint <duration_ticks> [name]
```

`duration_ticks` must be at least `1`. The optional name may contain spaces and is limited to 64 characters. If no name is supplied, the ticket is named `Transit Ticket`.

For example, a 30-minute ticket lasts 36,000 ticks:

```text
/transittickets blueprint 36000 30 Minute Pass
```

Minecraft normally runs at 20 ticks per second. Ticket time advances with the world's game time and pauses when the world is not running.

### Passage-limited tickets

```text
/transittickets blueprint passages <count> [name]
```

`count` must be at least `1`. Each successful validation at a ticket gate consumes one passage immediately.

Examples:

```text
/transittickets blueprint passages 1 Single Ride
/transittickets blueprint passages 5 Five Ride Pass
```

---

## Ticket Gate Behavior

- Use the gate while holding an issued, valid **Transit Ticket**.
- A valid ticket plays an acceptance sound and opens the center barrier.
- Passage-limited tickets lose one passage as soon as the gate accepts them. At zero passages, the ticket becomes **Used Up** and cannot open another gate.
- Time-limited tickets can open gates repeatedly until their world-time expiration.
- The gate closes after the player passes through and leaves the gate block.
- If nobody enters, the gate automatically closes after 100 ticks (about five seconds).
- An empty hand, an unissued ticket, an expired ticket, a used-up ticket, or any other item is rejected with a denial sound and an on-screen error message.

---

## Planned Features

- Create Deployer ticket printing
- Configurable default ticket types and allowed durations
- Optional compatibility with other Create transit and security add-ons

Planned features may change as development continues.

---

## Available Languages

- English (US)

Translations are welcome through pull requests.

---

## Installation

1. Install **Minecraft 1.20.1**.
2. Install **Minecraft Forge 47.x**. The development environment currently uses Forge **47.4.0**.
3. Install **[Create 0.5.1.f](https://modrinth.com/mod/create/version/HNYrbfZZ)** for Minecraft 1.20.1.
4. Download Create: Transit Tickets from the [GitHub Releases](https://github.com/RSlover521/Create-Transit-Tickets/releases) page.
5. Place both Create and Create: Transit Tickets in the Minecraft `mods` folder.
6. Launch Minecraft with the Forge profile.

> Create 0.5.1.f includes its required Flywheel and Registrate components in the distributed mod jar.

---

## Building from Source

Requirements:

- Java Development Kit 17
- Git

Clone and build the project:

```shell
git clone https://github.com/RSlover521/Create-Transit-Tickets.git
cd Create-Transit-Tickets
./gradlew build
```

On Windows PowerShell or Command Prompt, use:

```powershell
.\gradlew.bat build
```

The built mod jar will be created in `build/libs/`.

To launch the Forge development client:

```powershell
.\gradlew.bat runClient
```

---

## Links

- [Repository](https://github.com/RSlover521/Create-Transit-Tickets)
- [Releases](https://github.com/RSlover521/Create-Transit-Tickets/releases)
- [Report an Issue](https://github.com/RSlover521/Create-Transit-Tickets/issues)
- [Discussions](https://github.com/RSlover521/Create-Transit-Tickets/discussions)
- [Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.1.html)
- [Create 0.5.1.f](https://modrinth.com/mod/create/version/HNYrbfZZ)

---

## Supported Mod Versions

| Version | Minecraft | Forge | Create | Supported |
|---|---|---|---|:---:|
| 0.1.1-beta | 1.20.1 | 47.x | 0.5.1.f | Yes |
| 0.1.0-beta | 1.20.1 | 47.x | 0.5.1.f | Yes |

> This project is currently in beta, so features and saved item data may change between releases. Please confirm that you are using a supported version before opening an issue.

---

## License

- MIT License — see the [project license](https://github.com/RSlover521/Create-Transit-Tickets/blob/main/LICENSE) for details.
- Minecraft, Minecraft Forge, and Create belong to their respective owners.
