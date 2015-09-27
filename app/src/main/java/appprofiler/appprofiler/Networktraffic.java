package appprofiler.appprofiler;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.net.TrafficStats.getUidRxBytes;

/**
 * Created by soory_000 on 9/27/2015.
 */
public class Networktraffic {
    private int Datareceived;

    public int Networktraffic(Context context, PackageManager pm, String text) {

        List<ApplicationInfo> packages = pm.getInstalledApplications(
                PackageManager.GET_META_DATA);
        int UID;
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(text)) {
                UID = packageInfo.uid;
                Datareceived = (int) getUidRxBytes(UID);

                //   Toast.makeText(context, Datareceived, Toast.LENGTH_SHORT).show();
            }
        }
        return Datareceived;
    }
}
        /*File dir = new File("/proc/uid_stat/");
        String[] children = dir.list();
        List<Integer> uids = new ArrayList<Integer>();
        try {
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    int uid = Integer.parseInt(children[i]);
                    if ((uid >= 0 && uid < 2000) || (uid >= 10000)) {
                        uids.add(uid);
                    }
                }
            }*/
