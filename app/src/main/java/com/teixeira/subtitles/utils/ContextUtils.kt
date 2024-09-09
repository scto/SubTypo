package com.teixeira.subtitles.utils

import android.content.Context
import android.view.LayoutInflater

object ContextUtils {

  val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)
}
