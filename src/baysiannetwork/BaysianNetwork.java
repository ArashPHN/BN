/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baysiannetwork;

import com.sun.javafx.css.SizeUnits;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import sun.java2d.BackBufferCapsProvider;

/**
 *
 * @author arash
 */
public class BaysianNetwork {
    
    int n ;
    double [] fp = {0.9999,0.9998,0.9997,0.9996};
    
    ArrayList<Cutset> minCutset = new ArrayList<>();
    
    
    public boolean isfailed(boolean[] input){
        for(Cutset cut : minCutset)
            if(cut.isInCutset(input))
                return true ;
        return false ;
        
    }
    
    public BaysianNetwork(int n , ArrayList<Cutset> inputCutset){
        boolean[][] inputs = new boolean[(int)Math.pow(2, n)][n];
        boolean[] system = new  boolean[(int)Math.pow(2, n)];
        this.minCutset = inputCutset ;
        for (int i = 0; i < (int)Math.pow(2, n); i++) {
            String binary = Integer.toBinaryString(i);
            int different = n -binary.length();
            for (int j = 0; j < n; j++) {
                if(j<different){
                    System.out.println((int)Math.pow(2, n));
                    System.out.println(i + "  ,  "+ j);
                     inputs[i][j] = false ;
                }
               
                else{
                    if(binary.charAt(j-different) == '1')
                    inputs[i][j] = true ;
                    else
                    inputs[i][j] = false ;
                }
                
            }
        }
    
    
    ///////////////////////////////////////
    
    
     final String COMMA_DELIMITER = ",";
            final String NEW_LINE_SEPARATOR = "\n";
            File file = new File("BN.csv");
            try{
               FileWriter fw = new FileWriter(file);
                for (int i = 1; i < n; i++) 
                    fw.append("component " + i + COMMA_DELIMITER);
                
                fw.append("component " + n + COMMA_DELIMITER);
                fw.append("sys"+NEW_LINE_SEPARATOR);
                
                
                  for (int i = 0; i <(int)Math.pow(2, n); i++) {
                      boolean[] temp = new boolean[n];
                      for (int j = 0; j < n; j++) {
                          temp[j] = inputs[i][j];
                          if(inputs[i][j])
                               fw.append("1" + COMMA_DELIMITER);
                          else
                              fw.append("0" + COMMA_DELIMITER);
                      }
                      system[i] = !isfailed(temp);
                      if(system[i])
                          fw.append("0" + NEW_LINE_SEPARATOR);
                      else
                          fw.append("1" + NEW_LINE_SEPARATOR);
                  }
               
               
//                fw.append("Name" + COMMA_DELIMITER + "SignalProbability" + COMMA_DELIMITER + "EPP" +NEW_LINE_SEPARATOR);
//                 for (Gate g : gate) {
//                    fw.append(g.getName().replaceAll(",", "-") + COMMA_DELIMITER);
//                    fw.append(g.getSignalProbability()+ COMMA_DELIMITER);
//                    fw.append(g.getEpp()+NEW_LINE_SEPARATOR);
//                }
                
    	        fw.close();


    	}catch(Exception e){
    		          System.out.println("cant write in file !!");
    	}
            /////////////////////////////////////
    
    
    CompresingTable CCPTSys = new CompresingTable(system);
    
     
//    VariableElimination ve = new VariableEliminatin(CCPTSys.dictionary , CCPTSys.cCPT , {0.9999,0.9998,0.9997,0.9996});
        VariableElimination ve = new VariableElimination(CCPTSys.dictionary , CCPTSys.cCPT , fp[fp.length-1]);
        ve.writeComprsedCPTInFile("CPT_after_elimination_component_4");
        
        ve.writeDictionaryInFile("dictionary_after_elimination_component_4");
    }
    /**
     * @param args the command line arguments
     * 
     * 
     * 
     */
    
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter n :");
        int n =0;
        try {
           n = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Enter a int !!!!!!");
            System.exit(0);
        }
//        System.out.println("e : Entering CutSet is ended");
//        System.out.println("n : Enter a new CutSet");
        boolean flag = true ;
        ArrayList<Cutset> inputCutset = new ArrayList<>();
//        while(flag){
//            boolean flag2 = true;
//            ArrayList<Integer> set = new ArrayList<>();
//            while(flag2){
//                String s = sc.nextLine();
//                if(s=="n"){
//                      flag2 = false ;
//                      inputCutset.add(new Cutset(n, set));
//                      break ;
//                }
//                  
//                else if(s=="e"){
//                    flag = false ;
//                    flag2 = false ;
//                    if(!set.isEmpty())
//                        inputCutset.add(new Cutset(n, set));
//                   break ;
//                }
//                else{
//                    try {
//                      int  t = Integer.parseInt(s);
////                        System.out.println(Integer.toBinaryString(t));
//                      if(set.contains(t))
//                          System.out.println(t + " is contained in set ! ");
//                      else if (t >n)
//                            System.out.println(" this component isnt exist");
//                      
//                      else
//                          set.add(t);
//                    } catch (Exception e) {
//                        System.out.println("Enter a int !!!!!");
//                    }
//
//                     
//                }
//               
//            }

    int[] cutset1 = { 4} ;
    int[] cutset2 = {3};
    int[] cutset3 = {1,2};
    inputCutset.add(new Cutset(n, cutset1));
    inputCutset.add(new Cutset(n, cutset2));
    inputCutset.add(new Cutset(n, cutset3));
    
            
            new BaysianNetwork(n, inputCutset);
            
            
                       
        }

    private VariableElimination VariableEliminatin(ArrayList<Phrase> dictionary, ArrayList<RunAndPhrase> cCPT) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    }
    