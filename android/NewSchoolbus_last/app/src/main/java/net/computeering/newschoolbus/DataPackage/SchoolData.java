package net.computeering.newschoolbus.DataPackage;

import java.util.ArrayList;

/**
 * Created by kimtaewoo on 2016-03-21.
 */
public class SchoolData {
    public static String num;
    public static String schoolName;
    public static String locate;
    public static String lat;
    public static String lon;
    public static String master;
    public static String isNotice;
    public static String notice;
    public static String role;  // 학생, 학부모, 매니저 로 날아온다.

    public static ArrayList<String> carList;
    public static ArrayList<String> beaconList;
    public static double lat0 = 0;
    public static double lng0 = 0;
    public static boolean latlng_check = false;
    public static boolean SendThread_stopThread = false;
    public static boolean MapFragment_stopThread = false;
    public static String CAR_NAME;
    public static boolean Bluetooth_Check = true;

    public static void SetArrayList() {

        carList = new ArrayList<String>();
        beaconList = new ArrayList<String>();//용도는 차량용, 학원용  으로 날아온다 (구분자 TCP_SC._del)
    }

}
