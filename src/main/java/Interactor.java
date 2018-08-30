import sync.Downloader;
import sync.Notifier;
import sync.ObjectDeleter;
import sync.Uploader;

public class Interactor implements Uploader, Downloader, ObjectDeleter {
    @Override
    public void download(byte[] id, Notifier notifier) {
        // Domain specific code
        notifier.notifyDownloaded(id);
    }

    @Override
    public void upload(byte[] id, Notifier notifier) {
        // Domain specific code
        notifier.notifyUploaded(id);
    }

    @Override
    public void deleteFromClient(byte[] id, Notifier notifier) {
        // Domain specific code
        notifier.notifyDeletedFromClient(id);
    }

    @Override
    public void deleteFromServer(byte[] id, Notifier notifier) {
        // Domain specific code
        notifier.notifyDeletedFromServer(id);
    }
}
