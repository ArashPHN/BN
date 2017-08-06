/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baysiannetwork;

import java.util.ArrayList;

/**
 *
 * @author arash
 */
public class Cutset {
    ArrayList<Integer> set = new ArrayList<>(); ;
//    int n ;
    
    public Cutset(int n , ArrayList<Integer> set){
        this.set.addAll(set) ;
//        this.n = n ;
}

    public Cutset(int n, int[] cutset) {
        for (int i = 0; i < cutset.length; i++)
            if(cutset[i]< n)
              set.add(cutset[i]);
        else
                System.out.println("there is a problem in inpu cutset");
    }
 
    public boolean isInCutset(boolean[] input){
    boolean flag = true;
        try {
            for(int i = 0 ; i < set.size(); i ++){
        if(input[set.get(i)-1])
            flag = false ;
            }
        } catch (Exception e) {
            System.out.println("inputs Cutset is WRONG !!!");
            return false ;
        }
    
        return flag ;
}

}
