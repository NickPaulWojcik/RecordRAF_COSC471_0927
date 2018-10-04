/*  Author: Nicholas Wojcik
 *  Assignment: 0927
 *  GitHub URL: https://github.com/NickPaulWojcik/RecordRAF_COSC471_0927/blob/master/RandomAccessRecord.java
 *  
 *  Description: A console application that creates records of size 11 bytes in a Random Access File.  It
 *  			 creates the file if non existent.  The records consist of an int, double, and char.  Methods
 *               include functionality to add records, read records by number, print all records, print 
 *               user selected columns, search user supplied values in user supplied columns, and print the 
 *               characters of records with an int value less than a user supplied number, in a user supplied 
 *               record range.
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

//Record length is 4 + 8 + 2 = 14 (bytes)
//Record types is integer + double + character

public class RandomAccessRecord {
	
	// ****************************************** VARIABLES ************************
	static RandomAccessFile records;
	static final int recordLength = 14;
	static int recordQuantity = 0;
	static boolean cont = true;

	// ****************************************** MAIN-LOOP ************************
	public static void main(String[] args) throws IOException {
		try {
			
			// create a random access file, or clear if already existent
			records = new RandomAccessFile("recordBase","rw");
			records.setLength(0);
			
			
			// add individual records
			addRecord(1, 1.0, 'a');
			addRecord(2, 2.0, 'b');
			addRecord(3, 3.0, 'c');
			addRecord(4, 4.0, 'd');
			addRecord(5, 5.0, 'e');
			addRecord(6, 6.0, 'f');
			
			
			// print all the records currently in the random access file
			printAllRecords();
			
			
			// print the columns individually, user supplied parameters 
			printColumn("ints");
			printColumn("doubles");
			printColumn("chars");
			
			
			// print search results, first parameter is the column to parse and the 
			// second is the value to find
			printSearch("ints", "4");
			printSearch("doubles", "6.0");
			printSearch("chars", "c");
			
			
			// print characters in records with ints less than the first parameter, in 
			// record numbers between the first and second parameter
			printIntsLessThan(4, 0, 5);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// ******************************************* FUNCTIONS ***********************
	
	
	// Function: prints out the entire record, parameter is the record number
	private static void readRecord(int recordNumber) throws IOException {
		
		// place the file pointer at the beginning of the record(parameter)
		records.seek(recordNumber*recordLength);
		
		// prints out the record contents
		System.out.print("Record " + recordNumber + ": ");
		System.out.print("( " + records.readInt() + ", ");
		System.out.print(records.readDouble() + ", '");
		System.out.print(records.readChar() + "' )");
		System.out.println("");
	}
	
	
	
	// Function: adds a record with user supplied elements(parameters1-3)
	private static void addRecord(int a, double b, char c) throws IOException {
		
		// place the file pointer at the end of the last record
		records.seek(recordLength*recordQuantity);
		
		// writes out the supplied elements
		records.writeInt(a);
		records.writeDouble(b);
		records.writeChar(c);
		System.out.println("record added in position " + recordQuantity + ".");
		
		// increments the amount of records stored in the random access file
		recordQuantity++;
	}
	
	
	
	// Function: prints out all the records in order
	private static void printAllRecords() throws IOException {
		
		System.out.println();
		System.out.println("****** ALL_RECORDS ******");
		
		// loops through and prints all records via the "Function: ReadRecord(x)"
		for(int i = 0; i < recordQuantity; i++) {
			readRecord(i);
		}
	}
	
	
	
	// Function: prints out the elements in the parameter provided column
	private static void printColumn(String name) throws IOException {
		
		// prepares the parameter for parsing
		String temp = name.toLowerCase();
		
		// prints out all elements in the integer column
		if(temp.equals("int") || 
		   temp.equals("integer") || 
		   temp.equals("ints") || 
		   temp.equals("integers")) {
			System.out.println();
			System.out.println("****** INTEGERS(COL 1) ******");
			for(int i = 0; i < recordQuantity; i++) {
				records.seek(i*recordLength);
				System.out.println("Record " + i + ": " + records.readInt());
			}
			
		// prints out all the elements in the double column
		}else if(temp.equals("double") || 
				 temp.equals("doubles")) {
			System.out.println();
			System.out.println("****** DOUBLES(COL 2) ******");
			for(int i = 0; i < recordQuantity; i++) {
				records.seek((i*recordLength) + 4);
				System.out.println("Record " + i + ": " + records.readDouble());
			}
		}
		
		// prints out all the elements in the character column
		else if(temp.equals("char") || 
				temp.equals("character") || 
				temp.equals("chars") || 
				temp.equals("characters")) {
			System.out.println();
			System.out.println("****** CHARACTERS(COL 3) ******");
			for(int i = 0; i < recordQuantity; i++) {
				records.seek((i*recordLength) + 12);
				System.out.println("Record " + i + ": " + records.readChar());
			}
		}
	}
	
	
	
	// Function: searches a column(parameter1) for the values(parameter2) and outputs the matching records
	private static void printSearch(String name, String value) throws IOException {
		
		// prepares the parameter for parsing
		String temp = name.toLowerCase();
		
		// prints if the search is for an integer, matching with parameter2
		if(temp.equals("int") || 
		   temp.equals("integer") || 
		   temp.equals("ints") || 
		   temp.equals("integers")) {
			System.out.println();
			System.out.println("****** SEARCH INTEGERS FOR " + value +" ******");
			for(int i = 0; i < recordQuantity; i++) {
				records.seek(i*recordLength);
				if(records.readInt() == Integer.parseInt(value))
					readRecord(i);	
			}
			
		// prints if the search is for a double, matching with parameter2
		}else if(temp.equals("double") || 
				 temp.equals("doubles")) {
			System.out.println();
			System.out.println("****** SEARCH DOUBLES FOR " + value +" ******");
			for(int i = 0; i < recordQuantity; i++) {
				records.seek((i*recordLength) + 4);
				if(records.readDouble() == Double.parseDouble(value))
					readRecord(i);
			}
		}
		
		// prints if the search is for a character, matching with parameter2
		else if(temp.equals("char") || 
				temp.equals("character") || 
				temp.equals("chars") ||
				temp.equals("characters")) {
			System.out.println();
			System.out.println("****** SEARCH CHARACTERS FOR '" + value +"' ******");
			for(int i = 0; i < recordQuantity; i++) {
				records.seek((i*recordLength) + 12);
				if(records.readChar() == value.charAt(0) )
					readRecord(i);
			}
		}	
	}
	
	
	
	// Function: prints the characters of records that are <= parameter1, in the records between parameter2 and parameter3
	private static void printIntsLessThan(int x, int lower, int upper) throws IOException {
		System.out.println();
		System.out.println("****** RECORDS WITH INTS <= " + x +", IN RECORDS " + lower + "-" + upper + " ******");
		
		// parses through the selected record range (parameter2&3)
		for(int i = lower; i < recordQuantity || i < upper; i++) {
			records.seek(i*recordLength);
			
			// prints the records character if its integer is <= parameter1
			if(records.readInt() <= x) {
				System.out.print("Record " + i + ": ");
				records.seek((i*recordLength) + 12);
				System.out.println("( '" + records.readChar() + "' )");
			}	
		}
	}
}
