package be.iccbxl.pid.reservations_springboot.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    MEMBER("member"),
    AFFILIATE("affiliate"),
    PRESS("press"),
    PRODUCER("producer");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

}
