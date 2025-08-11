package be.iccbxl.pid.reservations_springboot.exception;

import lombok.Getter;

@Getter
public class DuplicateFieldException extends RuntimeException {

    private final String field;


    public DuplicateFieldException(String field) {
        super("Duplicate value for field: " + field );
        this.field = field;
    }

    public DuplicateFieldException(String field, String message) {
        super(message);
        this.field = field;
    }

}
