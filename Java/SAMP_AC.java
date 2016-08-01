import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import java.nio.file.Files;
import org.apache.commons.codec.digest.DigestUtils;



/**
 *
 * @author My Account
 * 
 */



public class SAMP_AC extends javax.swing.JFrame {

    /**
     * @param args the command line arguments
     */
    //public interface ac {
		// UPDATE `AC_Players` SET `AppRunning` = 0 WHERE `AppRunning` = 1
        static String dll = "samp_ac_x86";
        static String nick = Registry.readRegistry("HKEY_CURRENT_USER\\Software\\SAMP", "PlayerName", 0, "REG_SZ");
        /*static String url = "jdbc:mysql://pyxis.volt-host.com:3306/1574_GTAFC";
        static String user = "1574_GTAFC";
        static String password = "UsN3rIvT4EDH";*/
        static String hardwareid = DigestUtils.md5Hex(Serial.getSerialNumber() + " " + Serial.getMotherboardSN() + CPU.getCPUName() + CPU.getCPUSpeed() + Serial.getGFXCard() + Serial.getGFXCardRAM() + Serial.getTotalRAM() + " dgrFsvbghjrdfghtrdhdasdvgxfghR##$");
        static String hardwareid2 = DigestUtils.md5Hex(Serial.getSerialNumber( true ) + Serial.getMotherboardSN() + Serial.getCPUSerial() + Serial.getBIOSSerial( true ) + Serial.getHDDSerial());
        static String hardwareid3 = DigestUtils.md5Hex( Serial.getSerialNumber( ) ) ;
        static String volume = DigestUtils.md5Hex( cplusplus.GetVolumeSerial() ) ;
        static String gpci = DigestUtils.md5Hex( System.getProperty("user.home") );
        static String UUID = DigestUtils.md5Hex( Serial.getUUID() );
        
        static String ip = QueryHandler.getMyPublicIP();
        static final double ver = 1.92;
        static int afk = 0;
        static Toolkit tk = Toolkit.getDefaultToolkit();
        static boolean hasASI = false, hasTrainer = false;
        static String installdir;
        static boolean watchForBan = false;
        static String session = "";
        static String sampacdllhash = "";
        static String apikey = "";
        static String scannedMD5s = "";
        static boolean stopThreads = false;
        public static int pulsed;
        public static int mode = 0; // 0 = anti-cheat, 1 = match finder
        static boolean hasAllowedASI = false;
        static boolean hasNotAllowedASI = false;
        static boolean DetectedASILoader = false;
        static boolean gta_sa_exe_kick = false;
        static boolean hasOurASI = false;
        static Thread queryBugFix;
        static SystemTray systray;
        static String[] arr = { "anim/ped.ifp",             // file_0
                                "data/carmods.dat",         // file_1
                                "data/animgrp.dat",         // file_2
                                "data/melee.dat",           // file_3
                                "data/clothes.dat",         // file_4
                                "data/object.dat",          // file_5
                                "data/default.dat",         // file_6
                                "data/surface.dat",         // file_7
                                "data/default.ide",         // file_8
                                "data/gta.dat",             // file_9
                                "data/surfinfo.dat",        // file_10
                                "data/peds.ide",            // file_11
                                "data/vehicles.ide",        // file_12
                                "data/water.dat",           // file_13
                                "data/txdcut.ide",          // file_14
                                "data/water1.dat",          // file_15
                                "models/coll/weapons.col",  // file_16
                                "data/plants.dat",          // file_17
                                "data/furnitur.dat",        // file_18
                                "data/procobj.dat",         // file_19
                                "data/handling.cfg",        // file_20
                                "models/coll/peds.col",     // file_21
                                "models/coll/vehicles.col", // file_22
                                "samp.saa",                 // file_23
                                "samp.exe",                 // file_24
                                "vorbis.dll",               // file_25
                                "data/pedstats.dat",        // file_26
                                "data/ar_stats.dat" };      // file_27
        
        static String[] hash = { "4736b2c90b00981255f9507308ee9174", // anim/ped.ifp                | file_0
                                 "6cbe845361e76aae35ddca300867cadf", // data/carmods.dat            | file_1
                                 "6a484b0b2356c524207d939487f1bff1", // data/animgrp.dat            | file_2
                                 "b2f05657980e4a693f8ff5eadcbad8f8", // data/melee.dat              | file_3
                                 "8762637e580eb936111859ffa29bddb4", // data/clothes.dat            | file_4
                                 "46a5e7dff90078842e24d9de5e92cc3e", // data/object.dat             | file_5
                                 "8e133355396761bd5cd16bf873154b30", // data/default.dat            | file_6
                                 "9eb4e4e474abd5da2f3961a5ef549f9e", // data/surface.dat            | file_7
                                 "5b6d75bae827e2d88f24f2be66a037bb", // data/default.ide            | file_8
                                 "2d2e4f7f05e2d82b25c88707096d3393", // data/gta.dat                | file_9
                                 "605dd0beabccc797ce94a51a3e4a09eb", // data/surfinfo.dat           | file_10
                                 "f7dea69fa6ab973479b9ef0cf05d3d98", // data/peds.ide               | file_11
                                 "bdc3a0fced2402c5bc61585714457d4b", // data/vehicles.ide           | file_12
                                 "690400ecc92169d9eaddaaa948903efb", // data/water.dat              | file_13
                                 "e3c231039048a30680b8f13fb51cc4ac", // data/txdcut.ide             | file_14
                                 "16fe5a3e8c57d02eb62a44a96d8b9d39", // data/water1.dat             | file_15
                                 "510e74e32b323eee54dd7a243b073808", // models/coll/weapons.col     | file_16
                                 "a2713338dbbd55898a4195e4464c6b06", // data/plants.dat             | file_17
                                 "3199fc8b81a4c5334a497508fe408afd", // data/furnitur.dat           | file_18
                                 "7229fa03d65f135bd569c3692d67c4b3", // data/procobj.dat            | file_19
                                 "6868accef933f1855ec28ce193a78159", // data/handling.cfg           | file_20
                                 "74288cbdd843c3cfb77b036a5614ae9d", // models/coll/peds.col        | file_21
                                 "c84c1a1b67d5fad3df75dd8d45fc576b", // models/coll/vehicles.col    } file_22
                                 "810e2b066e0f28ad21f33d3e281d2b5f", // samp.saa                    | file_23
                                 "2f4614683ba833b75251ac4dad8b28ad", // samp.exe                    | file_24
                                 "2840f08dd9753a5b13c60d6d1c165c9a", // vorbis.dll                  | file_25
                                 "d722c90c92f3ad5c1b531596769f61cd", // data/pedstats.dat           | file_26
                                 "a98936b0f3523f23cad2eacc0eaf7a9b"  // data/ar_stats.dat           | file_27
                               //"2b7b803311d2b228f065c45d13e1aeb2"  // vorbisfile.dll              | file_??
        };
        
        File[] fileHandler = new File[arr.length+3];
        
        static String[] dllHash = { // samp.dll md5
            
	    "aba32dd8a9e904a21a66b46b7d31d8f1", // 0.3x R1-2 kyetard
            "4ae9841419ff9bdec4024b8bfaa1c7d6", // 0.3x-R1-2
            "36c7eaf51b7e477a121521ce0fa003ac", // 0.3x
            "07012dc468f2b89db2584e358a048e39", // 0.3e
            "f11ff91ab18484f8fecefb28b06556d4", // 0.3d 
            "14d255c662ac58ce3dd757b7e8b92fb8"  // 0.3d R2
                
        };
        
        static String[] surfInfoHash = { 
            
            "605dd0beabccc797ce94a51a3e4a09eb", // Original surfinfo.dat
            "1ef27e99d434d2252a6dce0b018c5fe6"  // Luk_Ass's no dust edit
                
        };
        
        static String[] sampsaa = { 

            "693b1497e7ce89869c24a43a3ff8e836", // 0.3e & 0.3x
            "810e2b066e0f28ad21f33d3e281d2b5f"  // 0.3d & 0.3d R2
                
        };
        
        static String[] sampexe = { // samp.exe md5
            
            "035dff67a86995de5fe943620adc8b9b", // 0.3x R1-2
            "0458ce157cf57fa8809fb9adb6384660", // 0.3x
            "c06568d52fecd2f97b7aaab911a83959", // 0.3e
            "2f4614683ba833b75251ac4dad8b28ad"  // 0.3d & 0.3d R2
                
        };
        
        static String[] vorbisfileHash = {
            "2b7b803311d2b228f065c45d13e1aeb2", // default file hash
            "6749a6f6886a9646c23bcbc7da412633", // ASI Loader ( http://spelsajten.net/asi.zip )
            "fbe34f162166fea391a3f74ff4701a25"  // ASI Loader
        };
   
        static String[] exeHash = { 
            "6c6160da9b175b66cf9127c86be57bf7", 
            "170b3a9108687b26da2d8901c6948a18", 
            "68ba9ec43813ad58e39d64f88ebdc6a6", 
            "c8a31567f7279889cff09e49f3b8ce7a", 
            "842c61a45ace7638ff1e85d1f7a38545", 
            "12cc5f78cf7db2d635c13abd9b2799f6",  // timecyc.aaa exe+
            "8a3ff2c40e64ecb8ebe9a51d36b18b21",
            "0bfc9ecd5398cbef80ff7afc51620a45",
            "2b5066bd4097ac2944ce6a9cf8fe5677",
            "e7697a085336f974a4a6102a51223960", // unmodded exe (al_pacino)
            "97b74faf7708c84d21db5f9df1c31c67"
        };
        
        public static String acasi = "4cc442de61bda092b3ca862434e4d844";
        public static String d3d9 = "12af1d728f733729f718b48ec3f1abe5";
        public static final int DEBUG = 0;
        public static int THREAD_WAIT = 5;
        public static String AutoArenaIP = "176.31.229.148:7830";
        public static int QUERY_BUG_THREAD_WAIT = 55000;
        public static final Loading loader = new Loading();
        
        public static TrayIcon trayIcon;
        
    //}
    //public static void loadFrame() { new initComponents(); }
        
    public static void main( String[] args ) {        
        
        QueryHandler.getAutoArenaIP();
        
        InputStream is = SAMP_AC.class.getResourceAsStream("images/ac.png");
        
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] byBuf = new byte[10000];

        Image image = null;
        try {
            bis.read(byBuf, 0, 10000);
            image = Toolkit.getDefaultToolkit().createImage(byBuf);
            
            setFilePermissions(new File(System.getProperty("user.dir") + "\\ac_log.txt"));
            PrintWriter out = new PrintWriter(new FileWriter( System.getProperty("user.dir") + "\\ac_log.txt", true));
            if(out != null) {
                out.println("\n ------ SAMP_AC v" + ver + " started on " + new java.util.Date().toString());
                out.close();
            }
        } catch(Exception e) { }
        
        
        loader.setAutoArenaText();
        loader.setIconImage(image);
        loader.setProgressText("Initializing");
        loader.setLocationRelativeTo(null);
        Loading.setDefaultLookAndFeelDecorated(true);
        loader.setVisible(true);
        
