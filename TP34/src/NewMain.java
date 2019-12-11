
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dibop
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashMap<Integer, Integer> h1 = new HashMap<>(), h2 = new HashMap<>();
        
        h1.put(1, 11);
        h1.put(2, 12);
        h2.put(2, 12);
        h2.put(4, 14);
        
        System.out.println(h1.keySet().stream().anyMatch(i -> h2.containsKey(i)));
    }
    
}
