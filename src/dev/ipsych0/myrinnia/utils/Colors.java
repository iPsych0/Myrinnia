package dev.ipsych0.myrinnia.utils;

import java.awt.*;

public class Colors {
    // UI
    public static Color selectedColor = new Color(0, 255, 255, 62);
    public static Color insufficientAmountColor = new Color(255, 0, 0, 62);
    public static Color progressBarColor = new Color(0, 255, 255, 93);
    public static Color progressBarOutlineColor = new Color(0, 255, 255, 127);

    // A*
    public static Color pathFindingColor = new Color(44, 255, 12, 127);
    public static Color unwalkableColour = new Color(255, 0, 0, 127);

    // Abilities
    public static Color abilityUnlockedColor = new Color(106, 105, 105, 96);
    public static Color cooldownColor = new Color(24, 24, 24, 192);
    public static Color blockedColor = new Color(224, 0, 0, 160);
    public static Color lockedColor = new Color(192, 8, 0, 192),
            unlockedColor = new Color(8, 148, 0, 192),
            hoverLockedColor = new Color(250, 8, 0, 192),
            hoverUnlockedColor = new Color(8, 192, 0, 192);

    // Recap
    public static Color recapBackgroundColor = new Color(27, 27, 27, 196);

    // HP UI
    public static Color hpColorRed = new Color(148, 8, 0);
    public static Color hpColorGreen = new Color(58, 143, 0);
    public static Color xpColor = new Color(0, 255, 255, 62);
    public static Color hpColorRedOutline = new Color(198, 8, 0);
    public static Color hpColorGreenOutline = new Color(58, 188, 0);
    public static Color xpColorOutline = new Color(0, 255, 255, 93);

    // Bounty
    public static Color completedColor = new Color(44, 255, 12, 62);
    public static Color acceptedColor = new Color(196, 204, 17, 62);
}
