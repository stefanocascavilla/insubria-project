import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class F {
    private static final int MAX_THREADS_NUMBER = 2;

    public static void main(String[] args) {
        if(args.length >= 2) {
            System.setSecurityManager(new RMISecurityManager());
            try {
                Registry registro = LocateRegistry.getRegistry(args[args.length - 1], 1099);
                RemoteS stub = (RemoteS) registro.lookup("sObj");

                for(int i = 0; i < MAX_THREADS_NUMBER; i++) {
                    new SingleF(i, stub, args.length - 1 == MAX_THREADS_NUMBER ? args[i] : args[0]);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
            }
        } else {
            System.err.println("You must insert an URL website and the IP of the server at least");
        }
    }
}