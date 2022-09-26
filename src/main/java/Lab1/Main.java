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

        //Runnable

        /*Scanner sc = new Scanner(System.in);
        System.out.print("Enter directory -> ");
        String dir = sc.nextLine();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 10, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        UsingRunnable fileParser = new UsingRunnable(dir, pool, RESULT_FILE_PATH);
        pool.execute(fileParser);
        while (pool.getActiveCount()!=0){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
        HelpedMethods.printFileDate(RESULT_FILE_PATH);*/

        //Callable

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter directory -> ");
        String dir = sc.nextLine();
        ExecutorService pool = Executors.newCachedThreadPool();
        UsingCallable fileParser = new UsingCallable(dir, pool, RESULT_FILE_PATH);
        Future<Void> result = (Future<Void>) pool.submit(fileParser);
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        HelpedMethods.printFileDate(RESULT_FILE_PATH);
    }
}
