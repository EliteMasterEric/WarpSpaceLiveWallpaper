package com.mastereric.warpspacelivewallpaper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

public class WarpSpacePreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("WarpSpaceWallpaper", "Preferences.onCreate");
        // Display the fragment as the main content.
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        PrefsFragment mPrefsFragment = new PrefsFragment();
        mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
        mFragmentTransaction.commit();

    }

    public static class PrefsFragment extends PreferenceFragment {
        public String source_link = "http://www.reddit.com/r/stevenuniverse/comments/37z4zk/i_just_digitally_redrew_the_warp_space_from_warp/";
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.w("WarpSpaceWallpaper", "PreferencesFragment.onCreate");
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            Preference button = (Preference)findPreference("source_button");
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) { try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(source_link));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                return true;

                }
            });
        }
    }
}