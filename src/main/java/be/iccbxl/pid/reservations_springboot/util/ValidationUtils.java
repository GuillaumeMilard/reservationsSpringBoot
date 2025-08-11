package be.iccbxl.pid.reservations_springboot.util;

public final class ValidationUtils {

    private ValidationUtils() {
        // Prevent instantiation
    }

    public static void requireNonNull(Object obj, String message) {
        if (obj == null) throw new IllegalArgumentException(message);
    }

    public static void requireNonEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(message);
    }

    public static void requireLengthBetween(String value, int min, int max, String message) {
        if (value == null || value.trim().isEmpty() || value.length() < min || value.length() > max) {
            throw new IllegalArgumentException(message);
        }
    }

}
