<h1 align="center">Create: Transit Tickets</h1>

<p align="center">
  <img width="256" height="256" alt="Create: Transit Tickets mod icon" src="src/main/resources/create_transit_tickets.png">
</p>

<p align="center">
  Survival-friendly, configurable transit tickets for Create train networks.
</p>

<p align="center">
  <a href="https://github.com/RSlover521/Create-Transit-Tickets">
    <img alt="GitHub Repository" src="https://img.shields.io/badge/GitHub-Repository-181717?style=for-the-badge&logo=github&color=darkcyan">
  </a>
  <a href="https://github.com/RSlover521/Create-Transit-Tickets/releases">
    <img alt="Latest Release" src="https://img.shields.io/github/v/release/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=lime">
  </a>
  <a href="https://github.com/RSlover521/Create-Transit-Tickets/blob/main/LICENSE">
    <img alt="License" src="https://img.shields.io/github/license/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=red">
  </a>
  <a href="https://github.com/RSlover521/Create-Transit-Tickets/discussions">
    <img alt="Discussions" src="https://img.shields.io/github/discussions/RSlover521/Create-Transit-Tickets?style=for-the-badge&color=blue&logo=github">
  </a>
  <a href="https://github.com/RSlover521/Create-Transit-Tickets/issues">
    <img alt="Open Issues" src="https://img.shields.io/github/issues/RSlover521/Create-Transit-Tickets?style=for-the-badge&logo=github&color=limegreen">
  </a>
  <a href="https://github.com/RSlover521/Create-Transit-Tickets/releases">
    <img alt="Total Downloads" src="https://img.shields.io/github/downloads/RSlover521/Create-Transit-Tickets/total?style=for-the-badge&logo=github&color=white">
  </a>
</p>

---

## 🌟 Create: Transit Tickets

Create: Transit Tickets adds expiring tickets and reusable ticket blueprints for survival-friendly public transportation systems built with the **Create** mod.

Craft blank tickets, configure a reusable blueprint, and issue tickets whose validity is measured using the world's game time. Tickets clearly show whether they are valid or expired, along with their remaining duration.

The current release focuses on a small, dependable ticket foundation. Validators, gates, routes, zones, fares, and other larger transit systems are planned for future development rather than bundled into the first version.

---

## 📦 Overview

**Create: Transit Tickets provides configurable, time-limited passes for Minecraft Forge 1.20.1 and Create-powered transit networks.**

The current ticket workflow is:

1. Craft a **Blank Ticket**.
2. Obtain or configure a reusable **Ticket Blueprint**.
3. Hold the blueprint and a blank ticket in opposite hands.
4. Use the blueprint to consume one blank ticket and issue a **Transit Ticket**.
5. Check the ticket tooltip to see its validity and remaining time.

---

## 🎫 Mod Feature Details

### Blank Ticket

- A simple survival-craftable ticket material.
- Contains no validity or expiration data.
- Consumed when a configured Transit Ticket is issued.

### Ticket Blueprint

- A reusable template for issuing tickets.
- Stores the ticket name and duration in item NBT.
- Supports future route and zone metadata.
- Remains in the player's inventory after printing a ticket.

### Transit Ticket

- Stores its issued time, duration, and expiration time.
- Uses Minecraft world game time rather than real-world time.
- Displays **Valid**, **Expired**, and remaining-time information in its tooltip.
- Uses the configured ticket name from its blueprint.

### Blueprint Helper Command

Creative players and command-level operators can generate configured blueprints with:

```text
/transittickets blueprint <duration_ticks> [name]
```

For example, a 30-minute ticket lasts 36,000 ticks:

```text
/transittickets blueprint 36000 30 Minute Pass
```

> Minecraft normally runs at 20 ticks per second. Ticket time advances with the world's game time and pauses when the world is not running.

---

## 🚧 Planned Features

- Create Deployer ticket printing
- Ticket validator and ticket gate blocks
- Route-specific and zone-based tickets
- Multi-use tickets
- Configurable default ticket types and allowed durations
- Optional compatibility with other Create transit and security add-ons

These features are not part of the current MVP and may change as development continues.

---

## 🌎 Available Languages

- English (US)

Translations are welcome through pull requests.

---

## 📝 License

- MIT License — See [`LICENSE`](LICENSE) for details.
- Minecraft, Minecraft Forge, and Create belong to their respective owners.

---

## 💾 Installation

1. Install **Minecraft 1.20.1**.
2. Install **Minecraft Forge 47.x**. The development environment currently uses Forge **47.4.0**.
3. Install **[Create 0.5.1.f](https://modrinth.com/mod/create/version/HNYrbfZZ)** for Minecraft 1.20.1.
4. Download Create: Transit Tickets from the [GitHub Releases](https://github.com/RSlover521/Create-Transit-Tickets/releases) page, when a release is available.
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

## 🔗 Mod Links

### GitHub

- [Repository](https://github.com/RSlover521/Create-Transit-Tickets)
- [Releases](https://github.com/RSlover521/Create-Transit-Tickets/releases)
- [Report an Issue](https://github.com/RSlover521/Create-Transit-Tickets/issues)
- [Discussions](https://github.com/RSlover521/Create-Transit-Tickets/discussions)

### Dependencies

- [Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.1.html)
- [Create 0.5.1.f](https://modrinth.com/mod/create/version/HNYrbfZZ)

---

## 🌟 Supported Mod Versions

| Version | Minecraft | Forge | Create | Supported |
|---|---|---|---|:---:|
| 0.1.0-beta | 1.20.1 | 47.x | 0.5.1.f | ✅ |

**Note: Please confirm that you are using a supported version before opening an issue. This project is currently in beta, so features and saved item data may change between releases.**

---
