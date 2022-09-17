package Lab1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String DIRECTORY_PATH = "DIRECTORY_PATH";
    private static final String RESULT_FILE_PATH = "RESULT_FILE_PATH";
    public static void main(String[] args) {
        File resultFile = new File(RESULT_FILE_PATH);
        if(resultFile.exists()){
            try {
                new FileWriter(RESULT_FILE_PATH).write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter directory -> ");
        String dir = sc.nextLine();
        ExecutorService pool = Executors.newCachedThreadPool();
        FileParser fileParser = new FileParser(dir,pool,RESULT_FILE_PATH);
        fileParser.run();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        fileParser.printFileDate();

        //OnlyOneThread.getResultFile();

    }
}
