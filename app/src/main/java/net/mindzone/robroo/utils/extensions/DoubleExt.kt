package net.mindzone.robroo.utils.extensions

import java.text.DecimalFormat

fun Double.currencyFormat(): String =
    DecimalFormat("#.##").format(this)