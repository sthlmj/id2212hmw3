
package marketplace;

import javax.persistence.*;
import java.io.Serializable;
/**
 * JPA use to create database tables, columns. DAO=Data Access Object
 * @author Joe
 */

//skapar relationen med db tabellen
@Entity(name = "item")
public class ItemDAO implements Serializable {
 
    //primär nyckeln
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    
    //kolumn
    @Column(name = "name", nullable = false)
    private String name;

    //kolumn
    @Column(name = "price", nullable = false)
    private float price;
    
    //kolumn
    @Column(name = "amount", nullable = true)
    private int amount;
    
    //kopplar ihop itemdao med userDAO tabellen. En ägare kan ha många produkter.
    @ManyToOne
    @JoinColumn(name = "owner", nullable = true)
    private UserDAO owner;

     public UserDAO getUserDAO(){
        return owner;
    }
    public ItemDAO()
    {
        
    }
    
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This constructor constructs the following variables. 
    public ItemDAO (String name, float price){
      this.name  = name;
      this.price = price;
    }
    
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This constructor constructs the following variables.     
    public ItemDAO (String name, float price, int amount){
      this.name  = name;
      this.price = price;
      this.amount = amount;
    }
    
   //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This constructor constructs the following variables.     
    public ItemDAO (UserDAO owner, String name, float price){
      this.owner = owner;
      this.name  = name;
      this.price = price;
    }
    
    
    //Entity, business logic. Hämtar itemID
    public Long getItemId() {
        return itemId;
    }
    //Entity, business logic. Lagrar itemID
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    //Entity, business logic.
    public String getName() {
        return name;
    }
    //Entity, business logic.
    public void setName(String name) {
        this.name = name;
    }
    //Entity, business logic.
    public float getPrice() {
        return price;
    }
    //Entity, business logic.
    public void setPrice(float price) {
        this.price = price;
    }
    //Entity, business logic.
    public int getAmount() {
        return amount;
    }
    //Entity, business logic.
    public void setAmount(int amount) {
        this.amount = amount;
    }
    //Entity, business logic.
    public Long getId() {
        return itemId;
    }
}