package sync.interop.notification;

import sync.enitity.Id;

public interface DownloadedNotification {
    void notifyDownloaded(Id id);
}
