package com.jaalee.examples.demos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * http://www.jaalee.com/
 * Jaalee, Inc.
 * This project is for developers, not for commercial purposes.
 * For the source codes which can be  used for commercial purposes, please contact us directly.
 * 
 * @author Alvin.Bert
 * Alvin.Bert.hu@gmail.com
 * 
 * service@jaalee.com
 */

public class DistanceBackgroundView extends View {

  private final Drawable drawable;

  public DistanceBackgroundView(Context context, AttributeSet attrs) {
    super(context, attrs);
    drawable = context.getResources().getDrawable(R.drawable.bg_distance);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    int width = drawable.getIntrinsicWidth() * canvas.getHeight() / drawable.getIntrinsicHeight();
    int deltaX = (width - canvas.getWidth()) / 2;
    drawable.setBounds(-deltaX, 0, width - deltaX, canvas.getHeight());
    drawable.draw(canvas);
  }
}
