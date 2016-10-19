/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Perples, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.computeering.newschoolbus.BeaconPackage;


import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECOServiceConnectListener;

import net.computeering.newschoolbus.CustomPreferences;
import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.DataPackage.UserData;
import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.SchoolListPackage.SchoolListActivity;
import net.computeering.newschoolbus.TCP.ServerCheck;
import net.computeering.newschoolbus.TCP.TCP_SC;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * RECOBackgroundMonitoringService is to monitor regions in the background.
 *
 * RECOBackgroundMonitoringService는 백그라운드에서 monitoring을 수행합니다.
 */
public class RecoBackgroundMonitoringService extends Service implements RECOMonitoringListener, RECOServiceConnectListener {
    String requireSendAcademy = "TRUE";
    String requireSendBus = "TRUE";


    CustomPreferences BeaconBackgroundMonitoringService_pref = new CustomPreferences(this);
    /**
     * We recommend 1 second for scanning, 10 seconds interval between scanning, and 60 seconds for region expiration time.
     * 1초 스캔, 10초 간격으로 스캔, 60초의 region expiration time은 당사 권장사항입니다.
     */
    private long mScanDuration = 1*1000L;
    private long mSleepDuration = 2*1000L;
    private long mRegionExpirationTime = 3*1000L;
    private int mNotificationID = 9999;
    private boolean mInitialSetting = true;
    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = false;
    public static final boolean DISCONTINUOUS_SCAN = false;
    public static final int REQUEST_ENABLE_BT = 1;
    public static final int REQUEST_LOCATION = 10;

    public BluetoothManager mBluetoothManager;
    public BluetoothAdapter mBluetoothAdapter;

    private RECOBeaconManager mRecoManager;
    private ArrayList<RECOBeaconRegion> mRegions;
    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
    public static final boolean SCAN_RECO_ONLY = true;

    @Override
    public void onCreate() {
        Log.i("BackMonitoringService", "onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("BackMonitoringService", "onStartCommand()");
        /**
         * Create an instance of RECOBeaconManager (to set scanning target and ranging timeout in the background.)
         * If you want to scan only RECO, and do not set ranging timeout in the backgournd, create an instance:
         * 		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), true, false);
         * WARNING: False enableRangingTimeout will affect the battery consumption.
         *
         * RECOBeaconManager 인스턴스틀 생성합니다. (스캔 대상 및 백그라운드 ranging timeout 설정)
         * RECO만을 스캔하고, 백그라운드 ranging timeout을 설정하고 싶지 않으시다면, 다음과 같이 생성하시기 바랍니다.
         * 		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), true, false);
         * 주의: enableRangingTimeout을 false로 설정 시, 배터리 소모량이 증가합니다.
         */
        mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), SCAN_RECO_ONLY, ENABLE_BACKGROUND_RANGING_TIMEOUT);
        this.bindRECOService();
        //this should be set to run in the background.
        //background에서 동작하기 위해서는 반드시 실행되어야 합니다.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("BackMonitoringService", "onDestroy()");
        this.tearDown();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("BackMonitoringService", "onTaskRemoved()");
        super.onTaskRemoved(rootIntent);
    }

    private void bindRECOService() {
        Log.i("BackMonitoringService", "bindRECOService()");

        mRegions = new ArrayList<RECOBeaconRegion>();
        this.generateBeaconRegion();

        mRecoManager.setMonitoringListener(this);
        mRecoManager.bind(this);
    }

    public void generateBeaconRegion() {
        ArrayList<RECOBeaconRegion> regions = new ArrayList<RECOBeaconRegion>();

        int mMajor1 = 111;
        int mMajor2 = 501;

        int mMinor1 = 4226;
        int mMinor2 = 4304;
        int mMinor3 = 4305;

        RECOBeaconRegion mRecoRegion1 = new RECOBeaconRegion(RECO_UUID, mMajor1, "차량용 비콘");
        mRecoRegion1.setRegionExpirationTimeMillis(mRegionExpirationTime);
        RECOBeaconRegion mRecoRegion2 = new RECOBeaconRegion(RECO_UUID, mMajor2, "학원용 비콘");
        mRecoRegion2.setRegionExpirationTimeMillis(mRegionExpirationTime);



        mRegions.add(mRecoRegion1);
        mRegions.add(mRecoRegion2);


    }


