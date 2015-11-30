
package marketplace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Joe
 */
@Entity(name="userdao")
public class UserDAO {
    
    @Id
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "itemBought", nullable = false)
    private int itemBought = 0;
    
    @Column(name = "itemSold", nullable = false)
    private int itemSold = 0;
    
    
    public UserDAO()
    {
        
    }
    
    public UserDAO (String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getItemBought() {
        return itemBought;
    }

    public int getItemSold() {
        return itemSold;
    }
    
    
    
}
