import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArrayInterface extends Remote {

    public void editArray(int x) throws RemoteException;

    public void printArray() throws RemoteException;
}
