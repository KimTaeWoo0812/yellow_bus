//package layout;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.model.LatLng;
//
//import net.computeering.newschoolbus.DataPackage.SchoolData;
//
//
//class MyLocationListener implements LocationListener {
//
//    LocationManager manager;
//    Location loc;
//
//    boolean useChanger;
//
//    Context mContext;
//    onChangedXYListener OnChangeXYListener;
//
//    public MyLocationListener(Context c) {
//        useChanger = false;
//        mContext = c;
//
//        setOption();
//    }
//
//    public MyLocationListener(Context c, onChangedXYListener onChangedXYListener_) {
//        useChanger = true;
//        mContext = c;
//        setOnChangedXYListener(onChangedXYListener_);
//        setOption();
//    }
//
//    /**
//     * �浵���� �����ɴϴ�.
//     */
//    void setOption() {
//        if (manager == null) {
//            manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
//        }
//        long minTime = 2000;
//
//        float minDistance = 0;
//
////        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////            return;
////        }
////        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, mContext);
//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
//
//    }
//
//    // ��ġ������ �Ʒ� �޼��带 ���ؼ� ���޵ȴ�.
////    @Override
////    public void onLocationChanged(Location location) {
//////        appendText("onLocationChanged()�� ȣ��Ǿ����ϴ�");
////        double latitude = SchoolData.lat0 = location.getLatitude();
////        double longitude = SchoolData.lng0 = location.getLongitude();
////        Toast.makeText(mContext, latitude + "  " + longitude, Toast.LENGTH_SHORT).show();
////        LatLng latlon = new LatLng(latitude, latitude);
////
////
////        MapFragment.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 15));//초기 위치...수정필요
////
////        if (useChanger == true)
////            OnChangeXYListener.onChangedXY(latitude, longitude);
////    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    public void setOnChangedXYListener(onChangedXYListener OnChangeXYListener_) {
//        OnChangeXYListener = OnChangeXYListener_;
//    }
//
//    /**
//     * GPS ������ �������� ��������
//     * ���������� ���� ����� alert â
//     */
//    public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//
//        alertDialog.setTitle("GPS �����������");
//        alertDialog.setMessage("GPS ������ ���� ���ֽ��ϴ�.\n ����â���� ���ðڽ��ϱ�?");
//
//        // OK �� ������ �Ǹ� ����â���� �̵��մϴ�.
//        alertDialog.setPositiveButton("����",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        mContext.startActivity(intent);
//                    }
//                });
//        // Cancle �ϸ� ���� �մϴ�.
//        alertDialog.setNegativeButton("���",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        alertDialog.show();
//    }
//
//    interface onChangedXYListener {
//        void onChangedXY(double lat_, double lon_);
//    }
//}