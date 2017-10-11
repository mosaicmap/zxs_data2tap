/*
 * InvalidDataException.java
 *
 *  created: 8.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;


/**
 *
 * @author Martin Pokorný
 */
public class InvalidDataException extends Exception {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

}   // InvalidDataException.java
