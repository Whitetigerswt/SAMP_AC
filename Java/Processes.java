
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author My Account
 */
public class Processes {
    /*public static java.util.List<String> listRunningProcesses() {
        java.util.List<String> processList = new ArrayList<String>();
        try {

            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                    + "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
                    + "Set service = locator.ConnectServer()\n"
                    + "Set processes = service.ExecQuery _\n"
                    + " (\"select ExecutablePath from Win32_Process\")\n"
                    + "For Each process in processes\n"
                    + "wscript.echo process.ExecutablePath \n"
                    + "Next\n"
                    + "Set WSHShell = Nothing\n";

            fw.write(vbs);
            fw.close();
            //long tickcount = System.currentTimeMillis();
            Process p = Runtime.getRuntime().exec("cscript //Nologo " + file.getPath());
            if((System.currentTimeMillis() - tickcount) > 1000) {
                SAMP_AC.println("Could not complete request, Do you have a firewall open?");
                SAMP_AC.println("We're done here.");
                SAMP_AC.println("Closing in 10 seconds...");
                
                SAMP_AC.stopThreadTemp(100000);
                System.exit(0);
            }
            InputStreamReader in = new InputStreamReader(p.getInputStream());
            BufferedReader input = new BufferedReader(in);
            String line = "";            
            
            while ((line = input.readLine()) != null) { 
                processList.add(line);
            }
            input.close();
            file.delete();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return processList;
    }*/
    public static String listRunningProcesses() throws Exception {
        /*java.util.List<String> processList = new ArrayList<String>();
        
        String[] theString = cplusplus.GetRunningProcesses( ).split("[\r\n]");
        for(int i=0; i < theString.length; ++i) { 
            if(theString[i].length() < 1) continue;

            processList.add(theString[i]);     
        } 
        //System.out.println("Got running processes");*/
        String test = cplusplus.GetRunningProcesses();
        //System.out.println("PROCESSES: " + test);
        return test;
    }
    public static boolean isInteger( String input )  
    {  
        try  
        {  
            Integer.parseInt( input );  
            return true;  
        }  
        catch( Exception e )  
        {  
            return false;  
        }  
    }  
    /*public static String listGTAProcess() {
        //java.util.List<String> processList = new ArrayList<String>();
        String t = null;
        try {

            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                    + "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
                    + "Set service = locator.ConnectServer()\n"
                    + "Set processes = service.ExecQuery _\n"
                    + " (\"select ExecutablePath from Win32_Process\")\n"
                    + "For Each process in processes\n"
                    + "wscript.echo process.ExecutablePath \n"
                    + "Next\n"
                    + "Set WSHShell = Nothing\n";

            fw.write(vbs);
            fw.close();
            //long tickcount = System.currentTimeMillis();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            if((System.currentTimeMillis() - tickcount) > 1000) {
                SAMP_AC.println("Could not complete request, Do you have a firewall open?");
                SAMP_AC.println("We're done here.");
                SAMP_AC.println("Closing in 10 seconds...");
                
                SAMP_AC.stopThreadTemp(100000);
                System.exit(0);
            }
            BufferedReader input =
                new BufferedReader
                (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                //processList.add(line);
                if(line.contains("gta_sa.exe"))
                {
                    input.close();
                    file.delete();
                    return line;
                }
            }
            input.close();
            file.delete();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }*/
    
    public static String listGTAProcess() {
        
        String[] theString = cplusplus.GetRunningProcesses().split("[\r\n]");
        for(int i=0; i < theString.length; ++i) { 
            if(theString[i].length() < 1) continue;
            if(theString[i].endsWith("gta_sa.exe")) {
                return theString[i];
            }
        }  
        
        return null;
    } 
    
     /*public static java.util.List<String> listRunningProcessesDesc() {
        java.util.List<String> processList = new ArrayList<String>();
        try {

            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                    + "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
                    + "Set service = locator.ConnectServer()\n"
                    + "Set processes = service.ExecQuery _\n"
                    + " (\"select Description from Win32_Process\")\n"
                    + "For Each process in processes\n"
                    + "wscript.echo process.Description\n"
                    + "Next\n"
                    + "Set WSHShell = Nothing\n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                new BufferedReader
                (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
            file.delete();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return processList;
    }*/
}
