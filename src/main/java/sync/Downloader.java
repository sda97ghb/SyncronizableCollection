package sync;

public interface Downloader {
    void download(byte[] id, Notifier notifier);
}
