import com.zeroc.Ice.*;
import Demo.*;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args,"config.client")) {
            //ObjectPrx coordinatorProxy = communicator.stringToProxy("Coordinator:default -p 10000");
            Demo.CoordinatorPrx coordinator = Demo.CoordinatorPrx.checkedCast(
                    communicator.propertyToProxy("Coordinator.Proxy")).ice_twoway().ice_timeout(-1).ice_secure(false);
            //Demo.CoordinatorPrx coordinator = Demo.CoordinatorPrx.checkedCast(coordinatorProxy);
            if (coordinator == null) {
                throw new Error("Invalid proxy");
            }

            // Datos
            int[] data = {30, 32, 61, 71, 46, 83, 84, 27, 94, 99, 58, 72, 59, 10, 67,
                    90, 33, 2, 29, 2, 65, 28, 93, 31, 14, 1, 88, 85, 80, 93, 89, 69,
                    17, 84, 31, 68, 65, 67, 77, 11, 32, 53, 1, 92, 87, 100, 58, 58, 37,
                    98, 25, 4, 16, 88, 21, 76, 66, 63, 16, 31, 11, 15, 52};


            while(true){

            }
            // Envía los datos al servidor coordinador para ordenar
            SortResult sortedData = coordinator.sortData(data);

            // Imprime el resultado ordenado
            for (int value : sortedData.data) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}