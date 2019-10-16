package pl.lodz.p.edu.flanki.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

