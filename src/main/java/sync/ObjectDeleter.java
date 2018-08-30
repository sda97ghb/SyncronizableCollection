package sync;

public interface ObjectDeleter {
    void deleteFromClient(byte[] id, Notifier notifier);
    void deleteFromServer(byte[] id, Notifier notifier);
}
