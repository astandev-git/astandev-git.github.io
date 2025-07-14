//Name: Adam Stancil

//Date: 3/10/24

//Program: Craps

/*Program Description: This program is a simple version of the dice game Craps. It will prompt the user for their name and will then
roll two six-sided dice and check the sum. According to the rules of Craps, a 2, 3, or 12 is a loss, while a 7 or 11 is a win. If 
the sum is another value (i.e., 4, 5, 6, 8, 9, or 10), a point is established. In this case the dice are rolled until either a 7 or 
the same point value is rolled. If 7 is rolled, it's a loss. Otherwise, it's a win. Rolls of 2, 3 or 12 do not cause you to lose
if a point has been established. If the point is an even number and the roll is doubles matching the point (3-3, 4-4 or 5-5), the 
win is special. The player will be prompted to stop after each win or loss.*/

//Inputs: User's name and whether they want to continue after each win or loss.

//Outputs: Visual of each dice roll and if the roll won/lost/pointed/no result. Name of user and game statistics at finish
//(game count, wins, wins with doubles, and losses). 

import java.util.Scanner;

public class Craps {

  // Main method
  public static void main(String[] args) {
  
  //Call play craps method
  playCraps();
  
  }
  
  // Play craps method  
  public static void playCraps() {
  
    // Declare Scanner Object
    Scanner input = new Scanner(System.in);

    // Declare dice face value constant
    final int DICE = 6;
    
    // Declare craps loss constants
    final int CRAP1 = 2;
    final int CRAP2 = 3;
    final int CRAP3 = 12;        
    
    // Declare natural win constants
    final int NATURAL1 = 7;
    final int NATURAL2 = 11;
  
    // Declare win/loss/double/game count statistic variables
    int win = 0;
    int loss = 0; 
    int win2X = 0;
    int count = 0;
 
    // Create loop end flag
    String endChoice; 
        
    // Prompt user for their name 
    System.out.print("Please enter your name: ");
    String user = input.nextLine();
  
    // Main craps game loop
    do {
      
      // Initial roll of two six-sided dice 
      int dice1 = rollDice(DICE);
      int dice2 = rollDice(DICE);  
      
      // Calculate dice sum
      int diceSum = dice1 + dice2;
      
      // Print dice visual results
      System.out.println("Your two dice are...");
      System.out.println();
      dicePrint(dice1);    
      dicePrint(dice2);    
      
      // Display roll sum
      System.out.println("You rolled a: " + diceSum + ".");      
      
      // Determine initial crap/natural/point loss/win/progression, advance counters, & print messages
      switch (diceSum) {
        case CRAP1:
        case CRAP2:
        case CRAP3:     loss++;
                        count++;
                        System.out.println("You Crapped out! You lose!"); 
          break;
        case NATURAL1:
        case NATURAL2:  win++;
                        count++;
                        System.out.println("You rolled a Natural! You win!");         
          break;
        default:        int point = diceSum;
                        System.out.println("That sets the point. Your next two dice are...");
                        System.out.println();                         
                        do {
                          
                          // Roll dice
                          dice1 = rollDice(DICE);
                          dice2 = rollDice(DICE); 
                          
                          // Calculate dice sum
                          diceSum = dice1 + dice2;
                          
                          // Print dice visual results
                          dicePrint(dice1);    
                          dicePrint(dice2);            
   
                          // Display roll sum
                          System.out.println("You rolled a: " + diceSum + ".");
                          
                          // Determine win2X/loss/win, advance counters, & print messages
                          if ((dice1==dice2) && (diceSum==point)) {
                            win2X++;
                            count++;
                            System.out.println("You rolled doubles matching the point! You WIN BIG!!!"); 
                          }
                          else
                            if (diceSum==NATURAL1) {
                              loss++;
                              count++;
                              System.out.println("You Crapped out! You lose!"); 
                            }
                            else
                              if (diceSum==point) {
                                win++;
                                count++; 
                                System.out.println("You win!"); 
                              }
                              else
                                System.out.println("No result. Your next two dice are..."); 
                        } while ((diceSum!=NATURAL1) && (diceSum!=point));
      }   
      
      // Prompt the user if they want to continue playing craps
      do {    
        System.out.print("Do you want to keep playing? (Y or N): ");
        endChoice = input.nextLine();   
      } while ( !(endChoice.equals("Y")) && !(endChoice.equals("N")) );

    } while (endChoice.equals("Y"));    
    
    // Print user name and game statistics
    System.out.println();
    System.out.println("Well played, " + user + ". You played " + count + " games.");
    System.out.println("Here are your game statistics:");    
    System.out.println("WINS: " + win + " | WINS W/ DOUBLES: " + win2X + " | LOSSES: " + loss);    
  }    
   
  // Roll dice method
  public static int rollDice(int dice) {
    
    // Roll random number based off dice face value
    int result = 1 + (int)(Math.random() * (dice-1));
    return result;
  } 

  // Dice visual print method
  public static void dicePrint(int dice) {
 
    System.out.println(dice);
    System.out.println();

    // Determine dice face value from pass and print corresponding visual
    switch (dice) {
      case 1:  System.out.println("______________");
               System.out.println("|            |");
               System.out.println("|            |");
               System.out.println("|     *      |");
               System.out.println("|            |");
               System.out.println("|____________|");
               System.out.println();
    	       break;
      case 2:  System.out.println("______________");
               System.out.println("|            |");
               System.out.println("|            |");
               System.out.println("|   *   *    |");
               System.out.println("|            |");
               System.out.println("|____________|");
               System.out.println();
    	       break;
      case 3:  System.out.println("______________");
               System.out.println("|            |");
               System.out.println("|   *        |");
               System.out.println("|     *      |");
               System.out.println("|       *    |");
               System.out.println("|____________|");
               System.out.println();
    	       break;
      case 4:  System.out.println("______________");
               System.out.println("|            |");
               System.out.println("|   *   *    |");
               System.out.println("|            |");
               System.out.println("|   *   *    |");
               System.out.println("|____________|");
               System.out.println();
    	       break;
      case 5:  System.out.println("______________");
               System.out.println("|            |");
               System.out.println("|   *   *    |");
               System.out.println("|     *      |");
               System.out.println("|   *   *    |");
               System.out.println("|____________|");
               System.out.println();               
    	       break;
      case 6:  System.out.println("______________");
               System.out.println("|            |");
               System.out.println("|   *   *    |");
               System.out.println("|   *   *    |");
               System.out.println("|   *   *    |");
               System.out.println("|____________|");
               System.out.println();               
    	       break;
    }
  }   
}