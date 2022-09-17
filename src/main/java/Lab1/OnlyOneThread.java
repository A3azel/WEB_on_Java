package Lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnlyOneThread {

    private static final String DIRECTORY_PATH = "C:\\Users\\Lenovo\\OneDrive\\Рабочий стол\\Всі лаби\\3 курс\\Java Web\\Lab1\\TestLab";
    private static final String RESULT_FILE_PATH = "C:\\Users\\Lenovo\\OneDrive\\Рабочий стол\\Всі лаби\\3 курс\\Java Web\\Lab1\\Lab1_Results.txt";

    public static void getResultFile(){
        File resultFile = new File(RESULT_FILE_PATH);
        if(resultFile.exists()){
            try {
                new FileWriter(RESULT_FILE_PATH).write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(DIRECTORY_PATH);
        if(!file.exists()){
            System.out.println("File is not found");
            return;
        }
        parsDirectory(file,resultFile);

    }

    public static void parsDirectory(File file,File resultFile){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File value : files) {
                parsDirectory(value, resultFile);
            }
        }
        if (file.isFile()){
            int resultOfChecking = 0;
            String absolutePath = file.getAbsolutePath();
            try {
                resultOfChecking = checkFile(absolutePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(resultOfChecking!=0){
            try {
                FileWriter fileWriter = new FileWriter(resultFile,true);
                fileWriter.write("File: " + file.getName() + " count of numbers: " + resultOfChecking + "\n");
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }

    }

    public static int checkFile(String filePath) throws FileNotFoundException {
        FileReader fr = new FileReader(filePath);
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
        }
        return countOfNumber;
    }

    public void printFileDate(){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(RESULT_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(fileReader);
        while (sc.hasNextLine()){
            System.out.println(sc.nextLine());
        }
    }
}
