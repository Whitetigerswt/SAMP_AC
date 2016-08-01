
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Whitetiger
 */
public class MatchFinder {
    private static final int ID_OFFSET = 10000;
    
    private static final int ID_INSERT_MATCH            = ID_OFFSET;
    private static final int ID_SEND_MATCH_RESULTS      = ID_OFFSET+1;
    private static final int ID_HANDLE_CHAT             = ID_OFFSET+2;
    private static final int ID_SEND_CHAT               = ID_OFFSET+3;
    
    private static final boolean DEBUG_PHP = true;
    static String matchid = "";
    static int lastUpdatedChat = 0;
    static boolean inMatch = false;
    static EnterResult enterresult = new EnterResult();
    static MatchInfo minfo = new MatchInfo();
    static String[] alphaplayers = {""};
    static String[] betaplayers = {""};
    
    
    public static void init() { // Called when we switch modes from AC to match finder.
        while(!inMatch) {
            insertMatch("1", 0, 0);
            stopThreadTemp(10000);
        }
    }
    
    public static void insertMatch(String prefs, int partyid, int ready) {
        sendRawQuery("insert_match.php", "Player=" + SAMP_AC.nick.replace("&", "") + "&prefs=" + prefs + "&partyid=" + partyid + "&ready=" + ready, ID_INSERT_MATCH);
    }
    
