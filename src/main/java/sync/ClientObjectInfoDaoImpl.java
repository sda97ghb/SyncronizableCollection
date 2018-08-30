package sync;

import java.util.LinkedList;
import java.util.List;

public class ClientObjectInfoDaoImpl implements ClientObjectInfoDao {
    @Override
    public List<ClientObjectInfo> getClientObjectInfos() {
        return new LinkedList<>();
    }

    @Override
    public void add(ClientObjectInfo clientObjectInfo) {
        ;
    }

    @Override
    public void remove(byte[] id) {
        ;
    }
}
