
/**
* Searches a log file and stores unique IP address and counts in a Hashmap 
  or stores unique usernames and counts in a separate
  Hashmap.
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


public class LogFileProcessor 
{

    private static int lines_parsed = 0;
    private static BufferedReader log_file_reader;

    /**
     * Default constructor
     */
    public LogFileProcessor()
    {

    }
    
    /** 
     * Searches for unique IP addresses and stores them in a hashmap
     * along with their occurrences
     * @param logFile
     * @param regexFile
     * @return HashMap<String, Integer>
     */
    public static HashMap <String, Integer> processIP(String logFile, String regexFile)
    {
        
        String line = "";
        String regex = regexFile;
        HashMap <String, Integer> iP_addresses = new HashMap <String,Integer>();
        
        
        try
        {
            log_file_reader = new BufferedReader(new FileReader(logFile));

            
            /**
             * Searches for the unique IP addresses and keeps a count of occurrences
             * and it stores them into a map
             */
            while((line = log_file_reader.readLine()) != null)
            {
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher= pattern.matcher(line);
                lines_parsed++;

                while(matcher.find())
                {
                    String match = matcher.group();
                    if(!iP_addresses.containsKey(match))
                    {

                        iP_addresses.put(match, 0);
                    }
                    iP_addresses.put(match, iP_addresses.get(match)+1);
                  

                }
                
            }
           
        }

        catch (IOException e){
            System.out.println("File not Found");
        }

        return iP_addresses;
    }

    
    /** 
     * Searches for unique usernames and stores them in a hashmap
     * along with their occurrences
     * @param logFile
     * @param regexFile
     * @return HashMap<String, Integer>
     */
    public static HashMap <String, Integer> processUsername(String logFile, String regexFile)
    {
        
        String line = "";
        String regex = regexFile;
        HashMap <String, Integer> usernames = new HashMap <String,Integer>();
        
        
        try
        {
            log_file_reader = new BufferedReader(new FileReader(logFile));
            
            /**
             * Searches for the unique usernames and keeps a count of occurrences
             * and it stores them into a map
             */

            while((line = log_file_reader.readLine()) != null)
            {
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher= pattern.matcher(line);

                while(matcher.find())
                {
                    String[] match = matcher.group().split(" ");
                    String username = match[1];
                    if(!usernames.containsKey(username))
                    {

                        usernames.put(username, 0);
                    }
                    usernames.put(username, usernames.get(username)+1);
                  

                }
            }
           
        }

        catch (IOException e){
            System.out.println("File not Found");
        }

        return usernames;
    }

    
    /** 
     * Retuns the size of the map
     * @param map
     * @return int
     */
    public static int size(HashMap<String,Integer> map)
    {
        return map.size();
    }
  
    
    
    /** 
     * Print the content of the HashMap
     * @param map
     */
    public static void printHashMap(HashMap<String,Integer> map){
        for(HashMap.Entry<String,Integer> entry: map.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    
    /** 
     * Prints the size of both maps along with the number of lines parsed
     * @param ipMap
     * @param userMap
     */
    public static void printDefault(HashMap<String,Integer> ipMap, HashMap<String,Integer> userMap)
    {
        System.out.println(lines_parsed + " lines in the log file were parsed.\n" + 
                "There are " + ipMap.size() +" unique IP addresses in the log.\n" + //
                "There are " + userMap.size() + " unique users in the log.");
    }
    
    

    public static void main(String[]args)
    {

        String log_file = args[0];

        String ip_pattern = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        String user_pattern = "user\\s[a-z]+";
        
        String answer = "";
        HashMap <String, Integer> ips = processIP(log_file, ip_pattern);
        HashMap <String, Integer> usernames = processUsername(log_file, user_pattern);
        
        answer = args[1];
            

        switch(answer.charAt(0))
        {
            case '0':
                printDefault(ips, usernames);
                break;
            case '1':
                printHashMap(ips);
                printDefault(ips, usernames);
                break;
            case '2':
                printHashMap(usernames);
                printDefault(ips, usernames);
                break;
            default:    
                System.out.println("Please enter a valid answer");

        }

    }//end main

}
