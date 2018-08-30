package sync;

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

    Notifier notifier;

    List<ClientObjectInfo> clientObjectInfos;
    List<ServerObjectInfo> serverObjectsInfos;

    private Set<byte[]> clientIds;
    private Set<byte[]> serverIds;
    private Set<byte[]> ids;

    public Sync(Uploader uploader, Downloader downloader,
                ClientObjectInfoDao clientObjectInfoDao, ServerObjectInfoDao serverObjectInfoDao) {
        this.uploader = uploader;
        this.downloader = downloader;

        this.clientObjectInfoDao = clientObjectInfoDao;
        this.serverObjectInfoDao = serverObjectInfoDao;

        this.notifier = new Notifier() {
            @Override
            public void notifyDownloaded(byte[] id) {
                clientObjectInfoDao.remove(id);
                clientObjectInfoDao.add(new ClientObjectInfo(id, TimestampFactory.getTimestamp(), SyncFlag.UNDEFINED));
            }

            @Override
            public void notifyUploaded(byte[] id) {
                clientObjectInfoDao.remove(id);
                clientObjectInfoDao.add(new ClientObjectInfo(id, TimestampFactory.getTimestamp(), SyncFlag.UNDEFINED));
            }

            @Override
            public void notifyDeleted(byte[] id) {
                clientObjectInfoDao.remove(id);
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
                return;
            }
            else {
                ClientObjectInfo clientObjectInfo = getClientObjectInfoById(id);
                if (clientObjectInfo.getSyncFlag() == SyncFlag.NO_SYNC) {
                    return;
                }

                if (!serverHas(id)) {
                    switch (clientObjectInfo.getSyncFlag()) {
                        case ADD:
                            uploader.upload(id, notifier);
                            return;
                        case UNDEFINED:
                            deleter.deleteFromClient(id, notifier);
                            return;
                        case DELETE:
                            deleter.deleteFromClient(id, notifier);
                            return;
                    }
                }
                else {
                    ServerObjectInfo serverObjectInfo = getServerObjectInfoById(id);
                    switch (clientObjectInfo.getSyncFlag()) {
                        case ADD:
                            uploader.upload(id, notifier);
                            return;
                        case UNDEFINED:
                            deleter.delete(id, notifier);
                            return;
                        case DELETE:
                            deleter.delete(id, notifier);
                            return;
                    }
                }
            }
        });
    }

    private boolean clientHas(byte[] id) {
        return clientIds.contains(id);
    }

    private boolean serverHas(byte[] id) {
        return serverIds.contains(id);
    }

    private ClientObjectInfo getClientObjectInfoById(byte[] id) {
        for (ClientObjectInfo clientObjectInfo : clientObjectInfos)
            if (clientObjectInfo.getId().equals(id))
                return clientObjectInfo;
    }

    private ServerObjectInfo getServerObjectInfoById(byte[] id) {
        for (ServerObjectInfo serverObjectInfo : serverObjectsInfos)
            if (serverObjectInfo.getId().equals(id))
                return serverObjectInfo;
    }
}
