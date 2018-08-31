package sync.interop;

import sync.enitity.Id;

public interface Uploader {
    void upload(Id id, Notifier notifier);
}
