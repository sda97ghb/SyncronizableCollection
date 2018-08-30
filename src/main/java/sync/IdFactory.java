package sync;

import java.nio.ByteBuffer;

public class IdFactory {
    static short lastEmittedId = 0;

    public static byte[] getId() {
        ++ lastEmittedId;
        return shortToBytes(lastEmittedId);
    }

    private static byte[] shortToBytes(short x) {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.putShort(x);
        return buffer.array();
    }
}
