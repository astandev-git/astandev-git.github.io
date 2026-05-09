/*
Name: Adam Stancil

Date: 5/7/24

Program: TempConversion

Program Description: This program will convert temperature between Fahrenheit, Celsius, or Kelvin depending on the user's 
selection.

Inputs: Menu choices for which type of temperature to convert, to display data, or quit the program (in the form of a number
1-5. Then an input temperature for converting into the other 2 types when those choices are picked.

Outputs: A prompt displaying which type of temperature to input for conversion when choosing that type of temperature to 
convert from the main menu. A table of all conversions made during this session when choosing the display data option from
the main menu.
*/

import java.util.ArrayList;

import java.util.Scanner;

public class TempConversion 
{
  
  // Create Scanner Object
  static Scanner input = new Scanner(System.in);
  
  // Main method - 
  public static void main(String[] args) 
  {
  
    // Create Fahrenheit array
    ArrayList<Double> fArray = new ArrayList<Double>();

    // Create Celsius array
    ArrayList<Double> cArray = new ArrayList<Double>();
    
    // Create Kelvin array
    ArrayList<Double> kArray = new ArrayList<Double>();
    
    // Create program end condition variable
    int menuChoice;
    
    // Create temporary variable for conversion math
    double workTemp;

    // Loop for menu to get temperature from user and do conversions
    do
    {
      menuChoice = showMenu();
      switch (menuChoice) {
        case 1:   workTemp = getTemperature("Fahrenheit"); 
                  fArray.add(workTemp);
                  cArray.add(calcCelsius(workTemp));
                  kArray.add(calcKelvin(workTemp)); 
            break;
        case 2:   workTemp = getTemperature("Celsius");   
                  cArray.add(workTemp);
                  fArray.add(calcFahrenheit(workTemp));
                  workTemp=calcFahrenheit(workTemp);
                  kArray.add(calcKelvin(workTemp)); 
            break;
        case 3:   workTemp = getTemperature("Fahrenheit");
                  fArray.add(workTemp);
                  cArray.add(calcCelsius(workTemp));
                  kArray.add(calcKelvin(workTemp));                   
            break;
        case 4:   displayData(fArray,cArray,kArray);
    	      break;
      }
    }
    while (!(menuChoice==5));
   
  }
  
  // Method showMenu - Display main menu to user and get selection. 
  public static int showMenu() 
  {
    // Create user menu choice variable
    int userChoice;
  
    // Loop to display menu and get user choice 
    do 
    {
      System.out.println("1. Convert Fahrenheit to Celsius");
      System.out.println("2. Convert Celsius to Fahrenheit");
      System.out.println("3. Convert Fahrenheit to Kelvin");
      System.out.println("4. Display Data");
      System.out.println("5. Quit Program");
      System.out.print("Enter your selection (1 - 5): ");
      userChoice = input.nextInt();
      if ( !(userChoice==1) && !(userChoice==2) && !(userChoice==3) && !(userChoice==4) && !(userChoice==5) )
        System.out.println("***INVALID INPUT***" + (userChoice));
    }
    while ( !(userChoice==1) && !(userChoice==2) && !(userChoice==3) && !(userChoice==4) && !(userChoice==5) );

    return userChoice;
  }

  // Method getTemperature - Get temperature for conversion from user (using input parameter as user prompt)
  public static double getTemperature(String tempType) 
  {
    System.out.print("Enter the " + (tempType) + " temperature for conversion: ");
      double temp = input.nextDouble();
    return temp;
  }

  // Method calcCelsius - Calculate and return Celsius temperature using parameter temperature
  public static double calcCelsius(double temp) 
  {
    double newTemp = ((5.0/9) * (temp - 32));
    return newTemp;
  }

  // Method calcFahrenheit - Calculate and return Fahrenheit temperature using parameter temperature 
  public static double calcFahrenheit(double temp) 
  {
    double newTemp = ((temp * (9.0/5)) + 32);
    return newTemp;
  }

  // Method calcKelvin - Calculate and return Kelvin temperature using parameter temperature
  public static double calcKelvin(double temp) 
  {
    double newTemp = ( (5.0/9) * (temp - 32) + 273.15);
    return newTemp;
  }
  
  // Method displayData - Print table with header and contents of all 3 temperature arrays
  public static void displayData(ArrayList<Double> fArray,ArrayList<Double> cArray,ArrayList<Double> kArray) 
  {
    System.out.println("Temperature Conversion History");
    System.out.println("------------------------------");
    System.out.println("Fahrenheit| Celsius | Kelvin");    
    for (int counter = 0; counter < fArray.size(); counter++)
      System.out.printf("%8.2f%10.2f%10.2f\n",fArray.get(counter),cArray.get(counter),kArray.get(counter));    
  }
      
}