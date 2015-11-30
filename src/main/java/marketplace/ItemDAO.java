/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketplace;

import javax.persistence.*;
import java.io.Serializable;
/**
 *
 * @author Mook
 */

@Entity(name = "Item")
public class ItemDAO {
 
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    
    
    
    public ItemDAO (String name, float price){
        
    }
    
    
    public ItemDAO (String name, float price, int amount){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
