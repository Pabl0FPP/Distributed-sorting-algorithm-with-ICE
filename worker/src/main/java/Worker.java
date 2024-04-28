import Demo.*;
import com.zeroc.Ice.*;

public class Worker {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args,"config.worker")) {
            ObjectAdapter adapter = communicator.createObjectAdapter("Worker");
            //WorkerI worker = new WorkerI();
            //adapter.add(worker, Util.stringToIdentity("Worker"));
            adapter.activate();

            //ObjectPrx coordinatorProxy = communicator.stringToProxy("");
            CoordinatorPrx coordinator = Demo.CoordinatorPrx.checkedCast(communicator.propertyToProxy("Coordinator.Proxy"));
            if (coordinator == null) {
                throw new Error("Invalid proxy");
            }
            coordinator.registerWorker(WorkerPrx.uncheckedCast(adapter.createProxy(Util.stringToIdentity("Worker"))));

            communicator.waitForShutdown();
        }
    }
}