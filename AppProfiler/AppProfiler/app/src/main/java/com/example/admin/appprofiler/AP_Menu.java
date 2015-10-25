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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AP_Menu extends ListActivity {

    private ArrayList<String> MenuList = null;
    private MenuLstPopulator menuListPopulator = null;
    private String sAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ap__menu);

        Intent intent = getIntent();
        sAppName = intent.getStringExtra("AppName");

        TextView app = (TextView) findViewById(R.id.TxtVw_AppName);
        app.setText(sAppName);

        new LoadApplications().execute();
    }

    private ArrayList<String> setMenuOptions(){

        ArrayList<String> list = new ArrayList<String>();

        list.add("Battery Radar");
        list.add("Data Sniffer");
        list.add("Usage Monitor");
        list.add("Log Manager");
        list.add("Network Watch");
        list.add("Server Locator");
        list.add("Task Manager");

        return list ;
    }

    protected void onListItemClick(ListView l,View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String value = (String)l.getItemAtPosition(position);

        try{

            if(value.equals("Data Sniffer"))
            {
                Intent DS_Intent = new Intent("com.example.admin.appprofiler.DataSniffer");
                DS_Intent.putExtra("SelectedValue",sAppName);

                if(DS_Intent != null) {
                    startActivity(DS_Intent);
                }
            }

        }catch(ActivityNotFoundException e){
            Toast.makeText(AP_Menu.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(AP_Menu.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class LoadApplications extends AsyncTask<Void, Void,Void>{

        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void...params){
            MenuList = setMenuOptions();
            menuListPopulator = new MenuLstPopulator(AP_Menu.this,R.layout.menu_lst_populator,MenuList);

            return null;
        }

        protected void onPostExecute(Void result){
            setListAdapter(menuListPopulator);
            progress.dismiss();
            super.onPostExecute(result);
        }

        protected void onPreExecute(){
            progress = ProgressDialog.show(AP_Menu.this,null,"Loading Menu-Options.....");
            super.onPreExecute();
        }

    }
}
