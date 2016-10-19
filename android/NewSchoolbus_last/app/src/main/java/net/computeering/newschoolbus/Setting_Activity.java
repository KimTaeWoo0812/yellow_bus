package net.computeering.newschoolbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;

public class Setting_Activity extends AppCompatActivity {
    CheckBox checkBox;
    CustomPreferences pref = new CustomPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);


        checkBox = (CheckBox) findViewById(R.id.chkbox);

        if(pref.getValue(CustomPreferences.Bluetooth_prefvalue,"").equals("TRUE")){
            checkBox.setChecked(true);
        }
        else if(pref.getValue(CustomPreferences.Bluetooth_prefvalue,"").equals("FALSE")){
            checkBox.setChecked(false);
        }
    }



    @Override
    public void onPause(){
        super.onPause();

        if(checkBox.isChecked() == true){
            pref.put(CustomPreferences.Bluetooth_prefvalue, "TRUE");
        }
        else{
            pref.put(CustomPreferences.Bluetooth_prefvalue, "FALSE");
        }

    }

}
