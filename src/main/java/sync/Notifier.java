package sync;

public interface Notifier {
    void notifyDownloaded(byte[] id);
    void notifyUploaded(byte[] id);
    void notifyDeletedFromClient(byte[] id);
    void notifyDeletedFromServer(byte[] id);
}
