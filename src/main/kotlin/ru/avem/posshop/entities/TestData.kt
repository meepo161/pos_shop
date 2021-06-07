package ru.avem.posshop.entities

import ru.avem.posshop.controllers.Test1Controller
import ru.avem.posshop.controllers.Test2Controller
import tornadofx.*
import kotlin.reflect.KClass

abstract class TController : Controller() {
    abstract fun startTest()
}

class Test(private val name: String, val controller: KClass<out TController>) {
    override fun toString() = name
}

val tests = listOf(
    Test("Испытание ПОС на воздухе", Test1Controller::class),
    Test("Испытание прочности изоляции", Test2Controller::class)
)

class TestItem(val name: String, val t1Temp: String, val t1Time: Double, val t1Voltage: Double, val t2Amperage: Double, val t2Time: Double, val t2Voltage: Double) {
    override fun toString() = name
}

val testItems = listOf(
    TestItem("Накладка", "60-70", 10.0, 90.0, 50.0, 60.0, 1000.0),
    TestItem("Носовая часть", "60-70", 10.0, 150.0, 50.0, 60.0, 1000.0),
    TestItem("Лопасть", "50-60", 10.0, 150.0, 50.0, 60.0, 850.0)
)
