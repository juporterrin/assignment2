import java.rmi.Naming;

public class Remote {
    public static void main(String[] args) {
        try{
            Calculator stub = (Calculator) Naming.lookup("rmi://192.168.1.134:1099/calculator");
            stub.pop();
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
