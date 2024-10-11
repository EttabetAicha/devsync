package org.aicha.model.enums;

public enum UserType {
    USER("USER"),
    MANAGER("MANAGER");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static UserType fromValue(String value) {
        for (UserType userType : values()) {
            if (userType.value.equalsIgnoreCase(value)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("Unknown user type: " + value);
    }
}
