package sync.interop.object_management;

import sync.enitity.Id;
import sync.interop.notification.DeletedFromClientNotification;

public interface ClientObjectDeleter {
    void deleteFromClient(Id id, DeletedFromClientNotification notifier);
}
