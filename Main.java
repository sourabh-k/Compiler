package com.company;

import java.util.*;
/**
 * Created by Sourabh on 8-Feb-17.
 * Main is oriented to remove left recursion from the productions both immediate and non-immediate
 * other classes does operations similar as of their name and are invoked by main when required.
 */

public class Main {

    public static void main(String[] args) {
	// write your code here
        int i,j,k,n;
        ArrayList<String> production =new ArrayList<String>();
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        ArrayList<String> charList = new ArrayList<String>();
        System.out.println("Enter the number of production");
        Scanner scanner =new Scanner(System.in);
        n= scanner.nextInt();
        for(i=0;i<n;i++)
        {
            production.add(scanner.next());
            ArrayList<String> tempProduction = new ArrayList<String>();
            String current = production.get(i);
             String c= current.charAt(0)+"";
            charList.add(c);
            StringBuilder stringBuilder = new StringBuilder();
            j=3;
            while(j<current.length())
            {
                if(current.charAt(j) == '|')
                {
                    tempProduction.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
                else
                {
                    stringBuilder.append(current.charAt(j));
                }
                j++;
            }
            tempProduction.add(stringBuilder.toString());
            map.put(c,tempProduction);

        }
        LeftFactoring Lf = new LeftFactoring(map,charList);
        Lf.leftFactor();
        //System.out.println("return");
        map = Lf.getMap();
        charList = Lf.getCharList();
        //Non immediate
       for(i=0;i<charList.size();i++)
       {
           String ai = charList.get(i);
         // System.out.println("ai "+ai);
           for (j = 0; j < i; j++)
           {
               String aj = charList.get(j);
              // System.out.println("aj " + aj);
               List<String> tempProductionAi = map.get(ai);
               int len= tempProductionAi.size();
               List<String> temp = new ArrayList<>();
               for (k = 0; k < len; k++)
               {
                  /* System.out.println("temp"+tempProductionAi.get(k));*/
                   if (aj.equals(tempProductionAi.get(k).charAt(0)+"")) {
                       String gama = tempProductionAi.get(k);
                       gama = gama.substring(1, gama.length());
                       List<String> tempProductionAj = map.get(aj);
                       temp.add(tempProductionAj.get(0) + gama);
                       for (int ii = 1; ii < tempProductionAj.size(); ii++) {
                           temp.add(tempProductionAj.get(ii) + gama);
                       }
                   }
                   else
                   {
                       temp.add(tempProductionAi.get(k));
                   }
               }
               map.remove(ai);
               map.put(ai, temp);
           }
       }
       int len= charList.size();
       for(i=0;i<len;i++)
       {
           ArrayList<String> beta = new ArrayList<>();
           ArrayList<String> alpha = new ArrayList<>();
           char o;
           String ai = charList.get(i);
           List<String> productionAi = map.get(ai);
           for(j=0;j<productionAi.size();j++)
           {
               String temp = productionAi.get(j);
               if(ai.equals(temp.charAt(0)+""))
               {
                     alpha.add(temp.substring(1,temp.length())+ ai+"'");
               }
               else
               {
                   beta.add(temp+ai+"'");
               }

           }
           if(beta.size() <productionAi.size())
           {
               alpha.add("e");
               charList.add(ai+"'");
               map.put(ai+"'",alpha);
               map.remove(ai);
               map.put(ai,beta);
           }
       }
       System.out.println("Result");
        for(i=0;i<charList.size();i++)
        {
            System.out.print(charList.get(i)+"-> ");
            List<String> out = map.get(charList.get(i));
            for(j=0;j< out.size();j++)
            {
                System.out.print(out.get(j));
                if(j<out.size()-1)
                    System.out.print(" | ");
            }
            System.out.println();
        }
        FirstSet Fs = new FirstSet(map,charList);

    }
}
