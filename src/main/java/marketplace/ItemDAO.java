
package marketplace;

import javax.persistence.*;
import java.io.Serializable;
/**
 * Used to create database tables with JPA
 * @author Joe
 */

//new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations
@Entity(name = "item")
public class ItemDAO {
 
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations
    @Column(name = "name", nullable = false)
    private String name;

    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations
    @Column(name = "price", nullable = false)
    private float price;
    
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations
    @Column(name = "amount", nullable = true)
    private int amount;
    
    //TODO: Ta bort? 
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

    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public Long getItemId() {
        return itemId;
    }
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public String getName() {
        return name;
    }
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public void setName(String name) {
        this.name = name;
    }
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public float getPrice() {
        return price;
    }
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public void setPrice(float price) {
        this.price = price;
    }
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public int getAmount() {
        return amount;
    }
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public void setAmount(int amount) {
        this.amount = amount;
    }
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This creates an object.
    public Long getId() {
        return itemId;
    }
}