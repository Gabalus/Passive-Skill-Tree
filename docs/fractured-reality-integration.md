# Fractured Reality Integration Plan

This fork should become the main home for the huge ARPG passive tree and ascendancy layer used by the Fractured Reality / Discords project.

## Correct responsibility split

- `InfiniDungeons`: protected Discord dungeon instances
- `FracturedRealityCore`: Echo Shards, recipe scrolls, Codex, Discord rewards, cross-mod routing
- `Passive Skill Tree`: enormous passive tree, player class identity, ascendancies, long-term build progression

FracturedRealityCore should not duplicate the passive tree.

## First API layer added

Package:

```text
daripher.skilltree.api.fractured
```

Types:

```text
FracturedProgressionSource
FracturedPassivePointAwardEvent
FracturedAscendancyUnlockEvent
```

## Intended usage from FracturedRealityCore

When a Discord is completed:

```java
MinecraftForge.EVENT_BUS.post(new FracturedPassivePointAwardEvent(
    player,
    amount,
    FracturedProgressionSource.DISCORD_CLEAR,
    "Tier " + tier + " Discord completed"
));
```

When a major trial or milestone unlocks an ascendancy:

```java
FracturedAscendancyUnlockEvent event = new FracturedAscendancyUnlockEvent(
    player,
    "void_cartographer",
    FracturedProgressionSource.ASCENDANCY_TRIAL
);
MinecraftForge.EVENT_BUS.post(event);
```

## Next internal wiring needed

The event classes are only the public seam. The next patch must connect them to this mod's actual player skill/point storage.

Required behavior:

1. Listen to `FracturedPassivePointAwardEvent`.
2. Add passive skill points to the target player using Passive Skill Tree's native storage.
3. Listen to `FracturedAscendancyUnlockEvent`.
4. Unlock or mark the requested Fractured Reality ascendancy/class in the native tree system.
5. Expose a safe helper API after the internal storage path is found.

## Fractured Reality ascendancy concepts

Initial ascendancies should be implemented as Passive Skill Tree data/classes, not in FracturedRealityCore:

- `void_cartographer`: Discord maps, reward rooms, rift control
- `blood_reaver`: melee, boss killing, leech/rage-like effects
- `rift_arcanist`: Iron's Spells, spell scrolls, arcane Discords
- `ash_gunslinger`: Scorched Guns, ammo economy, firearm damage
- `relic_warden`: L2Artifacts and defensive artifact scaling
- `echo_monk`: Epic Fight, melee mastery, mobility, perfect-dodge identity

## Passive tree design direction

Use ARPG-inspired structure:

- large connected outer web
- small stat nodes
- medium notables
- build-defining keystones
- separate ascendancy clusters
- Discord modifier/reward utility clusters
- magic/firearm/melee/artifact/loot branches tied to the selected mods

Do not clone another game's tree. Use the selected Minecraft mods as the mechanical foundation.
