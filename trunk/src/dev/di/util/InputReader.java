package dev.di.util;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Apr 16, 2007
 * Time: 5:08:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class InputReader {
    public static String readStream(DataInputStream in) throws IOException {
        int length = 0;
        StringBuffer buffer = new StringBuffer();
        byte[] data;
        if (length > 0) {
            data = new byte[length];
            in.readFully(data);
            buffer.append(getCharArrayFromByteArray(data));
        } else {
            int ch;
            while ((ch = in.read()) != -1) {
                buffer.append((char) ch);
            }
        }
        String reply = buffer.toString();

        return reply;
    }

    private static char[] getCharArrayFromByteArray(byte[] source) {
        char[] target = new char[source.length];
        for (int i = 0; i < source.length; i++) {
            target[i] = (char) source[i];
        }
        return target;
    }
}
