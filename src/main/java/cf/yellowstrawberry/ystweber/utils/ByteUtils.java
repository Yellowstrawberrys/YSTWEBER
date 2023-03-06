package cf.yellowstrawberry.ystweber.utils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class ByteUtils {
    public static byte[] getIntegerAsByteArray(int length, int integer) {
        byte[] n = ByteBuffer.allocate(4).putInt(integer).array();
        byte[] r = new byte[length];
        if(n.length < length) throw new IllegalArgumentException("Length needs to be lower than 4");
        System.arraycopy(n, n.length-length, r, 0, length);
        return r;
    }
    public static byte[] joinArrays(byte[]... arrays) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for(byte[] b : arrays) bos.writeBytes(b.clone());
        return bos.toByteArray();
    }
}
