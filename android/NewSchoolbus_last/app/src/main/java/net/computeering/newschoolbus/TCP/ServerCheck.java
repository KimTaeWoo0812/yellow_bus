package net.computeering.newschoolbus.TCP;//package net.computeering.newschoolbus.TCP;

import android.app.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.computeering.newschoolbus.R;


public class ServerCheck {

	private static Handler mHandler;
	private static Runnable mRunnable;
	private static android.app.ProgressDialog m_loadingDialog = null;

	public volatile static boolean canNotGet = false;
	static Context baseContext;

	public static boolean flag_forServer = false;

	public static void showLoading(final Context context){
		baseContext = context;
		if (m_loadingDialog == null) {

			Log.e("test", "con: " + context);
			m_loadingDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);

			m_loadingDialog.setIndeterminate(true);
			m_loadingDialog.setMessage("데이터 수신 중...");

		}
		flag_forServer = true;
		m_loadingDialog.show();
		currentActivity(context);

	}

	public static void hideLoading() {

		if (m_loadingDialog != null) {
			m_loadingDialog.dismiss();
			m_loadingDialog = null;
		}
	}

	public static void currentActivity(final Context mContext) {
		mRunnable = new Runnable() {
			@Override
			public void run() {

				if (m_loadingDialog != null) {
					if (flag_forServer) {
						Toast toast = Toast.makeText(mContext,
								"서버와 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT);
						toast.show();

						canNotGet = true;
						m_loadingDialog.dismiss();
						m_loadingDialog = null;
					}
				}
			}
		};

		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, 8000);

	}

	public void DialogSimple(){
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(baseContext);
		alt_bld.setMessage("다시 시도하시겠습니까?").setCancelable(
				false).setPositiveButton("취소",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do something
						android.os.Process.killProcess(android.os.Process.myPid());// 완전종료
						dialog.cancel();
					}
				}).setNegativeButton("다시 시도",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle("서버와 연결 실패");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.logo);
		alert.show();
	}
}

//  ServerCheck servercheck;
// servercheck.showdialog(this);
// servercheck.hideLoading();

// <style name="TransDialog2">
// <item name="android:windowFrame">@null</item>
// <item name="android:windowIsFloating">true</item>
// <item name="android:windowContentOverlay">@null</item>
// <item name="android:windowTitleStyle">@null</item>
// <item
// name="android:windowAnimationStyle">@+android:style/Animation.Dialog</item>
// <item name="android:windowBackground">@+android:color/transparent</item>
// </style>

// values�� styles.xml
