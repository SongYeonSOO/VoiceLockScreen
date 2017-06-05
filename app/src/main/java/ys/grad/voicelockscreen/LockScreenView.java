package ys.grad.voicelockscreen;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import android.widget.ImageView;
public class LockScreenView extends ImageView {

    public LockScreenView(Context context) {
        super(context);
    }

    public LockScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LockScreenView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void draw(Canvas canvas) {
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        setImageDrawable(wallpaperDrawable);
        super.draw(canvas);
    }
//change
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
