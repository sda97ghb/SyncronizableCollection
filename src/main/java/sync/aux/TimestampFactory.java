package sync.aux;

public class TimestampFactory {
    public static long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
