package library.dev.application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public List<Books> bookAnalyze(final String fileName) {
        List<Books> list = new ArrayList<>();

        // 경로에 "resources/" 접두사 추가
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("resources/" + fileName)) {
            if (is == null) {
                System.out.println("Failed to load resource: " + fileName);
                return list;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                String bookName = arr[0];
                String category = arr[1];
                String yesorno = arr[2];
                Books book = new Books(bookName, category, yesorno);
                list.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Members> memberAnalyze(final String fileName) {
        List<Members> list = new ArrayList<>();

        // 경로에 "resources/" 접두사 추가
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("resources/" + fileName)) {
            if (is == null) {
                System.out.println("Failed to load resource: " + fileName);
                return list;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
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
