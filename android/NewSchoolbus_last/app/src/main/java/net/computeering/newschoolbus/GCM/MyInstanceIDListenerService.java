package net.computeering.newschoolbus.GCM;

/**
 * Created by Seongho on 2016-03-28.
 */
import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
import net.computeering.newschoolbus.GCM.*;

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDLS";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}