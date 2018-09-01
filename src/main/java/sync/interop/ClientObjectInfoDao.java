package sync.interop;

import sync.enitity.ClientObjectInfo;
import sync.enitity.Id;

import java.util.List;

public interface ClientObjectInfoDao {
    List<ClientObjectInfo> getClientObjectInfos();

    void add(ClientObjectInfo clientObjectInfo);

    void update(ClientObjectInfo clientObjectInfo);

    void remove(Id id);
}
