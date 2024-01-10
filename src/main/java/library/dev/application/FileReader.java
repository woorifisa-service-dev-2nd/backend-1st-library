package library.dev.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.*;

public class FileReader {
    static Logger logger = Logger.getLogger(Application.class.getName());
    public FileReader () throws IOException {
        Filter inputFilter = (logRecord) -> logRecord.getLevel().equals(Level.WARNING);
        Formatter formatter = new SimpleFormatter();
        String fileDate = new SimpleDateFormat("_yyyyMMdd").format(new Date());
        Handler handler = new FileHandler("logFiles/"+fileDate+"-library.log");
        handler.setFormatter(formatter);
        logger.setFilter(inputFilter);
        logger.addHandler(handler);
    }

    public List<Books> bookAnalyze(final String fileName) {
        logger.info("bookAnalyze() is called");
        List<Books> list = new ArrayList<>();

        // 경로에 "resources/" 접두사 추가
        final Path path = Paths.get("src/main/resources/"+fileName);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] arr = line.split(",");
                String bookName = arr[0];
                String category = arr[1];
                String yesorno = arr[2];
                Books book = new Books(bookName, category, yesorno);
                list.add(book);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Members> memberAnalyze(final String fileName) {
        logger.info("memberAnalyze() is called");
        List<Members> list = new ArrayList<>();

        final Path path = Paths.get("src/main/resources/"+fileName);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] arr = line.split("\t");
                String name = arr[0];
                String bookName = arr[1];
                String expectedRD = arr[2];
                String realRD = arr[3];
                Members member = new Members(name, bookName, expectedRD, realRD);
                list.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
