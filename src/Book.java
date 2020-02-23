
public class Book {
	
	public boolean isScanned = false;
    private int score;
    private int bookID;

    public Book(int bookID, int score) {
        this.score = score;
        this.bookID = bookID;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getBookID() {
        return bookID;
    }
    
    public String toString() {
    	return "BookID: " + bookID + ", Score: " + score + "\n";
    }

}