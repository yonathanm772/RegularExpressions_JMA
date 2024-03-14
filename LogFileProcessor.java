
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.io.*;


public class LogFileProcessor {

    private static int lines_parsed = 0;
    private static BufferedReader log_file_reader;
    private static BufferedReader regex_reader;
    private static PrintWriter output_writer;

    public LogFileProcessor()
    {

    }//end NovelProcessor

    
    public static HashMap <String, Integer> processIP(String logFile, String regexFile)
    {
        
        String line = "";
        String regex = regexFile;
        HashMap <String, Integer> iP_addresses = new HashMap <String,Integer>();
        
        
        try
        {
            log_file_reader = new BufferedReader(new FileReader(logFile));

            
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

    public static HashMap <String, Integer> processUsername(String logFile, String regexFile)
    {
        
        String line = "";
        String regex = regexFile;
        HashMap <String, Integer> usernames = new HashMap <String,Integer>();
        
        
        try
        {
            log_file_reader = new BufferedReader(new FileReader(logFile));
            
            
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

    public static int size(HashMap<String,Integer> map)
    {
        return map.size();
    }
  
    
    public static void printHashMap(HashMap<String,Integer> map){
        for(HashMap.Entry<String,Integer> entry: map.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void printDefault(HashMap<String,Integer> ipMap, HashMap<String,Integer> userMap)
    {
        System.out.println(lines_parsed + " lines in the log file were parsed.\n" + 
                "There are " + ipMap.size() +" unique IP addresses in the log.\n" + //
                "There are " + userMap.size() + " unique users in the log.");
    }
    
    

    public static void main(String[]args)
    {
        

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of the file: ");
        String log_file = scan.nextLine();

        String ip_pattern = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        String user_pattern = "user\\s[a-z]+";
        

        
        String answer = "";
        HashMap <String, Integer> ips = processIP(log_file, ip_pattern);
        HashMap <String, Integer> usernames = processUsername(log_file, user_pattern);
        

        
        do
        {
            System.out.println("Please enter the print flag: "
            +"\n0: Print the default output"
            +"\n1: IP adresses and default output"  
            +"\n2: Usernames and default output"
            +"\nQ: Quit");
            answer = scan.nextLine().toLowerCase();
            

            switch(answer.charAt(0))
            {
                case '0':
                    printDefault(ips, usernames);
                    continue;
                case '1':
                    printHashMap(ips);
                    printDefault(ips, usernames);
                    continue;
                case '2':
                    printHashMap(ips);
                    printDefault(ips, usernames);
                    continue;
                case ('q'): 
                    System.out.println("Goodbye!");
                    break;
                default:    
                    System.out.println("Please enter a valid answer");

            }
        }while(answer.charAt(0) != 'q');
        
        
        scan.close();

    }//end main

}//end NovelProcessor