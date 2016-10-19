package net.computeering.newschoolbus.SchoolManagePackage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import net.computeering.newschoolbus.R;

public class test extends AppCompatActivity implements LocationListener {
    private GoogleMap mmap;

    private LocationManager locationManager;

    private String provider;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map__running_car_);


        GooglePlayServicesUtil.isGooglePlayServicesAvailable(test.this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, true);


        if (provider == null) {  //위치정보 설정이 안되어 있으면 설정하는 엑티비티로 이동합니다

            new AlertDialog.Builder(test.this)

                    .setTitle("위치서비스 동의")

                    .setNeutralButton("이동", new DialogInterface.OnClickListener() {

                        @Override

                        public void onClick(DialogInterface dialog, int which) {

                            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);

                        }

                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override

                public void onCancel(DialogInterface dialog) {

                    finish();

                }

            })

                    .show();

        } else {   //위치 정보 설정이 되어 있으면 현재위치를 받아옵니다
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 1, 1, test.this);

            setUpMapIfNeeded();

        }


    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//위치설정 엑티비티 종료 후

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 0:

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Criteria criteria = new Criteria();

                provider = locationManager.getBestProvider(criteria, true);

                if (provider == null) {//사용자가 위치설정동의 안했을때 종료

                    finish();

                } else {//사용자가 위치설정 동의 했을때

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(provider, 1L, 2F, test.this);

                    setUpMapIfNeeded();

                }

                break;

        }

    }


    @Override

    public void onBackPressed() {

        this.finish();

    }


    @Override

    protected void onResume() {

        super.onResume();

        setUpMapIfNeeded();


    }


    @Override

    protected void onPause() {

        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);

    }


    private void setUpMapIfNeeded() {

        if (mmap == null) {

            mmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mmap != null) {

                setUpMap();

            }

        }

    }


    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mmap.setMyLocationEnabled(true);

        mmap.getMyLocation();


    }


    boolean locationTag = true;


    @Override

    public void onLocationChanged(Location location) {

        //if (locationTag) {//한번만 위치를 가져오기 위해서 tag를 주었습니다

        Log.d("myLog", "onLocationChanged: !!" + "onLocationChanged!!");

        double lat = location.getLatitude();

        double lng = location.getLongitude();


        Toast.makeText(test.this, "위도  : " + lat + " 경도: " + lng, Toast.LENGTH_SHORT).show();

        //locationTag = false;

        //}


    }


    @Override

    public void onProviderDisabled(String provider) {

        // TODO Auto-generated method stub


    }


    @Override

    public void onProviderEnabled(String provider) {

        // TODO Auto-generated method stub


    }


    @Override

    public void onStatusChanged(String provider, int status, Bundle extras) {

        // TODO Auto-generated method stub


    }
}