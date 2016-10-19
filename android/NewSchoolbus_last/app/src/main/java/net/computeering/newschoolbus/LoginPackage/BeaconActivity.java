package net.computeering.newschoolbus.LoginPackage;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECOServiceConnectListener;

import net.computeering.newschoolbus.BeaconPackage.RecoBackgroundMonitoringService;
import net.computeering.newschoolbus.CustomPreferences;
import net.computeering.newschoolbus.R;

import java.util.ArrayList;
import java.util.Collection;

public class BeaconActivity extends AppCompatActivity implements RECOServiceConnectListener, RECOMonitoringListener {
    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
    public static final boolean SCAN_RECO_ONLY = true;
    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = false;
    public static final boolean DISCONTINUOUS_SCAN = false;
    public static final int REQUEST_ENABLE_BT = 1;
    public static final int REQUEST_LOCATION = 10;
    public RECOBeaconManager mRecoManager;
    public ArrayList<RECOBeaconRegion> mRegions;
    public BluetoothManager mBluetoothManager;
    public BluetoothAdapter mBluetoothAdapter;
    private long mScanPeriod = 1 * 1000L;
    private long mSleepPeriod = 2 * 1000L;
    private long mRegionExpirationTime = 3 * 1000L;
    private boolean mInitialSetting = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), SCAN_RECO_ONLY, ENABLE_BACKGROUND_RANGING_TIMEOUT);
        mRegions = this.generateBeaconRegion();
        //RECOMonitoringListener 를 설정합니다. (필수)
        mRecoManager.setMonitoringListener(this);
        //Set scan period and sleep period.
        //The default is 1 second for the scan period and 10 seconds for the sleep period.
        mRecoManager.setScanPeriod(mScanPeriod);
        mRecoManager.setSleepPeriod(mSleepPeriod);
        /**
         * RECOServiceConnectListener와 함께 RECOBeaconManager를 bind 합니다. RECOServiceConnectListener는 RECOActivity에 구현되어 있습니다.
         * monitoring 및 ranging 기능을 사용하기 위해서는, 이 메소드가 "반드시" 호출되어야 합니다.
         * bind후에, onServiceConnect() 콜백 메소드가 호출됩니다. 콜백 메소드 호출 이후 monitoring / ranging 작업을 수행하시기 바랍니다.
         */
        mRecoManager.bind(this);
        Intent intent = new Intent(this, RecoBackgroundMonitoringService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private ArrayList<RECOBeaconRegion> generateBeaconRegion() {
        ArrayList<RECOBeaconRegion> regions = new ArrayList<RECOBeaconRegion>();

        int mMajor1 = 501;
        int mMajor2 = 502;

        int mMinor1 = 4226;
        int mMinor2 = 4304;
        int mMinor3 = 4305;

        RECOBeaconRegion mRecoRegion1 = new RECOBeaconRegion(RECO_UUID, mMajor1, mMinor1, "꿈터어린이집 1호차량");
        RECOBeaconRegion mRecoRegion2 = new RECOBeaconRegion(RECO_UUID, mMajor1, mMinor2, "꿈터어린이집 2호차량");
        RECOBeaconRegion mRecoRegion3 = new RECOBeaconRegion(RECO_UUID, mMajor2, mMinor3, "대구어린이집 1호차량");
        RECOBeaconRegion mRecoRegion4 = new RECOBeaconRegion(RECO_UUID, mMajor1, "꿈터어린이집 전체차량");
        RECOBeaconRegion mRecoRegion5 = new RECOBeaconRegion(RECO_UUID, mMajor2, "대구어린이집 전체차량");

        regions.add(mRecoRegion1);
        regions.add(mRecoRegion2);
        regions.add(mRecoRegion3);
        regions.add(mRecoRegion4);
        regions.add(mRecoRegion5);

        RECOBeaconRegion recoRegion;

        //this.stop(mRegions);
        this.unbind();

        return regions;
    }

    protected void start(ArrayList<RECOBeaconRegion> regions) {
        Log.i("RecoMonitoringActivity", "start");

        for (RECOBeaconRegion region : regions) {
            try {
                region.setRegionExpirationTimeMillis(mRegionExpirationTime);
                mRecoManager.startMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.i("RECOMonitoringActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RecoMonitoringActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceConnect() {
        Log.i("RecoMonitoringActivity", "onServiceConnect");
        this.start(mRegions);
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }

    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState recoRegionState, RECOBeaconRegion recoRegion) {
        Log.i("RecoMonitoringActivity", "didDetermineStateForRegion()");
        Log.i("RecoMonitoringActivity", "region: " + recoRegion.getUniqueIdentifier() + ", state: " + recoRegionState.toString());

        if (mInitialSetting) {
            //mMonitoringListAdapter.updateRegion(recoRegion, recoRegionState, 0, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()));
            //mMonitoringListAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "didDetermineState", Toast.LENGTH_SHORT);

        }

        mInitialSetting = false;
        //Write the code when the state of the monitored region is changed
    }

    @Override
    public void didEnterRegion(RECOBeaconRegion recoRegion, Collection<RECOBeacon> beacons) {
        /**
         * For the first run, this callback method will not be called.
         * Please check the state of the region using didDetermineStateForRegion() callback method.
         *
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */

        //Get the region and found beacon list in the entered region
        Log.i("RecoMonitoringActivity", "didEnterRegion() region:" + recoRegion.getUniqueIdentifier());

        //mMonitoringListAdapter.updateRegion(recoRegion, RECOBeaconRegionState.RECOBeaconRegionInside, beacons.size(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()));
        //mMonitoringListAdapter.notifyDataSetChanged();
        //Write the code when the device is enter the region
        Toast.makeText(getApplicationContext(), "didEntertheRegion", Toast.LENGTH_SHORT);
    }

    @Override
    public void didExitRegion(RECOBeaconRegion recoRegion) {
        /**
         * For the first run, this callback method will not be called.
         * Please check the state of the region using didDetermineStateForRegion() callback method.
         *
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */

        Log.i("RecoMonitoringActivity", "didExitRegion() region:" + recoRegion.getUniqueIdentifier());
        Toast.makeText(getApplicationContext(), "didExitRegion", Toast.LENGTH_SHORT);
        //mMonitoringListAdapter.updateRegion(recoRegion, RECOBeaconRegionState.RECOBeaconRegionOutside, 0, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()));
        //mMonitoringListAdapter.notifyDataSetChanged();
        //Write the code when the device is exit the region
    }

    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion recoRegion) {
        Log.i("RecoMonitoringActivity", "didStartMonitoringForRegion: " + recoRegion.getUniqueIdentifier());
        //Write the code when starting monitoring the region is started successfully
        Toast.makeText(getApplicationContext(), "region상태 : " + recoRegion.getUniqueIdentifier(), Toast.LENGTH_SHORT);
    }


    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.i("RecoMonitoringActivity", "Remote Exception");
            e.printStackTrace();
        }
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

}
