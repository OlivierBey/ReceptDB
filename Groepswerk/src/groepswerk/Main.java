package groepswerk;

import java.sql.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static ArrayList<Recipe> listRecipe =new ArrayList<>();
	static boolean foundRecipe = false;
	static String url = "jdbc:sqlite:C:/Users/olivi/git/groepswerk1/Groepswerk/src/groepswerk/recipe.db";
    	
	public static void main(String[] args) throws SQLException {
		
		createTable();
		startCycle();
	}
	
	 private static void createTable() throws SQLException {
		 String sql = "CREATE TABLE IF NOT EXISTS recipes (\n"
	                + "	id integer PRIMARY KEY,\n"
	                + "	RecipeTitle text NOT NULL,\n"
	                + "	Veggie text,\n"
	                + "	MeatFish text,\n"
	                + "	Sidedish text \n"
	                + ");";
		 
		 try (Connection conn = DriverManager.getConnection(url);
	                Statement stmt = conn.createStatement()) {
	            stmt.execute(sql);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	
	private static void startCycle() {
		listRecipe.removeAll(listRecipe);
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
	}

	private static void NotFound() {
		System.out.println("Uw zoekactie heeft geen resultaten, probeer opnieuw");
		startCycle();
	}

	private static void searchRecipe() {
		loadDbList();
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
		Boolean foundRecipe = false;
		System.out.println("Geeft een ingredient in : ");
		String ingredient = scanner.nextLine();
		
		for (Recipe recipe : listRecipe) {
			
			if(ingredient.equalsIgnoreCase(recipe.vegetables) || 
					ingredient.equalsIgnoreCase(recipe.meatFish) || 
					ingredient.equalsIgnoreCase(recipe.sideDish)) {
			 System.out.println(recipe.name);
			 foundRecipe= true;
			}
			
		}
		if(foundRecipe== false) {
			NotFound();
		}
	}

	private static void printList() {
		
		for (Recipe recipe : listRecipe) {
			System.out.println(recipe.name + "\t"+" met ingredienten: "+ recipe.vegetables + " " + recipe.meatFish+" " + recipe.sideDish);
		}
		startCycle();
	}

	private static void loadDbList() {
		String sqlstring="SELECT RecipeTitle, Veggie, MeatFish, SideDish FROM recipes";
			
		try (Connection conn = DriverManager.getConnection(url);
				
				Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sqlstring)){
	            
	            while (rs.next()) {
	            	Recipe recept = new Recipe();
	            	recept.name=rs.getString("RecipeTitle");
	            	
	            	recept.meatFish= rs.getString("MeatFish");
	            	recept.vegetables= rs.getString("Veggie");
	            	recept.sideDish= rs.getString("sideDish");
	            	listRecipe.add(recept);
	            }
		}
		catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	private static void addRecipe() {
		System.out.println("Geef de naam van het recept in: ");
		Recipe recept1 = new Recipe();
		String hulpString = scanner.nextLine();
		if (hulpString.length()<25) {
			for (int i = hulpString.length(); i < 25; i++) {
				hulpString= hulpString+" ";
			}
		}
		recept1.name = hulpString;
		System.out.println("Welke ingredienten? (groenten vlees/vis bijgerecht)");
		hulpString = scanner.nextLine();
		String[] vegetableMeatFishSidedish= new String[3];
		vegetableMeatFishSidedish= hulpString.split(" ");
		
		if (vegetableMeatFishSidedish.length==3) {
			recept1.sideDish = vegetableMeatFishSidedish[2];
			recept1.vegetables= vegetableMeatFishSidedish[0];
			recept1.meatFish = vegetableMeatFishSidedish[1];
		}
		if (vegetableMeatFishSidedish.length==2) {
			
			recept1.vegetables= vegetableMeatFishSidedish[0];
			recept1.meatFish = vegetableMeatFishSidedish[1];
			recept1.sideDish = " ";
		}
		if (vegetableMeatFishSidedish.length==1) {
			recept1.sideDish = " ";
			recept1.vegetables= vegetableMeatFishSidedish[0];
			recept1.meatFish = " ";
		}listRecipe.add(recept1);
		addRecipeToDb(recept1);
		
		System.out.println("Bedankt, volgend recept is toegevoegd: " + 
		recept1.name + " met "+ recept1.vegetables + " " + recept1.meatFish+" " + recept1.sideDish);
		System.out.println();
		startCycle();
	}

	private static void addRecipeToDb(Recipe recept1) {
		String sqlstring1="INSERT INTO recipes(RecipeTitle, Veggie, MeatFish, SideDish) VALUES(?,?,?,?)";
		try (Connection conn = DriverManager.getConnection(url);
			
			PreparedStatement stmt= conn.prepareStatement(sqlstring1)){
				stmt.setString(1, recept1.name);
				stmt.setString(2, recept1.vegetables);
				stmt.setString(3, recept1.meatFish);
				stmt.setString(4, recept1.sideDish);
				stmt.executeUpdate();
				System.out.println("***Database updated***");
			}
			
		 catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}