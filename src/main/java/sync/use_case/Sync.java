package sync.use_case;

import sync.enitity.ClientObjectInfo;
import sync.enitity.Id;
import sync.enitity.ServerObjectInfo;
import sync.enitity.SyncFlag;
import sync.interop.*;
import sync.interop.conflict.ConflictResolver;
import sync.interop.conflict.DownloadResolution;
import sync.interop.notification.DownloadedNotification;
import sync.interop.notification.UploadedNotification;
import sync.interop.object_management.ClientObjectDeleter;
import sync.interop.object_management.Downloader;
import sync.interop.object_management.ServerObjectDeleter;
import sync.interop.object_management.Uploader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Sync {
    private boolean isDisposed = false;

    private Uploader uploader;
    private Downloader downloader;
    private ClientObjectDeleter clientObjectDeleter;
    private ServerObjectDeleter serverObjectDeleter;

    private ClientObjectInfoDao clientObjectInfoDao;
    private ServerObjectInfoDao serverObjectInfoDao;

    private ConflictResolver conflictResolver;

    private List<ClientObjectInfo> clientObjectInfos;
    private List<ServerObjectInfo> serverObjectsInfos;

    public Sync(Uploader uploader, Downloader downloader,
                ClientObjectDeleter clientObjectDeleter, ServerObjectDeleter serverObjectDeleter,
                ClientObjectInfoDao clientObjectInfoDao, ServerObjectInfoDao serverObjectInfoDao) {
        this.uploader = uploader;
        this.downloader = downloader;
        this.clientObjectDeleter = clientObjectDeleter;
        this.serverObjectDeleter = serverObjectDeleter;

        this.clientObjectInfoDao = clientObjectInfoDao;
        this.serverObjectInfoDao = serverObjectInfoDao;

        this.conflictResolver = new DownloadResolution();
    }

    public void setConflictResolver(ConflictResolver conflictResolver) {
        this.conflictResolver = conflictResolver;
    }

    public void execute()
    {
        if (isDisposed)
            return;
        isDisposed = true;

        clientObjectInfos = clientObjectInfoDao.getClientObjectInfos();
        serverObjectsInfos = serverObjectInfoDao.getServerObjectsInfos();

        Set<Id> clientIds = clientObjectInfos.stream().map(ClientObjectInfo::getId).collect(Collectors.toSet());
        Set<Id> serverIds = serverObjectsInfos.stream().map(ServerObjectInfo::getId).collect(Collectors.toSet());
        Set<Id> ids = new HashSet<>();
        ids.addAll(clientIds);
        ids.addAll(serverIds);

        ids.forEach(this::syncId);
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

    private void syncId(Id id) {
        ClientObjectInfo clientObjectInfo = getClientObjectInfoById(id);
        ServerObjectInfo serverObjectInfo = getServerObjectInfoById(id);

        boolean clientHasId = clientObjectInfo != null;
        boolean serverHasId = serverObjectInfo != null;

        DownloadedNotification downloadedNotification = downloadedId -> {
            if (serverObjectInfo != null)
                clientObjectInfoDao.update(new ClientObjectInfo(id, serverObjectInfo.getTimestamp(), SyncFlag.UNDEFINED));
        };

        if (!clientHasId) {
            downloader.download(id, downloadedNotification);
            return;
        }

        if (clientObjectInfo.getSyncFlag() == SyncFlag.NO_SYNC) {
            return;
        }

        UploadedNotification uploadedNotification = uploadedId -> {
            clientObjectInfoDao.update(new ClientObjectInfo(id, clientObjectInfo.getTimestamp(), SyncFlag.UNDEFINED));
            serverObjectInfoDao.update(new ServerObjectInfo(id, clientObjectInfo.getTimestamp()));
        };

        if (!serverHasId) {
            switch (clientObjectInfo.getSyncFlag()) {
                case UNDEFINED:
                    clientObjectDeleter.deleteFromClient(id, deletedId -> clientObjectInfoDao.remove(id));
                    break;
                case ADD:
                    uploader.upload(id, uploadedNotification);
                    break;
                case DELETE:
                    clientObjectDeleter.deleteFromClient(id, deletedId -> clientObjectInfoDao.remove(id));
                    break;
            }
            return;
        }

        boolean clientVersionIsNewer = clientObjectInfo.getTimestamp() > serverObjectInfo.getTimestamp();
        boolean serverVersionIsNewer = clientObjectInfo.getTimestamp() < serverObjectInfo.getTimestamp();

        if (clientVersionIsNewer) {
            switch (clientObjectInfo.getSyncFlag()) {
                case UNDEFINED:
                    uploader.upload(id, uploadedNotification);
                    break;
                case ADD:
                    uploader.upload(id, uploadedNotification);
                    break;
                case DELETE:
                    serverObjectDeleter.deleteFromServer(id, deletedId -> {
                        clientObjectInfoDao.remove(id);
                        serverObjectInfoDao.remove(id);
                    });
                    break;
            }
        }
        else if (serverVersionIsNewer) {
            switch (clientObjectInfo.getSyncFlag()) {
                case UNDEFINED:
                    downloader.download(id, downloadedNotification);
                    break;
                case ADD:
                    switch (conflictResolver.resolve(id)) {
                        case DOWNLOAD:
                            downloader.download(id, downloadedNotification);
                            break;
                        case UPLOAD:
                            uploader.upload(id, uploadedNotification);
                            break;
                    }
                    break;
                case DELETE:
                    downloader.download(id, downloadedNotification);
                    break;
            }
        }
        else {
            switch (clientObjectInfo.getSyncFlag()) {
                case ADD:
                    uploader.upload(id, uploadedNotification);
                    break;
                case DELETE:
                    downloader.download(id, downloadedNotification);
                    break;
            }
        }
    }
}
