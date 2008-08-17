package dev.di.data.service;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Nov 3, 2007
 * Time: 6:21:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DuplicateDataException extends Exception {
    public DuplicateDataException(String string) {
        super(string);
    }

    public DuplicateDataException() {
        super();
    }
}
