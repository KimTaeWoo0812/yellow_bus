package net.computeering.newschoolbus.LoginPackage;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.computeering.newschoolbus.CustomPreferences;
import net.computeering.newschoolbus.DataPackage.UserData;
import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.SchoolListPackage.SchoolListActivity;
import net.computeering.newschoolbus.TCP.ServerCheck;
import net.computeering.newschoolbus.TCP.TCP_SC;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    CustomPreferences LoginActivity_pref = new CustomPreferences(this);
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    String ID = "";
    String PW = "";

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        final CheckBox _autologinCheckbox = (CheckBox) findViewById(R.id.autoLogin_chkbox);


        /**
         * TODO :시스템이 강제로 블루투스를 켜기
         * If a user device turns off bluetooth, request to turn it on.
         **/
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        {
            if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON || mBluetoothAdapter.getState() == mBluetoothAdapter.STATE_ON) {
                //mBluetoothAdapter.disable();
            } else {
                mBluetoothAdapter.enable();
            }
        }
        Log.e("Seongho 5  ", " " + LoginActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, ""));
        Log.e("Seongho 5  ", " " + LoginActivity_pref.getValue(CustomPreferences.AUTOLOGIN_prevalue, ""));

        if (LoginActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, "").equals("")) {
            Log.e("LA_Seongho", "pref값 null인 신규이므로 회원가입 전환" + LoginActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, ""));
            Intent LA_forFirstUserGoSignupActivity_Intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(LA_forFirstUserGoSignupActivity_Intent);
        } else if(LoginActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, "").equals("FALSE") &&
                LoginActivity_pref.getValue(CustomPreferences.AUTOLOGIN_prevalue, "").equals("TRUE")) {
            Log.e("LA_Seongho", "신규는 아니고, 자동로그인이므로 로그인처리함! amifirst : " + LoginActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, "") + "autologin : " + LoginActivity_pref.getValue(CustomPreferences.AUTOLOGIN_prevalue, ""));
            _emailText.setText(LoginActivity_pref.getValue(ID, ""));
            _passwordText.setText(LoginActivity_pref.getValue(PW, ""));

            Log.e("LA_Seongho1", " " + LoginActivity_pref.getValue(CustomPreferences.EMAIL_prefvalue, ""));
            Log.e("LA_Seongho2"," "+LoginActivity_pref.getValue(CustomPreferences.PASSWORD_prefvalue, ""));


            ID =  LoginActivity_pref.getValue(CustomPreferences.EMAIL_prefvalue, "");
            PW = LoginActivity_pref.getValue(CustomPreferences.PASSWORD_prefvalue, "");
            _emailText.setText(ID);
            _passwordText.setText(PW);
            login();
        }else {
            Log.e("LA_Seongho", "무슨녀석일까 amifirst : "+ LoginActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, "")+"autologin : "+  LoginActivity_pref.getValue(CustomPreferences.AUTOLOGIN_prevalue, ""));
            _emailText.setText(LoginActivity_pref.getValue(CustomPreferences.EMAIL_prefvalue, ""));
            _passwordText.setText(LoginActivity_pref.getValue(CustomPreferences.PASSWORD_prefvalue, ""));
        }


        _loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ID = _emailText.getText().toString();
                PW = _passwordText.getText().toString();
                if (_autologinCheckbox.isChecked() == true) {
                    LoginActivity_pref.put(CustomPreferences.AMIFIRST_prefvalue, "FALSE");

                    LoginActivity_pref.put(CustomPreferences.AUTOLOGIN_prevalue, "TRUE");
                    Log.e("Seongho 3  ", " " + LoginActivity_pref.getValue(CustomPreferences.AUTOLOGIN_prevalue, ""));

                    LoginActivity_pref.put(CustomPreferences.EMAIL_prefvalue,ID);
                    LoginActivity_pref.put(CustomPreferences.PASSWORD_prefvalue,PW);
                    //LoginActivity_pref.getValue("PW","");
                }

                login_From_btn();
                //login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("Seongho", "LinkBtn Clicked!!!");
                LoginActivity_pref.put(CustomPreferences.AMIFIRST_prefvalue, "TRUE");
                Intent LA_GoToSignupActivity_Intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(LA_GoToSignupActivity_Intent);
                finish();

            }
        });


    }

    public void login_From_btn() {
        //서버에게 LA에 있는 아이디,비밀번호를 전송합니다.
        (new SetList()).execute(new String[]{"1"});
        ServerCheck.showLoading(this);
    }

    public void login() {
        // TODO: Implement your own authentication logic here.

        //서버에게 LA에 있는 아이디,비밀번호를 전송합니다.
        (new SetList()).execute(new String[]{"2"});
        ServerCheck.showLoading(this);

        // TODO: End of your own authentication logic here.

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        Intent LA_gotoSchoolListActivity_Intent = new Intent(LoginActivity.this, SchoolListActivity.class);
        startActivity(LA_gotoSchoolListActivity_Intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    //    TCP소켓 송수신부
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
                msg = "LOGIN" + TCP_SC._del + ID + TCP_SC._del + PW;

            else if (type.equals("2")) {
                msg = "LOGIN" + TCP_SC._del + ID + TCP_SC._del + PW;
            }

            TCP_SC.SendMsg(msg);
            Log.i("LA_Seongho:", "TCP_SC.SendMsg()" + msg);

            msg = TCP_SC.GetMsg();
            if(msg.equals("100"))
                return "100";
            Log.e("Log_test:  ", msg);
            ServerCheck.hideLoading();


            return "1";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.i("LA_Seongho:", "TCP_SC.GetMsg()" + msg);
            if (result.equals("1")) {
                String arrMsg[] = msg.split(TCP_SC._del);

                if (arrMsg[1].compareTo("0") == 0) {
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다!",
                            Toast.LENGTH_SHORT).show();
                    Log.i("LA_Seongho:", "TCP_SC.GetMsg() 알 수 없는 에러.");
                } else if (arrMsg[1].compareTo("1") == 0) {
                    UserData.name = arrMsg[2];
                    UserData.id = ID;
                    UserData.pw = PW;
                    Intent LA_gotoSignup_Intent = new Intent(LoginActivity.this, SchoolListActivity.class);
                    startActivityForResult(LA_gotoSignup_Intent, REQUEST_SIGNUP);
                    Log.i("LA_Seongho:", "TCP_SC.GetMsg() 로그인 성공.");
                    finish();
                }
                else if(result.compareTo("100") == 0){
                    Toast.makeText(LoginActivity.this, "연결이 불안정 합니다. 다시 시도해주세요!",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i("LA_Seongho:", "다른 예외처리");
                }
                //Log.i("받은 메시지:", msg);
            }
        }
    }

}
