
package marketplace;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**  
 * TODO: Make BANK db persistent for account and balance. 
 * TODO: Make buy function persistent so it saves counts for sold and bought products.
 * TODO: Make password length requirements and persistent.
 * TODO: Make an "Activity Indicator" that lists sold and bought items.
 * TODO: Make new Command myActivities for activity indicator. 
 * TODO: Make newTrader so that it rejects already taken user names. 
        sell                    OK 
        buy                     TODO
        wish                    TODO
        listProducts            OK
        listTraders             OK
        newTrader               OK
        getTrader               OK
        deleteTrader            OK
        myActivities            TODO
        quit                    OK
        help                    OK
 */

/**
 * This is the servant class for the Market.java interface.
 * @author joehulden
 */

//implementerar market interface creates and exports a new unicast remote object
@SuppressWarnings("serial")
public class MarketImpl extends UnicastRemoteObject implements Market {
    
    private String marketName;
    private ArrayList<Item> items = new ArrayList<Item>(); 
    private ArrayList<TraderAcc> traders = new ArrayList<TraderAcc>(); //fix
    private ArrayList<Item> wishlist = new ArrayList<Item>();
    private ArrayList<TraderAcc> traderaccs = new ArrayList<TraderAcc>();
    private Bank bank;
    
    //Entity manager usage. Getting an EntityManagerFactory. 
    private  EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("market"); // koppling till vårt "persistence unit name"
    
    //startar rmi registret(som dns uppslagning) kopplingen mot banken
    public MarketImpl(String marketName) throws RemoteException {
        super();
        this.marketName = marketName;
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            bank = (Bank) Naming.lookup("Nordea");
            
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
    }
    
    //implements interface. TODO check if it works.
    @Override
    public synchronized String[] listTraders() {
    	
        
        EntityManager em = this.emFactory.createEntityManager();
        
        em.getTransaction().begin();
        List <Object> result =  em.createQuery("SELECT a FROM userdao a").getResultList();
        String[] out = new String[result.size()];
        int i = 0;
        for(Object o : result  ){
            if(o instanceof UserDAO){
                UserDAO t = ((UserDAO) o);
                out[i++] = t.getName();
            }
        }
        em.getTransaction().commit();
        
        
        
        
    	
    	/*int i = 0;
    	for(TraderAcc t : traderaccs){
    		out[i++] = ((TraderAccImpl ) t).getName();
    	}*/
        return out;
    }
    
     /**
     * new for hmw3. EntityManager Usage. Getting an EntityManagerFactory
     * Creates an entity for the newTraderAcc method. ID2212 exercise 3, slide 60
     * @param name
     * @return
     * @throws RejectedException 
     * interface implementation 
     * TODO ta emot password i inparametern
     */
    @Override
    public synchronized TraderAcc newTrader(String name,String password) throws RemoteException, RejectedException {
        
        
        
        //TODO lägg till kontroller för att se om användarnamnet är taget. Skapa en lista med List interface.
        
        EntityManager em  = emFactory.createEntityManager(); // (hämtar en instans koppling till databasen) entitymanager behövs för att för persista data till databasen
        
        em.getTransaction().begin(); //Startar transaktion. EntityManager Usage. At transaction start.
             
        //Ser om användare existerar i databas (avbryt då transaktion)
        if( em.find(UserDAO.class, name)  != null) {
            em.getTransaction().rollback();
            throw new RejectedException("User already exist");
            //return null;?
        }
            
        if(em.getTransaction().isActive() ) {
            if(password == null) {
                throw new RejectedException("Please provide a password");
            }
            if(password.length() < 8){
                throw new RejectedException("Password must be atleast 8 char long");
            }
            UserDAO user = new UserDAO(name,password);
            em.persist(user);
            em.getTransaction().commit();
        }
        
        
        return new TraderAccImpl(name);
    }
    
    /**
     * Get DB Trader Account. This is working.
     * @param name
     * @return
     * @throws RejectedException 
     */
    //implements interface
    @Override
    public synchronized TraderAcc getTrader(String name,String password) throws RejectedException {
   
        
        EntityManager em = this.emFactory.createEntityManager();
        
        em.getTransaction().begin();
        UserDAO user = em.find(UserDAO.class, name);
        if(user != null) {
           
            try {
                em.getTransaction().commit();
                if(user.getPassword().equals(password)){
                   return new TraderAccImpl(name); 
                } 
                else {
                    throw new RejectedException("Wrong password for account");
                }
                
            } catch (RemoteException ex) {
               
            }
        }else {
            em.getTransaction().rollback();
            
        }
        throw new RejectedException("Account " + name + " does not exist");
       /* 
        try {
                for(TraderAcc t : traderaccs){
                     if(t.getName().equals(name)){ //account found
                        return t;  
                     }
                 } 

        } catch (RemoteException e) {
                e.printStackTrace();
        }*/
        
    }
    
    //implements interface. TODO Check if it works.
    @Override
    public synchronized boolean deleteTrader(String name) {
        
        EntityManager em = this.emFactory.createEntityManager();
        
        em.getTransaction().begin();
        UserDAO delete = em.find(UserDAO.class, name);
        if(delete != null) {
           em.remove(delete); 
           em.getTransaction().commit();
           return true;
        }else {
            em.getTransaction().rollback();
            return false;
        }
        
       /* 
        
        try{
            
            for(TraderAcc t : traderaccs){
                if(t.getName().equals(name)){ //account found
                    traderaccs.remove(t);
                    System.out.println("se.kth.id2212.ex2.marketrmi: " + marketName + " Account for " + name + " has been deleted");
               return true;
            }
        } 
        } catch (RemoteException ex) {
            }
        return false;*/
    }

