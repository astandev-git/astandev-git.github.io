/*
Name: Adam Stancil

Date: 10/21/24

Program: CIMS

Program Description: This program is a basic concrete inventory management system. See Readme file.
*/

import java.util.*;
import java.io.*;
import java.sql.Timestamp;

public class CIMS 
{

  // Create Scanner Object
  public static Scanner input = new Scanner(System.in);

  ////////////////// MAIN VARIABLES - INVENTORY, RECIPE, ORDER, ORDERHISTORY, LAMBDA //////////////////

  // Create Main inventory file
  static ArrayList<Material> inventory = new ArrayList<>();
  
  // Create Main recipe file
  static ArrayList<Recipe> batches = new ArrayList<>();
   
  // Create Current order file
  static Order currentOrder = new Order(); 
  
  // Create Order History file
  static ArrayList<Order> orderHistory = new ArrayList<>(); 
  
  // Create Static variables for use in lambda expression loops
  static boolean flag;
  static String nameLambda;
  static int intLambda;

  // Main method - Concrete Management System 
  public static void main(String[] args) 
  {
    // Read data files into main variables
    System.out.println("-----------------------------");
    System.out.println("Data Files Loaded - Inventory, Batches, OrderHistory");
    System.out.println("-----------------------------");
    readFiles();
    
    // Display menu and get initial choice
    menuMain();
    String userChoice = getChoice();
    
    // Main menu loop
    while (!userChoice.equals("5"))
    {
      switch (userChoice) 
      {
        case "1":  {loopInventory();
                   break;}
        case "2":  {loopBatch();
                   break;}
        case "3":  {loopOrder();
                   break;}
        case "4":  {writeFiles(); 
                   break;}
        case "5":  {System.out.println("Exiting Program.");
                   writeFiles(); 
                   break;}
        default:   {
                     System.out.println("Incorrect input. Press enter to continue...");
                     input.nextLine();
                   }   
      }
      menuMain(); 
      userChoice = getChoice();                  
    } 
  }
    
  
////////////////// MENU METHODS //////////////////
  
  
  // Method getChoice - Get user's choice for menus
  public static String getChoice() 
  {
    System.out.print("Input choice and press enter to continue: ");    
    String choice = input.nextLine();
    return choice;
  }   
  
  // Method menuMain - Print out the Main Menu options
  public static void menuMain() 
  {
    System.out.println("-----------------------------");
    System.out.println("Concrete Management System");
    System.out.println("-----------------------------");    
    System.out.println("1 | Inventory");        
    System.out.println("2 | Batch Recipes");        
    System.out.println("3 | Orders");                
    System.out.println("4 | Write to Data Files");
    System.out.println("5 | Exit & Write to Data Files");             
    System.out.println("-----------------------------");    
  }  
  
  // Method menuInventory - Print out the Inventory Menu options
  public static void menuInventory() 
  {
    System.out.println("-----------------------------");
    System.out.println("Inventory Sub-Menu");
    System.out.println("-----------------------------");    
    System.out.println("1 | Add Material Type");        
    System.out.println("2 | Remove Material Type");        
    System.out.println("3 | Modify Material Amount");                
    System.out.println("4 | Print Current Inventory");                    
    System.out.println("5 | Exit to Main Menu");                        
    System.out.println("-----------------------------");    
  }  
  
  // Method menuBatch - Print out the Batch Menu options
  public static void menuBatch() 
  {
    System.out.println("-----------------------------");
    System.out.println("Batch Recipes Sub-Menu");
    System.out.println("-----------------------------");    
    System.out.println("1 | Add Batch Recipe");        
    System.out.println("2 | Remove Batch Recipe");        
    System.out.println("3 | Print All Batch Recipes");                
    System.out.println("4 | Exit to Main Menu");                    
    System.out.println("-----------------------------");    
  } 
  
  // Method menuOrder - Print out the Order Menu options
  public static void menuOrder() 
  {
    System.out.println("-----------------------------");
    System.out.println("Orders Sub-Menu");
    System.out.println("-----------------------------");    
    System.out.println("1 | Add New Order");        
    System.out.println("2 | Print Current Order");
    System.out.println("3 | Clear Current Order");        
    System.out.println("4 | Verify Order Materials are in Inventory");                
    System.out.println("5 | Place Order and Update Inventory"); 
    System.out.println("6 | Print Order History");                    
    System.out.println("7 | Exit to Main Menu");                    
    System.out.println("-----------------------------");    
  }
  
