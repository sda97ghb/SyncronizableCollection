package test;

import sync.aux.TimestampFactory;
import sync.enitity.ClientObjectInfo;
import sync.enitity.Id;
import sync.enitity.SyncFlag;
import sync.interop.ClientObjectInfoDao;

import java.util.LinkedList;
import java.util.List;

public class ClientObjectInfoDaoImpl implements ClientObjectInfoDao {
    private List<ClientObjectInfo> clientObjectInfos = new LinkedList<>();

    ClientObjectInfoDaoImpl() {
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(1), 1000L, SyncFlag.UNDEFINED));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(2), 2000L, SyncFlag.NO_SYNC));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(3), 3000L, SyncFlag.ADD));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(4), 4000L, SyncFlag.DELETE));

        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(5), 5L, SyncFlag.UNDEFINED));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(6), 6L, SyncFlag.NO_SYNC));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(7), 7L, SyncFlag.ADD));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(8), 8L, SyncFlag.DELETE));

        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(9), 500L, SyncFlag.UNDEFINED));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(10), 600L, SyncFlag.NO_SYNC));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(11), 700L, SyncFlag.ADD));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(12), 800L, SyncFlag.DELETE));

        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(13), 10000L, SyncFlag.UNDEFINED));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(14), 20000L, SyncFlag.NO_SYNC));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(15), 30000L, SyncFlag.ADD));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(16), 40000L, SyncFlag.DELETE));
    }

    @Override
    public List<ClientObjectInfo> getClientObjectInfos() {
        return clientObjectInfos;
    }

    @Override
    public void add(ClientObjectInfo clientObjectInfo) {
        System.out.println("Add client info " + clientObjectInfo);
        clientObjectInfos.add(clientObjectInfo);
    }

    @Override
    public void update(ClientObjectInfo clientObjectInfo) {
        System.out.println("Update client info " + clientObjectInfo);
        for (ClientObjectInfo clientObjectInfo2 : clientObjectInfos) {
            if (clientObjectInfo2.getId().equals(clientObjectInfo.getId())) {
                clientObjectInfos.remove(clientObjectInfo2);
                clientObjectInfos.add(clientObjectInfo);
                return;
            }
        }
    }

    @Override
    public void remove(Id id) {
        System.out.println("Remove client info " + id);
        for (ClientObjectInfo clientObjectInfo : clientObjectInfos) {
            if (clientObjectInfo.getId().equals(id)) {
                clientObjectInfos.remove(clientObjectInfo);
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
