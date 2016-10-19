package net.computeering.newschoolbus;

import net.computeering.newschoolbus.BeaconPackage.RecoBackgroundMonitoringService;
import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.LoginPackage.LoginActivity;
import net.computeering.newschoolbus.SchoolManagePackage.SchoolMange_Activity;
import net.computeering.newschoolbus.TCP.ServerCheck;
import net.computeering.newschoolbus.TCP.TCP_SC;
import net.computeering.newschoolbus.UDP.UDP_SC;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECOServiceConnectListener;

import net.computeering.newschoolbus.BeaconPackage.RecoBackgroundMonitoringService;
import net.computeering.newschoolbus.R;

import java.util.ArrayList;
import java.util.Collection;

import layout.ActivityLogsListFragmentFragment;
import layout.MapFragment;

public class MasterMainActivity extends AppCompatActivity implements RECOServiceConnectListener, RECOMonitoringListener {
    ComponentName beaconServiceVar;
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

    CustomPreferences MasterMainActivity_pref = new CustomPreferences(this);
    static TextView view_schoolName;
    static TextView view_memager;
    static TextView view_loc;
    static TextView view_notice;
    private View mLayout;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    CustomPreferences pref = new CustomPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_main);

        mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), SCAN_RECO_ONLY, ENABLE_BACKGROUND_RANGING_TIMEOUT);
        //mRegions = this.generateBeaconRegion();
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
        beaconServiceVar = startService(intent);

