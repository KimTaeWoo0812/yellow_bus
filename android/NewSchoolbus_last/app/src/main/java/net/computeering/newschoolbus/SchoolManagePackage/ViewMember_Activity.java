package net.computeering.newschoolbus.SchoolManagePackage;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.TCP.TCP_SC;

import java.util.ArrayList;

public class ViewMember_Activity extends AppCompatActivity {
    Context mContext = this;
    ListView listView;
    RelativeLayout layout;
    ArrayList<OneOfList> list = new ArrayList<OneOfList>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member_);

        listView = (ListView) findViewById(R.id.listView);
        layout = (RelativeLayout) this.findViewById(R.id.mainLayout);

        (new SetList()).execute(new String[]{"1"});

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
                for (; ; ) {
                    msg = TCP_SC.GetMsg();
                    if(msg.equals("100"))
                        return "100";
                    boolean out = false;
                    String MSGS[] = msg.split(TCP_SC._endDel);

                    for (int i = 0; i < MSGS.length; i++) {

                        String msgs[] = MSGS[i].split(TCP_SC._del);
                        OneOfList data = new OneOfList();

                        if(i==0) {
                            if (!msgs[1].equals("0")) {
                                data.userId = msgs[1];
                                data.userName = msgs[2];
                                data.role = msgs[3];
                                list.add(data);
                            } else {
                                out = true;
                            }
                        }
                        else{
                            if (!msgs[0].equals("0")) {
                                data.userId = msgs[0];
                                data.userName = msgs[1];
                                data.role = msgs[2];
                                list.add(data);
                            } else {
                                out = true;
                            }
                        }

                    }
                    if (out)
                        break;
                }
                return "1";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.compareTo("1") == 0) {
                ListAdapter adapter = new ListAdapter(mContext, 0,
                        list);
                listView.setAdapter(adapter);
                //list 를 이용해서 리스트뷰에 뛰워주면 된다.
            }
            if(result.compareTo("100") == 0){
                Toast.makeText(ViewMember_Activity.this, "연결이 불안정 합니다. 다시 시도해주세요!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    class OneOfList{
        String userId;
        String userName;
        String role;

    }
    private class ListAdapter extends ArrayAdapter<OneOfList> {

        public ListAdapter(Context context, int resource,
                           ArrayList<OneOfList> noticeData) {
            super(context, resource, noticeData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.activity_view_member_adapter,
                    null, true);


            TextView id = (TextView) rowView.findViewById(R.id.id);
            TextView name = (TextView) rowView.findViewById(R.id.name);
            TextView role = (TextView) rowView.findViewById(R.id.role);


            id.setText(list.get(position).userId);
            name.setText(list.get(position).userName);
            role.setText(list.get(position).role);

            return rowView;
            // return super.getView(position, convertView, parent);
        }
    }
}