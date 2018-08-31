package sync.interop;

import sync.enitity.Id;
import sync.enitity.ServerObjectInfo;

import java.util.List;

public interface ServerObjectInfoDao {
    List<ServerObjectInfo> getServerObjectsInfos();

    void add(ServerObjectInfo serverObjectInfo);

    void remove(Id id);
}
