package appprofiler.appprofiler;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.net.TrafficStats.getUidRxBytes;
import static android.net.TrafficStats.getUidTxBytes;

/**
 * Created by soory_000 on 9/27/2015.
 */
public class Networktraffic {
    private String Datareceived;
    private String Datasent;

/*Use same method to return datasent and data received*/

    public String Networktraffic(Context context, PackageManager pm, String text) {

        List<ApplicationInfo> packages = pm.getInstalledApplications(
                PackageManager.GET_META_DATA);
        int UID;

        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(text)) {
                UID = packageInfo.uid;
                Datareceived = String.valueOf(getUidRxBytes(UID));
                break;
            }
        }
        if(Datareceived.equals("-1"))
            Datareceived = "Unsupported";
        return Datareceived;
    }
    public String Networktrafficsent(Context context, PackageManager pm, String text) {

        List<ApplicationInfo> packages = pm.getInstalledApplications(
                PackageManager.GET_META_DATA);
        int UID;
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(text)) {
                UID = packageInfo.uid;
                Datasent = String.valueOf(getUidTxBytes(UID));
                break;
            }
        }
        if(Datasent.equals("-1"))
            Datasent = "Unsupported";
        return Datasent;
    }


}

