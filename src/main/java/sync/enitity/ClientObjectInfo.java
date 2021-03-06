package sync.enitity;

public class ClientObjectInfo {
    private Id id;
    private Long timestamp;
    private SyncFlag syncFlag;

    public ClientObjectInfo() {
        this.id = null;
        this.timestamp = null;
        this.syncFlag = SyncFlag.UNDEFINED;
    }

    public ClientObjectInfo(Id id, Long timestamp, SyncFlag syncFlag) {
        this.id = id;
        this.timestamp = timestamp;
        this.syncFlag = syncFlag;
    }

    public Id getId() {
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
                "id=" + id +
                ", timestamp=" + timestamp +
                ", syncFlag=" + syncFlag +
                '}';
    }
}
