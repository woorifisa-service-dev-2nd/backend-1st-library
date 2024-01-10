package library.dev.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.selectNumber();
    }
}
