package test;

import sync.enitity.Id;
import sync.interop.object_management.ClientObjectDeleter;
import sync.interop.object_management.Downloader;
import sync.interop.ObjectDeleter;
import sync.interop.object_management.ServerObjectDeleter;
import sync.interop.object_management.Uploader;
import sync.interop.notification.DeletedFromClientNotification;
import sync.interop.notification.DeletedFromServerNotification;
import sync.interop.notification.DownloadedNotification;
import sync.interop.notification.UploadedNotification;

public class Interactor implements Uploader, Downloader, ClientObjectDeleter, ServerObjectDeleter {
    @Override
    public void download(Id id, DownloadedNotification notifier) {
        // Domain specific code
        System.out.println("Download " + id);

        notifier.notifyDownloaded(id);
    }

    @Override
    public void deleteFromClient(Id id, DeletedFromClientNotification notifier) {
        // Domain specific code
        System.out.println("Delete from client " + id);

        notifier.notifyDeletedFromClient(id);
    }

    @Override
    public void deleteFromServer(Id id, DeletedFromServerNotification notifier) {
        // Domain specific code
        System.out.println("Delete from server " + id);

        notifier.notifyDeletedFromServer(id);
    }

    @Override
    public void upload(Id id, UploadedNotification notifier) {
        // Domain specific code
        System.out.println("Upload " + id);

        notifier.notifyUploaded(id);
    }
}
