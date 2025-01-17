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

package com.teixeira.subtitles.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.MaterialColors
import com.teixeira.subtitles.App
import com.teixeira.subtitles.R

/**
 * Base activity for all application activities.
 *
 * @author Felipe Teixeira
 */
abstract class BaseActivity : AppCompatActivity() {

  protected open val statusBarColor: Int
    get() = MaterialColors.getColor(this, com.google.android.material.R.attr.colorSurface, 0)

  protected open val navigationBarColor: Int
    get() = MaterialColors.getColor(this, com.google.android.material.R.attr.colorSurface, 0)

  protected open val navigationBarDividerColor: Int
    get() = MaterialColors.getColor(this, com.google.android.material.R.attr.colorOutlineVariant, 0)

  protected val app: App
    get() = App.instance

  protected abstract fun bindView(): View

  override fun onCreate(savedInstanceState: Bundle?) {
    window?.apply {
      this.statusBarColor = this@BaseActivity.statusBarColor
      this.navigationBarColor = this@BaseActivity.navigationBarColor
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        this.navigationBarDividerColor = this@BaseActivity.navigationBarDividerColor
      }
    }
    super.onCreate(savedInstanceState)
    setContentView(bindView())
  }
}
