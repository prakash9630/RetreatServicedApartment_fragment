package com.example.prakash.RetreatServicedApartment.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by prakash on 4/27/2017.
 */

public class RoundRectCornerImageView extends android.support.v7.widget.AppCompatImageView {

    private float radius = 30.0f;
    private Path path;
    private RectF rect;

    public RoundRectCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundRectCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundRectCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}