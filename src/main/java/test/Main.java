package test;

import sync.interop.ClientObjectInfoDao;
import sync.interop.ServerObjectInfoDao;
import sync.use_case.Sync;
import test.Interactor;

public class Main {
    public static void main(String... args) {
        ClientObjectInfoDao clientObjectInfoDao = new ClientObjectInfoDaoImpl();
        ServerObjectInfoDao serverObjectInfoDao = new ServerObjectInfoDaoImpl();
        Interactor interactor = new Interactor();

        System.out.println("Initial values:");
        clientObjectInfoDao.getClientObjectInfos().forEach(System.out::println);
        serverObjectInfoDao.getServerObjectsInfos().forEach(System.out::println);
        System.out.println();

        Sync sync = new Sync(interactor, interactor, interactor, clientObjectInfoDao, serverObjectInfoDao);
        sync.execute();

        System.out.println("Result values:");
        clientObjectInfoDao.getClientObjectInfos().forEach(System.out::println);
        serverObjectInfoDao.getServerObjectsInfos().forEach(System.out::println);
        System.out.println();
    }
}