  // Method loopInventory - Loop for Inventory Sub-Menu
  public static void loopInventory() 
  {
    menuInventory();
    String userChoice = getChoice();
    
    // Inventory menu loop
    while (!userChoice.equals("5")) 
    {
      switch (userChoice) 
      {
        case "1":  {addMaterial();
                   break;}
        case "2":  {removeMaterial();
                   break;}
        case "3":  {modifyMaterial();
                   break;}
        case "4":  {printMaterial();
                   break;}
        case "5":  break;
        default:   {
                     System.out.println("Incorrect input. Press enter to continue...");
                     input.nextLine();
                   }
      }
      menuInventory(); 
      userChoice = getChoice();                       
    }  
  }  
 
  // Method loopBatch - Loop for Batch Sub-Menu
  public static void loopBatch() 
  {
    menuBatch();
    String userChoice = getChoice();
    
    // Batch menu loop
    while (!userChoice.equals("4")) 
    {
      switch (userChoice) 
      {
        case "1":  {addBatch();
                   break;}
        case "2":  {removeBatch();
                   break;}
        case "3":  {printBatch();
                   break;}
        case "4":  break;
        default:   {
                     System.out.println("Incorrect input. Press enter to continue...");
                     input.nextLine();
                   }  
      }
      menuBatch(); 
      userChoice = getChoice();                  
    }  
  }

  // Method loopOrder - Loop for Order Sub-Menu
  public static void loopOrder() 
  {
    menuOrder();
    String userChoice = getChoice();
          
    // Order menu loop
    while (!userChoice.equals("7")) 
    {
      switch (userChoice) 
      {
        case "1":  {addOrder();
                   break;}
        case "2":  {printOrder();
                   break;}  
        case "3":  {clearOrder();
                   break;}
        case "4":  {checkInventory(buildOrder());
                   break;}
        case "5":  {placeOrder();
                   break;}
        case "6":  {printOrderHistory();
                   break;}
        case "7":  break;
        default:   {
                     System.out.println("Incorrect input. Press enter to continue...");
                     input.nextLine();
                   }
      }
      menuOrder(); 
      userChoice = getChoice();                  
    }  
  }  
  
  
  ////////////////// INVENTORY METHODS //////////////////


  // Method addMaterial - Add a new material type to the inventory
  static void addMaterial()
  {
    
    // Loop to verify user input doesn't already exist
    do {
      System.out.println("Input material name and press enter: ");
      nameLambda = input.nextLine();
       
      flag = false;
        
      inventory.forEach( (n) -> {
        if (n.getName().equals(nameLambda)) {
          flag = true;
        }
      } );
        
      if (flag) {
        System.out.println("Material already exists. Please choose menu option to Modify.");          
      }
        
    } while ((nameLambda != null));

    if (!flag)
    {    
      intLambda = 0;
    
      // Loop to force positive input amount
      do {
        System.out.println("Input new material amount (positive integer) and press enter: ");
        try {
          intLambda = Integer.parseInt(input.nextLine());        
        } catch (NumberFormatException e) {
        }
      } while (intLambda <= 0);
        
      inventory.add(new Material(nameLambda,intLambda));
    }  
  }
  
  // Method removeMaterial - Remove an existing material type from the inventory
  static void removeMaterial()
  {
    // Loop to verify user input exists
    do {
      System.out.println("Input material name and press enter: ");
      nameLambda = input.nextLine();
       
      flag = false;
        
      inventory.forEach( (n) -> {
        if (n.getName().equals(nameLambda)) {
          flag = true;
        }
      } );
        
      if (!flag) {
        System.out.println("Material name not found.");          
      }
        
    } while ((nameLambda != null) && (!flag));

    // Using an Iterator to remove element
    Iterator<Material> iterator = inventory.iterator();
      
    while (iterator.hasNext()) 
    {
      Material test = iterator.next();
      if (test.getName().equals(nameLambda)) 
      {
        iterator.remove();
      }
    }
  }
  
