import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ArrayInterfaceImpl extends UnicastRemoteObject implements ArrayInterface {




    public ArrayInterfaceImpl() throws RemoteException {
    }

    int[] arr = {1,2,3,4,5};


    @Override
    public void editArray(int x) throws RemoteException {
        this.arr[0] = x;
    }

    @Override
    public void printArray() throws RemoteException {
    for(int i = 0; i < arr.length; i++){
        System.out.println(this.arr[i]);
    }
    }
}
