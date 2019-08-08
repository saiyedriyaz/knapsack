package com.mobiquityinc.exception;

import lombok.Getter;

import java.util.List;

/**
 * This class indicates the validation exception has occured while solving the packing problem.
 */
public class ValidationException extends APIException {

    @Getter
    private List<String> validationMessages;

    public ValidationException(String summary, List<String> validationMessages) {
        super(summary);
        this.validationMessages = validationMessages;
    }
}
