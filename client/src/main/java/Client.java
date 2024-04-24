public class Client {
    public static void main(String[] args) {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)){

            com.zeroc.Ice.ObjectPrx sorter = communicator.stringToProxy("Sorter: -p 10000");
            Demo.ClientHandlerPrx clientHandler = Demo.ClientHandlerPrx.checkedCast(
                    communicator.propertyToProxy("ClientHandler.Proxy")).ice_twoway();

            if(clientHandler == null)
            {
                throw new Error("Invalid proxy");
            }
        }
    }
}
