package marketplace;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MServer {
    private static final String USAGE = "java MServer <market_rmi_url>";
    private static final String MARKET = "JMart";

    //Konstruktur
    public MServer(String marketName) {
        try {
            Market marketobj = new MarketImpl(marketName);
            // Register the newly created object at rmiregistry.
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            Naming.rebind(marketName, marketobj);
            
            System.out.println(marketobj + " is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //startar Market Server
    public static void main(String[] args) {
        if (args.length > 1 || (args.length > 0 && args[0].equalsIgnoreCase("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String marketName;
        if (args.length > 0) {
            marketName = args[0];
        } else {
            marketName = MARKET;
        }
        new MServer(marketName);
    }
}
