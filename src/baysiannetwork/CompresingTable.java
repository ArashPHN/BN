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
public class CompresingTable {
   boolean[] array ;
   ArrayList<Phrase> dictionary = new ArrayList<>();
   ArrayList<RunAndPhrase> cCPT = new ArrayList<>();
   
   
   public int convertBoolean2int(boolean bool){
       if(bool)
           return 1 ;
       return 0 ;
   }
   
   
   public int phraseDetection(double v1 , double v2 ,int lenght){
       //this function returned the phraseNumber
       // and if the phrase dosent exist , make a new one then return its number
       for(Phrase phr : dictionary)
           if(phr.isEqual(v1, v2, lenght))
               return phr.phraseNumber ;
       dictionary.add(new Phrase(dictionary.size(), v1, v2, lenght));
       return dictionary.size()-1 ;
   }
   
   
    public CompresingTable(boolean[] array){
        this.array = array ;
        makeingCCPT();
        for(RunAndPhrase rap : cCPT){
            if(rap.isRun)     
            System.out.println( "run     " + rap.value +"   "+rap.lenghtOrNp);
            else
            System.out.println( "phrase    "  + rap.phraseNumber +"   "+rap.lenghtOrNp);
        }
        System.out.println("-------------------------------");
        for(Phrase ph : dictionary)
            System.out.println(ph.phraseNumber + "   " + ph.v1  + "    " + ph.v2 + "    " + ph.lenght);
        
    }
    
    
    public int phraseSequence(int begining , int lenght){
//        int lenght = end-begining +1 ;
        int np = 1;
        try {
             while(true){
               for(int i = 0 ; i< lenght ; i++ )
                if(array[begining+((np)*lenght)+i] != array[begining+i])
                    return np ;
                np++;
              }
             
           
        } catch (Exception e) {
            return np ;
        }
       
        }
    

    private void makeingCCPT() {
      int header = 0 ;
      while(header <array.length){
          if(header<array.length-1){
             if(array[header]==array[header+1]){
                 //this "if" is for counting a run 
                 int runLenght =2;
                 try {
                      while(array[header]==array[header+runLenght])
                          runLenght++ ;
                 RunAndPhrase run = new RunAndPhrase(runLenght, (double)convertBoolean2int(array[header]));
                 cCPT.add(run);
                 header = header + runLenght ;
                 } 
                 catch (Exception e) {
                 RunAndPhrase run = new RunAndPhrase(runLenght-1,(double) convertBoolean2int(array[header]));
                 cCPT.add(run);
                 header = header + runLenght ;
                     
                 }
                 
                
             }
             
             else{
                 // this "else" is for counting a phrase
                 int phraseLenght =2;
                 try {
                      while(array[header+1]==array[header+phraseLenght])
                          phraseLenght++ ;
                      int phraseNum = phraseDetection(convertBoolean2int(array[header]), convertBoolean2int(array[header+1]), phraseLenght);
                      int np = phraseSequence(header, phraseLenght);
                      cCPT.add(new RunAndPhrase(np, phraseNum));
                      header = header + (np*phraseLenght)  ;
                 
                 } catch (Exception e) {
                     int phraseNum = phraseDetection(convertBoolean2int(array[header]), convertBoolean2int(array[header+1]), phraseLenght-1);
//                      int np = phraseSequence(header, phraseLenght);
                      cCPT.add(new RunAndPhrase(1, phraseNum));
                      header = header + phraseLenght  ;
                     
                 }

             }
             
             
             
             
          }

      else{
              cCPT.add(new RunAndPhrase(1,(double) convertBoolean2int(array[header])));
              header++ ;
              }
            }
    }
    
    
    
}
