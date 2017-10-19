package com.rramos.mypersonalapp2.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rramos.mypersonalapp2.R;

public class MyPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Log.d("settings", "preference changed: " + s);
            if("fullname".equals(s)){
                Log.d("settings", "new value for username: " + sharedPreferences.getString(s, null));
                String actualizar = sharedPreferences.getString(s, null);
                boolean success = editor
                        .putString("fullnames", actualizar)
                        .commit();
            }else if("applicationUpdates".equals(s)){
                Log.d("settings", "new value for applicationUpdates: " + sharedPreferences.getBoolean(s, false));
            }else if("downloadType".equals(s)){
                Log.d("settings", "new value for downloadType: " + sharedPreferences.getString(s, null));
                String actualizar = sharedPreferences.getString(s, null);
                boolean succes = editor
                        .putString("fonttexto", actualizar)
                        .commit();

            }else if ("tipoestilo".equals(s)){
                Log.d("settings", "Nuevo estilo " + sharedPreferences.getString(s, null));
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

    }
}
