/**
* Creates a hashmap that will use the regex pattern for the key and the count as the data.
• Iterates through each output file and adds/updates each the count for each regex
pattern to the hashmap.
• Iterates through the completed hashmap after each file has been processed and outputs
the final count for each pattern across all novels.
* @author <Jonathan Moreira Alsina>
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/
import java.io.*;
import java.util.HashMap;


public class WordCounter 
{
    
    /** Searches for the files with _wc.txt and adds up the count for each regex pattern
     * @param args
     */
    public static void main(String[] args) 
    {
        
        String directory_path = System.getProperty("user.dir");
        HashMap <String, Integer> total_count = new HashMap <String, Integer>();
        File directory = new File(directory_path);

        /**
         * Loops through all the files in the directory
         */
        for(File file : directory.listFiles())
        {
           
            if (file.getName().endsWith("_wc.txt"))
            {
                try
                {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    
                    /**
                     * Reads line by line of each file and copies the counter 
                     * from each regex pattern into a new map
                     */
                    while((line = reader.readLine()) != null)
                    {
                        
                        int index = line.lastIndexOf("|");
                        String pattern = line.substring(0, index-1);

                        int count = Integer.parseInt(line.substring(index+2));

                        if (!total_count.containsKey(pattern))
                        {
                            total_count.put(pattern,0);
                        }
                        total_count.put(pattern, total_count.get(pattern)+ count);

                        
                        
                    }

                    System.out.println(file.getName() + " done");
                    reader.close();
                }
                catch(IOException e){
                    e.printStackTrace();;
                }
            }
        }

        try
        {
            PrintWriter outWriter = new PrintWriter(new FileWriter("final_wc.txt"));

            for (String pattern : total_count.keySet()) {
                outWriter.println(pattern + "|" + total_count.get(pattern));
            }

            outWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
