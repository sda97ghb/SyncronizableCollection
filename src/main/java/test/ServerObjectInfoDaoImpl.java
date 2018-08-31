package test;

import sync.aux.TimestampFactory;
import sync.enitity.Id;
import sync.enitity.ServerObjectInfo;
import sync.interop.ServerObjectInfoDao;

import java.util.LinkedList;
import java.util.List;

public class ServerObjectInfoDaoImpl implements ServerObjectInfoDao {
    private List<ServerObjectInfo> serverObjectInfos = new LinkedList<>();

    public ServerObjectInfoDaoImpl() {
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(1), randomTimestamp()));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(2), randomTimestamp()));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(3), randomTimestamp()));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(4), randomTimestamp()));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(5), randomTimestamp()));
    }

    @Override
    public List<ServerObjectInfo> getServerObjectsInfos() {
        return serverObjectInfos;
    }

    @Override
    public void add(ServerObjectInfo serverObjectInfo) {
        serverObjectInfos.add(serverObjectInfo);
    }

    @Override
    public void remove(Id id) {
        for (ServerObjectInfo serverObjectInfo : serverObjectInfos) {
            if (serverObjectInfo.getId().equals(id)) {
                serverObjectInfos.remove(serverObjectInfo);
                return;
            }
        }
    }

    private long randomNumber() {
        return Math.round(Math.random() * 10000.0);
    }

    private long randomTimestamp() {
        return TimestampFactory.getTimestamp() + randomNumber();
    }
}
