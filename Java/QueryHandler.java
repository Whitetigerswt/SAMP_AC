/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.TrayIcon;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import javax.net.ssl.HttpsURLConnection;
import java.text.*;


/**
 *
 * @author My PC
 */
public class QueryHandler extends SAMP_AC {
    
    
    private static final int ID_OFFSET = 5000;
    
    private static final int ID_CHECK_UPDATE        = ID_OFFSET;
    private static final int ID_CHECK_BAN           = ID_OFFSET+1;
    private static final int ID_SAMP_HASH           = ID_OFFSET+2;
    private static final int ID_CHECK_PROCESS       = ID_OFFSET+3;
    private static final int ID_CHECK_INJECTED      = ID_OFFSET+4;
    private static final int ID_CHECK_ASI           = ID_OFFSET+5;
    private static final int ID_GET_AUTOARENA       = ID_OFFSET+6;
    private static final int ID_GET_THREAD_WAIT     = ID_OFFSET+7;
    public static String Website = "https://gator3016.hostgator.com/~maarij94/";

    public static final boolean DEBUG_PHP = true;
    
    public static boolean banned = false;
    public static boolean banned_temp = false;
    
    private static long ms = System.currentTimeMillis();
    
    static {
        
        getWebsite();
    }
    public static void getWebsite() {
        try {
            URL autoIP = new URL("https://raw.github.com/Whitetigerswt/sampac_server_api/master/web.txt");
            BufferedReader in = new BufferedReader( new InputStreamReader(autoIP.openStream()));
            String ip_address = (in.readLine()).trim();
            if(ip_address.length() == 0) return;
            
            Website = ip_address;

        } catch(Exception e) {  }
    }
    
    public static void sendPulse(String serverinfo, double x, double y, double z) {
        if(DEBUG_PHP) System.out.println("been " + (int) Math.round((System.currentTimeMillis() - ms) / 1000L) + " seconds since last pulse.");
        ms = System.currentTimeMillis();
        sendRawQuery("pulse.php", "serverinfo=" + serverinfo + "&x=" + x + "&y=" + y + "&z=" + z, 0);
    }
    
    
    
    public static void getAutoArenaIP() {
        try {
            URL autoIP = new URL("" + Website + "AC_API/internal/autoarena.php");
            BufferedReader in = new BufferedReader( new InputStreamReader(autoIP.openStream()));
            String ip_address = (in.readLine()).trim();
            if(ip_address.length() == 0) return;
            
            SAMP_AC.AutoArenaIP = ip_address;
            

        } catch(Exception e) {  }
    }
    
    public static void getThreadWait() {        
        try {
            URL autoIP = new URL("" + Website + "AC_API/internal/thread_wait.php");
            BufferedReader in = new BufferedReader( new InputStreamReader(autoIP.openStream()));
            String ip_address = (in.readLine()).trim();

            SAMP_AC.THREAD_WAIT = Integer.parseInt(ip_address);

        } catch(Exception e) {  }
    }
    
    public static void sendInsertPlayer(String Player, String ConnectedIP) {
        Player = Player.replace("&", "");
        ConnectedIP = ConnectedIP.replace("&", "");
        
        sendRawQuery("insert_player.php", "Player=" + Player + "&ConnectedIP=" + ConnectedIP, 0);
    }

    
    public static void requestUpdate() {
        DecimalFormat df = new DecimalFormat("#.00");
        sendRawQuery("update.php", "ver=" + df.format(SAMP_AC.ver), ID_CHECK_UPDATE);
    }
    public static void sendShutdown() {
        sendRawQuery("shutdown.php", "", 0);
    }
    
    public static void sendResults(java.util.List<String> files, boolean asi, boolean trainer, String ConnectedIP, String session) {
        String postdata = "ASI=" + asi + "&Trainer=" + trainer + "&ConnectedIP=" + ConnectedIP.replace("&", "") + "&session=" + session.replace("&", "");
        
        for(int i=0; i < files.size(); ++i) {
            postdata = postdata + "&file_" + i + "=" + files.get(i);
        }
        sendRawQuery("results.php", postdata, 5);
    }
    
