
package marketplace;

import javax.persistence.*;
import java.io.Serializable;
/**
 * JPA use to create database tables, columns. DAO=Data Access Object
 * @author Joe
 */

//db table(entity) for item. Entity definition.
@Entity(name = "item")
public class ItemDAO {
 
    //Attributes for item entity.
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    
    //Attributes for item entity.
    @Column(name = "name", nullable = false)
    private String name;

<<<<<<< HEAD
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations
    @Column(name = "price", nullable = false)
    private float price;
    
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations
    @Column(name = "amount", nullable = true)
=======
    //Attributes for item entity.
    @Column(name = "name", nullable = false)
    private float price;
    
    //Attributes for item entity.
    @Column(name = "name", nullable = true)
>>>>>>> joes-branch
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
    //Entity, business logic.
    public Long getItemId() {
        return itemId;
    }
    //Entity, business logic.
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