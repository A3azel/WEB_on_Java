package Lab1;

import java.io.*;
import java.util.concurrent.ExecutorService;

public class UsingRunnable implements Runnable{
    private final String directoryPath;
    private final ExecutorService executorService;
    private final String resultFilePath;

    public UsingRunnable(String directoryPath, ExecutorService executorService, String resultFilePath) {
        this.directoryPath = directoryPath;
        this.executorService = executorService;
        this.resultFilePath = resultFilePath;
    }

    @Override
    public void run(){
        File file = new File(directoryPath);
        if(!file.exists()){
            System.out.println("File is not found");
            return;
        }
        File[] files = file.listFiles();
        for (File value : files) {
            if (value.isDirectory()) {
                UsingRunnable fileParser = new UsingRunnable(value.getAbsolutePath(),executorService, resultFilePath);
                executorService.execute(fileParser);
            }
            if(value.isFile()){
                try {
                    HelpedMethods.checkFile(value,resultFilePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
