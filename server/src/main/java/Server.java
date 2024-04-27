import com.zeroc.Ice.*;

public class Server {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("CoordinatorServer", "default -p 10000");
            ServerCoordinatorI coordinator = new ServerCoordinatorI();
            adapter.add(coordinator, Util.stringToIdentity("Coordinator"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}