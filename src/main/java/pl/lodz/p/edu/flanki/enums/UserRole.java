package pl.lodz.p.edu.flanki.enums;

public enum UserRole {
    USER("USER");

    private final String name;

    UserRole(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

