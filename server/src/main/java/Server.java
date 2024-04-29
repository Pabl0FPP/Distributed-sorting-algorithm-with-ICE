import com.zeroc.Ice.*;
import Demo.*;


public class Server {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args,"config.server")) {
            Demo.WorkerPrx workerPrx = WorkerPrx.checkedCast(
                    communicator.propertyToProxy("Worker.Proxy")).ice_twoway().ice_timeout(-1).ice_secure(false);
            if (workerPrx == null) {
                throw new Error("Invalid proxy");
            }
            ObjectAdapter adapter = communicator.createObjectAdapter("CoordinatorServer");
            ServerCoordinatorI coordinator = new ServerCoordinatorI(workerPrx);
            adapter.add(coordinator, Util.stringToIdentity("Coordinator"));
            adapter.activate();


            communicator.waitForShutdown();
        }
    }
}