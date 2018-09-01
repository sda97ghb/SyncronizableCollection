package sync.interop.object_management;

import sync.enitity.Id;
import sync.interop.notification.DeletedFromServerNotification;

public interface ServerObjectDeleter {
    void deleteFromServer(Id id, DeletedFromServerNotification notifier);
}
