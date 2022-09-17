package Lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser implements Runnable{
    private final String directoryPath;
    private final ExecutorService executorService;
    private final String resultFilePath;

    public FileParser(String directoryPath, ExecutorService executorService, String resultFilePath) {
        this.directoryPath = directoryPath;
        this.executorService = executorService;
        this.resultFilePath = resultFilePath;
    }

    public void checkFile(File file, String resultFilePath) throws FileNotFoundException {
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
        }
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
                FileParser fileParser = new FileParser(value.getAbsolutePath(),executorService, resultFilePath);
                executorService.execute(fileParser);
                //executorService.submit(fileParser);
            } else {
                try {
                    checkFile(value, resultFilePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
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
}
