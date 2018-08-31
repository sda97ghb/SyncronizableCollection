package sync.use_case;

import sync.aux.TimestampFactory;
import sync.enitity.ClientObjectInfo;
import sync.enitity.Id;
import sync.enitity.ServerObjectInfo;
import sync.enitity.SyncFlag;
import sync.interop.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Sync {
    private boolean isDisposed = false;

    private Uploader uploader;
    private Downloader downloader;
    private ObjectDeleter deleter;

    private ClientObjectInfoDao clientObjectInfoDao;
    private ServerObjectInfoDao serverObjectInfoDao;

    private Notifier notifier;

    private List<ClientObjectInfo> clientObjectInfos;
    private List<ServerObjectInfo> serverObjectsInfos;

    private Set<Id> clientIds;
    private Set<Id> serverIds;
    private Set<Id> ids;

    public Sync(Uploader uploader, Downloader downloader,
                ObjectDeleter deleter,
                ClientObjectInfoDao clientObjectInfoDao, ServerObjectInfoDao serverObjectInfoDao) {
        this.uploader = uploader;
        this.downloader = downloader;
        this.deleter = deleter;

        this.clientObjectInfoDao = clientObjectInfoDao;
        this.serverObjectInfoDao = serverObjectInfoDao;

        this.notifier = new Notifier() {
            @Override
            public void notifyDownloaded(Id id) {
                clientObjectInfoDao.remove(id);
                clientObjectInfoDao.add(new ClientObjectInfo(id, TimestampFactory.getTimestamp(), SyncFlag.UNDEFINED));
            }

            @Override
            public void notifyUploaded(Id id) {
                clientObjectInfoDao.remove(id);
                clientObjectInfoDao.add(new ClientObjectInfo(id, TimestampFactory.getTimestamp(), SyncFlag.UNDEFINED));
            }

            @Override
            public void notifyDeletedFromClient(Id id) {
                clientObjectInfoDao.remove(id);
            }

            @Override
            public void notifyDeletedFromServer(Id id) {
                serverObjectInfoDao.remove(id);
            }
        };
    }

    public void execute()
    {
        if (isDisposed)
            return;
        isDisposed = true;

        clientObjectInfos = clientObjectInfoDao.getClientObjectInfos();
        serverObjectsInfos = serverObjectInfoDao.getServerObjectsInfos();

        clientIds = clientObjectInfos.stream().map(ClientObjectInfo::getId).collect(Collectors.toSet());
        serverIds = serverObjectsInfos.stream().map(ServerObjectInfo::getId).collect(Collectors.toSet());
        ids = new HashSet<>();
        ids.addAll(clientIds);
        ids.addAll(serverIds);

        ids.forEach(id -> {
            if (!clientHas(id)) {
                downloader.download(id, notifier);
            }
            else {
                ClientObjectInfo clientObjectInfo = getClientObjectInfoById(id);
                if (clientObjectInfo.getSyncFlag() != SyncFlag.NO_SYNC) {
                    if (!serverHas(id)) {
                        switch (clientObjectInfo.getSyncFlag()) {
                            case ADD:
                                uploader.upload(id, notifier);
                                break;
                            case UNDEFINED:
                                deleter.deleteFromClient(id, notifier);
                                break;
                            case DELETE:
                                deleter.deleteFromClient(id, notifier);
                                break;
                        }
                    }
                    else {
                        ServerObjectInfo serverObjectInfo = getServerObjectInfoById(id);
                        switch (clientObjectInfo.getSyncFlag()) {
                            case ADD:
                                uploader.upload(id, notifier);
                                break;
                            case UNDEFINED:
                                if (clientObjectInfo.getTimestamp() < serverObjectInfo.getTimestamp())
                                    downloader.download(id, notifier);
                                else if (clientObjectInfo.getTimestamp() > serverObjectInfo.getTimestamp())
                                    uploader.upload(id, notifier);
                                break;
                            case DELETE:
                                if (clientObjectInfo.getTimestamp() >= serverObjectInfo.getTimestamp()) {
                                    deleter.deleteFromClient(id, notifier);
                                    deleter.deleteFromServer(id, notifier);
                                }
                                break;
                        }
                    }
                }
            }
        });
    }

    private boolean clientHas(Id id) {
        return clientIds.contains(id);
    }

    private boolean serverHas(Id id) {
        return serverIds.contains(id);
    }

    private ClientObjectInfo getClientObjectInfoById(Id id) {
        for (ClientObjectInfo clientObjectInfo : clientObjectInfos) {
            if (clientObjectInfo.getId().equals(id)) {
                return clientObjectInfo;
            }
        }
        return null;
    }

    private ServerObjectInfo getServerObjectInfoById(Id id) {
        for (ServerObjectInfo serverObjectInfo : serverObjectsInfos) {
            if (serverObjectInfo.getId().equals(id)) {
                return serverObjectInfo;
            }
        }
        return null;
    }
}
