//Name: Adam Stancil

//Date: 2/25/24

//Program: War

//Program Description: This program is going to simulate a running game of the card game of War, keeping track of wins, losses and ties.

//Inputs: After each hand, ask the user whether they want to continue playing (Yes or No).

//Outputs: Each hand shows 2 cards randomly selected from a standard deck of playing cards, one for Player 1 and one for Player 2. 
//It declares the higher card the winner of that hand. After the user is done playing, the game's results will display 
//wins/losses/ties for each player.

import java.util.Scanner;

public class War {
  public static void main(String[] args) {

    // Create Scanner Object
    Scanner input = new Scanner(System.in);

    // Set Number of Cards constant
    final int NUMBER_CARDS = 13;

    // Set Number of Suits constant
    final int NUMBER_SUITS = 4;

    // Create Win counter
    int player1Wins = 0;
    
    // Create Loss counter
    int player1Losses = 0;
    
    // Create Tie counter
    int ties = 0;

    // Create Tie counter
    String endChoice;

    //
    
    // Main game loop
    do {
    
      // Set Player 1 card value
      int player1Value = 1 + (int)(Math.random() * NUMBER_CARDS);

      // Set Player 1 card suit
      int player1Suit = (int)(Math.random() * NUMBER_SUITS);

      // Set Player 2 card value
      int player2Value = 1 + (int)(Math.random() * NUMBER_CARDS);

      // Set Player 2 card suit
      int player2Suit = (int)(Math.random() * NUMBER_SUITS);
    
      // Display Player 1's card value
    
      switch (player1Value) {
        case 1:  System.out.print("Player 1 picked the card: Ace of ");
      	       break;
        case 2:  System.out.print("Player 1 picked the card: Two of ");
      	       break;
        case 3:  System.out.print("Player 1 picked the card: Three of ");
      	       break;
        case 4:  System.out.print("Player 1 picked the card: Four of ");
      	       break;
        case 5:  System.out.print("Player 1 picked the card: Five of ");
      	       break;
        case 6:  System.out.print("Player 1 picked the card: Six of ");
      	       break;
        case 7:  System.out.print("Player 1 picked the card: Seven of ");
      	       break;
        case 8:  System.out.print("Player 1 picked the card: Eight of ");
      	       break;
        case 9:  System.out.print("Player 1 picked the card: Nine of ");
      	       break;
        case 10:  System.out.print("Player 1 picked the card: Ten of ");
      	       break;
        case 11:  System.out.print("Player 1 picked the card: Jack of ");
     	          break;
        case 12:  System.out.print("Player 1 picked the card: Queen of ");
      	       break;
        case 13:  System.out.print("Player 1 picked the card: King of ");
    	          break;
      }

      // Display Player 1's card suit
    
      switch (player1Suit) {
        case 0:  System.out.println("Spades");
      	       break;
        case 1:  System.out.println("Diamonds");
      	       break;
        case 2:  System.out.println("Clubs");
      	       break;
        case 3:  System.out.println("Hearts");
      	       break;
      }

      //

      // Display Player 2's card value
    
      switch (player2Value) {
        case 1:  System.out.print("Player 2 picked the card: Ace of ");
      	       break;
        case 2:  System.out.print("Player 2 picked the card: Two of ");
      	       break;
        case 3:  System.out.print("Player 2 picked the card: Three of ");
      	       break;
        case 4:  System.out.print("Player 2 picked the card: Four of ");
      	       break;
        case 5:  System.out.print("Player 2 picked the card: Five of ");
      	       break;
        case 6:  System.out.print("Player 2 picked the card: Six of ");
      	       break;
        case 7:  System.out.print("Player 2 picked the card: Seven of ");
      	       break;
        case 8:  System.out.print("Player 2 picked the card: Eight of ");
      	       break;
        case 9:  System.out.print("Player 2 picked the card: Nine of ");
      	       break;
        case 10:  System.out.print("Player 2 picked the card: Ten of ");
      	        break;
        case 11:  System.out.print("Player 2 picked the card: Jack of ");
      	        break;
        case 12:  System.out.print("Player 2 picked the card: Queen of ");
      	        break;
        case 13:  System.out.print("Player 2 picked the card: King of ");
      	        break;
      }

      // Display Player 2's card suit
    
      switch (player2Suit) {
        case 0:  System.out.println("Spades");
      	       break;
        case 1:  System.out.println("Diamonds");
      	       break;
        case 2:  System.out.println("Clubs");
      	       break;
        case 3:  System.out.println("Hearts");
      	       break;
      }

      //

      // Convert Ace to highest card for calculations

      if (player1Value==1)
        player1Value = 14;
      if (player2Value==1)
        player2Value = 14;
   
      //

      // Calculate and display winner
   
      if (player1Value>player2Value) {
        System.out.println("Player 1 wins!");
        player1Wins++;
      }
      else if (player1Value<player2Value)	{
        System.out.println("Player 2 wins!");
        player1Losses++;
      }
      else {
        System.out.println("Both Players Tie!");   
        ties++;
      }

      // Prompt the user if they want to continue
      do {
        System.out.print("Do you want to continue to play? (Yes or No): ");
        endChoice = input.nextLine();
      } while ( !(endChoice.equals("Yes")) && !(endChoice.equals("No")) );
      
    } while (endChoice.equals("Yes"));
    
    System.out.println("RESULTS:   WINS / LOSSES / TIES"); 
    System.out.println("Player 1:   " + player1Wins + "   /    " + player1Losses + "   /   " + ties); 
    System.out.println("Player 2:   " + player1Losses + "   /    " + player1Wins + "   /   " + ties); 
  }
}