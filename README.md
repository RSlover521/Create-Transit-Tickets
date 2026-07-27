# 🎟️ Create: Transit Tickets 🚂

![Create: Transit Tickets mod icon](https://raw.githubusercontent.com/RSlover521/Create-Transit-Tickets/main/src/main/resources/create_transit_tickets.png)

> 🚉 Survival-friendly, configurable transit tickets and ticket gates for Create train networks.

[![GitHub Repository](https://img.shields.io/badge/GitHub-Repository-181717?style=for-the-badge&logo=github&color=darkcyan)](https://github.com/RSlover521/Create-Transit-Tickets)
[![Latest Release](https://img.shields.io/github/v/release/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=lime)](https://github.com/RSlover521/Create-Transit-Tickets/releases)
[![License](https://img.shields.io/github/license/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=red)](https://github.com/RSlover521/Create-Transit-Tickets/blob/main/LICENSE)
[![Discussions](https://img.shields.io/github/discussions/RSlover521/Create-Transit-Tickets?style=for-the-badge&color=blue&logo=github)](https://github.com/RSlover521/Create-Transit-Tickets/discussions)
[![Open Issues](https://img.shields.io/github/issues/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=limegreen)](https://github.com/RSlover521/Create-Transit-Tickets/issues)
[![Total Downloads](https://img.shields.io/github/downloads/RSlover521/Create-Transit-Tickets/total?style=for-the-badge&logo=github&color=white)](https://github.com/RSlover521/Create-Transit-Tickets/releases)

---

## 🚆 About

Create: Transit Tickets adds reusable ticket blueprints, issued transit tickets, and working ticket gates for public transportation systems built with the **Create** mod.

Ticket blueprints are configured through an in-game menu—commands are no longer required for normal use. A blueprint stores a custom name, usage type, and transit service.

- 🎫 **Single Use** tickets allow one gate passage.
- 🔢 **Multiple Use** tickets allow a configurable number of passages.
- ⏳ **Limited Time** tickets remain valid for a configurable duration.
- ♾️ **Unlimited Time** tickets never expire.

Tickets can use the **Local**, **Semi-Fast**, or **Express** service. Operators can configure each gate to accept any service or require one specific service.

The Blank Ticket, Ticket Blueprint, Transit Ticket, and Ticket Gate use Create-style tooltips. Hold Shift while hovering to see their summaries and usage instructions.

---

## ✨ Current Version Highlights

The current `0.1.1-beta` development version includes:

- 📝 An in-game Ticket Blueprint configuration menu with name, type, service, passage, and duration controls.
- 🎫 Single Use, Multiple Use, Limited Time, and Unlimited Time ticket types.
- 🚉 Local, Semi-Fast, and Express ticket services.
- 🔧 Per-gate service requirements configured by operators with Create's Wrench.
- 📦 Operator-only wrench dismantling that returns the Ticket Gate item.
- 🚶 Gates that close after a player passes through or after a hard five-second timeout.
- 🛡️ Server-validated configuration packets for blueprints and gates.
- 📖 Create-style Shift summaries for every ticket item and the Ticket Gate.

---

## 🎫 Ticket Workflow

> 📄 **Blank Ticket** → 📝 **Ticket Blueprint** → 🎟️ **Transit Ticket** → 🚧 **Ticket Gate**

1. Craft a **Blank Ticket**.
2. Craft a **Ticket Blueprint**.
3. Hold and use the blueprint without a blank ticket to open its configuration menu.
4. Choose its name, ticket type, and service, then select **Done**.
5. Hold the configured blueprint and a blank ticket in opposite hands.
6. Use the blueprint to issue a **Transit Ticket**. One blank ticket is consumed in Survival mode; the blueprint is reusable.
7. Hold the issued ticket and use it on a compatible **Ticket Gate** to enter.

### 📄 Blank Ticket

- The material consumed when a ticket is issued.
- Contains no validity or expiration data by itself.
- Two sheets of paper craft four blank tickets.
- Hold Shift while hovering to display its Create-style summary.

### 📝 Ticket Blueprint

- A reusable template that issues tickets.
- Right-click it without a Blank Ticket in the other hand to open its configuration menu.
- Stores the ticket name, usage type, service, and any required passage or duration value.
- The service selector supports **Local**, **Semi-Fast**, and **Express**.
- **Multiple Use** displays a passage-count field.
- **Limited Time** displays a duration field. Supported suffixes are `t` (ticks), `s` (seconds), `m` (minutes), `h` (hours), and `d` (Minecraft days).
- One second equals 20 ticks, and one Minecraft day equals 24,000 ticks.
- **Single Use** and **Unlimited Time** require no additional value.
- Hold Shift while hovering to display its Create-style summary and usage instructions.

### 🎟️ Transit Ticket

- Uses the name configured on its blueprint.
- Retains the type and service configured on its blueprint.
- A Limited Time ticket stores its issue time, duration, and expiration time using the world's game time.
- Single Use and Multiple Use tickets store their original and remaining passage counts.
- An Unlimited Time ticket does not expire.
- Clearly displays **Valid**, **Expired**, or **Used Up** in the tooltip.
- Hold Shift while hovering to display its Create-style summary.

---

## 💻 Blueprint Commands (Optional)

The in-game blueprint menu is the recommended configuration method. Legacy helper commands remain available to Creative players and command sources with permission level 2 or higher.

> Command-created blueprints use the Local service and cover the legacy limited-time and passage-limited formats. Use the in-game menu for all four ticket types and all service choices.

### ⏳ Time-limited tickets

```text
/transittickets blueprint <duration_ticks> [name]
```

`duration_ticks` must be at least `1`. The optional name may contain spaces and is limited to 64 characters. If no name is supplied, the ticket is named `Transit Ticket`.

For example, a 30-minute ticket lasts 36,000 ticks:

```text
/transittickets blueprint 36000 30 Minute Pass
```

Minecraft normally runs at 20 ticks per second. Ticket time advances with the world's game time and pauses when the world is not running.

### 🔢 Passage-limited tickets

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

## 🚧 Ticket Gate Behavior

- Use the gate while holding an issued, valid **Transit Ticket**.
- Gates accept **Any Service** by default.
- An operator can right-click a placed gate with Create's Wrench to require **Local**, **Semi-Fast**, or **Express** service instead.
- Gate configuration is restricted to operators with permission level 2 or higher.
- An operator can sneak and right-click with Create's Wrench to dismantle the gate and drop its item.
- A valid ticket plays an acceptance sound and opens the center barrier.
- A valid ticket with the wrong service is rejected without consuming a passage.
- Passage-limited tickets lose one passage as soon as the gate accepts them. At zero passages, the ticket becomes **Used Up** and cannot open another gate.
- Limited Time tickets can open gates repeatedly until their world-time expiration. Unlimited Time tickets never expire.
- The gate closes after the player passes through and leaves the gate block.
- The gate always closes after 100 ticks (about five seconds), even if a player remains inside it.
- An empty hand, an unissued ticket, an expired ticket, a used-up ticket, or any other item is rejected with a denial sound and an on-screen error message.
- Hold Shift while hovering over the gate item to display its Create-style summary and wrench instructions.

---

## 🔮 Planned Features

- Create Deployer ticket printing
- Optional compatibility with other Create transit and security add-ons

Planned features may change as development continues.

---

## 🌐 Available Languages

- English (US)

Translations are welcome through pull requests.

---

## 📦 Installation

1. Install **Minecraft 1.20.1**.
2. Install **Minecraft Forge 47.x**. The development environment currently uses Forge **47.4.0**.
3. Install **[Create 0.5.1.f](https://modrinth.com/mod/create/version/HNYrbfZZ)** for Minecraft 1.20.1.
4. Download Create: Transit Tickets from the [GitHub Releases](https://github.com/RSlover521/Create-Transit-Tickets/releases) page.
5. Place both Create and Create: Transit Tickets in the Minecraft `mods` folder.
6. Launch Minecraft with the Forge profile.

> Create 0.5.1.f includes its required Flywheel and Registrate components in the distributed mod jar.

---

## 🛠️ Building from Source

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

## 🔗 Links

- [Repository](https://github.com/RSlover521/Create-Transit-Tickets)
- [Releases](https://github.com/RSlover521/Create-Transit-Tickets/releases)
- [Report an Issue](https://github.com/RSlover521/Create-Transit-Tickets/issues)
- [Discussions](https://github.com/RSlover521/Create-Transit-Tickets/discussions)
- [Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.1.html)
- [Create 0.5.1.f](https://modrinth.com/mod/create/version/HNYrbfZZ)

---

## ✅ Supported Mod Versions

| Version               | Minecraft | Forge  | Create   | Supported |
|-----------------------|-----------|--------|----------|:---------:|
| 0.1.2-beta (current)  | 1.20.1    | 47.x   | 0.5.1+   |    Yes    | 
| 0.1.1-beta            | 1.20.1    | 47.x   | 0.5.1.f  |    Yes    |
| 0.1.0-beta            | 1.20.1    | 47.x   | 0.5.1.f  |    Yes    |

> This project is currently in beta, so features and saved item data may change between releases. Please confirm that you are using a supported version before opening an issue.

---

## 📜 License

- MIT License — see the [project license](https://github.com/RSlover521/Create-Transit-Tickets/blob/main/LICENSE) for details.
- Minecraft, Minecraft Forge, and Create belong to their respective owners.
