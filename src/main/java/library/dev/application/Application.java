package library.dev.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Application {

    public static void numberOne(BufferedReader br) throws IOException {
        final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        FileReader fileReader = new FileReader();
        List<Books> books = fileReader.bookAnalyze("books.txt");
        List<Members> members = fileReader.memberAnalyze("member.txt");
        String bookName = "";
        String category = "";
        LocalDate expectedRD = null;
        LocalDate realRD = null;

        System.out.println("회원 이름을 입력하시오.");

        String name = br.readLine();

        for (int i = 0; i < members.size(); i++) {
            if (name.equals(members.get(i).name.trim())) {
                bookName = members.get(i).bookName.trim();
                expectedRD = LocalDate.parse(members.get(i).expectedRD.trim(), DATE_PATTERN);
                realRD = LocalDate.parse(members.get(i).realRD.trim(), DATE_PATTERN);

                for (int j = 0; j < books.size(); j++) {
                    if (bookName.equals(books.get(j).bookName.trim())) {
                        category = books.get(j).category.trim();
                    }
                }
                if (expectedRD.isAfter(realRD)) {
                    System.out.println("=========================================");
                    System.out.println("대출한 책 : " + bookName);
                    System.out.println("카테고리 : " + category);
                    System.out.println("남은 대출기간 : " + realRD.until(expectedRD, ChronoUnit.DAYS) );
                    System.out.println("대출 여부(가능)");
                    System.out.println("=========================================");
                } else {
                    System.out.println("=========================================");
                    System.out.println("대출한 책 : " + bookName);
                    System.out.println("카테고리 : " + category);
                    System.out.println("대출 연체일 : " + expectedRD.until(realRD, ChronoUnit.DAYS));
                    System.out.println("대출 여부(불가능)");
                    System.out.println("=========================================");
                }
                break;
            }
            if (i == members.size() - 1) {
                System.out.println("등록된 회원이 없습니다. 다시 입력해주세요.");
                numberOne(br);
            }

        }
    }

    public static void numberTwo(BufferedReader br) throws IOException{
        FileReader fileReader = new FileReader();
        List<Books> books = fileReader.bookAnalyze("books.txt");
        List<Members> members = fileReader.memberAnalyze("member.txt");
        System.out.println("카테고리를 입력하세요.");
        String category = br.readLine();
        int count = 0;
        for (int i = 0; i < books.size(); i++) {
            if (category.equals(books.get(i).category.trim())) {
            	count++;
                System.out.println("=========================================");
                System.out.println("제목 : " + books.get(i).bookName.trim() +", 카테고리 : "
                        + books.get(i).category.trim() + ", 대출 가능 여부 : " + books.get(i).yesorno.trim());
                System.out.println("=========================================");
                
            }
         
        }
        if (count == 0) {
            System.out.println("등록된 카테고리가 없습니다. 다시 입력해주세요.");
            numberTwo(br);
        }

    }

    public static void selectNumber(BufferedReader br) throws IOException{
        System.out.println("회원정보를 알려면 '1'을 카테코리를 하려면 '2'를 입력하시오.");
        
        char number = br.readLine().charAt(0);
        
        if (number == '1') {
            numberOne(br);
        } else if (number == '2') {
            numberTwo(br);
        } else {
            System.out.println("잘못 입력하였습니다. 다시 입력해주세요.");
            selectNumber(br);
        }
    }

}
