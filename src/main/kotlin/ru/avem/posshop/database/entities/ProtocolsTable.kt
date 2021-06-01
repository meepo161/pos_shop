package ru.avem.posshop.database.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object ProtocolsTable : IntIdTable() {
    val date = varchar("date", 256)
    val time = varchar("time", 256)
    val operator = varchar("operator", 256)
    val cipher1 = varchar("cipher1", 256)
    val cipher2 = varchar("cipher2", 256)
    val cipher3 = varchar("cipher3", 256)
    val productNumber1 = varchar("productNumber1", 256)
    val productNumber2 = varchar("productNumber2", 256)
    val productNumber3 = varchar("productNumber3", 256)
    val temp11 =  varchar("temp11", 99999999)
    val temp12 =  varchar("temp12", 99999999)
    val temp13 =  varchar("temp13", 99999999)
    val temp14 =  varchar("temp14", 99999999)
    val temp15 =  varchar("temp15", 99999999)
    val temp16 =  varchar("temp16", 99999999)
    val temp21 =  varchar("temp21", 99999999)
    val temp22 =  varchar("temp22", 99999999)
    val temp23 =  varchar("temp23", 99999999)
    val temp24 =  varchar("temp24", 99999999)
    val temp25 =  varchar("temp25", 99999999)
    val temp26 =  varchar("temp26", 99999999)
    val temp31 =  varchar("temp31", 99999999)
    val temp32 =  varchar("temp32", 99999999)
    val temp33 =  varchar("temp33", 99999999)
    val temp34 =  varchar("temp34", 99999999)
    val temp35 =  varchar("temp35", 99999999)
    val temp36 =  varchar("temp36", 99999999)
}

class Protocol(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Protocol>(ProtocolsTable)
    var date by ProtocolsTable.date
    var time by ProtocolsTable.time
    var operator by ProtocolsTable.operator
    var cipher1 by ProtocolsTable.cipher1
    var cipher2 by ProtocolsTable.cipher2
    var cipher3 by ProtocolsTable.cipher3
    var productNumber1 by ProtocolsTable.productNumber1
    var productNumber2 by ProtocolsTable.productNumber2
    var productNumber3 by ProtocolsTable.productNumber3
    var temp11 by ProtocolsTable.temp11
    var temp12 by ProtocolsTable.temp12
    var temp13 by ProtocolsTable.temp13
    var temp14 by ProtocolsTable.temp14
    var temp15 by ProtocolsTable.temp15
    var temp16 by ProtocolsTable.temp16
    var temp21 by ProtocolsTable.temp21
    var temp22 by ProtocolsTable.temp22
    var temp23 by ProtocolsTable.temp23
    var temp24 by ProtocolsTable.temp24
    var temp25 by ProtocolsTable.temp25
    var temp26 by ProtocolsTable.temp26
    var temp31 by ProtocolsTable.temp31
    var temp32 by ProtocolsTable.temp32
    var temp33 by ProtocolsTable.temp33
    var temp34 by ProtocolsTable.temp34
    var temp35 by ProtocolsTable.temp35
    var temp36 by ProtocolsTable.temp36
//    var amperage11 by ProtocolsTable.amperage11
//    var amperage12 by ProtocolsTable.amperage12
//    var amperage13 by ProtocolsTable.amperage13
//    var amperage14 by ProtocolsTable.amperage14
//    var amperage15 by ProtocolsTable.amperage15
//    var amperage16 by ProtocolsTable.amperage16
//    var amperage21 by ProtocolsTable.amperage21
//    var amperage22 by ProtocolsTable.amperage22
//    var amperage23 by ProtocolsTable.amperage23
//    var amperage24 by ProtocolsTable.amperage24
//    var amperage25 by ProtocolsTable.amperage25
//    var amperage26 by ProtocolsTable.amperage26
//    var amperage31 by ProtocolsTable.amperage31
//    var amperage32 by ProtocolsTable.amperage32
//    var amperage33 by ProtocolsTable.amperage33
//    var amperage34 by ProtocolsTable.amperage34
//    var amperage35 by ProtocolsTable.amperage35
//    var amperage36 by ProtocolsTable.amperage36

    override fun toString(): String {
        return "$id"
    }
}
