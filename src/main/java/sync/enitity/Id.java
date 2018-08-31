package sync.enitity;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Id {
    private byte[] data;

    public Id() {
        data = null;
    }

    public Id(byte[] data) {
        this.data = data;
    }

    public static Id fromString(String str) {
        return new Id(str.getBytes());
    }

    public static Id fromLong(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        return new Id(buffer.array());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return Arrays.equals(data, id.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "Id{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
