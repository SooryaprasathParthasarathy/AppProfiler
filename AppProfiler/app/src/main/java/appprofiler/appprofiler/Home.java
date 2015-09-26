package appprofiler.appprofiler;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DisplayApplication();
    }

    private void DisplayApplication() {
        PackageManager Manager;
        Manager = getPackageManager();
        List<ApplicationInfo> Installedapps = Manager.getInstalledApplications(0);
        ListView Listviewholder = (ListView) findViewById(R.id.Displayapps);
       // String pkg =
        //Drawable icon = getContext().getPackageManager().getApplicationIcon(pkg);
        String[] Apps = new String[Installedapps.size()]; //To convert into string for parsing
        for (ApplicationInfo app : Installedapps) {
            for (int i = 0; i < Installedapps.size(); i++) {
                Apps[i] = Installedapps.get(i).packageName;
            }
            ArrayAdapter<String> listitemsdetails = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, Apps);
            Listviewholder.setAdapter(listitemsdetails);
        }
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
