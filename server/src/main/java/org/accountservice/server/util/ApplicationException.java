package org.accountservice.server.util;

/**
 * @author Pavel Karpukhin
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }
}
