package appprofiler.appprofilerv1;

import android.os.AsyncTask;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetTask extends AsyncTask<String, Integer, String>
{
    String addr;
    @Override
    protected String doInBackground(String... params)
    {

        try
        {
            addr ="31.13.71.1";
         //   addr = InetAddress.getByName(params[0]);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return addr;
    }
}
