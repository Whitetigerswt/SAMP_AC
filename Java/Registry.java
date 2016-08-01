
import java.io.IOException;
import java.io.InputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author My Account
 */
public class Registry {
    public static final String readRegistry(String location, String key, int type2, String type)
    {
        int idx = location.indexOf("\\");
        String result = cplusplus.ReadRegistry(location.substring(idx + 1), key, type2);
        if(result.length() > 0) {
            return result;
        }
        
        try
        {
            // Run reg query, then read output with StreamReader (internal class)
            Process process = Runtime.getRuntime().exec("reg query " + 
                    '"'+ location + "\" /v " + key);

            InputStream is = process.getInputStream();
            StringBuilder sw = new StringBuilder();

            try
            {
                int c;
                while ((c = is.read()) != -1)
                    sw.append((char)c);
            }
            catch (IOException e)
            { 
            }

            String output = sw.toString();
            

            int i = output.indexOf(type);
            if (i == -1)
            {
                return null;
            }

            sw = new StringBuilder();
            i += 6; // skip REG_SZ

            // skip spaces or tabs
            for (;;)
            {
                if (i > output.length())
                    break;
                char c = output.charAt(i);
                if (c != ' ' && c != '\t')
                    break;
                ++i;
            }

            // take everything until end of line
            for (;;)
            {
                if (i > output.length())
                    break;
                char c = output.charAt(i);
                if (c == '\r' || c == '\n')
                    break;
                sw.append(c);
                ++i;
            }

            return sw.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public static final String readRegistry(String location, String key, String type)
    {
        
        try
        {
            // Run reg query, then read output with StreamReader (internal class)
            Process process = Runtime.getRuntime().exec("reg query " + 
                    '"'+ location + "\" /v " + key);

            InputStream is = process.getInputStream();
            StringBuilder sw = new StringBuilder();

            try
            {
                int c;
                while ((c = is.read()) != -1)
                    sw.append((char)c);
            }
            catch (IOException e)
            { 
            }

            String output = sw.toString();
            

            int i = output.indexOf(type);
            if (i == -1)
            {
                return null;
            }

            sw = new StringBuilder();
            i += 6; // skip REG_SZ

            // skip spaces or tabs
            for (;;)
            {
                if (i > output.length())
                    break;
                char c = output.charAt(i);
                if (c != ' ' && c != '\t')
                    break;
                ++i;
            }

            // take everything until end of line
            for (;;)
            {
                if (i > output.length())
                    break;
                char c = output.charAt(i);
                if (c == '\r' || c == '\n')
                    break;
                sw.append(c);
                ++i;
            }

            return sw.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
