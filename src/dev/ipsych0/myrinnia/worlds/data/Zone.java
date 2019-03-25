package dev.ipsych0.myrinnia.worlds.data;

public enum Zone {

    PortAzure("Port Azure", "myrinnia.wav"), Myrinnia("Myrinnia", "myrinnia.wav"), SunsetCove("Sunset Cove", "myrinnia.wav"),
    SunriseSands("Sunrise Sands", "myrinnia.wav"), LakeAzure("Lake Azure","myrinnia.wav"), MtAzure("Mt. Azure", "myrinnia.wav"),
    SunshineCoast("Sunshine Coast", "myrinnia.wav");

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
