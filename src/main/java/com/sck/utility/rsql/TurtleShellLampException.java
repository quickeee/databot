package com.sck.utility.rsql;



/**
 * This exception is throw in case of a not activated user trying to authenticate.
 */
public class TurtleShellLampException extends Exception {

    public TurtleShellLampException(String message) {
        super(message);
    }

    public TurtleShellLampException(String message, Throwable t) {
        super(message, t);
    }
}
