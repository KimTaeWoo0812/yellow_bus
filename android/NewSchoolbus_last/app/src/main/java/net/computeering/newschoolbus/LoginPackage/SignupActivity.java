package net.computeering.newschoolbus.LoginPackage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.computeering.newschoolbus.CustomPreferences;
import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.TCP.ServerCheck;
import net.computeering.newschoolbus.TCP.TCP_SC;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    CustomPreferences SignupActivity_pref = new CustomPreferences(this);

    int isChecked = 0;
    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    Button _signupButton;
    Button _idCheck;
    TextView _loginLink;

    String temp_for_send_msg_nameText;
    String temp_for_send_msg_emailText;
    String temp_for_send_msg_passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _idCheck = (Button) findViewById(R.id.btn_idCheck);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);
        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked == 0) {
                    Toast.makeText(SignupActivity.this, "아이디 중복확인을 해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                temp_for_send_msg_nameText = _nameText.getText().toString();
                temp_for_send_msg_emailText = _emailText.getText().toString();
                temp_for_send_msg_passwordText = _passwordText.getText().toString();
                SignupActivity_pref.put(CustomPreferences.AUTOLOGIN_prevalue, "TRUE");
                SignupActivity_pref.put(CustomPreferences.AMIFIRST_prefvalue, "FALSE");
                signup();
            }
        });
        _idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_for_send_msg_emailText = _emailText.getText().toString();
                Log.e("SA_Seongho", "중복체크부분에서 구글메서드반환값 : " + android.util.Patterns.EMAIL_ADDRESS.matcher(temp_for_send_msg_emailText).matches());
                if (!temp_for_send_msg_emailText.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(temp_for_send_msg_emailText).matches()) {
                    IdCheck();
                } else {
                    Toast.makeText(SignupActivity.this, "아이디 형식이 맞지 않습니다!",
                            Toast.LENGTH_SHORT).show();
                }

                Log.e("SA_Seongho", "중복체크 온클릭");

            }
        });
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                SignupActivity_pref.put(CustomPreferences.AMIFIRST_prefvalue, "TRUE");
                Intent SA_GoToLogin_Intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(SA_GoToLogin_Intent);
                finish();
            }
        });
    }

    public void IdCheck() {
        (new SetList()).execute(new String[]{"2"});
        ServerCheck.showLoading(this);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        //서버에게 LA에 있는 아이디,비밀번호,이름을 전송합니다.
        (new SetList()).execute(new String[]{"1"});
        ServerCheck.showLoading(this);

        // TODO: End of your own signup logic here.
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }


    public void onSignupSuccess() {
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        SignupActivity_pref.put(CustomPreferences.AMIFIRST_prefvalue, "FALSE");
        Log.e("SA_Seongho", "AMIFIRST_prefvalue 저장된 값 : " + SignupActivity_pref.getValue(CustomPreferences.AMIFIRST_prefvalue, null));

        SignupActivity_pref.put(CustomPreferences.NAME_prefvalue, name);
        Log.e("SA_Seongho", "NAME_prefvalue 저장된 값 : " + SignupActivity_pref.getValue(CustomPreferences.NAME_prefvalue, null));

        SignupActivity_pref.put(CustomPreferences.EMAIL_prefvalue, email);
        Log.e("SA_Seongho", "EMAIL_prefvalue 저장된 값 : " + SignupActivity_pref.getValue(CustomPreferences.EMAIL_prefvalue, null));

        SignupActivity_pref.put(CustomPreferences.PASSWORD_prefvalue, password);
        Log.e("SA_Seongho", "PASSWORD_prefvalue 저장된 값 : " + SignupActivity_pref.getValue(CustomPreferences.PASSWORD_prefvalue, null));
        _signupButton.setEnabled(true);

        Toast toast = Toast.makeText(getApplicationContext(),
                "회원가입이 성공적으로 완료되었으며, 자동로그인 됩니다.", Toast.LENGTH_SHORT);
        toast.show();
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

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
            if (type.compareTo("1") == 0) {
                msg = "JOINUS" + TCP_SC._del
                        + temp_for_send_msg_emailText + TCP_SC._del
                        + temp_for_send_msg_passwordText + TCP_SC._del
                        + temp_for_send_msg_nameText + TCP_SC._del;
                TCP_SC.SendMsg(msg);
                Log.i("SA_Seongho:", "TCP_SC.SendMsg()" + msg);

                msg = TCP_SC.GetMsg();
                if(msg.equals("100"))
                    return "100";
                ServerCheck.hideLoading();


                return "1";

            } else if (type.compareTo("2") == 0) {
                msg = "IDCHECK" + TCP_SC._del + temp_for_send_msg_emailText + TCP_SC._del;
                Log.e("TCP_Send: ", " 다음과같이 보낼 예정 : " + msg);
                TCP_SC.SendMsg(msg);

                msg = TCP_SC.GetMsg();
                if(msg.equals("100"))
                    return "100";
                ServerCheck.hideLoading();


                return "2";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("SA_Seongho", "회원가입 버튼 누르고 받아오는 result : " + result);
            Log.e("SA_Seongho", "회원가입 버튼 누르고 받아오는 msg : " + msg);
            if (result.compareTo("1") == 0) {

                String arrMsg[] = msg.split(TCP_SC._del);

                if (arrMsg[1].equals("1")) {
                    onSignupSuccess();
                } else {
                    Toast.makeText(SignupActivity.this, "회원가입 실패! 다시 시도해 주세요.",
                            Toast.LENGTH_SHORT).show();
                }
            } else if (result.compareTo("2") == 0) {
                String arrMsg[] = msg.split(TCP_SC._del);
                Log.e("test", "아이디 체크2: " + msg);
                Log.e("test", "아이디 체크: " + arrMsg[1].compareTo("1"));
                Log.e("test", "아이디 체크2: " + arrMsg[1]);


                if (arrMsg[1].compareTo("1") == 0) {
                    isChecked = 1;
                    Toast.makeText(SignupActivity.this, "아이디 사용 가능!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    isChecked = 0;
                    Toast.makeText(SignupActivity.this, "아이디 중복!",
                            Toast.LENGTH_SHORT).show();
                }
            } else if(result.compareTo("100") == 0){
                Toast.makeText(SignupActivity.this, "연결이 불안정 합니다. 다시 시도해주세요!",
                        Toast.LENGTH_SHORT).show();
            }


        }
    }
}
