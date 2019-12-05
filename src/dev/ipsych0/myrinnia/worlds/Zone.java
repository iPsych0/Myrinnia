package dev.ipsych0.myrinnia.worlds;

public enum Zone {

    PortAzure("Port Azure", "port_azure.wav"), Myrinnia("Myrinnia", "DUMMY"), SunsetCove("Sunset Cove", "sunsetsunrise.wav"),
    SunriseSands("Sunrise Sands", "sunsetsunrise.wav"), LakeAzure("Lake Azure", "lake_azure.wav"), MtAzure1("Mt. Azure", "lake_azure.wav"),
    MtAzure2("Mt. Azure", "lake_azure.wav"), MtAzure3("Mt. Azure", "lake_azure.wav"), SunshineCoast("Sunshine Coast", "sunshinecoast.wav"),
    PortAzureInside("Port Azure", "port_azure.wav"), ShamrockHarbour("Shamrock Harbour", "port_azure.wav"), ShamrockTown("Shamrock Town", "port_azure.wav"),
    ShamrockTownInside("Shamrock Town", "port_azure.wav");

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
