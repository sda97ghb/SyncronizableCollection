package sync;

public interface Uploader {
    void upload(byte[] id, Notifier notifier);
}
