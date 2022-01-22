package ru.avem.posshop.communication.model

import mu.KotlinLogging
import ru.avem.kserialpooler.communication.adapters.modbusrtu.ModbusRTUAdapter
import ru.avem.posshop.utils.sleep

interface IDeviceController {
    val name: String

    val protocolAdapter: ModbusRTUAdapter

    val id: Byte

    var isResponding: Boolean

    var requestTotalCount: Int
    var requestSuccessCount: Int

    fun readRegister(register: DeviceRegister) {

    }

    fun readRequest(request: String): Int {
        return 0
    }

    fun <T : Number> writeRegister(register: DeviceRegister, value: T) {

    }

    fun readAllRegisters() {

    }

    fun writeRegisters(register: DeviceRegister, values: List<Short>) {

    }

    fun writeRequest(request: String) {

    }

    val pollingRegisters: MutableList<DeviceRegister>
    val writingRegisters: MutableList<Pair<DeviceRegister, Number>>
    val pollingMutex: Any
    val writingMutex: Any

    fun IDeviceController.transactionWithAttempts(block: () -> Unit) {
        var attempt = 0
        val connection = protocolAdapter.connection

        while (attempt++ < connection.attemptCount) {
            requestTotalCount++

            try {
                block()
                requestSuccessCount++
                break
            } catch (e: ru.avem.kserialpooler.communication.utils.TransportException) {
                val message =
                    "repeat $attempt/${connection.attemptCount} attempts with common success rate = ${(requestSuccessCount) * 100 / requestTotalCount}%"
                KotlinLogging.logger(name).info(message)

                if (attempt == connection.attemptCount) {
                    throw ru.avem.kserialpooler.communication.utils.TransportException(message)
                }
            }
            sleep(10)
        }
    }

    fun getRegisterById(idRegister: String): DeviceRegister

    fun addPollingRegister(register: DeviceRegister) {
        synchronized(protocolAdapter.connection) {
            pollingRegisters.add(register)
        }
    }

    fun addWritingRegister(writingPair: Pair<DeviceRegister, Number>) {
        synchronized(protocolAdapter.connection) {
            writingRegisters.add(writingPair)
        }
    }

    fun removePollingRegister(register: DeviceRegister) {
        synchronized(protocolAdapter.connection) {
            pollingRegisters.remove(register)
        }
    }

    fun removeAllPollingRegisters() {
        synchronized(protocolAdapter.connection) {
            pollingRegisters.forEach(DeviceRegister::deleteObservers)
            pollingRegisters.clear()
        }
    }

    fun removeAllWritingRegisters() {
        synchronized(protocolAdapter.connection) {
            writingRegisters.forEach {
                it.first.deleteObservers()
            }
            writingRegisters.clear()
        }
    }

    fun readPollingRegisters() {
        synchronized(protocolAdapter.connection) {
            pollingRegisters.forEach {
                readRegister(it)
            }
        }
    }

    fun writeWritingRegisters() {
        synchronized(protocolAdapter.connection) {
            writingRegisters.forEach {
                writeRegister(it.first, it.second)
            }
        }
    }

    fun checkResponsibility()
}
