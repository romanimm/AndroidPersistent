package ch.hslu.mobpro.thirdapp.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;


public class MainActivity extends Activity {
    private TeeSettings teeSettings;
    private Context context;


    //nike.prelaj@stud.hslu.ch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        teeSettings = new TeeSettings(getApplicationContext());
        teeSettings.setOpenCount(teeSettings.getOpenCount());


        Button editTeePreferencesButton = (Button) findViewById(R.id.main_button_tee_preferences_edit);
        editTeePreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, TeaPreferenceActivity.class));
            }
        });

        Button resetTeePreferencesButton = (Button) findViewById(R.id.main_button_tee_set_default);
        resetTeePreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PreferenceManager.setDefaultValues(context,R.xml.preferences,true);
//                new TeaPreferenceInitializer().resetSettings();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                sharedPreferences.edit().clear().apply();
                updateText();
            }
        });

        Button saveButton = (Button) findViewById(R.id.main_button_fileSystem_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        Button loadButton = (Button) findViewById(R.id.main_button_fileSystem_load);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

    }

    private void load() {
        try {
            FileInputStream fileInputStream = new FileInputStream(getFile());
            StringBuilder fileContent = new StringBuilder("");

            byte[] buffer = new byte[1024];

            int n;
            while ((n = fileInputStream.read(buffer)) != -1)
            {
                fileContent.append(new String(buffer, 0, n));
            }
            ((EditText) findViewById(R.id.main_editText_fileSystem_input_text)).setText(fileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.wtf("Persistenz_main","damn!");
        }
    }

    private void save() {
        try {
            FileWriter fileWriter = new FileWriter(getFile());
            fileWriter.write(stringToSave());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.wtf("Persistenz_main","shit!");
        }
    }

    private String stringToSave() {
        return ((EditText) findViewById(R.id.main_editText_fileSystem_input_text)).getText().toString();
    }

    private File getFile() {
        String filename = "/myFile";
        File file;
        if ( ((CheckBox)findViewById(R.id.main_checkBox_extern)).isChecked()) {
            file =  new File(Environment.getExternalStorageDirectory(),filename);
        } else {
            file = new File(context.getFilesDir(), filename);
        }

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.wtf("Persistenz_main","bullshit!");
            }
        }

        return file;
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateText();
    }

    private void updateText() {
        ((TextView) findViewById(R.id.main_text_appPreferences_open))
                .setText(String.format(getString(R.string.main_text_appPreferences_open)
                        , teeSettings.getOpenCount()));

        TextView fav = (TextView) findViewById(R.id.main_text_appPreferences_fav);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPref.getBoolean("teaWithSugar", false)) {
            fav.setText(String.format(getString(R.string.main_text_appPreferences_fav),
                    sharedPref.getString("teaPreferred", getString(R.string.prefs_teaPreferred)),
                    sharedPref.getString("teaSweetener", "Rohrzucker")
            ));
        } else {
            fav.setText(String.format(getString(R.string.main_text_appPreferences_fav_noSuggar),
                    sharedPref.getString("teaPreferred", getString(R.string.prefs_teaPreferred))
            ));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
