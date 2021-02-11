package ru.avem.posshop.controllers

import javafx.application.Platform
import javafx.scene.text.Text
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.posshop.communication.model.CommunicationModel
import ru.avem.posshop.communication.model.devices.LatrStuckException
import ru.avem.posshop.communication.model.devices.avem.avem4.Avem4Controller
import ru.avem.posshop.communication.model.devices.avem.avem4.Avem4Model
import ru.avem.posshop.communication.model.devices.avem.avem7.Avem7Controller
import ru.avem.posshop.communication.model.devices.avem.avem7.Avem7Model
import ru.avem.posshop.communication.model.devices.avem.latr.AvemLatrController
import ru.avem.posshop.communication.model.devices.avem.latr.AvemLatrModel
import ru.avem.posshop.communication.model.devices.avem.latr.LatrControllerConfiguration
import ru.avem.posshop.communication.model.devices.owen.pr.OwenPrController
import ru.avem.posshop.communication.model.devices.owen.pr.OwenPrModel
import ru.avem.posshop.database.entities.ProtocolInsulation
import ru.avem.posshop.entities.TController
import ru.avem.posshop.utils.*
import ru.avem.posshop.view.MainView
import tornadofx.*
import java.text.SimpleDateFormat
import kotlin.concurrent.thread
import kotlin.experimental.and
import kotlin.time.ExperimentalTime

