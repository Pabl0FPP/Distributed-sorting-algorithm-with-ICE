import com.zeroc.Ice.Current;


public class ClientHandlerI implements Demo.ClientHandler{

    public String response(String s, Current current){

        return s;
    }
}