//    private void generateBeaconRegion() {
//        Log.i("BackMonitoringService", "generateBeaconRegion()");
//
//        RECOBeaconRegion recoRegion;
//
//        recoRegion = new RECOBeaconRegion(RECO_UUID, "RECO Sample Region");
//        recoRegion.setRegionExpirationTimeMillis(mRegionExpirationTime);
//        mRegions.add(recoRegion);
//    }

    private void startMonitoring() {
        Log.i("BackMonitoringService", "startMonitoring()");

        mRecoManager.setScanPeriod(mScanDuration);
        mRecoManager.setSleepPeriod(mSleepDuration);

        for(RECOBeaconRegion region : mRegions) {
            try {
                mRecoManager.startMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.e("BackMonitoringService", "RemoteException has occured while executing RECOManager.startMonitoringForRegion()");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("BackMonitoringService", "NullPointerException has occured while executing RECOManager.startMonitoringForRegion()");
                e.printStackTrace();
            }
        }
    }

    private void stopMonitoring() {
        Log.i("BackMonitoringService", "stopMonitoring()");

        for(RECOBeaconRegion region : mRegions) {
            try {
                mRecoManager.stopMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.e("BackMonitoringService", "RemoteException has occured while executing RECOManager.stopMonitoringForRegion()");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("BackMonitoringService", "NullPointerException has occured while executing RECOManager.stopMonitoringForRegion()");
                e.printStackTrace();
            }
        }
    }

    private void tearDown() {
        Log.i("BackMonitoringService", "tearDown()");
        this.stopMonitoring();

        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.e("BackMonitoringService", "RemoteException has occured while executing unbind()");
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnect() {
        Log.i("BackMonitoringService", "onServiceConnect()");
        this.startMonitoring();
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }

    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState state, RECOBeaconRegion region) {
        Log.i("BackMonitoringService", "didDetermineStateForRegion()");
        //Write the code when the state of the monitored region is changed
    }

    @Override
    public void didEnterRegion(RECOBeaconRegion region, Collection<RECOBeacon> beacons) {
        /**
         * For the first run, this callback method will not be called.
         * Please check the state of the region using didDetermineStateForRegion() callback method.
         *
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */

        //Get the region and found beacon list in the entered region
        Log.i("BackMonitoringService", "didEnterRegion() - " + region.getUniqueIdentifier());


        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(new Date());
        String for15MinCurrentTime = new SimpleDateFormat("mm", Locale.KOREA).format(new Date());
        //Toast.makeText(getApplicationContext(), for15MinCurrentTime, Toast.LENGTH_SHORT).show();

        //하루에 한번만 전송하는 로직

        //beaconList
        Log.e("SeonghoBeacon", " " + region.getProximityUuid());
        {
            if (currentTime.equals(BeaconBackgroundMonitoringService_pref.getValue("TODAY_prevalue", ""))) {
//                if (region.getMajor() == 001) {//차량일때
//                    if (requireSendBus.equals("FALSE")) {
//                        this.popupNotification(region.getUniqueIdentifier() + "에 이미 승차처리됨.");
//                        BeaconBackgroundMonitoringService_pref.put(CustomPreferences.TODAY_prevalue, "FALSE");
//                        Toast.makeText(getApplicationContext(), "이미 승차처리됨.", Toast.LENGTH_SHORT).show();
//                    }
//                }
                if (region.getMajor() == 501) { //학원일때
                    if (requireSendAcademy.equals("FALSE")) {
                        this.popupNotification(region.getUniqueIdentifier() + "에 이미 출석처리됨.");
                        BeaconBackgroundMonitoringService_pref.put(CustomPreferences.TODAY_prevalue, "FALSE");
                        Toast.makeText(getApplicationContext(), "이미 출석처리됨.", Toast.LENGTH_SHORT).show();

                    }
                }
            } else {
//                if (region.getMajor() == 001) {//차량일때
//                    if (requireSendBus.equals("TRUE")) {
//                        this.popupNotification(region.getUniqueIdentifier() + "에 승차하셨습니다.");
//                        BeaconBackgroundMonitoringService_pref.put(CustomPreferences.TODAY_prevalue, "FALSE");
//                        Toast.makeText(getApplicationContext(), "에 승차하셨습니다.", Toast.LENGTH_SHORT).show();
//                        requireSendBus = "FALSE";
//                    }
//                }
                if (region.getMajor() == 501) { //학원일때
                    if (requireSendAcademy.equals("TRUE")) {
                        this.popupNotification(region.getUniqueIdentifier() + "에 출석처리되었습니다.");
                        BeaconBackgroundMonitoringService_pref.put(CustomPreferences.TODAY_prevalue, "FALSE");
                        Toast.makeText(getApplicationContext(), "에 출석하셨습니다.", Toast.LENGTH_SHORT).show();
                        requireSendAcademy = "FALSE";




                        (new SetList()).execute(new String[]{"1"});

                    }
                }
            }
        }


        //15분에 한번 확인하는 로직
        //10초마다 비콘이 쏜다. 비콘 신호를 받으면 15분동안 계속 딜레이시킨다.
          //      비콘 신호를 못받은지 15분이 지났다. 그 후 새 신호가 들어오면 (새 버스를 승차한 것으로 간주) 한다.
        {
            long dueTime = 0;
            long lastSignal = System.currentTimeMillis();
            Log.e("15min","enterthe region "+lastSignal + " " +dueTime);
            if (lastSignal > dueTime) {   //새로운 차량 || 처음 차량 탑승
                Log.e("15min","enterthe region "+lastSignal + " " +dueTime);
                if (region.getMajor() == 111) {//차량일때
                    dueTime = lastSignal + (1000 * 60 * 15);//15분 연기
                    Log.e("15min","enterthe region "+lastSignal + " " +dueTime);
                    if (requireSendBus.equals("TRUE")) {
                        this.popupNotification(region.getUniqueIdentifier() + "에 승차하셨습니다.");
                        BeaconBackgroundMonitoringService_pref.put(CustomPreferences.TODAY_prevalue, "FALSE");
                        Toast.makeText(getApplicationContext(), "에 승차하셨습니다.", Toast.LENGTH_SHORT).show();
                        requireSendBus = "FALSE";

                        (new SetList()).execute(new String[]{"2"});

                    }
                }
            }
        }
        //Write the code when the device is enter the region
    }

    static String getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat f = new SimpleDateFormat("yyyy년MM월dd일hh시mm분ss초");
        return f.format(new Date(time));
    }


    @Override
    public void didExitRegion(RECOBeaconRegion region) {
        /**
         * For the first run, this callback method will not be called.
         * Please check the state of the region using didDetermineStateForRegion() callback method.
         *
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */
        Log.i("BackMonitoringService", "didExitRegion() - " + region.getUniqueIdentifier());
        //this.popupNotification("차량에서 내렸습니다." + region.getUniqueIdentifier());
        //Write the code when the device is exit the region
    }

    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion region) {
        Log.i("BackMonitoringService", "didStartMonitoringForRegion() - " + region.getUniqueIdentifier());
        //Write the code when starting monitoring the region is started successfully
    }

    private void popupNotification(String msg) {
        Log.i("BackMonitoringService", "popupNotification()");
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(new Date());
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo)
                .setContentTitle(msg + " " + currentTime)
                .setContentText(msg);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        builder.setStyle(inboxStyle);
        nm.notify(mNotificationID, builder.build());
        mNotificationID = (mNotificationID - 1) % 1000 + 9000;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // This method is not used
        return null;
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed.
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    public void monitoringDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed to monitor the region.
        //See the RECOErrorCode in the documents.
        return;
    }



    public class SetList extends AsyncTask<String, Void, String> {
        String msg = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            if (type.equals("1"))
            {
                msg = "LOG"+TCP_SC._del+ UserData.id+TCP_SC._del+UserData.name+
                        TCP_SC._del+ SchoolData.schoolName+"학원에 입장함"+TCP_SC._del+getDate()+TCP_SC._del;

                Log.e("beacon","   "+msg);
                TCP_SC.SendMsg(msg);
            }
            else if(type.equals("2")){

                TCP_SC.SendMsg("LOG"+TCP_SC._del+ UserData.id+TCP_SC._del+UserData.name+
                        TCP_SC._del+ SchoolData.schoolName+"차량에 승차함"+TCP_SC._del+getDate()+TCP_SC._del);
            }

            return "1";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("1")) {
            }
        }
    }
}
