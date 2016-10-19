package net.computeering.newschoolbus.SchoolManagePackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.MasterMainActivity;
import net.computeering.newschoolbus.R;

import java.util.ArrayList;

public class ChooseCar_Activity extends AppCompatActivity {
    Context mContext = this;
    ListView listView;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car_);

        listView = (ListView) findViewById(R.id.listView);
        layout = (RelativeLayout) this.findViewById(R.id.mainLayout);


        ListAdapter adapter = new ListAdapter(mContext, 0,SchoolData.carList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //Intent intent1 = new Intent(ChooseCar_Activity.this, Map_Running_car_Activity.class)
                Intent intent1 = new Intent(ChooseCar_Activity.this, Map_Running_car_Activity.class);;
                intent1.putExtra("carName",SchoolData.carList.get(position));

                startActivity(intent1);

            }
        });

    }







    private class ListAdapter extends ArrayAdapter<String> {

        public ListAdapter(Context context, int resource,
                           ArrayList<String> noticeData) {
            super(context, resource, noticeData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.activity_choose_car_adapter,
                    null, true);


            TextView carName = (TextView) rowView.findViewById(R.id.carName);
            carName.setText(SchoolData.carList.get(position));

            return rowView;
            // return super.getView(position, convertView, parent);
        }
    }
}
