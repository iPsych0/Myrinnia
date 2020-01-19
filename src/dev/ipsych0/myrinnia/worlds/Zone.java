package dev.ipsych0.myrinnia.worlds;

public enum Zone {

    PortAzure("Port Azure", "port_azure.ogg"), PortAzureInside("Port Azure", "port_azure.ogg"),
    Myrinnia("Myrinnia", "DUMMY"),
    SunsetCove("Sunset Cove", "sunsetsunrise.ogg"),
    SunriseSands("Sunrise Sands", "sunsetsunrise.ogg"),
    LakeAzure("Lake Azure", "lake_azure.ogg"),
    MtAzure1("Mt. Azure", "lake_azure.ogg"), MtAzure2("Mt. Azure", "lake_azure.ogg"), MtAzure3("Mt. Azure", "lake_azure.ogg"),
    SunshineCoast("Sunshine Coast", "sunshinecoast.ogg"),
    ShamrockHarbour("Shamrock Harbour", "shamrock.ogg"),
    ShamrockTown("Shamrock Town", "shamrock.ogg"), ShamrockTownInside("Shamrock Town", "shamrock.ogg"),
    MalachiteHills("Malachite Hills", "malachite_hills.ogg"),
    MalachiteHideout("Malachite Hideout", "malachite_hideout.ogg"),
    ShamrockMines1("Shamrock Mines B1", "shamrock_mines.ogg"), ShamrockMines2("Shamrock Mines B2", "shamrock_mines.ogg"), ShamrockMines3("Shamrock Mines B3", "shamrock_mines.ogg"),
    ShamrockMinesBasin("Shamrock Mines Basin", "shamrock_mines.ogg"),
    MalachiteFields("Malachite Fields", "malachite_hills.ogg"), MalachiteSlopes("Malachite Slopes", "malachite_hills.ogg"),
    MalachiteInside("Malachite Inside", "malachite_hills.ogg"), MalachitePass("Malachite Pass", "malachite_hills.ogg");

    private String musicFile;
    private String name;

    Zone(String name, String musicFile) {
        this.name = name;
        this.musicFile = musicFile;
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


}
