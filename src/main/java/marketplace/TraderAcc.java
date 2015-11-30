
package marketplace;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Interface for callback functions
 * @author joehulden
 */
public interface TraderAcc extends Remote {
        
    public String getName() throws RemoteException;
    
    public void itemSold(Item item) throws RemoteException;
    
    public void wishMatched(Item item) throws RemoteException;;
    
}