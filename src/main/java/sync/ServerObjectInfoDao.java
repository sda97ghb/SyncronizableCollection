package sync;

import java.util.List;

public interface ServerObjectInfoDao {
    List<ServerObjectInfo> getServerObjectsInfos();

    void add(ServerObjectInfo serverObjectInfo);

    void remove(byte[] id);
}
