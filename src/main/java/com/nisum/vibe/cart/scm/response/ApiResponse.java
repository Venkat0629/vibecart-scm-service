package com.nisum.vibe.cart.scm.response;

/**
 * Generic class for API response structure.
 * <p>
 * Encapsulates the success status, HTTP status code, message, and optional data of the response.
 * This class provides a standardized way to return API responses across the application.
 * </p>
 *
 * @param <T> The type of data included in the response. This can be any type depending on the API response.
 */
public class ApiResponse<T> {

    private boolean success;
    private int statusCode;
    private String message;
    private T data;

    /**
     * Default constructor for creating an empty instance of {@code ApiResponse}.
     */
    public ApiResponse() {
    }

    /**
     * Constructs a new {@code ApiResponse} with the specified details.
     *
     * @param success Indicates whether the operation was successful.
     * @param statusCode The HTTP status code to be included in the response.
     * @param message A message providing additional information about the response.
     * @param data The data to be included in the response, which can be of any type {@code T}.
     */
    public ApiResponse(boolean success, int statusCode, String message, T data) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    /**
     * Constructs a new {@code ApiResponse} with the specified details but without data.
     *
     * @param success Indicates whether the operation was successful.
     * @param statusCode The HTTP status code to be included in the response.
     * @param message A message providing additional information about the response.
     */
    public ApiResponse(boolean success, int statusCode, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * Returns whether the operation was successful.
     *
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets whether the operation was successful.
     *
     * @param success {@code true} if the operation was successful, {@code false} otherwise.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the HTTP status code of the response.
     *
     * @return The HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the HTTP status code of the response.
     *
     * @param statusCode The HTTP status code to be set.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns the message providing additional information about the response.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message providing additional information about the response.
     *
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the data included in the response.
     *
     * @return The data of type {@code T}.
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data to be included in the response.
     *
     * @param data The data of type {@code T} to be set.
     */
    public void setData(T data) {
        this.data = data;
    }
}
