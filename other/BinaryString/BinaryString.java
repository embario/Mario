import java.util.Scanner;


public class BinaryString {
        
        
        public String encryptedString;
        public static Scanner scan;
        
        public BinaryString(String estring){
                
                encryptedString = estring;
        }
        
        public String getEncryptedString(){ return encryptedString;}
        public void setEncryptedString(String estring){ encryptedString = estring;}
        
        public static void main(String[] args) {
                
                //TODO: Bug in Code: Negative numbers being printed....
                scan = new Scanner(System.in);
                System.out.print("Enter an Encrypted String (String consisting of 0's, 1's, 2's, or 3's): ");
                
                String input = scan.nextLine();
                BinaryString bc = new BinaryString(input);
                boolean quit = false;
                
                while(!quit){
                        
                        System.out.println("What do you want to do?");
                        System.out.println();
                        System.out.println(" 1) Decode String into a Binary String");
                        System.out.println(" 2) Check Encrypted String");
                        System.out.println(" 3) Change Encrypted String");
                        System.out.println(" 4) Quit");
                        
                        int number = scan.nextInt();
                        
                        if(number == 1){
                                
                                String [] array = decode(bc.getEncryptedString());
                                
                                System.out.println("Binary String Representation of Encrypted String: \""
                                                + bc.getEncryptedString()+ "\" ");
                                for(String s: array)
                                        System.out.print("("+s+")");
                                System.out.println();
                        }
                        
                        else if(number == 2)
                                System.out.println("Your Encrypted String is: " + bc.getEncryptedString());
                        
                        else if(number == 3){
                                
                                System.out.print("Enter an Encrypted String (String consisting of 0's, 1's, 2's, or 3's): ");
                                String s = scan.next();
                                bc.setEncryptedString(s);
                        }
                        else if(number == 4){
                                System.out.println("Goodbye");
                                System.exit(0);
                        }       
                }

        }//end main

        /** This method accepts an encrypted String and attempts to
         * recover its binary string representation. It is imperative that 
         * the encrypted String only contains 0, 1, 2, or 3. Otherwise,
         * this method cannot its binary representation.<p>
         * 
         * The algorithm is based upon assuming two cases - either the 
         * 1st character of the determined binary string is 0 or 1. <p>
         * 
         * Given an encrypted String Q, the relationship between Q and it's binary
         * representation P (say P is a String) is:<p>
         * 
         * Q{i} = P{i-1} + P{i} + P{i+1}<p>
         * 
         * where i is an index for functional iteration. A character in Q at index i
         * is determined by adding its neighbors to itself. This equation is manipulated
         * to determine P{i} and aggregating all of the characters in P at i through
         * iteration.<p>
         * 
         * @param bstring
         * @return
         */
        public static String[] decode(String estring){
                
                //Array to return
                String [] answers = {"",""};
                
                //Base Case
                int base = 0;
                
                /** Iterating twice for two cases
                 * (i.e. First digit in Binary String is 0 or 1) **/
                for(int k = 0; k < answers.length; k++){
                        
                        String p = "" + base;
                        
                        /**Iterating through the Encrypted String with
                         * respect to index position **/
                        for(int i = 0; i < estring.length()-1; i++){
                                
                                //One value at p{i} to be determined...
                                int pValue = 0;
                                
                                //Index of Encrypted String at i
                                int eIndex = Integer.parseInt(estring.charAt(i)+"");

                                //Initial Index of Binary String at i
                                int pIndex = base;
                                
                                //Testing for Index of Binary String at (i)
                                try{ pIndex = Integer.parseInt(p.charAt(i)+"");}
                                catch (Exception ex){ pIndex = base;}
                                
                                //Initial index of Binary String at (i-1)
                                int pLowIndex = 0;
                                
                                //Testing for Index of Binary String at (i-1)
                                try{ pLowIndex = Integer.parseInt(p.charAt(i-1)+"");}
                                catch (Exception e){ pLowIndex = 0;}
                                
                                //Initial index of Binary String at (i+1)
                                int pHighIndex = 0;
                                
                                //Testing for Index of Binary String at (i+1)
                                try{ pHighIndex = Integer.parseInt(p.charAt(i+1)+"");}
                                catch (Exception e){ pHighIndex = 0;}
                                
                                /* If we have numbers greater than 1
                                 * (i.e. NOT a Binary String Representation */
                                if(pIndex > 1 || pLowIndex > 1)
                                        answers[k] = "NONE";
                                
                                //Aggregation for one value at p{i}
                                pValue = (eIndex - pIndex - pLowIndex);
                                if(pValue < 0 || pValue > 1)
                                        answers[k] = "NONE";
                                
                                
                                /** ULTIMATE TEST **/
                                //If the sum of p's neighbors and itself does not
                                //equal our value of encrypted string at i...
                                if(!(eIndex == pLowIndex + pIndex + pHighIndex))
                                        answers[k] = "NONE";
                                
                                //Concatenated to Binary String
                                if(!answers[k].equals("NONE"))
                                        answers[k] = (p += pValue);
                                else
                                        break;
                                
                        }//end inner loop
                        
                        base++;
        
                }// end outer loop
                
                return answers;
        }

}