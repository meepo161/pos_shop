package ru.avem.posshop.communication.model

interface IDeviceModel {
    val registers: Map<String, DeviceRegister>

    fun getRegisterById(idRegister: String): DeviceRegister
}