  // Method modifyMaterial - Change the amount of an existing material in the inventory
  static void modifyMaterial()
  {
    // Loop to verify user input exists
    do {
      System.out.println("Input material name and press enter: ");
      nameLambda = input.nextLine();
       
      flag = false;
        
      inventory.forEach( (n) -> {
        if (n.getName().equals(nameLambda)) {
          flag = true;
        }
      } );
        
      if (!flag) {
        System.out.println("Material name not found.");          
      }
        
    } while ((nameLambda != null) && (!flag));
    
    intLambda = 0;
    
    // Loop to force positive input amount
    do {
      System.out.println("Input new material amount (positive integer) and press enter: ");
      try {
        intLambda = Integer.parseInt(input.nextLine());        
      } catch (NumberFormatException e) {
      }
    } while (intLambda <= 0);
    
    // Loop to find and modify Material amount in inventory
    inventory.forEach( (n) -> { 
      if (nameLambda.equals(n.getName())) 
      {
        int location = inventory.indexOf(n);
        inventory.set(location, new Material(nameLambda,intLambda));
      }
    } );    
  }

  // Method printMaterial - Print a list of all materials (Name and Amount) currently in the inventory
  static void printMaterial()
  {
    System.out.println("-----------------------------");
    System.out.println("Current Inventory");
    System.out.println("-----------------------------");    
    
    // Print inventory loop
    inventory.forEach( (n) -> { System.out.println("Material Name / Amount:   " + n.getName() + "  /  " + n.getAmount()); } );

    System.out.println("-----------------------------");    
    System.out.println("Press enter");    
    System.out.println("-----------------------------");        
    input.nextLine();
  }


  ////////////////// BATCH METHODS /////////////////


  // Method addBatch - Add a new batch recipe to the batches file
  public static void addBatch()
  {
    System.out.println("Input new batch recipe name and press enter: ");
    String batchName = input.nextLine();
   
    String userChoice = "-1";
    ArrayList<Material> tempBatches = new ArrayList<>();
    
    // Add Batch loop
    while (!userChoice.equals("N")) 
    {
      
      // Loop to verify user input exists
      do {
        System.out.println("Input material name and press enter: ");
        nameLambda = input.nextLine();
       
        flag = false;
        
        inventory.forEach( (n) -> {
          if (n.getName().equals(nameLambda)) {
            flag = true;
          }
        } );
        
        if (!flag) {
          System.out.println("Material name not found.");          
        }
        
      } while ((nameLambda != null) && (!flag));
     
      intLambda = 0;
    
      // Loop to force positive input amount
      do {
        System.out.println("Input material amount (positive integer) and press enter: ");
        try {
          intLambda = Integer.parseInt(input.nextLine());        
        } catch (NumberFormatException e) {
        }
      } while (intLambda <= 0);
       
      tempBatches.add(new Material(nameLambda,intLambda));
      
      do {
        System.out.println("Add another material to batch? (Y/N)");
        userChoice = getChoice();                  
      } while (!userChoice.equals("N") && !userChoice.equals("Y"));
    }
    
    batches.add(new Recipe(batchName,tempBatches));
  }
    
  // Method removeBatch - Remove an existing batch recipe from the batches file
  public static void removeBatch()
  {
    // Loop to verify user input exists
    do {
      System.out.println("Input batch recipe name and press enter: ");
      nameLambda = input.nextLine();
       
      flag = false;
        
      inventory.forEach( (n) -> {
        if (n.getName().equals(nameLambda)) {
          flag = true;
        }
      } );
        
      if (!flag) {
        System.out.println("Batch recipe name not found.");          
      }
        
    } while ((nameLambda != null) && (!flag));

    // Using an Iterator to remove element
    Iterator<Recipe> iterator = batches.iterator();
      
    while (iterator.hasNext()) 
    {
      Recipe test = iterator.next();
      if (test.getName().equals(nameLambda)) 
      {
        iterator.remove();
      }
    }
  }
  
  // Method printBatch - Print a list of all batch recipes in the batches file
  public static void printBatch()
  {
    System.out.println("-----------------------------");
    System.out.println("Current Batch Recipes");
    System.out.println("-----------------------------");    

    // Print batch loop
    batches.forEach( (n) -> { 
      System.out.println("Batch Name: " + n.getName()); 
      n.getComponent().forEach( (m) -> { 
        System.out.println("Material Name / Amount   " + m.getName() + "  /  " + m.getAmount()); 
      } );  
      System.out.println("-----------------------------");
    } );
       
    System.out.println("Press enter");    
    System.out.println("-----------------------------");        
    input.nextLine();  
  }


