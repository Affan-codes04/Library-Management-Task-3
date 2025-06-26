import java.util.*;

class Book {
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issueBook() {
        isIssued = true;
    }

    public void returnBook() {
        isIssued = false;
    }

    public String toString() {
        return title + " by " + author + (isIssued ? " [Issued]" : " [Available]");
    }
}

class User {
    private String name;
    private int id;
    private List<Book> issuedBooks;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.issuedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public int getId(){
        return id;
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
        book.issueBook();
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
        book.returnBook();
    }

    public void listIssuedBooks() {
        if (issuedBooks.isEmpty()) {
            System.out.println(name + " has no issued books.");
        } else {
            System.out.println(name + "'s Issued Books:");
            for (Book b : issuedBooks) {
                System.out.println("- " + b.getTitle());
            }
        }
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public void issueBook(String title, int userId) {
        Book bookToIssue = null;
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title) && !b.isIssued()) {
                bookToIssue = b;
                break;
            }
        }

        if (bookToIssue == null) {
            System.out.println("Book not available or already issued.");
            return;
        }

        for (User u : users) {
            if (u.getId() == userId) {
                u.issueBook(bookToIssue);
                System.out.println("Book '" + title + "' issued to " + u.getName());
                return;
            }
        }

        System.out.println("User not found.");
    }

    public void returnBook(String title, int userId) {
        for (User u : users) {
            if (u.getId() == userId) {
                for (Book b : books) {
                    if (b.getTitle().equalsIgnoreCase(title) && b.isIssued()) {
                        u.returnBook(b);
                        System.out.println("Book '" + title + "' returned by " + u.getName());
                        return;
                    }
                }
            }
        }
        System.out.println("Could not return the book.");
    }

    public void displayBooks() {
        System.out.println("\n Library Books:");
        for (Book b : books) {
            System.out.println("- " + b);
        }
    }
}

public class LibraryManagementSystem {

    public static void main(String[] args) {
        Library library = new Library();

        // Add books
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien"));
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("Java Basics", "Affan Bhai"));

        // Add users
        User u1 = new User("Ali", 101);
        User u2 = new User("Sara", 102);

        library.registerUser(u1);
        library.registerUser(u2);

        library.displayBooks();

        // Issue books
        library.issueBook("1984", 101);
        library.issueBook("The Hobbit", 102);
        library.issueBook("The Hobbit", 101); // Already issued

        library.displayBooks();

        // Return books
        library.returnBook("1984", 101);
        library.displayBooks();

        // Show user's issued books
        u1.listIssuedBooks();
        u2.listIssuedBooks();
    }
}
