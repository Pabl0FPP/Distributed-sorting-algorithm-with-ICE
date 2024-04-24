

public class Server {

    public static void main(String[] args) {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)){
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("DistributedSort","default -p 10000");
            com.zeroc.Ice.Object object = new ClientHandlerI();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("Sorter"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }

}
