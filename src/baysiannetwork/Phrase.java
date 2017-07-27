/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baysiannetwork;

/**
 *
 * @author arash
 */
public class Phrase {
    int phraseNumber , lenght ;
    double v1,v2 ;
    
    public Phrase(int phraseNumber , double v1 ,double v2,int lenght){
        this.phraseNumber = phraseNumber ;
        this.lenght = lenght ;
        this.v1 = v1 ;
        this.v2 = v2 ;
    }
    
    public boolean isEqual(double v1 , double v2 , int lenght){
        if(v1==this.v1 && v2==this.v2 && lenght==this.lenght)
            return true ;
        return false ;
    }
    
}
