package library.dev.application;

public class Members {
    String name;
    String bookName;
    String expectedRD;
    String realRD;

    Members(String name, String bookName, String expectedRD, String realRD) {
        this.name = name;
        this.bookName = bookName;
        this.expectedRD = expectedRD;
        this.realRD = realRD;
    }
}
