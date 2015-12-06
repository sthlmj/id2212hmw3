
package marketplace;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * JPA use to create database tables, columns. DAO=Data Access Object
 * @author Joe
 */

//skapar relationen med db tabellen
@Entity(name="userdao")
public class UserDAO implements Serializable {
    
    //primär nyckeln
    @Id
    @Column(name = "name", nullable = false)
    private String name;
    
    //kolumnen
    @Column(name = "password", nullable = false)
    private String password;
    
    //kolumn
    @Column(name = "itemBought", nullable = false)
    private int itemBought = 0;
    
    //kolumn
    @Column(name = "itemSold", nullable = false)
    private int itemSold = 0;
    
    //kopplar ihop userDAO med item tabellen
    @OneToMany(cascade=ALL,mappedBy="owner")
    private Collection<ItemDAO> items = new HashSet();
    
    
    public Collection<ItemDAO> getItems() {
        return items;
    }

   
    
    public UserDAO()
    {
        
    }
    
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This constructor constructs the following variables. 
    public UserDAO (String name, String password){
        this.name = name;
        this.password = password;
    }

    
    
   public void incrSoldItems(){
       this.itemSold++;
   }
   public void incrBoughtItems(){
       this.itemBought++;
   }
   
    public void setItems(Collection<ItemDAO> items) {
        this.items = items;
    }
    
    public void addItem(ItemDAO item){
        this.items.add(item);
    }
    
    
    //Entity, business logic.
    public String getName() {
        return name;
    }

    //Entity, business logic.
    public String getPassword() {
        return password;
    }

    //Entity, business logic.
    public int getItemBought() {
        return itemBought;
    }

    //Entity, business logic.
    public int getItemSold() {
        return itemSold;
    }
}