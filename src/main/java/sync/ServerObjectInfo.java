package sync;

import java.util.Arrays;

public class ServerObjectInfo {
    private byte[] id;
    private Long timestamp;

    public ServerObjectInfo() {
        this.id = null;
        this.timestamp = null;
    }

    public ServerObjectInfo(byte[] id, Long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public byte[] getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ServerObjectInfo{" +
                "id=" + Arrays.toString(id) +
                ", timestamp=" + timestamp +
                '}';
    }
}
