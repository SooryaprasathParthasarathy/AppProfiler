package com.ibc.android.demo.appslist.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibc.android.demo.appslist.activity.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class NetworkUsage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_usage);
        try {
            Networkanalyser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_network_usage, menu);
        return true;

}
    public void Networkanalyser() throws IOException {
        Intent intent;
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        String text= bundle.getString("SelectedValue");
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> l = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        String canonicalName ;
        for (ApplicationInfo ai : l){
            String n = (String)pm.getApplicationLabel(ai);
            if (n.equals(text) || text.equals(n)){
                canonicalName = ai.packageName;
                Networktraffic nt = new Networktraffic();
                String datareceived =   nt.Networktraffic(this, getPackageManager(), canonicalName);
                String datasent = nt.Networktrafficsent(this, getPackageManager(), canonicalName);

                if(!(datareceived.equals("Unsupported"))) {
                    TextView t = (TextView) findViewById(R.id.textView4);
                    float datareceivedMb = Float.parseFloat(datareceived) /(1024*1024);
                    t.setText(datareceivedMb+" "+"Mb");
                }
                if(!(datasent.equals("Unsupported"))) {
                    TextView t = (TextView) findViewById(R.id.textView3);
                    float datasentMb = Float.parseFloat(datasent)/(1024*1024);
                    t.setText(datasentMb+" "+"Mb");
                }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("Not supported");
                    alert.show();
                }
               WifiManager wifiManager=(WifiManager)getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo=wifiManager.getConnectionInfo();
                TextView ip = (TextView) findViewById(R.id.textView6);
                String addr = null;
              getStats();
            }
        }
    }
    public void getStats()  {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("traceroute yahoo.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int i;
        char[] buffer = new char[4096];
        StringBuffer output = new StringBuffer();
        try {
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("*************", "" + output);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
