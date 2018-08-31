package sync.enitity;

public class ServerObjectInfo {
    private Id id;
    private Long timestamp;

    public ServerObjectInfo() {
        this.id = null;
        this.timestamp = null;
    }

    public ServerObjectInfo(Id id, Long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Id getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ServerObjectInfo{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }
}
