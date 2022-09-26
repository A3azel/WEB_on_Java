package Lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class UsingCallable implements Callable<Void> {
    private final String directoryPath;
    private final ExecutorService executorService;
    private final String resultFilePath;

    public UsingCallable(String directoryPath, ExecutorService executorService, String resultFilePath) {
        this.directoryPath = directoryPath;
        this.executorService = executorService;
        this.resultFilePath = resultFilePath;
    }

    @Override
    public Void call() throws Exception {
        File file = new File(directoryPath);
        if(!file.exists()) {
            System.out.println("File is not found");
            return null;
        }
        File[] files = file.listFiles();
        ArrayList< Future<Void> > result = new ArrayList<>();
        for (File value : files) {
            if (value.isDirectory()) {
                UsingCallable usingCallable = new UsingCallable(value.getAbsolutePath(),executorService, resultFilePath);
                Future<Void> future =  executorService.submit(usingCallable);
                result.add(future);
            } else {
                HelpedMethods.checkFile(value,resultFilePath);
            }
            for (Future<Void> rez : result)
                rez.get();
        }
        return null;
    }
}
