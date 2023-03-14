import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Main {
	static File myInFile = new File("BooksDataFile.txt"); //file name
	static File myOutFile = new File("IsbnsAndTitles.csv"); //file name
	static TreeMap<String, Book> BST = new TreeMap<String,Book>(); //type string and book
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
			readAndFill(myInFile); //calling methods
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 createIsbnAndTitleCsv(myOutFile, BST); //writing out method
		 
		 
		
	}
 /**
  * TODO:
  * Into the TreeMap, load key/value pairs, ISBNs and
  * Books created from rows in the specified file.
  * This will allow us to look up books given their ISBN. 
  * 
  *  Notes:
  *  The file contains a header which isn’t useful to the code;
  *  read but take no action that line.
  *  Some columns from the file won’t be used;
  *  read and ignore those when creating Book objects.
  *  This method should not throw FileNotFoundException;
  *  handle this gracefully using the return data.
  */
	public static boolean readAndFill(File obj) {
		Scanner scBook = null; 
		
		try {
			scBook = new Scanner(myInFile);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		scBook.nextLine(); //skip header from text file
		
		while(scBook.hasNextLine()) {
			String line = scBook.nextLine(); 
			String[] tempArray1 = line.split("~"); //splitting text by "~" and store them into a String array
			if(tempArray1.length != 10) { //if the array have a different amount of element, the data is false and won't work properly			
				return false;
			}
			Book tempBook = new Book(tempArray1[2],tempArray1[3],Integer.parseInt(tempArray1[4]),tempArray1[5],tempArray1[6],Double.parseDouble(tempArray1[7]));
			BST.put(tempBook.ISBN(), tempBook); //put the ISBN (for natural order) and Book object into the tree
		}
		return true;
		
	}
	
	/**
	 * TODO:
	 * Data should appear sorted by ISBNorder.
	 * As you may remember from our BST discussion,
	 * you don’t need to force sorting to make this happen.
	 * This  method  should not throw FileNotFoundExceptionor 
	 * other  similar  exceptions;  handle  this gracefully using
	 * the return data. Make sure you check your -Xlint output;
	 * misuses of generics will cause point deductions
	 * 
	 * Tasks:
	 * Use  a PrintStream to  write  data  to  the  specified  file. 
	 * Include a  header  line  containing the text shown below. 
	 * Note that there should be no space after the comma; 
	 * this isn’t a good thing to do in CSV files
	 * 
	 * isbn,title
	 * 
	 * Ask the TreeMapto give you its keyset;
	 * research the method to determine the proper data type for the return.
	 * Loop  through keys;
	 * for  each  one,  send  one  output  line to  the  CSV  file
	 * containing the ISBN,  a comma, then the title. 
	 * Again here, write no space after the comma).  Example:
	 * 
	 * 000100039X,The Prophet
	 */
	
	public static boolean createIsbnAndTitleCsv(File file, TreeMap<String, Book> tree) { //using LNR
		//create CSV file
		try {
		      
		      if (myOutFile.createNewFile()) {
		        System.out.println("File created: " + myOutFile.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred."); //print if can't create a csv file
		      e.printStackTrace();
		    }
		
		//write into CSV file
		 try {
		      FileWriter myWriter = new FileWriter(myOutFile.getPath());
		      BufferedWriter out = new BufferedWriter(myWriter); //using buffered reader so it can create a newline	
		      out.write("isbn,title"); //printing header
		      out.newLine(); //make new line
		    //Get iterator
				Set<Map.Entry<String,Book>> set=tree.entrySet();   //get set
				Iterator<Map.Entry<String,Book>> iterator = set.iterator();   //create iterator
				while(iterator.hasNext()){ //iterate
					  Map.Entry<String,Book> mapEntry=(Map.Entry<String,Book>)iterator.next(); //assigning keyset to mapEntry			 
					  out.write(tree.get(mapEntry.getKey()).ISBN() + ","+ tree.get(mapEntry.getKey()).Title() ); //write title and isbn separated by a comma in every line
					  out.newLine(); //write into csv file					   
					} 
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		      return true;
		    } catch (IOException e) {
		      System.out.println("An error occurred."); //print when can't write into the csv file
		      e.printStackTrace();	      
		    }		
		return false;
		
	}
	
	

}
