package com.mobiquityinc.exception;

/**
 * This class indicates the API exception has occured while solving the packing problem.
 */
public class APIException extends Exception {

    public APIException(String message, Exception e) {
        super(message, e);
    }

    public APIException(String message) {
        super(message);
    }
}
