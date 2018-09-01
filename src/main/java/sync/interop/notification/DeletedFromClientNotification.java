package sync.interop.notification;

import sync.enitity.Id;

public interface DeletedFromClientNotification {
    void notifyDeletedFromClient(Id id);
}
