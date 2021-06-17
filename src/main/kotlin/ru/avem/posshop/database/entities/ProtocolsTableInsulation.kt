package ru.avem.posshop.database.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object ProtocolsTableInsulation : IntIdTable() {
    val date = varchar("date", 256)
    val time = varchar("time", 256)
    val dateEnd = varchar("dateEnd", 256)
    val timeEnd = varchar("timeEnd", 256)
    val operator = varchar("operator", 256)
    val cipher1 = varchar("cipher1", 256)
    val productNumber1 = varchar("productNumber1", 256)
    val cipher2 = varchar("cipher2", 256)
    val productNumber2 = varchar("productNumber2", 256)
    val cipher3 = varchar("cipher3", 256)
    val productNumber3 = varchar("productNumber3", 256)
    val voltage = varchar("voltage", 99999999)
    val amperage1 = varchar("amperage1", 99999999)
    val amperage2 = varchar("amperage2", 99999999)
    val amperage3 = varchar("amperage3", 99999999)

    var NUMBER_DATE_ATTESTATION = varchar("NUMBER_DATE_ATTESTATION", 512)
    var NAME_OF_OPERATION = varchar("NAME_OF_OPERATION", 512)
    var NUMBER_CONTROLLER = varchar("NUMBER_CONTROLLER", 512)
}

class ProtocolInsulation(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProtocolInsulation>(ProtocolsTableInsulation)

    var date by ProtocolsTableInsulation.date
    var time by ProtocolsTableInsulation.time
    var dateEnd by ProtocolsTableInsulation.dateEnd
    var timeEnd by ProtocolsTableInsulation.timeEnd
    var operator by ProtocolsTableInsulation.operator
    var cipher1 by ProtocolsTableInsulation.cipher1
    var productNumber1 by ProtocolsTableInsulation.productNumber1
    var cipher2 by ProtocolsTableInsulation.cipher2
    var productNumber2 by ProtocolsTableInsulation.productNumber2
    var cipher3 by ProtocolsTableInsulation.cipher3
    var productNumber3 by ProtocolsTableInsulation.productNumber3
    var voltage by ProtocolsTableInsulation.voltage
    var amperage1 by ProtocolsTableInsulation.amperage1
    var amperage2 by ProtocolsTableInsulation.amperage2
    var amperage3 by ProtocolsTableInsulation.amperage3

    var NUMBER_DATE_ATTESTATION by ProtocolsTableInsulation.NUMBER_DATE_ATTESTATION
    var NAME_OF_OPERATION by ProtocolsTableInsulation.NAME_OF_OPERATION
    var NUMBER_CONTROLLER by ProtocolsTableInsulation.NUMBER_CONTROLLER

    override fun toString(): String {
        return "$id"
    }
}