class Test2Controller : TController() {
    protected val owenPR = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.DD2) as OwenPrController
    protected val gv238 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV238) as AvemLatrController
    protected val a71 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A71) as Avem7Controller
    protected val a72 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A72) as Avem7Controller
    protected val a73 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A73) as Avem7Controller
    protected val a41 = CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A41) as Avem4Controller

    val controller: MainViewController by inject()
    val mainView: MainView by inject()

    private var logBuffer: String? = null

    @Volatile
    var isExperimentEnded: Boolean = true

    @Volatile
    var schemeAssembled: Boolean = false

    @Volatile
    var isValuesCorrect: Boolean = true

    //region переменные для значений с приборов
    @Volatile
    private var measuringU1: Double = 0.0

    @Volatile
    private var measuringU2: Double = 0.0

    @Volatile
    private var measuringU3: Double = 0.0

    @Volatile
    private var measuringI1: Double = 0.0

    @Volatile
    private var measuringI2: Double = 0.0

    @Volatile
    private var measuringI3: Double = 0.0
    //endregion

    //region переменные для защит ПР
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

    private var listOfValuesVoltage = mutableListOf<String>()
    private var listOfValuesAmperage1 = mutableListOf<String>()
    private var listOfValuesAmperage2 = mutableListOf<String>()
    private var listOfValuesAmperage3 = mutableListOf<String>()
    //endregion

    @Volatile
    var tickDrawJobInProcess = false

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

        //region avem poll
        CommunicationModel.startPoll(CommunicationModel.DeviceID.A41, Avem4Model.RMS_VOLTAGE) { value ->
            measuringU1 = value.toDouble()
            measuringU2 = value.toDouble()
            measuringU3 = value.toDouble()
        }

        val criticalAmperage =
            controller.tableValuesTest2[0].place1amperage.value.toString().replace(",", ".").toDouble()
        if (mainView.place1Prop.value) {
            CommunicationModel.startPoll(CommunicationModel.DeviceID.A71, Avem7Model.AMPERAGE) { value ->
                val amperage1 = value.toDouble()
                if (amperage1 > criticalAmperage && schemeAssembled) {
                    controller.cause = "Ток места 1 превысил заданный"
                }

                measuringI1 = amperage1
            }
        }

        if (mainView.place2Prop.value) {
            CommunicationModel.startPoll(CommunicationModel.DeviceID.A72, Avem7Model.AMPERAGE) { value ->
                val amperage2 = value.toDouble()
                if (amperage2 > criticalAmperage && schemeAssembled) {
                    controller.cause = "Ток места 2 превысил заданный"
                }

                measuringI2 = amperage2
            }
        }

        if (mainView.place3Prop.value) {
            CommunicationModel.startPoll(CommunicationModel.DeviceID.A73, Avem7Model.AMPERAGE) { value ->
                val amperage3 = value.toDouble()
                if (amperage3 > criticalAmperage && schemeAssembled) {
                    controller.cause = "Ток места 3 превысил заданный"
                }

                measuringI3 = amperage3
            }
        }
        //endregion

        //region latr poll
        CommunicationModel.startPoll(CommunicationModel.DeviceID.GV238, AvemLatrModel.DEVICE_STATUS) { }
        //endregion
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
            schemeAssembled = false
            isValuesCorrect = true
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
            while (!controller.isDevicesRespondingTest2() && controller.isExperimentRunning && timeToPrepare-- > 0) {
                sleep(100)
            }

            if (!controller.isDevicesRespondingTest2()) {
                var cause = ""
                cause += "Не отвечают приборы: "
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.DD2).isResponding) {
                    cause += "ПР "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.GV238).isResponding) {
                    cause += "ЛАТР "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A71).isResponding) {
                    cause += "АВЭМ7 (Место 1) "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A72).isResponding) {
                    cause += "АВЭМ7 (Место 2) "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A73).isResponding) {
                    cause += "АВЭМ7 (Место 3) "
                }
                if (!CommunicationModel.getDeviceById(CommunicationModel.DeviceID.A41).isResponding) {
                    cause += "АВЭМ4 "
                }
                controller.cause = cause
            }

            if (controller.isExperimentRunning && controller.isDevicesRespondingTest2()) {
                CommunicationModel.addWritingRegister(
                    CommunicationModel.DeviceID.DD2,
                    OwenPrModel.RESET_DOG,
                    1.toShort()
                )
                owenPR.initOwenPR()
                startPollDevices()
                sleep(1000)
            }

            if (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest2()) {
                runLater {
                    Toast.makeText("Нажмите кнопку ПУСК").show(Toast.ToastType.WARNING)
                }
            }

            var timeToStart = 300
            while (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest2() && timeToStart-- > 0) {
                appendOneMessageToLog(LogTag.DEBUG, "Нажмите кнопку ПУСК")
                sleep(100)
            }

            if (!startButton && controller.isExperimentRunning && controller.isDevicesRespondingTest2()) {
                controller.cause = "Не нажата кнопка ПУСК"
            }

            if (controller.isExperimentRunning && controller.isDevicesRespondingTest2()) {
                appendMessageToLog(LogTag.DEBUG, "Подготовка стенда")

                appendMessageToLog(LogTag.DEBUG, "Сбор схемы")
                owenPR.signalizeHV()
                if (mainView.place1Prop.value) {
                    owenPR.connectPlace1()
                }
                if (mainView.place2Prop.value) {
                    owenPR.connectPlace2()
                }
                if (mainView.place3Prop.value) {
                    owenPR.connectPlace3()
                }
                owenPR.startHV()
                sleep(2000)
                schemeAssembled = true
            }

            val allTime =
                (controller.tableValuesTest2[0].place1time.value.toString().replace(",", ".").toDouble()).toInt()

            CallbackTimer(
                tickPeriod = 1.seconds, tickTimes = allTime * 2,
                tickJob = {
                    if (!tickDrawJobInProcess) {
                        tickDrawJobInProcess = true
                        if (!controller.isExperimentRunning) it.stop()
                        if (isValuesCorrect) {
                            runLater {
                                if (mainView.place1Prop.value) {
                                    controller.tableValuesPlace1Test2[0].place1voltage.value =
                                        formatRealNumber(measuringU1).toString()
                                }

                                if (mainView.place2Prop.value) {
                                    controller.tableValuesPlace2Test2[0].place2voltage.value =
                                        formatRealNumber(measuringU2).toString()
                                }

                                if (mainView.place3Prop.value) {
                                    controller.tableValuesPlace3Test2[0].place3voltage.value =
                                        formatRealNumber(measuringU3).toString()
                                }

                                if (mainView.place1Prop.value) {
                                    controller.tableValuesPlace1Test2[0].place1amperage.value =
                                        formatRealNumber(measuringI1).toString()
                                }

                                if (mainView.place2Prop.value) {
                                    controller.tableValuesPlace2Test2[0].place2amperage.value =
                                        formatRealNumber(measuringI2).toString()
                                }

                                if (mainView.place3Prop.value) {
                                    controller.tableValuesPlace3Test2[0].place3amperage.value =
                                        formatRealNumber(measuringI3).toString()
                                }
                            }

                            listOfValuesVoltage.add(String.format("%.1f", measuringU1))
                            listOfValuesAmperage1.add(String.format("%.1f", measuringI1))
                            listOfValuesAmperage2.add(String.format("%.1f", measuringI2))
                            listOfValuesAmperage3.add(String.format("%.1f", measuringI3))
                        }
                        tickDrawJobInProcess = false
                    }
                }
            )

            val voltage =
                controller.tableValuesTest2[0].place1voltage.value.toString().replace(",", ".").toDouble() * 0.99

            if (controller.isExperimentRunning && controller.isDevicesRespondingTest2()) {
                initLatr(gv238)
            }
            if (controller.isExperimentRunning && controller.isDevicesRespondingTest2()) {
                val ktr = 1000 / 200
                configLatr(gv238, voltage / ktr)
            }

            while (controller.isExperimentRunning && controller.isDevicesRespondingTest2() && measuringU1 <= voltage) {
                appendOneMessageToLog(LogTag.MESSAGE, "Грубая регулировка напряжения")

                if (isLatrInErrorMode(gv238)) {
                    controller.cause = "Ошибка контроллера АРН"
                }
                sleep(100)
            }
            accurateRegulate(a41, Avem4Model.RMS_VOLTAGE, gv238, voltage)
            if (controller.isExperimentRunning && controller.isDevicesRespondingTest2()) {
                appendOneMessageToLog(LogTag.MESSAGE, "Напряжение выставлено")
            }

            val callbackTimer = CallbackTimer(
                tickPeriod = 1.seconds, tickTimes = allTime,
                tickJob = {
                    if (!controller.isExperimentRunning) it.stop()
                    runLater {
                        mainView.labelTimeRemaining.text =
                            "                   Осталось всего: " + toHHmmss((allTime - it.getCurrentTicks()) * 1000L)
                    }
                }
            )

            while (controller.isExperimentRunning && controller.isDevicesRespondingTest2() && callbackTimer.isRunning) {
                appendOneMessageToLog(LogTag.MESSAGE, "Ожидание завершения...")
                sleep(100)
            }
            isValuesCorrect = false
            gv238.reset()

            saveProtocolToDB()

            while (tickDrawJobInProcess) {
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
            }
        } catch (e: LatrStuckException) {
            appendMessageToLog(LogTag.ERROR, "Ошибка возврата АРН в начало. АРН застрял.")
            controller.cause = "Застрял ЛАТР"
        }
    }

    private fun configLatr(latrDevice: AvemLatrController, voltage: Double) {
        appendMessageToLog(LogTag.MESSAGE, "Конфигурирование и запуск АРН")
        presetRoughParameters(latrDevice)
        latrDevice.start((voltage * 1.01).toFloat())
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
        voltageDevice: Avem4Controller,
        voltageRegister: String,
        latrDevice: AvemLatrController,
        voltage: Double
    ) {
        if (controller.isExperimentRunning && controller.isDevicesRespondingTest2()) {
            presetAccurateParameters(latrDevice)

            while (controller.isExperimentRunning && controller.isDevicesRespondingTest2() &&
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
                minDuttyPercent = 45f,
                maxDuttyPercent = 45f,
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

    private fun saveProtocolToDB() {
        val dateFormatter = SimpleDateFormat("dd.MM.y")
        val timeFormatter = SimpleDateFormat("HH:mm:ss")
        val unixTime = System.currentTimeMillis()

        transaction {
            ProtocolInsulation.new {
                date = dateFormatter.format(unixTime).toString()
                time = timeFormatter.format(unixTime).toString()
                voltage = listOfValuesVoltage.toString()
                amperage1 = listOfValuesAmperage1.toString()
                amperage2 = listOfValuesAmperage2.toString()
                amperage3 = listOfValuesAmperage3.toString()
            }
        }
    }

    private fun setResult() {
        if (controller.cause.isNotEmpty()) {
            appendMessageToLog(LogTag.ERROR, "Испытание прервано по причине: ${controller.cause}")
        } else if (!controller.isDevicesRespondingTest2()) {
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
