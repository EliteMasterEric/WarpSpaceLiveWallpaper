package com.mastereric.warpspacelivewallpaper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        public String github_link = "https://github.com/MasterEric/WarpSpaceLiveWallpaper";
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.w("WarpSpaceWallpaper", "PreferencesFragment.onCreate");
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            Preference buttonEnable = findPreference("enable_button");
            buttonEnable.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    onClickEnable();
                    return true;
                }
            });


            Preference buttonGithub = findPreference("github_button");
            buttonGithub.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(github_link));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });

            Preference buttonSource = findPreference("source_button");
            buttonSource.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(source_link));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) { e.printStackTrace(); }
                return true;
                }
            });
        }
        public void onClickEnable() {
            Log.w("WarpSpaceWallpaper", "Button.onClick");
            Intent i = new Intent();

            Log.w("WarpSpaceWallpaper", "SettingIntent");
            if(Build.VERSION.SDK_INT > 15){
                i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            /*
            String p = WarpSpaceWallpaperService.class.getPackage().getName();
            Log.w("WarpSpaceWallpaper", "Package Name:"+p);
            String c = WarpSpaceWallpaperService.class.getCanonicalName();
            Log.w("WarpSpaceWallpaper", "Canonical Name:"+c);
            i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(p, c));
            */
                PackageManager pm = getActivity().getPackageManager();
                Intent intent_a = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
                        .putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                new ComponentName(getActivity(), WarpSpaceWallpaperService.class));
                Intent intent_b = new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);

                if(pm.resolveActivity(intent_a, 0) != null) {
                    Log.w("WarpSpaceWallpaper", "IntentA");
                    i = intent_a;
                }
                else if(pm.resolveActivity(intent_b, 0) != null) {
                    Log.w("WarpSpaceWallpaper", "IntentB");
                    i = intent_b;
                }
                else {
                    Log.w("WarpSpaceWallpaper", "IntentX");
                    i = Intent.createChooser(new Intent(Intent.ACTION_SET_WALLPAPER), "Test");
                }
            }
            else{
                Log.w("WarpSpaceWallpaper", "IntentElse:");
                i.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
            }
            Log.w("WarpSpaceWallpaper", "DoneIntentSet");
            try {
                Log.w("WarpSpaceWallpaper", "StartingActivity");
                startActivity(i);
                Log.w("WarpSpaceWallpaper", "AfterStartingActivity");
            } catch (Exception e) {
                Log.w("WarpSpaceWallpaper", "ActivityError:" + e.getMessage());
                e.printStackTrace();
            }
            Log.w("WarpSpaceWallpaper", "AfterTryBlock");
        }
    }
}