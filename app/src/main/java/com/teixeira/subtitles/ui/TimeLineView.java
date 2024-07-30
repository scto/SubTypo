/*
 * This file is part of Subtiles.
 *
 * Subtitles is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Subtitles is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Subtitles.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.teixeira.subtitles.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.teixeira.subtitles.models.Subtitle;
import com.teixeira.subtitles.utils.VideoUtils;
import java.util.List;

/**
 * @author Felipe Teixeira
 */
public class TimeLineView extends View {

  private long videoDuration;
  private long currentVideoPosition;
  private List<Subtitle> subtitles;

  private Rect bounds;
  private Paint paint;

  public TimeLineView(Context context) {
    this(context, null);
  }

  public TimeLineView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    bounds = new Rect();
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    videoDuration = 0;
    currentVideoPosition = 0;
    subtitles = null;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    drawSubtitles(canvas);
    drawTimeLine(canvas);
    drawPositionHandler(canvas);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // TODO: Implement this method
    return super.onTouchEvent(event);
  }

  public void setVideoDuration(long duration) {
    this.videoDuration = duration;
    invalidate();
  }

  public void setCurrentVideoPosition(long currentVideoPosition) {
    this.currentVideoPosition = currentVideoPosition;
    invalidate();
  }

  public void setSubtitles(List<Subtitle> subtitles) {
    this.subtitles = subtitles;
    invalidate();
  }

  private void drawTimeLine(Canvas canvas) {

    paint.setColor(Color.WHITE);

    int width = canvas.getWidth();
    int height = canvas.getHeight();

    float centerX = height / 2;
    float centerY = height / 2;

    long seconds = videoDuration / 1000;
    for (int i = 0; i <= seconds; i++) {

      paint.setAlpha(90);

      float x = (float) i / seconds * width;
      float y = height - (height / 4);
      canvas.drawLine(x, y, x, height, paint);

      paint.setAlpha(100);

      if (i % 60 == 0) {
        float minuteX = (float) i / seconds * width;
        float minuteY = centerY;
        canvas.drawLine(minuteX, minuteY, minuteX, height, paint);
      }

      if (i % 3600 == 0) {
        float hourX = (float) i / seconds * width;
        float hourY = centerY;
        canvas.drawLine(hourX, hourY, hourX, height, paint);
      }
    }
  }

  private void drawPositionHandler(Canvas canvas) {

    paint.setColor(Color.GREEN);

    int width = canvas.getWidth();
    int height = canvas.getHeight();

    float x = (float) currentVideoPosition / videoDuration * width;
    canvas.drawLine(x, 0, x, height / 2, paint);

    float size = 8;
    Path path = new Path();
    path.moveTo(x, size);
    path.lineTo(x - size, 0);
    path.lineTo(x + size, 0);
    path.close();

    canvas.drawPath(path, paint);

    paint.setColor(Color.WHITE);
    paint.setTextSize(14);

    String currentVideoPositionText= VideoUtils.getTime(currentVideoPosition);
    paint.getTextBounds(currentVideoPositionText, 0, currentVideoPositionText.length(), bounds);
    canvas.drawText(currentVideoPositionText, x - (bounds.width() / 2), height / 2, paint);
  }

  private void drawSubtitles(Canvas canvas) {

    if (subtitles == null) {
      return;
    }

    int width = canvas.getWidth();
    int height = canvas.getHeight();

    paint.setColor(Color.YELLOW);
    paint.setAlpha(80);

    for (Subtitle subtitle : subtitles) {
      try {
        long startTime = VideoUtils.getMilliSeconds(subtitle.getStartTime());
        long endTime = VideoUtils.getMilliSeconds(subtitle.getEndTime());

        float left = (float) startTime / videoDuration * width;
        float top = 0;
        float right = (float) endTime / videoDuration * width;
        float bottom = height;

        canvas.drawRect(new RectF(left, top, right, bottom), paint);
      } catch (Exception e) {
        // Ignore
      }
    }
  }
}
