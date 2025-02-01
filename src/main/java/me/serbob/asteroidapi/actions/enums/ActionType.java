package me.serbob.asteroidapi.actions.enums;

/*
 * If you need more, contact @Serbob
 */
public enum ActionType {
    // Basic behaviors
    IDLING,
    WANDERING,
    FOLLOWING,

    // Movement related
    LOCATION,
    PATHFINDING,
    JUMPING,
    SWIMMING,
    FLYING,

    // Resource related
    GATHERING,
    MINING,
    FARMING,
    FISHING,

    // Construction
    BUILDING,
    PLACING,
    BREAKING,

    // Combat
    FIGHTING,
    ATTACKING,
    DEFENDING,
    FLEEING,

    // Interaction
    SEARCHING,
    INTERACTION,
    TRADING,
    CRAFTING,

    // Inventory
    LOOTING,
    SORTING,
    DROPPING,

    // Utility
    EATING,
    HEALING,
    SLEEPING,
    CHATTING,

    // Special
    CUSTOM,     // For custom actions
    COMPLEX     // For actions that combine multiple types
}
