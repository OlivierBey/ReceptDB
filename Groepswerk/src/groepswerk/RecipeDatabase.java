package groepswerk;

import java.util.ArrayList;
import java.util.Scanner;

public class RecipeDatabase {
	
		  public static void main(String[] args) {
		    // Create an array list to store our recipes
		    ArrayList<String> recipes = new ArrayList<>();
		    
		    // Create a scanner to read user input
		    Scanner scanner = new Scanner(System.in);
		    
		    // Keep reading user input until they enter "exit"
		    while (true) {
		      System.out.println("Enter a recipe or 'exit' to quit: ");
		      String recipe = scanner.nextLine();
		      
		      if (recipe.equalsIgnoreCase("exit")) {
		        break;
		      }
		      
		      // Add the recipe to the array list
		      recipes.add(recipe);
		    }
		    
		    // Print all the recipes in the array list
		    System.out.println("Here are all the recipes in the database: ");
		    for (String recipe : recipes) {
		      System.out.println(recipe);
		    }
		  }
		}

		
		  
