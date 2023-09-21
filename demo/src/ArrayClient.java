import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ArrayClient {

    public static void main(String[] args) {

        try{
            Registry registry = LocateRegistry.getRegistry("localhost",1099);
            ArrayInterface stub = (ArrayInterface) registry.lookup("edit");
            stub.editArray(19);
            stub.printArray();
        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }


}
