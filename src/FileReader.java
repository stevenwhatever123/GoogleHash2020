
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FileReader {
    public static void main(String[] args) {
    	ArrayList<Library> libraries = new ArrayList<Library>();
        String filename = "e_so_many_books.txt";
        
        File inputFile = new File(filename);
        
        Scanner readLine = null;
        
        try {
            //Opens the file for reading.
            readLine = new Scanner(inputFile);    
        }
        
        //Catch an exception if the file does not exist and exit the program.
        catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
            System.exit(0);
        }
        
        //Read FirstLine
        int totalBooks = readLine.nextInt();
        int numLibraries= readLine.nextInt();
        int scanDays = readLine.nextInt();
        Book[] books = new Book[totalBooks];

        for (int i = 0; i < totalBooks; i++) {
            //Create book class (Have book ID starting at 0 and its score)
            books[i] = new Book(i, readLine.nextInt());
        }

        //readLine.next();
        for (int i = 0; i < numLibraries; i++) {
            int libraryID = i;
            int numBooks = readLine.nextInt();
            int signUp = readLine.nextInt();
            int ship = readLine.nextInt();
            
            //Take in list of books
            Book[] bookInLib = new Book[numBooks];
            for (int j = 0; j < numBooks; j++) {
                int bookID = readLine.nextInt();
                bookInLib[j] = books[bookID];
            }

            Library library = new Library(libraryID, bookInLib, signUp, ship);
            libraries.add(library);
        }   
        readLine.close();
        
        
        
        int totalScore = 0;
        
        for(int i = 0; i < libraries.size(); i++) {
        	totalScore += libraries.get(i).potentialScore;
        }
        
        
        int time = 0;
        
        ArrayList<Library> sortedLibraries = new ArrayList<Library>();
        
        ArrayList<Library> signedUpLibraries = new ArrayList<Library>();
        
        ArrayList<Book> scannedBooks = new ArrayList<Book>();
        
        ArrayList<Book> sortedBooks = new ArrayList<Book>(); 
    	
    	boolean isSigningUp = false;
    	Library signingUpLibrary = null;
    	int signingUpTime = 0;
    	
    	int score = 0;
    	
    	// Sorting the libraries based on signup time
    	for(int s = 0; s < libraries.size(); s++){
    		Library shortest = libraries.get(0);
    		for(int k = s + 1; k < libraries.size() -1; k++) {
    			if(libraries.get(s).signUpTime < shortest.signUpTime) {
        			shortest = libraries.get(s);
        		}
    		}
    		sortedLibraries.add(shortest);
    	}
    	
    	
    	for(Library l : sortedLibraries) {
    		int n = l.books.length; 
            for (int i = 1; i < n; ++i) { 
                Book key = l.books[i]; 
                int j = i; 
                while (j > 0 && (l.books[j-1].getScore() < key.getScore())) { 
                	l.books[j] = l.books[j - 1]; 
                    j = j - 1; 
                } 
                l.books[j] = key; 
            } 

    	}

    	/*
    	for(int i = 0; i < sortedLibraries.size(); i++) {
    		Library key = sortedLibraries.get(0);
    		int j = i;
    		while(j > 0 && sortedLibraries.get(j-1).potentialScore < key.potentialScore 
    				&& sortedLibraries.get(j-1).potentialScore > average) {
    			Library temp = sortedLibraries.get(j-1);
    			sortedLibraries.remove(j);
    			sortedLibraries.add(j, temp);
    			j = j - 1;
    		}
    		sortedLibraries.remove(j);
    		sortedLibraries.add(j, key);
    	}
    	*/
    	

   
    	
    	System.out.println("Time given: " + scanDays);
    	for(time = 0; time <= scanDays; time++) {
    		
    		//System.out.println("Time - " + time);
    		//System.out.println();
    		
    		signingUpTime = signingUpTime -1;
    		if(signingUpTime == 0) {
    			signingUpLibrary.signedUp = true;
    			signedUpLibraries.add(signingUpLibrary);
    			isSigningUp = false;
    			//System.out.println("Library " + signingUpLibrary.libID + " finishes signing up");
    		}
    		
    		if(!isSigningUp) {
    			
    			for(int i = 0; i < sortedLibraries.size(); i++) {
    				/*
    				// We use this for example a, d, e
    				if(!libraries.get(i).signedUp) {
       					signingUpLibrary = libraries.get(i);
       					signingUpTime = libraries.get(i).signUpTime;
       					isSigningUp = true;
       					//System.out.println("Signing up library " + libraries.get(i).libID);
       				}
       				*/
    				
    				
    				
    				// We use this for example b, c ,f
    				if(!sortedLibraries.get(i).signedUp) {
       					signingUpLibrary = sortedLibraries.get(i);
       					signingUpTime = sortedLibraries.get(i).signUpTime;
       					isSigningUp = true;
       					//System.out.println("Signing up library " + libraries.get(i).libID);
       				}
       				
       			}
   			}
    		
    		
    		for(Library l : signedUpLibraries) {
    			int count = 0;
    			int temp = l.lastScannedIndex;
    			if(l.signedUp) {	
    				for(int k = 0; k < l.numBooksShippedDaily; k++) {
    					boolean scannedBefore = false;
    					if(scannedBooks.isEmpty()) {
							score += l.books[temp].getScore();
							scannedBooks.add(l.books[temp]);
							l.scannedBooks.add(l.books[temp]);
							count ++;
							//System.out.println("Library " + l.libID);
							//System.out.println("Book: " + l.books[temp].getBookID() + " scanned");
							//System.out.println();
						} else {
							while(count != l.numBooksShippedDaily && temp < l.books.length){
								for(int i = 0; i < scannedBooks.size(); i++) {
									if(l.books[temp].getBookID() == scannedBooks.get(i).getBookID()) {
										scannedBefore = true;
									}
								}
								if(!scannedBefore) {
									score += l.books[temp].getScore();
	    							scannedBooks.add(l.books[temp]);
	    							l.scannedBooks.add(l.books[temp]);
	    							count ++;
	    							//System.out.println("Library " + l.libID);
	    							//System.out.println("Book: " + l.books[temp].getBookID() + " scanned");
	    							//System.out.println();
								} else {
									l.lastScannedIndex++;
									temp = l.lastScannedIndex;
								}
								scannedBefore = false;
							}
						}
    				}
    			}
    		}

    	}
    	
    	System.out.println(score);
    	
    	for(int i = 0; i < signedUpLibraries.size(); i++) {
    		if(signedUpLibraries.get(i).scannedBooks.isEmpty()) {
    			signedUpLibraries.remove(i);
    		}
    	}
    
    	
    	/*
    	PrintWriter writer = null;
  	  	try {
            writer = new PrintWriter("e.out", "UTF-8");
            writer.println(signedUpLibraries.size());

            for(int i = 0; i < signedUpLibraries.size(); i++) {
            	writer.print(signedUpLibraries.get(i).libID + " ");
            	writer.println(signedUpLibraries.get(i).scannedBooks.size());
                for(int j = 0; j < signedUpLibraries.get(i).scannedBooks.size(); j++) {
                	writer.print(signedUpLibraries.get(i).scannedBooks.get(j).getBookID() + " ");
                }
                writer.println();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        */
        
    	
    }

}
