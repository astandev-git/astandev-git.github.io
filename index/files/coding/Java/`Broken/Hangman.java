/*
Name: Adam Stancil

Date: 5/7/24

Program: Hangman

Program Description: This program is a two player hangman game. The first player will enter a word at least 5 letters long 
and the second player will guess letters until either the word is fully revealed or their attempts (7) are used up.

Inputs: First, a word at least 5 letters long. Then 1 letter at a time until the word has been revealed or 7 unsuccessful
guesses have been made.

Outputs: A status of the word (known and unknown letters) after each guess, the number of guesses left, and a win message
if successful.
*/

import java.util.Scanner;

public class Hangman 
{
  
  // Main method - 
  public static void main(String[] args) 
  {
  
    // Create Scanner Object
    Scanner input = new Scanner(System.in);
    
    // Set Max Guess size
    final int HUNG = 7;
   
    // Create Player 2 letter guess array
    ArrayList<char> guessArray = new ArrayList<char>();

    // Create Player 1 word choice variable
    String target;
    
    // Create program end flag variable
    String menuChoice;
    
    // Loop to display menu and get user choice 
    do
    {
      playRound();
      System.out.print("Would you like to play again? (y/n): ");
      menuChoice = input.nextLine();
      if ( !(menuChoice=="y") && !(menuChoice=="Y") && !(menuChoice=="n") && !(menuChoice=="N") )
        System.out.println("***INVALID INPUT***");
    }
    while ( !(menuChoice=="n") && !(menuChoice=="N") );
  }
  
  // Method playRound - Start a game of Hangman
  public static int playRound() 
  {
    target = getTarget();
   // while (hang < 8) 
 //   {
      
//}
//    printBoard(target,guessArray);
//guessArray.add(getGuess());
    
    
  }  
    
  // Method getTarget - 
  public static String getTarget()
  {
    do 
    {
      System.out.print("Enter the target word (at least 5 letters long): ");
      string target = input.nextLine();
      if ( target.length() < 5 )
        System.out.println("***INVALID INPUT***");
    } while ( target.length() < 5 );
    return target;
  }  
  
  // Method getGuess - 
  public static char getGuess()
  {
    System.out.print("Enter a letter: ");
      char guess = input.nextChar();
    return guess;
  }
}   
  // Method printBoard -
 // public static void printBoard(String target, ArrayList<char> guessArray)
//  {
  //  for (char counter: guess)
//      if (target[counter]==guess[] )
    
  /*  
  // Method writeData - 
  public static void writeData(String target, ArrayList<char> guessArray) 
  {
    // Create Output File
    java.io.File file = new java.io.File("Hangman"); 

    // Create Output Object
    java.io.PrintWriter output = new java.io.PrintWriter(file);
  
    // Outpute target word and guess list to file
    for (int counter = 0; counter < guessArray.size(); counter++)
      output.printf((target) + "%c\n",guessArray.get(counter));    
  }
      
}*/
