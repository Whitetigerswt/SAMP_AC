
import com.twmacinta.util.MD5;
import java.io.File;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author My Account
 */

public class cplusplus {

     public static native int b(int mem, int size); // ReadMemoryAddrInt - reads dwords and bytes
     public static native String c(int mem, int size); // ReadMemoryAddrDWORD
     public static native double d(int mem, int size); // ReadMemoryAddrFloat
     public static native String e(int mem, int size); // ReadMemoryAddrString
     public static native int h(String name); // GetModuleBaseAddress
     /*public static native String WriteMemoryAddr(String windowName, int mem, String val, int size);
     public static native String WriteMemoryAddrInt(String windowName, int mem, int val, int size);
     public static native String WriteMemoryAddrFloat(String windowName, int mem, String val, int size);*/
     public static native String x(String windowName); //GetInjectedLibrarys
     //public static native String GetPlayerConnectedIP(String windowName);
     public static native String f(); // GetActiveWindowTitle
     public static native String i(); // GetInjectedLibrarysAll
     public static native String j(); // GetRunningProcesses
     public static native String g(); // GetPCInfo
     public static native String a(); // GetOurFileName
     public static native void l(String value); // WriteBannedKey
     public static native boolean k(); // IsVM
     public static native String m(); // GetVolumeSerial (resets if formatted drive) 
     public static native void n(); // SetProcessPriority
     public static native int o(String in, String out); // MoveFileEx func
     public static native String p(String main, String key, int type);
     public static native String q(String input); // md5file
     //public static native String listRunningProcesses();
     //public static native String GetFPSLimit();

     static {
        System.setProperty("jsse.enableSNIExtension", "false");
        String path  = new File ("").getAbsolutePath() + "\\" + SAMP_AC.dll + ".dll";
        
        
        //path = new File ("").getAbsolutePath() + "\\compatibility.dll";
        
        File f = new File( path );

        if( f.exists( ) && f.canRead( ) ) {
            try {
                //System.setProperty("com.twmacinta.util.MD5.NATIVE_LIB_FILE", "samp_ac_x86");
                //MD5.initNativeLibrary(false);
                
                SAMP_AC.sampacdllhash = MD5.asHex(MD5.getHash(f));
                //System.out.println("samp_ac_x86.dll md5: " + SAMP_AC.sampacdllhash);
                if(!SAMP_AC.sampacdllhash.equals("372a5bc1d31340a4b864b7696bbf6fb1")) {
                    SAMP_AC.println("samp_ac_x86.dll failed verification test.");
                    SAMP_AC.stopThreadTemp(10000);
                    System.exit(0);
                }
            } catch(Exception e) { 
                SAMP_AC.println("Failed to check dll.");
                SAMP_AC.stopThreadTemp(10000);
                System.exit(0);
            }    
        } else {
            SAMP_AC.println("Failed to check samp_ac.dll.");
            SAMP_AC.stopThreadTemp(10000);
            System.exit(0);
        }
         try {
            System.loadLibrary(SAMP_AC.dll);
         } catch (UnsatisfiedLinkError e) {
            SAMP_AC.println("Failed to load .DLL: " + e.toString());
            try {
                Thread.sleep(10000);
                System.exit(0);
            }
            catch( InterruptedException ee) {
                System.exit(0);
            }  
        }

     }
     public static String GetInjectedProgs(String windowName) {
         String res = x(windowName);
         if(QueryHandler.DEBUG_PHP == true) System.out.println("INJECTED: " + res);
         return res;
     }
     public static String GetOurFileName() {
         return a();
     }
     public static int ReadMemoryAddrInt(int mem, int size) {
         return b(mem, size);
     }
     public static String ReadMemoryAddrDWORD(int mem, int size) {
         return c(mem, size);
     }
     public static double ReadMemoryAddrFloat(int mem, int size) {
         return d(mem, size);
     }
     public static String ReadMemoryAddrString(int mem, int size) {
         return e(mem, size);
     }
     public static String GetActiveWindowTitle() {
         return f();
     }
     public static String GetPCInfo() {
         return g();
     }
     public static int GetModuleBaseAddress(String mod) {
         return h(mod);
     }
     public static String GetInjectedLibrarysAll() {
         return i();
     }
     public static String GetRunningProcesses() {
         return j();
     }
     public static boolean IsVM() {
         return k();
     }
     public static void WriteBannedKey(String key) {
         l(key);
     }
     public static String GetVolumeSerial() {
         return m();
     }
     public static void SetProcessPriority() {
         n();
     }
     public static int MoveFile(String in, String out) {
         return o(in, out);
     }
     public static String ReadRegistry(String main, String key, int type) {
         return p(main, key, type);
     }
     public static String md5file(String input) {
         return q(input);
     }
}