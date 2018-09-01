package sync.interop.object_management;

import sync.enitity.Id;
import sync.interop.notification.UploadedNotification;

public interface Uploader {
    void upload(Id id, UploadedNotification notifier);
}
