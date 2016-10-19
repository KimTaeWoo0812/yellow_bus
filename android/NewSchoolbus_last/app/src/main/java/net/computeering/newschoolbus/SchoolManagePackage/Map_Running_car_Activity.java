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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.UDP.UDP_SC;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


//여기서 3초에 한번씩
//UDP_SC.SendMsg("GET" + UDP_SC._del + carName+ UDP_SC._del +"lat"+ UDP_SC._del+lon);
//으로 위치 전송하면 된다
//끝나면 끝 메시지 날리기
public class Map_Running_car_Activity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback,LocationListener {
    public static GoogleMap map;
    long start = System.currentTimeMillis();
    long end = System.currentTimeMillis();
    String carName = "";
    double lat;
    double lon;
    public static boolean stopThread = false;
    public static InetAddress host = null;
    public static final int port = 5001;
    String keyName = "";
    Context context = this;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    HashMap<String,LatLng> latlng = new HashMap<String,LatLng>();
    private LocationManager locationManager;
    private String provider;

    Iterator iterator2;
    Set key;
    //UDP_Socket socket;


    @Override
    public void onLocationChanged(Location location) {
        SchoolData.latlng_check = true;
        //end = System.currentTimeMillis();

        SchoolData.lat0 = location.getLatitude();
        SchoolData.lng0 = location.getLongitude();
        LatLng latlon = new LatLng( location.getLatitude(), location.getLongitude());
        Map_Running_car_Activity.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 15));

        Log.e("thread 로케이션", " a " + SchoolData.lat0 + "   " + SchoolData.lng0);
        Toast.makeText(Map_Running_car_Activity.this, "위도  : " + SchoolData.lat0 + " 경도: " + SchoolData.lng0 , Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__running_car_);

        //socket = UDP_Socket.shared();
        SchoolData.MapFragment_stopThread = true;
        SchoolData.SendThread_stopThread = false;
        Intent intent = getIntent();
        carName = intent.getStringExtra("carName");
        SchoolData.CAR_NAME = carName;
        end = (long) (end + 3000.0);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MarkersThread_For_Driver thread_driver = new MarkersThread_For_Driver(map);
        thread_driver.start();
        Send_Thread t = new Send_Thread();
        t.start();
        Toast.makeText(Map_Running_car_Activity.this, "위치를 탐색 중입니다..", Toast.LENGTH_SHORT).show();



        GooglePlayServicesUtil.isGooglePlayServicesAvailable(Map_Running_car_Activity.this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, true);



        if(provider==null){  //위치정보 설정이 안되어 있으면 설정하는 엑티비티로 이동합니다

            new AlertDialog.Builder(Map_Running_car_Activity.this)

                    .setTitle("위치서비스 동의")

                    .setNeutralButton("이동" ,new DialogInterface.OnClickListener() {

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

        }else{   //위치 정보 설정이 되어 있으면 현재위치를 받아옵니다
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
            locationManager.requestLocationUpdates(provider, 1, 1, Map_Running_car_Activity.this);

            setUpMapIfNeeded();

        }


    }
    private void setUpMapIfNeeded() {

        if (map == null) {

            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (map != null) {

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
        map.setMyLocationEnabled(true);

        map.getMyLocation();


    }
    @Override
    protected void onPause(){
        stopThread = true;
        Log.e("스탑", "온퍼즈");


        super.onPause();

    }
    @Override
    protected void onDestroy(){
        SchoolData.SendThread_stopThread = true;
        SchoolData.MapFragment_stopThread = false;

        super.onDestroy();
    }
    @Override
    public void onMapReady(GoogleMap _map) {
        map = _map;

        map.setOnMyLocationButtonClickListener(this);
        //map is ready
        //_map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        this.map = _map;
        enableMyLocation();
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (map != null) {
            // Access to the location has been granted to the app.
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    class MarkersThread_For_Driver extends Thread {

        public MarkersThread_For_Driver(GoogleMap map) {
        }
        //map에 받아온 위치 넣으면 된다,

        @Override
        public void run() {
            key = latlng.keySet();
            for(;!SchoolData.SendThread_stopThread;) {

                String msg;

                for(int i = 0; i<SchoolData.carList.size();i++) {
                    msg = UDP_SC.GetMsg();
                    try {

                        String msgs[] = msg.split(UDP_SC._del);

                        keyName = msgs[0];
                        lat = Double.valueOf(msgs[1]);
                        lon = Double.valueOf(msgs[2]);
                        Log.e("Map:  ", keyName + "  " + lat + "  " + lon);
                    }
                    catch (NullPointerException e){
                        continue;
                    }
                    catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                    LatLng MELBOURNE = new LatLng(lat, lon);
                    latlng.put(keyName, MELBOURNE);
                }
                Map_Running_car_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        map.clear();
                        for (iterator2 = key.iterator(); iterator2.hasNext(); ) {
                            try {
                                keyName = (String) iterator2.next();

                            } catch (java.util.ConcurrentModificationException e) {
                                System.err.println("익셉션");
                                break;
                            }
                            map.addMarker(new MarkerOptions()
                                    .position(latlng.get(keyName))
                                    .title(keyName)
                                    .snippet(keyName)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_small)));
                            Log.e("test+"," "+latlng.get(keyName).latitude+ "  "+latlng.get(keyName).longitude);
//                            map.addMarker(new MarkerOptions()
//                                    .position(latlng.get(keyName))
//                                    .title(keyName)
//                                    .snippet(keyName)
//                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo)));
                        }
                        //Toast.makeText(Map_Running_car_Activity.this, "위도  : " + SchoolData.lat0 + " 경도: " + SchoolData.lng0 +" 운전자", Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    //Toast.makeText(Map_Running_car_Activity.this, "위도  : " + SchoolData.lat0 + " 경도: " + SchoolData.lng0 +" 운전자", Toast.LENGTH_SHORT).show();
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



        }
//
////            DatagramSocket socket = null;
////            try {
////                host = InetAddress.getByName("192.168.43.30");
////            } catch (UnknownHostException e) {
////                e.printStackTrace();
////            }
//
//            //UDP_SC.SendMsg("GET" + UDP_SC._del + carName);
//
////            for (int i = 0; i < SchoolData.carList.size(); i++) {
////                UDP_SC.SendMsg("START" + UDP_SC._del + SchoolData.carList.get(i));
////                Log.e("Map:  ", SchoolData.carList.get(i));
////                Log.e("Map: -> ", UDP_SC._del);
////                try {
////                    Thread.sleep(70);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
//
//            String msg = "";
//            //DatagramPacket sendPacket, receivePacket;
//            while (!stopThread) {
//                try {
//
////                    byte[] timeData = new byte[256];
//                    msg = UDP_SC.GetMsg();
//                    try {
//
//                        String msgs[] = msg.split(UDP_SC._del);
//
//                        keyName = msgs[0];
//                        lat = Double.valueOf(msgs[1]);
//                        lon = Double.valueOf(msgs[2]);
//                        Log.e("Map:  ", keyName + "  " + lat + "  " + lon);
//                    } catch (java.lang.ArrayIndexOutOfBoundsException e){
//                        continue;
//                    }
//                    LatLng MELBOURNE = new LatLng(lat, lon);
//                    latlng.put(keyName, MELBOURNE);
//                    UDP_SC.SendMsg("aaa"+UDP_SC._del);
////                    if (end - start > 3000) {
////                        Log.e("로케이션1", " a " + lat0 + "  " + lng0);
////                        UDP_SC.SendMsg("GET" + UDP_SC._del + carName+ UDP_SC._del +lat0+ UDP_SC._del+lng0);
////                        start = System.currentTimeMillis();
////
////                        Log.e("로케이션2", " a " + lat0 + "  " + lng0);
////                        //Toast.makeText(Map_Running_car_Activity.this, "위도  : " + lat0 + " 경도: " + lng0, Toast.LENGTH_SHORT).show();
////                    }
//
//
//                        Map_Running_car_Activity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                //Toast.makeText(context, lat + "  " + lon, Toast.LENGTH_SHORT).show();
//                                //map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(keyName));
//
//                                key = latlng.keySet();
//                                map.clear();
//                                for (iterator2 = key.iterator(); iterator2.hasNext(); ) {
//                                    try {
//                                        keyName = (String) iterator2.next();
//                                        // 여기 keyName으로 레디스에서 값을 찾는다.
//                                    } catch (java.util.ConcurrentModificationException e) {
//                                        System.err.println("익셉션");
//                                        break;
//                                    }
//                                    map.addMarker(new MarkerOptions()
//                                            .position(latlng.get(keyName))
//                                            .title(keyName)
//                                            .snippet(keyName)
//                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo)));
//                                }
//                            }
//                            //Marker melbourne =
//                        });
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                    //stopThread = true;
//                    Log.e("익셉션", "맵 스레드 종료");
//                }
//
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            Log.e("종료!"," ");
//
//            UDP_SC.SendMsg("DONE" + UDP_SC._del + carName + UDP_SC._del + "stopThread");
//            for (int i = 0; i < SchoolData.carList.size(); i++) {
//                UDP_SC.SendMsg("STOP" + UDP_SC._del + SchoolData.carList.get(i)+ UDP_SC._del +"stopThread");
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
////            for (int i = 0; i < SchoolData.carList.size(); i++) {
////                UDP_SC.SendMsg("STOP" + UDP_SC._del + SchoolData.carList.get(i)+ UDP_SC._del +"딴데");
////                try {
////                    Thread.sleep(70);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
//            try {
//                //UDP_SC.CloseChannel();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
    }
}