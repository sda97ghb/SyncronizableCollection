package sync;

import java.util.List;

public interface ClientObjectInfoDao {
    List<ClientObjectInfo> getClientObjectInfos();

    void add(ClientObjectInfo clientObjectInfo);

    void remove(byte[] id);
}
