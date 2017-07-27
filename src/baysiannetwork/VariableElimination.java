/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baysiannetwork;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 *
 * @author arash
 */
class VariableElimination {
    ArrayList<Phrase> dictionary = new ArrayList<>();
    ArrayList<RunAndPhrase> cCPT = new ArrayList<>();
//    ArrayList<Phrase> newDictionary = new ArrayList<>();
    ArrayList<RunAndPhrase> newCCPT = new ArrayList<>();
    double faliurePrabability ;
    
    public VariableElimination(ArrayList<Phrase> dictionary , ArrayList<RunAndPhrase> cCPT ,double faliurePrabability){
        this.dictionary = dictionary ;
        this.cCPT = cCPT ;
        this.faliurePrabability =faliurePrabability;
        
        
        eliminationLastVariable();
        
        
        
           for(RunAndPhrase rap : newCCPT){
            if(rap.isRun)     
            System.out.println( "run     " + rap.value +"   "+rap.lenghtOrNp);
            else
            System.out.println( "phrase    "  + rap.phraseNumber +"   "+rap.lenghtOrNp);
        }
        System.out.println("-------------------------------");
        for(Phrase ph : dictionary)
            System.out.println(ph.phraseNumber + "   " + ph.v1  + "    " + ph.v2 + "    " + ph.lenght);
        
    }

    private void eliminationLastVariable() {
        int S = 1;
        double R=0 ;
        double Pf = faliurePrabability;
        for(RunAndPhrase rap : cCPT){
            if(rap.isRun){
                if(S%2==1){
                    if(rap.lenghtOrNp%2==1){
//                        1st case
                        RunAndPhrase newRAP = new RunAndPhrase((rap.lenghtOrNp-1)/2, rap.value);
                        R = rap.value*Pf;
                        newCCPT.add(newRAP);
                        S = S +rap.lenghtOrNp;
                       
                    }
                    else{
//                        2st case
                        RunAndPhrase newRAP = new RunAndPhrase(rap.lenghtOrNp/2, rap.value);
                        R = 0;
                        newCCPT.add(newRAP);
                        S = S +rap.lenghtOrNp;
                    }
                    
                    
                    
                }
                else{
                    if(rap.lenghtOrNp%2==1){
//                        3st case
                        if(rap.lenghtOrNp==1){
//                           newCCPT.add(new RunAndPhrase(1, (rap.value*(1-Pf)+R)));
                        RunAndPhrase newRAP = new RunAndPhrase(1, rap.value);
                        newCCPT.add(newRAP); 
                        }
                        else{
                             newCCPT.add(new RunAndPhrase(1, (rap.value*(1-Pf)+R)));
                        RunAndPhrase newRAP = new RunAndPhrase((rap.lenghtOrNp-1)/2, rap.value);
                        newCCPT.add(newRAP);
                        }
                        R = 0;
                        S = S +rap.lenghtOrNp;
                       
                    }
                    else{
//                        4st case
                        newCCPT.add(new RunAndPhrase(1, (rap.value*(1-Pf)+R)));
                        RunAndPhrase newRAP = new RunAndPhrase((rap.lenghtOrNp-2)/2, rap.value);
                        R = rap.value*Pf;
                        newCCPT.add(newRAP);
                        S = S +rap.lenghtOrNp;
                    }
                }
            }
            else{
                 Phrase ph = dictionary.get(rap.phraseNumber);
                 if(S%2==0){
                     if(ph.lenght%2==1){
//                         5st case
                         int phraseLenght = ((ph.lenght-3)/2)+1;
                         if(phraseLenght>0){
                             double v1 = (ph.v1*Pf)+(ph.v2*(1-Pf));
                            int phraseNmber = phraseDetection(v1, ph.v2, phraseLenght);
                            newCCPT.add(new RunAndPhrase(rap.lenghtOrNp, phraseNmber));
                         }
                         R = ph.v2 * Pf;
                         S = S + (rap.lenghtOrNp * ph.lenght) ;
                         
                     }
                     else{
//                         6st case 
                        int phraseLenght = ((ph.lenght-2)/2)+1;
                        double v1 = (ph.v1*Pf)+(ph.v2*(1-Pf));
                        int phraseNmber = phraseDetection(v1, ph.v2, phraseLenght);
                        newCCPT.add(new RunAndPhrase(rap.lenghtOrNp, phraseNmber));
                        R = 0;
                        S = S + (rap.lenghtOrNp * ph.lenght) ;
                     }
                }
                else{
                      if(ph.lenght%2==1){
//                         7st case
                         int phraseLenght = ((ph.lenght-1)/2)+1;
                         double v1 = (ph.v1*(1-Pf))+R;
                         int phraseNmber = phraseDetection(v1, ph.v2, phraseLenght);
                         newCCPT.add(new RunAndPhrase(rap.lenghtOrNp, phraseNmber));
                         R = 0;
                         S = S + (rap.lenghtOrNp * ph.lenght) ;
                         
                     }
                     else{
//                         8st case 
                        int phraseLenght = ((ph.lenght-2)/2)+1;
                        double v1 = (ph.v1*(1-Pf))+R;
                        int phraseNmber = phraseDetection(v1, ph.v2, phraseLenght);
                        newCCPT.add(new RunAndPhrase(rap.lenghtOrNp, phraseNmber));
                        R = 0;
                        S = S + (rap.lenghtOrNp * ph.lenght) ;
                     }
                     
                    
                }
                
            }
        }
        
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
    
    
       
       
       public void writeComprsedCPTInFile(String fileName ){
        final String COMMA_DELIMITER = ",";
        final String NEW_LINE_SEPARATOR = "\n";
        File file = new File(fileName+".csv");
         try{
             FileWriter fw = new FileWriter(file);
             fw.append("Run Or Phrase" + COMMA_DELIMITER);
             fw.append("value Or phraseNumber" + COMMA_DELIMITER);
             fw.append("Lenght Or np" + NEW_LINE_SEPARATOR);
             for (RunAndPhrase rap : newCCPT) {
                 if(rap.isRun){
                      fw.append("Run" + COMMA_DELIMITER);
                       fw.append(rap.value + COMMA_DELIMITER);
                        fw.append(rap.lenghtOrNp + NEW_LINE_SEPARATOR);
                 }
                 else{
                     fw.append("phrase" + COMMA_DELIMITER);
                       fw.append(rap.phraseNumber + COMMA_DELIMITER);
                        fw.append(rap.lenghtOrNp + NEW_LINE_SEPARATOR);
                 }
             }
             
             
                   fw.close();
            }
            catch(Exception e){
                System.out.println("some thing is wrong in writing file !!");
            }
               
    }
       
       
     public void writeDictionaryInFile(String fileName ){
        final String COMMA_DELIMITER = ",";
        final String NEW_LINE_SEPARATOR = "\n";
        File file = new File(fileName+".csv");
         try{
             FileWriter fw = new FileWriter(file);
             fw.append("Phrase Number" + COMMA_DELIMITER);
             fw.append("v1" + COMMA_DELIMITER);
             fw.append("v2" + COMMA_DELIMITER);
             fw.append("Lenght of phrase" + NEW_LINE_SEPARATOR);
             for (Phrase ph : dictionary) {
                fw.append(ph.phraseNumber + COMMA_DELIMITER);
                 fw.append(ph.v1 + COMMA_DELIMITER);
                  fw.append(ph.v2 + COMMA_DELIMITER);
                   fw.append(ph.lenght + NEW_LINE_SEPARATOR);
             }
             
                   fw.close();
            }
            catch(Exception e){
                System.out.println("some thing is wrong in writing file !!");
            }
               
    }
       
}
