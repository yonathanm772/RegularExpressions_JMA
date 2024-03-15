/**
* It accepts two inputs from the command-line, 
1) the name of the file to process (e.g. the novel) and 
2) a file with a list of regex patterns based on the word list to search 
The program searches for each pattern listed in the file and then writes the pattern as well as
the number of times that pattern appeared in the file into an output file.
* @author <Jonathan Moreira Alsina>
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.io.*;


public class NovelProcessor {

    private static BufferedReader novel_reader;
    private static BufferedReader regex_reader;
    private static PrintWriter output_writer;

    /**
     * Default Constructor
     */
    public NovelProcessor()
    {

    }//end NovelProcessor

    
    
    /** 
     * It processes the novel and searches for words given by the pattern
     * from the regex file, and put them into a hashmap along with their
     * occurrences
     * @param novelFile
     * @param regexFile
     * @return HashMap<String, Integer>
     */
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

    
    
    /** 
     * It creates a file with the regex patterns along with their counts
     * @param wordMap
     * @param outputFileName
     */
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

        String novel_file = args[0];
        String regex_file = args[1];
        

        HashMap <String, Integer> word_count = processNovel(novel_file, regex_file);

        outputFile(word_count, novel_file);
        System.out.println("File has been created");
    }

}