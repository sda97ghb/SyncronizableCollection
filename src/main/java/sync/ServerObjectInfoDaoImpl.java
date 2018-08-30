package sync;

import java.util.LinkedList;
import java.util.List;

public class ServerObjectInfoDaoImpl implements ServerObjectInfoDao {
    @Override
    public List<ServerObjectInfo> getServerObjectsInfos() {
        return new LinkedList<>();
    }

    @Override
    public void add(ServerObjectInfo serverObjectInfo) {
        ;
    }

    @Override
    public void remove(byte[] id) {
        ;
    }
}
