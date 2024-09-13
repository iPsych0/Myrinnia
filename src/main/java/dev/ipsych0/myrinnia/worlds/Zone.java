package dev.ipsych0.myrinnia.worlds;

import lombok.Getter;

@Getter
public enum Zone {

    PortAzure("Port Azure", "port_azure.ogg", "./res/worlds/port_azure.tmx"),
    Myrinnia("Myrinnia", "DUMMY", "./res/worlds/myrinnia_DUMMY_MAP.tmx"),
    LakeAzure("Lake Azure", "lake_azure.ogg", "./res/worlds/lake_azure.tmx"),
    SunriseSands("Sunrise Sands", "sunshinecoast.ogg", "./res/worlds/sunrise_sands.tmx"),
    SunsetCove("Sunset Cove", "sunshinecoast.ogg", "./res/worlds/sunset_cove.tmx"),
    SunshineCoast("Sunshine Coast", "sunshinecoast.ogg", "./res/worlds/sunshine_coast.tmx"),
    PortAzureInside("Port Azure", "port_azure.ogg", "./res/worlds/port_azure_inside.tmx"),
    MtAzure1("Mt. Azure", "lake_azure.ogg", "./res/worlds/mt_azure1.tmx"),
    MtAzure2("Mt. Azure", "lake_azure.ogg", "./res/worlds/mt_azure2.tmx"),
    MtAzure3("Mt. Azure", "lake_azure.ogg", "./res/worlds/mt_azure3.tmx"),

    ShamrockHarbour("Shamrock Harbour", "shamrock.ogg", "./res/worlds/shamrock_harbour.tmx"),
    ShamrockTown("Shamrock Town", "shamrock.ogg", "./res/worlds/shamrock_town.tmx"),
    ShamrockTownInside("Shamrock Town", "shamrock.ogg", "./res/worlds/shamrock_town_inside.tmx"),
    ShamrockMines1("Shamrock Mines B1", "shamrock_mines.ogg", "./res/worlds/shamrock_mines1.tmx"),
    ShamrockMines2("Shamrock Mines B2", "shamrock_mines.ogg", "./res/worlds/shamrock_mines2.tmx"),
    ShamrockMines3("Shamrock Mines B3", "shamrock_mines.ogg", "./res/worlds/shamrock_mines3.tmx"),
    ShamrockMinesBasin("Shamrock Mines Basin", "shamrock_mines.ogg", "./res/worlds/shamrock_mines4.tmx"),
    ShamrockCliffs("Shamrock Cliffs", "celenor_coast.ogg", "./res/worlds/shamrock_cliffs.tmx"),
    ShamrockWolfDen("Wolf Den", "celenor_coast.ogg", "./res/worlds/wolfs_den.tmx"),

    MalachiteHills("Malachite Hills", "malachite_hills.ogg", "./res/worlds/malachite_hills.tmx"),
    MalachiteHideout("Malachite Hideout", "malachite_hideout.ogg", "./res/worlds/malachite_hideout.tmx"),
    MalachiteFields("Malachite Fields", "malachite_hills.ogg", "./res/worlds/malachite_fields.tmx"),
    MalachiteInside("Malachite Inside", "malachite_hills.ogg", "./res/worlds/malachite_inside.tmx"),
    MalachiteSlopes("Malachite Slopes", "malachite_hills.ogg", "./res/worlds/malachite_slopes.tmx"),
    MalachitePass("Malachite Pass", "malachite_hills.ogg", "./res/worlds/malachite_pass.tmx"),
    MalachiteOverpass("Malachite Overpass", "malachite_hills.ogg", "./res/worlds/malachite_overpass.tmx"),

    CelenorForestEdge("Celenor Forest Edge", "celenor_forest_edge.ogg", "./res/worlds/celenor_forest_edge.tmx"),
    CelenorForestThicket("Celenor Forest Thicket", "celenor_forest_edge.ogg", "./res/worlds/celenor_forest_thicket.tmx"),
    CelenorForestShrine("Old Shrine", "celenor_forest_edge.ogg", "./res/worlds/celenor_shrine.tmx"),
    Celewynn("Celewynn", "celewynn.ogg", "./res/worlds/celewynn_polluted.tmx"),
    CelewynnInside("Celewynn", "celewynn.ogg", "./res/worlds/celewynn_inside.tmx"),
    CelenorSeaboard("Celenor Seaboard", "celenor_coast.ogg", "./res/worlds/celenor_seaboard.tmx"),
    CelenorCaves("Celenor Caves", "celenor_forest_edge.ogg", "./res/worlds/celenor_caves_polluted.tmx"),
    AemirRiverflank("Aemir Riverflank", "celenor_forest_edge.ogg", "./res/worlds/aemir_riverflank_polluted.tmx"),
    WardensCabin("Warden's Cabin", "wardens_cabin.ogg", "./res/worlds/wardens_cabin.tmx"),

    CelenorForestCreek("Celenor Forest Creek", "celenor_forest_edge.ogg", "./res/worlds/celenor_forest_creek.tmx"),
    CelenorForestBog("Celenor Forest Bog", "celenor_forest_edge.ogg", "./res/worlds/celenor_forest_bog.tmx"),
    CelenorForestMeadows("Celenor Forest Meadows", "celenor_forest_edge.ogg", "./res/worlds/celenor_forest_meadows.tmx"),
    CelenorForestLandslide("Celenor Forest Landslide", "celenor_forest_edge.ogg", "./res/worlds/celenor_forest_landslide.tmx"),
    Fyddnymed("Fyddnymed", "celenor_forest_edge.ogg", "./res/worlds/fyddnymed.tmx"),
    FyddnymedCanopy("Fyddnymed Canopy", "celenor_forest_edge.ogg", "./res/worlds/fyddnymed_canopy.tmx"),

    StozarsDescent("Stozar's Descent", "malachite_hills.ogg", "./res/worlds/stozars_descent.tmx"),
    StozarsCauseway("Stozar's Causeway", "malachite_hills.ogg", "./res/worlds/stozars_causeway.tmx"),
    StozarInside("Stozar Inside", "malachite_hills.ogg", "./res/worlds/stozar_inside.tmx"),
    StozarsQuarry("Stozar's Quarry", "malachite_hills.ogg", "./res/worlds/stozars_quarry.tmx"),
    BonebladeBadlands("Boneblade Badlands", "malachite_hills.ogg", "./res/worlds/boneblade_badlands.tmx"),
    LakeClayfall("Lake Clayfall", "malachite_hills.ogg", "./res/worlds/lake_clayfall.tmx"),
    RuinsOfTheForgotten("Ruins of the Forgotten", "malachite_hills.ogg", "./res/worlds/ruins_of_the_forgotten.tmx"),
    ForgottenShore("The Forgotten Shore", "malachite_hills.ogg", "./res/worlds/forgotten_shore.tmx"),
    ForgottenCave("The Forgotten Cave", "malachite_hills.ogg", "./res/worlds/forgotten_shore_cave.tmx"),
    RedrockOutpost("Redrock Outpost", "malachite_hills.ogg", "./res/worlds/redrock_outpost.tmx"),
    RedrockInside("Redrock Outpost", "malachite_hills.ogg", "./res/worlds/redrock_inside.tmx");


    private final String musicFile;
    private final String name;
    private final String path;

    Zone(String name, String musicFile, String path) {
        this.name = name;
        this.musicFile = musicFile;
        this.path = path;
    }

}
