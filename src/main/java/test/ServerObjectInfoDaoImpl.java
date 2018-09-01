package test;

import sync.aux.TimestampFactory;
import sync.enitity.Id;
import sync.enitity.ServerObjectInfo;
import sync.interop.ServerObjectInfoDao;

import java.util.LinkedList;
import java.util.List;

public class ServerObjectInfoDaoImpl implements ServerObjectInfoDao {
    private List<ServerObjectInfo> serverObjectInfos = new LinkedList<>();

    ServerObjectInfoDaoImpl() {
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(1), 1L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(2), 2L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(3), 3L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(4), 4L));

        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(5), 5000L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(6), 6000L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(7), 7000L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(8), 8000L));

        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(9), 500L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(10), 600L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(11), 700L));
        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(12), 800L));

        serverObjectInfos.add(new ServerObjectInfo(Id.fromLong(17), 50000L));
    }

    @Override
    public List<ServerObjectInfo> getServerObjectsInfos() {
        return serverObjectInfos;
    }

    @Override
    public void add(ServerObjectInfo serverObjectInfo) {
        System.out.println("Add server info " + serverObjectInfo);
        serverObjectInfos.add(serverObjectInfo);
    }

    @Override
    public void update(ServerObjectInfo serverObjectInfo) {
        System.out.println("Update server info " + serverObjectInfo);
        for (ServerObjectInfo serverObjectInfo2 : serverObjectInfos) {
            if (serverObjectInfo2.getId().equals(serverObjectInfo.getId())) {
                serverObjectInfos.remove(serverObjectInfo2);
                serverObjectInfos.add(serverObjectInfo);
                return;
            }
        }
    }

    @Override
    public void remove(Id id) {
        System.out.println("Remove server info " + id);
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