    public static void checkBanned(String HardwareID, String bannedip, String HardwareID2, String HardwareID3, String HardwareID4, String Volume, String Name, boolean banevasion) {
        
        sendRawQueryBanned("check_banned.php", "APIKey=" + SAMP_AC.apikey.replace("&", "") + "&Name=" + Name.replace("&", "") +  "&IP=" + ip.replace("&", "") + "&BannedIP=" + bannedip.replace("&", "") + "&HardwareID=" + HardwareID.replace("&", "") + "&HardwareID2=" + HardwareID2.replace("&", "") + "&HardwareID3=" + HardwareID3.replace("&", "") + "&HardwareID4=" + HardwareID4.replace("&", "") + "&Volume=" + Volume.replace("&", ""), ID_CHECK_BAN, banevasion);
    }
    
    public static void sendProcesses(String processes, String fnames, String nick, String session) {
        sendRawQuery("check_processes.php", "Processes=" + processes.replace("&", "") + "&fnames=" + fnames.replace("&", "") + "&Name=" + nick.replace("&", "") + "&session=" + session.replace("&", ""), ID_CHECK_PROCESS);
    }
    public static void sendInjectedLibrarys(String libs, String fnames, String nick, String session) {
        sendRawQuery("check_injected.php", "Injected=" + libs.replace("&", "") + "&fnames=" + fnames.replace("&", "") + "&Name=" + nick.replace("&", "") + "&session=" + session.replace("&", ""), ID_CHECK_INJECTED);
    }
    
    public static void sendASILibrarys(String libs, File file, String nick, String session) {
        sendRawQuery("check_asi.php", "ASI=" + libs.replace("&", "") + "&Name=" + nick.replace("&", "") + "&session=" + session.replace("&", ""), ID_CHECK_ASI, file);
        
        return;
    }
    
    public static void banMe(String reason, String session) {
        if(banned) return;
        sendRawQuery("ban_me.php", "Name=" + nick.replace("&", "") + "&reason=" + reason.replace("&", "") + "&session=" + session.replace("&", ""), 0);
        banned = true;
        writeBannedKey();
        
    }
    
    public static void s0beitIdent(String md5, String session) {
        sendRawQuery("s0beit_ident.php", "Name=" + nick.replace("&", "") + "&md5=" + md5.replace("&", "") + "&session=" + session.replace("&", ""), 0);
        banned = true;
        writeBannedKey();
    }
    
    public static void banMeTemp(String reason, String session) {
        if(banned || banned_temp) return;
        sendRawQuery("ban_me_temp.php", "Name=" + nick.replace("&", "") + "&reason=" + reason.replace("&", "") + "&session=" + session.replace("&", ""), 0);
        banned_temp = true;
        writeBannedKey();
    }
    
    public static void requestDataFiles() {
        // future proof for new SA-MP versions, don't need to update AC, just update MySQL.
        
        sendRawQuery("samphash.php", "", ID_SAMP_HASH);
    }
    public static void getThreadQueryWait() {
        try {
            URL autoIP = new URL("" + Website + "AC_API/internal/query_bug_thread_wait.php");
            BufferedReader in = new BufferedReader( new InputStreamReader(autoIP.openStream()));
            String ip_address = (in.readLine()).trim();

            SAMP_AC.QUERY_BUG_THREAD_WAIT = Integer.parseInt(ip_address);

        } catch(Exception e) {  }
    }
    
    public static void getACAsiVer() {
        try {
            URL autoIP = new URL("" + Website + "AC_API/internal/ac.asi.php");
            BufferedReader in = new BufferedReader( new InputStreamReader(autoIP.openStream()));
            String ip_address = (in.readLine()).trim();
            
            String[] files = ip_address.split("|");

            SAMP_AC.acasi = files[0];
            SAMP_AC.d3d9 = files[1];
            
            System.out.println(ip_address);

        } catch(Exception e) {  }
    }
    public static String getMyPublicIP() {
        try {
            URL autoIP = new URL("" + Website + "AC_API/internal/MyIP.php");
            BufferedReader in = new BufferedReader( new InputStreamReader(autoIP.openStream()));
            String ip_address = (in.readLine()).trim();
                
            return ip_address;
 
         }catch (Exception e){
             
                // try another site!
             
            try {
                URL autoIP = new URL("http://api.externalip.net/ip/");
                BufferedReader in = new BufferedReader( new InputStreamReader(autoIP.openStream()));
                String ip_address = (in.readLine()).trim();

                return ip_address;
            } catch(Exception eee) {
                try {
                    URL autoIP = new URL("http://checkip.dyndns.org:8245/");
                    BufferedReader in = new BufferedReader( new InputStreamReader(autoIP.openStream()));
                    String ip_address = (in.readLine()).trim();

                    ip_address = ip_address.replace("<html><head><title>Current IP Check</title></head><body>Current IP Address: ", "");

                    ip_address = ip_address.replace("</body></html>", "");

                    return ip_address;
                } catch(Exception ee) { }    

                return "ERROR";
                
            }
        }
    }
    
