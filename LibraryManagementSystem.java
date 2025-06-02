
enum AccountStatus {
    ACTIVE, BLOCKED
}

enum BookStatus {
    AVAILABLE, ISSUED, RESERVED, LOST
}

abstract class User {
    protected String userID;
    protected String name;
    protected String email;
    protected String phone;
    protected Account account;

    public abstract List<Book> searchBook(String title);
    public void viewProfile() {
        System.out.println("Name: " + name + ", Email: " + email);
    }
}

class Member extends User {
    private Date membershipDate;
    private List<BookItem> borrowedBooks = new ArrayList<>();

    @Override
    public List<Book> searchBook(String title) {
        return new ArrayList<>();
    }

    public boolean borrowBook(BookItem bookItem) {
        if (bookItem.getStatus() == BookStatus.AVAILABLE) {
            borrowedBooks.add(bookItem);
            bookItem.setStatus(BookStatus.ISSUED);
            return true;
        }
        return false;
    }

    public boolean returnBook(BookItem bookItem) {
        borrowedBooks.remove(bookItem);
        bookItem.setStatus(BookStatus.AVAILABLE);
        return true;
    }

    public boolean reserveBook(BookItem bookItem) {
        if (bookItem.getStatus() == BookStatus.AVAILABLE) {
            bookItem.setStatus(BookStatus.RESERVED);
            return true;
        }
        return false;
    }
}

class Librarian extends User {

    @Override
    public List<Book> searchBook(String title) {
        return new ArrayList<>();
    }

    public void addBook(Book book) {
        // Add book to catalog
    }

    public void removeBook(String bookID) {
        // Remove book logic
    }

    public void blockMember(Member member) {
        member.account.setStatus(AccountStatus.BLOCKED);
    }

    public boolean issueBook(BookItem bookItem, Member member) {
        return member.borrowBook(bookItem);
    }
}

class Book {
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private String category;

    public String getBookInfo() {
        return title + " by " + author;
    }
}

class BookItem extends Book {
    private String barcode;
    private boolean isReferenceOnly;
    private BookStatus status;
    private Date dueDate;
    private String location;

    public boolean checkout(Member member) {
        if (!isReferenceOnly && status == BookStatus.AVAILABLE) {
            status = BookStatus.ISSUED;
            return true;
        }
        return false;
    }

    public boolean returnBook() {
        status = BookStatus.AVAILABLE;
        return true;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}

class Account {
    private String username;
    private String password;
    private AccountStatus status;

    public boolean resetPassword(String newPassword) {
        this.password = newPassword;
        return true;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}

class Fine {
    private String fineID;
    private double amount;
    private Date issueDate;

    public double calculateFine(int daysLate) {
        double dailyRate = 1.0;
        return daysLate * dailyRate;
    }
}
