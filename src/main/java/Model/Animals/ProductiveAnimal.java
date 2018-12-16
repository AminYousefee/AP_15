package Model.Animals;

import java.util.HashMap;
import java.util.Map;

public abstract class ProductiveAnimal extends NonWildAnimal {
    
    

    private static  HashMap<Integer,AnimalInfo> PossibleProducts;


    
    public abstract boolean eat();
    public abstract boolean produce();
    public abstract boolean die();
    
    

    public static AnimalInfo findAnimalType(String name){
        for (Map.Entry<Integer,AnimalInfo> entry:PossibleProducts.entrySet()) {
            if (entry.getValue().getName().equals(name)){
                return entry.getValue();
            }
        }
        return null;
        
    }
    
    
    
    
    
    

}
