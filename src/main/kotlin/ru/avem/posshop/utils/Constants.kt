package ru.avem.posshop.utils

import javafx.scene.paint.Color
import tornadofx.c

enum class LogTag(val c: Color) {
    MESSAGE(c("#5dbb25")),
    ERROR(c("#ff3935")),
    DEBUG(c("#359eee"))
}

enum class State(val c: Color) {
    OK(c("#00FF0093")),
    INTERMEDIATE(c("#FFFF0093")),
    BAD(c("#FF000093")),
}

const val YES = "ДА"
const val NO = "НЕТ"

const val SHEET_PASSWORD = "444488888888"

const val BREAK_IKAS = 1.0E9
