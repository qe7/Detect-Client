package github.qe7.detect.module;

public enum Category {

    COMBAT("Combat"),
    PLAYER("Player"),
    MOVEMENT("Movement"),
    VISUAL("Visuals"),
    EXPLOIT("Exploit"),
    CLIENT("Client");

    public String name;

    Category(String name) {
        this.name = name;
    }

}
