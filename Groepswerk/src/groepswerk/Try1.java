package groepswerk;

import java.sql.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Try1 {
	static Scanner scanner = new Scanner(System.in);
	static ArrayList<Recipe> listRecipe =new ArrayList<>();
	static boolean conestablish= false;
	static boolean isTableMt= false;
    static Connection conn = null;
    static String url = "jdbc:sqlite:C:/Users/olivi/git/groepswerk1/Groepswerk/src/groepswerk/recipe.db";

	
	public static void main(String[] args) throws SQLException {
		
		
/*		if (conestablish= true) {
			if(isTableMt = true) {
				startCycle();	
			} else {
				createTable();
			}
		}
			else {
			connect();
		}*/

	connect();
	createTable();

	
	}
	
	 private static void createTable() throws SQLException {
		 String sql = "CREATE TABLE IF NOT EXISTS recipes (\n"
	                + "	id integer PRIMARY KEY,\n"
	                + "	RecipeTitle text NOT NULL,\n"
	                + "	veggie text,\n"
	                + "	meatFish text,\n"
	                + "	Sidedish text \n"
	                + ");";
		 
		 try (Connection conn = DriverManager.getConnection(url);
	                Statement stmt = conn.createStatement()) {
	            // create a new table
	            stmt.execute(sql);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	

	public static void connect() {
	        try {
	          
	          conn = DriverManager.getConnection(url);
	          System.out.println("Connection to SQLite has been established.");
	          conestablish= true;
	            
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        } finally {
	            try {
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
	    }	
	
	
	
	
	


	private static void startCycle() {
		System.out.println("---------------------------------------------------------");
		System.out.println("  Welkom! Wilt U een recept toevoegen of zoeken? T of Z  ");
		System.out.println("---------------------------------------------------------");
		String input = scanner.nextLine();
		if(input.equalsIgnoreCase("T")){
			addRecipe();
		} else if( input.equalsIgnoreCase("Z")){
			searchRecipe();}
			else {
				NotFound();}
		startCycle();
	}


	private static void NotFound() {
		System.out.println("Uw zoekactie heeft geen resultaten, probeer opnieuw");
		startCycle();
	}

	private static void searchRecipe() {
		System.out.println("Wilt u kiezen in de Lijst of zoeken op Ingredient? L of I");
		String input = scanner.nextLine();
		if(input.equalsIgnoreCase("L")){
			printList();
		} else if( input.equalsIgnoreCase("I")){
			searchIngredient();}
			else {
				NotFound();}
	}

	private static void searchIngredient() {
		System.out.println("Geeft een ingredient in : ");
		String ingredient = scanner.nextLine();
		ArrayList<String> result = new ArrayList<>();
		
		for (Recipe recipe : listRecipe) {
			if(ingredient.equalsIgnoreCase(recipe.vegetables)){
				result.add(recipe.name);}
			else if(ingredient.equalsIgnoreCase(recipe.meatFish)){
				result.add(recipe.name);}
			else if(ingredient.equalsIgnoreCase(recipe.sideDish)){
				result.add(recipe.name);}
			else {NotFound();}
		}
	for (String string : result) {
		System.out.println(string);
	}
	ingredient= null;
	}







	private static void printList() {
		for (Recipe recipe : listRecipe) {
			System.out.println(recipe.name);
		}
	}


	private static void addRecipe() {
		System.out.println("Geef de naam van het recept in: ");
		Recipe recept1 = new Recipe();
		String hulpString = scanner.nextLine();
		recept1.name = hulpString;
		System.out.println("Welke ingredienten? (groenten vlees/vis bijgerecht)");
		hulpString = scanner.nextLine();
		String[] vegetableMeatFishSidedish= hulpString.split(" ");
		if(vegetableMeatFishSidedish[0]!=null) {
			recept1.vegetables= vegetableMeatFishSidedish[0];
		}
		if(vegetableMeatFishSidedish[1]!=null) {	
			recept1.meatFish = vegetableMeatFishSidedish[1];
		}
		if(vegetableMeatFishSidedish[2]!=null) {
		recept1.sideDish = vegetableMeatFishSidedish[2];
		}
		listRecipe.add(recept1);
		System.out.println("Bedankt, volgend recept is toegevoegd: " + 
		recept1.name + " met "+ recept1.vegetables + " " + recept1.meatFish+" " + recept1.sideDish);
		System.out.println();
		
	}
}