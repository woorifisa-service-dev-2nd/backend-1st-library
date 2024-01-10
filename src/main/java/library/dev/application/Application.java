package library.dev.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.*;

public class Application {
    static Logger logger = Logger.getLogger(Application.class.getName());
    FileReader fileReader = new FileReader();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public Application () throws IOException {
        Filter inputFilter = (logRecord) -> logRecord.getMessage().contains("INPUT") || logRecord.getLevel().equals(Level.WARNING);
        Formatter formatter = new SimpleFormatter();
        String fileDate = new SimpleDateFormat("_yyyyMMdd").format(new Date());
        Handler handler = new FileHandler("logFiles/"+fileDate+"-library.log");
        handler.setFormatter(formatter);
        logger.setFilter(inputFilter);
        logger.addHandler(handler);
    }
    public void numberOne() {
        logger.info("numberOne() is called");
        final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Books> books = fileReader.bookAnalyze("books.txt");
        List<Members> members = fileReader.memberAnalyze("member.txt");

        System.out.println("회원 이름을 입력하시오.");

        String name = null;
        try {
            name = br.readLine();
            logger.info("[INPUT DATA] name = "+name);
        } catch (IOException e) {
            logger.warning("IOException occured while using BufferedReader");
            throw new RuntimeException(e);
        }

        for (int i = 0; i < members.size(); i++) {
            String category = "";
            if (name.equals(members.get(i).name.trim())) {
                String bookName = members.get(i).bookName.trim();
                LocalDate expectedRD = LocalDate.parse(members.get(i).expectedRD.trim(), DATE_PATTERN);
                LocalDate realRD = LocalDate.parse(members.get(i).realRD.trim(), DATE_PATTERN);

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
                numberOne();
            }

        }
    }

    public void numberTwo(){
        logger.info("numberTwo() is called");
        List<Books> books = fileReader.bookAnalyze("books.txt");
        List<Members> members = fileReader.memberAnalyze("member.txt");
        System.out.println("카테고리를 입력하세요.");
        String category = null;
        try {
            category = br.readLine();
            logger.info("[INPUT DATA] category = "+ category);
        } catch (IOException e) {
            logger.warning("IOException occured while using BufferedReader");
            throw new RuntimeException(e);
        }
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
            numberTwo();
        }

    }

    public void selectNumber(){
        logger.info("selectNumber() is called");
        System.out.println("회원정보를 알려면 '1'을 카테고리를 알려면 '2'를 입력하시오.");

        int number = 0;
        try {
            number = Integer.parseInt(br.readLine());
            logger.info("[INPUT DATA] number = "+number);
        } catch (IOException e) {
            logger.warning("IOException occured while using BufferedReader");
            throw new RuntimeException(e);
        }

        if (number == 1) {
            numberOne();
        } else if (number == 2) {
            numberTwo();
        } else {
            System.out.println("잘못 입력하였습니다. 다시 입력해주세요.");
            selectNumber();
        }
    }

}
