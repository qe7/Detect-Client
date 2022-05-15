package github.qe7.detect.module;

public enum Category {

    COMBAT("Combat"),
    MOVEMENT("Movement"),

    PLAYER("Player"),
    MISC("Misc"),
    VISUAL("Visuals"),
    EXPLOIT("Exploit");

    public String name;

    Category(String name) {
        this.name = name;
    }

}
