import com.zeroc.Ice.*;

public class Server {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args,"config.server")) {
            ObjectAdapter adapter = communicator.createObjectAdapter("CoordinatorServer");
            ServerCoordinatorI coordinator = new ServerCoordinatorI();
            adapter.add(coordinator, Util.stringToIdentity("Coordinator"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}