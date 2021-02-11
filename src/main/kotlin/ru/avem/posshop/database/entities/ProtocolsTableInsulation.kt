package ru.avem.posshop.database.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object ProtocolsTableInsulation : IntIdTable() {
    val date = varchar("date", 256)
    val time = varchar("time", 256)
    val voltage = varchar("voltage", 99999999)
    val amperage1 = varchar("amperage1", 99999999)
    val amperage2 = varchar("amperage2", 99999999)
    val amperage3 = varchar("amperage3", 99999999)
}

class ProtocolInsulation(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProtocolInsulation>(ProtocolsTableInsulation)

    var date by ProtocolsTableInsulation.date
    var time by ProtocolsTableInsulation.time
    var voltage by ProtocolsTableInsulation.voltage
    var amperage1 by ProtocolsTableInsulation.amperage1
    var amperage2 by ProtocolsTableInsulation.amperage2
    var amperage3 by ProtocolsTableInsulation.amperage3

    override fun toString(): String {
        return "$id"
    }
}
