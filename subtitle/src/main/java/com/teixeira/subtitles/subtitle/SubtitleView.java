/*
 * This file is part of SubTypo.
 *
 * SubTypo is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SubTypo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with SubTypo.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.teixeira.subtitles.subtitle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.teixeira.subtitles.subtitle.models.Paragraph;

/**
 * @author Felipe Teixeira
 */
public class SubtitleView extends View {

  private TextPaint textPaint;
  private Paragraph paragraph;

  public SubtitleView(Context context) {
    this(context, null);
  }

  public SubtitleView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SubtitleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    textPaint = new TextPaint();
    textPaint.setAntiAlias(true);
    textPaint.setTextSize(35);
    textPaint.setColor(Color.WHITE);
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);

    drawText(canvas);
  }

  public void setParagraph(@Nullable Paragraph paragraph) {
    this.paragraph = paragraph;
    invalidate();
  }

  private void drawText(Canvas canvas) {

    if (paragraph == null) {
      return;
    }

    Spanned text;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      text = Html.fromHtml(paragraph.getText().replaceAll("\n", "<br/>"), Html.FROM_HTML_MODE_LEGACY);
    } else {
      text = Html.fromHtml(paragraph.getText().replaceAll("\n", "<br/>"));
    }

    SpannableString span = new SpannableString(text);
    span.setSpan(
        new BackgroundColorSpan(Color.BLACK), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

    StaticLayout staticLayout =
        StaticLayout.Builder.obtain(span, 0, span.length(), textPaint, canvas.getWidth())
            .setAlignment(Layout.Alignment.ALIGN_CENTER)
            .build();

    canvas.save();
    canvas.translate(0, (getHeight() - 60) - staticLayout.getHeight());
    staticLayout.draw(canvas);
    canvas.restore();
  }

  String formatParagraphText(String text) {
    String[] lines = text.split("\n");
    for (String line : lines) {
      
    }
    return text;
  }
}
