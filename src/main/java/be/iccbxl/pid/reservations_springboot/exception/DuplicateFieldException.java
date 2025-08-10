package be.iccbxl.pid.reservations_springboot.exception;

public class DuplicateFieldException extends RuntimeException {

    private final String field;

    public DuplicateFieldException(String field) {
        super("Duplicate value for field: " + field);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