  ////////////////// ORDER METHODS //////////////////

  
  // Method addOrder - Make a new customer order possibly consisting of multiple batches in multiple quantities
  public static void addOrder()
  {
    String orderNumber = String.valueOf(1 + Integer.parseInt(orderHistory.get(orderHistory.size()-1).getOrderNumber()));
    System.out.println("New Order Number: " + orderNumber);
   
    String userChoice = "-1";
    ArrayList<Material> tempOrder = new ArrayList<>();
    
    // Add Order loop
    while (!userChoice.equals("N")) 
    {
      int amount = 0;
      
      // Loop to verify user input exists
      do {
        System.out.println("Input batch name and press enter: ");
        nameLambda = input.nextLine();
        
        flag = false;
        
        batches.forEach( (n) -> {
          if (n.getName().equals(nameLambda)) {
            flag = true;
          }
        } );
        
        if (!flag) {
          System.out.println("Batch name not found.");          
        }
        
      } while ((nameLambda != null) && (!flag));
      
      // Loop to force positive input amount
      do {
        System.out.println("Input number of batches (positive amount) and press enter: ");
        try {
          amount = Integer.parseInt(input.nextLine());        
        } catch (NumberFormatException e) {
        }
      } while (amount <= 0);
      
      tempOrder.add(new Material(nameLambda,amount));
      
      do {
        System.out.println("Add another batch to the current order? (Y/N)");
        userChoice = getChoice();       
      } while (!userChoice.equals("N") && !userChoice.equals("Y"));           
    }
    
    currentOrder.setOrderNumber(orderNumber);
    currentOrder.setOrderList(tempOrder);
  }
  
  // Method printOrder - Print the current order
  public static void printOrder()
  {
    System.out.println("-----------------------------");
    System.out.println("Order Number: " + currentOrder.getOrderNumber());   
    System.out.println("------------%3CmxGraphModel%3E%3Croot%3E%3CmxCell%20id%3D%220%22%2F%3E%3CmxCell%20id%3D%221%22%20parent%3D%220%22%2F%3E%3CmxCell%20id%3D%222%22%20value%3D%22%22%20style%3D%22edgeStyle%3DorthogonalEdgeStyle%3Brounded%3D0%3BorthogonalLoop%3D1%3BjettySize%3Dauto%3Bhtml%3D1%3B%22%20edge%3D%221%22%20source%3D%223%22%20target%3D%225%22%20parent%3D%221%22%3E%3CmxGeometry%20relative%3D%221%22%20as%3D%22geometry%22%2F%3E%3C%2FmxCell%3E%3CmxCell%20id%3D%223%22%20value%3D%22Print%26lt%3Bdiv%26gt%3BBatches%26lt%3B%2Fdiv%26gt%3B%22%20style%3D%22whiteSpace%3Dwrap%3Bhtml%3D1%3Bshape%3Dmxgraph.basic.pentagon%22%20vertex%3D%221%22%20parent%3D%221%22%3E%3CmxGeometry%20x%3D%22-217.5%22%20y%3D%22505.25%22%20width%3D%2265%22%20height%3D%2260%22%20as%3D%22geometry%22%2F%3E%3C%2FmxCell%3E%3CmxCell%20id%3D%224%22%20value%3D%22%22%20style%3D%22edgeStyle%3DorthogonalEdgeStyle%3Brounded%3D0%3BorthogonalLoop%3D1%3BjettySize%3Dauto%3Bhtml%3D1%3BentryX%3D0.5%3BentryY%3D0%3BentryDx%3D0%3BentryDy%3D0%3BentryPerimeter%3D0%3B%22%20edge%3D%221%22%20source%3D%225%22%20target%3D%226%22%20parent%3D%221%22%3E%3CmxGeometry%20relative%3D%221%22%20as%3D%22geometry%22%3E%3CmxPoint%20x%3D%22-185%22%20y%3D%22648.25%22%20as%3D%22targetPoint%22%2F%3E%3C%2FmxGeometry%3E%3C%2FmxCell%3E%3CmxCell%20id%3D%225%22%20value%3D%22Print%20All%20Batches%22%20style%3D%22rounded%3D0%3BwhiteSpace%3Dwrap%3Bhtml%3D1%3B%22%20vertex%3D%221%22%20parent%3D%221%22%3E%3CmxGeometry%20x%3D%22-230%22%20y%3D%22592.25%22%20width%3D%2290%22%20height%3D%2234%22%20as%3D%22geometry%22%2F%3E%3C%2FmxCell%3E%3CmxCell%20id%3D%226%22%20value%3D%22Main%26lt%3Bdiv%26gt%3BMenu%26lt%3B%2Fdiv%26gt%3B%22%20style%3D%22whiteSpace%3Dwrap%3Bhtml%3D1%3Bshape%3Dmxgraph.basic.pentagon%22%20vertex%3D%221%22%20parent%3D%221%22%3E%3CmxGeometry%20x%3D%22-220%22%20y%3D%22661.25%22%20width%3D%2270%22%20height%3D%2260%22%20as%3D%22geometry%22%2F%3E%3C%2FmxCell%3E%3C%2Froot%3E%3C%2FmxGraphModel%3E-----------------");
        
    // Print order loop
    if (currentOrder.getOrderNumber() == null) {
      System.out.println("No Current Order"); 
    } else {
      currentOrder.getOrderList().forEach( (n) -> { 
        System.out.println("Batch Name / Amount   " + n.getName() + "  /  " + n.getAmount()); 
      } );
    }
    
    System.out.println("-----------------------------");           
    System.out.println("Press enter");    
    System.out.println("-----------------------------");        
    input.nextLine();
  }
    
