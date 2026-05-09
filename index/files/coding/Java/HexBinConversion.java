/*
Name: Adam Stancil

Course: CSC2025X02 Computer Arch/Assembly SP25

Date: 2/5/25

Program: Hex/Binary/Decimal Conversion

Language: Java SE 21 (JGrasp)

Program Description: This program can convert a binary, hexadecimal, or integer into the other two number forms.
*/

import java.util.Scanner;

public class HexBinConversion {
  
  // Create a Scanner object for user input
  public static Scanner input = new Scanner(System.in);
  
  // Main method
  public static void main(String[] args) {
 
    // Display menu and get initial choice
    menuMain();
    String userChoice = getChoice();
    
    // Main menu loop
    while (!userChoice.equals("4"))
    {
      switch (userChoice) 
      {
        case "1":  {convertBinary();
                   break;}
        case "2":  {convertHex();
                   break;}
        case "3":  {convertInteger();
                   break;}
        default:   {
                     System.out.println("Incorrect input. Press enter to continue...");
                     input.nextLine();
                   }   
      }
      menuMain(); 
      userChoice = getChoice();                  
    }
    System.out.println("Exiting Program."); 
  }
  
  // Method menuMain - Print the Main Menu options 
  // Input - none | Output - Prompt to screen
  // Memory -
  public static void menuMain() {
    System.out.println("-------------------------------------");
    System.out.println("Binary/Hexadecimal/Integer Conversion");
    System.out.println("-------------------------------------");    
    System.out.println("Select a Value type to convert from:");
    System.out.println("1 | 16-Bit Binary Value");        
    System.out.println("2 | 32-Bit Hexadecimal Value");        
    System.out.println("3 | Integer Value");                
    System.out.println("4 | Quit");
    System.out.println("-------------------------------------");    
  }    
   
  // Method convertBinary - Convert a binary number into hexadecimal and integer
  // Input - binary number (string) | Output - binary, hex, and integer to screen
  // Memory -
  public static void convertBinary() {
  
    // Prompt user for input
    System.out.println("----------------------------------------------");
    System.out.println("Please input a 16-bit Binary value to convert:");
    System.out.println("----------------------------------------------");    
    
    // Declare and get binary value from user
    String binValue = getChoice();
    
    // Declare variable for int conversion value
    int intValue = 0;

    // Declare variable for hex conversion value    
    StringBuilder hexString = new StringBuilder("0000");
    
    // Declare variable for loop tracking (start at 0 for binary conversion math)
    int loopCount = 0;
    
    // Declare variable for hex character position tracking    
    int hexCount = 3;
    
    // Declare variable for hex character conversion
    int hexValue = 0;

    // Loop to parse input binary value starting at LSB and convert to int
    for (int i = binValue.length()-1; i >= 0; i--) {
      
      // Only increment values if binary flag is 1
      if (binValue.charAt(i) == '1') {
      
        // Increment decimal values by Base 2 raised to loop counter (decimal conversion)
        intValue += Math.pow(2,loopCount);
        
        // Increment values similar as above, but on a 4 character reset loop (2^0-2^3) for hex conversion
        hexValue += Math.pow(2,Math.floorMod(loopCount,4));
      }

      // Increment binary positional loop counter
      loopCount++;
    
      // Every four binary digits (loop passes) determine the hex character (hex conversion)
      if (Math.floorMod(loopCount,4) == 0) {
        
        // Assign hexadecimal character to current position in hexString based on hexValue
        switch (hexValue) {
          case 0:
          case 1:          
          case 2:
          case 3:          
          case 4:
          case 5:          
          case 6:
          case 7:          
          case 8:
          case 9: hexString.setCharAt(hexCount, Integer.toString(hexValue).charAt(0));
              break;
          case 10:  hexString.setCharAt(hexCount, 'A');
              break;
          case 11:  hexString.setCharAt(hexCount, 'B');
              break;
          case 12:  hexString.setCharAt(hexCount, 'C');
              break;
          case 13:  hexString.setCharAt(hexCount, 'D');
              break;
          case 14:  hexString.setCharAt(hexCount, 'E');
              break;
          case 15:  hexString.setCharAt(hexCount, 'F');
              break;  
          default:  System.out.println("BINARY TO HEX CONVERSION ERROR");
        }
          
        // Reset decimal value for next hex character
        hexValue = 0;

        // Decrement position counter for hex character
        hexCount--;         
      }
    }

    // Output the user input and converted values
    System.out.println("Original Binary Value: " + binValue);
    System.out.println("Converted Integer Value: " + intValue);    
    System.out.println("Converted Hexadecimal Value: " + hexString);      
  }
  
