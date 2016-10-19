/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package layout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import net.computeering.newschoolbus.DataPackage.SchoolData;
import net.computeering.newschoolbus.DataPackage.UserData;
import net.computeering.newschoolbus.TCP.ServerCheck;
import net.computeering.newschoolbus.TCP.TCP_SC;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A sample which shows how to use {@link SwipeRefreshLayout} within a
 * {@link android.support.v4.app.ListFragment} to add the 'swipe-to-refresh' gesture to a
 * {@link android.widget.ListView}. This is provided through the provided re-usable
 * {@link ActivityLogsListFragment} class.
 *
 * <p>To provide an accessible way to trigger the refresh, this app also provides a refresh
 * action item. This item should be displayed in the Action Bar's overflow item.
 *
 * <p>In this sample app, the refresh updates the ListView with a random set of new items.
 *
 * <p>This sample also provides the functionality to change the colors displayed in the
 * {@link SwipeRefreshLayout} through the options menu. This is meant to
 * showcase the use of color rather than being something that should be integrated into apps.
 */
public class ActivityLogsListFragmentFragment extends ActivityLogsListFragment {

    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> data2 = new ArrayList<>();
    ListAdapter adapter;
    public boolean isFirst = true;
    //private static final int LIST_ITEM_COUNT = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Notify the system to allow an options menu for this fragment.
        setHasOptionsMenu(true);
    }

    // BEGIN_INCLUDE (setup_views)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Create an ArrayAdapter to contain the data for the ListView. Each item in the ListView
         * uses the system-defined simple_list_item_1 layout that contains one TextView.
         */

        new SetList().execute();




        // BEGIN_INCLUDE (setup_refreshlistener)
        /**
         * Implement {@link SwipeRefreshLayout.OnRefreshListener}. When users do the "swipe to
         * refresh" gesture, SwipeRefreshLayout invokes
         * {@link SwipeRefreshLayout.OnRefreshListener#onRefresh onRefresh()}. In
         * {@link SwipeRefreshLayout.OnRefreshListener#onRefresh onRefresh()}, call a method that
         * refreshes the content. Call the same method in response to the Refresh action from the
         * action bar.
         */
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });
        // END_INCLUDE (setup_refreshlistener)
    }
    // END_INCLUDE (setup_views)


    // BEGIN_INCLUDE (setup_refresh_menu_listener)
    /**
     * Respond to the user's selection of the Refresh action item. Start the SwipeRefreshLayout
     * progress bar, then initiate the background task that refreshes the content.
     *
     * <p>A color scheme menu item used for demonstrating the use of SwipeRefreshLayout's color
     * scheme functionality. This kind of menu item should not be incorporated into your app,
     * it just to demonstrate the use of color. Instead you should choose a color scheme based
     * off of your application's branding.
     */

    // END_INCLUDE (setup_refresh_menu_listener)

    // BEGIN_INCLUDE (initiate_refresh)
    /**
     * By abstracting the refresh process to a single method, the app allows both the
     * SwipeGestureLayout onRefresh() method and the Refresh action item to refresh the content.
     */
    private void initiateRefresh() {
        Log.i("Seongho", "initiateRefresh");

        /**
         * Execute the background task, which uses {@link AsyncTask} to load the data.
         */
        new SetList().execute();
    }
    // END_INCLUDE (initiate_refresh)

    // BEGIN_INCLUDE (refresh_complete)
    /**
     * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
     * ListAdapter and turns off the progress bar.
     */
    //새로고침이 완료되었을 떄
    private void onRefreshComplete(List<String> result) {
        Log.i("Seongho", "onRefreshComplete");

        // Remove all items from the ListAdapter, and then replace them with the new items
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        adapter.clear();
        for (String cheese : result) {
            adapter.add(cheese);
        }

        // Stop the refreshing indicator
        setRefreshing(false);
    }
    // END_INCLUDE (refresh_complete)

    /**
     * Dummy {@link AsyncTask} which simulates a long running task to fetch new cheeses.
     */

    //새로고치는 로직
    private class SetList extends AsyncTask<String, Void, String> {
        String msg = "";

        @Override
        protected String doInBackground(String... params) {
            data.clear();
            // Sleep for a small amount of time to simulate a background-task
            if (SchoolData.role.equals("학생"))
                msg = "L_LIST_TO_S" + TCP_SC._del + UserData.id + TCP_SC._del;
            else if (SchoolData.role.equals("학부모"))
                msg = "L_LIST_TO_P" + TCP_SC._del + UserData.id + TCP_SC._del;
            else {

                Log.e("LogFragment:  ", "들어옴 2 return");
                return null;
            }
            TCP_SC.SendMsg(msg);

            for (; ; ) {
                msg = TCP_SC.GetMsg();
                if (msg.equals("100"))
                    return "100";
                Log.e("Log: ", "받음  " + msg);
                boolean out = false;


                String MSGS[] = msg.split(TCP_SC._endDel);

                for (int i = 0; i < MSGS.length; i++) {
                    String msgs[] = MSGS[i].split(TCP_SC._del);
                    Log.e("Log: ", "나옴1  "+MSGS[i]);
                    if (i == 0) {
                        if (!msgs[1].equals("0")) {
                            data.add(msgs[1] + "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + msgs[2]);
                        } else {
                            out = true;
                        }
                    } else {
                        if (!msgs[0].equals("0")) {
                            data.add(msgs[0] + "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + msgs[1]);
                        } else {
                            out = true;
                        }
                    }

                }
                if (out)
                    break;

            }
            for (; ; ) {
                msg = TCP_SC.GetMsg();
                if (msg.equals("100"))
                    return "100";
                Log.e("Log: ", "받음  " + msg);
                boolean out = false;


                String MSGS[] = msg.split(TCP_SC._endDel);

                for (int i = 0; i < MSGS.length; i++) {
                    String msgs[] = MSGS[i].split(TCP_SC._del);
                    Log.e("Log: ", "나옴2  "+MSGS[i]);
                    if (!msgs[0].equals("0")) {
                        data.add(msgs[0] + "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + msgs[1]);
                    } else {
                        out = true;
                    }
                }
                if (out)
                    break;

            }
//            Log.e("Log: ", "나옴1  ");
//            for(int i = data.size()-1;i>=0;i--){
//
//                Log.e("Log_testtt: ", "  "+data.get(i));
//            }
            for(int i = data.size()-1;i>=0;i--){
                data2.add(data.get(i));
            }
//            for(int i = 0;i<data.size();i++){
//
//                Log.e("Log_testtt2: ", "  " + data.get(i));
//            }
//            for(int i = 0;i<data2.size();i++){
//
//                Log.e("Log_testtt3: ", "  " + data2.get(i));
//            }
            // Return a new random list of cheeses
            return "1";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            //onRefreshComplete(result);

            //if(isFirst) {
            if (result.compareTo("1") == 0) {
                adapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        data2);

                // Set the adapter between the ListView and its backing data.
                setListAdapter(adapter);
                //}
                Log.e("Log: ", "asddddddddddddddddddddddd");
                //setListAdapter(adapter);

                Log.e("Log: ", "나옴2");

            } else if (result.compareTo("100") == 0) {
                Toast.makeText(getContext(), "연결이 불안정 합니다. 다시 시도해주세요!",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

}
