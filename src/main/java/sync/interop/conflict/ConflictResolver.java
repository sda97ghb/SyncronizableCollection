package sync.interop.conflict;

import sync.enitity.Id;

public interface ConflictResolver {
    ConflictResolution resolve(Id id);
}
