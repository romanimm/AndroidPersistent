package ch.hslu.mobpro.thirdapp.app;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by roman on 16.03.2015.
 */
public class TeaPreferenceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new TeaPreferenceInitializer())
                .commit();
    }
}
