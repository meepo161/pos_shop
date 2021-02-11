package ru.avem.posshop.controllers

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.scene.text.Text
import ru.avem.posshop.app.Pos.Companion.isAppRunning
import ru.avem.posshop.communication.model.CommunicationModel
import ru.avem.posshop.entities.*
import ru.avem.posshop.utils.LogTag
import ru.avem.posshop.utils.State
import ru.avem.posshop.utils.Toast
import ru.avem.posshop.utils.sleep
import ru.avem.posshop.view.MainView
import tornadofx.*
import java.text.SimpleDateFormat
import kotlin.concurrent.thread
import kotlin.reflect.full.createInstance
import kotlin.time.ExperimentalTime

class MainViewController : Controller() {
    val view: MainView by inject()
    var position1 = ""

    @Volatile
    var isExperimentRunning: Boolean = true

    var cause: String = ""
        set(value) {
            if (value != "") {
                isExperimentRunning = false
            }
            field = value
        }

    var tableValuesTest21 = observableListOf(
        TableValuesTest21(
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )
    var tableValuesTest22 = observableListOf(
        TableValuesTest22(
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )
    var tableValuesTest23 = observableListOf(
        TableValuesTest23(
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )

    var tableValuesTest1 = observableListOf(
        TableValuesTest1(
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )
    var tableValuesTest2 = observableListOf(
        TableValuesTest2(
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )

    var tableValuesPlace1Test1 = observableListOf(
        TableValuesPlace1Test1(
            SimpleStringProperty("1"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace1Test1(
            SimpleStringProperty("2"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace1Test1(
            SimpleStringProperty("3"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace1Test1(
            SimpleStringProperty("4"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace1Test1(
            SimpleStringProperty("5"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace1Test1(
            SimpleStringProperty("6"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )
    var tableValuesPlace2Test1 = observableListOf(
        TableValuesPlace2Test1(
            SimpleStringProperty("1"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace2Test1(
            SimpleStringProperty("2"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace2Test1(
            SimpleStringProperty("3"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace2Test1(
            SimpleStringProperty("4"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace2Test1(
            SimpleStringProperty("5"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace2Test1(
            SimpleStringProperty("6"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )
    var tableValuesPlace3Test1 = observableListOf(
        TableValuesPlace3Test1(
            SimpleStringProperty("1"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace3Test1(
            SimpleStringProperty("2"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace3Test1(
            SimpleStringProperty("3"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace3Test1(
            SimpleStringProperty("4"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace3Test1(
            SimpleStringProperty("5"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesPlace3Test1(
            SimpleStringProperty("6"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )

    var tableValuesPlace1Test2 = observableListOf(
        TableValuesPlace1Test2(
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )
    var tableValuesPlace2Test2 = observableListOf(
        TableValuesPlace2Test2(
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )
    var tableValuesPlace3Test2 = observableListOf(
        TableValuesPlace3Test2(
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )

    val tableValuesTestHV1 = observableListOf(
        TableValuesTestHV(
            SimpleStringProperty("1"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesTestHV(
            SimpleStringProperty("2"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        ),
        TableValuesTestHV(
            SimpleStringProperty("3"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0"),
            SimpleStringProperty("0.0")
        )
    )

    init {
        thread(isDaemon = true) {
            runLater {
                view.buttonStop.isDisable = true
            }
            while (isAppRunning) {
                if (CommunicationModel.getDeviceById(CommunicationModel.DeviceID.DD2).isResponding) {
                    runLater {
                        view.comIndicate.fill = State.OK.c
                    }
                } else {
                    runLater {
                        view.comIndicate.fill = State.BAD.c
                    }
                }
                sleep(1000)
            }
        }
    }

    fun isDevicesRespondingTest1(): Boolean {
        return CommunicationModel.getDeviceById(CommunicationModel.DeviceID.DD2).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA1).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA2).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA3).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA4).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA5).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA6).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM1).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM2).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM3).isResponding
                && (if (view.place1Prop.value) CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV238).isResponding else true)
                && (if (view.place2Prop.value) CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV239).isResponding else true)
                && (if (view.place3Prop.value) CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV240).isResponding else true)
    }

    fun isDevicesRespondingTest2(): Boolean {
        return CommunicationModel.getDeviceById(CommunicationModel.DeviceID.DD2).isResponding
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV238).isResponding
                && (if (view.place1Prop.value) CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A71).isResponding else true)
                && (if (view.place2Prop.value) CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A72).isResponding else true)
                && (if (view.place3Prop.value) CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A73).isResponding else true)
                && CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A41).isResponding
    }

    @OptIn(ExperimentalTime::class)
    fun handleStartTest() {
        if (view.comboBoxTests.selectedItem == null) {
            runLater {
                Toast.makeText("Выберите испытание").show(Toast.ToastType.ERROR)
            }
        } else if (!isAtLeastOneIsSelected()) {
            runLater {
                Toast.makeText("Выберите хотя бы одно место испытания").show(Toast.ToastType.ERROR)
            }
        } else {
            view.testsProp.get().controller.createInstance().startTest()
        }
    }

    fun handleStopTest() {
        cause = "Отменено оператором"
    }

    private fun appendMessageToLog(tag: LogTag, _msg: String) {
        val msg = Text("${SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())} | $_msg")
        msg.style {
            fill = when (tag) {
                LogTag.MESSAGE -> tag.c
                LogTag.ERROR -> tag.c
                LogTag.DEBUG -> tag.c
            }
        }

        Platform.runLater {
            view.vBoxLog.add(msg)
        }
    }

    private fun isAtLeastOneIsSelected(): Boolean {
        return view.checkBoxPlace1.isSelected ||
                view.checkBoxPlace2.isSelected ||
                view.checkBoxPlace3.isSelected
    }

    fun showAboutUs() {
        Toast.makeText("Версия ПО: 1.0.0\nВерсия БСУ: 1.0.0\nДата: 11.02.2021").show(Toast.ToastType.INFORMATION)
    }
}
