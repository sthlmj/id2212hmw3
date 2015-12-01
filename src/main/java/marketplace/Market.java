
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
    
    public String[] listTraderAccs() throws RemoteException; 
        
    public TraderAcc newTraderAcc(String name) throws RemoteException, RejectedException;
    
    public TraderAcc getTraderAcc(String name) throws RemoteException,RejectedException;
    
    public boolean deleteTraderAcc(String name) throws RemoteException;
    
    public void sell(Item item) throws RemoteException;
    
    public Item buy(Item item) throws RemoteException, RejectedException;
    
    public void wish(Item item) throws RemoteException, RejectedException;
    
    public List<String> listProducts() throws RemoteException;   
}