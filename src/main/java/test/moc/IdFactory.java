package test.moc;

import sync.enitity.Id;

public class IdFactory {
    private static short lastEmittedId = 0;

    public static Id getId() {
        ++ lastEmittedId;
        return Id.fromLong(lastEmittedId);
    }
}
