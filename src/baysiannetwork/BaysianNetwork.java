/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baysiannetwork;

import com.sun.javafx.css.SizeUnits;
import com.sun.xml.internal.ws.util.StringUtils;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import sun.java2d.BackBufferCapsProvider;

/**
 *
 * @author arash
 */
public class BaysianNetwork {
    
    int n ;
    double [] fp = {0.9999,0.9998,0.9997,0.9996};
    double[] failprobability ;
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
                          fw.append("1" + NEW_LINE_SEPARATOR);
                      else
                          fw.append("0" + NEW_LINE_SEPARATOR);
                  }
               
               

                
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
        File input ;
        int n = 0 ;
        ArrayList<Cutset> inputCutset = new ArrayList<>();
        try {
           input = new File("input.txt");
           Scanner scanner = new Scanner(input);
           String line = scanner.nextLine() ;
           n = Integer.parseInt(line);
           System.out.println("number of component : " + n);
           line = scanner.nextLine();
           System.out.println(line);
           double[] workProbability = new double[n];
           
           
           for(int i = 0 ; i < n; i ++){
               if(line.contains(",")){
                    workProbability[i] = Double.parseDouble(line.substring(0, line.indexOf(',')));
                    line = line.substring(line.indexOf(',')+1);
               }
               else
                    workProbability[i] = Double.parseDouble(line.substring(1));
               System.out.println("work probability of component " + (i+1) + " : " + workProbability[i]);
           }
           while(scanner.hasNextLine()){
               line = scanner.nextLine();
//               int[] cutset = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray(); 
               String[] cutsetStr = line.split(",");
               int index = 0 ;
               int[] cutset = new int[cutsetStr.length];
               for(int i = 0 ; i < cutset.length ; i++){
                   try {
                   cutset[index] = Integer.parseInt(cutsetStr[i]);
                   index ++ ;
                   } catch (NumberFormatException e) {
                       System.out.println(e);
                   }
               }
                  
                  cutset = Arrays.copyOf(cutset, index);
//                   
//               }
               if(cutset.length>0){
                    inputCutset.add(new Cutset(n, cutset));
                    System.out.println("cutset " + line + " added ");
                    for (int i = 0; i < cutset.length; i++) 
                        System.out.print(cutset[i] + " , ");
                   System.out.println("------------------");
               }
                   
           }
                
        } catch (Exception e) {
            System.out.println("something in input file has a problem :| ");
        }


            new BaysianNetwork(n, inputCutset);
            
            
                       
        }

    private VariableElimination VariableEliminatin(ArrayList<Phrase> dictionary, ArrayList<RunAndPhrase> cCPT) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    }
    