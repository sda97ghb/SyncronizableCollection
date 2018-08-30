package sync;

import java.util.Arrays;

public class ClientObjectInfo {
    private byte[] id;
    private Long timestamp;
    private SyncFlag syncFlag;

    public ClientObjectInfo() {
        this.id = null;
        this.timestamp = null;
        this.syncFlag = SyncFlag.UNDEFINED;
    }

    public ClientObjectInfo(byte[] id, Long timestamp, SyncFlag syncFlag) {
        this.id = id;
        this.timestamp = timestamp;
        this.syncFlag = syncFlag;
    }

    public byte[] getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public SyncFlag getSyncFlag() {
        return syncFlag;
    }

    @Override
    public String toString() {
        return "ClientObjectInfo{" +
                "id=" + Arrays.toString(id) +
                ", timestamp=" + timestamp +
                ", syncFlag=" + syncFlag +
                '}';
    }
}
