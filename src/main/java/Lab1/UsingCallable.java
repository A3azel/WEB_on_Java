package Lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsingCallable implements Callable<Void> {
    private final String directoryPath;
    private final ExecutorService executorService;
    private final String resultFilePath;

    public UsingCallable(String directoryPath, ExecutorService executorService, String resultFilePath) {
        this.directoryPath = directoryPath;
        this.executorService = executorService;
        this.resultFilePath = resultFilePath;
    }

    public String checkFile(File file) throws FileNotFoundException {
        FileReader fr = new FileReader(file);
        Scanner sc = new Scanner(fr);
        ArrayList<String> fileContent = new ArrayList<>();
        int countOfNumber = 0;
        while (sc.hasNext()){
            fileContent.add(sc.next());
        }
        Pattern pattern = Pattern.compile("(\\b)\\d+(\\.)*(\\d*|\\s)(\\b)");
        Matcher matcher = pattern.matcher(fileContent.toString());
        while (matcher.find()){
            countOfNumber++;
            //System.out.println(matcher.group());
        }
        if(countOfNumber!=0){
            try {
                FileWriter fileWriter = new FileWriter(resultFilePath,true);
                fileWriter.write("File: " + file.getName() + " count of numbers: " + countOfNumber + "\n");
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "File: " + file.getName() + " count of numbers: " + countOfNumber + "\n";
        }
        return "";
    }

    public void printFileDate(){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(resultFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(fileReader);
        while (sc.hasNextLine()){
            System.out.println(sc.nextLine());
        }
    }

    @Override
    public Void call() throws Exception {
        File file = new File(directoryPath);
        if(!file.exists()){
            System.out.println("File is not found");
        }else {
            File[] files = file.listFiles();
            ArrayList< Future<Void> > result = new ArrayList<>();
            for (File value : files) {
                if (value.isDirectory()) {
                    UsingCallable usingCallable = new UsingCallable(value.getAbsolutePath(),executorService, resultFilePath);
                    Future<Void> future =  executorService.submit(usingCallable);
                    result.add(future);
                } else {
                        checkFile(value);
                }
                for (Future<Void> rez : result)
                    rez.get();
            }
        }
        return null;
    }
}
