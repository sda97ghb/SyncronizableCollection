import sync.Sync;

public class Main {
    public static void main(String... args) {
        Interactor interactor = new Interactor();
        Sync sync = new Sync(interactor, interactor);
    }
}
