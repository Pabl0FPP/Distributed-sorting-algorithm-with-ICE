import Demo.*;
import com.zeroc.Ice.*;

public class Worker {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args,"config.worker")) {
            ObjectAdapter adapter = communicator.createObjectAdapter("Worker");
            com.zeroc.Ice.Object worker = new WorkerI();
            adapter.add(worker, Util.stringToIdentity("Worker"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}