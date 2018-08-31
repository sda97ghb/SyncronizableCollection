package test;

import sync.aux.TimestampFactory;
import sync.enitity.ClientObjectInfo;
import sync.enitity.SyncFlag;
import sync.interop.ClientObjectInfoDao;
import sync.enitity.Id;

import java.util.LinkedList;
import java.util.List;

public class ClientObjectInfoDaoImpl implements ClientObjectInfoDao {
    private List<ClientObjectInfo> clientObjectInfos = new LinkedList<>();

    public ClientObjectInfoDaoImpl() {
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(1), randomTimestamp(), SyncFlag.UNDEFINED));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(2), randomTimestamp(), SyncFlag.UNDEFINED));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(3), randomTimestamp(), SyncFlag.UNDEFINED));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(4), randomTimestamp(), SyncFlag.UNDEFINED));
        clientObjectInfos.add(new ClientObjectInfo(Id.fromLong(5), randomTimestamp(), SyncFlag.UNDEFINED));
    }

    @Override
    public List<ClientObjectInfo> getClientObjectInfos() {
        return clientObjectInfos;
    }

    @Override
    public void add(ClientObjectInfo clientObjectInfo) {
        clientObjectInfos.add(clientObjectInfo);
    }

    @Override
    public void remove(Id id) {
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
