package com.mastereric.warpspacelivewallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WarpSpaceSetter extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        Button enable = (Button) findViewById(R.id.button);
        enable.setOnClickListener(this);
    }

    public void onClick(View view) {
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
            PackageManager pm = getPackageManager();
            Intent intent_a = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
                            .putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                    new ComponentName(this, WarpSpaceWallpaperService.class));
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
            Log.w("WarpSpaceWallpaper", "ActivityError:"+e.getMessage());
            e.printStackTrace();
        }
        Log.w("WarpSpaceWallpaper", "AfterTryBlock");
    }
} 