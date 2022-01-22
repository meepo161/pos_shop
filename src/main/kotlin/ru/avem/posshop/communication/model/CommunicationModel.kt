package ru.avem.posshop.communication.model

import ru.avem.kserialpooler.communication.Connection
import ru.avem.kserialpooler.communication.adapters.modbusrtu.ModbusRTUAdapter
import ru.avem.kserialpooler.communication.utils.SerialParameters
import ru.avem.posshop.app.Pos.Companion.isAppRunning
import ru.avem.posshop.communication.model.devices.avem.avem4.Avem4Controller
import ru.avem.posshop.communication.model.devices.avem.avem7.Avem7Controller
import ru.avem.posshop.communication.model.devices.avem.latr.AvemLatrController
import ru.avem.posshop.communication.model.devices.owen.pr.OwenPrController
import ru.avem.posshop.communication.model.devices.owen.trm136.Trm136Controller
import ru.avem.posshop.communication.model.devices.parma.ParmaController
import java.lang.Thread.sleep
import kotlin.concurrent.thread

object CommunicationModel {
    @Suppress("UNUSED_PARAMETER")
    enum class DeviceID(description: String) {
        DD2("ПР"),
        PARMA1("ПАРМА1"),
        PARMA2("ПАРМА2"),
        PARMA3("ПАРМА3"),
        PARMA4("ПАРМА4"),
        PARMA5("ПАРМА5"),
        PARMA6("ПАРМА6"),
        TRM1("TRM1"),
        TRM2("TRM2"),
        TRM3("TRM3"),
        GV238("GV238"),
        GV239("GV239"),
        GV240("GV240"),
        A71("Ток утечки 1"),
        A72("Ток утечки 2"),
        A73("Ток утечки 3"),
        A41("Напряжение на ОИ")
    }

    private var isConnected = false

    private val connection = Connection(
        adapterName = "CP2103 USB to RS-485",
        serialParameters = SerialParameters(8, 0, 1, 38400),
        timeoutRead = 100,
        timeoutWrite = 100
    ).apply {
        connect()
        isConnected = true
    }

    private val modbusAdapter = ModbusRTUAdapter(connection)

    private val deviceControllers: Map<DeviceID, IDeviceController> = mapOf(
        DeviceID.DD2 to OwenPrController(DeviceID.DD2.toString(), modbusAdapter, 2),
        DeviceID.PARMA1 to ParmaController(DeviceID.PARMA1.toString(), modbusAdapter, 31),
        DeviceID.PARMA2 to ParmaController(DeviceID.PARMA2.toString(), modbusAdapter, 32),
        DeviceID.PARMA3 to ParmaController(DeviceID.PARMA3.toString(), modbusAdapter, 33),
        DeviceID.PARMA4 to ParmaController(DeviceID.PARMA4.toString(), modbusAdapter, 34),
        DeviceID.PARMA5 to ParmaController(DeviceID.PARMA5.toString(), modbusAdapter, 35),
        DeviceID.PARMA6 to ParmaController(DeviceID.PARMA6.toString(), modbusAdapter, 36),
        DeviceID.TRM1 to Trm136Controller(DeviceID.TRM1.toString(), modbusAdapter, 8),
        DeviceID.TRM2 to Trm136Controller(DeviceID.TRM2.toString(), modbusAdapter, 16),
        DeviceID.TRM3 to Trm136Controller(DeviceID.TRM3.toString(), modbusAdapter, 24),
        DeviceID.GV238 to AvemLatrController(DeviceID.GV238.toString(), modbusAdapter, 238.toByte(), DeviceID.GV238),
        DeviceID.GV239 to AvemLatrController(DeviceID.GV239.toString(), modbusAdapter, 239.toByte(), DeviceID.GV239),
        DeviceID.GV240 to AvemLatrController(DeviceID.GV240.toString(), modbusAdapter, 240.toByte(), DeviceID.GV240),
        DeviceID.A71 to Avem7Controller(DeviceID.A71.toString(), modbusAdapter, 3),
        DeviceID.A72 to Avem7Controller(DeviceID.A72.toString(), modbusAdapter, 4),
        DeviceID.A73 to Avem7Controller(DeviceID.A73.toString(), modbusAdapter, 5),
        DeviceID.A41 to Avem4Controller(DeviceID.A41.toString(), modbusAdapter, 6)
    )

    init {
        thread(isDaemon = true) {
            while (isAppRunning) {
                if (isConnected) {
                    deviceControllers.values.forEach {
                        it.readPollingRegisters()
                    }
                }
                sleep(100)
            }
        }
        thread(isDaemon = true) {
            while (isAppRunning) {
                if (isConnected) {
                    deviceControllers.values.forEach {
                        it.writeWritingRegisters()
                    }
                }
                sleep(100)
            }
        }
    }

    fun getDeviceById(deviceID: DeviceID) = deviceControllers[deviceID] ?: error("Не определено $deviceID")

    fun startPoll(deviceID: DeviceID, registerID: String, block: (Number) -> Unit) {
        val device = getDeviceById(deviceID)
        val register = device.getRegisterById(registerID)
        register.addObserver { _, arg ->
            block(arg as Number)
        }
        device.addPollingRegister(register)
    }

    fun clearPollingRegisters() {
        deviceControllers.values.forEach(IDeviceController::removeAllPollingRegisters)
        deviceControllers.values.forEach(IDeviceController::removeAllWritingRegisters)
    }

    fun removePollingRegister(deviceID: DeviceID, registerID: String) {
        val device = getDeviceById(deviceID)
        val register = device.getRegisterById(registerID)
        register.deleteObservers()
        device.removePollingRegister(register)
    }

    fun checkDevices(): List<DeviceID> {
        deviceControllers.values.forEach(IDeviceController::checkResponsibility)
        return deviceControllers.filter { !it.value.isResponding }.keys.toList()
    }

    fun addWritingRegister(deviceID: DeviceID, registerID: String, value: Number) {
        val device = getDeviceById(deviceID)
        val register = device.getRegisterById(registerID)
        device.addWritingRegister(register to value)
    }
}
