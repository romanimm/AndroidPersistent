package ch.hslu.mobpro.thirdapp.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by roman on 16.03.2015.
 */
public class TeaPreferenceInitializer extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(getActivity().getApplicationContext(), R.xml.preferences, false);
    }

    public void resetSettings(){
        getPreferenceScreen().getEditor().clear().commit();
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Toast.makeText(getActivity().getApplicationContext(), "Changed key: " + key, Toast.LENGTH_SHORT).show();
    }
}
