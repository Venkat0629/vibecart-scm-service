package com.nisum.vibe.cart.scm.model;

import java.util.UUID;

/**
 * Utility class for generating UUIDs (Universally Unique Identifiers).
 * <p>
 * This class provides a static method to generate a UUID and format it according to specific requirements:
 * <ul>
 *     <li>Generates a UUID using the `UUID.randomUUID()` method.</li>
 *     <li>Truncates the UUID to the first 12 characters.</li>
 *     <li>Converts the UUID string to uppercase.</li>
 *     <li>Replaces all hyphens ('-') in the UUID string with the character 'A'.</li>
 * </ul>
 * </p>
 */
public class UUIDGenerator {

    /**
     * Generates a formatted UUID.
     * <p>
     * This method performs the following operations on the UUID:
     * <ul>
     *     <li>Generates a new UUID using `UUID.randomUUID()`.</li>
     *     <li>Truncates the UUID to the first 12 characters.</li>
     *     <li>Converts the truncated UUID to uppercase.</li>
     *     <li>Replaces all hyphens ('-') in the UUID string with the character 'A'.</li>
     * </ul>
     * </p>
     *
     * @return a formatted UUID string
     */
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString().substring(0, 12).toUpperCase().replaceAll("-", "A"); // Return the UUID as a string
    }
}
