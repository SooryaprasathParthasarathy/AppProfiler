package appprofiler.appprofiler;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.ActionBarPolicy;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class NetworkUsage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_usage);
        Networkanalyser();
    }

    public void Networkanalyser() {
        Intent intent;
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        String text= bundle.getString("SelectedValue");
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> l = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        String canonicalName ;
        for (ApplicationInfo ai : l){
            String n = (String)pm.getApplicationLabel(ai);
            if (n.contains(text) || text.contains(n)){
                canonicalName = ai.packageName;
                Networktraffic nt = new Networktraffic();
             String datareceived =   nt.Networktraffic(this,getPackageManager(),canonicalName);
                String datasent = nt.Networktrafficsent(this,getPackageManager(),canonicalName);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                if(!(datareceived.equals("Unsupported"))) {
                    TextView t = (TextView) findViewById(R.id.textView4);
                    t.setText(datareceived);
                }
                if(!(datasent.equals("Unsupported"))) {
                    TextView t = (TextView) findViewById(R.id.textView3);
                    t.setText(datasent);
                }
                else
                    alert.setMessage("Not supported");
                alert.show();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_network_usage, menu);
        return true;
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
