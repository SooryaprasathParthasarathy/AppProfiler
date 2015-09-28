package appprofiler.appprofiler;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DisplayApplication();
    }

    private void DisplayApplication() {
        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        PackageManager Manager;
        Manager = getPackageManager();

        List<ApplicationInfo> Installedapps = Manager.getInstalledApplications(0);
        ApplicationInfo[] applicationInfo = new ApplicationInfo[Installedapps.size()];
        String[] applicationname = new String[Installedapps.size()];

            for (int i = 0; i < Installedapps.size(); i++) {
                try {
                    applicationInfo[i] = (Manager.getApplicationInfo(Installedapps.get(i).packageName, 0));
                    applicationname[i] = Manager.getApplicationLabel(applicationInfo[i]).toString();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> listitemsdetails = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, applicationname);
                dropdown.setAdapter(listitemsdetails);
            }
        }

    public void RetriveDetails(View view) {
        Intent i = new Intent(this, NetworkUsage.class);
        Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
        String text = mySpinner.getSelectedItem().toString();
        i.putExtra("SelectedValue",text);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public Context getContext() {
        return this;
    }
}
