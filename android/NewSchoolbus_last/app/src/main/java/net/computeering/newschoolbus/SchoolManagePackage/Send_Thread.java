package net.computeering.newschoolbus.SchoolManagePackage;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.UDP.UDP_SC;

/**
 * Created by kimtaewoo on 2016-03-26.
 */
public class Send_Thread extends Thread {

    boolean flag = true;
    public Send_Thread(){

    }

    @Override
    public void run(){
        for(;!SchoolData.SendThread_stopThread;){
            Log.e("thread: ", "running!");
            if(SchoolData.latlng_check){
                Log.e("thread: ", "send!");
                UDP_SC.SendMsg("GET" + UDP_SC._del + SchoolData.CAR_NAME + UDP_SC._del + SchoolData.lat0 + UDP_SC._del + SchoolData.lng0);

                if(flag) {
                    UDP_SC.SendMsg("START" + UDP_SC._del + SchoolData.CAR_NAME);
                    flag = false;
                }
            }

            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        UDP_SC.SendMsg("DONE" + UDP_SC._del + SchoolData.CAR_NAME + UDP_SC._del + "stopThread");
        for (int i = 0; i < SchoolData.carList.size(); i++) {
            UDP_SC.SendMsg("STOP" + UDP_SC._del + SchoolData.carList.get(i) + UDP_SC._del + "stopThread");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