  // Method convertHex - Convert a hexadecimal number into binary and integer
  // Input - hex number (string) | Output - hex, binary, and integer to screen
  // Memory -
  public static void convertHex() {
  
    // Prompt user for input
    System.out.println("---------------------------------------------------");
    System.out.println("Please input a 32-bit Hexadecimal value to convert:");
    System.out.println("---------------------------------------------------");    
    
    // Declare and get hex value from user
    String hexString = getChoice();

    // Declare variable for int conversion value
    int intValue = 0;

    // Declare variable for binary conversion value    
    String binValue = "";

    // Declare variable for loop tracking to be used for binary conversion math
    int loopCount = 0;

    // Loop to parse input hex value starting at MSB and convert to binary
    for (int i = 0; i <= hexString.length()-1; i++) {
        
      // Assign binary substrings for each hex character (hex to binary conversion)
      switch (hexString.charAt(i)) {
        case '0':  binValue+="0000";
            break;
        case '1':  binValue+="0001";
            break; 
        case '2':  binValue+="0010";
            break; 
        case '3':  binValue+="0011";
            break; 
        case '4':  binValue+="0100";
            break;
        case '5':  binValue+="0101";
            break;
        case '6':  binValue+="0110";
            break;
        case '7':  binValue+="0111";
            break; 
        case '8':  binValue+="1000";
            break;
        case '9':  binValue+="1001";
            break;
        case 'A':  binValue+="1010";
            break;
        case 'B':  binValue+="1011";
            break;
        case 'C':  binValue+="1100";
            break;
        case 'D':  binValue+="1101";
            break; 
        case 'E':  binValue+="1110";
            break;
        case 'F':  binValue+="1111";
            break;
        default:  System.out.println("HEX TO BINARY CONVERSION ERROR");
      }
    }

    // Loop to parse converted binary value starting at LSB and convert to int
    for (int i = binValue.length()-1; i >= 0; i--) {
      
      // Only increment values if binary flag is 1
      if (binValue.charAt(i) == '1') {
      
        // Increment decimal values by Base 2 raised to loop counter (decimal conversion)
        intValue += Math.pow(2,loopCount);
      }
      
      // Increment binary positional loop counter
      loopCount++;
    }

    // Output the user input and converted values
    System.out.println("Original Hexadecimal Value: " + hexString); 
    System.out.println("Converted Binary Value: " + binValue);
    System.out.println("Converted Integer Value: " + intValue);    
 }
  
  // Method convertInteger - Convert an integer number into binary and hexadecimal
  // Input - integer number (string) | Output - integer, binary, and hex to screen
  // Memory -  
  public static void convertInteger() {
  
    // Prompt user for input
    System.out.println("-----------------------------------------");
    System.out.println("Please input an Integer value to convert:");
    System.out.println("-----------------------------------------");    
    
    // Declare and get integer value from user
    int intValue = Integer.parseInt(getChoice());

    // Declare variable for binary conversion value and call recursive function to calculate   
    String binValue = recDecimalBinary(intValue);
    
    // Decimal to Binary function doesn't ensure a 16-bit binary value, so padding is necessary
    if (binValue.length() < 16) {
      
      // Declare dummy string for padding
      String zeros = "0000000000000000";
      
      // Append extra zeros to ensure 16 characters
      binValue = zeros.substring(binValue.length()) + binValue;
    }

    // Declare variable for hex conversion value    
    StringBuilder hexString = new StringBuilder("0000");
    
    // Declare variable for loop tracking (start at 0 for binary conversion math)
    int loopCount = 0;
    
    // Declare variable for hex character position tracking    
    int hexCount = 3;
    
    // Declare variable for hex character conversion
    int hexValue = 0;

    // Loop to parse input binary value starting at LSB and convert to int
    for (int i = binValue.length()-1; i >= 0; i--) {
      
      // Only increment values if binary flag is 1
      if (binValue.charAt(i) == '1') {
       
        // Increment values similar as above, but on a 4 character reset loop (2^0-2^3) for hex conversion
        hexValue += Math.pow(2,Math.floorMod(loopCount,4));
      }

      // Increment binary positional loop counter
      loopCount++;
    
      // Every four binary digits (loop passes) determine the hex character (hex conversion)
      if (Math.floorMod(loopCount,4) == 0) {

        // Assign hexadecimal character to current position in hexString based on hexValue
        switch (hexValue) {
          case 0:
          case 1:          
          case 2:
          case 3:          
          case 4:
          case 5:          
          case 6:
          case 7:          
          case 8:
          case 9: hexString.setCharAt(hexCount, Integer.toString(hexValue).charAt(0));
              break;
          case 10:  hexString.setCharAt(hexCount, 'A');
              break;
          case 11:  hexString.setCharAt(hexCount, 'B');
              break;
          case 12:  hexString.setCharAt(hexCount, 'C');
              break;
          case 13:  hexString.setCharAt(hexCount, 'D');
              break;
          case 14:  hexString.setCharAt(hexCount, 'E');
              break;
          case 15:  hexString.setCharAt(hexCount, 'F');
              break;  
          default:  System.out.println("BINARY TO HEX CONVERSION ERROR");
        }
          
        // Reset decimal value for next hex character
        hexValue = 0;

        // Decrement position counter for hex character
        hexCount--;         
      }
    }
 
    // Output the user input and converted values
    System.out.println("Original Integer Value: " + intValue); 
    System.out.println("Converted Binary Value: " + binValue);
    System.out.println("Converted Hexadecimal Value: " + hexString);     
  }
  
  // Method recDecimalBinary - Recursively convert decimal to binary
  // Input - integer number (int) | Output - binary number returned (string)
  // Memory -    
  public static String recDecimalBinary(int n) {

    // Base case  
    if (n == 0) {
      return "";
      
    // Recursive call 
    } else {
      return recDecimalBinary(n / 2) + (n % 2);
    }
  }
   
  // Method getChoice - Get user's choice for menus 
  // Input - user's choice (string) | Output - Prompt to screen
  // Memory -  
  public static String getChoice() {
    System.out.print("Input choice and press enter to continue: ");    
    String choice = input.nextLine();
    return choice;
  }
}