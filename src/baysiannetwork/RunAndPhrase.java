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
public class RunAndPhrase {
    
    boolean isRun ;
    int lenghtOrNp ;
    int phraseNumber ;
    double value ;
    
    public RunAndPhrase(int lenght , double value){
        isRun = true ;
        lenghtOrNp = lenght ;
        this.value = value ;
    }
    
    public RunAndPhrase(int Np , int phraseNumber){
        isRun = false ;
        lenghtOrNp = Np ;
        this.phraseNumber = phraseNumber ;
    }
    
    
    
}
