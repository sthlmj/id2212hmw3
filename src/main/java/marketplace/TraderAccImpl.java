
package marketplace;
import java.io.Serializable;
import java.rmi.RemoteException;
/**
 *  implements the callback functions
 * @author joehulden
 */

//Seriliaze för att kunna skicka till servern
@SuppressWarnings("serial")
public class TraderAccImpl  implements TraderAcc, Serializable  {
    
    private float balance = 0;
    private String name;
    
    private float price;
    private String itemName;
    
    public TraderAccImpl(String name) throws RemoteException {
        super();
        this.name = name;
    }
    
    public String getName(){
    	return this.name;
    }
    
    //Obsolete
    @Override
    public boolean equals(Object obj){
        System.out.println("anropas ");
    	if(obj instanceof TraderAcc){
    		return ((TraderAccImpl) obj ).name.equals(this.name);
    	}
    	return false;
    }

    //implements callback
	@Override
	public void itemSold(Item item) throws RemoteException {
		System.out.println("Item sold: " + item.name()  + " for " + item.price());
	}
        
    //implements callback
	@Override
	public void wishMatched(Item item) throws RemoteException {
		System.out.println("Wished item its on the market: " + item.name() + " for " + item.price());
	}
}