//        Intent MAA_gotoLoginActivity_Intent = new Intent(MasterMainActivity.this, LoginActivity.class);
//        startActivity(MAA_gotoLoginActivity_Intent);
        String temp = pref.getValue(CustomPreferences.Bluetooth_prefvalue,"");

        if(temp.equals("FALSE"))
            SchoolData.Bluetooth_Check = false;

        //!@
        // 여기 블루투스 키고 끄는거 넣으면 됨
        {
            if (SchoolData.Bluetooth_Check) {
                //그대로
            } else {
                //블루투스 끄기
            }
        }
        /**
         * 이하 onCreate항목은 기본값을 최대한 유지합니다.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        SchoolData.SetArrayList();
        (new SetList()).execute(new String[]{"1"});
        ServerCheck.showLoading(MasterMainActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(beaconServiceVar == null){
            return;
        }
        Intent i = new Intent();
        i.setComponent(beaconServiceVar);
        if(stopService(i)){
            Log.e("MMA_Seongho","Service Stopped!!");
            Toast.makeText(getApplicationContext(),"비콘 감지 서비스 종료됨",Toast.LENGTH_SHORT);
        }else{
            Log.e("MMA_Seongho","Service ALREADY Stopped!!");
            Toast.makeText(getApplicationContext(),"비콘 감지 서비스 이미 종료됨",Toast.LENGTH_SHORT);
        }


    }

//    @Override
//    protected void onPause(){
//        for (int i = 0; i < SchoolData.carList.size(); i++) {
//            UDP_SC.SendMsg("STOP" + UDP_SC._del + SchoolData.carList.get(i)+ UDP_SC._del +"메인페이지");
//            Log.e("Map:  ", SchoolData.carList.get(i));
//            Log.e("Map: -> ", UDP_SC._del);
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        super.onPause();
//
//    }

    public class SetList extends AsyncTask<String, Void, String> {
        String msg = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String type = params[0];
            if (type.compareTo("1") == 0) {
                msg = TCP_SC.GetMsg();
                if(msg.equals("100"))
                    return "100";
                Log.e("Main_test:  ", msg);
                String msgs[] = msg.split(TCP_SC._del);

                SchoolData.num = msgs[1];
                SchoolData.schoolName = msgs[2];
                SchoolData.locate = msgs[3];
                SchoolData.lat = msgs[4];
                SchoolData.lon = msgs[5];
                SchoolData.master = msgs[6];
                SchoolData.isNotice = msgs[7];
                SchoolData.notice = msgs[8];
            }

            msg = "S_CAR" + TCP_SC._del + SchoolData.schoolName + TCP_SC._del;
            TCP_SC.SendMsg(msg);

            for (; ; ) {
                msg = TCP_SC.GetMsg();
                if(msg.equals("100"))
                    return "100";
                boolean out = false;
                Log.e("Main_test:  ", msg + "  ");

//                for(int j=0;j<= TCP_Que.top;j++){
//                    Log.e("Main_test for ", TCP_Que.Queue[j]);
//                }
                String MSGS[] = msg.split(TCP_SC._endDel);

                for (int i = 0; i < MSGS.length; i++) {

                    String msgs[] = MSGS[i].split(TCP_SC._del);
                    //Log.e("Main_test", msgs[1] + "  " + msgs[2]);
                    if (i == 0) {
                        //차 받기
                        if (!msgs[1].equals("0"))
                            SchoolData.carList.add(SchoolData.schoolName + "!" + msgs[1]);
                        else {
                            out = true;
                        }
                    } else {
                        if (!msgs[0].equals("0"))
                            SchoolData.carList.add(SchoolData.schoolName + "!" + msgs[0]);
                        else {
                            out = true;
                        }
                    }

                }
                if (out)
                    break;
            }


            //비콘
            msg = "S_BEACON" + TCP_SC._del + SchoolData.schoolName + TCP_SC._del;

            TCP_SC.SendMsg(msg);

            for (; ; ) {
                msg = TCP_SC.GetMsg();
                if(msg.equals("100"))
                    return "100";

                Log.e("Main_test:  ", msg + "  ");
                boolean out = false;
                String MSGS[] = msg.split(TCP_SC._endDel);
                for (int i = 0; i < MSGS.length; i++) {
                    String msgs[] = MSGS[i].split(TCP_SC._del);
                    if (i == 0) {
                        //비콘 받기
                        if (!msgs[1].equals("0"))
                            SchoolData.beaconList.add(msgs[1] + TCP_SC._del + msgs[2]);
                        else {
                            out = true;
                        }
                    } else {
                        if (!msgs[0].equals("0"))
                            SchoolData.beaconList.add(msgs[0] + TCP_SC._del + msgs[1]);
                        else {
                            out = true;
                        }
                    }

                }
                if (out)
                    break;
            }

            ServerCheck.hideLoading();

            return "1";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.compareTo("1") == 0) {
                //학원 정보 화면에 출력



            }
            if(result.compareTo("100") == 0){
                Toast.makeText(MasterMainActivity.this, "연결이 불안정 합니다. 다시 시도해주세요!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_memage) {
            Intent goAdminIntent = new Intent(MasterMainActivity.this, SchoolMange_Activity.class);
            startActivity(goAdminIntent);
            return true;
        }
//        if (id == R.id.action_setting) {
//            Intent setting_page = new Intent(MasterMainActivity.this, Setting_Activity.class);
//            startActivity(setting_page);
//        }

        if (id == R.id.action_logout_in_mastermainactivity) {
            MasterMainActivity_pref.put(CustomPreferences.AUTOLOGIN_prevalue,"FALSE");
            MasterMainActivity_pref.put(CustomPreferences.AMIFIRST_prefvalue,"FALSE");
            Intent gobackLoginActivityIntent = new Intent(MasterMainActivity.this, LoginActivity.class);
            startActivity(gobackLoginActivityIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_master_main, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));




            return rootView;
        }
    }

    /**
     * A simple fragment that displays a TextView.
     */
    public static class TextFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
            View rootView =  inflater.inflate(R.layout.text_fragment, container, false);



            view_schoolName = (TextView) rootView.findViewById(R.id.view_schoolName);
            view_memager = (TextView) rootView.findViewById(R.id.view_memager);
            view_loc = (TextView) rootView.findViewById(R.id.view_loc);
            view_notice = (TextView) rootView.findViewById(R.id.view_notice);

            view_schoolName.setText(SchoolData.schoolName);
            view_memager.setText(SchoolData.master);
            view_loc.setText(SchoolData.locate);
            if(SchoolData.isNotice.equals("1"))
                view_notice.setText(SchoolData.notice);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new TextFragment();
                case 1:
                    return new MapFragment();
                case 2:
                    return new ActivityLogsListFragmentFragment();
                default:
                    return new TextFragment();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "학 원   홈";
                case 1:
                    return "차 량   위 치";
                case 2:
                    return "활 동   로 그";
            }
            return null;
        }
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
