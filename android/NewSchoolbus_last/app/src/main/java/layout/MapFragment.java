package layout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.SchoolManagePackage.Map_Running_car_Activity;
import net.computeering.newschoolbus.SchoolManagePackage.PermissionUtils;
import net.computeering.newschoolbus.UDP.UDP_SC;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,LocationListener {
    public FragmentManager manager;
    public FragmentTransaction transaction;
    public SupportMapFragment fragment;
    public static GoogleMap map;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    double lat = 5;
    double lon = 5;
    String keyName = "";
    private static boolean stopThread = false;
    public static InetAddress host = null;
    public static final int port = 5001;
    MarkersThread_For_Student thread_student;
    HashMap<String,LatLng> latlng = new HashMap<String,LatLng>();
    Iterator iterator2;
    Set key;
    boolean flag = true;

  //  MyLocationListener listener;


    public MapFragment() {

        // Required empty public constructor
    }

    public static void myAddMarker(Double lat, Double lon, GoogleMap map) {
        //map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("myAddmarkerFunction"));
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UDP_SC.SetData();
        Log.e("UDP_ 시작  :  ", " ");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


    //    @Override
//    public void onResume(){
//    }
//    @Override
//    public void onStop(){
//
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();

        fragment.getMapAsync(this);
       // listener = new MyLocationListener(getContext());
        return view;
    }

    @Override
    public void onMapReady(GoogleMap _map) {
        //map is ready
        //_map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        Log.e("UDP_ 레디 :  ", " ");
        this.map = _map;
        //myAddMarker(lat, lon, map);
        //if(SchoolData.role.equals("학생"))
        enableMyLocation();
        thread_student = new MarkersThread_For_Student(map);
        thread_student.start();
    }
    private void enableMyLocation() {
       if (map != null) {
            // Access to the location has been granted to the app.
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        if(flag) {
//            LatLng latlon = new LatLng(SchoolData.lat0, SchoolData.lng0);
//            Map_Running_car_Activity.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 15));//초기 위치...수정필요
//            flag = false;
//        }
//         double latitude = SchoolData.lat0 = location.getLatitude();
//        double longitude = SchoolData.lng0 = location.getLongitude();
//        Toast.makeText(getContext(), latitude + "  " + longitude, Toast.LENGTH_SHORT).show();
//        LatLng latlon = new LatLng(latitude, latitude);
//
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 15));//초기 위치...수정필요

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


//        if (useChanger == true)
//            OnChangeXYListener.onChangedXY(latitude, longitude);
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


    class MarkersThread_For_Student extends Thread {

        public MarkersThread_For_Student(GoogleMap map) {
        }
        //map에 받아온 위치 넣으면 된다,

        @Override
        public void run() {

            Log.e("UDP_ 고!  :  ", " ");
            DatagramSocket socket = null;
            //host = UDP_SC.host;
            //InetAddress.getByName("192.168.43.30");

            for (int i = 0; i < SchoolData.carList.size(); i++) {
//                String msg = "START" + UDP_SC._del + SchoolData.carList.get(i);
//                byte[] dummyData = new byte[256];
//                try {
//                    socket = new DatagramSocket();
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }
//                Log.e("UDP_보내는 메시지   :  ", msg);
//                dummyData = msg.getBytes();
//                Log.e("UDP_보내는 메시지  2 :  ", " " + dummyData);
//                DatagramPacket sendPacket = new DatagramPacket(dummyData, dummyData.length, host, port);
//                try {
//                    socket.send(sendPacket);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


                UDP_SC.SendMsg("START" + UDP_SC._del + SchoolData.carList.get(i));
                Log.e("Map:  ", SchoolData.carList.get(i));
                Log.e("Map: -> ", UDP_SC._del);
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            String msg = "";
            //DatagramPacket sendPacket, receivePacket;
            while (!stopThread&&!SchoolData.MapFragment_stopThread) {
                try {

                    byte[] timeData = new byte[256];


//                    receivePacket = new DatagramPacket(timeData, timeData.length);
//                    try {
//                        socket.receive(receivePacket);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("UDP_받은 메시지 33  :  ", " ");
//                    msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
//                    Log.e("UDP_받은 메시지 ALL  :  ", msg);



                    msg = UDP_SC.GetMsg();
                    try {

                        String msgs[] = msg.split(UDP_SC._del);

                        keyName = msgs[0];
                        lat = Double.valueOf(msgs[1]);
                        lon = Double.valueOf(msgs[2]);
                        Log.e("Map:  ", keyName + "  " + lat + "  " + lon);
                    } catch (java.lang.ArrayIndexOutOfBoundsException e){
                        continue;
                    }
                    //Toast.makeText(getContext(), lat + "  " + lon, Toast.LENGTH_SHORT).show();
                    //map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(keyName));

                    LatLng MELBOURNE = new LatLng(lat, lon);
                    latlng.put(keyName, MELBOURNE);



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            map.clear();
                            key = latlng.keySet();
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
                            }
//                            if(flag) {
//                                LatLng latlon = new LatLng(SchoolData.lat0, SchoolData.lng0);
//                                MapFragment.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 15));//초기 위치...수정필요
//                                flag = false;
//                            }
                        }
                    });

                } catch (NullPointerException e) {
                    //stopThread = true;
                    Log.e("익셉션", "맵 스레드 종료");
                }
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            for (int i = 0; i < SchoolData.carList.size(); i++) {
////                msg = "STOP" + UDP_SC._del + SchoolData.carList.get(i);
////                byte[] dummyData = new byte[256];
////
////                Log.e("UDP_보내는 메시지   :  ", msg);
////                dummyData = msg.getBytes();
////                Log.e("UDP_보내는 메시지  2 :  ", " " + dummyData);
////                sendPacket = new DatagramPacket(dummyData, dummyData.length, host, port);
////                try {
////                    socket.send(sendPacket);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//
//                UDP_SC.SendMsg("STOP" + UDP_SC._del + SchoolData.carList.get(i));
//                try {
//                    Thread.sleep(70);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            try {
                //UDP_SC.CloseChannel();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}