  // Method clearOrder - Clear the current order  
  public static void clearOrder()
  {
    currentOrder.setOrderNumber(null);
    currentOrder.setOrderList(null);
  }

  // Method buildOrder - Compile and return arraylist of total materials in order 
  public static ArrayList<Material> buildOrder()
  {

    // Create variable that reflects name and number ordered of each batch in order
    ArrayList<Material> tempOrder = currentOrder.getOrderList();       

    // Create variable to hold total amounts of materials in order
    ArrayList<Material> tempAmounts = new ArrayList<>();
        
    // Loop to add batch recipe materials multiplied by number of batches ordered (for each batch in order)  
    tempOrder.forEach( (n) -> {
      int batchMult = n.getAmount();
      String recipe = n.getName();

      // Loop to find correct batch recipe
      batches.forEach( (m) -> {

        // When the correct batch recipe is found
        if (recipe.equals(m.getName())) {
       
          // Loop through each material in the batch recipe
          m.getComponent().forEach( (l) -> {
            
            flag = false;

            // Loop to find if material is already in tempAmounts
            tempAmounts.forEach( (a) -> {
              
              // If material is in tempAmounts, add (material amount in batch recipe * batches ordered) to existing
              if (l.getName() == a.getName()) { 
                a.setAmount(a.getAmount() + (l.getAmount()*batchMult));
                flag = true;
              } 
            } );

            // If material isn't already in tempAmounts, create new entry in tempAmounts
            if (!flag) {
              tempAmounts.add(new Material(l.getName(),(l.getAmount()*batchMult)));
            }            
          } );
        }
      } );
    } );  
    return tempAmounts;
  }
  
  // Method checkInventory - Compare order material amounts against current Inventory
  public static boolean checkInventory(ArrayList<Material> tempAmounts) 
  {
    flag = true;
  
    // Compare tempAmounts to current Inventory to determine if Order materials are in Inventory
    tempAmounts.forEach( (x) -> {
      inventory.forEach( (y) -> {
        if (x.getName().equals(y.getName())) {
      
          // Compare amounts in order vs inventory
          if (x.getAmount() > y.getAmount()) {
            System.out.println("Order " + currentOrder.getOrderNumber() + " Verification Failed: Not enough " + y.getName());
            flag = false;
          }
        }
      } );
    } );

    if (!flag) {
      return false;
    } else {
      System.out.println("Order " + currentOrder.getOrderNumber() + " Verified: Sufficient materials in Inventory");
      return true;
    }
  }
      
