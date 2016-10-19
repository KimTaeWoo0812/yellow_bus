package net.computeering.newschoolbus.SchoolManagePackage;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.TCP.ServerCheck;
import net.computeering.newschoolbus.TCP.TCP_SC;

public class SchoolMange_Activity extends AppCompatActivity {
    EditText notice;
    String text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_mange_);

        notice = (EditText) findViewById(R.id.notice);

        try {
            if (SchoolData.isNotice.equals("1"))
                notice.setText(SchoolData.notice);
        } catch (NullPointerException e){

        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_re_notice:// 공지 수정
                (new SetList()).execute(new String[]{"1"});
                text = notice.getText().toString();
                ServerCheck.showLoading(this);

                break;
            case R.id.btn_view_mamber:// 원생 관리
                (new SetList()).execute(new String[]{"2"});
                ServerCheck.showLoading(this);
                break;
            case R.id.btn_drive_car:// 원생 관리
                Intent intent1 = new Intent(SchoolMange_Activity.this, ChooseCar_Activity.class);
                startActivity(intent1);
                break;
        }
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
            if (type.compareTo("1") == 0) {

                msg = "M_NOTICE" + TCP_SC._del + SchoolData.schoolName + TCP_SC._del+ text + TCP_SC._del;
                TCP_SC.SendMsg(msg);

                msg = TCP_SC.GetMsg();
                if(msg.equals("100"))
                    return "100";
                ServerCheck.hideLoading();
//                String msgs[] = msg.split(TCP_SC._del);
//                if(msgs[1].equals("1"))
//                    //성공
//                else
//                    //실패

                return "100";
            }
            else if(type.compareTo("2") == 0) {
                msg = "M_VIEW_MEMBER" + TCP_SC._del + SchoolData.schoolName + TCP_SC._del;
                TCP_SC.SendMsg(msg);

                return "2";
            }

            return "100";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.compareTo("2") == 0) {
                ServerCheck.hideLoading();
                Intent intent1 = new Intent(SchoolMange_Activity.this, ViewMember_Activity.class);
                startActivity(intent1);

            }

        }
    }


}
