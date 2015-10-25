package com.example.admin.appprofiler;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/**
 * Created by ADMIN on 10/11/2015.
 */
public class MenuLstPopulator extends ArrayAdapter<String> {
    private Context context;
    private List<String> menulst;
    private String appName;

    //private static ListView Menu_lst_view;
    //private static String[] Menu_options = new String[]{"Battery Radar","Data Sniffer","Usage Monitor",
    //"Log Manager","Network Watch","Server Locator","Task Manager"};

    public MenuLstPopulator(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.menulst = objects;
    }

    public int getCount(){
        return ((null != menulst)? menulst.size() : 0);
    }

    public String getItem(int position){
        return ((null != menulst)?menulst.get(position): null);
    }

    public View getView(int position,View convertView, ViewGroup parent) {
        View view = convertView;

        if(null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.menu_lst_populator,null);
        }

        String data = menulst.get(position);

        if(null != data){

            TextView menu = (TextView) view.findViewById(R.id.menu_name);
            menu.setText(data);
        }
        return view;
    }
}
