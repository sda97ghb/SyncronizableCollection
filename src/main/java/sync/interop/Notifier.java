package sync.interop;

import sync.enitity.Id;

public interface Notifier {
    void notifyDownloaded(Id id);
    void notifyUploaded(Id id);
    void notifyDeletedFromClient(Id id);
    void notifyDeletedFromServer(Id id);
}
