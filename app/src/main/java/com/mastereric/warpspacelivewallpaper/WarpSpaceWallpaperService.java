package com.mastereric.warpspacelivewallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Calendar;

public class WarpSpaceWallpaperService extends WallpaperService {

    public void onCreate() {
        super.onCreate();
        Log.w("WarpSpaceWallpaper", "WallpaperService.onCreate");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.w("WarpSpaceWallpaper", "WallpaperService.onDestroy");
    }

    public Engine onCreateEngine() {
        Log.w("WarpSpaceWallpaper", "WallpaperService.onCreateEngine");
        return new WarpSpaceWallpaperEngine();
    }

    class WarpSpaceWallpaperEngine extends Engine {
        private final Handler handler = new Handler();
        private boolean visible = true;
        public Bitmap imageNoLightning;
        public Bitmap imageTopLightning;
        public Bitmap imageBottomLightning;
        public Bitmap imageDualLightning;

        public float lastxOffset;
        public float lastyOffset;
        public float lastxStep;
        public float lastyStep;
        //Percentage out of 100.
        public float lastxPixels;
        public float lastyPixels;

        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        WarpSpaceWallpaperEngine() {
            Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine");
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine.onCreate");
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine.onVisiblityChanged");
            this.visible = visible;
            // don't draw if the wallpaper is in the background
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine.onSurfaceDestroyed");
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) {
            Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine.onOffsetsChanged");
            updateVars(xOffset, yOffset, xStep, yStep, xPixels, yPixels);
            draw();
        }

        void updateVars(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) {
            lastxOffset = xOffset;
            lastxStep = xStep;
            lastxPixels = xPixels;
            lastyOffset = yOffset;
            lastyStep = yStep;
            lastyPixels = yPixels;
        }
        void draw() {
            final SurfaceHolder holder = getSurfaceHolder();

            Canvas c = null;
            try {
                c = holder.lockCanvas();
                // clear the canvas
                c.drawColor(Color.BLACK);
                switch((int)Math.round(Math.random() * 20)) {
                    case 1:
                        //(lastxOffset+-+
                        imageDualLightning = BitmapFactory.decodeResource(getResources(), com.mastereric.warpspacelivewallpaper.R.drawable.duallightningc);
                        c.drawBitmap(scaleCenterCrop(imageDualLightning, c.getHeight(), (int)(c.getHeight()*1.777), (int)((c.getHeight()*1.777-c.getWidth())*(lastxOffset))), 0, 0, null);
                        Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine.draw" + c.getWidth() + "-" + c.getHeight() + "(" +(c.getHeight()*1.777-c.getWidth())+") -" + (int) ((c.getHeight() * 1.777 - c.getWidth()) * (Math.abs(lastxPixels) / 100)) + " ("+(Math.abs(lastxPixels)/ 100)+")");
                        imageDualLightning.recycle();
                        break;
                    case 2:
                        imageBottomLightning = BitmapFactory.decodeResource(getResources(), com.mastereric.warpspacelivewallpaper.R.drawable.bottomlightningc);
                        c.drawBitmap(scaleCenterCrop(imageBottomLightning, c.getHeight(), (int)(c.getHeight()*1.777), (int)((c.getHeight()*1.777-c.getWidth())*(lastxOffset))), 0, 0, null);
                        Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine.draw" + c.getWidth() + "-" + c.getHeight() + "(" +(c.getHeight()*1.777-c.getWidth())+") -" + (int) ((c.getHeight() * 1.777 - c.getWidth()) * (Math.abs(lastxPixels) / 100)) + " ("+(Math.abs(lastxPixels)/ 100)+")");
                        imageBottomLightning.recycle();
                        break;
                    case 3:
                        imageTopLightning = BitmapFactory.decodeResource(getResources(), com.mastereric.warpspacelivewallpaper.R.drawable.toplightningc);
                        c.drawBitmap(scaleCenterCrop(imageTopLightning, c.getHeight(), (int)(c.getHeight()*1.777), (int)((c.getHeight()*1.777-c.getWidth())*(lastxOffset))), 0, 0, null);
                        Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine.draw" + c.getWidth() + "-" + c.getHeight() + "(" +(c.getHeight()*1.777-c.getWidth())+") -" + (int) ((c.getHeight() * 1.777 - c.getWidth()) * (Math.abs(lastxPixels) / 100)) + " ("+(Math.abs(lastxPixels)/ 100)+")");
                        imageTopLightning.recycle();
                        break;
                    default:
                        imageNoLightning = BitmapFactory.decodeResource(getResources(), com.mastereric.warpspacelivewallpaper.R.drawable.nolightningc);
                        c.drawBitmap(scaleCenterCrop(imageNoLightning, c.getHeight(), (int) (c.getHeight() * 1.777), (int)((c.getHeight()*1.777-c.getWidth())*(lastxOffset))), 0, 0, null);
                        Log.w("WarpSpaceWallpaper", "WarpSpaceWallpaperEngine.draw" + c.getWidth() + "-" + c.getHeight() + "(" +(c.getHeight()*1.777-c.getWidth())+") -" + (int) ((c.getHeight() * 1.777 - c.getWidth()) * (Math.abs(lastxPixels) / 100)) + " ("+(Math.abs(lastxPixels)/ 100)+")");
                        imageNoLightning.recycle();
                        break;
                }
            } finally {
                if (c != null)
                    holder.unlockCanvasAndPost(c);
            }

            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, 100); //every 10 milliseconds
            }
        }
    }
    public static Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth, int offset) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left-offset, top, left + scaledWidth-offset, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }
}
