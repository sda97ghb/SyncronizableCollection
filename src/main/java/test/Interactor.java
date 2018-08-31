package test;

import sync.enitity.Id;
import sync.interop.Downloader;
import sync.interop.Notifier;
import sync.interop.ObjectDeleter;
import sync.interop.Uploader;

public class Interactor implements Uploader, Downloader, ObjectDeleter {
    @Override
    public void download(Id id, Notifier notifier) {
        // Domain specific code
        notifier.notifyDownloaded(id);
    }

    @Override
    public void deleteFromClient(Id id, Notifier notifier) {
        // Domain specific code
        notifier.notifyDeletedFromClient(id);
    }

    @Override
    public void deleteFromServer(Id id, Notifier notifier) {
        // Domain specific code
        notifier.notifyDeletedFromServer(id);
    }

    @Override
    public void upload(Id id, Notifier notifier) {
        // Domain specific code
        notifier.notifyUploaded(id);
    }
}
