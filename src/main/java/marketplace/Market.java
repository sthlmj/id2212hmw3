
package marketplace;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import javax.persistence.EntityManager;
/**
 * This is the market interface.
 * @author joehulden
 */
public interface Market extends Remote {    
    
    public String[] listTraders() throws RemoteException; 
        
    public TraderAcc newTrader(String name,String password) throws RemoteException, RejectedException;
    
    public TraderAcc getTrader(String name,String password) throws RemoteException,RejectedException;
    
    public boolean deleteTrader(String name) throws RemoteException;
    
    public void sell(Item item) throws RemoteException;
    
    public Item buy(Item item) throws RemoteException, RejectedException;
    
    public void wish(Item item) throws RemoteException, RejectedException;
    
    public List<String> listProducts() throws RemoteException;
    
    public String[] myActivities(String name) throws RemoteException, RejectedException; 
}