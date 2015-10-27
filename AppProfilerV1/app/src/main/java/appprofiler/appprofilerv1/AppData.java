package appprofiler.appprofilerv1;

/**
 * Created by soory_000 on 10/27/2015.
 */
    import android.app.Application;
    import android.content.pm.PackageInfo;

    public class AppData extends Application {

        PackageInfo packageInfo;

        public PackageInfo getPackageInfo() {
            return packageInfo;
        }

        public void setPackageInfo(PackageInfo packageInfo) {
            this.packageInfo = packageInfo;
        }
    }

