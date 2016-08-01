/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.TrayIcon;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;

/**
 *
 * @author My Account
 */
public class Download {
    static boolean[] remember = new boolean[SAMP_AC.arr.length+3];
    
    public static void downloadUpdate(String link, double ver) throws Exception
    {
        
        SAMP_AC.trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "Downloading update...",
                                TrayIcon.MessageType.INFO);
        
        SAMP_AC.loader.dispose();
        
        TransferComplete(ver, downloadFile(link));
            
    }
    public static String downloadFile(String link) {
        String mydir = System.getProperty("java.io.tmpdir") + "\\";
        String f = "";
        try {

            java.io.BufferedInputStream in = new java.io.BufferedInputStream(new 

            java.net.URL(link).openStream());

            java.io.FileOutputStream fos = new java.io.FileOutputStream(mydir + "update.zip");
            java.io.BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
            byte[] data = new byte[1024];
            int x=0;
            while((x=in.read(data,0,1024))>=0)
            {
                bout.write(data,0,x);
            }
            bout.close();
            in.close();

            f = doUnzip(mydir + "update.zip", mydir);
        } catch(Exception e) { e.printStackTrace(); }
                
        new File(mydir + "update.zip").delete();
           
        return mydir + f;
    }
    public static void TransferComplete(double ver, String abspath) {
  
        if(SAMP_AC.DEBUG == 1) {
            SAMP_AC.println("TransferComplete");
        }
        SAMP_AC.println("\"" + abspath + "\"");
        SAMP_AC.println(" We've updated to the new version, This program will close in 10 seconds.");
        SAMP_AC.println("Access the new version at ");
        SAMP_AC.println(System.getProperty("user.dir"));
        
        SAMP_AC.trayIcon.displayMessage("Whitetiger's Anti-Cheat", 
                                "Update completed, Launching installer...",
                                TrayIcon.MessageType.INFO);
        
        try {
            ProcessBuilder builder = new ProcessBuilder(
                new String[] {"cmd.exe", "/C",abspath});
            Process newProcess = builder.start();

        } catch(Exception e) { e.printStackTrace(); } 
        
        try {
            Thread.sleep(10000);
        } catch(InterruptedException e) { }    
        System.exit(0);
    }
    public static final void copyInputStream(InputStream in, OutputStream out)
        throws IOException
    {
        byte[] buffer = new byte[1024];
        int len;
        while((len = in.read(buffer)) >= 0)
        out.write(buffer, 0, len);
        in.close();
        out.close();
    }
    
    public static void copyFile(String orig, String dest) throws IOException {
        InputStream in = new FileInputStream(orig);
        OutputStream out = new FileOutputStream(dest);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
           out.write(buf, 0, len);
        }
        in.close();
        out.close(); 
    }
    
    public static boolean requestFileDownload(String filename, String gtadir, int i) {
        boolean returnval = false;
        String newfilename = filename.replace('\\', '/');
        
        if(!remember[i]) {
            remember[i] = true;
            while(true) {
                int result = JOptionPane.showConfirmDialog(null,
                    filename + " is modified, download and replace with the default one?", 
                    "Download unmodifed file?", 
                    JOptionPane.YES_NO_OPTION
                );

                if(result == JOptionPane.YES_OPTION) {
                     String path = QueryHandler.Website + "tiger/ac_files/unmodded_files/zipped/" + newfilename + ".zip";
                     String origfile = downloadFile(path); //+ newfilename.substring(newfilename.lastIndexOf('/') == -1 ? 0 : newfilename.lastIndexOf('/'));
                     try {
                        SAMP_AC.setFilePermissions(new File(origfile));
                        SAMP_AC.setFilePermissions(new File(gtadir + filename));
                     } catch(Exception e) { e.printStackTrace();}
                     try {
                         copyFile(origfile, gtadir + filename);
                     } catch(Exception e) { e.printStackTrace(); cplusplus.MoveFile(origfile, gtadir + filename); }

                    break;
                } else if(result == JOptionPane.NO_OPTION) {

                    break;
                }
            }
        }
        
        return returnval;
    }
    
    public static boolean AsiPerformUpdate() {
        String path = QueryHandler.Website + "tiger/ac_files/unmodded_files/zipped/ac.asi.zip";
        String origfile = downloadFile(path) + "ac.asi";
        path = QueryHandler.Website + "tiger/ac_files/unmodded_files/zipped/d3d9.dll.zip";
        String d3d9 = downloadFile(path) + "d3d9.dll";
        try {
            SAMP_AC.setFilePermissions(new File(origfile));
            SAMP_AC.setFilePermissions(new File(d3d9));
            
            SAMP_AC.setFilePermissions(new File(SAMP_AC.installdir + "ac.asi"));
            SAMP_AC.setFilePermissions(new File(SAMP_AC.installdir + "d3d9.dll"));
        } catch(Exception e) { }
        try {
            copyFile(origfile, SAMP_AC.installdir + "ac.asi");
            copyFile(d3d9, SAMP_AC.installdir + "d3d9.dll");
            return true;
        } catch(IOException e) {
            cplusplus.MoveFile(origfile, SAMP_AC.installdir + "ac.asi");
            cplusplus.MoveFile(d3d9, SAMP_AC.installdir + "d3d9.dll");
            // movefileex
        }
    
        return false;
    }
    public static String doUnzip(String inputZip, String destinationDirectory)
        throws IOException {
        String returnval = "";
        int BUFFER = 2048;
        List<String> zipFiles = new ArrayList<String>();
        File sourceZipFile = new File(inputZip);
        File unzipDestinationDirectory = new File(destinationDirectory);
        unzipDestinationDirectory.mkdir();

        ZipFile zipFile;
        // Open Zip file for reading
        zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);

        // Create an enumeration of the entries in the zip file
        Enumeration zipFileEntries = zipFile.entries();

        // Process each entry
        while (zipFileEntries.hasMoreElements()) {
            // grab a zip file entry
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

            String currentEntry = entry.getName();
            
            if(currentEntry.endsWith(".exe")) {
                returnval = currentEntry;
            }

            File destFile = new File(unzipDestinationDirectory, currentEntry);
            //destFile = new File(unzipDestinationDirectory, destFile.getName());

            if (currentEntry.endsWith(".zip")) {
                zipFiles.add(destFile.getAbsolutePath());
            }

            // grab file's parent directory structure
            File destinationParent = destFile.getParentFile();

            // create the parent directory structure if needed
            destinationParent.mkdirs();

            try {
                // extract file if not a directory
                if (!entry.isDirectory()) {
                    BufferedInputStream is =
                            new BufferedInputStream(zipFile.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest =
                            new BufferedOutputStream(fos, BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            } catch (IOException ioe) {

            }
        }
        zipFile.close();

        for (Iterator iter = zipFiles.iterator(); iter.hasNext();) {
            String zipName = (String)iter.next();
            doUnzip(
                zipName,
                destinationDirectory +
                    File.separatorChar +
                    zipName.substring(0,zipName.lastIndexOf(".zip"))
            );
        }
        return returnval;
    }
}
