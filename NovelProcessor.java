
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.io.*;


public class NovelProcessor {

    private static BufferedReader novel_reader;
    private static BufferedReader regex_reader;
    private static PrintWriter output_writer;

    public NovelProcessor()
    {

    }//end NovelProcessor

    
    public static HashMap <String, Integer> processNovel(String novelFile, String regexFile)
    {
        
        String line = "";
        HashMap <String, Integer> word_count = new HashMap <String,Integer>();

        try
        {
            novel_reader = new BufferedReader(new FileReader(novelFile));
            regex_reader = new BufferedReader(new FileReader(regexFile));
            
            while ((line = regex_reader.readLine()) != null)
            {
                word_count.put(line.trim(), 0);
            }
            
            while((line = novel_reader.readLine()) != null)
            {

                for(String regexString : word_count.keySet())
                {

                    Pattern pattern = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher= pattern.matcher(line);

                    while(matcher.find())
                    {
                        word_count.put(regexString, word_count.get(regexString)+1);
                    }

                }
            }
           
        }

        catch (IOException e){
            System.out.println("File not Found");
        }

        return word_count;
    }

    
    public static void outputFile( HashMap<String,Integer> wordMap, String outputFileName)
    {
     
        try
        {
            
            output_writer = new PrintWriter(new FileWriter(outputFileName.replaceAll(".txt","_wc.txt")));
            
            for( HashMap.Entry<String, Integer> entry : wordMap.entrySet())
            {           
                output_writer.println(entry.getKey() + " | " + entry.getValue());
            }
          
            output_writer.close();
        }

        catch(IOException e){
            e.printStackTrace();
        }      
    }
    

    public static void main(String[]args)
    {

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of the file: ");
        String novel_file = scan.nextLine();
        
        System.out.println("Please enter the name of the regex file: ");
        String regex_file = scan.nextLine();

        HashMap <String, Integer> word_count = processNovel(novel_file, regex_file);

        outputFile(word_count, novel_file);
        
        scan.close();

    }//end main

}//end NovelProcessor