package sync.interop.conflict;

import sync.enitity.Id;

public class DownloadResolution implements ConflictResolver {
    @Override
    public ConflictResolution resolve(Id id) {
        return ConflictResolution.DOWNLOAD;
    }
}