        if(SystemTray.isSupported()) {
            systray = SystemTray.getSystemTray();
            
            ActionListener exitListener;
            exitListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    systray.remove(trayIcon);
                    println("Stopping Anti-Cheat... (User controlled)");
                    System.exit(0);
                }
            };
            
            File ff = new File(System.getProperty("java.io.tmpdir") + "/acfirstrun.txt");
            boolean exception = false;
            if(!ff.exists()) {
                try {
                    PrintWriter out = new PrintWriter(new FileWriter( ff.getAbsolutePath(), true));
                    out.write("a");
                    out.close();
                } catch(Exception e) { exception = true; }
                if(!exception) {
                    int result = JOptionPane.showConfirmDialog(null,
                        "Support Anti-Cheat developers - Play on Public AutoArena?", 
                        "Open Public AutoArena", 
                        JOptionPane.YES_NO_OPTION
                    );

                    if(result == JOptionPane.YES_OPTION) {
                        Loading.openAutoarena();
                    } else { }
                }
            }
            
            loader.setProgress(1);
            loader.setProgressText("Creating tray icon.");
                    
            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);
            
            trayIcon = new TrayIcon(image, "Whitetiger's Anti-Cheat", popup);

            trayIcon.setImageAutoSize(true);

            try {
                systray.add(trayIcon);
                
                
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                "Whitetiger's Anti-Cheat has started successfully.",
                TrayIcon.MessageType.INFO);
                 
                
            } catch (AWTException e) {
                
           }   
            loader.setProgress(2);
        }
        
        //SuggestPCRestart("test", "test");
        
        File f = new File(cplusplus.GetOurFileName());
        
        loader.setProgressText("Computing hash...");
        loader.setProgress(7);

        try {
            apikey = getMD5(f);
        } catch(Exception e) { }
        
        loader.setProgress(10);
        
        loader.setProgressText("Adding firewall exception.");
        loader.setProgress(10);
        try {
            if(System.getProperty("os.name").equals("Windows XP")) {
                Process proc = Runtime.getRuntime().exec("netsh firewall delete allowedprogram \"" + cplusplus.GetOurFileName() + "\"");
                proc.waitFor();
                proc = Runtime.getRuntime().exec("netsh firewall add allowedprogram program=\"" + cplusplus.GetOurFileName() + "\" name=\"SAMP_AC\" mode=ENABLE scope=ALL profile=ALL");
                proc.waitFor();
            } else if(System.getProperty("os.name").equals("Windows 8") || System.getProperty("os.name").equals("Windows 7") || System.getProperty("os.name").equals("Windows Vista")) {
                Process proc = Runtime.getRuntime().exec("netsh advfirewall firewall delete rule name=\"SAMP_AC\"");
                proc.waitFor();
                proc = Runtime.getRuntime().exec("netsh advfirewall firewall add rule name=\"SAMP_AC\" dir=in action=allow program=\"" + cplusplus.GetOurFileName() + "\" enable=yes remoteip=any profile=public,private");
                proc.waitFor();
            } else if(System.getProperty("os.name").equals("Windows 2000") || System.getProperty("os.name").equals("Windows 95") || System.getProperty("os.name").equals("Windows 98")) { 
                println("This system's Operating System is not supported.");
                println("Closing in 10 seconds.");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "This system's Operating System is not supported, Closing.",
                                    TrayIcon.MessageType.ERROR);
				loader.dispose();
                stopThreadTemp(10000);
                handleIdle("This operating system is not supported.");
                systray.remove(trayIcon);
                System.exit(0);
            }
            // stateful FTP not needed anymore
            //Runtime.getRuntime().exec("netsh advfirewall set global StatefulFTP disable");
        } catch(Exception e) {
            println("Fucked up error just occured, report this: " + e.toString());
        }
        loader.setProgressText("Checking for update.");
        loader.setProgress(14);
            
        QueryHandler.requestUpdate();
        
        loader.setProgressText("Checking if SAMP_AC is already running.");
        loader.setProgress(18);
        
        checkIfRunning();
        loader.setProgressText("Retrieving PC info.");
        loader.setProgress(22);
        
        if(cplusplus.IsVM() == true) {
            println("This application will not run on a Virtual Machine.");
            println("Closing in 10 seconds.");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "This application will not run on a Virtual Machine, Closing.",
                                TrayIcon.MessageType.ERROR);
            loader.dispose();
            stopThreadTemp(10000);
            systray.remove(trayIcon);
            
            System.exit(0);
        }
        
        loader.setProgress(26);
       
        try {
            String val = Registry.readRegistry("HKEY_LOCAL_MACHINE\\SYSTEM\\ControlSet001\\Control\\SystemInformation", "SystemProductName", 0, "REG_SZ");
            
            if(val.contains("VirtualBox") == true) {
                println("This application will not run on a Virtual Machine.");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "This application will not run on a Virtual Machine, Closing.",
                                TrayIcon.MessageType.ERROR);
                println("Closing in 10 seconds.");
                loader.dispose();
                stopThreadTemp(10000);
                systray.remove(trayIcon);
                System.exit(0);
            }
        } catch(Exception e) { }
        
        loader.setProgress(30);
        
        if(nick == null) {
            println("We've detected you don't even have SA-MP installed, or maybe a firewall is blocking the application");
            println("There could be a more serious problem with your system, try double checking that the registry is enabled.");
            println("If the registry is disabled, enable it, connect to a server and restart this application");
            println("If your registry can't be enabled for whatever reason, you may have a virus.");
            println("We're done here.");
            println("Closing in 10 seconds.");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "Check ac_log.txt for more info, Closing.",
                                TrayIcon.MessageType.ERROR);
            println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");
            
            File ff = null;   
            try {
                ff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                ff.deleteOnExit();
                
                PrintWriter out;
                try {
                    out = new PrintWriter(ff.getAbsolutePath());
                    out.println("error message: We've detected you don't even have SA-MP installed, or maybe a firewall is blocking");
                    out.println("The registry could not be accessed, what most likely occured is sa-mp is not installed, or a firewall is blocking it.");
                    out.println("Solution: make sure sa-mp is installed, and has been launched at least once, or disable firewall.");
                    out.println("SAMP_AC Version: " + ver);
                    
                    out.close();
                } catch(Exception e) { }  

                QueryHandler.handleFileUpload(ff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);
                ff.delete();
            } catch(Exception e) { }    
            
            loader.dispose();
            stopThreadTemp(10000);
            systray.remove(trayIcon);
            System.exit(0);
        }
        loader.setProgress(37);
        if(DEBUG == 0) {
            println("Welcome to the Whitetiger's Anti-Cheat " + String.format("%.02f", ver) + " by Whitetiger");
            println(" ");
        } else {
            println("SAMP_AC: Debug = 1");
        }    
        loader.setProgress(40);
        
        loader.setProgressText("Checking ban.");
        checkForBan();
        
        loader.setProgress(44);

        println("Please Wait...");    
        
        loader.setProgressText("Requesting file values.");
        QueryHandler.requestDataFiles(); 
        
        loader.setProgressText("Preparing for closing AC.");
        loader.setProgress(55);
        
        Runtime.getRuntime().addShutdownHook(new Thread() { 
            @Override 
            public void run() { 
                QueryHandler.sendShutdown();
                PrintWriter out;
                File ff;
                try {
                    ff = File.createTempFile("__" + System.currentTimeMillis(), ".txt");
                    ff.deleteOnExit();
                    out = new PrintWriter(new FileWriter(ff.getAbsolutePath()));
                    
                    out.println("Pulsed pulse.php " + QueryHandler.pulsed + " times in this session (approx " + calculateTime(QueryHandler.pulsed * 20) + ")");
                    QueryHandler.handleFileUpload(ff.getAbsolutePath(), "info/" + GetIPAndPort() + "/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "pulsed_" + SAMP_AC.nick + "_" + System.currentTimeMillis() + ".txt", true);
                } catch(Exception e) { }     
            }
        });
        
        loader.setProgressText("Determining sessions information");
        loader.setProgress(58);
       
        session = DigestUtils.md5Hex("" + System.currentTimeMillis()); 
        
        loader.setProgress(59);
        loader.setProgressText("Checking GTA directory.");

        try { 
            
            printGtaFiles( );
            
            
        } catch(Exception e) {
            println("SOME MAJOR SHIT JUST FUCKING HAPPEND!");
            println(e.toString());
            
            File ff;   
            try {
                ff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                ff.deleteOnExit();
                PrintWriter out;
                try {
                    out = new PrintWriter(ff.getAbsolutePath());
                    out.println("error message: SOME MAJOR SHIT JUST FUCKING HAPPEND!");
                    out.println(SAMP_AC.getStackTrace(e));
                    out.println("Solution: turn off firewall(s)");
                    out.println("SAMP_AC Version: " + ver);
                    out.close();
                } catch(Exception ee) { }
                QueryHandler.handleFileUpload(ff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);
                ff.delete();
            } catch(Exception eee) { }
           
            loader.dispose();
            stopThreadTemp(10000);
            systray.remove(trayIcon);
            System.exit(0);
        }   
        loader.setProgress(65);
        loader.setProgressText("Checking ac.asi version");
        
        asiVersionCheck();
        
        loader.setProgress(70);
        loader.setProgressText("Sending server information");

        QueryHandler.sendInsertPlayer(nick, GetIPPortAndName());
        
        loader.setProgress(75);
        
        /*if(notencrypted.equals("null")) {
            notencrypted = Serial.getSerialNumber(true);
            
            String placeholder = Serial.getMotherboardSN();
            if(!placeholder.equals("null")) {
                notencrypted += placeholder;
            }
            
            placeholder = Serial.getHDDSerial();
            if(!placeholder.equals("null")) {
                notencrypted += placeholder;
            }
            
            placeholder = Serial.getCPUSerial();
            if(!placeholder.equals("null")) {
                notencrypted += placeholder;
            }
            placeholder = Serial.getBIOSSerial();
            if(!placeholder.equals("null")) {
                notencrypted += placeholder;
            } 
            placeholder = Serial.getBIOSSerial(true);
            if(!placeholder.equals("null")) {
                notencrypted += placeholder;
            }
            
            
            if(notencrypted.equals("null")) {
                // really now? let's at least get the MAC address, fuck.
                notencrypted = Serial.getMacAddress();
                if(notencrypted.equals("null")) {
                    notencrypted = "we tried really hard to get a value for this, but in the end, failed.";
                }
            }
            
            hardwareid2 = DigestUtils.md5Hex(notencrypted); 
        }*/
        
        loader.setProgress(83);
        
        println("Confirmed! You're ready to play.");
        //println("Closing this window will stop the anti cheat.");
        
        trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                "You're ready to play.",
                TrayIcon.MessageType.INFO);        
        
        
        if(mode == 0) {

            loader.setProgress(93);
            loader.setProgressText("Setting up stable connection.");
            QueryHandler.getThreadQueryWait();
            QueryHandler.getThreadWait();
            queryBugFix( );
            loader.setProgressText("Initiating loop");
            loader.setProgress(100);
            doLoop( );
            println("Ending main thread.");
        } else if(mode == 1) {
            loader.setProgress(100);
            MatchFinder.init();
        }

    }
    
    public static void asiVersionCheck() {
        
        File asi = new File(installdir + "ac.asi");
        if(!asi.exists()) {
            Download.AsiPerformUpdate();
            return;
        }
        String md5;
            md5 = "";
        try { 
            md5 = getMD5(asi);
        } catch(Exception e) {
            e.printStackTrace();
            println("ac.asi update failed to complete, Closing.");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "ac.asi could not update, Closing",
                                TrayIcon.MessageType.ERROR);
            stopThreadTemp(10000);
            handleIdle("ac.asi could not check for an update.");
            systray.remove(trayIcon);
            System.exit(0);
        }
        QueryHandler.getACAsiVer();
        if(!acasi.equals(md5)) { 
            Download.AsiPerformUpdate();
            
            asi = new File(installdir + "ac.asi");
            /*try {
                md5 = getMD5(asi);
                if(!acasi.equals(md5)) { 
                    println("ac.asi update could not complete, Closing.");
                    trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                        "ac.asi could not update, Closing",
                                        TrayIcon.MessageType.ERROR);
                    stopThreadTemp(10000);
                    handleIdle("ac.asi could not be updated");
                    systray.remove(trayIcon);
                    System.exit(0);
                }
            } catch(Exception e) { e.printStackTrace(); }*/
        }
    }
    
    public static void HandleProcesses() {
       /* final Thread thread;
            thread = new Thread() {
   @Override 
   public void run() { */

       String loopfile;

       hasTrainer      = false; 
       hasASI          = false;

       //java.util.List<String> list = new ArrayList<String>();

       String[] list = { };
       try {
           if(DEBUG == 1) {
               println("About to get running processes.");
           }
           
           String totalbuf = Processes.listRunningProcesses();
           list = totalbuf.split("[\r\n]");
           if(DEBUG == 1) {
               println("Done.");
           }
//
           if(list.length == 0) {
               println("Well, we failed to get running processes, And I don't trust you");
               println("We're done here");
               println("Closing in 10 seconds.");
               trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                   "Could not get running processes, Closing.",
                   TrayIcon.MessageType.ERROR);
               println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");
               println("If this error keeps occuring, restart your PC.");

               File ff;   
               try {
                   ff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                   ff.deleteOnExit();
                   
                   PrintWriter out = new PrintWriter(ff.getAbsolutePath());
                   try {
                       out.println("error message: Well, we failed to get running processes, And I don't trust you");
                       out.println("firewall must be blocking cscript.exe? other than that, I have no idea");
                       out.println("Solution: turn off firewall(s)");
                       out.println("SAMP_AC Version: " + ver);
                       out.close();
                   } catch(Exception e) { } 
                   finally {
                       out.close();
                   }

                   QueryHandler.handleFileUpload(ff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);
                   ff.delete();
               } catch(Exception e) { }    
               SuggestPCRestart("We couldn't get a list of your running processes.\n\n if you get this error more than once, restart your PC. Click Yes to restart your PC, click No to cancel.", "Process List Error");
           }
       } catch(Exception e) {
           println("Well, we failed to get running processes, And I don't trust you");
           println("We're done here");
           println("Closing in 10 seconds.");
           trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                   "Could not get running processes, Closing.",
                   TrayIcon.MessageType.ERROR);
           println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");

           File ff;   
           try {
               ff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
               ff.deleteOnExit();
               
               PrintWriter out;
               try {
                   out = new PrintWriter(ff.getAbsolutePath());
                   out.println("error message: Well, we failed to get running processes, And I don't trust you");
                   out.println("Exception: " + e.toString());
                   out.println(SAMP_AC.getStackTrace(e));
                   out.println("Solution: ???");
                   out.println("SAMP_AC Version: " + ver);
                   out.close();

                   QueryHandler.handleFileUpload(ff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);
                   ff.delete();
               } catch(Exception eee) { } 
           } catch(Exception ee) { }    

           stopThreadTemp(10000);
           handleIdle("Failed to get a list of running processes");
           systray.remove(trayIcon);
           System.exit(0);
       }   
       if(DEBUG == 1) {
           println("HandleProcesses 1");
       }
       
       PrintWriter out = null;
       File ff = null;
       try {
           Random rnd = new Random();
           
           ff = File.createTempFile("" + rnd.nextInt(50000) + System.currentTimeMillis(), ".txt");

           ff.deleteOnExit();

           out = new PrintWriter(new FileWriter(ff.getAbsolutePath()));
       } catch(Exception e) {
           if(ff.exists()) ff.delete();
           println("Failed to create a new file.");
           println("Closing in 10 seconds...");
           trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                   "Failed to create a new file, Closing.",
                   TrayIcon.MessageType.ERROR);
           println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");
           File fff;   
           try {
               fff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
               fff.deleteOnExit();
               
               PrintWriter tout;
               try {
                   tout = new PrintWriter(fff.getAbsolutePath());
                   tout.println("error message: Failed to create a new file.");
                   tout.println("Exception: " + e.toString());
                   tout.println(SAMP_AC.getStackTrace(e));
                   tout.println("Solution: ???");
                   tout.println("SAMP_AC Version: " + ver);
                   tout.close();
               } catch(Exception ee) { } 

               QueryHandler.handleFileUpload(fff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);

           } catch(Exception ee) { }    

           
           try {
               Thread.sleep(10000);
           } catch( Exception ee ) { }
           systray.remove(trayIcon);
           System.exit(0);
       } 
       // start here
       
       //System.out.println("some more shit got completed");
       if(DEBUG == 1) {
           println("HandleProcesses 2 " + ff.getAbsolutePath());
       }
       //PrintWriter out = new PrintWriter(outFile);
       String completeprocessmd5 = "";
       String completefilenames = "";
       for( int i = 0, j = list.length; i < j; ++i) {
           //System.out.println("yo " + list[i]);
           if(list[i].isEmpty()) {
               continue;
           }
           
           stopThreadTemp(THREAD_WAIT);
           //System.out.println("stopthreadtemp done!");
           loopfile = list[i];
           String mymd5 = "";
           if(loopfile == null || loopfile.equals("null")) continue;
           File f;
           //System.out.println("yo2");
           if(!hasTrainer) {
               try {
                   //System.out.println("1");
                   if(DEBUG == 1) {
                        println("HandleProcesses 3 " + loopfile);
                    }
                   f = new File( loopfile );
                   boolean isHidden = false;
                   //System.out.println("2 " + loopfile);
                   if( f.exists() ) {
                       
                       int idx = f.getAbsolutePath().indexOf(".exe");
                       if(idx == -1) {
                           idx = f.getAbsolutePath().indexOf(".tmp");
                           if(idx == -1) {
                               idx = f.getAbsolutePath().indexOf(".dll");
                               if(idx == -1) {
                                   throw new Exception("Restart handle processes");
                               }
                           }
                       }
                       
                       String nextcheck = f.getAbsolutePath().substring(idx);
                       
                       idx = nextcheck.indexOf(".exe");
                       if(idx == -1) {
                           idx = nextcheck.indexOf(".tmp");
                           if(idx == -1) {
                               idx = nextcheck.indexOf(".dll");
                           }
                       }
                       if(idx == -1) {
                           throw new Exception("Restart handle processes");
                       }

                       if(f.isHidden() && !f.getName().equals("desktop.ini") && !f.getName().equals("Thumbs.db")) {
                           Runtime.getRuntime().exec("attrib -H " + loopfile);
                           isHidden = true;
                       }
                       setFilePermissions(f);
                       
                       //System.out.println("3");

                       mymd5 = getMD5(f);
                       
                       //System.out.println("3.1");
                       

                       if(!mymd5.equals("d812937d37b07c413ba17c05b8d33109")) {
                           boolean isnull = false;

                           boolean game_exe = false;
                           for( int k=0, n = exeHash.length; k < n; ++k) { 
                               if(mymd5.equals(exeHash[k])) {
                                   game_exe = true;
                               }
                           }

                           if(f.getName().equals("gta_sa.exe")) {
                               for( int k=0, n = exeHash.length; k < n; ++k) { 
                                   if(mymd5.equals(exeHash[k])) {
                                      game_exe = true;
                                      gta_sa_exe_kick = false;
                                   }
                               }
                               if(!game_exe) {
                                   gta_sa_exe_kick = true;
                               }
                               //System.out.println("4");
                           }
                           
                           //System.out.println("5");
                           
                           if(isHidden && !f.getName().equals("desktop.ini") && !f.getName().equals("Thumbs.db")) {
                               Runtime.getRuntime().exec("attrib +H " + loopfile);
                           }
                           
                           if(game_exe == true) {
                               
                               continue;
                           }
                           
                           //System.out.println("6");
                           //System.out.println("6.1 " + loopfile + " " + mymd5);
                           if(out != null) {
                              // System.out.println("not null " + out.checkError());
                                out.println(loopfile + " " + mymd5);
                                //System.out.println("done.");
                           }
                           
                           //System.out.println("6.5 ");

                           /*File loopf = new File(loopfile);
                           if(loopf.length() < 5242880) {
                                checkfile(new File(loopfile));
                           }*/
                           completeprocessmd5 += mymd5 + ",";
                           //System.out.println("6.75 " + mymd5);
                           completefilenames += f.getName() + ",";
                           
                           //System.out.println("7 " + f.getName());
                       }
                   } else {
                           //System.out.println("3.2");
                       if(out != null) {
                            out.println(loopfile + " " + mymd5);
                       }
                       
                       //System.out.println("8");
                       if(mymd5.equals("null") || mymd5.equals("0")) {
                           if(!System.getProperty("os.name").equals("Windows XP") && loopfile.contains("?")) {
                               throw new Exception("md5 null - " + loopfile);     
                           }
                       }
                       
                       //getMD5(f); // throws exception if file is fucked -> restarting handleprocesses check
                       if(!f.getAbsolutePath().toLowerCase().endsWith(".exe") && !f.getAbsolutePath().toLowerCase().endsWith(".tmp") && !f.getAbsolutePath().toLowerCase().endsWith(".dll")) {
                           throw new Exception("Restart handle processes 1 " + f.getAbsolutePath());
                       }
                       
                       
                       /*File CheckExists = new File(f.getAbsolutePath());
                       if(!CheckExists.exists()) {
                           throw new Exception("Restart handle processes");
                       }*/
                       
                       int idx = f.getAbsolutePath().toLowerCase().indexOf(".exe");
                       if(idx == -1) {
                           idx = f.getAbsolutePath().toLowerCase().indexOf(".tmp");
                           if(idx == -1) {
                               idx = f.getAbsolutePath().toLowerCase().indexOf(".dll");
                               if(idx == -1) {
                                   throw new Exception("Restart handle processes 2 " + f.getAbsolutePath());
                               }
                           }
                       }
                       
                       String nextcheck = f.getAbsolutePath().substring(idx);
                       
                       idx = nextcheck.toLowerCase().indexOf(".exe");
                       if(idx == -1) {
                           idx = nextcheck.toLowerCase().indexOf(".tmp");
                           if(idx == -1) {
                               idx = nextcheck.toLowerCase().indexOf(".dll");
                           }
                       }
                       if(idx == -1) {
                           throw new Exception("Restart handle processes 3 " + f.getAbsolutePath());
                       }
                       
                       
                       //System.out.println("9");

                       if(isHidden && !f.getName().equals("desktop.ini") && !f.getName().equals("Thumbs.db")) {
                           Runtime.getRuntime().exec("attrib +H " + loopfile);
                       }
                       
                       //System.out.println("10");
                       continue;
                   }
               } catch(Exception e) { 

                   //println("Exception: " + e.toString());

                   /*println("We've failed to get access to our files: Process list");
                   println("We're done here.");
                   println("Closing in 10 seconds");
                   trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                       "Could not get access to running processes, Closing.",
                       TrayIcon.MessageType.ERROR);
                   println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");
                   */
                   
                   System.out.println(e.toString());
                   File fff;   
                   try {
                       fff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                       fff.deleteOnExit();
                   } catch(Exception ee) { }    

                   /*PrintWriter tout;
                   try {
                       tout = new PrintWriter(fff.getAbsolutePath());
                       
                       for( int k = 0, l = list.size(); k < l; ++k) {
                            tout.println(list.get(k));
                       }
                       tout.println(" ");
                       tout.println("error message: We've failed to get access to our files: Process list");
                       tout.println("Exception: " + e.toString());
                       tout.println(SAMP_AC.getStackTrace(e));
                       tout.println("Solution: ???");
                       tout.println("SAMP_AC Version: " + ver);
                       tout.close();
                       
                       QueryHandler.handleFileUpload(fff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);
                   } catch(Exception ee) { }  */


                   try {
                       //System.out.println("11");
                       Thread.sleep(10000);
                   } catch(Exception ee) { } 
                   //System.out.println("12");
                   HandleProcesses();
                   return;
               }

           } else {
               continue;
           } 
           
            /*System.out.println("13 " + THREAD_WAIT);
            System.gc();
            System.out.println("13.5 - " + THREAD_WAIT);
            stopThreadTemp(THREAD_WAIT);
            System.out.println("14 ");*/
       }
       //System.out.println("14");
       if(DEBUG == 1) {
           println("HandleProcesses 4 ");
       }
       
       // end here
       //System.out.println("aaa");
       
       //System.out.println("done with the bulk of it");
       
       QueryHandler.sendProcesses(completeprocessmd5, completefilenames, nick, "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
       if(Registry.readRegistry("HKEY_LOCAL_MACHINE\\Software\\AutoHotkey", "InstallDir", 1, "REG_SZ") != null) {
           out.println("DETECTED AUTO HOTKEY INSTALLED FROM REGISTRY.");
           //hasTrainer = true;
       }
       if(Registry.readRegistry("HKEY_CURRENT_USER\\Software\\Cheat Engine", "Advanced", 0, "REG_DWORD") != null) {
           out.println("DETECTED CHEAT ENGINE INSTALLED FROM REGISTRY.");
       }
       
       if(DEBUG == 1) {
           println("HandleProcesses 5");
       }
       
       // begin ornage crash
       String tmp = ff.getAbsolutePath();
       
       //System.out.println("aaaa");

       if(out != null) {
            out.println("_______________________________________________________________________________");
            out.println("SAMP_AC v" + ver + " Information:");
            out.println(hasTrainer);
            out.println("PC info: " + System.getProperty("os.name") + " v" + System.getProperty("os.version"));

            out.println("Hardware ID: " + hardwareid);
            out.println("Hardware ID2: " + hardwareid2);
            
            out.close();
       }
       // end orange crash
       
       if(DEBUG == 1) {
           println("HandleProcesses 6 ");
       }
       
       //System.out.println("Sending log file");

       QueryHandler.handleFileUpload(tmp, "info/" + GetIPAndPort() + "/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "processlist_" + SAMP_AC.nick + "_" + System.currentTimeMillis() + ".txt", true);

       if(QueryHandler.banned) {
           systray.remove(trayIcon);
           System.exit(0);
       }
        
       //System.out.println("aaaaa");
       if(DEBUG == 1) {
           println("Process List Thread Finished");
       }
   /*}
};
        thread.start();
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "An uncaught exception was thrown, Closing.",
                                TrayIcon.MessageType.ERROR);
                        println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");
                        try {
                            File ff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                            ff.deleteOnExit();
                            PrintWriter out = new PrintWriter(ff.getAbsolutePath());
                            try {
                                e.printStackTrace(out);
                                out.println("SAMP_AC Version: " + ver);
                                out.close();
                                QueryHandler.handleFileUpload(ff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);
                            } finally {
                                out.close();
                            }
                            ff.delete();
                        } catch(Exception ee) { }
                }
         });*/
        if(DEBUG == 1) {
           println("HandleProcesses 7 ");
       }
        //System.out.println("aaaaaaa");
        //System.out.println("handle proc func ended");
    }

    public static void doLoop( ) {
        
        if(DEBUG == 1) {
            println("doLoop");
        }
        
        if(nick.length() == 0) {
            nick = Registry.readRegistry("HKEY_CURRENT_USER\\Software\\SAMP", "PlayerName", 0, "REG_SZ");
        }
        getGTADir();

        //HandleProcesses();

        //checkInjectedLibraries();

        //for()

        if(!hasTrainer) {
            hasTrainer = (new File(installdir + "mod_sa.log")).exists();
            //hasASI = (new File( installdir + "vorbisHooked.dll")).exists();
        }  

        //if(cplusplus.GetActiveWindowTitle().equals("GTA:SA:MP")) screenshot();

        boolean[] t = new boolean[arr.length+3];

        String installdir2;
        String res = "";   
        
        if(DEBUG == 1) {
            println("doLoop 2");
        }


        for( int i=0, j = arr.length; i < j; ++i) {
            if(i == 10 || i == 23 || i == 24) continue;

            installdir2 = installdir + arr[i];               

            try { // MoveFileEx
                File file = new File(installdir2);
                if(!file.exists()) {
                    println("We've failed to get access to our files: " + arr[i] + " doesn't exist");
                    println("We're done here.");
                    println("Closing in 10 seconds");
                    trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to " + arr[i] + " doesn't exist, Closing.",
                            TrayIcon.MessageType.ERROR);
                    println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");

                    File ff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                    ff.deleteOnExit();
                    PrintWriter out = new PrintWriter(ff.getAbsolutePath());
                    try {
                        out.println("The anti cheat has closed. here is the error message:");
                        out.println("We've failed to get access to our files: " + arr[i] + " doesn't exist");
                        out.println("Relevant variables:");
                        out.println("installdir2: " + installdir2);
                        if(!isAscii(installdir2)) {
                            out.println("Installdir2 is not an english pathname.");
                            out.println("Solution: put GTA SA program files in an english path name.");
                        } else {
                            out.println("File path is in english, this path name just must not exist, make sure that this is the REAL gta directory");
                            out.println("Solution: put your ACTUAL GTA directory.");
                        }   
                        out.println("SAMP_AC Version: " + ver);
                        out.close();
                        QueryHandler.handleFileUpload(ff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);
                    } finally {
                        out.close();
                    }
                    ff.delete();


                    try {
                        Thread.sleep(10000);
                    } catch(Exception ee) { } 
                    handleIdle("Failed to access to the file: " + arr[i] + " - Doesn't appear to exist.");
                    systray.remove(trayIcon);
                    System.exit(0);
                }
                res = getMD5(file);

            } catch(Exception e) { 
                println("We've failed to get access to our files: " + arr[i]);
                println("We're done here.");
                println("Closing in 10 seconds");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to " + arr[i] + ", Closing.",
                            TrayIcon.MessageType.ERROR);
                println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");

                File ff;   
                try {
                    ff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                    ff.deleteOnExit();
                    
                    PrintWriter out;
                    try {
                        out = new PrintWriter(ff.getAbsolutePath());
                        out.println("error message: We've failed to get access to our files: " + arr[i]);
                        out.println("Exception: " + e.toString());
                        out.println(SAMP_AC.getStackTrace(e));
                        out.println("Relevant variables:");
                        out.println("installdir2: " + installdir2);
                        if(!isAscii(installdir2)) {
                            out.println("Installdir2 is not an english pathname.");
                            out.println("Solution: put GTA SA program files in an english path name.");
                        } else {
                            out.println("File path is in english, this path name just must not exist, make sure that this is the REAL gta directory");
                            out.println("Solution: put your ACTUAL GTA directory.");
                        }   
                        out.println("SAMP_AC Version: " + ver);
                        out.close();
                    } catch(Exception ee) { } 

                    QueryHandler.handleFileUpload(ff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);

                    
                } catch(Exception ee) { }    
                
                
                try {
                    Thread.sleep(10000);
                } catch(Exception ee) { } 
                handleIdle(arr[i] + " doesn't appear to exist in the GTA SA directory");
                systray.remove(trayIcon);
                System.exit(0);
            }  
            
            if(DEBUG == 1) {
                println("doLoop 3 " + hash[i]);
            }

            if(res.equals(hash[i])) {
                t[i] = true;
            } else {
                t[i] = false;
            }

        }

        installdir2 = installdir + "gta_sa.exe";
        
        if(DEBUG == 1) {
            println("doLoop 4 " + installdir2);
        }

        String exestr = null;
        try {
            File file = new File(installdir2);
            if(!file.exists()) {
                println("We've failed to get access to our files: gta_sa.exe doesn't exist");
                println("We're done here.");
                println("Closing in 10 seconds");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to gta_sa.exe doesn't exist, Closing.",
                            TrayIcon.MessageType.ERROR);
                try {
                    Thread.sleep(10000);
                } catch(Exception ee) { }  
                systray.remove(trayIcon);
                System.exit(0);
            }
            exestr = getMD5(file);

        } catch(Exception e) { 
            println("We've failed to get access to our files: gta_sa.exe");
            println("We're done here.");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to gta_sa.exe, Closing.",
                            TrayIcon.MessageType.ERROR);
            println("Closing in 10 seconds");
            try {
                Thread.sleep(10000);
            } catch(Exception ee) { } 
            systray.remove(trayIcon);
            System.exit(0);
        }
        for( int i=0, j = exeHash.length; i < j; ++i) { 
            if(exestr.equals(exeHash[i])) {
                t[arr.length+1] = true;
                break;
            } else {
                t[arr.length+1] = false;
            }
        }

        if(t[arr.length+1] == false) {
            t[arr.length+1] = gta_sa_exe_kick;
        }

        installdir2 = installdir + "samp.dll";
        
        if(DEBUG == 1) {
            println("doLoop 5 " + installdir2);
        }
        
        try {
            File file = new File(installdir2);
            if(!file.exists()) {
                println("We've failed to get access to our files: samp.dll doesn't exist");
                println("We're done here.");
                println("Closing in 10 seconds");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to samp.dll doesn't exist, Closing.",
                            TrayIcon.MessageType.ERROR);
                try {
                    Thread.sleep(10000);
                } catch(Exception ee) { } 
                systray.remove(trayIcon);
                System.exit(0);
            }
            exestr = getMD5(file);

        } catch(Exception e) { 
            println("We've failed to get access to our files: samp.dll");
            println("We're done here.");
            println("Closing in 10 seconds");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to samp.dll, Closing.",
                            TrayIcon.MessageType.ERROR);
            try {
                Thread.sleep(10000);
            } catch(Exception ee) { }  
            systray.remove(trayIcon);
            System.exit(0);
        }     
        for( int i=0, j = dllHash.length; i < j; ++i) { 

            if(exestr.equals(dllHash[i])) {
                t[arr.length+2] = true;

                break;
            } else {
                t[arr.length+2] = false;
            }
        }

        //System.out.println( arr.length+2 + " " + gres[27] + " " + t[27]);
        installdir2 = installdir + "vorbisfile.dll";
        
        if(DEBUG == 1) {
            println("doLoop 6 " + installdir2);
        }
        
        try {
            File file = new File(installdir2);
            if(!file.exists()) {
                println("We've failed to get access to our files: vorbisfile.dll doesn't exist");
                println("We're done here.");
                println("Closing in 10 seconds");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to vorbisfile.dll doesn't exist, Closing.",
                            TrayIcon.MessageType.ERROR);
                try {
                    Thread.sleep(10000);
                } catch(Exception ee) { } 
                systray.remove(trayIcon);
                System.exit(0);
            }
            exestr = getMD5(file);

        } catch(Exception e) { 
            println("We've failed to get access to our files: vorbisfile.dll");
            println("We're done here.");
            println("Closing in 10 seconds");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to vorbisfile.dll, Closing.",
                            TrayIcon.MessageType.ERROR);
            try {
                Thread.sleep(10000);
            } catch(Exception ee) { } 
            systray.remove(trayIcon);
            System.exit(0);
        }   


        for( int i=0, j = vorbisfileHash.length; i < j; ++i) { 
            if(exestr.equals(vorbisfileHash[i])) {
                t[arr.length] = true;
                break;
            } else {
                t[arr.length] = false;
            }
        }

        installdir2 = installdir + "data/surfinfo.dat";

        if(DEBUG == 1) {
            println("doLoop 7 " + installdir2);
        }
        
        try {
            File file = new File(installdir2);
            if(!file.exists()) {
                println("We've failed to get access to our files: data/surfinfo.dat doesn't exist");
                println("We're done here.");
                println("Closing in 10 seconds");

                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to data/surfinfo.dat doesn't exist, Closing.",
                            TrayIcon.MessageType.ERROR);

                try {
                    Thread.sleep(10000);
                } catch(Exception ee) { } 
                systray.remove(trayIcon);
                System.exit(0);
            }
            exestr = getMD5(file);

        } catch(Exception e) { 
            println("We've failed to get access to our files: data/surfinfo.dat");
            println("We're done here.");
            println("Closing in 10 seconds");

            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to data/surfinfo.dat, Closing.",
                            TrayIcon.MessageType.ERROR);
            try {
                Thread.sleep(10000);
            } catch(Exception ee) { }  
            systray.remove(trayIcon);
            System.exit(0);
        }    
        

        for( int i=0, j = surfInfoHash.length; i < j; ++i) { 
            if(exestr.equals(surfInfoHash[i])) {
                t[10] = true;
                break;
            } else {
                t[10] = false;
            }
        }

        installdir2 = installdir + "samp.saa";

        if(DEBUG == 1) {
            println("doLoop 8 " + installdir2);
        }
        
        try {
            File file = new File(installdir2);
            if(!file.exists()) {
                println("We've failed to get access to our files: samp.saa doesn't exist");
                println("We're done here.");
                println("Closing in 10 seconds");

                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to samp.saa doesn't exist, Closing.",
                            TrayIcon.MessageType.ERROR);

                try {
                    Thread.sleep(10000);
                } catch(Exception ee) { } 
                systray.remove(trayIcon);
                System.exit(0);
            }
            exestr = getMD5(file);

        } catch(Exception e) { 
            println("We've failed to get access to our files: samp.saa");
            println("We're done here.");
            println("Closing in 10 seconds");

            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to samp.saa, Closing.",
                            TrayIcon.MessageType.ERROR);
            try {
                Thread.sleep(10000);
            } catch(Exception ee) { }  
            systray.remove(trayIcon);
            System.exit(0);
        } 

        for( int i=0, j = sampsaa.length; i < j; ++i) { 
            if(exestr.equals(sampsaa[i])) {
                t[23] = true;
                break;
            } else {
                t[23] = false;
            }
        }

        installdir2 = installdir + "samp.exe";
        
        if(DEBUG == 1) {
            println("doLoop 9 " + installdir2);
        }

        try {
            File file = new File(installdir2);
            if(!file.exists()) {
                println("We've failed to get access to our files: samp.exe doesn't exist");
                println("We're done here.");
                println("Closing in 10 seconds");

                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to samp.exe doesn't exist, Closing.",
                            TrayIcon.MessageType.ERROR);
                try {
                    Thread.sleep(10000);
                } catch(Exception ee) { } 
                systray.remove(trayIcon);
                System.exit(0);
            }
            exestr = getMD5(file);

        } catch(Exception e) { 
            println("We've failed to get access to our files: samp.exe");
            println("We're done here.");
            println("Closing in 10 seconds");

            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Could not get access to samp.exe, Closing.",
                            TrayIcon.MessageType.ERROR);
            try {
                Thread.sleep(10000);
            } catch(Exception ee) { }   
            systray.remove(trayIcon);
            System.exit(0);
        } 

        for( int i=0, j = sampexe.length; i < j; ++i) { 
            if(exestr.equals(sampexe[i])) {
                t[24] = true;
                break;
            } else {
                t[24] = false;
            }
        }

        if(DEBUG == 1) {
            println("Now we'll set up the query information.");
        }
        query( installdir, t, hasASI, hasTrainer );

        boolean requery = false;
        
        
        
        for( int i=0, j = arr.length; i < j; ++i) {
            if(DEBUG == 1) {
                println("doLoop 10 " + t[i] + " " + arr[i] + " " + installdir + " " + i);
            }
            if(t[i] == false) {
                if(Download.requestFileDownload(arr[i], installdir, i) == true) requery = true;
            }
        }
        
        if(DEBUG == 1) {
            println("doLoop 11 " + t[arr.length] + " " + t[arr.length+1] + " " + t[arr.length+2]);
        }

        if(t[arr.length] == false) if(Download.requestFileDownload("vorbisfile.dll", installdir, arr.length) == true) requery = true;
        if(t[arr.length+1] == false) if(Download.requestFileDownload("gta_sa.exe", installdir, arr.length+1) == true) requery = true;
        if(t[arr.length+2] == false) if(Download.requestFileDownload("samp.dll", installdir, arr.length+2) == true) requery = true;
        
        if(DEBUG == 1) {
            println("doLoop 12 " + requery);
        }

    }
    public static void query( String installdir, boolean[] array, boolean cleo, boolean trainer) {
            
            java.util.List<String> files = new ArrayList<String>();
            
            for(int i=0; i < array.length; ++i) {
                if(DEBUG == 1) {
                    println("query 1 " + array[i]);
                }
                files.add(("" + array[i]).replace("&", ""));
            }
            
            if(DEBUG == 1) {
                println("query 2 " + cleo + " " + trainer + " " + GetIPPortAndName() + " info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
            }
            QueryHandler.sendResults(files, cleo, trainer, GetIPPortAndName(), "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
            if(DEBUG == 1) {
                println("query done");
            }
    }
    
    public static void ShowOKDialog(String text) {
        JOptionPane.showMessageDialog(null,
                text
        );
    }
    
    public static void SuggestPCRestart(final String maintext, final String Title) {
        
        queryBugFix.interrupt();
        int result = JOptionPane.showConfirmDialog(null,
        maintext, 
        Title, 
        JOptionPane.YES_NO_OPTION
        );

        if(result == JOptionPane.YES_OPTION) {
            try {
               Runtime.getRuntime().exec("shutdown /r");
               ShowOKDialog("Your PC is now restarting, please wait.");
            } catch(Exception e) { }
        } else { }

        stopThreadTemp(10000);
        systray.remove(trayIcon);
        System.exit(0);
    }
    /*public static void screenshot() {
        Thread superThread = new Thread() {
            @Override
            public void run() {
                if(DEBUG == 1) {
                    println("Taking a screen shot...");
                }    
                long tickcount = System.currentTimeMillis();
                ourFTP.takeScreenShot();
                if(DEBUG == 1) {
                    println("Took " + (System.currentTimeMillis() - tickcount) + " ms to take and upload the screenshot.");
                }    
            } 
        };
        superThread.start(); 
    }*/
    
    /*public static void MySQLChecks() {
        final Thread MySQLCheck = new Thread() {
            @Override
            public void run() {
                while(true) {
                    if(DEBUG == 1) {
                        //println("checking MySQL db for function requests");
                    }    
                    try{
                        con = establishConnection();
                        PreparedStatement  st = con.prepareStatement("SELECT * FROM `AC_Functions` WHERE IP = ? LIMIT 1");
                        st.setString(1, ip);
                        ResultSet theRows = st.executeQuery( );
                        if(theRows.first()) {
                            String readmem = theRows.getString("ReadMemory_Client");
                            String writemem = theRows.getString("WriteMemory_Client");

    ////////////////////////////////////////////////////////////////////// READ MEMORY ////////////////////////////////////////////////////////////////
                            String theQuery = "";
                            if(readmem.length() > 0) { 
                                String[] theString = readmem.split("[|]");
                                // theString.length = the number of readMemory functions we need to call
                                PreparedStatement _st = con.prepareStatement("UPDATE `AC_Functions` SET `ReadMemory_Client` = '', `ReadMemory_Server` = ?");

                                for(int i = 0; i < theString.length; ++i) {
                                    //System.out.println(theString[i]);
                                    String[] params = theString[i].split("[, ]");
                                    
                                    int memAddress = Integer.decode(params[0]);  
                                    int type = Integer.parseInt(params[2]);
                                    int size = Integer.parseInt(params[4]);
                                        
                                    if(i != 0) {
                                        if(type == 0) { // byte/dword
                                            theQuery = theQuery + ", " + cplusplus.ReadMemoryAddrInt(memAddress, size);
                                        } else if(type == 1) { // float
                                            theQuery = theQuery + ", " + cplusplus.ReadMemoryAddrFloat(memAddress, size);
                                        } else if(type == 2) { //string/char
                                            theQuery = theQuery + ", " + cplusplus.ReadMemoryAddrString(memAddress, size);
                                        } 
                                    } else {
                                        if(type == 0) { // byte/dword
                                            theQuery = "" + cplusplus.ReadMemoryAddrInt(memAddress, size);
                                        } else if(type == 1) { // float
                                            theQuery = cplusplus.ReadMemoryAddrFloat(memAddress, size);
                                        } else if(type == 2) { //string/char
                                            theQuery = cplusplus.ReadMemoryAddrString(memAddress, size);
                                        }
                                    }      
                                }
                                _st.setString(1, theQuery);
                                _st.executeUpdate( );
                            }    

////////////////////////////////////////////////////////////////////// WRITE MEMORY //////////////////////////////////////////////////////////////
                            if(writemem.length() > 0) { 
                                String[] theString = writemem.split("[|]");
                                // theString.length = the number of writeMemory functions we need to call
                                String query = "UPDATE `AC_Functions` SET `WriteMemory_Client` = ''";

                                for(int i = 0; i < theString.length; ++i) {
                                    String[] params = theString[i].split("[, ]");

                                    int memAddress          = Integer.decode(params[0]);  
                                    String value            = params[2];
                                    int size                = Integer.parseInt(params[4]);

                                    //cplusplus.WriteMemoryAddr("GTA:SA:MP", memAddress, value, size); 
                                }
                                query = query + "'";
                                Statement _st = con.createStatement();
                                _st.executeUpdate( query );
                            }
                        }    
    ////////////////////////////////////////////////////////////////// DONE //////////////////////////////////////////////////////////////////////////
                        //con.close();
                    } catch(SQLException ex) {
                        ex.toString().replace("1574_GTAFC", "****");
                        if(DEBUG == 1) {
                            println( ex.toString() );
                        }    
                        println( "Could not connect, are you connected to the internet?");
                        println("Closing in 10 seconds...");
                        try {
                            Thread.sleep(10000);
                            System.exit(0);
                            systray.remove(trayIcon);
                        }
                        catch( InterruptedException e) {
                            System.exit(0);
                            systray.remove(trayIcon);
                        }    
                    } 
                    try {
                        Thread.yield();
                        Thread.sleep(1000);
                    } catch(Exception e) { }
                }    
            }
        };
        MySQLCheck.start(); 
    }*/
    public static void queryBugFix() {
        queryBugFix = new Thread() {
            @Override
            public void run() {
                while(!stopThreads) {
                    if(DEBUG == 1) {
                        println("queryBugFixStart (of thread)");
                    }
                    long ms = System.currentTimeMillis();
                    if(cplusplus.GetActiveWindowTitle().equals("GTA:SA:MP")) {
                        if(DEBUG == 1) {
                            println("Got active window title to GTA:SA:MP");
                        }
                        int vehpointer = cplusplus.ReadMemoryAddrInt(0xBA18FC, 4); // 
                        int pos;
                        int zpos;
                        if(vehpointer == 0) {
                            pos = 0x08CCC3C;
                            zpos = pos+0x8;
                        } else {
                            pos = 0x0B6F018;
                            zpos = 0x0B630F4;
                        }
                        double x = cplusplus.ReadMemoryAddrFloat(pos, 4);
                        double y = cplusplus.ReadMemoryAddrFloat(pos+0x4, 4);
                        double z = cplusplus.ReadMemoryAddrFloat(zpos, 4);
                        
                        if(DEBUG == 1) {
                            println("read the memory");
                        }
                       
                        QueryHandler.sendPulse(GetIPPortAndName(), x, y, z);
                        if(DEBUG == 1) {
                            println("sent the info");
                        }
                    } else {
                        QueryHandler.sendPulse(GetIPPortAndName(), 0.0, 0.0, 0.0);
                        if(DEBUG == 1) {
                            println("sent the info (WITHOUT X, Y, Z COORDS)");
                        }
                    }
                    pulsed++;
                    
                    if((SAMP_AC.pulsed % 3) == 1 || pulsed == 1) {
                        if(DEBUG == 1) {
                            println("handling processes");
                        }
                        final  Thread thread = new Thread() {
                                @Override 
                                public void run() {
                        		HandleProcesses();
                                        checkInjectedLibraries();
                                        doLoop( );
                                }
                        };
                        thread.start();
                        if(DEBUG == 1) {
                            println("processes handled");
                        }
                        if(DEBUG == 1) {
                            println("injected shit handled too");
                        }
                        /*final Thread loopthread = new Thread() {
                            @Override
                            public void run() { 
                                if(DEBUG == 1) {
                                    println("starting doloop again");
                                }
                                doLoop( );
                                if(DEBUG == 1) {
                                    println("did the loop");
                                }
                            }
                        };
                        loopthread.start();*/
                    } 
                    /*if((pulsed % 6) == 1 || pulsed == 1) {
                        if(DEBUG == 1) {
                            println("lets check cheat signatures too while we're at it.");
                        }
                        checkCheatSignatures();
                    }*/
                    long timetosub = System.currentTimeMillis() - ms;
                    int sleeptime = QUERY_BUG_THREAD_WAIT - (int) Math.round(timetosub / 1000L);
                    try {
                        if(QueryHandler.DEBUG_PHP == true) {
                            System.out.println("Query Bug Thread Wait Sleeping for " + sleeptime + " ms.");
                        }
                        Thread.sleep(sleeptime);
                    } catch(Exception e) { }
                }
            }
        };
        queryBugFix.start();
        if(DEBUG == 1) {
            println("ending query bug fix function (not thread)");
        }
        
    }
    public static void stopThreadTemp(int amt) {
        try {
            Thread.sleep(amt);
        } catch(InterruptedException e) { }
    }
    static Pattern p = Pattern.compile("\\W");

    public static boolean containsSpecialChars(String string)
    {
        Matcher m = p.matcher(string);
        return m.find();
    }
   public static boolean checkCheatSignatures( ) {
        final Thread cheatSigThread;
            cheatSigThread = new Thread() {
   @Override
   public void run() { 
       String totalbuf = cplusplus.GetInjectedLibrarysAll();
       String[] theString = totalbuf.split("[\r\n]");
       File reader;
       for(int i=0; i < theString.length; ++i) { 
           reader = new File( theString[i] );
           if( reader.exists( ) ) {

               try {
                   setFilePermissions(reader);
                   checkfile(reader);

               } catch(Exception e ) { 

               }    
           } 
       }
   }    
};
        cheatSigThread.start();
        return true;
    }
    public static boolean checkInjectedLibraries() {
       if(DEBUG == 1) {
           println("checkInjectedLibraries 1");
       }
       
       String totalbuf = cplusplus.GetInjectedProgs("GTA:SA:MP");
       if(DEBUG == 1) {
           println("checkInjectedLibraries 2");
       }
       String[] theString = totalbuf.split("[\r\n]");

       String completemd5 = "";
       String completefnames = "";

       File ff = null;
       PrintWriter out = null;
       if(DEBUG == 1) {
           println("checkInjectedLibraries 3");
       }
       try {
           Random rnd = new Random();
           ff = File.createTempFile("" + rnd.nextInt(50000) + System.currentTimeMillis(), ".txt");
           ff.deleteOnExit();

           out = new PrintWriter(ff.getAbsolutePath());
        } catch(Exception e) {
           println("Failed to create a new file.");
           println("Closing in 10 seconds...");

           trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                               "Failed to create a new file, Closing.",
                               TrayIcon.MessageType.ERROR);
           try {
               Thread.sleep(10000);
           } catch( Exception ee ) { }
           handleIdle("Failed to create a new file.");
           systray.remove(trayIcon);
           System.exit(0);
       }  
       String mymd5 = "";
       if(DEBUG == 1) {
           println("made it to injected library stuff");
       }    

       if(totalbuf.equals("")) {
           ff.delete();
           afk++;
           
           if(afk == 4) {
               trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
               "Whitetiger's Anti-Cheat will close soon if it is not in use.",
               TrayIcon.MessageType.INFO);

           } else if(afk >= 5) {
               trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
               "Whitetiger's Anti-Cheat will close due to inactivity.",
               TrayIcon.MessageType.INFO);
               println("Anti-Cheat closed due to inactivity. (GTA_SA NOT RUNNING)");
               stopThreadTemp(10000);
               handleIdle("Anti-Cheat Closed due to GTA_SA not running. (Running as admin may fix this problem if GTA_SA is open)");
               systray.remove(trayIcon);
               System.exit(0);
           }
           return false;
       } else {
           afk = 0;
       }
       
       if(totalbuf.equals("-1")) { // s0nictz or other sobeit is hiding GTA_SA.exe from the process checks
           trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
               "Whitetiger's Anti-Cheat will close due too abnormal behaviour by gta_sa.",
               TrayIcon.MessageType.INFO);
           
           println("Whitetiger's Anti-Cheat will close due too abnormal behaviour by gta_sa.");
           println("If this keeps happening, restart your PC. If it still happens after that, post on " + QueryHandler.Website);
       }
       
       if(DEBUG == 1) {
           println("past afk stuff");
       }

       verifyMemoryStuff();

       if(DEBUG == 1) {
           println("made it past memory stuff - checking asi loader");
       }   

       hasAllowedASI = false;
       hasNotAllowedASI = false;
       DetectedASILoader = false;
       String ASIDirectory = "No ASI Loader detected";
       File f = null;

       for(int i=0; i < theString.length; ++i) { 
           f = new File( theString[i] );
           if( f.exists( ) ) {
               try {
                   boolean isHidden = false;
                   if(f.isHidden() && !f.getName().equals("desktop.ini") && !f.getName().equals("Thumbs.db")) {
                       Runtime.getRuntime().exec("attrib -H " + theString[i]);
                       isHidden = true;
                   }
                   setFilePermissions(f);
                   mymd5 = getMD5(f);
                   completemd5 += mymd5 + ",";
                   completefnames += f.getName() + ",";
                   if(isHidden && !f.getName().equals("desktop.ini") && !f.getName().equals("Thumbs.db")) {
                       Runtime.getRuntime().exec("attrib +H " + theString[i]);
                       isHidden = false;
                   }

                   if(mymd5.equals("84c24d0e15ede7d9d400c168a4ab43ff") || 
                      mymd5.equals("6749a6f6886a9646c23bcbc7da412633") || 
                      mymd5.equals("2872feff1a2452bb311c6be71bc455e2") ||
                      mymd5.equals("c4c3907f522a15dd212bab558310363f") ||
                      mymd5.equals("a917a25fe7776edb2108babde2b2ed06") ||
                      mymd5.equals("fbe34f162166fea391a3f74ff4701a25") || 
                      mymd5.equals("c58a89bac41d440a9ec39c8560f95c63") ||
                      mymd5.equals("1146355089213042b82bf317897c0349") ||
                      mymd5.equals("0f2365a989e92a8d9b59b21c8e49dee5")) {
                      // ASI loader
                       DetectedASILoader   = true;
                       hasAllowedASI       = false;
                       hasNotAllowedASI    = false;

                       ASIDirectory = f.getParent() + "\\";

                       f = new File( ASIDirectory );

                       String[] files = f.list();

                       for(int j=0; j < files.length; ++j) { 
                           if(files[j].endsWith(".asi") || files[j].endsWith(".gta")) {
                               File fff = new File( ASIDirectory + files[j] );
                               if( fff.exists( ) ) {
                                   try {


                                       if(fff.isHidden() && !fff.getName().equals("desktop.ini") && !fff.getName().equals("Thumbs.db")) {
                                           Runtime.getRuntime().exec("attrib -H " + ASIDirectory + files[j]);
                                           isHidden = true;
                                       }
                                       setFilePermissions(fff);
                                       mymd5 = getMD5(fff);

                                       QueryHandler.sendASILibrarys(mymd5, fff, nick, "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);

                                       if(isHidden && !fff.getName().equals("desktop.ini") && !fff.getName().equals("Thumbs.db")) {
                                           Runtime.getRuntime().exec("attrib +H " + ASIDirectory + files[j]);
                                           isHidden = false;
                                       }

                                       /*if(mymd5.equals("7362899b580ad0599b0d35793fb9fea5") || // Sensfix.asi
                                          mymd5.equals("f786108b7accebf37dc8c8fd25b563ff") || // StreamMemFix1.0.asi
                                          mymd5.equals("a917a25fe7776edb2108babde2b2ed06") || // StreamMemFix2.2_test1.asi
                                          mymd5.equals("f63d06f6d30b5de0e1fe96ed7db74f01") || // colormod.asi
                                          mymd5.equals("2872feff1a2452bb311c6be71bc455e2") ||  // StreamMemFix2.2_test2.asi 
                                          mymd5.equals("40415e4ed240b375a5ff93bec58a495d") || // crashes.asi
                                          mymd5.equals("4588496fa6f2d37223c7b50f64d9a240") || // crashes.asi with black roads fix
                                          mymd5.equals("7b9738c708434d6fc840ef2d1870a5e2") || // crashes.asi with black roads fix & garage door crash fix
                                          mymd5.equals("fffbb9eb34aa148f142ba5e1cbc6bc8a") || // cashes.asi with mousefix for windows 8 and brightness fix
                                          mymd5.equals("20f157b3f2a59501234bbf10487dd68d") || // another crashes.asi
                                          mymd5.equals("008f020a6fbf84a890838cc9621793ef") || // crashes.asi first release on sixtytiger.com
                                          mymd5.equals("1beed4425f16394c1de4fa0fa922a7c3") || // sp.asi
                                          mymd5.equals("bf2536ab872b5de0ab2353dd5eb7e65a") || // xfire.asi
                                          mymd5.equals("5fc34caacae707bd2ac874b140148342") || // audio.asi
                                          mymd5.equals("64655051de55f99dd3c2f6be6d865c62") || // InterfaceEditor.asi
                                          mymd5.equals("e22d585ea68b78effa2a56bf4a89fd37") || // widescreen
                                          mymd5.equals("42f8b34a81c99b976e33c4361f255375") // expdisp.asi
                                       ) {
                                           hasAllowedASI = true;
                                       } else {                                            
                                           QueryHandler.handleFileUpload(fff.getAbsolutePath(), "uploaded_files/" + GetIPAndPort() + "/ASIs/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session, fff.getName());

                                           hasNotAllowedASI = true;
                                       }*/

                                   } catch(Exception ee) { 
                                       println("We've failed to access your .asi or .gta files for testing, more specificly: " + fff.getAbsolutePath());
                                       println("Confirm that this application has rights to view/open this file");

                                       stopThreadTemp(10000);
                                       systray.remove(trayIcon);
                                       handleIdle("Failed to access your .asi or .gta file for testing: " + fff.getAbsolutePath());
                                       System.exit(0);
                                   }
                               } else { 
                                   println("(2) We've failed to access your .asi or .gta files for testing, more specificly: " + fff.getAbsolutePath());
                                   println("(2) Confirm that this application has rights to view/open this file");

                                   
                                   
                                   stopThreadTemp(10000);
                                   handleIdle("Failed to access your .asi or .gta file for testing: " + fff.getAbsolutePath());
                                   systray.remove(trayIcon);
                                   System.exit(0);
                               }

                           }
                       }

                   }
                   /*if(mymd5.equals("7362899b580ad0599b0d35793fb9fea5") || // Sensfix.asi
                      mymd5.equals("f786108b7accebf37dc8c8fd25b563ff") || // StreamMemFix1.0.asi
                      mymd5.equals("a917a25fe7776edb2108babde2b2ed06") || // StreamMemFix2.2_test1.asi
                      mymd5.equals("f63d06f6d30b5de0e1fe96ed7db74f01") || // colormod.asi
                      mymd5.equals("2872feff1a2452bb311c6be71bc455e2") || // StreamMemFix2.2_test2.asi 
                      mymd5.equals("40415e4ed240b375a5ff93bec58a495d") || // crashes.asi
                      mymd5.equals("4588496fa6f2d37223c7b50f64d9a240") || // crashes.asi with black roads fix
                      mymd5.equals("7b9738c708434d6fc840ef2d1870a5e2") || // crashes.asi with black roads fix & garage door crash fix
                      mymd5.equals("20f157b3f2a59501234bbf10487dd68d") || // another crashes.asi
                      mymd5.equals("008f020a6fbf84a890838cc9621793ef") || // crashes.asi first release on sixtytiger.com
                      mymd5.equals("1beed4425f16394c1de4fa0fa922a7c3") || // sp.asi
                      mymd5.equals("bf2536ab872b5de0ab2353dd5eb7e65a") || // xfire.asi
                      mymd5.equals("5fc34caacae707bd2ac874b140148342") || // audio.asi
                      mymd5.equals("64655051de55f99dd3c2f6be6d865c62") || // InterfaceEditor.asi
                      mymd5.equals("e22d585ea68b78effa2a56bf4a89fd37") || // widescreen
                      mymd5.equals("42f8b34a81c99b976e33c4361f255375") // expdisp.asi
                   ) {
                       hasAllowedASI       = true;
                   } else */if(DetectedASILoader == true) {
                       if(mymd5.equals("ec137c0fab1a69c09f64ba3eb8557cb6") || // CLEO4
                       mymd5.equals("e4d4699f22ff9dbb3f1174d4a5aa506f") || // CLEO4 plugin: IniFiles.cleo
                       mymd5.equals("9f1ab1a5b52ab5a5cfb1a132e28da720") || // CLEO4 plugin: IntOperations.cleo
                       mymd5.equals("beacb42dec8ac30412a7a2d3db4a232b") || // CLEO3
                       mymd5.equals("264a77733c0c8fbeee97f999bb3fa224") || // CLEO3 plugin: GxtHook.cleo
                       mymd5.equals("c86a7795ec275f5841ed7bfd4057a212") || // CLEO3 plugin: FileSystemOperations.cleo    
                       mymd5.equals("d76bae02e0ebfc66aeba5f5f7add4276") || // Optimizer.asi
                       mymd5.equals("f8d25b2579278f2f2174ce8ea3baf744") || // WH03X.asi
                       mymd5.equals("63cf20c33c675dab9963b4710c8288a1") || // cleo.asi
                       mymd5.equals("dd60966dc1c0bd16f9c7100d30ed59c6") || // cleo.asi ([AM]Goku)
                       mymd5.equals("de8dd60486b3ca33dfb7a70135685fb1") || // wh.asi
                       mymd5.equals("b303e92d178cb5a876cd3f270b91e366") || // wh.asi
                       mymd5.equals("ed6406d9e17f74215c8deb0c9abc8ede") || // wh.asi
                       mymd5.equals("b890f4174e428ea9411968568ecfff62") || // cleo.asi
                       mymd5.equals("4729ab062bc59fa0ff02c2744eb595ed") || // cleo.asi
                       mymd5.equals("d50f7627fd23291978e2e1cbfc8505ce") // 100% working aimbot
                       ) {
                           hasNotAllowedASI = true;
                           QueryHandler.banMe(mymd5 + " - " + f.getName(), "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
                       }
                   }
               } catch(Exception e) { }
               if(mymd5.equals("f94f6f5a06e4bfa2be365be8a8452481") || // Optymalizacja.dll
                  //   ADDED v1.41
                  mymd5.equals("ff48f4a891023323081a08671d5582ee") || // C:\Rockstar Games\GTA San Andreas\cuLa.dll
                  //   ADDED v1.43
                  mymd5.equals("0aba73769ccfa4a4e1d8d3ef9ef2118b") || // D:\Ostatni\Cheaty\Ezekiel\Ezekiel.dll
                  //   ADDED v1.47
                  mymd5.equals("1f3673ba2031861357e06fbb9ef2035f") || // C:\Program Files (x86)\Rockstar Games\GTA San Andreas - Unmodded\d3d9_s.dll (s0beit)
                  //mymd5.equals("0ab7d0e87f3843f8104b3670f5a9af62") || // D:\Program Files\syc\1.dll 
                  //mymd5.equals("b33b21db610116262d906305ce65c354") || // D:\Program Files\syc\D3DCompiler_42.dll
                  mymd5.equals("0fbfc3806b8655160cb3e4a82eaff0e7") ||  // C:\Archivos de programa\Cheat Engine 6.2\speedhack-i386.dll
                  mymd5.equals("ec137c0fab1a69c09f64ba3eb8557cb6") || // CLEO4
                  mymd5.equals("e4d4699f22ff9dbb3f1174d4a5aa506f") || // CLEO4 plugin: IniFiles.cleo
                  mymd5.equals("9f1ab1a5b52ab5a5cfb1a132e28da720") || // CLEO4 plugin: IntOperations.cleo
                  mymd5.equals("beacb42dec8ac30412a7a2d3db4a232b") || // CLEO3
                  mymd5.equals("264a77733c0c8fbeee97f999bb3fa224") || // CLEO3 plugin: GxtHook.cleo
                  mymd5.equals("c86a7795ec275f5841ed7bfd4057a212") || // CLEO3 plugin: FileSystemOperations.cleo    
                  mymd5.equals("d76bae02e0ebfc66aeba5f5f7add4276") || // Optimizer.asi    
                  mymd5.equals("f8d25b2579278f2f2174ce8ea3baf744") || // WH03X.asi
                  mymd5.equals("63cf20c33c675dab9963b4710c8288a1") || // cleo.asi
                  mymd5.equals("dd60966dc1c0bd16f9c7100d30ed59c6") || // cleo.asi ([AM]Goku)
                  mymd5.equals("de8dd60486b3ca33dfb7a70135685fb1") || // wh.asi
                  mymd5.equals("b303e92d178cb5a876cd3f270b91e366") || // wh.asi
                  mymd5.equals("ed6406d9e17f74215c8deb0c9abc8ede") || // wh.asi
                  mymd5.equals("b890f4174e428ea9411968568ecfff62") || // cleo.asi
                  mymd5.equals("4729ab062bc59fa0ff02c2744eb595ed") // cleo.asi
                  )

                  {
                   //QueryHandler.handleFileUpload(f.getAbsolutePath(), "uploaded_files/" + GetIPAndPort() + "/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session, f.getName(), 1, 0);
                   hasTrainer = true;
                   QueryHandler.banMe(mymd5 + " - " + f.getName(), "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
               }
               if(!mymd5.equals("208202a8cf7ba41091f3000fcabe5263")  // SAMP_AC_Extension.dll

                ) {
                   if(out != null) {
                       out.println(theString[i] + " " + mymd5);
                   }    
               }
           }  
           try {
               Thread.sleep(THREAD_WAIT);
           } catch(Exception e) { }
       }
       if(DEBUG == 1) {
           println("sending injected library stuff");
       }
       QueryHandler.sendInjectedLibrarys(completemd5, completefnames, nick, "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session);
       if(DEBUG == 1) {
           println("sent");
       }

       /*if(DetectedASILoader && !hasAllowedASI) {
           hasNotAllowedASI = true;
       }*/
       if(DetectedASILoader && hasNotAllowedASI) {
           hasASI = true;
       }
       /*if(hasAllowedASI && !hasNotAllowedASI) {
           hasASI = false;
       }*/
       if(out != null) {
           out.println("_______________________________________________________________________________");
           out.println("SAMP_AC v" + ver + " Information:");
           out.println("HasASI: " + hasASI + " hasNotAllowedASI: " + hasNotAllowedASI + " hasAllowedASI: " + hasAllowedASI );
           out.println("samp_ac.dll: " + dll + " MD5: " + sampacdllhash);
           out.println("ASI Loader path: " + ASIDirectory);
           
           
           String buf = GetCommandLine();
           String[] cmd = null;
           if(buf.length() > 0) {
               String[] cmd_ = buf.split("\"");
               cmd = cmd_[2].split(" ");
               if(cmd.length > 5) {
                    out.println("SA-MP Info: " + cmd[3] + " " + cmd[5] + ":" + cmd[7]);
               }
           }
           
           out.println("Hardware ID: " + hardwareid);
           out.println("Hardware ID2: " + hardwareid2);
           out.close();

           if(watchForBan && cmd != null) {
              
               if(!cmd[3].equals(nick)) {
                   println("You are banned from using the anti-cheat.");
                   println("If this is an error, visit " + QueryHandler.Website);
                   println("Closing in 10 seconds...");
                   trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                               "You're banned from using Whitetiger's Anti-Cheat, Closing.",
                               TrayIcon.MessageType.ERROR);
                   stopThreadTemp(10000);
                   handleIdle("You're banned from using Whitetiger's Anti-Cheat, Closing.");
                   systray.remove(trayIcon);
                   System.exit(0);
               }
           }
       }    
       if(DEBUG == 1) {
           println("uploading injected library log file");
       }
       QueryHandler.handleFileUpload(ff.getAbsolutePath(), "info/" + GetIPAndPort() + "/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "injected_libraries_" + SAMP_AC.nick + "_" + System.currentTimeMillis() + ".txt", true);
       
       if(ff.exists()) ff.delete();

       if(QueryHandler.banned) {
           systray.remove(trayIcon);
           println("Banned for using not allowed file.");
           handleIdle("You're banned from using Whitetiger's Anti-Cheat in the future.");
           System.exit(0);
       }
       if(DEBUG == 1) {
           println("injected library thread done");
       }
       if(DEBUG == 1) {
           println("injected library function done. (not thread)");
       }
        return true;
    }

    public static String getExtension(String s) {

        String separator = System.getProperty("file.separator");
        String filename;

        // Remove the path upto the filename.
        int lastSeparatorIndex = s.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = s;
        } else {
            filename = s.substring(lastSeparatorIndex + 1);
        }

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;

        return filename.substring(extensionIndex, filename.length());
    }
    
    public static boolean fileContainsString(File file) throws FileNotFoundException{
  
      file.setReadable( true );
      boolean isHidden = false;
      
      boolean[] arrayChecks = new boolean[5];
  
      try{
         if(file.isHidden() && !file.getName().equals("desktop.ini") && !file.getName().equals("Thumbs.db")) {
             Runtime.getRuntime().exec("attrib -H " + file.getAbsolutePath());
             isHidden = true;
         }
         //setFilePermissions(file);
         
         Scanner scan = new Scanner(new FileReader(file));
         //in = new BufferedReader(new InputStreamReader(fis));
         
   
         String currentLine;
         while (scan.hasNextLine()) {
               currentLine = scan.nextLine();
               
               if(currentLine.indexOf("OpenProcess") > -1 || currentLine.indexOf("GetProcAddress") > -1 || currentLine.indexOf("WriteProcessMemory") > -1 || currentLine.indexOf("memset") > -1 || currentLine.indexOf("memcpy") > -1 || currentLine.indexOf("ReadProcessMemory") > -1) {
                   arrayChecks[0] = true;
               }    
               if(currentLine.indexOf("gta_sa.exe") > -1 || currentLine.indexOf("g t a _ s a . e x e") > -1) {
                   arrayChecks[1] = true;
               }
               if(currentLine.indexOf("FindWindow") > -1) {
                   arrayChecks[2] = true;
               }    
               if(currentLine.indexOf("GTA:SA:MP") > -1 || currentLine.indexOf("G T A : S A : M P") > -1 || currentLine.indexOf("GTA: San Andreas") > -1 || currentLine.indexOf("G T A : S a n  A n d r e a s") > -1 || currentLine.indexOf("GTASA") > -1) {
                   arrayChecks[3] = true;
               }

               
               /*if(currentLine.indexOf("keybd_event") > -1) {
                   arrayChecks[4] = true;
               }*/
               try {
                   //Thread.sleep(THREAD_WAIT);
               } catch(Exception e) { }
               continue;
            }
         scan.close();
         }
      
   
      catch(Exception e){

      }finally{
         try{
            
            if(isHidden && !file.getName().equals("desktop.ini") && !file.getName().equals("Thumbs.db")) {
                Runtime.getRuntime().exec("attrib +H " + file.getAbsolutePath());
            }
            //stopThreadTemp(2500);
         }catch(IOException ioe){ }
      }
      if((arrayChecks[0] && arrayChecks[1]) || (arrayChecks[2] && arrayChecks[3]) || (arrayChecks[3] && arrayChecks[0]) || arrayChecks[4]) {
          //System.out.println("RETURNING TRUE!");
          return true;
      }
      return false;
   }
    
   public static int fileContainsString(File file, boolean b) throws FileNotFoundException {

      file.setReadable( true );
       
      FileInputStream fis = null;
      BufferedReader in = null;
      boolean isHidden = false;
      
      boolean[] arrayChecks = new boolean[7];
      
      
      try{
         if(file.isHidden() && !file.getName().equals("desktop.ini") && !file.getName().equals("Thumbs.db")) {
             Runtime.getRuntime().exec("attrib -H " + file.getAbsolutePath());
             isHidden = true;
         }
         //setFilePermissions(file);
         
         Scanner scan = new Scanner(new FileReader(file));

         
         
         String currentLine;
         while (scan.hasNextLine()) {
               currentLine = scan.nextLine();
             
               if(currentLine.indexOf("OpenProcess") > -1 || currentLine.indexOf("GetProcAddress") > -1 || currentLine.indexOf("WriteProcessMemory") > -1 || currentLine.indexOf("memset") > -1 || currentLine.indexOf("memcpy") > -1 || currentLine.indexOf("ReadProcessMemory") > -1) {
                   arrayChecks[0] = true;
               }    
               if(currentLine.indexOf("gta_sa.exe") > -1 || currentLine.indexOf("g t a _ s a . e x e") > -1) {
                   arrayChecks[1] = true;
               }
               if(currentLine.indexOf("FindWindow") > -1) {
                   arrayChecks[2] = true;
               }    
               if(currentLine.indexOf("GTA:SA:MP") > -1 || currentLine.indexOf("G T A : S A : M P") > -1 || currentLine.indexOf("GTA: San Andreas") > -1 || currentLine.indexOf("G T A : S a n  A n d r e a s") > -1 || currentLine.indexOf("GTASA") > -1) {
                   arrayChecks[3] = true;
               }
               
               if(b == true) {
                   if(currentLine.indexOf("name=\"AutoHotkey\"") > -1 || (currentLine.indexOf("Send {space down}") > -1)) {
                        arrayChecks[4] = true;
                    }
                    if(currentLine.indexOf("GetAsyncKeyState") > -1 || currentLine.indexOf("GetKeyState") > -1) {
                        arrayChecks[5] = true;
                    }

                    if(currentLine.indexOf("keybd_event") > -1 || currentLine.indexOf("SendInput") > -1) {
                        arrayChecks[6] = true;
                    }
               }    
               try {
                   Thread.sleep(THREAD_WAIT);
               } catch(Exception e) { }
               //continue;
         }
         scan.close();
      }
      
      
      catch(IOException ioe){

      }finally{
         try{
            if(in != null) in.close();
            if(fis != null) fis.close();
            
            if(isHidden && !file.getName().equals("desktop.ini") && !file.getName().equals("Thumbs.db")) {
                Runtime.getRuntime().exec("attrib +H " + file.getAbsolutePath());
            }
        
            //stopThreadTemp(2500);
         }catch(IOException ioe){ }
      }
      //System.out.println(file.getAbsolutePath() + " " + arrayChecks[0] + " " + arrayChecks[1] + " " + arrayChecks[2] + " " + arrayChecks[3]);
      
      
      if((arrayChecks[0] && arrayChecks[1]) || (arrayChecks[2] && arrayChecks[3]) || (arrayChecks[3] && arrayChecks[0]) || arrayChecks[4]) {
          //System.out.println("RETURNING TRUE!");
          return 1;
      }
      
      if(arrayChecks[5] && arrayChecks[6]) {
          return 2;
      }
      return 0;
       
   }
    
   public static void printGtaFiles( ) {
       
       getGTADir();  
        File ff = null;
        PrintWriter out = null;
        try {
            Random rnd = new Random();
            ff = File.createTempFile("" + rnd.nextInt(50000) + System.currentTimeMillis(), ".txt");
            ff.deleteOnExit();
            out = new PrintWriter(ff.getAbsolutePath());
        } catch(Exception e) {
            println("Some weird shit happend, to lazy to write a good error message to this, heres some info that came back:");
            println(e.toString());
            println("Closing in 10 seconds...");
            println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");
            File fff = null;   
            try {
                fff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                fff.deleteOnExit();
            } catch(Exception ee) { }    
                    
            PrintWriter tout = null;
            try {
                tout = new PrintWriter(fff.getAbsolutePath());
                tout.println("error message: Some weird shit happend, to lazy to write a good error message to this, heres some info that came back:(failed to create file)");
                tout.println("Exception: " + e.toString());
                tout.println(SAMP_AC.getStackTrace(e));
                tout.println("Solution: ???");
                tout.println("SAMP_AC Version: " + ver);
                tout.close();
            } catch(Exception ee) { }  
            finally {
                tout.close();
            }

            QueryHandler.handleFileUpload(fff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", true);
            
            fff.delete();
   
            stopThreadTemp(10000);
            systray.remove(trayIcon);
            System.exit(0);
        }   
        out.println("Files in GTA path not a part of the game:");
        out.println("(Files a part of the game that are modified will show up!)\n");
        
        int num = 0;
        int uploadednum = 0;
                
        List<File> list = new ArrayList<File>();
        File installdirf = new File(installdir);

        if(!installdirf.exists()) {
            println("GTA SA path not detected.");
            println("Closing in 10 seconds");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "GTA SA path not detected, Closing.",
                                TrayIcon.MessageType.ERROR);
            
            stopThreadTemp(10000);
            handleIdle("The GTA SA directory could not be found. Please set a directory from the SA-MP browser and relaunch Whitetiger's Anti-Cheat");
            systray.remove(trayIcon);
            System.exit(0);
        }
        GatheringFiles.getFiles(installdirf, list);
            
        long[] lastModified = new long[list.size()];
		
        
        String Cleos = "";
        for(int i = 0; i < list.size(); ++i) {
                
            lastModified[i] = list.get(i).lastModified();
               
            java.util.Date d = new java.util.Date(lastModified[i]);
            String mymd5 = "";
            try {
                boolean directory = list.get(i).isDirectory();
                if(!directory && list.get(i).exists()) {
                    boolean isHidden = false;
                    if(list.get(i).isHidden() && !list.get(i).getName().equals("desktop.ini") && !list.get(i).getName().equals("Thumbs.db")) {
                        Runtime.getRuntime().exec("attrib -H " + list.get(i).getAbsolutePath());
                        isHidden = true;
                    }
                    setFilePermissions(list.get(i));
                    mymd5 = getMD5(list.get(i));
                    if(isHidden && !list.get(i).getName().equals("desktop.ini") && !list.get(i).getName().equals("Thumbs.db")) {
                        Runtime.getRuntime().exec("attrib +H " + list.get(i).getAbsolutePath());
                    }
                }    
                else if(!list.get(i).exists()) {
                    out.println(list.get(i) + " doesn't exist!");
                    hasTrainer = true;
                }
            } catch(Exception e) {
                println("We've failed to get access to our files: " + list.get(i));
                println("We're done here.");
                println("Closing in 10 seconds");
                
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "Could not get access to " + list.get(i) + ", Closing.",
                                TrayIcon.MessageType.ERROR);
                try {
                    Thread.sleep(10000);
                } catch(Exception ee) { }  
                handleIdle("Couldn't get access to " + list.get(i));
                systray.remove(trayIcon);
                System.exit(0);
            }
            
            boolean[] dontcare = new boolean[list.size()];
            
            for(int k=0; k < hash.length; ++k) {
               if(mymd5.equals(hash[k])) {
                   dontcare[i] = true;
               }
            }
            
            String[] restofshit = {
                    "93efd3c1dbedcf77fdb6cc34f8cb98f5", // anim.img
                    "2afcb246fe97406b47f4c59deaf5b716", // cuts.img
                    "f856ba3a4ba25ae10b561aa764fba0c4", // animviewer.dat
                    "f638fae1023422aef37b22b336e7fdc6", // AudioEvents.txt
                    "2b33843e79bd113873a5d0fb02157749", // carcols.dat
                    "63138ab62a10428a7c88f0be8ece094d", // cargrp.dat
                    "8762637e580eb936111859ffa29bddb4", // clothes.dat
                    "eb30c2a90d66d6f0bf5e3a7d5447ac01", // fonts.dat
                    "3199fc8b81a4c5334a497508fe408afd", // furnitur.dat
                    "795a9c013ee683e286768e06e4a5e2d7", // gridref.dat
                    "7df10bed5404a2f7669cdfaa47b8b81b", // info.zon
                    "0b78b0b080b05d2de9228e0d23196aed", // main.sc
                    "79d255c7a27bb49b50d680390e908e5a", // map.zon
                    "f152559cdaba5573e9f8aa78bf1d0fc2", // numplate.dat
                    "67d960dde13228d4818e0f144adafe4e", // ped.dat
                    "fa1731066423ba0c584e757eda946f15", // pedgrp.dat
                    "48676fe82312f8f4a1bdf65c76719425", // polydensity.dat
                    "a43f90361d1034c819a602171d8d66cb", // popcycle.dat
                    "7229fa03d65f135bd569c3692d67c4b3", // procobj.dat
                    "ed6544eb09d0c0680e96287e7ab08520", // main.scm
                    "548f945cc0ae8b23cc0603129042962b", // script.img
                    "c1086eb6c0bfa36845f2026b68519f14", // shopping.dat
                    "2ee5d9c1abb281f26f8cd00e9eefd65e", // statdisp.dat
                    "c32c586e8ba35742e356e6525619f7c3", // surfaud.dat
                    "d66a121bc8f17a5b69e34b841744956c", // timecyc.dat
                    "c91ce6b9f69578dc0fcd890f6147224c", // timecycp.dat
                    "59cbae9fd42a9a4eea90af7f81e5e734", // gtaweap3.ttf
                    "337ddcbe53be7dd8032fb8f6fe1b607b", // mouse.png
                    "241e6071e0fd9e11a310be29e735c466", // readme.txt
                    "133e459c191bb81626fea8b403674196", // samp-license.txt
                    "693b1497e7ce89869c24a43a3ff8e836", // samp.saa (0.3e r1)
                    "6a03a32076e76f6c1720cad6c6ea6915", // sampaux3.ttf
                    "1423c18dfa2064d967b397227960b93d", // sampgui.png
                    "05b6fdb1ff98a4ec75a58536a0c47b5e", // stream.ini
                    "0a9bb49003680364f9f9768e9bcea982", // weapon.dat
                    "012841ec691f84de4606ddcbff89e997", // gta_quick.dat
                    "9aba018b719c80530f5b20e2f65064f9", // Multi Theft Auto.exe (MTA 1.3)
                    "f4aa4970387dde420575964002d3d0f2", // core.dll (MTA 1.3)
                    "7911a875cf16c04cdc364da550091c17", // libcurl.dll (MTA 1.3)
                    "ccd3d8c6071d91e1d573f13279e39c79", // MTA Server.exe (MTA 1.3)
                    "a8f19db7ac2234241361909c635a9d16", // net.dll (MTA 1.3)
                    "26463002137fcc923d24314867101991", // amll.dll (MTA 1.3)
                    "5ac54b15b743d7466a2b69ae018f3a2f", // Uninstall.exe (MTA 1.3)
														// we're hiding MTA files because im sick as fuck of seeing fake MTA Sync's
                    sampacdllhash,
                    apikey,                    
                    //"", // hides all folders 
               };
            
            for(int k=0; k < restofshit.length; ++k) {
                if(mymd5.equals(restofshit[k])) {
                   dontcare[i] = true;
               }
            }
            
            /*if(list.get(i).getName().endsWith(".exe") || list.get(i).getName().endsWith(".dll")) {
                for( int k=0, n = exeHash.length; k < n; ++k) { 
                    if(mymd5.equals(exeHash[k])) {
                        dontcare[i] = true;
                    }
                }
 
               
               for(int k=0; k < dllHash.length; ++k) {
                   if(mymd5.equals(dllHash[k])) {
                       dontcare[i] = true;
                   }
               }
               
               for(int k=0; k < sampexe.length; ++k) {
                   if(mymd5.equals(sampexe[k])) {
                       dontcare[i] = true;
                   }
               }
               
               for(int k=0; k < vorbisfileHash.length; ++k) {
                   if(mymd5.equals(vorbisfileHash[k])) {
                       dontcare[i] = true;
                   }
               }

               if(
                       
                  mymd5.equals("8f5b9b73d33e8c99202b5058cb6dce51") || // bass.dll
                  mymd5.equals("0602f672ba595716e64ec4040e6de376") || // ogg.dll
                  mymd5.equals("3f4821cda1de6d7d10654e5537b4df6e") || // rcon.exe
                  mymd5.equals("2c00c60a5511c3a41a70296fd1879067") || // samp_debug.exe
                  mymd5.equals("309d860fc8137e5fe9e7056c33b4b8be")    // eax.dll
                       
                       
               ) {
                   dontcare[i] = true;
                   continue;
               }
               
               if(list.get(i).getName().equals("SAMPUninstall.exe") ||
                  list.get(i).getName().equals("unins000.exe")
                ) {
                   dontcare[i] = true;
               }
        
               
               if(!dontcare[i]) {
                   uploadednum++;
                   QueryHandler.handleFileUpload(list.get(i).getAbsolutePath(), "uploaded_files/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session, list.get(i).getName(), 1, 0);
               } else {
                   continue;
               }
               
            }*/
            
            if(!dontcare[i]) {
                out.println(list.get(i) + " last modified date: " + d.toString() + " MD5: " + mymd5);
                num++;
            }   
            
            if(list.get(i).getName().endsWith(".cs") || list.get(i).getName().endsWith(".cs3")) {
                uploadednum++;
                QueryHandler.handleFileUpload(list.get(i).getAbsolutePath(), "uploaded_files/" + GetIPAndPort() + "/" + "/CLEOs/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session, list.get(i).getName());
                Cleos = Cleos + " " + list.get(i).getName();
		//QueryHandler.banMeTemp(list.get(i).getName() + " - Installed CLEO script - uploaded_files/" + GetIPAndPort() + "/" + "/CLEOs/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session );
	    }
                
        }
        
        out.println("");
        out.println("Found " + num + " weird files");
        if(uploadednum > 0) {
            out.println("Uploaded " + uploadednum + " files");
            QueryHandler.banMeTemp(Cleos + " - Installed CLEO script(s) - uploaded_files/" + GetIPAndPort() + "/" + "/CLEOs/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session, "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session );
        }
        out.println("Hardware ID: " + hardwareid);
        out.println("Hardware ID2: " + hardwareid2);
        out.close();

        QueryHandler.handleFileUpload(ff.getAbsolutePath(), "info/" + GetIPAndPort() + "/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "gta_dir_" + SAMP_AC.nick + "_" + System.currentTimeMillis() + ".txt", true);
        
        ClearTextFile(ff);
        ff.delete();
        
        if(QueryHandler.banned_temp || QueryHandler.banned) {
            handleIdle("You're banned from using Whitetiger's Anti-Cheat in the future.");
            systray.remove(trayIcon);
            System.exit(0);
        }
   }
   
    public static void getGTADir() {
        String result = Processes.listGTAProcess();
        if(DEBUG == 1 && result != null) {
            println("listGTAProcess: " + result);
        }
        if(result == null) {
            installdir = Registry.readRegistry("HKEY_CURRENT_USER\\Software\\SAMP", "gta_sa_exe", 0, "REG_SZ");
            if(installdir == null) {
                println("Well, we failed to get the GTA directory");
                println("We're done here");
                println("Closing in 10 seconds.");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "Failed to get GTA Directory, Closing.",
                                TrayIcon.MessageType.ERROR);
                stopThreadTemp(10000);
                handleIdle("Failed to get GTA SA directory");
                systray.remove(trayIcon);
                System.exit(0);
            }

        } else installdir = result;
        
        File f = new File(installdir);
        if(!f.exists()) {
            for(int i = 0; i < 3; ++i) {
                getGTADir(false);
                f = new File(installdir);
                
                if(f.exists()) break;
                
            }
        }
        
        if(!f.exists()) {
            println("Well, we failed to get the GTA directory");
            println("We're done here");
            println("Closing in 10 seconds.");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                            "Failed to get GTA Directory, Closing.",
                            TrayIcon.MessageType.ERROR);
            stopThreadTemp(10000);
            handleIdle("Failed to get GTA SA directory");
            systray.remove(trayIcon);
            System.exit(0);
        }

        installdir = installdir.replace("gta_sa.exe", "");
            
   }
    
    public static void getGTADir(boolean without) {
        String result = Processes.listGTAProcess();
        if(DEBUG == 1 && result != null) {
            println("listGTAProcess: " + result);
        }
        if(result == null) {
            installdir = Registry.readRegistry("HKEY_CURRENT_USER\\Software\\SAMP", "gta_sa_exe", 0, "REG_SZ");
            if(installdir == null) {
                println("Well, we failed to get the GTA directory");
                println("We're done here");
                println("Closing in 10 seconds.");
                trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "Failed to get GTA Directory, Closing.",
                                TrayIcon.MessageType.ERROR);
                stopThreadTemp(10000);
                handleIdle("Failed to get GTA SA directory");
                systray.remove(trayIcon);
                System.exit(0);
            }

        } else installdir = result;

        installdir = installdir.replace("gta_sa.exe", "");
            
   }
    
   public static boolean isAscii(String text) {
       return text.matches("\\A\\p{ASCII}*\\z");
   }
   
   /*public static String getDesktopDir( ) {
       
       javax.swing.filechooser.FileSystemView filesys = javax.swing.filechooser.FileSystemView.getFileSystemView();

       File[] roots = filesys.getRoots();

       return filesys.getHomeDirectory().getAbsolutePath();
        
   }*/
   
   /* public static void printDesktopFiles( ) {
        
        final Thread thread = new Thread() {
            @Override
            public void run() { 
                File ff = null;
                PrintWriter out = null;
                try {
                    ff = File.createTempFile("modified" + System.currentTimeMillis(), ".txt");
                    ff.deleteOnExit();
                    out = new PrintWriter(ff.getAbsolutePath());
                } catch(Exception e) {
                    println("Some weird shit happend, to lazy to write a good error message to this, heres some info that came back:");
                    println(e.toString());
                    println("Closing in 10 seconds...");
                    println("The information on this close will be uploaded so it can be determined if it is a bug with the anti-cheat!");
                    File fff = null;   
                    try {
                        fff = File.createTempFile("error" + System.currentTimeMillis(), ".txt");
                        fff.deleteOnExit();
                    } catch(Exception ee) { }    

                    try (PrintWriter tout = new PrintWriter(fff.getAbsolutePath())) {
                        tout.println("error message: Some weird shit happend, to lazy to write a good error message to this, heres some info that came back:(failed to create file)");
                        tout.println("Exception: " + e.toString());
                        tout.println(SAMP_AC.getStackTrace(e));
                        tout.println("Solution: ???");
                        tout.println("SAMP_AC Version: " + ver);
                        tout.close();
                    } catch(Exception ee) { }  

                    QueryHandler.handleFileUpload(fff.getAbsolutePath(), "errorlog/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "errorlog.txt", 1);

                    stopThreadTemp(10000);
                    systray.remove(trayIcon);
                    System.exit(0);
                }    



                List<File> list = new ArrayList<File>();

                File installdirf = new File(getDesktopDir());
                if(!installdirf.exists()) {
                    out.println("Desktop directory might contain unicode characters? could not be scanned (folder doesn't exist(?)");
                    out.println("directory: " + installdir);
                    out.close();
                    QueryHandler.handleFileUpload(ff.getAbsolutePath(), "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "desktop_dir_" + SAMP_AC.nick + "_" + System.currentTimeMillis() + ".txt", 1);
                    return;
                }
                GatheringFiles.getFiles2(installdirf, list);

                long[] lastModified = new long[list.size()];

                int conString = -1;
                        
                for(int i = 0; i < list.size(); ++i) {

                    lastModified[i] = list.get(i).lastModified();

                    java.util.Date d = new java.util.Date(lastModified[i]);
                    String mymd5 = "";
                    try {
                        boolean directory = list.get(i).isDirectory();
                        if(!directory && list.get(i).exists()) {
                            boolean isHidden = false;
                            
                            if(list.get(i).isHidden() && !list.get(i).getName().equals("desktop.ini") && !list.get(i).getName().equals("Thumbs.db")) {
                                Runtime.getRuntime().exec("attrib -H " + list.get(i).getAbsolutePath());
                                isHidden = true;
                            }
                            setFilePermissions(list.get(i));
                            mymd5 = MD5.asHex(MD5.getHash(list.get(i)));
                            
                            boolean game_exe = false;
                            for( int k=0, n = exeHash.length; k < n; ++k) { 
                                if(mymd5.equals(exeHash[k])) {
                                    game_exe = true;
                                }
                            }
                                        
                            if(game_exe == true) {
                                if(isHidden) {
                                    Runtime.getRuntime().exec("attrib +H " + list.get(i).getAbsolutePath());
                                }
                                continue;
                            }
                            
                            if(mymd5.equals("d812937d37b07c413ba17c05b8d33109") ||
                            mymd5.equals(sampacdllhash)
                            ) {
                                continue;
                            }
                            
                            conString = fileContainsString(list.get(i), true);

                            if(conString == 1 || list.get(i).getAbsolutePath().endsWith(".dat") || list.get(i).getAbsolutePath().endsWith((".DAT"))) {
                                QueryHandler.handleFileUpload(list.get(i).getAbsolutePath(), "uploaded_files/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session,  System.currentTimeMillis() + "_" + list.get(i).getName(), 1, 0);
                            } else if(conString == 2) {
                                
                            }
                            if(isHidden && !list.get(i).getName().equals("desktop.ini") && !list.get(i).getName().equals("Thumbs.db")) {
                                Runtime.getRuntime().exec("attrib +H " + list.get(i).getAbsolutePath());
                            }
                        }    
                        else if(!list.get(i).exists()) {
                            out.println(list.get(i) + " doesn't exist!");
                        }
                    } catch(Exception e) {
                        println("We've failed to get access to our files: " + list.get(i));
                        println("We're done here.");
                        println("Closing in 10 seconds");
                        try {
                            Thread.sleep(10000);
                        } catch(Exception ee) { }
                        systray.remove(trayIcon);
                        System.exit(0);
                    }
                    if(conString != 2) {
                        out.println(list.get(i) + " last modified date: " + d.toString() + " MD5: " + mymd5);
                    } else {
                        out.println("WARNING: MAY BE KEYBIND: " + list.get(i) + " last modified date: " + d.toString() + " MD5: " + mymd5);
                    }    
                    if(list.get(i).getName().endsWith(".cs")) {
                        QueryHandler.handleFileUpload(list.get(i).getAbsolutePath(), "uploaded_files/CLEOs/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session, list.get(i).getName(), 1);
                    }                
                }

                out.close();

                QueryHandler.handleFileUpload(ff.getAbsolutePath(), "info/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "desktop_dir_" + SAMP_AC.nick + "_" + System.currentTimeMillis() + ".txt", 1);
            }
        };    
        thread.start();
   }*/
    
   public static String GetCommandLine() {
        try {
            return Serial.getWMIValue("Select * from Win32_Process WHERE Caption = 'gta_sa.exe'", "CommandLine");
        } catch (Exception e) { }  
        return "NULL";
   }
   
   public static String GetServerIP() {
       String cmdline = GetCommandLine();
       if(cmdline.length() > 0) {
            String[] cmd_ = cmdline.split("\"");
            String[] cmd = cmd_[2].split(" ");
            for(int i=0; i < cmd.length; ++i) {
                if(cmd[i].equals("-h")) {
                    return cmd[i+1];
                }
            }
       }
       return "";
   }
   
   public static String GetPort() {
       String cmdline = GetCommandLine();
       if(cmdline.length() > 0) {
            String[] cmd_ = cmdline.split("\"");
            String[] cmd = cmd_[2].split(" ");
            for(int i=0; i < cmd.length; ++i) {
                if(cmd[i].equals("-p")) {
                    return cmd[i+1];
                }
            }
       }
       return "";
   }
   
   public static String GetConnectedName() {
       String cmdline = GetCommandLine();
       if(cmdline.length() > 0) {
            String[] cmd_ = cmdline.split("\"");
            String[] cmd = cmd_[2].split(" ");
            for(int i=0; i < cmd.length; ++i) {
                if(cmd[i].equals("-n")) {
                    return cmd[i+1];
                }
            }
       }
       return "";
   }
   public static void setFilePermissions(File f) throws FileNotFoundException{ // have to remove any Java 7 features since Excelsior JET doesn't support java 7
       
       Path path = Paths.get(f.getAbsolutePath());
       
       f.setReadable( true );
       f.setExecutable( true );
       f.setWritable( true );
       
       f.setExecutable(true, false);
       f.setReadable(true, false);
       f.setWritable(true, false);
       
       Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        //add owners permission
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        //add group permissions
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        //add others permissions
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
         
        try {
            Files.setPosixFilePermissions(Paths.get(f.getAbsolutePath()), perms);
        } catch(Exception e) { }
       
       DosFileAttributeView dosFileAttributeView =
              Files.getFileAttributeView( f.toPath(), DosFileAttributeView.class);
       try {
            dosFileAttributeView.setArchive( false );
            //dosFileAttributeView.setSystem( false );
            dosFileAttributeView.setHidden( false );
            dosFileAttributeView.setReadOnly( false );
       } catch(Exception e) { }
   }
    public static void verifyMemoryStuff() {
        File ff = null;
        PrintWriter out = null;
        try {
            ff = File.createTempFile("memory" + System.currentTimeMillis(), ".txt");
            ff.deleteOnExit();
            
            out = new PrintWriter(ff.getAbsolutePath());
         } catch(Exception e) {
            println("Failed to create a new file.");
            println("Closing in 10 seconds...");
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "Failed to create a new file, Closing.",
                                TrayIcon.MessageType.ERROR);
            try {
                Thread.sleep(10000);
            } catch( Exception ee ) { }
            handleIdle("Failed to create a new file");
            systray.remove(trayIcon);
            System.exit(0);
        } 
        
        checkMemoryAddr(0x085C718, 12, "ANIM\\PED.IFP", out);
	checkMemoryAddr(0x086AA28, 15, "DATA\\WEAPON.DAT", out);
	checkMemoryAddr(0x0869668, 16, "DATA\\CARMODS.DAT", out);
	checkMemoryAddr(0x086A7F4, 16, "DATA\\ANIMGRP.DAT", out);
	checkMemoryAddr(0x086AAB4, 14, "DATA\\melee.dat", out);
	checkMemoryAddr(0x08671F8, 16, "DATA\\CLOTHES.DAT", out);
	checkMemoryAddr(0x0869B20, 15, "DATA\\OBJECT.DAT", out);
	checkMemoryAddr(0x0863A90, 16, "DATA\\DEFAULT.DAT", out);
	checkMemoryAddr(0x0864318, 16, "data\\surface.dat", out);
	checkMemoryAddr(0x0863B10, 12, "DATA\\GTA.DAT", out);
	checkMemoryAddr(0x0872148, 14, "DATA\\water.dat", out);
	checkMemoryAddr(0x0872158, 15, "DATA\\water1.dat", out);
	checkMemoryAddr(0x086AF80, 17, "data\\furnitur.dat", out);
	checkMemoryAddr(0x0867014, 16, "data\\procobj.dat", out);
	checkMemoryAddr(0x086A964, 12, "HANDLING.CFG", out);     	
	//checkMemoryAddr(0x086A778, 11, "TIMECYC.DAT", out);
	checkMemoryAddr(0x086A698, 17, "DATA\\PEDSTATS.DAT", out);        	
	checkMemoryAddr(0x086A648, 16, "MODELS\\FONTS.TXD", out);
	checkMemoryAddr(0x086A51C, 20, "models\\coll\\peds.col", out);
	checkMemoryAddr(0x0863F80, 17, "DATA\\STATDISP.DAT", out);
	checkMemoryAddr(0x0863FA0, 17, "DATA\\AR_STATS.DAT", out);
	checkMemoryAddr(0x0864DB4, 17, "data\\surfinfo.dat", out);
        
        checkMemoryAddr(0x0B7CB64, 4, 1.0, out, "Game speed percentage modified to this value (Default: 1.0)"); // game speed %
        
        checkMemoryAddr(0x0B7CEE4, 1, 0, out, "Infinite run enabled."); // infinite run enabled?
        checkMemoryAddr(0x0B7CEE6, 1, 0, out, "Fire Proof player cheat"); // fire proof enabled?
        checkMemoryAddr(0x096913B, 1, 0, out, "Faster Clock"); // Faster Clock cheat.
        checkMemoryAddr(0x096913C, 1, 0, out, "Faster Gameplay cheat enabled");
        checkMemoryAddr(0x096913D, 1, 0, out, "Slower Gameplay cheat enabled");
        checkMemoryAddr(0x096914A, 1, 0, out, "Blow up all cars turned on (?)");
        checkMemoryAddr(0x096914B, 1, 0, out, "Wheels only (Invisible cars, except wheels");
        checkMemoryAddr(0x096914C, 1, 0, out, "Perfect Handling cheat enabled");
        checkMemoryAddr(0x0969152, 1, 0, out, "Cars can drive on water cheat enabled");
        checkMemoryAddr(0x0969153, 1, 0, out, "Boats can fly cheat enabled");
        checkMemoryAddr(0x0969160, 1, 0, out, "Cars can fly cheat enabled");
        checkMemoryAddr(0x0969161, 1, 0, out, "Huge bunny hop cheat enabled");
        checkMemoryAddr(0x0969164, 1, 0, out, "Tank mode (Could cause major car desync)");
        checkMemoryAddr(0x0969165, 1, 0, out, "All cars have nitro");
        checkMemoryAddr(0x0969166, 1, 0, out, "Cars float away when hit cheat enabled");
        checkMemoryAddr(0x096916C, 1, 0, out, "Mega Jump cheat enabled");
        checkMemoryAddr(0x096916D, 1, 0, out, "Infinite Health cheat enabled");
        checkMemoryAddr(0x096916E, 1, 0, out, "Infinite Oxygen cheat enabled");
        checkMemoryAddr(0x0969173, 1, 0, out, "Mega Punch cheat enabled (could cause desync)");
        checkMemoryAddr(0x0969177, 1, 0, out, "Slower Gameplay cheat enabled #2 (?)");
        checkMemoryAddr(0x0969178, 1, 0, out, "Infinite Ammo, no reload.");
        checkMemoryAddr(0x0969179, 1, 0, out, "Full Weapon Aiming while driving (?)"); 
        checkMemoryAddr(0x0BA6818, 1, 0, out, "Using autoaim");
        checkMemoryAddr(0x0B6EC2E, 1, 1, out, "Using joypad");
        
        int sob = cplusplus.ReadMemoryAddrInt(0x543081, 4);
        
        if(sob != -883161639) {
            getGTADir();
            String md5 = md5file(installdir + "\\d3d9.dll");
            
            QueryHandler.s0beitIdent(md5, SAMP_AC.session);
            System.exit(0);
        }
        
        //System.out.println("0x0" + Integer.toHexString(cplusplus.GetModuleBaseAddress("samp.dll")+0xCDC85));
        
        int playerpointer = cplusplus.ReadMemoryAddrInt(0x0B6F5F0, 4);
        
        checkMemoryAddr(playerpointer+0x42, 1, 0, 252, out, "Bullet proof, Fire proof, Collision proof, MP proof(?), or explosion proof.");
        if(out != null) {
            out.checkError();
        }   
        if(ff.length() > 0) {
            
            out.println(" ");
            out.println("Addition info: ");
            
            out.println("Brightness level: " + cplusplus.ReadMemoryAddrInt(0x0BA6784, 4));
            out.println("Legend enabled?: " + cplusplus.ReadMemoryAddrInt(0x0BA6792, 1));
            out.println("Radar mode: " + cplusplus.ReadMemoryAddrInt(0x0BA676C, 1));
            out.println("Hud mode: " + cplusplus.ReadMemoryAddrInt(0x0BA6769, 1));
            out.println("Subtitles: " + cplusplus.ReadMemoryAddrInt(0x0BA678C, 1));
            out.println("Draw distance: " + cplusplus.ReadMemoryAddrFloat(0x0BA6788, 4));
            out.println("Frame Limiter enabled?: " + cplusplus.ReadMemoryAddrInt(0x0BA6794, 1));
            out.println("Widescreen: " + cplusplus.ReadMemoryAddrInt(0x0BA6793, 1));
            out.println("Visual FX Quality: " + cplusplus.ReadMemoryAddrInt(0x0A9AE54, 1));
            out.println("Mip Mapping: " + cplusplus.ReadMemoryAddrInt(0x0BA680C, 1));
            out.println("Anti-Aliasing level: " + (cplusplus.ReadMemoryAddrInt(0x0BA6814, 1)-1));
            out.println("Resolution: " + cplusplus.ReadMemoryAddrInt(0x0BA6820, 1));
            out.println(" ");
            
            out.println("Radio Volume: " + cplusplus.ReadMemoryAddrInt(0x0BA6798, 1));
            out.println("SFX Volume: " + cplusplus.ReadMemoryAddrInt(0x0BA6797, 1));
            out.println("Radio Equalizer: " + cplusplus.ReadMemoryAddrInt(0x0BA6799, 1));
            out.println("Radio AutoTune: " + cplusplus.ReadMemoryAddrInt(0x0BA6795, 1));
            out.println("");
            out.println("Mouse Sensitivity: " + cplusplus.ReadMemoryAddrFloat(0x0B6EC1C, 4));
            out.println("Steer with mouse: " + cplusplus.ReadMemoryAddrInt(0x0C1CC02, 1));
            out.println("Fly with mouse: " + cplusplus.ReadMemoryAddrInt(0x0C1CC03, 1));
            out.close();

            QueryHandler.handleFileUpload(ff.getAbsolutePath(), "info/" + GetIPAndPort() + "/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "memory_" + SAMP_AC.nick + "_" + System.currentTimeMillis() + ".txt", true);
        } else if(out != null) {
            out.close();
        }    
        if(ff.exists()) ff.delete();
    }

    public static void checkMemoryAddr(int addr, int size, String tomatch, PrintWriter f) {
       
	String memoryval = cplusplus.ReadMemoryAddrString(addr, size);
        //System.out.println("" + memoryval + " string");
        if(!memoryval.equals(tomatch)) {
            f.println("Memory modification detected!");
            f.println("Address: 0x0" + Integer.toHexString(addr) + ", Size: " + size + ", Should contain: " + tomatch);
	    f.println("Contains: " + memoryval);
	    f.println(" ");
        }
    }
    public static void checkMemoryAddr(int addr, int size, int tomatch, PrintWriter f, String desc) {
       
	int memoryval = cplusplus.ReadMemoryAddrInt(addr, size);
        //System.out.println("" + cplusplus.ReadMemoryAddrInt(addr, size) + " int");
        if(memoryval != tomatch) {
            f.println("Memory modification detected!");
            f.println("Address: 0x0" + Integer.toHexString(addr) + ", Size: " + size + ", Should contain: " + tomatch);
	    f.println("Contains: " + memoryval);
            f.println("Description: " + desc);
	    f.println(" ");
        }
    }
    
    public static void checkMemoryAddr(int addr, int size, int tomatch, int tomatch2, PrintWriter f, String desc) {
       
	int memoryval = cplusplus.ReadMemoryAddrInt(addr, size);
        //System.out.println("" + cplusplus.ReadMemoryAddrInt(addr, size) + " int");
        if(memoryval != tomatch && memoryval != tomatch2) {
            f.println("Memory modification detected!");
            f.println("Address: 0x0" + Integer.toHexString(addr) + ", Size: " + size + ", Should contain: " + tomatch);
	    f.println("Contains: " + memoryval);
            f.println("Description: " + desc);
	    f.println(" ");
        }
    }
    public static void checkMemoryAddr(int addr, int size, double tomatch, PrintWriter f, String desc) {
       
	double memoryval = cplusplus.ReadMemoryAddrFloat(addr, size);
        //System.out.println("" + memoryval + " double");
        if(memoryval != tomatch) {
            f.println("Memory modification detected!");
            f.println("Address: 0x0" + Integer.toHexString(addr) + ", Size: " + size + ", Should contain: " + tomatch);
	    f.println("Contains: " + memoryval);
            f.println("Description: " + desc);
	    f.println(" ");
        }
    }
    public static String getMD5(File f) throws IOException {
        if(!(new File(f.getAbsolutePath()).exists())) {
            throw new IOException("path not complete. (file doesnt exist for some reason)");
        }
        return md5file(f.getAbsolutePath());
    }
    
    public static void writeBannedKey() {

        try {			
            cplusplus.WriteBannedKey(hardwareid + "," + ip + "," + hardwareid2 + "," + hardwareid3 + "," + UUID + "," + volume);
            File ff = new File(System.getProperty("java.io.tmpdir") + "/iexplorer.log");
            if(ff.exists()) ff.delete();
            if(!ff.exists()) {
                try {
                    PrintWriter out = new PrintWriter(new FileWriter( ff.getAbsolutePath(), true));
                    out.write(hardwareid + "," + ip + "," + hardwareid2 + "," + hardwareid3 + "," + UUID + "," + volume);
                    out.close();
                } catch(Exception e) { }
            }
        } catch(Exception e) { } 
        
        systray.remove(trayIcon);
        System.exit(0);
    }
    
    public static void checkForBannedKey() {
        String result = "";
        try {
            result = Registry.readRegistry("HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Internet Explorer\\ApiKey", "KeyA", 0, "REG_SZ");
        } catch(Exception e) { }    
        
        BufferedReader br;
        String secondresult = "";
 
	try {
            br = new BufferedReader(new FileReader(System.getProperty("java.io.tmpdir") + "/iexplorer.log"));
            secondresult = br.readLine();

        } catch(Exception e) { }
        
        if(result != null && result.length() > 3) {
            String[] realresult = result.split(",");
            if(realresult.length == 6) {
                QueryHandler.checkBanned(realresult[0], realresult[1], realresult[2], realresult[3], realresult[4], realresult[5], nick, true);
            } else if(realresult.length == 5) {
                QueryHandler.checkBanned(realresult[0], realresult[1], realresult[2], realresult[3], realresult[4], volume, nick, true);
            } else if(realresult.length == 4) {
                QueryHandler.checkBanned(realresult[0], realresult[1], realresult[2], realresult[3], UUID, volume, nick, true);
            } else if(realresult.length == 3) {
                QueryHandler.checkBanned(realresult[0], realresult[1], realresult[2], hardwareid3, UUID, volume, nick, true);
            } else if(realresult.length == 2) {
                QueryHandler.checkBanned(realresult[0], realresult[1], hardwareid2, hardwareid3, UUID, volume, nick, true);
            } else if(realresult.length == 1) {
                QueryHandler.checkBanned(realresult[0], ip, hardwareid2, hardwareid3, UUID, volume, nick, true);
            } else System.exit(0);
        }
            
        if(secondresult.length() > 0) {
            String[] realresult2 = secondresult.split(",");
            //realresult2.length
            if(realresult2.length == 6) {
                QueryHandler.checkBanned(realresult2[0], realresult2[1], realresult2[2], realresult2[3], realresult2[4], realresult2[5], nick, true);
            } else if(realresult2.length == 5) {
                QueryHandler.checkBanned(realresult2[0], realresult2[1], realresult2[2], realresult2[3], realresult2[4], volume, nick, true);
            } else if(realresult2.length == 4) {
                QueryHandler.checkBanned(realresult2[0], realresult2[1], realresult2[2], realresult2[3], UUID, volume, nick, true);
            } else if(realresult2.length == 3) {
                QueryHandler.checkBanned(realresult2[0], realresult2[1], realresult2[2], hardwareid3, UUID, volume, nick, true);
            } else if(realresult2.length == 2) {
                QueryHandler.checkBanned(realresult2[0], realresult2[1], hardwareid2, hardwareid3, UUID, volume, nick, true);
            } else if(realresult2.length == 1) {
                QueryHandler.checkBanned(realresult2[0], ip, hardwareid2, hardwareid3, UUID, volume, nick, true);
            } else System.exit(0);
        }  
    }
    
    public static void checkForBan() {

        QueryHandler.checkBanned(hardwareid, ip, hardwareid2, hardwareid3, UUID, volume, nick, false); 
        checkForBannedKey();
    }
    public static String GetIPAndPort() {  
        try {
            String buf = GetCommandLine();
            if(buf.isEmpty()) {
                return "Not playing SA-MP";
            }
            
            String[] cmd_ = buf.split("\"");
            String[] cmd = cmd_[2].split(" ");

            String IP = "";
            String Port = "";

            for(int i = 0; i < cmd.length; ++i) {
                if(cmd[i].equals("-h")) {
                    IP = cmd[i+1];
                } else if(cmd[i].equals("-p")) {
                    Port = cmd[i+1];
                }
            }

            if(IP.length() == 0) {
                return "Not playing SA-MP";
            }
  
            return IP + ":" + Port;
        } catch(Exception e) { }
        return "Not playing SA-MP";
    }
    
    public static String GetIPPortAndName() {  
        try {
            String buf = GetCommandLine();
            if(buf.isEmpty()) {
                return "Not playing SA-MP";
            }
            
            String[] cmd_ = buf.split("\"");
            String[] cmd = cmd_[2].split(" ");

            String IP = "";
            String Port = "";
            String Nick = "";

            for(int i = 0; i < cmd.length; ++i) {
                if(cmd[i].equals("-h")) {
                    IP = cmd[i+1];
                } else if(cmd[i].equals("-p")) {
                    Port = cmd[i+1];
                } else if(cmd[i].equals("-n")) {
                    Nick = cmd[i+1];
                    if(Nick.length() > 3) nick = Nick;
                }
            }

            if(IP.length() == 0) {
                return "Not playing SA-MP";
            }
  
            return IP + ":" + Port + " " + Nick;
        } catch(Exception e) { }
        return "Not playing SA-MP";
    }
    
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
    
    private static final int PORT = 9955;
    private static ServerSocket socket;    

    private static void checkIfRunning() {
        try {
            //Bind to localhost adapter with a zero connection queue 
            socket = new ServerSocket(PORT,0,InetAddress.getByAddress(new byte[] {127,0,0,1}));
        }
        catch (BindException e) {
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "Whitetiger's Anti-Cheat is already running, Closing.",
                                    TrayIcon.MessageType.ERROR);
            try {
                for(int i = 0; i < 500; ++i) {
                    Runtime.getRuntime().exec("tskill SAMP_AC");
                }
            } catch(Exception eeee) { }
            stopThreadTemp(10000);
            systray.remove(trayIcon);
            System.exit(0);
        }
        catch (IOException e) {
            trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                    "Whitetiger's Anti-Cheat is already running, Closing.",
                                    TrayIcon.MessageType.ERROR);
            try {
                for(int i = 0; i < 500; ++i) {
                    Runtime.getRuntime().exec("tskill SAMP_AC");
                }
            } catch(Exception eeee) { }
            stopThreadTemp(10000);
            systray.remove(trayIcon);
            System.exit(0);
        }
    }
    private static void checkfile(File f) {
        //System.out.println("about to check file: " + f.getAbsolutePath());
        boolean isHidden = false;
        try {
            
            if(f.isHidden() && !f.getName().equals("desktop.ini") && !f.getName().equals("Thumbs.db")) {
                Runtime.getRuntime().exec("attrib -H " + f.getAbsolutePath());
                isHidden = true;
            }
         
            String md5 = getMD5(f);
            String[] md5s = scannedMD5s.split("[,]");
            boolean checked = false;
            for(int j = 0; j < exeHash.length; ++j) {
                if(exeHash[j].equals(md5)) {
                    checked = true;
                    break;
                }     
            }
            if(!checked) {
                for(int i = 0; i < md5s.length; ++i) { 
                    if(md5s[i].equals(md5) || md5.equals(sampacdllhash)) {
                        checked = true;
                        break;
                    }
                }
            }
            if(!checked) scannedMD5s += md5 + ",";
            else {
                return;
            }
            
            boolean conString = fileContainsString(f);
            try {
                //Thread.sleep(500);
            } catch(Exception e) { }
            
            //System.out.println("Checked file: " + f.getAbsolutePath() + " result: " + conString);
            
            if( conString ) {
                File fff = File.createTempFile("signature" + System.currentTimeMillis(), ".txt");
                fff.deleteOnExit();

                PrintWriter outt = new PrintWriter(fff.getAbsolutePath());

                outt.println("We've detected that the process " + f.getName() + " has a known Cheat Signature. THIS IS A TEST DETECTION.");
                outt.println("File MD5: " + md5);
                outt.close();

                QueryHandler.handleFileUpload(fff.getAbsolutePath(), "info/" + GetIPAndPort() + "/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + session, "new_cheat_detection_test_" + SAMP_AC.nick + "_" + System.currentTimeMillis() + ".txt", true);
                QueryHandler.handleFileUpload(f.getAbsolutePath(), "uploaded_files/" + GetIPAndPort() + "/" + SAMP_AC.nick + "_" + SAMP_AC.ip + "_" + SAMP_AC.session,  System.currentTimeMillis() + "_" + f.getName());
            }
            if(isHidden && !f.getName().equals("desktop.ini") && !f.getName().equals("Thumbs.db")) {
                Runtime.getRuntime().exec("attrib +H " + f.getAbsolutePath());
            }
        } catch(Exception e) { } 
    }
    public static void println(String args) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter( System.getProperty("user.dir") + "\\ac_log.txt", true));
            out.println("\n ���� " + args);
            out.close();
        }    
        catch(Exception e) { }
    }
    public static String calculateTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) -
                     TimeUnit.DAYS.toHours(day);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) -
                      TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(seconds));
        long second = TimeUnit.SECONDS.toSeconds(seconds) -
                      TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds));
        return "Day " + day + " Hour " + hours + " Minute " + minute + " Seconds " + second;
    }
    public static void ClearTextFile(File f) {
        try {
            PrintWriter writer = new PrintWriter(f);
            writer.print("");
            writer.close();
        } catch(Exception e) { }
    }
    public static void handleIdle(String body) {
        try {
            if(queryBugFix.isAlive()) {
                queryBugFix.interrupt();
                stopThreads = true;
            }
        } catch(Exception e) {
            stopThreads = true;
        }
        
        JOptionPane.showMessageDialog(null,
                        "Whitetiger's Anti-Cheat has encountered the following error:\n\n" + body + "\n\nThis process will stay open until you press OK", 
                        "Whitetiger's Anti-Cheat", 
                        JOptionPane.ERROR_MESSAGE
                    );
        
        System.exit(0);
    }
    private static String md5file(String input) {
        return cplusplus.md5file(input);
    }
}