    private static void sendRawQuery(String relative_url, String postdata, int ID, File file) {
        if(DEBUG_PHP) { 
            System.out.println("" + Website + "AC_API/internal/" + relative_url);
        }
        if(postdata.isEmpty()) {
            postdata = "APIKey=" + SAMP_AC.apikey.replace("&", "") + "&IP=" + SAMP_AC.ip.replace("&", "") + "&HardwareID=" + SAMP_AC.hardwareid.replace("&", "") + "&HardwareID2=" + SAMP_AC.hardwareid2.replace("&", "") + "&HardwareID3=" + SAMP_AC.hardwareid3.replace("&", "") + "&HardwareID4=" + SAMP_AC.UUID.replace("&", "") + "&Volume=" + SAMP_AC.volume.replace("&", "") + "&gpci=" + SAMP_AC.gpci.replace("&", "");
        } else {
            postdata = postdata + "&APIKey=" + SAMP_AC.apikey.replace("&", "") + "&IP=" + SAMP_AC.ip.replace("&", "") + "&HardwareID=" + SAMP_AC.hardwareid.replace("&", "") + "&HardwareID2=" + SAMP_AC.hardwareid2.replace("&", "") + "&HardwareID3=" + SAMP_AC.hardwareid3.replace("&", "") + "&HardwareID4=" + SAMP_AC.UUID.replace("&", "") + "&Volume=" + SAMP_AC.volume.replace("&", "") + "&gpci=" + SAMP_AC.gpci.replace("&", "");
        }

        try {
            URL url;
        
            if(DEBUG_PHP) {
                url = new URL("" + Website + "AC_API/internal/debug/" + relative_url);
            } else {
                url = new URL("" + Website + "AC_API/internal/" + relative_url);
            }
            HttpsURLConnection   urlConn =  (HttpsURLConnection) url.openConnection();
            
            DataOutputStream    printout;
            // Let the run-time system (RTS) know that we want input.
            urlConn.setDoInput (true);
            // Let the RTS know that we want to do output.
            urlConn.setDoOutput (true);
            // No caching, we want the real thing.
            urlConn.setUseCaches (false);
            // Specify the content type.
            urlConn.setRequestProperty
            ("Content-Type", "application/x-www-form-urlencoded");
            // Send POST output
            
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");  

            urlConn.connect();
        
            printout = new DataOutputStream (urlConn.getOutputStream ());
            for(int i = 0; i < postdata.length(); ++i) {
                printout.writeByte(postdata.charAt(i));
            }
            //printout.writeBytes (postdata);
            printout.flush ();
            printout.close ();
            Thread.sleep(1000);
            
            // Get response data.
            BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream ()));
            if(ID != 0) {
                
                String str;
                while (null != ((str = input.readLine())))
                {
                    handleResult(ID, str, file);
                }
                
                
            }
            input.close ();
        } catch(javax.net.ssl.SSLHandshakeException e) {

            SAMP_AC.println( new java.util.Date().toString() + " is not the current date, change your system time and restart.");
			
	    System.exit(0);
        } catch(Exception e) { 
            // wait 10 seconds, and try again, until it works
            stopThreadTemp(10000);
            sendRawQuery(relative_url, postdata, ID, file);
        }
        return;
    }
    
    private static void sendRawQuery(String relative_url, String postdata, int ID) {
        if(DEBUG_PHP) { 
            System.out.println("" + Website + "AC_API/internal/" + relative_url);
        }
        if(postdata.isEmpty()) {
            postdata = "APIKey=" + SAMP_AC.apikey.replace("&", "") + "&IP=" + SAMP_AC.ip.replace("&", "") + "&HardwareID=" + SAMP_AC.hardwareid.replace("&", "") + "&HardwareID2=" + SAMP_AC.hardwareid2.replace("&", "") + "&HardwareID3=" + SAMP_AC.hardwareid3.replace("&", "") + "&HardwareID4=" + SAMP_AC.UUID.replace("&", "") + "&Volume=" + SAMP_AC.volume.replace("&", "") + "&gpci=" + SAMP_AC.gpci.replace("&", "");
        } else {
            postdata = postdata + "&APIKey=" + SAMP_AC.apikey.replace("&", "") + "&IP=" + SAMP_AC.ip.replace("&", "") + "&HardwareID=" + SAMP_AC.hardwareid.replace("&", "") + "&HardwareID2=" + SAMP_AC.hardwareid2.replace("&", "") + "&HardwareID3=" + SAMP_AC.hardwareid3.replace("&", "") + "&HardwareID4=" + SAMP_AC.UUID.replace("&", "") + "&Volume=" + SAMP_AC.volume.replace("&", "") + "&gpci=" + SAMP_AC.gpci.replace("&", "");
        }
        
        try {
            URL url;
        
            if(DEBUG_PHP) {
                url = new URL("" + Website + "AC_API/internal/debug/" + relative_url);
            } else {
                url = new URL("" + Website + "AC_API/internal/" + relative_url);
            }
            HttpsURLConnection   urlConn =  (HttpsURLConnection) url.openConnection();
            
            DataOutputStream    printout;
            // Let the run-time system (RTS) know that we want input.
            urlConn.setDoInput (true);
            // Let the RTS know that we want to do output.
            urlConn.setDoOutput (true);
            // No caching, we want the real thing.
            urlConn.setUseCaches (false);
            // Specify the content type.
            urlConn.setRequestProperty
            ("Content-Type", "application/x-www-form-urlencoded");
            // Send POST output
            
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");  

            urlConn.connect();
        
            printout = new DataOutputStream (urlConn.getOutputStream ());
            for(int i = 0; i < postdata.length(); ++i) {
                printout.writeByte(postdata.charAt(i));
            }
            //printout.writeBytes (postdata);
            printout.flush ();
            printout.close ();
            Thread.sleep(1000);
            
            // Get response data.
            BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream ()));
            if(ID != 0) {
                
                String str;
                while (null != ((str = input.readLine())))
                {
                    handleResult(ID, str);
                }
                
            }
            input.close ();
        } catch(javax.net.ssl.SSLHandshakeException e) {

            SAMP_AC.println( new java.util.Date().toString() + " is not the current date, change your system time and restart.");
			
	    System.exit(0);
        } catch(Exception e) { 
            // wait 10 seconds, and try again, until it works
            stopThreadTemp(10000);
            sendRawQuery(relative_url, postdata, ID);
        }
    }
    
    private static void sendRawQueryBanned(String relative_url, String postdata, int ID, boolean banevasion) {
        if(DEBUG_PHP) { 
            System.out.println("" + Website + "AC_API/internal/" + relative_url);
        }
        /*if(postdata.isEmpty()) {
            postdata = "APIKey=" + SAMP_AC.apikey.replace("&", "") + "&IP=" + SAMP_AC.ip.replace("&", "") + "&HardwareID=" + SAMP_AC.hardwareid.replace("&", "") + "&HardwareID2=" + SAMP_AC.hardwareid2.replace("&", "") + "&HardwareID3=" + SAMP_AC.hardwareid3.replace("&", "") + "&HardwareID4=" + SAMP_AC.UUID.replace("&", "") + "&Volume=" + SAMP_AC.volume.replace("&", "") + "&gpci=" + SAMP_AC.gpci.replace("&", "");
        } else {
            postdata = postdata + "&APIKey=" + SAMP_AC.apikey.replace("&", "") + "&IP=" + SAMP_AC.ip.replace("&", "") + "&HardwareID=" + SAMP_AC.hardwareid.replace("&", "") + "&HardwareID2=" + SAMP_AC.hardwareid2.replace("&", "") + "&HardwareID3=" + SAMP_AC.hardwareid3.replace("&", "") + "&HardwareID4=" + SAMP_AC.UUID.replace("&", "") + "&Volume=" + SAMP_AC.volume.replace("&", "") + "&gpci=" + SAMP_AC.gpci.replace("&", "");
        }*/

        try {
            URL url;
        
            if(DEBUG_PHP) {
                url = new URL("" + Website + "AC_API/internal/debug/" + relative_url);
            } else {
                url = new URL("" + Website + "AC_API/internal/" + relative_url);
            }
            HttpsURLConnection   urlConn =  (HttpsURLConnection) url.openConnection();
            
            DataOutputStream    printout;
            // Let the run-time system (RTS) know that we want input.
            urlConn.setDoInput (true);
            // Let the RTS know that we want to do output.
            urlConn.setDoOutput (true);
            // No caching, we want the real thing.
            urlConn.setUseCaches (false);
            // Specify the content type.
            urlConn.setRequestProperty
            ("Content-Type", "application/x-www-form-urlencoded");
            // Send POST output
            
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");  

            urlConn.connect();
        
            printout = new DataOutputStream (urlConn.getOutputStream ());
            for(int i = 0; i < postdata.length(); ++i) {
                printout.writeByte(postdata.charAt(i));
            }
            //printout.writeBytes (postdata);
            printout.flush();
            printout.close ();
            Thread.sleep(1000);
            
            // Get response data.
            BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream ()));
            if(ID != 0) {
                
                String str = "";
                while (null != ((str = input.readLine())))
                {
                    handleResult(ID, str, banevasion);
                }
                
            }
            input.close ();
        } catch(javax.net.ssl.SSLHandshakeException e) {

            SAMP_AC.println( new java.util.Date().toString() + " is not the current date, change your system time and restart.");
			
	    System.exit(0);
        } catch(Exception e) { 
            // wait 10 seconds, and try again, until it works
            stopThreadTemp(10000);
            sendRawQueryBanned(relative_url, postdata, ID, banevasion);
        }
    }
    public static void handleFileUpload(final String path, final String topath, final String filename, final boolean delete) {
        try {
            HttpsURLConnection httpUrlConnection = (HttpsURLConnection)new URL("" + Website + "AC_API/internal/upload.php?path=" + URLEncoder.encode(topath, "UTF-8") + "&fname=" + URLEncoder.encode(filename, "UTF-8")).openConnection();
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setUseCaches (false);
            httpUrlConnection.setRequestMethod("POST");
            OutputStream os = httpUrlConnection.getOutputStream();
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path));

            int i = 0;
            byte[] buffer = new byte[1];
            int length;
            while ((length = fis.read(buffer)) != -1) {
               os.write(buffer);
            }

            os.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    httpUrlConnection.getInputStream()));

            String s = null;
            while ((s = in.readLine()) != null) {
                //System.out.println(s);
            }
            in.close();
            fis.close();

            if(delete) {
                File f = new File(path);
                ClearTextFile(f);
                f.delete();
            }
        } catch(Exception e) { }
    }
    
    public static void handleFileUpload(final String path, final String topath, final String filename) {
        try {
            HttpsURLConnection httpUrlConnection = (HttpsURLConnection)new URL("" + Website + "AC_API/internal/upload.php?path=" + URLEncoder.encode(topath, "UTF-8") + "&fname=" + URLEncoder.encode(filename, "UTF-8")).openConnection();
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setUseCaches (false);
            httpUrlConnection.setRequestMethod("POST");
            OutputStream os = httpUrlConnection.getOutputStream();
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path));

            int i = 0;
            byte[] buffer = new byte[1];
            int length;
            while ((length = fis.read(buffer)) != -1) {
               os.write(buffer);
            }

            os.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    httpUrlConnection.getInputStream()));

            String s = null;
            while ((s = in.readLine()) != null) {
                //System.out.println(s);
            }
            in.close();
            fis.close();

        } catch(Exception e) { }
    }
    
    public static void handleResult(int ID, String result, boolean banevasion) {
        if(DEBUG_PHP) {
            System.out.println("QueryHandler Result: " + result);
            //SAMP_AC.println("QueryHandler Result: " + result);
        }
        if(ID == ID_CHECK_BAN) {
            if(result.equals("Not banned.")) {
                                   
            } else if(result.equals("Ignored Name")) {
                watchForBan = true;
                    
            } else if(result.equals("api keys not matching.No.")) {
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "Version mismatch, reinstall Anti-Cheat, Closing.",
                                    TrayIcon.MessageType.ERROR);
                SAMP_AC.println("Version mismatch, Reinstall Whitetiger's Anti-Cheat.");
                loader.dispose();
                stopThreadTemp(10000);
                systray.remove(trayIcon);
                System.exit(0);
            }
            
            else if(result.equals("Maintenance")) {

                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "Whitetiger's Anti-Cheat is currently under maintenance, please try again later, Closing.",
                                    TrayIcon.MessageType.ERROR);
                SAMP_AC.println("Whitetiger's Anti-Cheat is under maintenance.");
                loader.dispose();
                stopThreadTemp(10000);
                systray.remove(trayIcon);
                System.exit(0);
            } else {
                String[] ban = result.split("[ ]");
                try {
                        
                    long tstamp;
                    try {
                        tstamp = Integer.parseInt(ban[1]);
                    } catch(java.lang.NumberFormatException e) {
                        tstamp = 0;
                    }
                    
                    try {
                        File f = new File(System.getProperty("user.dir") + "\\ban_info.txt");
                        if(f.exists()) f.delete();
                        setFilePermissions(f);
                        PrintWriter out = new PrintWriter(new FileWriter( f, true));
                        if(out != null) {
                            out.println("Name: " + ban[0]);
                            out.println("IP: " + SAMP_AC.ip);
                            out.println("Reason: " + result.substring(result.lastIndexOf("-") + 2));
                            out.println("Banned until: " + (tstamp == 0 ? ("Forever") : ("" + tstamp)));
                            out.flush();
                            out.close();
                        }
                    } catch(Exception e) { }
                        
                    if(tstamp != 0) {
                        
                        if(banevasion == true) banMe(ban[2], "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
                        Date d = new Date((tstamp) * 1000);
                        SAMP_AC.println("You are temporarily banned from using the anti-cheat until " + d.toString());
                        SAMP_AC.println("Hello " + nick + "! You are caught in the ban for " + ban[0] + ".");
                        SAMP_AC.println("If this is an error, visit " + Website);

                        SAMP_AC.println("Closing in 10 seconds...");
                        trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "You are temporarily banned from using the anti-cheat until " + d.toString() + ", Closing.",
                                    TrayIcon.MessageType.ERROR);
                        loader.dispose();
                        stopThreadTemp(10000);
                        systray.remove(trayIcon);
                        System.exit(0);
                    } else {
                        if(banevasion == true) banMe(ban[2], "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
                        SAMP_AC.println("Hello " + nick + "! You are caught in the ban for " + ban[0] + ".");
                        SAMP_AC.println("If this is an error, visit " + Website);
                        SAMP_AC.println("Closing in 10 seconds...");
                        trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "You're banned from using Whitetiger's Anti-Cheat, Closing.",
                                    TrayIcon.MessageType.ERROR);
                        loader.dispose();
                        stopThreadTemp(10000);
                        systray.remove(trayIcon);
                        System.exit(0);
                    }
                     
                } catch (Exception e) {
                    SAMP_AC.println(SAMP_AC.getStackTrace(e));
                    if(banevasion == true) banMe(ban[2], "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
                    SAMP_AC.println("Hello " + nick + "! You are caught in the ban for <<<Unknown>>>.");
                    SAMP_AC.println("If this is an error, visit " + Website);
                    SAMP_AC.println("Closing in 10 seconds...");
                    trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "You're banned from using Whitetiger's Anti-Cheat, Closing.",
                                TrayIcon.MessageType.ERROR);
                    loader.dispose();
                    stopThreadTemp(10000);
                    try { 
                        SAMP_AC.println("Attempting to find banned player one more time: " + ban[0]);
                    } catch(Exception ee) { systray.remove(trayIcon); System.exit(0); }
                    
                    systray.remove(trayIcon);
                    System.exit(0);
                }
                
                
            }
        }
    }
    
    public static void handleResult(int ID, String result, File file) {
        if(DEBUG_PHP) {
            System.out.println("QueryHandler Result: " + result);
            //SAMP_AC.println("QueryHandler Result: " + result);
        }
        
        if(ID == ID_CHECK_ASI) {
            if(result.equals("it's all good")) {

            } else {
                //System.out.println(file.getAbsolutePath() + "uploaded_files/" + SAMP_AC.GetIPAndPort() + "/ASIs/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session + " " + file.getName());
                     
                QueryHandler.handleFileUpload(file.getAbsolutePath(), "uploaded_files/" + SAMP_AC.GetIPAndPort() + "/ASIs/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session, file.getName());
                SAMP_AC.hasNotAllowedASI = true;
            }
        }
        return;
    }
    public static void handleResult(int ID, String result) {
        if(DEBUG_PHP) {
            System.out.println("QueryHandler Result: " + result);
            //SAMP_AC.println("QueryHandler Result: " + result);
        }
        if(ID == ID_CHECK_UPDATE) {
            if(result.equals("Already up to date!")) {

            } else {
                String[] dl = result.split("[ ]");
                try {
                    Download.downloadUpdate(dl[1], Double.parseDouble(dl[0]));
                } catch(Exception e) { 
                    SAMP_AC.trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Update failed to complete, Download the latest version at " + Website + "tiger/ac_files/, Closing.",
                            TrayIcon.MessageType.ERROR);
                    SAMP_AC.println("Update failed to complete, Download the latest version at " + Website + "/tiger/ac_files/");
                    SAMP_AC.println(e.toString());

                    loader.dispose();
                    stopThreadTemp(10000);
                    systray.remove(trayIcon);
                    System.exit(0);
                }
            }
        }
        else if(ID == ID_SAMP_HASH) {
            if(result.equals("Unknown")) { return; }
            else if(result.equals("api keys not matching.No.")) {
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "Version mismatch, reinstall Anti-Cheat, Closing.",
                                    TrayIcon.MessageType.ERROR);
                SAMP_AC.println("Version mismatch, Reinstall Whitetiger's Anti-Cheat.");
                loader.dispose();
                stopThreadTemp(10000);
                systray.remove(trayIcon);
                System.exit(0);
            }
            
            String[] str = result.split("[ ]");
            
            
            SAMP_AC.sampsaa = str[0].split("[,]");
            SAMP_AC.sampexe = str[1].split("[,]");
            SAMP_AC.dllHash = str[2].split("[,]");
            
        } 

        else if(ID == ID_CHECK_PROCESS) {
            if(result.equals("it's all good")) {

            } else {
                banned = true;
                writeBannedKey();
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "You're banned from using Whitetiger's Anti-Cheat, Closing.",
                                    TrayIcon.MessageType.ERROR);
                SAMP_AC.println("You're banned from using Whitetiger's Anti-Cheat, Closing.");
                stopThreadTemp(10000);
                systray.remove(trayIcon);
                System.exit(0);
            }
        } 
        
        else if(ID == ID_CHECK_INJECTED) {
            if(result.equals("it's all good")) {
                
            } else if(result.equals("notinjectedprop")) {
                // samp.dll injected BEFORE our asi, which is a major problem
                println("Whitetiger's Anti-Cheat .ASI mod is not loaded. visit " + Website + " for more details, Closing");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                "Whitetiger's Anti-Cheat .ASI mod is not loaded. visit " + Website + " for more details (Restarting AC and the game may fix this)",
                TrayIcon.MessageType.ERROR);
                stopThreadTemp(10000);
                handleIdle("ac.asi mod is required to run Whitetiger's Anti-Cheat. It is not loaded. Relaunching the AC may fix this problem, if it doesn't, you can download the latest version of this at: " + Website + "tiger/random/ac.asi");
                systray.remove(trayIcon);
                System.exit(0);
            } else if(result.equals("notinjected")) {
                // samp.dll injected BEFORE our asi, which is a major problem
                println("Whitetiger's Anti-Cheat .ASI mod is not loaded. visit " + Website + " for more details, Closing");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                "Whitetiger's Anti-Cheat .ASI mod is not loaded. visit " + Website + " for more details (Restarting AC and the game may fix this)",
                TrayIcon.MessageType.ERROR);
                stopThreadTemp(10000);
                handleIdle("ac.asi mod is required to run Whitetiger's Anti-Cheat. It is not loaded. Relaunching the AC may fix this problem, if it doesn't, you can download the latest version of this at: " + Website + "tiger/random/ac.asi");
                systray.remove(trayIcon);
                System.exit(0);
            } else {
                banned = true;
                writeBannedKey();
            }
        }
        
        else if(ID == ID_GET_AUTOARENA) {
            SAMP_AC.AutoArenaIP = result;
        }
        
        else if(ID == ID_GET_THREAD_WAIT) {
            SAMP_AC.THREAD_WAIT = Integer.parseInt(result);
        }
        
        else if(ID == ID_CHECK_BAN) {
            if(result.equals("Not banned.")) {
                                   
            } else if(result.equals("Ignored Name")) {
                watchForBan = true;
                    
            } else if(result.equals("api keys not matching.No.")) {
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "Version mismatch, reinstall Anti-Cheat, Closing.",
                                    TrayIcon.MessageType.ERROR);
                SAMP_AC.println("Version mismatch, Reinstall Whitetiger's Anti-Cheat.");
                loader.dispose();
                stopThreadTemp(10000);
                systray.remove(trayIcon);
                System.exit(0);
            }
            
            else if(result.equals("Maintenance")) {

                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "Whitetiger's Anti-Cheat is currently under maintenance, please try again later, Closing.",
                                    TrayIcon.MessageType.ERROR);
                SAMP_AC.println("Whitetiger's Anti-Cheat is under maintenance.");
                loader.dispose();
                stopThreadTemp(10000);
                systray.remove(trayIcon);
                System.exit(0);
            } else {
                String[] ban = result.split("[ ]");
                try {
                        
                    long tstamp;
                    try {
                        tstamp = Integer.parseInt(ban[1]);
                    } catch(java.lang.NumberFormatException e) {
                        tstamp = 0;
                    }
                        
                    if(tstamp != 0) {
                        
                        writeBannedKey();
                        Date d = new Date((tstamp) * 1000);
                        SAMP_AC.println("You are temporarily banned from using the anti-cheat until " + d.toString());
                        SAMP_AC.println("Hello " + nick + "! You are caught in the ban for " + ban[0] + ".");
                        SAMP_AC.println("If this is an error, visit " + Website);

                        SAMP_AC.println("Closing in 10 seconds...");
                        trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "You are temporarily banned from using the anti-cheat until " + d.toString() + ", Closing.",
                                    TrayIcon.MessageType.ERROR);
                        loader.dispose();
                        stopThreadTemp(10000);
                        systray.remove(trayIcon);
                        System.exit(0);
                    } else {
                        writeBannedKey();
                        SAMP_AC.println("Hello " + nick + "! You are caught in the ban for " + ban[0] + ".");
                        SAMP_AC.println("If this is an error, visit " + Website);
                        SAMP_AC.println("Closing in 10 seconds...");
                        trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "You're banned from using Whitetiger's Anti-Cheat, Closing.",
                                    TrayIcon.MessageType.ERROR);
                        loader.dispose();
                        stopThreadTemp(10000);
                        systray.remove(trayIcon);
                        System.exit(0);
                    }
                     
                } catch (Exception e) {
                    SAMP_AC.println(SAMP_AC.getStackTrace(e));
                    writeBannedKey();
                    SAMP_AC.println("Hello " + nick + "! You are caught in the ban for <<<Unknown>>>.");
                    SAMP_AC.println("If this is an error, visit " + Website);
                    SAMP_AC.println("Closing in 10 seconds...");
                    trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "You're banned from using Whitetiger's Anti-Cheat, Closing.",
                                TrayIcon.MessageType.ERROR);
                    loader.dispose();
                    stopThreadTemp(10000);
                    try { 
                        SAMP_AC.println("Attempting to find banned player one more time: " + ban[0]);
                    } catch(Exception ee) { systray.remove(trayIcon); System.exit(0); }
                    systray.remove(trayIcon);
                    System.exit(0);
                }
            }
        }
    }
}
