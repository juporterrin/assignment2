import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ArrayServer {

    public static void main(String[] args) {
        try{
            ArrayInterfaceImpl stub = new ArrayInterfaceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("edit",stub);
            System.out.println("RMI server is running!");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
