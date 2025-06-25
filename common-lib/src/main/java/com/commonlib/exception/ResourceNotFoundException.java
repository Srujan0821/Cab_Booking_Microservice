package com.commonlib.exception;


public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND");
    }
}
