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
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class DataSniffer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sniffer);
        try {
            Networkanalyser();
        }catch (IOException e){
            e.printStackTrace();
        }
        //setContentView(R.layout.data_sniffer);
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

        for (ApplicationInfo ai : lstAppInfo){
            String n = (String)pm.getApplicationLabel(ai);
            if (n.equals(text) || text.equals(n)){
                canonicalName = ai.packageName;
                NetTrafficAnalyser nt = new NetTrafficAnalyser();
                String datareceived =   nt.Networktraffic(this, getPackageManager(), canonicalName);
                String datasent = nt.Networktrafficsent(this, getPackageManager(), canonicalName);

                if(!(datareceived.equals("Unsupported"))) {
                    //TextView t = (TextView) findViewById(R.id.otvBR);
                    TextView t = (TextView)findViewById(R.id.tvRBR);
                    float datareceivedMb = Float.parseFloat(datareceived) /(1024*1024);
                    t.setText(String.format("%.2f Mb",datareceivedMb));
                }
                if(!(datasent.equals("Unsupported"))) {
                    TextView t = (TextView) findViewById(R.id.tvRBS);
                    float datasentMb = Float.parseFloat(datasent)/(1024*1024);
                    t.setText(String.format("%.2f Mb",datasentMb)); }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("Not supported");
                    alert.show();
                }

            }
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
}

