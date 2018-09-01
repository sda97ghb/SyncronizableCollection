package sync.interop.notification;

import sync.enitity.Id;

public interface DeletedFromServerNotification {
    void notifyDeletedFromServer(Id id);
}
