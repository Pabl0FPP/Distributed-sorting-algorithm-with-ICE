import Demo.*;
import com.zeroc.Ice.*;

public class Worker {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Worker", "default -p 0");
            //WorkerI worker = new WorkerI();
            //adapter.add(worker, Util.stringToIdentity("Worker"));
            adapter.activate();

            ObjectPrx coordinatorProxy = communicator.stringToProxy("Coordinator:default -p 10000");
            CoordinatorPrx coordinator = Demo.CoordinatorPrx.checkedCast(coordinatorProxy);
            if (coordinator == null) {
                throw new Error("Invalid proxy");
            }
            coordinator.registerWorker(WorkerPrx.uncheckedCast(adapter.createProxy(Util.stringToIdentity("Worker"))));

            communicator.waitForShutdown();
        }
    }
}