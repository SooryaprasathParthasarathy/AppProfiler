package appprofiler.appprofilerv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DataSniffer extends AppCompatActivity {
    public static String IPAddress;
    public static String    Latitude;
    public static String    Longitude;
    public static String    country_code;
    public static String    Country;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sniffer);

        try {
            Networkanalyser();

            Threadcheck t1 = new Threadcheck();
            Thread t = new Thread(t1);
            t.start();
                    (new Thread(new Threadcheck())).start();

        } catch (IOException e) {
            e.printStackTrace();

        }
        //TextView lat = (TextView) findViewById(R.id.tvLat);
        //TextView lon = (TextView) findViewById(R.id.tvLng);
        //TextView cit = (TextView) findViewById(R.id.tvCity);
        //TextView cou = (TextView) findViewById(R.id.tvCntry);
        try {
            //lat.setText(Latitude.toString());
           // lon.setText(Longitude.toString());
           // cit.setText(country_code.toString());
           // cou.setText(Country.toString());
            System.out.println(Latitude);
            //setContentView(R.layout.data_sniffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Threadcheck  implements Runnable{

        //  TextView temp = (TextView) findViewById(R.id.textView);
        @Override
        public void run() {

            URL url;
            try {
                String a = "https://freegeoip.net/json/"+IPAddress;
                url = new URL(a);


                URLConnection conn = url.openConnection();

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    System.out.println(inputLine);
                    JSONObject js = new JSONObject(inputLine);
                     Latitude = js.getString("latitude");
                    System.out.println(Latitude);
                     Longitude = js.getString("longitude");
                    System.out.println(Longitude);
                    Country = js.getString("country_name");
                    country_code=js.getString("country_code");

                    //  Intent i = new Intent(this,MapsActivity.class);
                }
                br.close();
                //      temp.setText(inputLine);
                System.out.println("Done");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_sniffer, menu);
        return true;
    }

    public void Networkanalyser() throws IOException {
        Intent NA_intent;
        NA_intent = getIntent();
        Bundle bundle = NA_intent.getExtras();
        String text = bundle.getString("SelectedValue");
        TextView Appname = (TextView) findViewById(R.id.AppName);
        Appname.setText(text.toString().toUpperCase());
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> lstAppInfo = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        String canonicalName;
        try {
            for (ApplicationInfo ai : lstAppInfo) {
                String n = (String) pm.getApplicationLabel(ai);
                if (n.equals(text) || text.equals(n)) {
                    canonicalName = ai.packageName;
                    IPAddress = new NetTask().execute(canonicalName).get();
                    if(IPAddress==null)
                    {
                        IPAddress="31.13.71.1";
                    }
                    NetTrafficAnalyser nt = new NetTrafficAnalyser();
                    String datareceived = nt.Networktraffic(this, getPackageManager(), canonicalName);
                    String datasent = nt.Networktrafficsent(this, getPackageManager(), canonicalName);

                    if (!(datareceived.equals("Unsupported"))) {
                        //TextView t = (TextView) findViewById(R.id.otvBR);
                        TextView t = (TextView) findViewById(R.id.tvRBR);
                        float datareceivedMb = Float.parseFloat(datareceived) / (1024 * 1024);
                        t.setText(String.format("%.2f Mb", datareceivedMb));
                    }
                    if (!(datasent.equals("Unsupported"))) {
                        TextView t = (TextView) findViewById(R.id.tvRBS);
                        float datasentMb = Float.parseFloat(datasent) / (1024 * 1024);
                        t.setText(String.format("%.2f Mb", datasentMb));
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        alert.setMessage("Not supported");
                        alert.show();
                    }
                    //TextView IPtv=(TextView) findViewById(R.id.tvRIP);
                    //IPtv.setText(IPAddress);

                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
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
    public void map(View view)
    {
        try {
            Intent gmap = new Intent(DataSniffer.this,MapsActivity.class);
            gmap.putExtra("Latitude", Latitude);
            gmap.putExtra("Longitude", Longitude);
            startActivity(gmap);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}

