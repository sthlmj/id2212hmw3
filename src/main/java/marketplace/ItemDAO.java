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

@Entity(name = "item")
public class ItemDAO {
 
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    
    @Column(name = "name", nullable = false)
    private String name;

    
     @Column(name = "name", nullable = false)
    private float price;
    
    @Column(name = "name", nullable = true)
    private int amount;
    
    
    public ItemDAO()
    {
        
    }
    
    public ItemDAO (String name, float price){
      this.name  = name;
      this.price = price;
    }
    
    
    public ItemDAO (String name, float price, int amount){
      this.name  = name;
      this.price = price;
      this.amount = amount;
    }

    
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
    public Long getId() {
        return itemId;
    }
    
}
