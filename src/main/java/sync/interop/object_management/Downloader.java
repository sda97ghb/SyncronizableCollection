package sync.interop.object_management;

import sync.enitity.Id;
import sync.interop.notification.DownloadedNotification;

public interface Downloader {
    void download(Id id, DownloadedNotification notifier);
}
