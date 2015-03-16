package ch.hslu.mobpro.thirdapp.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by roman on 16.03.2015.
 */
public class TeeSettings {

    /* Ich dachte das macht es etwas schöner... */
    
    private static final String preferenceFqn = TeeSettings.class.getCanonicalName();

    private static final String OPEN_COUNT = "OPEN_COUNT";

    private final SharedPreferences preferences;

    public TeeSettings(Context context) {
        preferences = context.getSharedPreferences(preferenceFqn, Context.MODE_PRIVATE);

    }

    public int getOpenCount() {
        return preferences.getInt(OPEN_COUNT, 0) + 1;
    }

    public void setOpenCount(int value) {
        preferences.edit().putInt(OPEN_COUNT,value).apply();
    }
}