  // Method placeOrder - Remove order materials from inventory and write data files
  public static void placeOrder()
  {
    
    // Only place order if materials are in inventory
    if (checkInventory(buildOrder())) {
      
      // Create copy of all materials in order
      ArrayList<Material> tempOrder = buildOrder();
      
      // Remove each material in order from inventory
      tempOrder.forEach( (x) -> {
        inventory.forEach( (y) -> {
          if (x.getName().equals(y.getName())) {
            // Remove material from inventory
            y.setAmount(y.getAmount() - x.getAmount());
          } else {
          }
        } );         
      } );
      orderHistory.add(new Order(currentOrder.getOrderNumber(),currentOrder.getOrderList()));
      System.out.println("Order Placed.");     
      writeFiles();
      clearOrder();
    } else {
      System.out.println("Order NOT Placed!");
    }
  }

  // Method printOrderHistory - Print the order history
  public static void printOrderHistory()
  {
        
    // Print order loop
    orderHistory.forEach( (n) -> { 

      System.out.println("-----------------------------");
      System.out.println("Order Number: " + n.getOrderNumber());   
      System.out.println("-----------------------------");

      n.getOrderList().forEach( (m) -> { 
        System.out.println("Batch Name / Amount   " + m.getName() + "  /  " + m.getAmount()); 
      } );
    } );
    
    System.out.println("-----------------------------");           
    System.out.println("Press enter");    
    System.out.println("-----------------------------");        
    input.nextLine();
  }


  ////////////////// READ METHODS //////////////////  
 
 
  // Method readFiles - Load current inventory/recipe variables from files
  public static void readFiles()
  {
    readInventory();
    readBatches();
    readOrderHistory();
//    readOrder();                 //FOR TESTING PURPOSES
  }
  
