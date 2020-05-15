package dev.ipsych0.myrinnia.worlds;

public enum Zone {

    PortAzure("Port Azure", "port_azure.ogg", "/worlds/port_azure.tmx"),
    Myrinnia("Myrinnia", "DUMMY", "/worlds/myrinnia_DUMMY_MAP.tmx"),
    LakeAzure("Lake Azure", "lake_azure.ogg", "/worlds/lake_azure.tmx"),
    SunriseSands("Sunrise Sands", "sunshinecoast.ogg", "/worlds/sunrise_sands.tmx"),
    SunsetCove("Sunset Cove", "sunshinecoast.ogg", "/worlds/sunset_cove.tmx"),
    SunshineCoast("Sunshine Coast", "sunshinecoast.ogg", "/worlds/sunshine_coast.tmx"),
    PortAzureInside("Port Azure", "port_azure.ogg", "/worlds/port_azure_inside.tmx"),
    MtAzure1("Mt. Azure", "lake_azure.ogg", "/worlds/mt_azure1.tmx"),
    MtAzure2("Mt. Azure", "lake_azure.ogg", "/worlds/mt_azure2.tmx"),
    MtAzure3("Mt. Azure", "lake_azure.ogg", "/worlds/mt_azure3.tmx"),

    ShamrockHarbour("Shamrock Harbour", "shamrock.ogg", "/worlds/shamrock_harbour.tmx"),
    ShamrockTown("Shamrock Town", "shamrock.ogg", "/worlds/shamrock_town.tmx"),
    ShamrockTownInside("Shamrock Town", "shamrock.ogg", "/worlds/shamrock_town_inside.tmx"),
    ShamrockMines1("Shamrock Mines B1", "shamrock_mines.ogg", "/worlds/shamrock_mines1.tmx"),
    ShamrockMines2("Shamrock Mines B2", "shamrock_mines.ogg", "/worlds/shamrock_mines2.tmx"),
    ShamrockMines3("Shamrock Mines B3", "shamrock_mines.ogg", "/worlds/shamrock_mines3.tmx"),
    ShamrockMinesBasin("Shamrock Mines Basin", "shamrock_mines.ogg", "/worlds/shamrock_mines4.tmx"),
    ShamrockCliffs("Shamrock Cliffs", "celenor_coast.ogg", "/worlds/shamrock_cliffs.tmx"),

    MalachiteHills("Malachite Hills", "malachite_hills.ogg", "/worlds/malachite_hills.tmx"),
    MalachiteHideout("Malachite Hideout", "malachite_hideout.ogg", "/worlds/malachite_hideout.tmx"),
    MalachiteFields("Malachite Fields", "malachite_hills.ogg", "/worlds/malachite_fields.tmx"),
    MalachiteInside("Malachite Inside", "malachite_hills.ogg", "/worlds/malachite_inside.tmx"),
    MalachiteSlopes("Malachite Slopes", "malachite_hills.ogg", "/worlds/malachite_slopes.tmx"),
    MalachitePass("Malachite Pass", "malachite_hills.ogg", "/worlds/malachite_pass.tmx"),
    MalachiteOverpass("Malachite Overpass", "malachite_hills.ogg", "/worlds/malachite_overpass.tmx"),

    CelenorForestEdge("Celenor Forest Edge", "celenor_forest_edge.ogg", "/worlds/celenor_forest_edge.tmx"),
    CelenorForestThicket("Celenor Forest Thicket", "celenor_forest_edge.ogg", "/worlds/celenor_forest_thicket.tmx"),
    CelenorForestShrine("Old Shrine", "celenor_forest_edge.ogg", "/worlds/celenor_shrine.tmx"),
    Celewynn("Celewynn", "celewynn.ogg", "/worlds/celewynn.tmx"),
    CelewynnInside("Celewynn", "celewynn.ogg", "/worlds/celewynn_inside.tmx"),
    CelenorSeaboard("Celenor Seaboard", "celenor_coast.ogg", "/worlds/celenor_seaboard.tmx"),
    CelenorCaves("Celenor Caves", "celenor_forest_edge.ogg", "/worlds/celenor_caves.tmx"),
    AemirRiverflank("Aemir Riverflank", "celenor_forest_edge.ogg", "/worlds/aemir_riverflank.tmx"),
    WardensCabin("Warden's Cabin", "wardens_cabin.ogg", "/worlds/wardens_cabin.tmx"),

    FarnorRiverMeander("Farnor River Meander", "malachite_hills.ogg", "/worlds/farnor_river_meander.tmx");


    private String musicFile;
    private String name;
    private String path;

    Zone(String name, String musicFile, String path) {
        this.name = name;
        this.musicFile = musicFile;
        this.path = path;
    }

    public String getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(String musicFile) {
        this.musicFile = musicFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
