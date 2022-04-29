package ru.avem.posshop.controllers

import javafx.application.Platform
import javafx.scene.control.ButtonType
import javafx.scene.text.Text
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.posshop.communication.model.CommunicationModel
import ru.avem.posshop.communication.model.devices.LatrStuckException
import ru.avem.posshop.communication.model.devices.avem.latr.AvemLatrController
import ru.avem.posshop.communication.model.devices.avem.latr.AvemLatrModel
import ru.avem.posshop.communication.model.devices.avem.latr.LatrControllerConfiguration
import ru.avem.posshop.communication.model.devices.owen.pr.OwenPrController
import ru.avem.posshop.communication.model.devices.owen.pr.OwenPrModel
import ru.avem.posshop.communication.model.devices.owen.trm136.Trm136Controller
import ru.avem.posshop.communication.model.devices.owen.trm136.Trm136Model
import ru.avem.posshop.communication.model.devices.parma.ParmaController
import ru.avem.posshop.communication.model.devices.parma.ParmaModel
import ru.avem.posshop.database.entities.Protocol
import ru.avem.posshop.database.entities.ProtocolRotorBlade
import ru.avem.posshop.database.entities.ProtocolVars
import ru.avem.posshop.entities.TController
import ru.avem.posshop.protocol.saveProtocolAsWorkbook
import ru.avem.posshop.utils.*
import ru.avem.posshop.view.MainView
import tornadofx.*
import java.awt.Desktop
import java.io.File
import java.text.SimpleDateFormat
import kotlin.concurrent.thread
import kotlin.experimental.and
import kotlin.time.ExperimentalTime

