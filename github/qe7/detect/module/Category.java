package github.qe7.detect.module;

public enum Category {

    COMBAT("Combat"),
    MOVEMENT("Movement"),
    VISUAL("Visuals"),
    PLAYER("Player"),
    EXPLOIT("Exploit");

    public String name;

    Category(String name) {
        this.name = name;
    }

}