    private static void sendRawQuery(String relative_url, String postdata, int ID) {
        if(DEBUG_PHP) { 
            System.out.println(relative_url);
        }
        if(postdata.isEmpty()) {
            postdata = "APIKey=" + SAMP_AC.apikey.replace("&", "") + "&IP=" + SAMP_AC.ip.replace("&", "") + "&HardwareID=" + SAMP_AC.hardwareid.replace("&", "") + "&HardwareID2=" + SAMP_AC.hardwareid2.replace("&", "");
        } else {
            postdata = postdata + "&APIKey=" + SAMP_AC.apikey.replace("&", "") + "&IP=" + SAMP_AC.ip.replace("&", "") + "&HardwareID=" + SAMP_AC.hardwareid.replace("&", "") + "&HardwareID2=" + SAMP_AC.hardwareid2.replace("&", "");
        }

        try {
            URL url = null;
        
            if(DEBUG_PHP) {
                url = new URL("https://www.sixtytiger.com/match_api/debug/" + relative_url);
            } else {
                url = new URL("https://www.sixtytiger.com/match_api/" + relative_url);
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
            printout.writeBytes (postdata);
            printout.flush ();
            printout.close ();
            
            // Get response data.
            BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream ()));
            if(ID != 0) {
                
                String str;
                String totalstr = "";
                while (null != ((str = input.readLine())))
                {
                    if(ID != ID_HANDLE_CHAT) 
                        handleResult(ID, str);
                    else totalstr += str + '\n';
                }
                if(ID == ID_HANDLE_CHAT) handleResult(ID, totalstr);
                
            }
            input.close ();
        } catch(javax.net.ssl.SSLHandshakeException e) {

            SAMP_AC.println( new java.util.Date().toString() + " is not the current date, change your system time and restart.");
			
	    System.exit(0);
        } catch(Exception e) { 
            // wait 10 seconds, and try again, until it works
            e.printStackTrace();
            stopThreadTemp(10000);
            sendRawQuery(relative_url, postdata, ID);
        }
    }
    public static void handleResult(int ID, String result) {
        if(DEBUG_PHP) {
            System.out.println("MatchFinder Result: " + result);
            SAMP_AC.println("MatchFinder Result: " + result);
        }
        if(ID == ID_INSERT_MATCH) {
            String[] res = result.split("[ ]");
            
            if(res[0].equals("foundmatch")) {
                matchid = res[1];
                
                String matchinfo = getMatchInfo(matchid);
                if(!matchinfo.equals("-1")) {

                    String[] matchinfo_split = matchinfo.split("[ ]");
                    
                    matchinfo = parseMatchPlayers(matchinfo_split);  
                    int players = Integer.parseInt(matchinfo_split[2]);
                    int timestamp = Integer.parseInt(matchinfo_split[3]);
                    java.util.Date time = new java.util.Date((long)timestamp*1000);
                    
                    matchinfo = matchinfo + "\n\nNumber of players: " + players + "\nMatch Started on: " + time.toString();
                    
                    minfo.setTextArea( matchinfo );
                    minfo.setVisible(true);
                    inMatch = true;
                    
                    final Thread chatThread = new Thread() {
                        @Override
                        public void run() { 
                            handleChat();
                        }
                    };
                    chatThread.start();
                }  
            }
        } else if(ID == ID_SEND_MATCH_RESULTS) {
            if(result.equals("voted")) {
                enterresult.setVisible(false);
                inMatch = false;

                while(true) {
                    int dialogres = JOptionPane.showConfirmDialog(null,
                        "You have set your vote successfully. Search for another match?", 
                        "Success", 
                        JOptionPane.YES_NO_OPTION
                    );

                    if(dialogres == JOptionPane.YES_OPTION) {
                        init();
                        return;
                    } else if(dialogres == JOptionPane.NO_OPTION) {
                        System.exit(0);
                        break;
                    }
                }
                
            }
        } else if(ID == ID_HANDLE_CHAT) {
            System.out.println(result);
            String[] str = result.split("[ ]");
            if(str[0].length() > 0) {
                lastUpdatedChat = Integer.parseInt(str[0]);
                int realchat = result.indexOf(" ");
                minfo.setChat(result.substring(realchat));
            }
        } else if(ID == ID_SEND_CHAT) {
            System.out.println(result);
        }
    }
    
    public static void handleChat() {
        while(true) {
            sendRawQuery("getchat.php", "Player=" + SAMP_AC.nick.replace("&", "") + "&MatchID=" + matchid.replace("&", "") + "&lastupdate=" + lastUpdatedChat, ID_HANDLE_CHAT);
            stopThreadTemp(1000);
        }
    }
    public static void sendChat(String text) {
        sendRawQuery("sendchat.php", "Name=" + SAMP_AC.nick.replace("&", "") + "&MatchID=" + matchid.replace("&", "") + "&Chat=" + text.replace("&", "and"), ID_SEND_CHAT);
    }
    
    public static String parseMatchPlayers(String[] matchinfo) {
        alphaplayers = matchinfo[0].split("[|]");
        betaplayers = matchinfo[1].split("[|]");
        
        enterresult.setPlayers(alphaplayers, betaplayers);
        
        String str = "Alpha Players:\n";
        for(int i = 0; i < alphaplayers.length; ++i) {
            str = str + alphaplayers[i] + "\n";
        }
        
        str = str + "Beta Players:\n";
        for(int i = 0; i < betaplayers.length; ++i) {
            str = str + betaplayers[i] + "\n";
        }
        
        return str;
    }
    
    public static void sendResults() {
        int betascore = enterresult.getBetaScore();
        int alphascore = enterresult.getAlphaScore();
        
        String comment = enterresult.getMatchComment();
        
        if(betascore == -1 || alphascore == -1) {
            JOptionPane.showMessageDialog(null, "You must set a score for both Alpha and Beta team.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sendRawQuery("send_match_results.php", "Player=" + SAMP_AC.nick.replace("&", "") + "&MatchID=" + matchid.replace("&", "") + "&Scores=" + alphascore + "|" + betascore + "&Comment=" + comment.replace("&", "and"), ID_SEND_MATCH_RESULTS);
    }
    
    public static String getMatchInfo(String matchid) {
        try {
            
            URL url = new URL("https://www.sixtytiger.com/match_api/getmatchinfo.php");
            
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
            printout.writeBytes ("matchid=" + matchid);
            printout.flush ();
            printout.close ();
            
            // Get response data.
            BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream ()));
            return input.readLine().trim();


        } catch(Exception e) { e.printStackTrace();  }
        return "-1";
    }
    
    public static void stopThreadTemp(int amt) {
        try {
            Thread.sleep(amt);
        } catch(InterruptedException e) { }
    }
}
