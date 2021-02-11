package ru.avem.posshop.controllers

import javafx.application.Platform
import javafx.scene.text.Text
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.posshop.communication.model.CommunicationModel
import ru.avem.posshop.communication.model.devices.LatrStuckException
import ru.avem.posshop.communication.model.devices.avem.avem4.Avem4Controller
import ru.avem.posshop.communication.model.devices.avem.avem7.Avem7Controller
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
import ru.avem.posshop.entities.TController
import ru.avem.posshop.utils.*
import ru.avem.posshop.view.MainView
import tornadofx.*
import java.text.SimpleDateFormat
import kotlin.concurrent.thread
import kotlin.experimental.and
import kotlin.time.ExperimentalTime

class Test1Controller : TController() {
    protected val owenPR = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.DD2) as OwenPrController
    protected val parma1 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA1) as ParmaController
    protected val parma2 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA2) as ParmaController
    protected val parma3 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA3) as ParmaController
    protected val parma4 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA4) as ParmaController
    protected val parma5 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA5) as ParmaController
    protected val parma6 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.PARMA6) as ParmaController
    protected val trm1 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM1) as Trm136Controller
    protected val trm2 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM2) as Trm136Controller
    protected val trm3 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.TRM3) as Trm136Controller
    protected val gv238 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV238) as AvemLatrController
    protected val gv239 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV239) as AvemLatrController
    protected val gv240 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV240) as AvemLatrController
    protected val a71 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A71) as Avem7Controller
    protected val a72 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A72) as Avem7Controller
    protected val a73 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A73) as Avem7Controller
    protected val a41 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A41) as Avem4Controller

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

    @Volatile
    private var trmStatus11: Boolean = false

    @Volatile
    private var trmStatus12: Boolean = false

    @Volatile
    private var trmStatus13: Boolean = false

    @Volatile
    private var trmStatus14: Boolean = false

    @Volatile
    private var trmStatus15: Boolean = false

    @Volatile
    private var trmStatus16: Boolean = false

    @Volatile
    private var trmStatus17: Boolean = false

    @Volatile
    private var trmStatus21: Boolean = false

    @Volatile
    private var trmStatus22: Boolean = false

    @Volatile
    private var trmStatus23: Boolean = false

    @Volatile
    private var trmStatus24: Boolean = false

    @Volatile
    private var trmStatus25: Boolean = false

    @Volatile
    private var trmStatus26: Boolean = false

    @Volatile
    private var trmStatus31: Boolean = false

    @Volatile
    private var trmStatus32: Boolean = false

    @Volatile
    private var trmStatus33: Boolean = false

    @Volatile
    private var trmStatus34: Boolean = false

    @Volatile
    private var trmStatus35: Boolean = false

    @Volatile
    private var trmStatus36: Boolean = false
    //endregion

    //region переменные для защит ПР
    @Volatile
    private var doorCabinet: Boolean = false

    @Volatile
    private var doorZone: Boolean = false

    @Volatile
    private var visibleGap: Boolean = false

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

    private var listOfAmperage11 = mutableListOf<String>()
    private var listOfAmperage12 = mutableListOf<String>()
    private var listOfAmperage13 = mutableListOf<String>()
    private var listOfAmperage14 = mutableListOf<String>()
    private var listOfAmperage15 = mutableListOf<String>()
    private var listOfAmperage16 = mutableListOf<String>()
    private var listOfAmperage21 = mutableListOf<String>()
    private var listOfAmperage22 = mutableListOf<String>()
    private var listOfAmperage23 = mutableListOf<String>()
    private var listOfAmperage24 = mutableListOf<String>()
    private var listOfAmperage25 = mutableListOf<String>()
    private var listOfAmperage26 = mutableListOf<String>()
    private var listOfAmperage31 = mutableListOf<String>()
    private var listOfAmperage32 = mutableListOf<String>()
    private var listOfAmperage33 = mutableListOf<String>()
    private var listOfAmperage34 = mutableListOf<String>()
    private var listOfAmperage35 = mutableListOf<String>()
    private var listOfAmperage36 = mutableListOf<String>()
    //endregion

    class RingBuffer<T>(private val size: Int) {
        private val innerStorage = mutableListOf<T>()
        private var position = 0

        fun add(value: T) {
            if (innerStorage.size != size) {
                innerStorage.add(value)
            } else {
                innerStorage[position] = value
                position++
            }
            if (position >= size) position %= size
        }

        fun isNotEmpty() = innerStorage.isNotEmpty()
    }

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

            visibleGap = value.toShort() and 8 > 0
            if (visibleGap) {
                controller.cause = "Не замкнут видимый разрыв"
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
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_2) { value ->
            measuringt12 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_3) { value ->
            measuringt13 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_4) { value ->
            measuringt14 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_5) { value ->
            measuringt15 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM1, Trm136Model.TEMPERATURE_6) { value ->
            measuringt16 = value.toDouble()
        }

        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_1) { value ->
            measuringt21 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_2) { value ->
            measuringt22 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_3) { value ->
            measuringt23 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_4) { value ->
            measuringt24 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_5) { value ->
            measuringt25 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM2, Trm136Model.TEMPERATURE_6) { value ->
            measuringt26 = value.toDouble()
        }

        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_1) { value ->
            measuringt31 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_2) { value ->
            measuringt32 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_3) { value ->
            measuringt33 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_4) { value ->
            measuringt34 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_5) { value ->
            measuringt35 = value.toDouble()
        }
        CommunicationModel.startPoll(CommunicationModel.DeviceID.TRM3, Trm136Model.TEMPERATURE_6) { value ->
            measuringt36 = value.toDouble()
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
            appendMessageToLog(LogTag.DEBUG, "Начало испытания")
            sleep(1000)

            runLater {
                mainView.labelTestStatus.text = ""
                mainView.labelTestStatusEnd1.text = ""
                mainView.labelTimeRemaining.text = ""
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
                startPollDevices()
                sleep(1000)
            }

            if (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                runLater {
                    Toast.makeText("Нажмите кнопку ПУСК").show(Toast.ToastType.WARNING)
                }
            }

            var timeToStart = 300
            while (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest1() && timeToStart-- > 0) {
                appendOneMessageToLog(LogTag.DEBUG, "Нажмите кнопку ПУСК")
                sleep(100)
            }

            if (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                controller.cause = "Не нажата кнопка ПУСК"
            }

            if (controller.isExperimentRunning && controller.isDevicesRespondingTest1()) {
                appendMessageToLog(LogTag.DEBUG, "Подготовка стенда")
                appendMessageToLog(LogTag.DEBUG, "Проверка датчиков")
                startCheckStatusTrmSensors()
                sleep(2000)
            }

            val allTime =
                (controller.tableValuesTest1[0].place1time.value.toString().replace(",", ".").toDouble() * 60).toInt()

            val callbackTimer = CallbackTimer(
                tickPeriod = 1.seconds, tickTimes = allTime,
                tickJob = {
                    if (!tickJobInProcess) {
                        tickJobInProcess = true
                        if (!controller.isExperimentRunning) it.stop()
                        runLater {
                            mainView.labelTimeRemaining.text =
                                "                   Осталось всего: " + toHHmmss((allTime - it.getCurrentTicks()) * 1000L)

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
                                    formatRealNumber(measuringI11).toString()
                                controller.tableValuesPlace1Test1[1].place1amperage.value =
                                    formatRealNumber(measuringI12).toString()
                                controller.tableValuesPlace1Test1[2].place1amperage.value =
                                    formatRealNumber(measuringI13).toString()
                                controller.tableValuesPlace1Test1[3].place1amperage.value =
                                    formatRealNumber(measuringI14).toString()
                                controller.tableValuesPlace1Test1[4].place1amperage.value =
                                    formatRealNumber(measuringI15).toString()
                                controller.tableValuesPlace1Test1[5].place1amperage.value =
                                    formatRealNumber(measuringI16).toString()
                            }

                            if (mainView.place2Prop.value) {
                                controller.tableValuesPlace2Test1[0].place2amperage.value =
                                    formatRealNumber(measuringI21).toString()
                                controller.tableValuesPlace2Test1[1].place2amperage.value =
                                    formatRealNumber(measuringI22).toString()
                                controller.tableValuesPlace2Test1[2].place2amperage.value =
                                    formatRealNumber(measuringI23).toString()
                                controller.tableValuesPlace2Test1[3].place2amperage.value =
                                    formatRealNumber(measuringI24).toString()
                                controller.tableValuesPlace2Test1[4].place2amperage.value =
                                    formatRealNumber(measuringI25).toString()
                                controller.tableValuesPlace2Test1[5].place2amperage.value =
                                    formatRealNumber(measuringI26).toString()
                            }

                            if (mainView.place3Prop.value) {
                                controller.tableValuesPlace3Test1[0].place3amperage.value =
                                    formatRealNumber(measuringI21).toString()
                                controller.tableValuesPlace3Test1[1].place3amperage.value =
                                    formatRealNumber(measuringI22).toString()
                                controller.tableValuesPlace3Test1[2].place3amperage.value =
                                    formatRealNumber(measuringI23).toString()
                                controller.tableValuesPlace3Test1[3].place3amperage.value =
                                    formatRealNumber(measuringI24).toString()
                                controller.tableValuesPlace3Test1[4].place3amperage.value =
                                    formatRealNumber(measuringI25).toString()
                                controller.tableValuesPlace3Test1[5].place3amperage.value =
                                    formatRealNumber(measuringI26).toString()
                            }

                            if (mainView.place1Prop.value) {
                                if (measuringt11 < -50 || measuringt11 > 100 || !trmStatus11) {
                                    controller.tableValuesPlace1Test1[0].place1temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace1Test1[0].place1temp.value =
                                        String.format("%.2f", measuringt11)
                                }
                                if (measuringt12 < -50 || measuringt12 > 100 || !trmStatus12) {
                                    controller.tableValuesPlace1Test1[1].place1temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace1Test1[1].place1temp.value =
                                        String.format("%.2f", measuringt12)
                                }
                                if (measuringt13 < -50 || measuringt13 > 100 || !trmStatus13) {
                                    controller.tableValuesPlace1Test1[2].place1temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace1Test1[2].place1temp.value =
                                        String.format("%.2f", measuringt13)
                                }
                                if (measuringt14 < -50 || measuringt14 > 100 || !trmStatus14) {
                                    controller.tableValuesPlace1Test1[3].place1temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace1Test1[3].place1temp.value =
                                        String.format("%.2f", measuringt14)
                                }
                                if (measuringt15 < -50 || measuringt15 > 100 || !trmStatus15) {
                                    controller.tableValuesPlace1Test1[4].place1temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace1Test1[4].place1temp.value =
                                        String.format("%.2f", measuringt15)
                                }
                                if (measuringt16 < -50 || measuringt16 > 100 || !trmStatus16) {
                                    controller.tableValuesPlace1Test1[5].place1temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace1Test1[5].place1temp.value =
                                        String.format("%.2f", measuringt16)
                                }
                            }

                            if (mainView.place2Prop.value) {
                                if (measuringt21 < -50 || measuringt21 > 100 || !trmStatus21) {
                                    controller.tableValuesPlace2Test1[0].place2temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace2Test1[0].place2temp.value =
                                        String.format("%.2f", measuringt21)
                                }
                                if (measuringt22 < -50 || measuringt22 > 100 || !trmStatus22) {
                                    controller.tableValuesPlace2Test1[1].place2temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace2Test1[1].place2temp.value =
                                        String.format("%.2f", measuringt22)
                                }
                                if (measuringt23 < -50 || measuringt23 > 100 || !trmStatus23) {
                                    controller.tableValuesPlace2Test1[2].place2temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace2Test1[2].place2temp.value =
                                        String.format("%.2f", measuringt23)
                                }
                                if (measuringt24 < -50 || measuringt24 > 100 || !trmStatus24) {
                                    controller.tableValuesPlace2Test1[3].place2temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace2Test1[3].place2temp.value =
                                        String.format("%.2f", measuringt24)
                                }
                                if (measuringt25 < -50 || measuringt25 > 100 || !trmStatus25) {
                                    controller.tableValuesPlace2Test1[4].place2temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace2Test1[4].place2temp.value =
                                        String.format("%.2f", measuringt25)
                                }
                                if (measuringt26 < -50 || measuringt26 > 100 || !trmStatus26) {
                                    controller.tableValuesPlace2Test1[5].place2temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace2Test1[5].place2temp.value =
                                        String.format("%.2f", measuringt26)
                                }
                            }
                            if (mainView.place3Prop.value) {
                                if (measuringt31 < -50 || measuringt31 > 100 || !trmStatus31) {
                                    controller.tableValuesPlace3Test1[0].place3temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace3Test1[0].place3temp.value =
                                        String.format("%.2f", measuringt31)
                                }
                                if (measuringt32 < -50 || measuringt32 > 100 || !trmStatus32) {
                                    controller.tableValuesPlace3Test1[1].place3temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace3Test1[1].place3temp.value =
                                        String.format("%.2f", measuringt32)
                                }
                                if (measuringt33 < -50 || measuringt33 > 100 || !trmStatus33) {
                                    controller.tableValuesPlace3Test1[2].place3temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace3Test1[2].place3temp.value =
                                        String.format("%.2f", measuringt33)
                                }
                                if (measuringt34 < -50 || measuringt34 > 100 || !trmStatus34) {
                                    controller.tableValuesPlace3Test1[3].place3temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace3Test1[3].place3temp.value =
                                        String.format("%.2f", measuringt34)
                                }
                                if (measuringt35 < -50 || measuringt35 > 100 || !trmStatus35) {
                                    controller.tableValuesPlace3Test1[4].place3temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace3Test1[4].place3temp.value =
                                        String.format("%.2f", measuringt35)
                                }
                                if (measuringt36 < -50 || measuringt36 > 100 || !trmStatus36) {
                                    controller.tableValuesPlace3Test1[5].place3temp.value = "-.--"
                                } else {
                                    controller.tableValuesPlace3Test1[5].place3temp.value =
                                        String.format("%.2f", measuringt36)
                                }
                            }
                        }

                        val temperature =
                            controller.tableValuesTest1[0].place1temp.value.toString().replace(",", ".").toDouble()
                        val percent = 0.05
                        if (mainView.place1Prop.value) {
                            if (measuringt11 < -50 || measuringt11 > 100 || !trmStatus11) {
                                owenPR.off11()
                            } else {
                                if (measuringt11 < temperature * (1 - percent)) {
                                    owenPR.on11()
                                }
                                if (measuringt11 > temperature * (1 + percent)) {
                                    owenPR.off11()
                                }
                            }
                            if (measuringt12 < -50 || measuringt12 > 100 || !trmStatus12) {
                                owenPR.off12()
                            } else {
                                if (measuringt12 < temperature * (1 - percent)) {
                                    owenPR.on12()
                                }
                                if (measuringt12 > temperature * (1 + percent)) {
                                    owenPR.off12()
                                }
                            }
                            if (measuringt13 < -50 || measuringt13 > 100 || !trmStatus13) {
                                owenPR.off13()
                            } else {
                                if (measuringt13 < temperature * (1 - percent)) {
                                    owenPR.on13()
                                }
                                if (measuringt13 > temperature * (1 + percent)) {
                                    owenPR.off13()
                                }
                            }
                            if (measuringt14 < -50 || measuringt14 > 100 || !trmStatus14) {
                                owenPR.off14()
                            } else {
                                if (measuringt14 < temperature * (1 - percent)) {
                                    owenPR.on14()
                                }
                                if (measuringt14 > temperature * (1 + percent)) {
                                    owenPR.off14()
                                }
                            }
                            if (measuringt15 < -50 || measuringt15 > 100 || !trmStatus15) {
                                owenPR.off15()
                            } else {
                                if (measuringt15 < temperature * (1 - percent)) {
                                    owenPR.on15()
                                }
                                if (measuringt15 > temperature * (1 + percent)) {
                                    owenPR.off15()
                                }
                            }
                            if (measuringt16 < -50 || measuringt16 > 100 || !trmStatus16) {
                                owenPR.off16()
                            } else {
                                if (measuringt16 < temperature * (1 - percent)) {
                                    owenPR.on16()
                                }
                                if (measuringt16 > temperature * (1 + percent)) {
                                    owenPR.off16()
                                }
                            }
                        }
                        if (measuringt11 < -50 || measuringt11 > 100 || !trmStatus11) {
                            listOfValues11.add("-99.9")
                        } else {
                            listOfValues11.add(String.format("%.1f", measuringt11))
                        }
                        if (measuringt12 < -50 || measuringt12 > 100 || !trmStatus12) {
                            listOfValues12.add("-99.9")
                        } else {
                            listOfValues12.add(String.format("%.1f", measuringt12))
                        }
                        if (measuringt13 < -50 || measuringt13 > 100 || !trmStatus13) {
                            listOfValues13.add("-99.9")
                        } else {
                            listOfValues13.add(String.format("%.1f", measuringt13))
                        }
                        if (measuringt14 < -50 || measuringt14 > 100 || !trmStatus14) {
                            listOfValues14.add("-99.9")
                        } else {
                            listOfValues14.add(String.format("%.1f", measuringt14))
                        }
                        if (measuringt15 < -50 || measuringt15 > 100 || !trmStatus15) {
                            listOfValues15.add("-99.9")
                        } else {
                            listOfValues15.add(String.format("%.1f", measuringt15))
                        }
                        if (measuringt16 < -50 || measuringt16 > 100 || !trmStatus16) {
                            listOfValues16.add("-99.9")
                        } else {
                            listOfValues16.add(String.format("%.1f", measuringt16))
                        }

                        if (mainView.place2Prop.value) {
                            if (measuringt21 < -50 || measuringt21 > 100 || !trmStatus21) {
                                owenPR.off21()
                            } else {
                                if (measuringt21 < temperature * (1 - percent)) {
                                    owenPR.on21()
                                }
                                if (measuringt21 > temperature * (1 + percent)) {
                                    owenPR.off21()
                                }
                            }
                            if (measuringt22 < -50 || measuringt22 > 100 || !trmStatus22) {
                                owenPR.off22()
                            } else {
                                if (measuringt22 < temperature * (1 - percent)) {
                                    owenPR.on22()
                                }
                                if (measuringt22 > temperature * (1 + percent)) {
                                    owenPR.off22()
                                }
                            }
                            if (measuringt23 < -50 || measuringt23 > 100 || !trmStatus23) {
                                owenPR.off23()
                            } else {
                                if (measuringt23 < temperature * (1 - percent)) {
                                    owenPR.on23()
                                }
                                if (measuringt23 > temperature * (1 + percent)) {
                                    owenPR.off23()
                                }
                            }
                            if (measuringt24 < -50 || measuringt24 > 100 || !trmStatus24) {
                                owenPR.off24()
                            } else {
                                if (measuringt24 < temperature * (1 - percent)) {
                                    owenPR.on24()
                                }
                                if (measuringt24 > temperature * (1 + percent)) {
                                    owenPR.off24()
                                }
                            }
                            if (measuringt25 < -50 || measuringt25 > 100 || !trmStatus25) {
                                owenPR.off25()
                            } else {
                                if (measuringt25 < temperature * (1 - percent)) {
                                    owenPR.on25()
                                }
                                if (measuringt25 > temperature * (1 + percent)) {
                                    owenPR.off25()
                                }
                            }
                            if (measuringt26 < -50 || measuringt26 > 100 || !trmStatus26) {
                                owenPR.off26()
                            } else {
                                if (measuringt26 < temperature * (1 - percent)) {
                                    owenPR.on26()
                                }
                                if (measuringt26 > temperature * (1 + percent)) {
                                    owenPR.off26()
                                }
                            }
                        }

                        if (measuringt21 < -50 || measuringt21 > 100 || !trmStatus21) {
                            listOfValues21.add("-99.9")
                        } else {
                            listOfValues21.add(String.format("%.1f", measuringt21))
                        }
                        if (measuringt22 < -50 || measuringt22 > 100 || !trmStatus22) {
                            listOfValues22.add("-99.9")
                        } else {
                            listOfValues22.add(String.format("%.1f", measuringt22))
                        }
                        if (measuringt23 < -50 || measuringt23 > 100 || !trmStatus23) {
                            listOfValues23.add("-99.9")
                        } else {
                            listOfValues23.add(String.format("%.1f", measuringt23))
                        }
                        if (measuringt24 < -50 || measuringt24 > 100 || !trmStatus24) {
                            listOfValues24.add("-99.9")
                        } else {
                            listOfValues24.add(String.format("%.1f", measuringt24))
                        }
                        if (measuringt25 < -50 || measuringt25 > 100 || !trmStatus25) {
                            listOfValues25.add("-99.9")
                        } else {
                            listOfValues25.add(String.format("%.1f", measuringt25))
                        }
                        if (measuringt26 < -50 || measuringt26 > 100 || !trmStatus26) {
                            listOfValues26.add("-99.9")
                        } else {
                            listOfValues26.add(String.format("%.1f", measuringt26))
                        }

                        if (mainView.place3Prop.value) {
                            if (measuringt31 < -50 || measuringt31 > 100 || !trmStatus31) {
                                owenPR.off31()
                            } else {
                                if (measuringt31 < temperature * (1 - percent)) {
                                    owenPR.on31()
                                }
                                if (measuringt31 > temperature * (1 + percent)) {
                                    owenPR.off31()
                                }
                            }
                            if (measuringt32 < -50 || measuringt32 > 100 || !trmStatus32) {
                                owenPR.off32()
                            } else {
                                if (measuringt32 < temperature * (1 - percent)) {
                                    owenPR.on32()
                                }
                                if (measuringt32 > temperature * (1 + percent)) {
                                    owenPR.off32()
                                }
                            }
                            if (measuringt33 < -50 || measuringt33 > 100 || !trmStatus33) {
                                owenPR.off33()
                            } else {
                                if (measuringt33 < temperature * (1 - percent)) {
                                    owenPR.on33()
                                }
                                if (measuringt33 > temperature * (1 + percent)) {
                                    owenPR.off33()
                                }
                            }
                            if (measuringt34 < -50 || measuringt34 > 100 || !trmStatus34) {
                                owenPR.off34()
                            } else {
                                if (measuringt34 < temperature * (1 - percent)) {
                                    owenPR.on34()
                                }
                                if (measuringt34 > temperature * (1 + percent)) {
                                    owenPR.off34()
                                }
                            }
                            if (measuringt35 < -50 || measuringt35 > 100 || !trmStatus35) {
                                owenPR.off35()
                            } else {
                                if (measuringt35 < temperature * (1 - percent)) {
                                    owenPR.on35()
                                }
                                if (measuringt35 > temperature * (1 + percent)) {
                                    owenPR.off35()
                                }
                            }
                            if (measuringt36 < -50 || measuringt36 > 100 || !trmStatus36) {
                                owenPR.off36()
                            } else {
                                if (measuringt36 < temperature * (1 - percent)) {
                                    owenPR.on36()
                                }
                                if (measuringt36 > temperature * (1 + percent)) {
                                    owenPR.off36()
                                }
                            }
                        }

                        if (mainView.place3Prop.value) {
                            if (measuringt31 < -50 || measuringt31 > 100 || !trmStatus31) {
                                listOfValues31.add("-99.9")
                            } else {
                                listOfValues31.add(String.format("%.1f", measuringt31))
                            }
                            if (measuringt32 < -50 || measuringt32 > 100 || !trmStatus32) {
                                listOfValues32.add("-99.9")
                            } else {
                                listOfValues32.add(String.format("%.1f", measuringt32))
                            }
                            if (measuringt33 < -50 || measuringt33 > 100 || !trmStatus33) {
                                listOfValues33.add("-99.9")
                            } else {
                                listOfValues33.add(String.format("%.1f", measuringt33))
                            }
                            if (measuringt34 < -50 || measuringt34 > 100 || !trmStatus34) {
                                listOfValues34.add("-99.9")
                            } else {
                                listOfValues34.add(String.format("%.1f", measuringt34))
                            }
                            if (measuringt35 < -50 || measuringt35 > 100 || !trmStatus35) {
                                listOfValues35.add("-99.9")
                            } else {
                                listOfValues35.add(String.format("%.1f", measuringt35))
                            }
                            if (measuringt36 < -50 || measuringt36 > 100 || !trmStatus36) {
                                listOfValues36.add("-99.9")
                            } else {
                                listOfValues36.add(String.format("%.1f", measuringt36))
                            }
                        }



                        listOfAmperage11.add(String.format("%.1f", measuringI11))
                        listOfAmperage12.add(String.format("%.1f", measuringI12))
                        listOfAmperage13.add(String.format("%.1f", measuringI13))
                        listOfAmperage14.add(String.format("%.1f", measuringI14))
                        listOfAmperage15.add(String.format("%.1f", measuringI15))
                        listOfAmperage16.add(String.format("%.1f", measuringI16))
                        listOfAmperage21.add(String.format("%.1f", measuringI21))
                        listOfAmperage22.add(String.format("%.1f", measuringI22))
                        listOfAmperage23.add(String.format("%.1f", measuringI23))
                        listOfAmperage24.add(String.format("%.1f", measuringI24))
                        listOfAmperage25.add(String.format("%.1f", measuringI25))
                        listOfAmperage26.add(String.format("%.1f", measuringI26))
                        listOfAmperage31.add(String.format("%.1f", measuringI31))
                        listOfAmperage32.add(String.format("%.1f", measuringI32))
                        listOfAmperage33.add(String.format("%.1f", measuringI33))
                        listOfAmperage34.add(String.format("%.1f", measuringI34))
                        listOfAmperage35.add(String.format("%.1f", measuringI35))
                        listOfAmperage36.add(String.format("%.1f", measuringI36))

                        tickJobInProcess = false
                    }
                }
            )

            val voltage = controller.tableValuesTest1[0].place1voltage.value.toString().replace(",", ".").toDouble()

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

            while (controller.isExperimentRunning && controller.isDevicesRespondingTest1() && callbackTimer.isRunning) {
                appendOneMessageToLog(LogTag.MESSAGE, "Ожидание завершения...")
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

            if (listOfValues11.isNotEmpty()) {
                saveProtocolToDB()
            }

            while (tickJobInProcess) {
                sleep(100)
            }

            owenPR.offAllKMs()
            appendMessageToLog(LogTag.MESSAGE, "Испытание завершено")
            setResult()

            finalizeExperiment()
            runLater {
                mainView.labelTestStatus.text = ""
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
    }

    private fun initLatr(latrDevice: AvemLatrController) {
        try {
            latrDevice.reset {
//                appendOneMessageToLog(
//                    LogTag.MESSAGE,
//                    "В предыдущем испытании АРН не завершил свою работу нормально. АРН возвращается в начало."
//                )
            }
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

    private fun startCheckStatusTrmSensors() {
        thread(isDaemon = true) {
            while (controller.isExperimentRunning) {
                trmStatus11 = trm1.checkStatus(0) == 1
                trmStatus12 = trm1.checkStatus(1) == 1
                trmStatus13 = trm1.checkStatus(2) == 1
                trmStatus14 = trm1.checkStatus(3) == 1
                trmStatus15 = trm1.checkStatus(4) == 1
                trmStatus16 = trm1.checkStatus(5) == 1
                trmStatus17 = trm1.checkStatus(6) == 1

                trmStatus21 = trm2.checkStatus(0) == 1
                trmStatus22 = trm2.checkStatus(1) == 1
                trmStatus23 = trm2.checkStatus(2) == 1
                trmStatus24 = trm2.checkStatus(3) == 1
                trmStatus25 = trm2.checkStatus(4) == 1
                trmStatus26 = trm2.checkStatus(5) == 1

                trmStatus31 = trm3.checkStatus(0) == 1
                trmStatus32 = trm3.checkStatus(1) == 1
                trmStatus33 = trm3.checkStatus(2) == 1
                trmStatus34 = trm3.checkStatus(3) == 1
                trmStatus35 = trm3.checkStatus(4) == 1
                trmStatus36 = trm3.checkStatus(5) == 1
                sleep(100)
            }
        }
    }

    private fun saveProtocolToDB() {
        val dateFormatter = SimpleDateFormat("dd.MM.y")
        val timeFormatter = SimpleDateFormat("HH:mm:ss")
        val unixTime = System.currentTimeMillis()

        transaction {
            Protocol.new {
                date = dateFormatter.format(unixTime).toString()
                time = timeFormatter.format(unixTime).toString()
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
                amperage11 = listOfAmperage11.toString()
                amperage12 = listOfAmperage12.toString()
                amperage13 = listOfAmperage13.toString()
                amperage14 = listOfAmperage14.toString()
                amperage15 = listOfAmperage15.toString()
                amperage16 = listOfAmperage16.toString()
                amperage21 = listOfAmperage21.toString()
                amperage22 = listOfAmperage22.toString()
                amperage23 = listOfAmperage23.toString()
                amperage24 = listOfAmperage24.toString()
                amperage25 = listOfAmperage25.toString()
                amperage26 = listOfAmperage26.toString()
                amperage31 = listOfAmperage31.toString()
                amperage32 = listOfAmperage32.toString()
                amperage33 = listOfAmperage33.toString()
                amperage34 = listOfAmperage34.toString()
                amperage35 = listOfAmperage35.toString()
                amperage36 = listOfAmperage36.toString()
            }
        }
    }

    private fun setResult() {
        if (controller.cause.isNotEmpty()) {
            appendMessageToLog(LogTag.ERROR, "Испытание прервано по причине: ${controller.cause}")
        } else if (!controller.isDevicesRespondingTest1()) {
            appendMessageToLog(LogTag.ERROR, "Испытание прервано по причине: \nпотеряна связь с устройствами")
        } else {
            appendMessageToLog(LogTag.MESSAGE, "Испытание завершено успешно")
        }
    }

    private fun finalizeExperiment() {
        isExperimentEnded = true
        owenPR.offAllKMs()
        CommunicationModel.clearPollingRegisters()
    }
}
