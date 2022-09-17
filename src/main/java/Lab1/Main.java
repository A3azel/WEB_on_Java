package Lab1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    private static final String DIRECTORY_PATH = "C:\\Users\\Lenovo\\OneDrive\\Рабочий стол\\Всі лаби\\3 курс\\Java Web\\Lab1\\TestLab";
    private static final String RESULT_FILE_PATH = "C:\\Users\\Lenovo\\OneDrive\\Рабочий стол\\Всі лаби\\3 курс\\Java Web\\Lab1\\Lab1_Results.txt";
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
        //ExecutorService pool = Executors.newCachedThreadPool();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        FileParser fileParser = new FileParser(dir, pool, RESULT_FILE_PATH);
        pool.execute(fileParser);
        while (pool.getActiveCount()!=0){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        pool.shutdown();
        fileParser.printFileDate();
    }
}
