package marketplace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public interface Item  extends Remote{
    
    public String name() throws RemoteException;

    public float price() throws RemoteException;

    public TraderAcc trader() throws RemoteException;
}
