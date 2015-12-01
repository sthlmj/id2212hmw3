package marketplace;

/**
 * BankImpl.java is the servant class serving Bank.java
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

// Declaring the remote object
@SuppressWarnings("serial")
public class BankImpl extends UnicastRemoteObject implements Bank {
    private String bankName;
    private Map<String, Account> accounts = new HashMap<String, Account>();

// Binding reference to remote object in the remote object registry  
    public BankImpl(String bankName) throws RemoteException {
        super();
        this.bankName = bankName;
    }

// Begin: Implementation of remote interface
    @Override
    public synchronized String[] listAccounts() {
        return accounts.keySet().toArray(new String[1]);
    }

    /**
     * TODO: See if need to modify this.
     * Creates an entity for the newAccount method. ID2212 exercise 3, slide 60
     * @param name
     * @return
     * @throws RejectedException 
     */
    @Override
    public synchronized Account newAccount(String name) throws RemoteException,
                                                               RejectedException {
        AccountImpl account = (AccountImpl) accounts.get(name);
        if (account != null) {
            System.out.println("Account [" + name + "] exists!!!");
            throw new RejectedException("Rejected: se.kth.id2212.ex2.Bank: " + bankName
                                        + " Account for: " + name + " already exists: " + account);
        }
        account = new AccountImpl(name);
        accounts.put(name, account);
        System.out.println("se.kth.id2212.ex2.Bank: " + bankName + " Account: " + account
                           + " has been created for " + name);
        return account;
    }

    @Override
    public synchronized Account getAccount(String name) {
        return accounts.get(name);
    }

    @Override
    public synchronized boolean deleteAccount(String name) {
        if (!hasAccount(name)) {
            return false;
        }
        accounts.remove(name);
        System.out.println("se.kth.id2212.ex2.Bank: " + bankName + " Account for " + name
                           + " has been deleted");
        return true;
    }

    private boolean hasAccount(String name) {
        return accounts.get(name) != null;
    }
}
