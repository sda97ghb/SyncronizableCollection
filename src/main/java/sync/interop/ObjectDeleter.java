package sync.interop;

import sync.enitity.Id;

public interface ObjectDeleter {
    void deleteFromClient(Id id, Notifier notifier);
    void deleteFromServer(Id id, Notifier notifier);
}
