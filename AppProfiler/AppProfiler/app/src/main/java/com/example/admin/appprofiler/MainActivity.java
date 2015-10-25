package com.example.admin.appprofiler;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ListPopulator listPopulator = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager = getPackageManager();

        new LoadApplications().execute();
    }

    protected void onListItemClick(ListView l,View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        ApplicationInfo app = applist.get(position);
        try{

            String name = (String)packageManager.getApplicationLabel(app);
            //Intent intent = packageManager.getLaunchIntentForPackage(app.packageName);
            Intent intent = new Intent("com.example.admin.appprofiler.AP_Menu");
            intent.putExtra("AppName",name);


            if(intent != null) {
                startActivity(intent);
            }
            //String value = (String)l.getItemAtPosition(position);

        }catch(ActivityNotFoundException e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list){

        ArrayList<ApplicationInfo> appList = new ArrayList<ApplicationInfo>();

        for(ApplicationInfo info : list){
            try{
                if(packageManager.getLaunchIntentForPackage(info.packageName)!= null){
                    appList.add(info);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return appList;
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void>{

        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listPopulator = new ListPopulator(MainActivity.this,R.layout.app_list,applist);

            return null;
        }

        protected void onPostExecute(Void result){
            setListAdapter(listPopulator);
            progress.dismiss();
            super.onPostExecute(result);
        }

        protected void onPreExecute(){
            progress = ProgressDialog.show(MainActivity.this,null,"Loading apps Info.....");
            super.onPreExecute();
        }
    }

}
