import java.io.*;
import java.util.HashMap;


public class WordCounter 
{
    public static void main(String[] args) 
    {
        
        String directory_path = System.getProperty("user.dir");
        HashMap <String, Integer> total_count = new HashMap <String, Integer>();
        File directory = new File(directory_path);

        
        for(File file : directory.listFiles())
        {
           
            if (file.getName().endsWith("_wc.txt"))
            {
                try
                {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = "";
                    
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
