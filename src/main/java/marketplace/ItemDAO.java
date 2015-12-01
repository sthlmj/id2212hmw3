
package marketplace;

import javax.persistence.*;
import java.io.Serializable;
/**
 * JPA use to create database tables, columns. DAO=Data Access Object
 * @author Joe
 */

//db table(entity) for item. Entity definition.
@Entity(name = "item")
public class ItemDAO implements Serializable {
 
    //Attributes for item entity.
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    
    //Attributes for item entity.
    @Column(name = "name", nullable = false)
    private String name;

    //Attributes for item entity.
    @Column(name = "price", nullable = false)
    private float price;
    
    //Attributes for item entity.
    @Column(name = "amount", nullable = true)
    private int amount;
    
    @JoinColumn(name = "owner", nullable = true)
    private UserDAO owner;
    /*@ManyToOne
    private UserDAO owner;*/
    
    @ManyToOne
    @JoinColumn(name="id", nullable=false)
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