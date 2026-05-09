//Name: Adam Stancil

//Date: 3/24/24

//Program: Connect 4

/*Program Description: This program is a two-player connect four game. Each player takes turns dropping their token 
(either an X or an O) into an eight-row, eight-column game board. The first player to connect four of their tokens
in a row, column, or diagonal wins.*/

//Inputs: Players alternate inputs of which column (1-8) to drop their token into.

//Outputs: After each player's turn, a picture of the board. If noone has won, the other player takes their turn.

import java.util.Scanner;

public class Connect4 
{

  // Create Scanner Object
  Scanner input = new Scanner(System.in);
  
  // Main method
  public static void main(String[] args) 
  {
  
    // Print instructions to users
    System.out.println("Welcome to CONNECT FOUR! Here are the rules: "); 
    System.out.println(""); 
    System.out.println("The first player to connect four of their tokens");     
    System.out.println("(X or O) in a row, column, or diagonal WINS!");     
    System.out.println(""); 
       
    // Run game method    
    connectFour();    
    
  }
   
  // Connect Four game method
  public static void connectFour() 
  {
  
    // Declare game board size constants
    final int ROWS = 8;
    final int COLUMNS = 8;
    
    // Declare game board array
    char[][] myArray = new char[ROWS][COLUMNS];
  
    // Create Scanner Object
    Scanner input = new Scanner(System.in);
    
    // Declare game round counter variable
    int round = 0;
    
    // Declare user column input variable
    int dropColumn = 0;
    
    // Declare end game flag
    boolean win = true;
    
    // Declare token
    char token;
    
    // Initial game board display
    printBoard(myArray);
    
    // Main game loop
    do 
    {
    
      // Increment round counter
      round++;
      
      // Determine player turn, take column input, and call token drop method
      if (round%2==1) 
      {
        token = 'X';
        System.out.print("Drop an X token at column (1-8): ");
        dropColumn = (int) input.nextInt();    
        dropToken(myArray,dropColumn-1,token);          
      }  
      else 
      {
        token = 'O';
        System.out.print("Drop an O token at column (1-8): ");
        dropColumn = (int) input.nextInt();            
        dropToken(myArray,dropColumn-1,token);   
      }
    
    // Display board  
    printBoard(myArray);
    
    // Determine winner and print message
    win = checkWin(myArray,token);
    if (win)
    {
      System.out.print("");
      System.out.print("Player " + token + " wins!");
    }
    
    } while (!win);      
  
  }
  
  // Check win method
  public static boolean checkWin(char[][] my, char token)
  {

    // Declare win counter variable 
    int win = 0;
    
    // Declare game win condition (4) constant
    final int WINCOUNT = 4;
 
    // Check horizontal win
    for (int row = 0; row < my.length; row++) 
    {
   
      win = 0;
      for (int column = 0; column < my[row].length; column++) 
      {
        if (my[row][column]==token)
          win++;
        else
          win = 0;
        
        if (win==WINCOUNT)
            return true;
      }
    
    }
    
    // Check vertical win
    for (int column = 0; column < my[0].length; column++)
    {
    
      win = 0;
      for (int row = 0; row < my.length; row++)  
      {
        if (my[row][column]==token)
          win++;
        else
          win = 0;

        if (win==WINCOUNT)
          return true;
      }  
    
    }
  
    // Check diagonal win 1
    for (int row=3; row<my.length; row++)
      for (int column=0; column<my[row].length-3; column++)
        if (my[row][column] == token && my[row-1][column+1] == token && my[row-2][column+2] == token && my[row-3][column+3] == token)
          return true;
  
    // Check diagonal win 2
    for (int row=3; row<my.length; row++)
      for (int column=3; column<my[row].length; column++)
        if (my[row][column] == token && my[row-1][column-1] == token && my[row-2][column-2] == token && my[row-3][column-3] == token)
          return true;  
    
    return false;
  
  }
  
  // Drop token method
  public static void dropToken(char[][] my, int column, char token) 
  {
    
    // Drop token in column 
    for (int row = my.length-1; row > 0; row--) 
      if (my[row][column]==0) 
      {
        my[row][column]=token;
        return;
      }
  
  }
  
  // Display board method
  public static void printBoard(char[][] my) 
  {
    
    // Print game board
    for (int row = 0; row < my.length; row++) 
    {
      for (int column = 0; column < my[row].length; column++) 
        System.out.print("|" + my[row][column]);
      System.out.println("|");      
    }                   
    
    System.out.println("_________________");
    System.out.println(" 1 2 3 4 5 6 7 8");
     
  }     
  
}
