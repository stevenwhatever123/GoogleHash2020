
import java.util.ArrayList;

public class Library {
    public int signUpTime;
    public int libID;
    public Book[] books;
    public int numBooksShippedDaily;
    public ArrayList<Book> sortedBooks = new ArrayList<Book>();
    public ArrayList<Book> scannedBooks = new ArrayList<Book>();
    public boolean signedUp = false;
    public int lastScannedIndex = 0;
    public int potentialScore = 0;
    
    public Library(int libID, Book[] books, int time, int numBooksShippedDaily) {
        this.libID = libID;
        this.books = books;
        this.signUpTime = time;
        this.numBooksShippedDaily = numBooksShippedDaily;
        potentialScore = calculate(books);
    }
    
    public ArrayList<Book> sortingBooks() {
    	for(int s = 0; s < books.length - 1; s++){
    		for(int k = 0; k < books.length - 1; k++){
    			if(books[k].getScore() > books[k + 1].getScore()){ 
    				sortedBooks.add(books[k]);
    			}       
    		}   
    	}
    	return sortedBooks;
    }
    
    public int calculate(Book[] book) {
    	int score = 0;
    	for(int i = 0; i < book.length; i++) {
    		boolean scannedBefore = false;
    		if(scannedBooks.isEmpty()) {
    			score += books[i].getScore();
				scannedBooks.add(books[i]);
    		}else {
    			for(int j = 0; j < scannedBooks.size(); j++) {
        			if(book[i].getBookID() == scannedBooks.get(j).getBookID()) {
        				scannedBefore = true;
        			}
        		}
    			if(!scannedBefore) {
					score += books[i].getScore();
					scannedBooks.add(books[i]);
				}
				scannedBefore = false;
    		}
    	}
    	scannedBooks.clear();
    	return score;
    }
}