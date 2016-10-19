package net.computeering.newschoolbus.SplashPackage;

/**
 * Created by Karthik's on 27-02-2016.
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.computeering.newschoolbus.CustomPreferences;
import net.computeering.newschoolbus.DataPackage.UserData;
import net.computeering.newschoolbus.LoginPackage.LoginActivity;
import net.computeering.newschoolbus.LoginPackage.SignupActivity;
import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.SchoolListPackage.SchoolListActivity;
import net.computeering.newschoolbus.TCP.AndroidSocket;
import net.computeering.newschoolbus.TCP.ServerCheck;
import net.computeering.newschoolbus.TCP.TCP_SC;

public class SplashActivity extends Activity {
    CustomPreferences SplashActivity_pref = new CustomPreferences(this);
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    String ID = "";
    String PW = "";

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3500;
    private KenBurnsView mKenBurns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setAnimation();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mKenBurns.setImageResource(R.drawable.splash_background);

        /**
         * TODO :TCP_main() 소켓 열기
         **/

        AndroidSocket._shared = null;
        TCP_SC.SetData();




        Log.i("LA_Seongho:", "TCP_main()소켓을 열었습니다.");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                ServerCheck.showLoading(SplashActivity.this);
                (new SetList()).execute(new String[]{"1"});
            }
        }, SPLASH_TIME_OUT);
    }

    private void setAnimation() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(2000);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(2000);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(100);
        animatorSet.start();

        findViewById(R.id.imagelogo).setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        findViewById(R.id.imagelogo).startAnimation(anim);
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
            if (type.equals("1")) {
//                try {
//                    Thread.sleep(2700);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TCP_SC.SendMsg("RODING"+TCP_SC._del);
                String msg = TCP_SC.GetMsg();

                if(!msg.equals("100")){

                    return "1";
                }

            }
            return "100";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ServerCheck.hideLoading();
            if (result.equals("1")) {
                if (SplashActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, "").equals("")) {
                    Log.e("LA_Seongho", "getSharedPreferences값이 비었으므로 회원가입 액티비티로 전환합니다 내용 :" + SplashActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, ""));
                    Intent SA_forFirstUserGoSignupActivity_Intent = new Intent(SplashActivity.this, SignupActivity.class);
                    startActivity(SA_forFirstUserGoSignupActivity_Intent);
                } else {
                    Intent SA_forAlreadyUserGoLoginActivity_Intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(SA_forAlreadyUserGoLoginActivity_Intent);
                    Log.e("LA_Seongho", "이미 가입된 회원이므로 로그인 액티비티로 전환합니다.");
                }
                finish();
            }
            else{
                DialogSimple();
            }
        }
    }
    public void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
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

                        Intent intent5 = new Intent(SplashActivity.this,
                                SplashActivity.class);
                        startActivity(intent5);
                        finish();// 이 화면 종료

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