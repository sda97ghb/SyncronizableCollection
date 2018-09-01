package sync.interop.conflict;

import sync.enitity.Id;

public class UploadResolution implements ConflictResolver {
    @Override
    public ConflictResolution resolve(Id id) {
        return ConflictResolution.UPLOAD;
    }
}
