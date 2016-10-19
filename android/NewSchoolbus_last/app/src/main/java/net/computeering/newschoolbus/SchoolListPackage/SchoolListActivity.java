package net.computeering.newschoolbus.SchoolListPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.computeering.newschoolbus.CustomPreferences;
import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.DataPackage.UserData;
import net.computeering.newschoolbus.LoginPackage.LoginActivity;
import net.computeering.newschoolbus.MasterMainActivity;
import net.computeering.newschoolbus.R;
import net.computeering.newschoolbus.SchoolManagePackage.SchoolMange_Activity;
import net.computeering.newschoolbus.TCP.ServerCheck;
import net.computeering.newschoolbus.TCP.TCP_SC;

import java.util.ArrayList;
import java.util.Collections;

public class SchoolListActivity extends AppCompatActivity {
    CustomPreferences SchoolListActivity_pref = new CustomPreferences(this);
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    private TextView stickyView;
    private ListView listView;
    private View heroImageView;
    boolean return_back = true;
    private View stickyViewSpacer;

    private int MAX_ROWS = 20;

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(return_back) {
//            try {
//                TCP_SC.CloseChannel();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            android.os.Process.killProcess(android.os.Process.myPid());// 완전종료
//        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        mListView = new ListView(this);
         /* Initialise list view, hero image, and sticky view */
        listView = (ListView) findViewById(R.id.mList);
        heroImageView = findViewById(R.id.heroImageView);
        stickyView = (TextView) findViewById(R.id.stickyView);

        /* Inflate list header layout */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.school_list_list_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        /* Add list view header */
        listView.addHeaderView(listHeader);

        /* Handle list View scroll events */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/
                if (listView.getFirstVisiblePosition() == 0) {
                    View firstChild = listView.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int heroTopY = stickyViewSpacer.getTop();
                    stickyView.setY(Math.max(0, heroTopY + topY));

                    /* Set the image to scroll half of the amount that of ListView */
                    heroImageView.setY(topY * 0.9f);
                }
            }
        });

        mListView = (ListView) findViewById(R.id.mList);
        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ListData mData = mAdapter.mListData.get(position - 1);
                SchoolData.schoolName = mData.schoolName;
                SchoolData.role = mData.role;

                (new SetList()).execute(new String[]{"2"});
                ServerCheck.showLoading(SchoolListActivity.this);

//
//                Toast.makeText(SchoolListActivity.this, mData.schoolName + "으로 이동합니다", Toast.LENGTH_SHORT).show();
//                Intent SLA_GoToMasterMainActivity_Intent = new Intent(SchoolListActivity.this, MasterMainActivity.class);
//                startActivity(SLA_GoToMasterMainActivity_Intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schoollistactivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return_back = false;
            SchoolListActivity_pref.put(CustomPreferences.AUTOLOGIN_prevalue,"FALSE");
            SchoolListActivity_pref.put(CustomPreferences.AMIFIRST_prefvalue,"FALSE");
            Intent gobackLoginActivityIntent = new Intent(SchoolListActivity.this, LoginActivity.class);
            startActivity(gobackLoginActivityIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        (new SetList()).execute(new String[]{"1"});
        ServerCheck.showLoading(this);
        super.onResume();
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

                Log.e("SchoolList_test:  ", " 1 ");

                mAdapter = new ListViewAdapter(SchoolListActivity.this);

                Log.e("SchoolList_test:  ", " 1 ");

                String msg2 = "S_LIST" + TCP_SC._del + UserData.id + TCP_SC._del;


                Log.e("SchoolList_test:  ", " 1 ");

                Log.e("SchoolList_test:  ", msg2);
                Log.e("SchoolList_test:  ", TCP_SC._del);
                Log.e("SchoolList_test:  ", UserData.id);

                TCP_SC.SendMsg(msg2);

                for (; ; ) {
                    msg = TCP_SC.GetMsg();
                    if(msg.equals("100"))
                        return "100";
                    Log.e("11111:  ", msg);
                    boolean out = false;
                    String MSGS[] = msg.split(TCP_SC._endDel);
                    for (int i = 0; i < MSGS.length; i++) {
                        String msgs[] = MSGS[i].split(TCP_SC._del);

                        if(i==0) {
                            if (!msgs[1].equals("0")) {
                                Log.e("11111", msgs[1] + "  " + msgs[2]);
                                mAdapter.addItem(null,
                                        msgs[1],
                                        msgs[2]);
                            }
                            else{
                                out = true;
                            }
                        }
                        else{
                            if (!msgs[0].equals("0")) {
                                Log.e("11111", msgs[0] + "  " + msgs[1]);
                                mAdapter.addItem(null,
                                        msgs[0],
                                        msgs[1]);
                            }
                            else{
                                out = true;
                            }
                        }

                    }
                    if(out)
                        break;
                }
                ServerCheck.hideLoading();

                return "1";
            }
            if (type.compareTo("2") == 0) {
                msg = "S_INFO"+TCP_SC._del+ SchoolData.schoolName+TCP_SC._del;
                TCP_SC.SendMsg(msg);

                return "2";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.compareTo("1") == 0) {
                mListView.setAdapter(mAdapter);
            }
            if (result.compareTo("2") == 0) {
                Intent SLA_GoToMasterMainActivity_Intent = new Intent(SchoolListActivity.this, MasterMainActivity.class);
                startActivity(SLA_GoToMasterMainActivity_Intent);
            }
            else if(result.compareTo("100") == 0) {
                Toast.makeText(SchoolListActivity.this, "연결이 불안정 합니다. 다시 시도해주세요!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class ViewHolder {
        public ImageView mIcon;
        public TextView mText;
        public TextView mDate;
    }


    public class ListViewAdapter extends BaseAdapter {
        public Context mContext = null;
        public ArrayList<ListData> mListData = new ArrayList<ListData>();

        public void addItem(Drawable icon, String mTitle, String mRole){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
            addInfo.schoolName = mTitle;
            addInfo.role = mRole;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.school_list_list_row, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
                holder.mText = (TextView) convertView.findViewById(R.id.mText);
                holder.mDate = (TextView) convertView.findViewById(R.id.mDate);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);
            }else{
                holder.mIcon.setVisibility(View.GONE);
            }

            holder.mText.setText(mData.schoolName);
            holder.mDate.setText(mData.role);

            return convertView;
        }
    }
}
