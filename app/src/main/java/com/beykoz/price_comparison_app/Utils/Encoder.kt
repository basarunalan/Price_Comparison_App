package com.beykoz.price_comparison_app.Utils

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.encoder(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())