    //list all products available on the market. This is working.
    @Override
    public List<String> listProducts() throws RemoteException {
        List <String> out = new ArrayList<>();
        EntityManager em = this.emFactory.createEntityManager();
        
        em.getTransaction().begin();
        for(Object i :  em.createQuery("SELECT a FROM item a").getResultList() ){
            if(i instanceof ItemDAO){
                ItemDAO t = ((ItemDAO) i);
                out.add("Name: " + t.getName() + " Price: " + t.getPrice());
            }
        }
        em.getTransaction().commit();
       
    	/*for(Item i : items){
            out.add("Name: " + i.name() + " Price: " + i.price());
    	}*/
        return out;
    }	

    //implements interface TODO
    @Override
    public Item buy(Item item) throws RemoteException, RejectedException {
        Item temp = null;
        Account buyer = bank.getAccount(item.trader().getName());
        if(buyer == null) {
            throw new RejectedException("You have no bank account");
        }
        
        EntityManager em  = emFactory.createEntityManager(); // (hämtar en instans koppling till databasen) entitymanager behövs för att för persista data till databasen
        em.getTransaction().begin();  
        
        //sök efter matchning
        List <ItemDAO> items = (List <ItemDAO>) em.createQuery("SELECT i FROM item i WHERE i.name = :name AND i.price = :price")
                .setParameter("name", item.name()).setParameter("price", item.price()).getResultList();
        if(items.isEmpty()){
            throw new RejectedException("Item could not be purchased");
        }
        for(ItemDAO i : items){
            temp = new ItemImpl(i.getName(), i.getPrice());
            i.getUserDAO().incrSoldItems(); //seller 
            em.find(UserDAO.class, item.trader().getName()).incrBoughtItems();//Buyer
            em.remove(i);
        }
        em.getTransaction().commit();
        return temp;
//uppdatera 
        
        
        
        
        /*
        em.find(UserDAO.class, it.trader().getName()).incrSoldItems();//Seller
        em.find(UserDAO.class, item.trader().getName()).incrBoughtItems();//Buyer 
        
        
            int i = 0;
            for(Item it: items) {
                
                //här säljs varan
                if(it.name().equals(item.name()) && it.price() <= item.price()) {
                    
                    EntityManager em  = emFactory.createEntityManager(); // (hämtar en instans koppling till databasen) entitymanager behövs för att för persista data till databasen
                    em.getTransaction().begin();   
                    
                    
                    buyer.withdraw(it.price());
                    bank.getAccount(it.trader().getName()).deposit(it.price());
                    Item temp  = items.remove(i);
                    temp.trader().itemSold(temp);
                    
                    em.getTransaction().commit();
                    return temp;
                }
                i++;
            }*/
            
    }

    //implements interface TODO
    @Override
    public void wish(Item item) throws RemoteException, RejectedException 
    {
        EntityManager em  = emFactory.createEntityManager();
        em.getTransaction().begin();
        List <ItemDAO> items = (List <ItemDAO>) em.createQuery("SELECT i FROM item i WHERE i.name = :name AND i.price = :price")
                .setParameter("name", item.name()).setParameter("price", item.price()).getResultList();
        
        for(ItemDAO i : items){
            
           item.trader().wishMatched(new ItemImpl(i.getName(), i.getPrice()));
           return;
        }
        em.getTransaction().commit();
        wishlist.add(item);
		
    }

    //implements interface. This is working.
    @Override
    public void sell(Item item) throws RemoteException {
        
        item.trader().getName();
        
        for(Item it: wishlist) {
            
            //wishMatched
            if(item.name().equals(it.name())  && item.price() <= it.price()) {
                it.trader().wishMatched(item);
            }  
        }
        
        EntityManager em = this.emFactory.createEntityManager();
        em.getTransaction().begin();
        UserDAO user  = em.find(UserDAO.class, item.trader().getName());
        if(user == null) {
            em.getTransaction().rollback();
        } 
        else {
            user.addItem(new ItemDAO(user,item.name(),item.price()));
            em.persist(user);
            em.getTransaction().commit();
        }
        
          

       
       // items.add(item);
    }

    //implements myActivities interface for bought and sold items.
    @Override
    public synchronized String[] myActivities(String name) throws RejectedException {

        EntityManager em = emFactory.createEntityManager();
        
        
        UserDAO user = em.find(UserDAO.class, name);
        if(user == null){
            throw new RejectedException("User does not exist");
        }
        
        String[] out = new String[2]; // en plats för vardera egenskap
        out[0] = "Items bought: " + Integer.toString(user.getItemBought());
        out[1] = "Items sold: " + Integer.toString(user.getItemSold());
        
        
       /* em.getTransaction().begin();
        List <Object> activities =  em.createQuery("SELECT itemsold, itembought FROM userdao").getResultList();
        String[] out = new String[activities.size()];
        int i = 0;
        for(Object o : activities  ){
            if(o instanceof UserDAO){
                UserDAO t = ((UserDAO) o);
                out[i++] = t.getName();
            }
        }
        em.getTransaction().commit();*/
        return out;
    }
    
}