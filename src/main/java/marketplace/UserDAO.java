
package marketplace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * JPA use to create database tables, columns. DAO=Data Access Object
 * @author Joe
 */

//db table(entity) for userdao. Entity definition.
@Entity(name="userdao")
public class UserDAO {
    
    //Attributes for item entity.
    @Id
    @Column(name = "name", nullable = false)
    private String name;
    
    //Attributes for item entity.
    @Column(name = "password", nullable = false)
    private String password;
    
    //Attributes for item entity.
    @Column(name = "itemBought", nullable = false)
    private int itemBought = 0;
    
    //Attributes for item entity.
    @Column(name = "itemSold", nullable = false)
    private int itemSold = 0;
    
    //TODO: Ta bort? 
    public UserDAO()
    {
        
    }
    
    //new for hmw3 (Persistence, EntityManagerFactory, EntityManager) annotations. This constructor constructs the following variables. 
    public UserDAO (String name, String password){
        this.name = name;
        this.password = password;
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