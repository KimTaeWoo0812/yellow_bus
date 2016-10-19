package net.computeering.newschoolbus.TCP;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class Broadcast_Receiver extends BroadcastReceiver {

	String action;

	@Override
	public void onReceive(Context context, Intent intent) {

		action = intent.getAction();

		Log.d("TestReceiver", "action : " + action);


		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			try {
				TCP_SC.CloseChannel();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				TCP_SC.SetData();
			} catch(Exception e){
				e.printStackTrace();
			}

		}
	}

}



