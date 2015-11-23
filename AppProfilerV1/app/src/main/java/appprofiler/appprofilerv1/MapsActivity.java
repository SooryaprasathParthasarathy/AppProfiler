package appprofiler.appprofilerv1;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

public class MapsActivity extends Activity {



    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        //Bundle b = i.getExtras();

        String lat = i.getStringExtra("Latitude");
        String lon = i.getStringExtra("Longitude");

        //final LatLng latlng1 = new LatLng(Double.parseDouble(lat) , Double.parseDouble(lon));
        final LatLng latlng1 = new LatLng(36.778259,-119.417931);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            Marker TP = googleMap.addMarker(new MarkerOptions().
                    position(latlng1).title("App Profiler"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
