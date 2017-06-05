package ys.grad.voicelockscreen;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends Activity {
String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragManager = getFragmentManager();
        LockScreenAuthFragment frag = new LockScreenAuthFragment();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_main);
        ImageView backgroundImageView = (ImageView) findViewById(R.id.image_view_lock_screen);
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        backgroundImageView.setImageDrawable(wallpaperDrawable);
        frameLayout.invalidate();

        fragManager.beginTransaction().replace(R.id.activity_main_fragment,frag).addToBackStack(null).commit();
    }
}
