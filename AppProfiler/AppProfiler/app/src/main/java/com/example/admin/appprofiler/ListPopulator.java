package com.example.admin.appprofiler;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ADMIN on 9/29/2015.
 */
public class ListPopulator extends ArrayAdapter<ApplicationInfo>
{
    private List<ApplicationInfo> appList = null;
    private Context context;
    private PackageManager packageManager;

    public ListPopulator(Context context, int resource, List<ApplicationInfo> objects) {
        super(context, resource, objects);

        this.context = context;
        this.appList = objects;
        packageManager = context.getPackageManager();
    }

    public int getCount(){
        return ((null != appList)? appList.size() : 0);
    }

    public ApplicationInfo getItem(int position){
        return ((null != appList)?appList.get(position): null);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position,View convertView, ViewGroup parent) {
        View view = convertView;

        if(null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.app_list,null);
        }

        ApplicationInfo data = appList.get(position);

        if(null != data){
            TextView appName = (TextView) view.findViewById(R.id.app_name);
            ImageView iconView = (ImageView) view.findViewById(R.id.app_icon);

            appName.setText(data.loadLabel(packageManager));
            iconView.setImageDrawable(data.loadIcon(packageManager));
        }
        return view;
    }
}
