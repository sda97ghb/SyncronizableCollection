package sync.interop;

import sync.enitity.Id;

public interface Downloader {
    void download(Id id, Notifier notifier);
}