  // Method readInventory - Load current inventory from file
  public static void readInventory()  
  {
    try {
      File myObj = new File("Inventory.txt");
      Scanner myReader = new Scanner(myObj);
      
      // Create variable to determine which line of data is being read
      int lineCount = 1;
      
      // Create temporary variables
      String[] nameArray = null;
      String[] amountArray = null;
      String[] readArray = null;
   
      // Loop to read each line, parse by space seperator, and assign to temporary array
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        readArray = data.split(" ");

        if (lineCount == 1) {
          nameArray = readArray;
        }
        if (lineCount == 2) {
          amountArray = readArray; 
        }
        lineCount++;
      }

      // Loop to add Names and Amounts from temporary arrays to main inventory variable 
      for (int i = 0; i <= (nameArray.length-1); i++) {       
        inventory.add(new Material(nameArray[i],Integer.parseInt(amountArray[i])));
      }
      
      // Close reader
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
 
  // Method readBatches - Load current batch recipes from file
  public static void readBatches()  
  {
    try {
      File myObj = new File("Batches.txt");
      Scanner myReader = new Scanner(myObj);
      
      // Create variable to determine which line of data is being read
      int lineCount = 1;
      
      // Create temporary variables
      String batchName = null;
      String[] nameArray = null;
      String[] amountArray = null;
      String[] readArray = null;
   
      // Loop to read each line, parse by space seperator, and assign to temporary array
      while (myReader.hasNextLine()) {

        String data = myReader.nextLine();
        readArray = data.split(" ");

        switch (lineCount % 3) 
        {
          case 1:  batchName = data;
                   break;
          case 2:  nameArray = readArray;
                   break;
          case 0:  {
                     amountArray = readArray;
                     ArrayList<Material> tempBatches = new ArrayList<>();
               
                     // Loop to add Names and Amounts from temporary arrays to tempBatches variable 
                     for (int i = 0; i <= (nameArray.length-1); i++) {       
                       tempBatches.add(new Material(nameArray[i],Integer.parseInt(amountArray[i])));
                     }
                     batches.add(new Recipe(batchName,tempBatches));
                     break;
                   }  
        }
        lineCount++;
      }

      // Close reader
      myReader.close();
      
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
    
/*  // Method readOrder - Load current order from file
  public static void readOrder()  
  {
    try {  
      File myObj = new File("Order.txt");
      Scanner myReader = new Scanner(myObj);
      
      // Create variable to determine which line of data is being read
      int lineCount = 1;
      
      // Create temporary variables
      String orderNumber = null;
      String[] nameArray = null;
      String[] amountArray = null;
      String[] readArray = null;
      
      // Loop to read each line, parse by comma seperator, and assign to temporary array
      while (myReader.hasNextLine()) {

        String data = myReader.nextLine();
        readArray = data.split(",");

        switch (lineCount % 3) 
        {
          case 1:  orderNumber = data;
                   break;
          case 2:  nameArray = readArray;
                   break;
          case 0:  {
                     amountArray = readArray;
                     ArrayList<Material> tempOrder = new ArrayList<>();
               
                     // Loop to add Names and Amounts from temporary arrays to tempOrder variable 
                     for (int i = 0; i <= (nameArray.length-1); i++) {       
                       tempOrder.add(new Material(nameArray[i],Integer.parseInt(amountArray[i])));
                     }
                     currentOrder.setOrderNumber(orderNumber);
                     currentOrder.setOrderList(tempOrder);
                     break;
                   }  
        }
        lineCount++;
      }

      // Close reader
      myReader.close();
      
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }                       //FOR TESTING PURPOSES */

  // Method readOrderHistory - Load order history from file
  public static void readOrderHistory()  
  {
    try {
      File myObj = new File("OrderHistory.txt");
      Scanner myReader = new Scanner(myObj);
      
      // Create variable to determine which line of data is being read
      int lineCount = 1;
      
      // Create temporary variables
      String orderNumber = null;
      String[] nameArray = null;
      String[] amountArray = null;
      String[] readArray = null;
   
      // Loop to read each line, parse by space seperator, and assign to temporary array
      while (myReader.hasNextLine()) {

        String data = myReader.nextLine();
        readArray = data.split(",");

        switch (lineCount % 3) 
        {
          case 1:  orderNumber = data;
                   break;
          case 2:  nameArray = readArray;
                   break;
          case 0:  {
                     amountArray = readArray;
                     ArrayList<Material> tempOrders = new ArrayList<>();
               
                     // Loop to add Names and Amounts from temporary arrays to tempBatches variable 
                     for (int i = 0; i <= (nameArray.length-1); i++) {       
                       tempOrders.add(new Material(nameArray[i],Integer.parseInt(amountArray[i])));
                     }
                     orderHistory.add(new Order(orderNumber,tempOrders));
                     break;
                   }  
        }
        lineCount++;
      }

      // Close reader
      myReader.close();
      
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }  
    
    
  ////////////////// WRITE METHODS //////////////////  


  // Method writeFiles - Output current inventory/recipe variables to files
  public static void writeFiles()
  {
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    String tempTime = currentTimestamp + " ";
    String tempName = null;  
    String cleanName = null;  
    
    // Write to main Inventory data file
    tempName = "Inventory.txt";
    writeInventory(tempName);

    // Write to timestamped backup Inventory data file        
    tempName = tempTime.substring(0,19) + " Inventory.txt";
    cleanName = tempName.replaceAll(":", "-"); 
    writeInventory(cleanName);    
    
    // Write to main Batches data file
    tempName = "Batches.txt";
    writeBatches(tempName);

    // Write to timestamped backup Batches data file            
    tempName = tempTime.substring(0,19) + " Batches.txt";
    cleanName = tempName.replaceAll(":", "-");     
    writeBatches(cleanName);
    
    // Write to main OrderHistory data file
    tempName = "OrderHistory.txt";
    writeOrderHistory(tempName);

    // Write to timestamped backup Batches data file            
    tempName = tempTime.substring(0,19) + " OrderHistory.txt";
    cleanName = tempName.replaceAll(":", "-");     
    writeOrderHistory(cleanName);
  }

  // Method writeInventory - Output current inventory to data file
  public static void writeInventory(String fileName)
  {
    try {
      
      // Create data file if it doesn't already exist
      File myObj = new File(fileName);
      myObj.createNewFile();
      
      FileWriter myWriter = new FileWriter(fileName);
      
      // Loop to write material names to Inventory data file
      for (int i = 0; i <= inventory.size()-1; i++) {
        myWriter.write(inventory.get(i).getName() + " ");   
      }

      myWriter.write(System.lineSeparator());

      // Loop to write material amounts to Inventory data file 
      for (int i = 0; i <= inventory.size()-1; i++) {
        myWriter.write(inventory.get(i).getAmount() + " ");   
      }

      myWriter.close();
      System.out.println("Successfully wrote to " + fileName);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }  
  }
 
  // Method writeBatches - Output current batch recipes to data file 
  public static void writeBatches(String fileName)
  {
    try {
      
      // Create data file if it doesn't already exist
      File myObj = new File(fileName);
      myObj.createNewFile();
      
      FileWriter myWriter = new FileWriter(fileName);

      // Loop to write batch names to Batches data file
      for (int i = 0; i <= batches.size()-1; i++) {
        myWriter.write(batches.get(i).getName()); 
        
        myWriter.write(System.lineSeparator());

        ArrayList<Material> tempComponent = batches.get(i).getComponent();

        // Loop to write material names to Batches data file 
        for (int j = 0; j <= tempComponent.size()-1; j++) {
          myWriter.write(tempComponent.get(j).getName() + " ");  
        }
        
        myWriter.write(System.lineSeparator());        
        
        // Loop to write material amounts to Batches data file 
        for (int k = 0; k <= tempComponent.size()-1; k++) {
          myWriter.write(tempComponent.get(k).getAmount() + " ");    
        }
        
        myWriter.write(System.lineSeparator());
      }
    
      myWriter.close();
      System.out.println("Successfully wrote to " + fileName);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
 
  // Method writeOrderHistory - Output current OrderHistory to data file 
  public static void writeOrderHistory(String fileName)
  {
    try {
      
      // Create data file if it doesn't already exist
      File myObj = new File(fileName);
      myObj.createNewFile();
      
      FileWriter myWriter = new FileWriter(fileName);
      
      // Loop to write current order to OrderHistory data file
      for (int i = 0; i <= orderHistory.size()-1; i++) {
        myWriter.write(orderHistory.get(i).getOrderNumber()); 
        
        myWriter.write(System.lineSeparator());

        ArrayList<Material> tempComponent = orderHistory.get(i).getOrderList();

        // Loop to write material names to OrderHistory data file 
        for (int j = 0; j <= tempComponent.size()-1; j++) {
          myWriter.write(tempComponent.get(j).getName() + ",");  
        }
        
        myWriter.write(System.lineSeparator());        
        
        // Loop to write material amounts to OrderHistory data file 
        for (int k = 0; k <= tempComponent.size()-1; k++) {
          myWriter.write(tempComponent.get(k).getAmount() + ",");    
        }
        
        myWriter.write(System.lineSeparator());
      }

      myWriter.close();
      System.out.println("Successfully wrote to " + fileName);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }  
  }
} 
 
 
////////////////// CLASSES //////////////////
  

class Material  
{  
  private String name;  
  private int amount;
  
  public Material(String inputName, int inputAmount)
  {
    name = inputName;
    amount = inputAmount;
  }
  
  // Getter Name
  public String getName()
  {
    return name;
  }
  
  // Getter Amount
  public int getAmount()
  {
    return amount;
  }
  
  // Setter Name
  public void setName(String newName)
  {
    this.name = newName;
  }
    
  // Setter Amount
  public void setAmount(int newAmount)
  {
    this.amount = newAmount;
  }
}    
    
class Recipe  
{
  private String name;  
  private ArrayList<Material> component;
  
  public Recipe(String inputName, ArrayList<Material> inputBatches)
  {
    name = inputName;
    component = inputBatches;
  }    
  
  // Getter Name
  public String getName()
  {
    return name;
  }
  
  // Getter Component
  public ArrayList<Material> getComponent()
  {
    return component;
  }
  
  // Setter Name
  public void setName(String newName)
  {
    this.name = newName;
  }
    
  // Setter Amount
  public void setComponent(ArrayList<Material> newComponent)
  {
    this.component = newComponent;
  }
}   
   
class Order  
{   
  private String orderNumber;  
  private ArrayList<Material> orderList;
     
  public Order()
  {
    orderNumber = null;
  }   
     
  public Order(String inputName, ArrayList<Material> inputList)
  {
    orderNumber = inputName;
    orderList = inputList;
  }    
 
 // Getter OrderNumber
  public String getOrderNumber()
  {
    return orderNumber;
  }
  
  // Getter OrderList
  public ArrayList<Material> getOrderList()
  {
    return orderList;
  }
  
  // Setter OrderNumber
  public void setOrderNumber(String newOrderNumber)
  {
    this.orderNumber = newOrderNumber;
  }
    
  // Setter OrderList
  public void setOrderList(ArrayList<Material> newOrderList)
  {
    this.orderList = newOrderList;
  }
}  