class Test1Controller : TController() {
    protected val owenPR = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.DD2) as OwenPrController
    protected val parma1 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA1) as ParmaController
    protected val trm1 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM1) as Trm136Controller
    protected val trm2 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM2) as Trm136Controller
    protected val trm3 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM3) as Trm136Controller
    protected val gv238 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV238) as AvemLatrController
    protected val gv239 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV239) as AvemLatrController
    protected val gv240 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV240) as AvemLatrController

    val controller: MainViewController by inject()
    val mainView: MainView by inject()

    private var logBuffer: String? = null

    @Volatile
    var isExperimentEnded: Boolean = true

    //region переменные для значений с приборов
    @Volatile
    private var measuringU11: Double = 0.0

    @Volatile
    private var measuringU12: Double = 0.0

    @Volatile
    private var measuringU13: Double = 0.0

    @Volatile
    private var measuringU14: Double = 0.0

    @Volatile
    private var measuringU15: Double = 0.0

    @Volatile
    private var measuringU16: Double = 0.0

    @Volatile
    private var measuringU21: Double = 0.0

    @Volatile
    private var measuringU22: Double = 0.0

    @Volatile
    private var measuringU23: Double = 0.0

    @Volatile
    private var measuringU24: Double = 0.0

    @Volatile
    private var measuringU25: Double = 0.0

    @Volatile
    private var measuringU26: Double = 0.0

    @Volatile
    private var measuringU31: Double = 0.0

    @Volatile
    private var measuringU32: Double = 0.0

    @Volatile
    private var measuringU33: Double = 0.0

    @Volatile
    private var measuringU34: Double = 0.0

    @Volatile
    private var measuringU35: Double = 0.0

    @Volatile
    private var measuringU36: Double = 0.0

    @Volatile
    private var measuringI11: Double = 0.0

    @Volatile
    private var measuringI12: Double = 0.0

    @Volatile
    private var measuringI13: Double = 0.0

    @Volatile
    private var measuringI14: Double = 0.0

    @Volatile
    private var measuringI15: Double = 0.0

    @Volatile
    private var measuringI16: Double = 0.0

    @Volatile
    private var measuringI21: Double = 0.0

    @Volatile
    private var measuringI22: Double = 0.0

    @Volatile
    private var measuringI23: Double = 0.0

    @Volatile
    private var measuringI24: Double = 0.0

    @Volatile
    private var measuringI25: Double = 0.0

    @Volatile
    private var measuringI26: Double = 0.0

    @Volatile
    private var measuringI31: Double = 0.0

    @Volatile
    private var measuringI32: Double = 0.0

    @Volatile
    private var measuringI33: Double = 0.0

    @Volatile
    private var measuringI34: Double = 0.0

    @Volatile
    private var measuringI35: Double = 0.0

    @Volatile
    private var measuringI36: Double = 0.0

    @Volatile
    private var measuringt11: Double = 0.0

    @Volatile
    private var measuringt12: Double = 0.0

    @Volatile
    private var measuringt13: Double = 0.0

    @Volatile
    private var measuringt14: Double = 0.0

    @Volatile
    private var measuringt15: Double = 0.0

    @Volatile
    private var measuringt16: Double = 0.0

    @Volatile
    private var measuringt21: Double = 0.0

    @Volatile
    private var measuringt22: Double = 0.0

    @Volatile
    private var measuringt23: Double = 0.0

    @Volatile
    private var measuringt24: Double = 0.0

    @Volatile
    private var measuringt25: Double = 0.0

    @Volatile
    private var measuringt26: Double = 0.0

    @Volatile
    private var measuringt31: Double = 0.0

    @Volatile
    private var measuringt32: Double = 0.0

    @Volatile
    private var measuringt33: Double = 0.0

    @Volatile
    private var measuringt34: Double = 0.0

    @Volatile
    private var measuringt35: Double = 0.0

    @Volatile
    private var measuringt36: Double = 0.0
    //endregion

    //region переменные для защит ПР
    @Volatile
    private var doorCabinet: Boolean = false

    @Volatile
    private var doorZone: Boolean = false

    @Volatile
    private var startButton: Boolean = false

    @Volatile
    private var stopButton: Boolean = false

    @Volatile
    private var currentI1: Boolean = false

    @Volatile
    private var currentI2: Boolean = false

    @Volatile
    private var currentI3: Boolean = false

    @Volatile
    private var isolation1: Boolean = false

    @Volatile
    private var isolation2: Boolean = false

    @Volatile
    private var isolation3: Boolean = false
    //endregion

    @Volatile
    var tickJobInProcess = false

    @Volatile
    var isLatrRegulation = false

    @Volatile
    var currentStage1 = 0

    @Volatile
    var currentStage2 = 0

    @Volatile
    var currentStage3 = 0

    @Volatile
    var allProcessEnded1 = false

    @Volatile
    var allProcessEnded2 = false

    @Volatile
    var allProcessEnded3 = false

    @Volatile
    var temperature: Double = 0.0

    @Volatile
    var isNeedHeating1 = false

    @Volatile
    var isNeedHeating2 = false

    @Volatile
    var isNeedHeating3 = false

    @Volatile
    var isHeating1 = false

    @Volatile
    var isHeating2 = false

    @Volatile
    var isHeating3 = false

    private var isClicked: Boolean = false

    //region листы для БД
    private var listOfValues11 = mutableListOf<String>()
    private var listOfValues12 = mutableListOf<String>()
    private var listOfValues13 = mutableListOf<String>()
    private var listOfValues14 = mutableListOf<String>()
    private var listOfValues15 = mutableListOf<String>()
    private var listOfValues16 = mutableListOf<String>()
    private var listOfValues21 = mutableListOf<String>()
    private var listOfValues22 = mutableListOf<String>()
    private var listOfValues23 = mutableListOf<String>()
    private var listOfValues24 = mutableListOf<String>()
    private var listOfValues25 = mutableListOf<String>()
    private var listOfValues26 = mutableListOf<String>()
    private var listOfValues31 = mutableListOf<String>()
    private var listOfValues32 = mutableListOf<String>()
    private var listOfValues33 = mutableListOf<String>()
    private var listOfValues34 = mutableListOf<String>()
    private var listOfValues35 = mutableListOf<String>()
    private var listOfValues36 = mutableListOf<String>()

    private var listOfValuesVoltage11 = mutableListOf<String>()
    private var listOfValuesVoltage12 = mutableListOf<String>()
    private var listOfValuesVoltage13 = mutableListOf<String>()
    private var listOfValuesVoltage14 = mutableListOf<String>()
    private var listOfValuesVoltage15 = mutableListOf<String>()
    private var listOfValuesVoltage16 = mutableListOf<String>()
    private var listOfValuesVoltage21 = mutableListOf<String>()
    private var listOfValuesVoltage22 = mutableListOf<String>()
    private var listOfValuesVoltage23 = mutableListOf<String>()
    private var listOfValuesVoltage24 = mutableListOf<String>()
    private var listOfValuesVoltage25 = mutableListOf<String>()
    private var listOfValuesVoltage26 = mutableListOf<String>()
    private var listOfValuesVoltage31 = mutableListOf<String>()
    private var listOfValuesVoltage32 = mutableListOf<String>()
    private var listOfValuesVoltage33 = mutableListOf<String>()
    private var listOfValuesVoltage34 = mutableListOf<String>()
    private var listOfValuesVoltage35 = mutableListOf<String>()
    private var listOfValuesVoltage36 = mutableListOf<String>()

    private var listOfValuesAmperage11 = mutableListOf<String>()
    private var listOfValuesAmperage12 = mutableListOf<String>()
    private var listOfValuesAmperage13 = mutableListOf<String>()
    private var listOfValuesAmperage14 = mutableListOf<String>()
    private var listOfValuesAmperage15 = mutableListOf<String>()
    private var listOfValuesAmperage16 = mutableListOf<String>()
    private var listOfValuesAmperage21 = mutableListOf<String>()
    private var listOfValuesAmperage22 = mutableListOf<String>()
    private var listOfValuesAmperage23 = mutableListOf<String>()
    private var listOfValuesAmperage24 = mutableListOf<String>()
    private var listOfValuesAmperage25 = mutableListOf<String>()
    private var listOfValuesAmperage26 = mutableListOf<String>()
    private var listOfValuesAmperage31 = mutableListOf<String>()
    private var listOfValuesAmperage32 = mutableListOf<String>()
    private var listOfValuesAmperage33 = mutableListOf<String>()
    private var listOfValuesAmperage34 = mutableListOf<String>()
    private var listOfValuesAmperage35 = mutableListOf<String>()
    private var listOfValuesAmperage36 = mutableListOf<String>()
    //endregion

    var unixTimeStart = 0L

    var unixTimeStart11 = 0L
    var unixTimeStart12 = 0L
    var unixTimeStart13 = 0L
    var unixTimeStart14 = 0L
    var unixTimeStart15 = 0L
    var unixTimeStart16 = 0L

    var unixTimeWork11 = 0L
    var unixTimeWork12 = 0L
    var unixTimeWork13 = 0L
    var unixTimeWork14 = 0L
    var unixTimeWork15 = 0L
    var unixTimeWork16 = 0L

    var unixTimeEnd11 = 0L
    var unixTimeEnd12 = 0L
    var unixTimeEnd13 = 0L
    var unixTimeEnd14 = 0L
    var unixTimeEnd15 = 0L
    var unixTimeEnd16 = 0L

    var unixTimeStart21 = 0L
    var unixTimeStart22 = 0L
    var unixTimeStart23 = 0L
    var unixTimeStart24 = 0L
    var unixTimeStart25 = 0L
    var unixTimeStart26 = 0L

    var unixTimeWork21 = 0L
    var unixTimeWork22 = 0L
    var unixTimeWork23 = 0L
    var unixTimeWork24 = 0L
    var unixTimeWork25 = 0L
    var unixTimeWork26 = 0L

    var unixTimeEnd21 = 0L
    var unixTimeEnd22 = 0L
    var unixTimeEnd23 = 0L
    var unixTimeEnd24 = 0L
    var unixTimeEnd25 = 0L
    var unixTimeEnd26 = 0L

    var unixTimeStart31 = 0L
    var unixTimeStart32 = 0L
    var unixTimeStart33 = 0L
    var unixTimeStart34 = 0L
    var unixTimeStart35 = 0L
    var unixTimeStart36 = 0L

    var unixTimeWork31 = 0L
    var unixTimeWork32 = 0L
    var unixTimeWork33 = 0L
    var unixTimeWork34 = 0L
    var unixTimeWork35 = 0L
    var unixTimeWork36 = 0L

    var unixTimeEnd31 = 0L
    var unixTimeEnd32 = 0L
    var unixTimeEnd33 = 0L
    var unixTimeEnd34 = 0L
    var unixTimeEnd35 = 0L
    var unixTimeEnd36 = 0L

    private fun appendOneMessageToLog(tag: LogTag, message: String) {
        if (logBuffer == null || logBuffer != message) {
            logBuffer = message
            appendMessageToLog(tag, message)
        }
    }

    fun appendMessageToLog(tag: LogTag, _msg: String) {
        val msg = Text("${SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())} | $_msg")
        msg.style {
            fill = when (tag) {
                LogTag.MESSAGE -> tag.c
                LogTag.ERROR -> tag.c
                LogTag.DEBUG -> tag.c
            }
            fontSize = 26.px
        }

        Platform.runLater {
            mainView.vBoxLog.add(msg)
        }
    }

    private fun startPollDevices() {
        //region pr pool
        CommunicationModel.startPoll(CommunicationModel.DeviceID.DD2, OwenPrModel.FIXED_STATES_REGISTER_1) { value ->
//            doorCabinet = value.toShort() and 1 > 0 // TODO нет в стенде, но есть в тз
//            if (doorCabinet) {
//                controller.cause = "Открыта дверь шкафа"
//            }

            doorZone = value.toShort() and 2 > 0
            if (doorZone) {
                controller.cause = "Открыта дверь зоны"
            }

            stopButton = value.toShort() and 32 > 0
            if (stopButton) {
                controller.cause = "Нажали кнопку СТОП"
            }

            startButton = value.toShort() and 64 > 0
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.DD2, OwenPrModel.FIXED_STATES_REGISTER_2) { value ->
            currentI1 = value.toShort() and 1 > 0
            if (currentI1) {
                controller.cause = "Токовая защита ОИ 1"
            }

            currentI2 = value.toShort() and 2 > 0
            if (currentI2) {
                controller.cause = "Токовая защита ОИ 2"
            }

            currentI3 = value.toShort() and 4 > 0
            if (currentI3) {
                controller.cause = "Токовая защита ОИ 3"
            }

            isolation1 = value.toShort() and 8 > 0
            if (isolation1) {
                controller.cause = "Пробой изоляции ОИ 1"
            }

            isolation2 = value.toShort() and 16 > 0
            if (isolation2) {
                controller.cause = "Пробой изоляции ОИ 2"
            }

            isolation3 = value.toShort() and 32 > 0
            if (isolation3) {
                controller.cause = "Пробой изоляции ОИ 3"
            }
        }
        //endregion

        //region parma poll
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UA) { value ->
            measuringU11 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UA) { value ->
            measuringU12 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UA) { value ->
            measuringU13 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UA) { value ->
            measuringU14 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UA) { value ->
            measuringU15 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UA) { value ->
            measuringU16 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UB) { value ->
            measuringU21 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UB) { value ->
            measuringU22 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UB) { value ->
            measuringU23 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UB) { value ->
            measuringU24 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UB) { value ->
            measuringU25 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UB) { value ->
            measuringU26 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UC) { value ->
            measuringU31 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UC) { value ->
            measuringU32 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UC) { value ->
            measuringU33 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UC) { value ->
            measuringU34 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UC) { value ->
            measuringU35 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.UC) { value ->
            measuringU36 = if (!value.toDouble().isNaN()) {
                value.toDouble()
            } else {
                -99.9
            }
        }

        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.IA) { value ->
            measuringI11 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.IB) { value ->
            measuringI12 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA1, ParmaModel.IC) { value ->
            measuringI13 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA2, ParmaModel.IA) { value ->
            measuringI14 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA2, ParmaModel.IB) { value ->
            measuringI15 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA2, ParmaModel.IC) { value ->
            measuringI16 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA3, ParmaModel.IA) { value ->
            measuringI21 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA3, ParmaModel.IB) { value ->
            measuringI22 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA3, ParmaModel.IC) { value ->
            measuringI23 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA4, ParmaModel.IA) { value ->
            measuringI24 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA4, ParmaModel.IB) { value ->
            measuringI25 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA4, ParmaModel.IC) { value ->
            measuringI26 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA5, ParmaModel.IA) { value ->
            measuringI31 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA5, ParmaModel.IB) { value ->
            measuringI32 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA5, ParmaModel.IC) { value ->
            measuringI33 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA6, ParmaModel.IA) { value ->
            measuringI34 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA6, ParmaModel.IB) { value ->
            measuringI35 = value.toDouble() * 10
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.PARMA6, ParmaModel.IC) { value ->
            measuringI36 = value.toDouble() * 10
        }
        //endregion

        //region trm poll
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_1) { value ->
            measuringt11 = value.toDouble()
            if (measuringt11 > 75) {
                controller.cause = "Перегрев 1 ОИ 1 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_2) { value ->
            measuringt12 = value.toDouble()
            if (measuringt12 > 75) {
                controller.cause = "Перегрев 1 ОИ 2 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_3) { value ->
            measuringt13 = value.toDouble()
            if (measuringt13 > 75) {
                controller.cause = "Перегрев 1 ОИ 3 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_4) { value ->
            measuringt14 = value.toDouble()
            if (measuringt14 > 75) {
                controller.cause = "Перегрев 1 ОИ 4 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_5) { value ->
            measuringt15 = value.toDouble()
            if (measuringt15 > 75) {
                controller.cause = "Перегрев 1 ОИ 5 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_6) { value ->
            measuringt16 = value.toDouble()
            if (measuringt16 > 75) {
                controller.cause = "Перегрев 1 ОИ 6 секции. Температура превысила 75°C"
            }
        }

        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_1) { value ->
            measuringt21 = value.toDouble()
            if (measuringt21 > 75) {
                controller.cause = "Перегрев 2 ОИ 1 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_2) { value ->
            measuringt22 = value.toDouble()
            if (measuringt22 > 75) {
                controller.cause = "Перегрев 2 ОИ 2 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_3) { value ->
            measuringt23 = value.toDouble()
            if (measuringt23 > 75) {
                controller.cause = "Перегрев 2 ОИ 3 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_4) { value ->
            measuringt24 = value.toDouble()
            if (measuringt24 > 75) {
                controller.cause = "Перегрев 2 ОИ 4 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_5) { value ->
            measuringt25 = value.toDouble()
            if (measuringt25 > 75) {
                controller.cause = "Перегрев 2 ОИ 5 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_6) { value ->
            measuringt26 = value.toDouble()
            if (measuringt26 > 75) {
                controller.cause = "Перегрев 2 ОИ 6 секции. Температура превысила 75°C"
            }
        }

        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_1) { value ->
            measuringt31 = value.toDouble()
            if (measuringt31 > 75) {
                controller.cause = "Перегрев 3 ОИ 1 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_2) { value ->
            measuringt32 = value.toDouble()
            if (measuringt32 > 75) {
                controller.cause = "Перегрев 3 ОИ 2 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_3) { value ->
            measuringt33 = value.toDouble()
            if (measuringt33 > 75) {
                controller.cause = "Перегрев 3 ОИ 3 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_4) { value ->
            measuringt34 = value.toDouble()
            if (measuringt34 > 75) {
                controller.cause = "Перегрев 3 ОИ 4 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_5) { value ->
            measuringt35 = value.toDouble()
            if (measuringt35 > 75) {
                controller.cause = "Перегрев 3 ОИ 5 секции. Температура превысила 75°C"
            }
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_6) { value ->
            measuringt36 = value.toDouble()
            if (measuringt36 > 75) {
                controller.cause = "Перегрев 3 ОИ 6 секции. Температура превысила 75°C"
            }
        }
        //endregion

        if (mainView.place1Prop.value) {
            CommunicationModel.startPoll(CommunicationModel.DeviceID.GV238, AvemLatrModel.DEVICE_STATUS) { }
        }
        if (mainView.place2Prop.value) {
            CommunicationModel.startPoll(CommunicationModel.DeviceID.GV239, AvemLatrModel.DEVICE_STATUS) { }
        }
        if (mainView.place3Prop.value) {
            CommunicationModel.startPoll(CommunicationModel.DeviceID.GV240, AvemLatrModel.DEVICE_STATUS) { }
        }
    }

    @ExperimentalTime
    override fun startTest() {
        thread(isDaemon = true) {
            runLater {
                mainView.buttonStop.isDisable = false
                mainView.buttonStart.isDisable = true
            }
            controller.cause = ""
            controller.isExperimentRunning = true
            isExperimentEnded = false
            isClicked = false
            appendMessageToLog(LogTag.DEBUG, "Начало испытания")
            unixTimeStart = System.currentTimeMillis()
            clearTable()
            temperature = controller.tableValuesTest1[0].place1temp.value.replace(",", ".").toDouble()

            sleep(1000)

            listOfValues11.clear()
            listOfValues12.clear()
            listOfValues13.clear()
            listOfValues14.clear()
            listOfValues15.clear()
            listOfValues16.clear()
            listOfValues21.clear()
            listOfValues22.clear()
            listOfValues23.clear()
            listOfValues24.clear()
            listOfValues25.clear()
            listOfValues26.clear()
            listOfValues31.clear()
            listOfValues32.clear()
            listOfValues33.clear()
            listOfValues34.clear()
            listOfValues35.clear()
            listOfValues36.clear()
            listOfValuesVoltage11.clear()
            listOfValuesVoltage12.clear()
            listOfValuesVoltage13.clear()
            listOfValuesVoltage14.clear()
            listOfValuesVoltage15.clear()
            listOfValuesVoltage16.clear()
            listOfValuesVoltage21.clear()
            listOfValuesVoltage22.clear()
            listOfValuesVoltage23.clear()
            listOfValuesVoltage24.clear()
            listOfValuesVoltage25.clear()
            listOfValuesVoltage26.clear()
            listOfValuesVoltage31.clear()
            listOfValuesVoltage32.clear()
            listOfValuesVoltage33.clear()
            listOfValuesVoltage34.clear()
            listOfValuesVoltage35.clear()
            listOfValuesVoltage36.clear()
            listOfValuesAmperage11.clear()
            listOfValuesAmperage12.clear()
            listOfValuesAmperage13.clear()
            listOfValuesAmperage14.clear()
            listOfValuesAmperage15.clear()
            listOfValuesAmperage16.clear()
            listOfValuesAmperage21.clear()
            listOfValuesAmperage22.clear()
            listOfValuesAmperage23.clear()
            listOfValuesAmperage24.clear()
            listOfValuesAmperage25.clear()
            listOfValuesAmperage26.clear()
            listOfValuesAmperage31.clear()
            listOfValuesAmperage32.clear()
            listOfValuesAmperage33.clear()
            listOfValuesAmperage34.clear()
            listOfValuesAmperage35.clear()
            listOfValuesAmperage36.clear()


            runLater {
                mainView.labelTestStatusEnd1.text = ""
                mainView.labelTimeRemaining1.text = ""
                mainView.labelTimeRemaining2.text = ""
                mainView.labelTimeRemaining3.text = ""
                mainView.buttonStop.isDisable = false
                mainView.buttonStart.isDisable = true
                mainView.comboBoxTests.isDisable = true
                mainView.comboBoxTestItem.isDisable = true
                mainView.initTableTest1.isDisable = true
                mainView.initTableTest2.isDisable = true
                mainView.checkBoxPlace1.isDisable = true
                mainView.checkBoxPlace2.isDisable = true
                mainView.checkBoxPlace3.isDisable = true
            }

            if (controller.isExperimentRunning) {
                owenPR.initOwenPR()
                startPollDevices()
                appendMessageToLog(LogTag.DEBUG, "Инициализация устройств")
                sleep(1000)
            }

            var timeToPrepare = 300
            while (!controller.isDevicesRespondingTest1() && controller.isExperimentRunning && timeToPrepare-- > 0) {
                sleep(100)
            }

            if (!controller.isDevicesRespondingTest1()) {
                var cause = ""
                cause += "Не отвечают приборы: "
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.DD2).isResponding) {
                    cause += "ПР "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA1).isResponding) {
                    cause += "ПАРМА1 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA2).isResponding) {
                    cause += "ПАРМА2 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA3).isResponding) {
                    cause += "ПАРМА3 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA4).isResponding) {
                    cause += "ПАРМА4 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA5).isResponding) {
                    cause += "ПАРМА5 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA6).isResponding) {
                    cause += "ПАРМА6 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM1).isResponding) {
                    cause += "ТРМ1 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM2).isResponding) {
                    cause += "ТРМ2 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM3).isResponding) {
                    cause += "ТРМ3 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV238).isResponding && mainView.place1Prop.value) {
                    cause += "ЛАТР1 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV239).isResponding && mainView.place2Prop.value) {
                    cause += "ЛАТР2 "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV240).isResponding && mainView.place3Prop.value) {
                    cause += "ЛАТР3 "
                }
//                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A71).isResponding) {
//                    cause += "АВЭМ71 "
//                }
//                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A72).isResponding) {
//                    cause += "АВЭМ72 "
//                }
//                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A73).isResponding) {
//                    cause += "АВЭМ73 "
//                }
//                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A41).isResponding) {
//                    cause += "АВЭМ41 "
//                }
                controller.cause = cause
            }

            if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                CommunicationModel.addWritingRegister(
                    CommunicationModel.DeviceID.DD2,
                    OwenPrModel.RESET_DOG,
                    1.toShort()
                )
                owenPR.initOwenPR()
                sleep(1000)
            }

            if (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                runLater {
                    Toast.makeText("Нажмите кнопку ПУСК").show(Toast.ToastType.WARNING)
                }
                appendOneMessageToLog(LogTag.DEBUG, "Нажмите кнопку ПУСК")
            }

            var timeToStart = 300
            while (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest1() && timeToStart-- > 0) {
                sleep(100)
            }

            if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                soundError()
                soundWarning(1)
            }

            if (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                controller.cause = "Не нажата кнопка ПУСК"
            }

            if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                appendMessageToLog(LogTag.DEBUG, "Подготовка стенда")
            }

            val voltage = controller.tableValuesTest1[0].place1voltage.value.toString().replace(",", ".").toDouble()
            isLatrRegulation = true
            initValuesForRegulation()
            if (mainView.place1Prop.value) {
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    initLatr(gv238)
                }
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    configLatr(gv238, voltage)
                }

                while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() && measuringU11 <= voltage) {
                    appendOneMessageToLog(LogTag.MESSAGE, "Грубая регулировка напряжения")

                    if (isLatrInErrorMode(gv238)) {
                        controller.cause = "Ошибка контроллера АРН"
                    }
                    sleep(100)
                }
                accurateRegulate(parma1, ParmaModel.UA, gv238, voltage)
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    appendOneMessageToLog(LogTag.MESSAGE, "Напряжение места 1 выставлено")
                }
            }

            if (mainView.place2Prop.value) {
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    initLatr(gv239)
                }
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    configLatr(gv239, voltage)
                }

                while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() && measuringU21 <= voltage) {
                    appendOneMessageToLog(LogTag.MESSAGE, "Грубая регулировка напряжения")

                    if (isLatrInErrorMode(gv239)) {
                        controller.cause = "Ошибка контроллера АРН"
                    }
                    sleep(100)
                }
                accurateRegulate(parma1, ParmaModel.UB, gv239, voltage)
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    appendOneMessageToLog(LogTag.MESSAGE, "Напряжение места 2 выставлено")
                }
            }

            if (mainView.place3Prop.value) {
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    initLatr(gv240)
                }
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    configLatr(gv240, voltage)
                }

                while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() && measuringU31 <= voltage) {
                    appendOneMessageToLog(LogTag.MESSAGE, "Грубая регулировка напряжения")

                    if (isLatrInErrorMode(gv240)) {
                        controller.cause = "Ошибка контроллера АРН"
                    }
                    sleep(100)
                }
                accurateRegulate(parma1, ParmaModel.UC, gv240, voltage)
                if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                    appendOneMessageToLog(LogTag.MESSAGE, "Напряжение места 3 выставлено")
                }
            }

            isLatrRegulation = false

            collectValuesInProtocolInTimer()
            allProcessEnded1 = false
            allProcessEnded2 = false
            allProcessEnded3 = false

            thread(isDaemon = true) {
                while (controller.isExperimentRunning) {
                    setValuesInTimer()
                    sleep(1000)
                }
            }

            if (mainView.place1Prop.value) {
                thread(isDaemon = true) {
                    for (currentSection in 0..5) {
                        if (mainView.listCB[currentSection].isSelected) {
                            currentStage1 = currentSection
                            runLater {
                                mainView.tableTestPlace1Test1.selectionModel.select(currentSection)
                                mainView.labelTimeRemaining1.text =
                                    "Нагрев"
                            }

                            if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                                    appendOneMessageToLog(
                                        LogTag.MESSAGE,
                                        "Начался нагрев 1 ОИ ${currentSection + 1} секции. Ожидайте"
                                    )
                                when (currentSection) {
                                    0 -> {
                                        unixTimeStart11 = System.currentTimeMillis()
                                    }
                                    1 -> {
                                        unixTimeStart12 = System.currentTimeMillis()
                                    }
                                    2 -> {
                                        unixTimeStart13 = System.currentTimeMillis()
                                    }
                                    3 -> {
                                        unixTimeStart14 = System.currentTimeMillis()
                                    }
                                    4 -> {
                                        unixTimeStart15 = System.currentTimeMillis()
                                    }
                                    5 -> {
                                        unixTimeStart16 = System.currentTimeMillis()
                                    }
                                }
                                firstHeatingSection1()
                                appendMessageToLog(LogTag.ERROR, "Нагрев секции 1 ОИ завершен")
                                isHeating1 = false
                                appendOneMessageToLog(
                                    LogTag.MESSAGE,
                                    "Запущен отсчет времени для 1 ОИ ${currentSection + 1} секции."
                                )
                            }

                            val allTime =
                                (controller.tableValuesTest1[0].place1time.value.toString().replace(",", ".")
                                    .toDouble() * 60).toInt()

                            val callbackTimer = CallbackTimer(
                                tickPeriod = 1.seconds, tickTimes = allTime,
                                tickJob = {
                                    if (!tickJobInProcess) {
                                        tickJobInProcess = true
                                        if (!controller.isExperimentRunning) it.stop()
                                        runLater {
                                            mainView.labelTimeRemaining1.text =
                                                "Осталось: " + toHHmmss(
                                                    (allTime - it.getCurrentTicks()) * 1000L
                                                )
                                        }
                                        tickJobInProcess = false
                                    }
                                }
                            )
                            when (currentSection) {
                                0 -> {
                                    unixTimeWork11 = System.currentTimeMillis()
                                }
                                1 -> {
                                    unixTimeWork12 = System.currentTimeMillis()
                                }
                                2 -> {
                                    unixTimeWork13 = System.currentTimeMillis()
                                }
                                3 -> {
                                    unixTimeWork14 = System.currentTimeMillis()
                                }
                                4 -> {
                                    unixTimeWork15 = System.currentTimeMillis()
                                }
                                5 -> {
                                    unixTimeWork16 = System.currentTimeMillis()
                                }
                            }
                            heatingSection1()


                            while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() && callbackTimer.isRunning) {
                                checkProtections()
                                sleep(2000)
                            }

                            when (currentSection) {
                                0 -> {
                                    unixTimeEnd11 = System.currentTimeMillis()
                                }
                                1 -> {
                                    unixTimeEnd12 = System.currentTimeMillis()
                                }
                                2 -> {
                                    unixTimeEnd13 = System.currentTimeMillis()
                                }
                                3 -> {
                                    unixTimeEnd14 = System.currentTimeMillis()
                                }
                                4 -> {
                                    unixTimeEnd15 = System.currentTimeMillis()
                                }
                                5 -> {
                                    unixTimeEnd16 = System.currentTimeMillis()
                                }
                            }

                            isNeedHeating1 = false
                            if (currentSection < 5) {
                                appendOneMessageToLog(LogTag.MESSAGE, "Переход к следующей секции")
                            } else {
                                allProcessEnded1 = true
                                appendOneMessageToLog(LogTag.MESSAGE, "Ожидание завершения")
                            }
                            sleepWhile(5)
                        }
                    }
                }
            } else {
                allProcessEnded1 = true
            }

            if (mainView.place2Prop.value) {
                thread(isDaemon = true) {
                    for (currentSection in 0..5) {
                        if (mainView.listCB[currentSection].isSelected) {
                            currentStage2 = currentSection
                            runLater {
                                mainView.tableTestPlace2Test1.selectionModel.select(currentSection)
                                mainView.labelTimeRemaining2.text =
                                    "Нагрев"
                            }

                            if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                                appendOneMessageToLog(
                                    LogTag.MESSAGE,
                                    "Начался нагрев 2 ОИ ${currentSection + 1} секции. Ожидайте"
                                )
                                when (currentSection) {
                                    0 -> {
                                        unixTimeStart21 = System.currentTimeMillis()
                                    }
                                    1 -> {
                                        unixTimeStart22 = System.currentTimeMillis()
                                    }
                                    2 -> {
                                        unixTimeStart23 = System.currentTimeMillis()
                                    }
                                    3 -> {
                                        unixTimeStart24 = System.currentTimeMillis()
                                    }
                                    4 -> {
                                        unixTimeStart25 = System.currentTimeMillis()
                                    }
                                    5 -> {
                                        unixTimeStart26 = System.currentTimeMillis()
                                    }
                                }
                                firstHeatingSection2()
                                appendMessageToLog(LogTag.ERROR, "Нагрев секции 2 ОИ завершен")
                                isHeating2 = false
                                appendOneMessageToLog(
                                    LogTag.MESSAGE,
                                    "Запущен отсчет времени для 2 ОИ ${currentSection + 1} секции."
                                )
                            }

                            val allTime =
                                (controller.tableValuesTest1[0].place1time.value.toString().replace(",", ".")
                                    .toDouble() * 60).toInt()

                            val callbackTimer = CallbackTimer(
                                tickPeriod = 1.seconds, tickTimes = allTime,
                                tickJob = {
                                    if (!tickJobInProcess) {
                                        tickJobInProcess = true
                                        if (!controller.isExperimentRunning) it.stop()
                                        runLater {
                                            mainView.labelTimeRemaining2.text =
                                                "Осталось: " + toHHmmss(
                                                    (allTime - it.getCurrentTicks()) * 1000L
                                                )
                                        }
                                        tickJobInProcess = false
                                    }
                                }
                            )
                            when (currentSection) {
                                0 -> {
                                    unixTimeWork21 = System.currentTimeMillis()
                                }
                                1 -> {
                                    unixTimeWork22 = System.currentTimeMillis()
                                }
                                2 -> {
                                    unixTimeWork23 = System.currentTimeMillis()
                                }
                                3 -> {
                                    unixTimeWork24 = System.currentTimeMillis()
                                }
                                4 -> {
                                    unixTimeWork25 = System.currentTimeMillis()
                                }
                                5 -> {
                                    unixTimeWork26 = System.currentTimeMillis()
                                }
                            }
                            heatingSection2()


                            while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() && callbackTimer.isRunning) {
                                checkProtections()
                                sleep(2000)
                            }

                            when (currentSection) {
                                0 -> {
                                    unixTimeEnd21 = System.currentTimeMillis()
                                }
                                1 -> {
                                    unixTimeEnd22 = System.currentTimeMillis()
                                }
                                2 -> {
                                    unixTimeEnd23 = System.currentTimeMillis()
                                }
                                3 -> {
                                    unixTimeEnd24 = System.currentTimeMillis()
                                }
                                4 -> {
                                    unixTimeEnd25 = System.currentTimeMillis()
                                }
                                5 -> {
                                    unixTimeEnd26 = System.currentTimeMillis()
                                }
                            }

                            isNeedHeating2 = false
                            if (currentSection < 5) {
                                appendOneMessageToLog(LogTag.MESSAGE, "Переход к следующей секции")
                            } else {
                                allProcessEnded2 = true
                                appendOneMessageToLog(LogTag.MESSAGE, "Ожидание завершения")
                            }
                            sleepWhile(5)
                        }
                    }
                }
            } else {
                allProcessEnded2 = true
            }

            if (mainView.place3Prop.value) {
                thread(isDaemon = true) {
                    for (currentSection in 0..5) {
                        if (mainView.listCB[currentSection].isSelected) {
                            currentStage3 = currentSection
                            runLater {
                                mainView.tableTestPlace3Test1.selectionModel.select(currentSection)
                                mainView.labelTimeRemaining3.text =
                                    "Нагрев"
                            }

                            if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                                appendOneMessageToLog(
                                    LogTag.MESSAGE,
                                    "Начался нагрев 3 ОИ ${currentSection + 1} секции. Ожидайте"
                                )
                                when (currentSection) {
                                    0 -> {
                                        unixTimeStart31 = System.currentTimeMillis()
                                    }
                                    1 -> {
                                        unixTimeStart32 = System.currentTimeMillis()
                                    }
                                    2 -> {
                                        unixTimeStart33 = System.currentTimeMillis()
                                    }
                                    3 -> {
                                        unixTimeStart34 = System.currentTimeMillis()
                                    }
                                    4 -> {
                                        unixTimeStart35 = System.currentTimeMillis()
                                    }
                                    5 -> {
                                        unixTimeStart36 = System.currentTimeMillis()
                                    }
                                }
                                firstHeatingSection3()
                                appendMessageToLog(LogTag.ERROR, "Нагрев секции 3 ОИ завершен")
                                isHeating3 = false
                                appendOneMessageToLog(
                                    LogTag.MESSAGE,
                                    "Запущен отсчет времени для 3 ОИ ${currentSection + 1} секции."
                                )
                            }

                            val allTime =
                                (controller.tableValuesTest1[0].place1time.value.toString().replace(",", ".")
                                    .toDouble() * 60).toInt()

                            val callbackTimer = CallbackTimer(
                                tickPeriod = 1.seconds, tickTimes = allTime,
                                tickJob = {
                                    if (!tickJobInProcess) {
                                        tickJobInProcess = true
                                        if (!controller.isExperimentRunning) it.stop()
                                        runLater {
                                            mainView.labelTimeRemaining3.text =
                                                "Осталось: " + toHHmmss(
                                                    (allTime - it.getCurrentTicks()) * 1000L
                                                )
                                        }
                                        tickJobInProcess = false
                                    }
                                }
                            )
                            when (currentSection) {
                                0 -> {
                                    unixTimeWork31 = System.currentTimeMillis()
                                }
                                1 -> {
                                    unixTimeWork32 = System.currentTimeMillis()
                                }
                                2 -> {
                                    unixTimeWork33 = System.currentTimeMillis()
                                }
                                3 -> {
                                    unixTimeWork34 = System.currentTimeMillis()
                                }
                                4 -> {
                                    unixTimeWork35 = System.currentTimeMillis()
                                }
                                5 -> {
                                    unixTimeWork36 = System.currentTimeMillis()
                                }
                            }
                            heatingSection3()


                            while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() && callbackTimer.isRunning) {
                                checkProtections()
                                sleep(2000)
                            }

                            when (currentSection) {
                                0 -> {
                                    unixTimeEnd31 = System.currentTimeMillis()
                                }
                                1 -> {
                                    unixTimeEnd32 = System.currentTimeMillis()
                                }
                                2 -> {
                                    unixTimeEnd33 = System.currentTimeMillis()
                                }
                                3 -> {
                                    unixTimeEnd34 = System.currentTimeMillis()
                                }
                                4 -> {
                                    unixTimeEnd35 = System.currentTimeMillis()
                                }
                                5 -> {
                                    unixTimeEnd36 = System.currentTimeMillis()
                                }
                            }

                            isNeedHeating3 = false
                            if (currentSection < 5) {
                                appendOneMessageToLog(LogTag.MESSAGE, "Переход к следующей секции")
                            } else {
                                allProcessEnded3 = true
                                appendOneMessageToLog(LogTag.MESSAGE, "Ожидание завершения")
                            }
                            sleepWhile(5)
                        }
                    }
                }
            } else {
                allProcessEnded3 = true
            }

            while (controller.isExperimentRunning && (!allProcessEnded1 || !allProcessEnded2 || !allProcessEnded3)) {
                sleep(100)
            }

            if (mainView.place1Prop.value) {
                gv238.reset()
            }
            if (mainView.place2Prop.value) {
                gv239.reset()
            }
            if (mainView.place3Prop.value) {
                gv240.reset()
            }

            while (tickJobInProcess) {
                sleep(100)
            }

            appendMessageToLog(LogTag.MESSAGE, "Испытание завершено")
            owenPR.offAllKMs()
            setResult()

            soundWarning(2)

            finalizeExperiment()

            if (listOfValues11.isNotEmpty() || listOfValues21.isNotEmpty() || listOfValues31.isNotEmpty()) {
                saveProtocolToDB()
                Singleton.currentProtocol = transaction {
                    Protocol.all().toList().asObservable()
                }.last()
                runLater {
                    confirm(
                        "Печать протокола",
                        "Испытание завершено. Хотите напечатать протокол?",
                        ButtonType.YES, ButtonType.NO,
                        owner = mainView.currentWindow,
                        title = "Печать"
                    ) {
                        if (mainView.place1Prop.value && mainView.place2Prop.value && mainView.place3Prop.value) {
                            saveProtocolAsWorkbook(Singleton.currentProtocol)
                            Desktop.getDesktop().print(File("protocol.xlsx"))
                        } else {
                            var protocol = Singleton.currentProtocol
                            if (mainView.place1Prop.value) {
                                val protocolRotorBlade = transaction {
                                    ProtocolRotorBlade.new {
                                        date = protocol.date
                                        time = protocol.time
                                        dateEnd = protocol.dateEnd
                                        timeEnd = protocol.timeEnd
                                        cipher = protocol.cipher1
                                        productName = protocol.productNumber1
                                        operator = protocol.operator
                                        temp1 = protocol.temp11
                                        temp2 = protocol.temp12
                                        temp3 = protocol.temp13
                                        temp4 = protocol.temp14
                                        temp5 = protocol.temp15
                                        temp6 = protocol.temp16
                                        voltage1 = protocol.voltage11
                                        voltage2 = protocol.voltage12
                                        voltage3 = protocol.voltage13
                                        voltage4 = protocol.voltage14
                                        voltage5 = protocol.voltage15
                                        voltage6 = protocol.voltage16
                                        amperage1 = protocol.amperage11
                                        amperage2 = protocol.amperage12
                                        amperage3 = protocol.amperage13
                                        amperage4 = protocol.amperage14
                                        amperage5 = protocol.amperage15
                                        amperage6 = protocol.amperage16
                                        NUMBER_DATE_ATTESTATION = protocol.NUMBER_DATE_ATTESTATION
                                        NAME_OF_OPERATION = protocol.NAME_OF_OPERATION
                                        NUMBER_CONTROLLER = protocol.NUMBER_CONTROLLER
                                        T1 = protocol.T1
                                        T2 = protocol.T2
                                        T3 = protocol.T3
                                        T4 = protocol.T4
                                        T5 = protocol.T5
                                        T6 = protocol.T6

                                        unixTimeStartProtocol1 = protocol.unixTimeStartProtocol11
                                        unixTimeStartProtocol2 = protocol.unixTimeStartProtocol12
                                        unixTimeStartProtocol3 = protocol.unixTimeStartProtocol13
                                        unixTimeStartProtocol4 = protocol.unixTimeStartProtocol14
                                        unixTimeStartProtocol5 = protocol.unixTimeStartProtocol15
                                        unixTimeStartProtocol6 = protocol.unixTimeStartProtocol16
                                        unixTimeWorkProtocol1 = protocol.unixTimeWorkProtocol11
                                        unixTimeWorkProtocol2 = protocol.unixTimeWorkProtocol12
                                        unixTimeWorkProtocol3 = protocol.unixTimeWorkProtocol13
                                        unixTimeWorkProtocol4 = protocol.unixTimeWorkProtocol14
                                        unixTimeWorkProtocol5 = protocol.unixTimeWorkProtocol15
                                        unixTimeWorkProtocol6 = protocol.unixTimeWorkProtocol16
                                        unixTimeEndProtocol1 = protocol.unixTimeEndProtocol11
                                        unixTimeEndProtocol2 = protocol.unixTimeEndProtocol12
                                        unixTimeEndProtocol3 = protocol.unixTimeEndProtocol13
                                        unixTimeEndProtocol4 = protocol.unixTimeEndProtocol14
                                        unixTimeEndProtocol5 = protocol.unixTimeEndProtocol15
                                        unixTimeEndProtocol6 = protocol.unixTimeEndProtocol16
                                    }
                                }
                                saveProtocolAsWorkbook(protocolRotorBlade)
                                Desktop.getDesktop().print(File("protocol1RotorBlade.xlsx"))
                            }
                            if (mainView.place2Prop.value) {
                                protocol = Singleton.currentProtocol
                                val protocolRotorBlade = transaction {
                                    ProtocolRotorBlade.new {
                                        date = protocol.date
                                        time = protocol.time
                                        dateEnd = protocol.dateEnd
                                        timeEnd = protocol.timeEnd
                                        cipher = protocol.cipher2
                                        productName = protocol.productNumber2
                                        operator = protocol.operator
                                        temp1 = protocol.temp21
                                        temp2 = protocol.temp22
                                        temp3 = protocol.temp23
                                        temp4 = protocol.temp24
                                        temp5 = protocol.temp25
                                        temp6 = protocol.temp26
                                        voltage1 = protocol.voltage21
                                        voltage2 = protocol.voltage22
                                        voltage3 = protocol.voltage23
                                        voltage4 = protocol.voltage24
                                        voltage5 = protocol.voltage25
                                        voltage6 = protocol.voltage26
                                        amperage1 = protocol.amperage21
                                        amperage2 = protocol.amperage22
                                        amperage3 = protocol.amperage23
                                        amperage4 = protocol.amperage24
                                        amperage5 = protocol.amperage25
                                        amperage6 = protocol.amperage26
                                        NUMBER_DATE_ATTESTATION = protocol.NUMBER_DATE_ATTESTATION
                                        NAME_OF_OPERATION = protocol.NAME_OF_OPERATION
                                        NUMBER_CONTROLLER = protocol.NUMBER_CONTROLLER
                                        T1 = protocol.T7
                                        T2 = protocol.T8
                                        T3 = protocol.T9
                                        T4 = protocol.T10
                                        T5 = protocol.T11
                                        T6 = protocol.T12

                                        unixTimeStartProtocol1 = protocol.unixTimeStartProtocol21
                                        unixTimeStartProtocol2 = protocol.unixTimeStartProtocol22
                                        unixTimeStartProtocol3 = protocol.unixTimeStartProtocol23
                                        unixTimeStartProtocol4 = protocol.unixTimeStartProtocol24
                                        unixTimeStartProtocol5 = protocol.unixTimeStartProtocol25
                                        unixTimeStartProtocol6 = protocol.unixTimeStartProtocol26
                                        unixTimeWorkProtocol1 = protocol.unixTimeWorkProtocol21
                                        unixTimeWorkProtocol2 = protocol.unixTimeWorkProtocol22
                                        unixTimeWorkProtocol3 = protocol.unixTimeWorkProtocol23
                                        unixTimeWorkProtocol4 = protocol.unixTimeWorkProtocol24
                                        unixTimeWorkProtocol5 = protocol.unixTimeWorkProtocol25
                                        unixTimeWorkProtocol6 = protocol.unixTimeWorkProtocol26
                                        unixTimeEndProtocol1 = protocol.unixTimeEndProtocol21
                                        unixTimeEndProtocol2 = protocol.unixTimeEndProtocol22
                                        unixTimeEndProtocol3 = protocol.unixTimeEndProtocol23
                                        unixTimeEndProtocol4 = protocol.unixTimeEndProtocol24
                                        unixTimeEndProtocol5 = protocol.unixTimeEndProtocol25
                                        unixTimeEndProtocol6 = protocol.unixTimeEndProtocol26
                                    }
                                }
                                saveProtocolAsWorkbook(protocolRotorBlade)
                                Desktop.getDesktop().print(File("protocol1RotorBlade.xlsx"))
                            }
                            if (mainView.place3Prop.value) {
                                protocol = Singleton.currentProtocol
                                val protocolRotorBlade = transaction {
                                    ProtocolRotorBlade.new {
                                        date = protocol.date
                                        time = protocol.time
                                        dateEnd = protocol.dateEnd
                                        timeEnd = protocol.timeEnd
                                        cipher = protocol.cipher3
                                        productName = protocol.productNumber3
                                        operator = protocol.operator
                                        temp1 = protocol.temp31
                                        temp2 = protocol.temp32
                                        temp3 = protocol.temp33
                                        temp4 = protocol.temp34
                                        temp5 = protocol.temp35
                                        temp6 = protocol.temp36
                                        voltage1 = protocol.voltage31
                                        voltage2 = protocol.voltage32
                                        voltage3 = protocol.voltage33
                                        voltage4 = protocol.voltage34
                                        voltage5 = protocol.voltage35
                                        voltage6 = protocol.voltage36
                                        amperage1 = protocol.amperage31
                                        amperage2 = protocol.amperage32
                                        amperage3 = protocol.amperage33
                                        amperage4 = protocol.amperage34
                                        amperage5 = protocol.amperage35
                                        amperage6 = protocol.amperage36
                                        NUMBER_DATE_ATTESTATION = protocol.NUMBER_DATE_ATTESTATION
                                        NAME_OF_OPERATION = protocol.NAME_OF_OPERATION
                                        NUMBER_CONTROLLER = protocol.NUMBER_CONTROLLER
                                        T1 = protocol.T13
                                        T2 = protocol.T14
                                        T3 = protocol.T15
                                        T4 = protocol.T16
                                        T5 = protocol.T17
                                        T6 = protocol.T18

                                        unixTimeStartProtocol1 = protocol.unixTimeStartProtocol31
                                        unixTimeStartProtocol2 = protocol.unixTimeStartProtocol32
                                        unixTimeStartProtocol3 = protocol.unixTimeStartProtocol33
                                        unixTimeStartProtocol4 = protocol.unixTimeStartProtocol34
                                        unixTimeStartProtocol5 = protocol.unixTimeStartProtocol35
                                        unixTimeStartProtocol6 = protocol.unixTimeStartProtocol36
                                        unixTimeWorkProtocol1 = protocol.unixTimeWorkProtocol31
                                        unixTimeWorkProtocol2 = protocol.unixTimeWorkProtocol32
                                        unixTimeWorkProtocol3 = protocol.unixTimeWorkProtocol33
                                        unixTimeWorkProtocol4 = protocol.unixTimeWorkProtocol34
                                        unixTimeWorkProtocol5 = protocol.unixTimeWorkProtocol35
                                        unixTimeWorkProtocol6 = protocol.unixTimeWorkProtocol36
                                        unixTimeEndProtocol1 = protocol.unixTimeEndProtocol31
                                        unixTimeEndProtocol2 = protocol.unixTimeEndProtocol32
                                        unixTimeEndProtocol3 = protocol.unixTimeEndProtocol33
                                        unixTimeEndProtocol4 = protocol.unixTimeEndProtocol34
                                        unixTimeEndProtocol5 = protocol.unixTimeEndProtocol35
                                        unixTimeEndProtocol6 = protocol.unixTimeEndProtocol36
                                    }
                                }
                                saveProtocolAsWorkbook(protocolRotorBlade)
                                Desktop.getDesktop().print(File("protocol1RotorBlade.xlsx"))
                            }
                        }
                    }
                }
            }

        }
    }

    private fun saveProtocolToDB() {
        val dateFormatter = SimpleDateFormat("dd.MM.y")
        val timeFormatter = SimpleDateFormat("HH:mm:ss")
        val unixTimeEnd = System.currentTimeMillis()

        val protocolVars = transaction {
            ProtocolVars.all().toList().asObservable()
        }.first()

        transaction {
            Singleton.currentProtocol = Protocol.new {
                date = dateFormatter.format(unixTimeStart).toString()
                time = timeFormatter.format(unixTimeStart).toString()
                dateEnd = dateFormatter.format(unixTimeEnd).toString()
                timeEnd = timeFormatter.format(unixTimeEnd).toString()
                operator = controller.position1
                cipher1 = mainView.tfCipher1.text.toString()
                productNumber1 = mainView.tfProductNumber1.text.toString()
                cipher2 = mainView.tfCipher2.text.toString()
                productNumber2 = mainView.tfProductNumber2.text.toString()
                cipher3 = mainView.tfCipher3.text.toString()
                productNumber3 = mainView.tfProductNumber3.text.toString()
                temp11 = listOfValues11.toString()
                temp12 = listOfValues12.toString()
                temp13 = listOfValues13.toString()
                temp14 = listOfValues14.toString()
                temp15 = listOfValues15.toString()
                temp16 = listOfValues16.toString()
                temp21 = listOfValues21.toString()
                temp22 = listOfValues22.toString()
                temp23 = listOfValues23.toString()
                temp24 = listOfValues24.toString()
                temp25 = listOfValues25.toString()
                temp26 = listOfValues26.toString()
                temp31 = listOfValues31.toString()
                temp32 = listOfValues32.toString()
                temp33 = listOfValues33.toString()
                temp34 = listOfValues34.toString()
                temp35 = listOfValues35.toString()
                temp36 = listOfValues36.toString()
                voltage11 = listOfValuesVoltage11.toString()
                voltage12 = listOfValuesVoltage12.toString()
                voltage13 = listOfValuesVoltage13.toString()
                voltage14 = listOfValuesVoltage14.toString()
                voltage15 = listOfValuesVoltage15.toString()
                voltage16 = listOfValuesVoltage16.toString()
                voltage21 = listOfValuesVoltage21.toString()
                voltage22 = listOfValuesVoltage22.toString()
                voltage23 = listOfValuesVoltage23.toString()
                voltage24 = listOfValuesVoltage24.toString()
                voltage25 = listOfValuesVoltage25.toString()
                voltage26 = listOfValuesVoltage26.toString()
                voltage31 = listOfValuesVoltage31.toString()
                voltage32 = listOfValuesVoltage32.toString()
                voltage33 = listOfValuesVoltage33.toString()
                voltage34 = listOfValuesVoltage34.toString()
                voltage35 = listOfValuesVoltage35.toString()
                voltage36 = listOfValuesVoltage36.toString()
                amperage11 = listOfValuesAmperage11.toString()
                amperage12 = listOfValuesAmperage12.toString()
                amperage13 = listOfValuesAmperage13.toString()
                amperage14 = listOfValuesAmperage14.toString()
                amperage15 = listOfValuesAmperage15.toString()
                amperage16 = listOfValuesAmperage16.toString()
                amperage21 = listOfValuesAmperage21.toString()
                amperage22 = listOfValuesAmperage22.toString()
                amperage23 = listOfValuesAmperage23.toString()
                amperage24 = listOfValuesAmperage24.toString()
                amperage25 = listOfValuesAmperage25.toString()
                amperage26 = listOfValuesAmperage26.toString()
                amperage31 = listOfValuesAmperage31.toString()
                amperage32 = listOfValuesAmperage32.toString()
                amperage33 = listOfValuesAmperage33.toString()
                amperage34 = listOfValuesAmperage34.toString()
                amperage35 = listOfValuesAmperage35.toString()
                amperage36 = listOfValuesAmperage36.toString()
                NUMBER_DATE_ATTESTATION = protocolVars.NUMBER_DATE_ATTESTATION
                NAME_OF_OPERATION = protocolVars.NAME_OF_OPERATION
                NUMBER_CONTROLLER = protocolVars.NUMBER_CONTROLLER
                T1 = protocolVars.T1
                T2 = protocolVars.T2
                T3 = protocolVars.T3
                T4 = protocolVars.T4
                T5 = protocolVars.T5
                T6 = protocolVars.T6
                T7 = protocolVars.T7
                T8 = protocolVars.T8
                T9 = protocolVars.T9
                T10 = protocolVars.T10
                T11 = protocolVars.T11
                T12 = protocolVars.T12
                T13 = protocolVars.T13
                T14 = protocolVars.T14
                T15 = protocolVars.T15
                T16 = protocolVars.T16
                T17 = protocolVars.T17
                T18 = protocolVars.T18

                unixTimeStartProtocol11 = if (unixTimeStart11 != 0L) {
                    timeFormatter.format(unixTimeStart11).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol12 = if (unixTimeStart12 != 0L) {
                    timeFormatter.format(unixTimeStart12).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol13 = if (unixTimeStart13 != 0L) {
                    timeFormatter.format(unixTimeStart13).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol14 = if (unixTimeStart14 != 0L) {
                    timeFormatter.format(unixTimeStart14).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol15 = if (unixTimeStart15 != 0L) {
                    timeFormatter.format(unixTimeStart15).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol16 = if (unixTimeStart16 != 0L) {
                    timeFormatter.format(unixTimeStart16).toString()
                } else {
                    "0"
                }

                unixTimeWorkProtocol11 = if (unixTimeWork11 != 0L) {
                    timeFormatter.format(unixTimeWork11).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol12 = if (unixTimeWork12 != 0L) {
                    timeFormatter.format(unixTimeWork12).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol13 = if (unixTimeWork13 != 0L) {
                    timeFormatter.format(unixTimeWork13).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol14 = if (unixTimeWork14 != 0L) {
                    timeFormatter.format(unixTimeWork14).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol15 = if (unixTimeWork15 != 0L) {
                    timeFormatter.format(unixTimeWork15).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol16 = if (unixTimeWork16 != 0L) {
                    timeFormatter.format(unixTimeWork16).toString()
                } else {
                    "0"
                }

                unixTimeEndProtocol11 = if (unixTimeEnd11 != 0L) {
                    timeFormatter.format(unixTimeEnd11).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol12 = if (unixTimeEnd12 != 0L) {
                    timeFormatter.format(unixTimeEnd12).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol13 = if (unixTimeEnd13 != 0L) {
                    timeFormatter.format(unixTimeEnd13).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol14 = if (unixTimeEnd14 != 0L) {
                    timeFormatter.format(unixTimeEnd14).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol15 = if (unixTimeEnd15 != 0L) {
                    timeFormatter.format(unixTimeEnd15).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol16 = if (unixTimeEnd16 != 0L) {
                    timeFormatter.format(unixTimeEnd16).toString()
                } else {
                    "0"
                }


                unixTimeStartProtocol21 = if (unixTimeStart21 != 0L) {
                    timeFormatter.format(unixTimeStart21).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol22 = if (unixTimeStart22 != 0L) {
                    timeFormatter.format(unixTimeStart22).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol23 = if (unixTimeStart23 != 0L) {
                    timeFormatter.format(unixTimeStart23).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol24 = if (unixTimeStart24 != 0L) {
                    timeFormatter.format(unixTimeStart24).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol25 = if (unixTimeStart25 != 0L) {
                    timeFormatter.format(unixTimeStart25).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol26 = if (unixTimeStart26 != 0L) {
                    timeFormatter.format(unixTimeStart26).toString()
                } else {
                    "0"
                }

                unixTimeWorkProtocol21 = if (unixTimeWork21 != 0L) {
                    timeFormatter.format(unixTimeWork21).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol22 = if (unixTimeWork22 != 0L) {
                    timeFormatter.format(unixTimeWork22).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol23 = if (unixTimeWork23 != 0L) {
                    timeFormatter.format(unixTimeWork23).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol24 = if (unixTimeWork24 != 0L) {
                    timeFormatter.format(unixTimeWork24).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol25 = if (unixTimeWork25 != 0L) {
                    timeFormatter.format(unixTimeWork25).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol26 = if (unixTimeWork26 != 0L) {
                    timeFormatter.format(unixTimeWork26).toString()
                } else {
                    "0"
                }

                unixTimeEndProtocol21 = if (unixTimeEnd21 != 0L) {
                    timeFormatter.format(unixTimeEnd21).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol22 = if (unixTimeEnd22 != 0L) {
                    timeFormatter.format(unixTimeEnd22).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol23 = if (unixTimeEnd23 != 0L) {
                    timeFormatter.format(unixTimeEnd23).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol24 = if (unixTimeEnd24 != 0L) {
                    timeFormatter.format(unixTimeEnd24).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol25 = if (unixTimeEnd25 != 0L) {
                    timeFormatter.format(unixTimeEnd25).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol26 = if (unixTimeEnd26 != 0L) {
                    timeFormatter.format(unixTimeEnd26).toString()
                } else {
                    "0"
                }


                unixTimeStartProtocol31 = if (unixTimeStart31 != 0L) {
                    timeFormatter.format(unixTimeStart31).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol32 = if (unixTimeStart32 != 0L) {
                    timeFormatter.format(unixTimeStart32).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol33 = if (unixTimeStart33 != 0L) {
                    timeFormatter.format(unixTimeStart33).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol34 = if (unixTimeStart34 != 0L) {
                    timeFormatter.format(unixTimeStart34).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol35 = if (unixTimeStart35 != 0L) {
                    timeFormatter.format(unixTimeStart35).toString()
                } else {
                    "0"
                }
                unixTimeStartProtocol36 = if (unixTimeStart36 != 0L) {
                    timeFormatter.format(unixTimeStart36).toString()
                } else {
                    "0"
                }

                unixTimeWorkProtocol31 = if (unixTimeWork31 != 0L) {
                    timeFormatter.format(unixTimeWork31).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol32 = if (unixTimeWork32 != 0L) {
                    timeFormatter.format(unixTimeWork32).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol33 = if (unixTimeWork33 != 0L) {
                    timeFormatter.format(unixTimeWork33).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol34 = if (unixTimeWork34 != 0L) {
                    timeFormatter.format(unixTimeWork34).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol35 = if (unixTimeWork35 != 0L) {
                    timeFormatter.format(unixTimeWork35).toString()
                } else {
                    "0"
                }
                unixTimeWorkProtocol36 = if (unixTimeWork36 != 0L) {
                    timeFormatter.format(unixTimeWork36).toString()
                } else {
                    "0"
                }

                unixTimeEndProtocol31 = if (unixTimeEnd31 != 0L) {
                    timeFormatter.format(unixTimeEnd31).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol32 = if (unixTimeEnd32 != 0L) {
                    timeFormatter.format(unixTimeEnd32).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol33 = if (unixTimeEnd33 != 0L) {
                    timeFormatter.format(unixTimeEnd33).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol34 = if (unixTimeEnd34 != 0L) {
                    timeFormatter.format(unixTimeEnd34).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol35 = if (unixTimeEnd35 != 0L) {
                    timeFormatter.format(unixTimeEnd35).toString()
                } else {
                    "0"
                }
                unixTimeEndProtocol36 = if (unixTimeEnd36 != 0L) {
                    timeFormatter.format(unixTimeEnd36).toString()
                } else {
                    "0"
                }
            }
        }
    }

    private fun checkProtections() {
        if (
            measuringt11 > 75 || measuringt12 > 75 || measuringt13 > 75 || measuringt14 > 75 || measuringt15 > 75 || measuringt16 > 75 ||
            measuringt21 > 75 || measuringt22 > 75 || measuringt23 > 75 || measuringt24 > 75 || measuringt25 > 75 || measuringt26 > 75 ||
            measuringt31 > 75 || measuringt32 > 75 || measuringt33 > 75 || measuringt34 > 75 || measuringt35 > 75 || measuringt36 > 75
        ) {
            soundError()
            soundWarning(1)
        }
    }

    private fun clearTable() {
        controller.tableValuesPlace1Test1[0].place1voltage.value = "0.0"
        controller.tableValuesPlace1Test1[1].place1voltage.value = "0.0"
        controller.tableValuesPlace1Test1[2].place1voltage.value = "0.0"
        controller.tableValuesPlace1Test1[3].place1voltage.value = "0.0"
        controller.tableValuesPlace1Test1[4].place1voltage.value = "0.0"
        controller.tableValuesPlace1Test1[5].place1voltage.value = "0.0"
        controller.tableValuesPlace2Test1[0].place2voltage.value = "0.0"
        controller.tableValuesPlace2Test1[1].place2voltage.value = "0.0"
        controller.tableValuesPlace2Test1[2].place2voltage.value = "0.0"
        controller.tableValuesPlace2Test1[3].place2voltage.value = "0.0"
        controller.tableValuesPlace2Test1[4].place2voltage.value = "0.0"
        controller.tableValuesPlace2Test1[5].place2voltage.value = "0.0"
        controller.tableValuesPlace3Test1[0].place3voltage.value = "0.0"
        controller.tableValuesPlace3Test1[1].place3voltage.value = "0.0"
        controller.tableValuesPlace3Test1[2].place3voltage.value = "0.0"
        controller.tableValuesPlace3Test1[3].place3voltage.value = "0.0"
        controller.tableValuesPlace3Test1[4].place3voltage.value = "0.0"
        controller.tableValuesPlace3Test1[5].place3voltage.value = "0.0"
        controller.tableValuesPlace1Test1[0].place1amperage.value = "0.0"
        controller.tableValuesPlace1Test1[1].place1amperage.value = "0.0"
        controller.tableValuesPlace1Test1[2].place1amperage.value = "0.0"
        controller.tableValuesPlace1Test1[3].place1amperage.value = "0.0"
        controller.tableValuesPlace1Test1[4].place1amperage.value = "0.0"
        controller.tableValuesPlace1Test1[5].place1amperage.value = "0.0"
        controller.tableValuesPlace2Test1[0].place2amperage.value = "0.0"
        controller.tableValuesPlace2Test1[1].place2amperage.value = "0.0"
        controller.tableValuesPlace2Test1[2].place2amperage.value = "0.0"
        controller.tableValuesPlace2Test1[3].place2amperage.value = "0.0"
        controller.tableValuesPlace2Test1[4].place2amperage.value = "0.0"
        controller.tableValuesPlace2Test1[5].place2amperage.value = "0.0"
        controller.tableValuesPlace3Test1[0].place3amperage.value = "0.0"
        controller.tableValuesPlace3Test1[1].place3amperage.value = "0.0"
        controller.tableValuesPlace3Test1[2].place3amperage.value = "0.0"
        controller.tableValuesPlace3Test1[3].place3amperage.value = "0.0"
        controller.tableValuesPlace3Test1[4].place3amperage.value = "0.0"
        controller.tableValuesPlace3Test1[5].place3amperage.value = "0.0"
        controller.tableValuesPlace1Test1[0].place1temp.value = "0.0"
        controller.tableValuesPlace1Test1[1].place1temp.value = "0.0"
        controller.tableValuesPlace1Test1[2].place1temp.value = "0.0"
        controller.tableValuesPlace1Test1[3].place1temp.value = "0.0"
        controller.tableValuesPlace1Test1[4].place1temp.value = "0.0"
        controller.tableValuesPlace1Test1[5].place1temp.value = "0.0"
        controller.tableValuesPlace2Test1[0].place2temp.value = "0.0"
        controller.tableValuesPlace2Test1[1].place2temp.value = "0.0"
        controller.tableValuesPlace2Test1[2].place2temp.value = "0.0"
        controller.tableValuesPlace2Test1[3].place2temp.value = "0.0"
        controller.tableValuesPlace2Test1[4].place2temp.value = "0.0"
        controller.tableValuesPlace2Test1[5].place2temp.value = "0.0"
        controller.tableValuesPlace3Test1[0].place3temp.value = "0.0"
        controller.tableValuesPlace3Test1[1].place3temp.value = "0.0"
        controller.tableValuesPlace3Test1[2].place3temp.value = "0.0"
        controller.tableValuesPlace3Test1[3].place3temp.value = "0.0"
        controller.tableValuesPlace3Test1[4].place3temp.value = "0.0"
        controller.tableValuesPlace3Test1[5].place3temp.value = "0.0"
    }

    private fun collectValuesInProtocolInTimer() {
        thread(isDaemon = true) {
            while (controller.isExperimentRunning) {
                if (measuringt11 < -50 || measuringt11 > 100) {
                    listOfValues11.add("-99.9")
                } else {
                    listOfValues11.add(String.format("%.1f", measuringt11))
                    listOfValuesVoltage11.add(String.format("%.1f", measuringU11))
                    listOfValuesAmperage11.add(String.format("%.1f", measuringI11))
                }
                if (measuringt12 < -50 || measuringt12 > 100) {
                    listOfValues12.add("-99.9")
                } else {
                    listOfValues12.add(String.format("%.1f", measuringt12))
                    listOfValuesVoltage12.add(String.format("%.1f", measuringU12))
                    listOfValuesAmperage12.add(String.format("%.1f", measuringI12))
                }
                if (measuringt13 < -50 || measuringt13 > 100) {
                    listOfValues13.add("-99.9")
                } else {
                    listOfValues13.add(String.format("%.1f", measuringt13))
                    listOfValuesVoltage13.add(String.format("%.1f", measuringU13))
                    listOfValuesAmperage13.add(String.format("%.1f", measuringI13))
                }
                if (measuringt14 < -50 || measuringt14 > 100) {
                    listOfValues14.add("-99.9")
                } else {
                    listOfValues14.add(String.format("%.1f", measuringt14))
                    listOfValuesVoltage14.add(String.format("%.1f", measuringU14))
                    listOfValuesAmperage14.add(String.format("%.1f", measuringI14))
                }
                if (measuringt15 < -50 || measuringt15 > 100) {
                    listOfValues15.add("-99.9")
                } else {
                    listOfValues15.add(String.format("%.1f", measuringt15))
                    listOfValuesVoltage15.add(String.format("%.1f", measuringU15))
                    listOfValuesAmperage15.add(String.format("%.1f", measuringI15))
                }
                if (measuringt16 < -50 || measuringt16 > 100) {
                    listOfValues16.add("-99.9")
                } else {
                    listOfValues16.add(String.format("%.1f", measuringt16))
                    listOfValuesVoltage16.add(String.format("%.1f", measuringU16))
                    listOfValuesAmperage16.add(String.format("%.1f", measuringI16))
                }

                if (measuringt21 < -50 || measuringt21 > 100) {
                    listOfValues21.add("-99.9")
                } else {
                    listOfValues21.add(String.format("%.1f", measuringt21))
                    listOfValuesVoltage21.add(String.format("%.1f", measuringU21))
                    listOfValuesAmperage21.add(String.format("%.1f", measuringI21))
                }
                if (measuringt22 < -50 || measuringt22 > 100) {
                    listOfValues22.add("-99.9")
                } else {
                    listOfValues22.add(String.format("%.1f", measuringt22))
                    listOfValuesVoltage22.add(String.format("%.1f", measuringU22))
                    listOfValuesAmperage22.add(String.format("%.1f", measuringI22))
                }
                if (measuringt23 < -50 || measuringt23 > 100) {
                    listOfValues23.add("-99.9")
                } else {
                    listOfValues23.add(String.format("%.1f", measuringt23))
                    listOfValuesVoltage23.add(String.format("%.1f", measuringU23))
                    listOfValuesAmperage23.add(String.format("%.1f", measuringI23))
                }
                if (measuringt24 < -50 || measuringt24 > 100) {
                    listOfValues24.add("-99.9")
                } else {
                    listOfValues24.add(String.format("%.1f", measuringt24))
                    listOfValuesVoltage24.add(String.format("%.1f", measuringU24))
                    listOfValuesAmperage24.add(String.format("%.1f", measuringI24))
                }
                if (measuringt25 < -50 || measuringt25 > 100) {
                    listOfValues25.add("-99.9")
                } else {
                    listOfValues25.add(String.format("%.1f", measuringt25))
                    listOfValuesVoltage25.add(String.format("%.1f", measuringU25))
                    listOfValuesAmperage25.add(String.format("%.1f", measuringI25))
                }
                if (measuringt26 < -50 || measuringt26 > 100) {
                    listOfValues26.add("-99.9")
                } else {
                    listOfValues26.add(String.format("%.1f", measuringt26))
                    listOfValuesVoltage26.add(String.format("%.1f", measuringU26))
                    listOfValuesAmperage26.add(String.format("%.1f", measuringI26))
                }

                if (measuringt31 < -50 || measuringt31 > 100) {
                    listOfValues31.add("-99.9")
                } else {
                    listOfValues31.add(String.format("%.1f", measuringt31))
                    listOfValuesVoltage31.add(String.format("%.1f", measuringU31))
                    listOfValuesAmperage31.add(String.format("%.1f", measuringI31))
                }
                if (measuringt32 < -50 || measuringt32 > 100) {
                    listOfValues32.add("-99.9")
                } else {
                    listOfValues32.add(String.format("%.1f", measuringt32))
                    listOfValuesVoltage32.add(String.format("%.1f", measuringU32))
                    listOfValuesAmperage32.add(String.format("%.1f", measuringI32))
                }
                if (measuringt33 < -50 || measuringt33 > 100) {
                    listOfValues33.add("-99.9")
                } else {
                    listOfValues33.add(String.format("%.1f", measuringt33))
                    listOfValuesVoltage33.add(String.format("%.1f", measuringU33))
                    listOfValuesAmperage33.add(String.format("%.1f", measuringI33))
                }
                if (measuringt34 < -50 || measuringt34 > 100) {
                    listOfValues34.add("-99.9")
                } else {
                    listOfValues34.add(String.format("%.1f", measuringt34))
                    listOfValuesVoltage34.add(String.format("%.1f", measuringU34))
                    listOfValuesAmperage34.add(String.format("%.1f", measuringI34))
                }
                if (measuringt35 < -50 || measuringt35 > 100) {
                    listOfValues35.add("-99.9")
                } else {
                    listOfValues35.add(String.format("%.1f", measuringt35))
                    listOfValuesVoltage35.add(String.format("%.1f", measuringU35))
                    listOfValuesAmperage35.add(String.format("%.1f", measuringI35))
                }
                if (measuringt36 < -50 || measuringt36 > 100) {
                    listOfValues36.add("-99.9")
                } else {
                    listOfValues36.add(String.format("%.1f", measuringt36))
                    listOfValuesVoltage36.add(String.format("%.1f", measuringU36))
                    listOfValuesAmperage36.add(String.format("%.1f", measuringI36))
                }
                sleep(1000)
            }
        }
    }

    private fun setValuesInTimer() {
        if (mainView.place1Prop.value) {
            controller.tableValuesPlace1Test1[0].place1voltage.value =
                formatRealNumber(measuringU11).toString()
            controller.tableValuesPlace1Test1[1].place1voltage.value =
                formatRealNumber(measuringU12).toString()
            controller.tableValuesPlace1Test1[2].place1voltage.value =
                formatRealNumber(measuringU13).toString()
            controller.tableValuesPlace1Test1[3].place1voltage.value =
                formatRealNumber(measuringU14).toString()
            controller.tableValuesPlace1Test1[4].place1voltage.value =
                formatRealNumber(measuringU15).toString()
            controller.tableValuesPlace1Test1[5].place1voltage.value =
                formatRealNumber(measuringU16).toString()
        }
        if (mainView.place2Prop.value) {
            controller.tableValuesPlace2Test1[0].place2voltage.value =
                formatRealNumber(measuringU21).toString()
            controller.tableValuesPlace2Test1[1].place2voltage.value =
                formatRealNumber(measuringU22).toString()
            controller.tableValuesPlace2Test1[2].place2voltage.value =
                formatRealNumber(measuringU23).toString()
            controller.tableValuesPlace2Test1[3].place2voltage.value =
                formatRealNumber(measuringU24).toString()
            controller.tableValuesPlace2Test1[4].place2voltage.value =
                formatRealNumber(measuringU25).toString()
            controller.tableValuesPlace2Test1[5].place2voltage.value =
                formatRealNumber(measuringU26).toString()
        }
        if (mainView.place3Prop.value) {
            controller.tableValuesPlace3Test1[0].place3voltage.value =
                formatRealNumber(measuringU31).toString()
            controller.tableValuesPlace3Test1[1].place3voltage.value =
                formatRealNumber(measuringU32).toString()
            controller.tableValuesPlace3Test1[2].place3voltage.value =
                formatRealNumber(measuringU33).toString()
            controller.tableValuesPlace3Test1[3].place3voltage.value =
                formatRealNumber(measuringU34).toString()
            controller.tableValuesPlace3Test1[4].place3voltage.value =
                formatRealNumber(measuringU35).toString()
            controller.tableValuesPlace3Test1[5].place3voltage.value =
                formatRealNumber(measuringU36).toString()
        }

        if (mainView.place1Prop.value) {
            controller.tableValuesPlace1Test1[0].place1amperage.value =
                String.format("%.2f", measuringI11)
            controller.tableValuesPlace1Test1[1].place1amperage.value =
                String.format("%.2f", measuringI12)
            controller.tableValuesPlace1Test1[2].place1amperage.value =
                String.format("%.2f", measuringI13)
            controller.tableValuesPlace1Test1[3].place1amperage.value =
                String.format("%.2f", measuringI14)
            controller.tableValuesPlace1Test1[4].place1amperage.value =
                String.format("%.2f", measuringI15)
            controller.tableValuesPlace1Test1[5].place1amperage.value =
                String.format("%.2f", measuringI16)
        }
        if (mainView.place2Prop.value) {
            controller.tableValuesPlace2Test1[0].place2amperage.value =
                String.format("%.2f", measuringI21)
            controller.tableValuesPlace2Test1[1].place2amperage.value =
                String.format("%.2f", measuringI22)
            controller.tableValuesPlace2Test1[2].place2amperage.value =
                String.format("%.2f", measuringI23)
            controller.tableValuesPlace2Test1[3].place2amperage.value =
                String.format("%.2f", measuringI24)
            controller.tableValuesPlace2Test1[4].place2amperage.value =
                String.format("%.2f", measuringI25)
            controller.tableValuesPlace2Test1[5].place2amperage.value =
                String.format("%.2f", measuringI26)
        }
        if (mainView.place3Prop.value) {
            controller.tableValuesPlace3Test1[0].place3amperage.value =
                String.format("%.2f", measuringI31)
            controller.tableValuesPlace3Test1[1].place3amperage.value =
                String.format("%.2f", measuringI32)
            controller.tableValuesPlace3Test1[2].place3amperage.value =
                String.format("%.2f", measuringI33)
            controller.tableValuesPlace3Test1[3].place3amperage.value =
                String.format("%.2f", measuringI34)
            controller.tableValuesPlace3Test1[4].place3amperage.value =
                String.format("%.2f", measuringI35)
            controller.tableValuesPlace3Test1[5].place3amperage.value =
                String.format("%.2f", measuringI36)
        }

        if (mainView.place1Prop.value) {
            if (measuringt11 < -50 || measuringt11 > 100) {
                controller.tableValuesPlace1Test1[0].place1temp.value = "-.--"
            } else {
                controller.tableValuesPlace1Test1[0].place1temp.value =
                    String.format("%.1f", measuringt11)
            }
            if (measuringt12 < -50 || measuringt12 > 100) {
                controller.tableValuesPlace1Test1[1].place1temp.value = "-.--"
            } else {
                controller.tableValuesPlace1Test1[1].place1temp.value =
                    String.format("%.1f", measuringt12)
            }
            if (measuringt13 < -50 || measuringt13 > 100) {
                controller.tableValuesPlace1Test1[2].place1temp.value = "-.--"
            } else {
                controller.tableValuesPlace1Test1[2].place1temp.value =
                    String.format("%.1f", measuringt13)
            }
            if (measuringt14 < -50 || measuringt14 > 100) {
                controller.tableValuesPlace1Test1[3].place1temp.value = "-.--"
            } else {
                controller.tableValuesPlace1Test1[3].place1temp.value =
                    String.format("%.1f", measuringt14)
            }
            if (measuringt15 < -50 || measuringt15 > 100) {
                controller.tableValuesPlace1Test1[4].place1temp.value = "-.--"
            } else {
                controller.tableValuesPlace1Test1[4].place1temp.value =
                    String.format("%.1f", measuringt15)
            }
            if (measuringt16 < -50 || measuringt16 > 100) {
                controller.tableValuesPlace1Test1[5].place1temp.value = "-.--"
            } else {
                controller.tableValuesPlace1Test1[5].place1temp.value =
                    String.format("%.1f", measuringt16)
            }
        }
        if (mainView.place2Prop.value) {
            if (measuringt21 < -50 || measuringt21 > 100) {
                controller.tableValuesPlace2Test1[0].place2temp.value = "-.--"
            } else {
                controller.tableValuesPlace2Test1[0].place2temp.value =
                    String.format("%.1f", measuringt21)
            }
            if (measuringt22 < -50 || measuringt22 > 100) {
                controller.tableValuesPlace2Test1[1].place2temp.value = "-.--"
            } else {
                controller.tableValuesPlace2Test1[1].place2temp.value =
                    String.format("%.1f", measuringt22)
            }
            if (measuringt23 < -50 || measuringt23 > 100) {
                controller.tableValuesPlace2Test1[2].place2temp.value = "-.--"
            } else {
                controller.tableValuesPlace2Test1[2].place2temp.value =
                    String.format("%.1f", measuringt23)
            }
            if (measuringt24 < -50 || measuringt24 > 100) {
                controller.tableValuesPlace2Test1[3].place2temp.value = "-.--"
            } else {
                controller.tableValuesPlace2Test1[3].place2temp.value =
                    String.format("%.1f", measuringt24)
            }
            if (measuringt25 < -50 || measuringt25 > 100) {
                controller.tableValuesPlace2Test1[4].place2temp.value = "-.--"
            } else {
                controller.tableValuesPlace2Test1[4].place2temp.value =
                    String.format("%.1f", measuringt25)
            }
            if (measuringt26 < -50 || measuringt26 > 100) {
                controller.tableValuesPlace2Test1[5].place2temp.value = "-.--"
            } else {
                controller.tableValuesPlace2Test1[5].place2temp.value =
                    String.format("%.1f", measuringt26)
            }
        }
        if (mainView.place3Prop.value) {
            if (measuringt31 < -50 || measuringt31 > 100) {
                controller.tableValuesPlace3Test1[0].place3temp.value = "-.--"
            } else {
                controller.tableValuesPlace3Test1[0].place3temp.value =
                    String.format("%.1f", measuringt31)
            }
            if (measuringt32 < -50 || measuringt32 > 100) {
                controller.tableValuesPlace3Test1[1].place3temp.value = "-.--"
            } else {
                controller.tableValuesPlace3Test1[1].place3temp.value =
                    String.format("%.1f", measuringt32)
            }
            if (measuringt33 < -50 || measuringt33 > 100) {
                controller.tableValuesPlace3Test1[2].place3temp.value = "-.--"
            } else {
                controller.tableValuesPlace3Test1[2].place3temp.value =
                    String.format("%.1f", measuringt33)
            }
            if (measuringt34 < -50 || measuringt34 > 100) {
                controller.tableValuesPlace3Test1[3].place3temp.value = "-.--"
            } else {
                controller.tableValuesPlace3Test1[3].place3temp.value =
                    String.format("%.1f", measuringt34)
            }
            if (measuringt35 < -50 || measuringt35 > 100) {
                controller.tableValuesPlace3Test1[4].place3temp.value = "-.--"
            } else {
                controller.tableValuesPlace3Test1[4].place3temp.value =
                    String.format("%.1f", measuringt35)
            }
            if (measuringt36 < -50 || measuringt36 > 100) {
                controller.tableValuesPlace3Test1[5].place3temp.value = "-.--"
            } else {
                controller.tableValuesPlace3Test1[5].place3temp.value =
                    String.format("%.1f", measuringt36)
            }
        }
    }

    private fun initValuesForRegulation() {
        thread(isDaemon = true) {
            while (controller.isExperimentRunning && isLatrRegulation) {
                runLater {
                    setValuesInTable()
                }
                sleep(300)
            }
        }
    }

    private fun firstHeatingSection1() {
        isHeating1 = true
        thread(isDaemon = true) {
            while (controller.isExperimentRunning && isHeating1) {
                runLater {
                    setValuesInTable()
                }
                sleep(300)
            }
        }

        var isNeedHeatingFirst1 = mainView.place1Prop.value
        var isNeedHeatingSecond1 = mainView.place1Prop.value
        var isNeedHeatingThird1 = mainView.place1Prop.value


        while (controller.isExperimentRunning && isNeedHeatingFirst1) {
            val percent = 0.4
            when (currentStage1) {
                0 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt11 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on11()
                            isNeedHeatingFirst1 = true
                        } else {
                            owenPR.off11()
                            isNeedHeatingFirst1 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt12 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on12()
                            isNeedHeatingFirst1 = true
                        } else {
                            owenPR.off12()
                            isNeedHeatingFirst1 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt13 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on13()
                            isNeedHeatingFirst1 = true
                        } else {
                            owenPR.off13()
                            isNeedHeatingFirst1 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt14 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on14()
                            isNeedHeatingFirst1 = true
                        } else {
                            owenPR.off14()
                            isNeedHeatingFirst1 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt15 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on15()
                            isNeedHeatingFirst1 = true
                        } else {
                            owenPR.off15()
                            isNeedHeatingFirst1 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt16 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on16()
                            isNeedHeatingFirst1 = true
                        } else {
                            owenPR.off16()
                            isNeedHeatingFirst1 = false
                        }
                    }
                }
            }
            var time = 15 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 25 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 40 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
        }

        while (controller.isExperimentRunning && isNeedHeatingSecond1) {
            val percent = 0.3
            when (currentStage1) {
                0 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt11 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on11()
                            isNeedHeatingSecond1 = true
                        } else {
                            owenPR.off11()
                            isNeedHeatingSecond1 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt12 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on12()
                            isNeedHeatingSecond1 = true
                        } else {
                            owenPR.off12()
                            isNeedHeatingSecond1 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt13 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on13()
                            isNeedHeatingSecond1 = true
                        } else {
                            owenPR.off13()
                            isNeedHeatingSecond1 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt14 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on14()
                            isNeedHeatingSecond1 = true
                        } else {
                            owenPR.off14()
                            isNeedHeatingSecond1 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt15 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on15()
                            isNeedHeatingSecond1 = true
                        } else {
                            owenPR.off15()
                            isNeedHeatingSecond1 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt16 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on16()
                            isNeedHeatingSecond1 = true
                        } else {
                            owenPR.off16()
                            isNeedHeatingSecond1 = false
                        }
                    }
                }
            }
            var time = 10 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 40 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
        }

        while (controller.isExperimentRunning && isNeedHeatingThird1) {
            val const = 5
            when (currentStage1) {
                0 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt11 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on11()
                            isNeedHeatingThird1 = true
                        } else {
                            owenPR.off11()
                            isNeedHeatingThird1 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt12 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on12()
                            isNeedHeatingThird1 = true
                        } else {
                            owenPR.off12()
                            isNeedHeatingThird1 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt13 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on13()
                            isNeedHeatingThird1 = true
                        } else {
                            owenPR.off13()
                            isNeedHeatingThird1 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt14 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on14()
                            isNeedHeatingThird1 = true
                        } else {
                            owenPR.off14()
                            isNeedHeatingThird1 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt15 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on15()
                            isNeedHeatingThird1 = true
                        } else {
                            owenPR.off15()
                            isNeedHeatingThird1 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place1Prop.value) {
                        if (measuringt16 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on16()
                            isNeedHeatingThird1 = true
                        } else {
                            owenPR.off16()
                            isNeedHeatingThird1 = false
                        }
                    }
                }
            }
            var time = 5 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 5 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 60 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 10 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
                var isNeedBreak = false
                when (currentStage1) {
                    0 -> {
                        if (mainView.place1Prop.value) {
                            isNeedBreak = measuringt11 > temperature - const
                        }
                    }
                    1 -> {
                        if (mainView.place1Prop.value) {
                            isNeedBreak = measuringt12 > temperature - const
                        }
                    }
                    2 -> {
                        if (mainView.place1Prop.value) {
                            isNeedBreak = measuringt13 > temperature - const
                        }
                    }
                    3 -> {
                        if (mainView.place1Prop.value) {
                            isNeedBreak = measuringt14 > temperature - const
                        }
                    }
                    4 -> {
                        if (mainView.place1Prop.value) {
                            isNeedBreak = measuringt15 > temperature - const
                        }
                    }
                    5 -> {
                        if (mainView.place1Prop.value) {
                            isNeedBreak = measuringt16 > temperature - const
                        }
                    }
                }
                if (isNeedBreak) break
            }
        }
    }

    private fun firstHeatingSection2() {
        isHeating2 = true
        thread(isDaemon = true) {
            while (controller.isExperimentRunning && isHeating2) {
                runLater {
                    setValuesInTable()
                }
                sleep(300)
            }
        }

        var isNeedHeatingFirst2 = mainView.place2Prop.value

        var isNeedHeatingSecond2 = mainView.place2Prop.value

        var isNeedHeatingThird2 = mainView.place2Prop.value


        while (controller.isExperimentRunning && isNeedHeatingFirst2) {
            val percent = 0.4
            when (currentStage2) {
                0 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt21 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on21()
                            isNeedHeatingFirst2 = true
                        } else {
                            owenPR.off21()
                            isNeedHeatingFirst2 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt22 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on22()
                            isNeedHeatingFirst2 = true
                        } else {
                            owenPR.off22()
                            isNeedHeatingFirst2 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt23 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on23()
                            isNeedHeatingFirst2 = true
                        } else {
                            owenPR.off23()
                            isNeedHeatingFirst2 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt24 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on24()
                            isNeedHeatingFirst2 = true
                        } else {
                            owenPR.off24()
                            isNeedHeatingFirst2 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt25 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on25()
                            isNeedHeatingFirst2 = true
                        } else {
                            owenPR.off25()
                            isNeedHeatingFirst2 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt26 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on26()
                            isNeedHeatingFirst2 = true
                        } else {
                            owenPR.off26()
                            isNeedHeatingFirst2 = false
                        }
                    }
                }
            }
            var time = 15 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 25 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 40 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
        }

        while (controller.isExperimentRunning && isNeedHeatingSecond2) {
            val percent = 0.3
            when (currentStage2) {
                0 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt21 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on21()
                            isNeedHeatingSecond2 = true
                        } else {
                            owenPR.off21()
                            isNeedHeatingSecond2 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt22 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on22()
                            isNeedHeatingSecond2 = true
                        } else {
                            owenPR.off22()
                            isNeedHeatingSecond2 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt23 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on23()
                            isNeedHeatingSecond2 = true
                        } else {
                            owenPR.off23()
                            isNeedHeatingSecond2 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt24 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on24()
                            isNeedHeatingSecond2 = true
                        } else {
                            owenPR.off24()
                            isNeedHeatingSecond2 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt25 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on25()
                            isNeedHeatingSecond2 = true
                        } else {
                            owenPR.off25()
                            isNeedHeatingSecond2 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt26 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on26()
                            isNeedHeatingSecond2 = true
                        } else {
                            owenPR.off26()
                            isNeedHeatingSecond2 = false
                        }
                    }
                }
            }
            var time = 10 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 40 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
        }

        while (controller.isExperimentRunning && isNeedHeatingThird2) {
            val const = 5
            when (currentStage2) {
                0 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt21 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on21()
                            isNeedHeatingThird2 = true
                        } else {
                            owenPR.off21()
                            isNeedHeatingThird2 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt22 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on22()
                            isNeedHeatingThird2 = true
                        } else {
                            owenPR.off22()
                            isNeedHeatingThird2 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt23 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on23()
                            isNeedHeatingThird2 = true
                        } else {
                            owenPR.off23()
                            isNeedHeatingThird2 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt24 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on24()
                            isNeedHeatingThird2 = true
                        } else {
                            owenPR.off24()
                            isNeedHeatingThird2 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt25 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on25()
                            isNeedHeatingThird2 = true
                        } else {
                            owenPR.off25()
                            isNeedHeatingThird2 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place2Prop.value) {
                        if (measuringt26 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on26()
                            isNeedHeatingThird2 = true
                        } else {
                            owenPR.off26()
                            isNeedHeatingThird2 = false
                        }
                    }
                }
            }
            var time = 5 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 5 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 60 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 10 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
                var isNeedBreak = false
                when (currentStage2) {
                    0 -> {
                        if (mainView.place2Prop.value) {
                            isNeedBreak = measuringt21 > temperature - const
                        }
                    }
                    1 -> {
                        if (mainView.place2Prop.value) {
                            isNeedBreak = measuringt22 > temperature - const
                        }
                    }
                    2 -> {
                        if (mainView.place2Prop.value) {
                            isNeedBreak = measuringt23 > temperature - const
                        }
                    }
                    3 -> {
                        if (mainView.place2Prop.value) {
                            isNeedBreak = measuringt24 > temperature - const
                        }
                    }
                    4 -> {
                        if (mainView.place2Prop.value) {
                            isNeedBreak = measuringt25 > temperature - const
                        }
                    }
                    5 -> {
                        if (mainView.place2Prop.value) {
                            isNeedBreak = measuringt26 > temperature - const
                        }
                    }
                }
                if (isNeedBreak) break
            }
        }
    }

    private fun firstHeatingSection3() {
        isHeating3 = true
        thread(isDaemon = true) {
            while (controller.isExperimentRunning && isHeating3) {
                runLater {
                    setValuesInTable()
                }
                sleep(300)
            }
        }

        var isNeedHeatingFirst3 = mainView.place3Prop.value

        var isNeedHeatingSecond3 = mainView.place3Prop.value

        var isNeedHeatingThird3 = mainView.place3Prop.value


        while (controller.isExperimentRunning && isNeedHeatingFirst3) {
            val percent = 0.4
            when (currentStage3) {
                0 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt31 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on31()
                            isNeedHeatingFirst3 = true
                        } else {
                            owenPR.off31()
                            isNeedHeatingFirst3 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt32 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on32()
                            isNeedHeatingFirst3 = true
                        } else {
                            owenPR.off32()
                            isNeedHeatingFirst3 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt33 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on33()
                            isNeedHeatingFirst3 = true
                        } else {
                            owenPR.off33()
                            isNeedHeatingFirst3 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt34 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on34()
                            isNeedHeatingFirst3 = true
                        } else {
                            owenPR.off34()
                            isNeedHeatingFirst3 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt35 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on35()
                            isNeedHeatingFirst3 = true
                        } else {
                            owenPR.off35()
                            isNeedHeatingFirst3 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt36 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on36()
                            isNeedHeatingFirst3 = true
                        } else {
                            owenPR.off36()
                            isNeedHeatingFirst3 = false
                        }
                    }
                }
            }
            var time = 15 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 25 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 40 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
        }

        while (controller.isExperimentRunning && isNeedHeatingSecond3) {
            val percent = 0.3
            when (currentStage3) {
                0 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt31 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on31()
                            isNeedHeatingSecond3 = true
                        } else {
                            owenPR.off31()
                            isNeedHeatingSecond3 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt32 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on32()
                            isNeedHeatingSecond3 = true
                        } else {
                            owenPR.off32()
                            isNeedHeatingSecond3 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt33 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on33()
                            isNeedHeatingSecond3 = true
                        } else {
                            owenPR.off33()
                            isNeedHeatingSecond3 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt34 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on34()
                            isNeedHeatingSecond3 = true
                        } else {
                            owenPR.off34()
                            isNeedHeatingSecond3 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt35 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on35()
                            isNeedHeatingSecond3 = true
                        } else {
                            owenPR.off35()
                            isNeedHeatingSecond3 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt36 < temperature * (1 - percent) && controller.isExperimentRunning) {
                            owenPR.on36()
                            isNeedHeatingSecond3 = true
                        } else {
                            owenPR.off36()
                            isNeedHeatingSecond3 = false
                        }
                    }
                }
            }
            var time = 10 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 40 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 20 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
        }

        while (controller.isExperimentRunning && isNeedHeatingThird3) {
            val const = 5
            when (currentStage3) {
                0 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt31 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on31()
                            isNeedHeatingThird3 = true
                        } else {
                            owenPR.off31()
                            isNeedHeatingThird3 = false
                        }
                    }
                }
                1 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt32 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on32()
                            isNeedHeatingThird3 = true
                        } else {
                            owenPR.off32()
                            isNeedHeatingThird3 = false
                        }
                    }
                }
                2 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt33 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on33()
                            isNeedHeatingThird3 = true
                        } else {
                            owenPR.off33()
                            isNeedHeatingThird3 = false
                        }
                    }
                }
                3 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt34 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on34()
                            isNeedHeatingThird3 = true
                        } else {
                            owenPR.off34()
                            isNeedHeatingThird3 = false
                        }
                    }
                }
                4 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt35 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on35()
                            isNeedHeatingThird3 = true
                        } else {
                            owenPR.off35()
                            isNeedHeatingThird3 = false
                        }
                    }
                }
                5 -> {
                    if (mainView.place3Prop.value) {
                        if (measuringt36 < temperature - const && controller.isExperimentRunning) {
                            owenPR.on36()
                            isNeedHeatingThird3 = true
                        } else {
                            owenPR.off36()
                            isNeedHeatingThird3 = false
                        }
                    }
                }
            }
            var time = 5 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 5 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
            }
            if (controller.isExperimentRunning) {
                owenPR.offAllKMs()
            }
            time = 60 * 100
            if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                time = 10 * 100
            }
            while (controller.isExperimentRunning && time-- > 0) {
                sleep(10)
                var isNeedBreak = false
                when (currentStage3) {
                    0 -> {
                        if (mainView.place3Prop.value) {
                            isNeedBreak = measuringt31 > temperature - const
                        }
                    }
                    1 -> {
                        if (mainView.place3Prop.value) {
                            isNeedBreak = measuringt32 > temperature - const
                        }
                    }
                    2 -> {
                        if (mainView.place3Prop.value) {
                            isNeedBreak = measuringt33 > temperature - const
                        }
                    }
                    3 -> {
                        if (mainView.place3Prop.value) {
                            isNeedBreak = measuringt34 > temperature - const
                        }
                    }
                    4 -> {
                        if (mainView.place3Prop.value) {
                            isNeedBreak = measuringt35 > temperature - const
                        }
                    }
                    5 -> {
                        if (mainView.place3Prop.value) {
                            isNeedBreak = measuringt36 > temperature - const
                        }
                    }
                }
                if (isNeedBreak) break
            }
        }
    }

    private fun heatingSection1() {
        isNeedHeating1 = true
        val const = 0
        thread(isDaemon = true) {
            while (controller.isExperimentRunning && isNeedHeating1) {
                when (currentStage1) {
                    0 -> {
                        if (mainView.place1Prop.value) {
                            if (measuringt11 < temperature - const && controller.isExperimentRunning && isNeedHeating1) {
                                owenPR.on11()
                            }
                        }
                    }
                    1 -> {
                        if (mainView.place1Prop.value) {
                            if (measuringt12 < temperature - const && controller.isExperimentRunning && isNeedHeating1) {
                                owenPR.on12()
                            }
                        }
                    }
                    2 -> {
                        if (mainView.place1Prop.value) {
                            if (measuringt13 < temperature - const && controller.isExperimentRunning && isNeedHeating1) {
                                owenPR.on13()
                            }
                        }
                    }
                    3 -> {
                        if (mainView.place1Prop.value) {
                            if (measuringt14 < temperature - const && controller.isExperimentRunning && isNeedHeating1) {
                                owenPR.on14()
                            }
                        }
                    }
                    4 -> {
                        if (mainView.place1Prop.value) {
                            if (measuringt15 < temperature - const && controller.isExperimentRunning && isNeedHeating1) {
                                owenPR.on15()
                            }
                        }
                    }
                    5 -> {
                        if (mainView.place1Prop.value) {
                            if (measuringt16 < temperature - const && controller.isExperimentRunning && isNeedHeating1) {
                                owenPR.on16()
                            }
                        }
                    }
                }
                var time = 5 * 100
                if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                    time = 4 * 100
                }
                while (controller.isExperimentRunning && isNeedHeating1 && time-- > 0) {
                    sleep(10)
                }
                if (controller.isExperimentRunning) {
                    owenPR.offAllKMs()
                }
                time = 40 * 100
                if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                    time = 6 * 100
                }
                while (controller.isExperimentRunning && isNeedHeating1 && time-- > 0) {
                    sleep(10)
                }
            }
        }
    }

    private fun heatingSection2() {
        isNeedHeating2 = true
        val const = 0
        thread(isDaemon = true) {
            while (controller.isExperimentRunning && isNeedHeating2) {
                when (currentStage2) {
                    0 -> {
                        if (mainView.place2Prop.value) {
                            if (measuringt21 < temperature - const && controller.isExperimentRunning && isNeedHeating2) {
                                owenPR.on21()
                            }
                        }
                    }
                    1 -> {
                        if (mainView.place2Prop.value) {
                            if (measuringt22 < temperature - const && controller.isExperimentRunning && isNeedHeating2) {
                                owenPR.on22()
                            }
                        }
                    }
                    2 -> {
                        if (mainView.place2Prop.value) {
                            if (measuringt23 < temperature - const && controller.isExperimentRunning && isNeedHeating2) {
                                owenPR.on23()
                            }
                        }
                    }
                    3 -> {
                        if (mainView.place2Prop.value) {
                            if (measuringt24 < temperature - const && controller.isExperimentRunning && isNeedHeating2) {
                                owenPR.on24()
                            }
                        }
                    }
                    4 -> {
                        if (mainView.place2Prop.value) {
                            if (measuringt25 < temperature - const && controller.isExperimentRunning && isNeedHeating2) {
                                owenPR.on25()
                            }
                        }
                    }
                    5 -> {
                        if (mainView.place2Prop.value) {
                            if (measuringt26 < temperature - const && controller.isExperimentRunning && isNeedHeating2) {
                                owenPR.on26()
                            }
                        }
                    }
                }
                var time = 5 * 100
                if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                    time = 4 * 100
                }
                while (controller.isExperimentRunning && isNeedHeating2 && time-- > 0) {
                    sleep(10)
                }
                if (controller.isExperimentRunning) {
                    owenPR.offAllKMs()
                }
                time = 40 * 100
                if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                    time = 6 * 100
                }
                while (controller.isExperimentRunning && isNeedHeating2 && time-- > 0) {
                    sleep(10)
                }
            }
        }
    }

    private fun heatingSection3() {
        isNeedHeating3 = true
        val const = 0
        thread(isDaemon = true) {
            while (controller.isExperimentRunning && isNeedHeating3) {
                when (currentStage3) {
                    0 -> {
                        if (mainView.place3Prop.value) {
                            if (measuringt31 < temperature - const && controller.isExperimentRunning && isNeedHeating3) {
                                owenPR.on31()
                            }
                        }
                    }
                    1 -> {
                        if (mainView.place3Prop.value) {
                            if (measuringt32 < temperature - const && controller.isExperimentRunning && isNeedHeating3) {
                                owenPR.on32()
                            }
                        }
                    }
                    2 -> {
                        if (mainView.place3Prop.value) {
                            if (measuringt33 < temperature - const && controller.isExperimentRunning && isNeedHeating3) {
                                owenPR.on33()
                            }
                        }
                    }
                    3 -> {
                        if (mainView.place3Prop.value) {
                            if (measuringt34 < temperature - const && controller.isExperimentRunning && isNeedHeating3) {
                                owenPR.on34()
                            }
                        }
                    }
                    4 -> {
                        if (mainView.place3Prop.value) {
                            if (measuringt35 < temperature - const && controller.isExperimentRunning && isNeedHeating3) {
                                owenPR.on35()
                            }
                        }
                    }
                    5 -> {
                        if (mainView.place3Prop.value) {
                            if (measuringt36 < temperature - const && controller.isExperimentRunning && isNeedHeating3) {
                                owenPR.on36()
                            }
                        }
                    }
                }
                var time = 5 * 100
                if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                    time = 4 * 100
                }
                while (controller.isExperimentRunning && isNeedHeating3 && time-- > 0) {
                    sleep(10)
                }
                if (controller.isExperimentRunning) {
                    owenPR.offAllKMs()
                }
                time = 40 * 100
                if (mainView.comboBoxTestItem.selectedItem?.name.toString() == "Накладка") {
                    time = 6 * 100
                }
                while (controller.isExperimentRunning && isNeedHeating3 && time-- > 0) {
                    sleep(10)
                }
            }
        }
    }

    private fun setValuesInTable() {
        if (mainView.place1Prop.value) {
            controller.tableValuesPlace1Test1[0].place1voltage.value =
                formatRealNumber(measuringU11).toString()
            controller.tableValuesPlace1Test1[1].place1voltage.value =
                formatRealNumber(measuringU12).toString()
            controller.tableValuesPlace1Test1[2].place1voltage.value =
                formatRealNumber(measuringU13).toString()
            controller.tableValuesPlace1Test1[3].place1voltage.value =
                formatRealNumber(measuringU14).toString()
            controller.tableValuesPlace1Test1[4].place1voltage.value =
                formatRealNumber(measuringU15).toString()
            controller.tableValuesPlace1Test1[5].place1voltage.value =
                formatRealNumber(measuringU16).toString()
        }
        if (mainView.place2Prop.value) {
            controller.tableValuesPlace2Test1[0].place2voltage.value =
                formatRealNumber(measuringU21).toString()
            controller.tableValuesPlace2Test1[1].place2voltage.value =
                formatRealNumber(measuringU22).toString()
            controller.tableValuesPlace2Test1[2].place2voltage.value =
                formatRealNumber(measuringU23).toString()
            controller.tableValuesPlace2Test1[3].place2voltage.value =
                formatRealNumber(measuringU24).toString()
            controller.tableValuesPlace2Test1[4].place2voltage.value =
                formatRealNumber(measuringU25).toString()
            controller.tableValuesPlace2Test1[5].place2voltage.value =
                formatRealNumber(measuringU26).toString()
        }
        if (mainView.place3Prop.value) {
            controller.tableValuesPlace3Test1[0].place3voltage.value =
                formatRealNumber(measuringU31).toString()
            controller.tableValuesPlace3Test1[1].place3voltage.value =
                formatRealNumber(measuringU32).toString()
            controller.tableValuesPlace3Test1[2].place3voltage.value =
                formatRealNumber(measuringU33).toString()
            controller.tableValuesPlace3Test1[3].place3voltage.value =
                formatRealNumber(measuringU34).toString()
            controller.tableValuesPlace3Test1[4].place3voltage.value =
                formatRealNumber(measuringU35).toString()
            controller.tableValuesPlace3Test1[5].place3voltage.value =
                formatRealNumber(measuringU36).toString()
        }
        if (mainView.place1Prop.value) {
            controller.tableValuesPlace1Test1[0].place1amperage.value = String.format("%.2f", measuringI11)
            controller.tableValuesPlace1Test1[1].place1amperage.value = String.format("%.2f", measuringI12)
            controller.tableValuesPlace1Test1[2].place1amperage.value = String.format("%.2f", measuringI13)
            controller.tableValuesPlace1Test1[3].place1amperage.value = String.format("%.2f", measuringI14)
            controller.tableValuesPlace1Test1[4].place1amperage.value = String.format("%.2f", measuringI15)
            controller.tableValuesPlace1Test1[5].place1amperage.value = String.format("%.2f", measuringI16)
        }
        if (mainView.place2Prop.value) {
            controller.tableValuesPlace2Test1[0].place2amperage.value = String.format("%.2f", measuringI21)
            controller.tableValuesPlace2Test1[1].place2amperage.value = String.format("%.2f", measuringI22)
            controller.tableValuesPlace2Test1[2].place2amperage.value = String.format("%.2f", measuringI23)
            controller.tableValuesPlace2Test1[3].place2amperage.value = String.format("%.2f", measuringI24)
            controller.tableValuesPlace2Test1[4].place2amperage.value = String.format("%.2f", measuringI25)
            controller.tableValuesPlace2Test1[5].place2amperage.value = String.format("%.2f", measuringI26)
        }
        if (mainView.place3Prop.value) {
            controller.tableValuesPlace3Test1[0].place3amperage.value = String.format("%.2f", measuringI31)
            controller.tableValuesPlace3Test1[1].place3amperage.value = String.format("%.2f", measuringI32)
            controller.tableValuesPlace3Test1[2].place3amperage.value = String.format("%.2f", measuringI33)
            controller.tableValuesPlace3Test1[3].place3amperage.value = String.format("%.2f", measuringI34)
            controller.tableValuesPlace3Test1[4].place3amperage.value = String.format("%.2f", measuringI35)
            controller.tableValuesPlace3Test1[5].place3amperage.value = String.format("%.2f", measuringI36)
        }

        if (mainView.place1Prop.value) {
            controller.tableValuesPlace1Test1[0].place1temp.value = String.format("%.1f", measuringt11)
            controller.tableValuesPlace1Test1[1].place1temp.value = String.format("%.1f", measuringt12)
            controller.tableValuesPlace1Test1[2].place1temp.value = String.format("%.1f", measuringt13)
            controller.tableValuesPlace1Test1[3].place1temp.value = String.format("%.1f", measuringt14)
            controller.tableValuesPlace1Test1[4].place1temp.value = String.format("%.1f", measuringt15)
            controller.tableValuesPlace1Test1[5].place1temp.value = String.format("%.1f", measuringt16)
        }
        if (mainView.place2Prop.value) {
            controller.tableValuesPlace2Test1[0].place2temp.value = String.format("%.1f", measuringt21)
            controller.tableValuesPlace2Test1[1].place2temp.value = String.format("%.1f", measuringt22)
            controller.tableValuesPlace2Test1[2].place2temp.value = String.format("%.1f", measuringt23)
            controller.tableValuesPlace2Test1[3].place2temp.value = String.format("%.1f", measuringt24)
            controller.tableValuesPlace2Test1[4].place2temp.value = String.format("%.1f", measuringt25)
            controller.tableValuesPlace2Test1[5].place2temp.value = String.format("%.1f", measuringt26)
        }
        if (mainView.place3Prop.value) {
            controller.tableValuesPlace3Test1[0].place3temp.value = String.format("%.1f", measuringt31)
            controller.tableValuesPlace3Test1[1].place3temp.value = String.format("%.1f", measuringt32)
            controller.tableValuesPlace3Test1[2].place3temp.value = String.format("%.1f", measuringt33)
            controller.tableValuesPlace3Test1[3].place3temp.value = String.format("%.1f", measuringt34)
            controller.tableValuesPlace3Test1[4].place3temp.value = String.format("%.1f", measuringt35)
            controller.tableValuesPlace3Test1[5].place3temp.value = String.format("%.1f", measuringt36)
        }
    }

    private fun setResult() {
        if (controller.cause.isNotEmpty()) {
            appendMessageToLog(LogTag.ERROR, "Испытание прервано по причине: ${controller.cause}")
            soundError()
        } else if (!controller.isDevicesRespondingTest1()) {
            appendMessageToLog(LogTag.ERROR, "Испытание прервано по причине: \nпотеряна связь с устройствами")
            soundError()
        } else {
            appendMessageToLog(LogTag.MESSAGE, "Испытание завершено успешно")
            soundError()
        }
    }

    private fun finalizeExperiment() {
        controller.isExperimentRunning = false
        isExperimentEnded = true
        owenPR.offAllKMs()
        CommunicationModel.clearPollingRegisters()
        runLater {
            mainView.labelTimeRemaining1.text = ""
            mainView.buttonStart.isDisable = false
            mainView.buttonStop.isDisable = true
            mainView.mainMenuBar.isDisable = false
            mainView.comboBoxTests.isDisable = false
            mainView.comboBoxTestItem.isDisable = false
            mainView.initTableTest1.isDisable = false
            mainView.initTableTest2.isDisable = false
            mainView.checkBoxPlace1.isDisable = false
            mainView.checkBoxPlace2.isDisable = false
            mainView.checkBoxPlace3.isDisable = false
        }
    }

    private fun presetAccurateParameters(latrDevice: AvemLatrController) {
        latrDevice.presetParameters(
            LatrControllerConfiguration(
                minDuttyPercent = 50f,
                maxDuttyPercent = 50f,
                corridor = 0.2f,
                delta = 0.03f,
                timePulseMin = 100,
                timePulseMax = 100
            )
        )
    }

    private fun isLatrInErrorMode(latrDevice: AvemLatrController) =
        when (latrDevice.getRegisterById(AvemLatrModel.DEVICE_STATUS).value) {
            0x81 -> {
                appendMessageToLog(LogTag.ERROR, "Сработал верхний концевик при движении вверх")
                true
            }
            0x82 -> {
                appendMessageToLog(LogTag.ERROR, "Сработал нижний концевик при движении вниз")
                true
            }
            0x83 -> {
                appendMessageToLog(LogTag.ERROR, "Сработали оба концевика")
                true
            }
            0x84 -> {
                appendMessageToLog(LogTag.ERROR, "Время регулирования превысило заданное")
                true
            }
            0x85 -> {
                appendMessageToLog(LogTag.ERROR, "Застревание АРН")
                true
            }
            else -> false
        }

    private fun soundWarning(times: Int) {
        thread(isDaemon = true) {
            for (i in 0 until times) {
                owenPR.onSound()
                sleep(1000)
                owenPR.offSound()
                sleep(1000)
            }
        }
    }

    private fun sleepWhile(second: Int) {
        var timer = second * 10
        while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() && timer-- > 0) {
            sleep(100)
        }
    }

    private fun initLatr(latrDevice: AvemLatrController) {
        try {
            latrDevice.reset {}
        } catch (e: LatrStuckException) {
            appendMessageToLog(LogTag.ERROR, "Ошибка возврата АРН в начало. АРН застрял.")
            controller.cause = "Застрял ЛАТР"
        }
    }

    private fun configLatr(latrDevice: AvemLatrController, voltage: Double) {
        appendMessageToLog(LogTag.MESSAGE, "Конфигурирование и запуск АРН")
        presetRoughParameters(latrDevice)
        latrDevice.start((voltage * 1.03).toFloat())
    }

    private fun presetRoughParameters(latrDevice: AvemLatrController) {
        latrDevice.presetParameters(
            LatrControllerConfiguration(
                minDuttyPercent = 35f,
                maxDuttyPercent = 35f,
                corridor = 0.2f,
                delta = 0.03f,
                timePulseMin = 100,
                timePulseMax = 100
            )
        )
    }

    private fun accurateRegulate(
        voltageDevice: ParmaController,
        voltageRegister: String,
        latrDevice: AvemLatrController,
        voltage: Double
    ) {
        if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
            presetAccurateParameters(latrDevice)

            while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() &&
                (voltageDevice.getRegisterById(voltageRegister).value.toFloat() <= voltage * 0.97f ||
                        voltageDevice.getRegisterById(voltageRegister).value.toFloat() >= voltage * 1.03f)
            ) {
                if (isLatrInErrorMode(latrDevice)) {
                    controller.cause = "Ошибка контроллера АРН"
                }
                appendOneMessageToLog(LogTag.MESSAGE, "Точная регулировка напряжения")

                if (voltageDevice.getRegisterById(voltageRegister).value.toFloat() <= voltage * 0.97f) {
                    latrDevice.plusVoltage()
                    sleep(200)
                }
                if (voltageDevice.getRegisterById(voltageRegister).value.toFloat() >= voltage * 1.03f) {
                    latrDevice.minusVoltage()
                    sleep(200)
                }
                latrDevice.stop()
                sleep(1000)
            }
        }
        latrDevice.stop()
    }

}
