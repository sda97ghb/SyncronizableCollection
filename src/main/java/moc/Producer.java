package moc;

import aux.RandomBytesGenerator;
import sync.*;

import java.util.LinkedList;
import java.util.List;

public class Producer {
    public static List<ServerObject> createServerObjects() {
        List<ServerObject> list = new LinkedList<>();
        for (int i = 0; i < 10; ++ i)
            list.add(new ServerObject(
                    IdFactory.getId(),
                    TimestampFactory.getTimestamp(),
                    RandomBytesGenerator.getRandomBytes(6)));
        return list;
    }

    public static List<ClientObject> createClientObjects() {
        List<ClientObject> list = new LinkedList<>();
        for (int i = 0; i < 10; ++ i)
            list.add(new ClientObject(
                    IdFactory.getId(),
                    TimestampFactory.getTimestamp(),
                    SyncFlag.ADD,
                    RandomBytesGenerator.getRandomBytes(10)
            ));
        return list;
    }
}
