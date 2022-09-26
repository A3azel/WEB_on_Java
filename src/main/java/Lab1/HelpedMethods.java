package Lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpedMethods {

    public static void printFileDate(String resultFilePath){
        try {
            FileReader fileReader = new FileReader(resultFilePath);
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine()){
                System.out.println(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void checkFile(File file, String resultFilePath) throws FileNotFoundException {
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
}
