package ru.avem.posshop.database.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object ProtocolsTable : IntIdTable() {
    val date = varchar("date", 256)
    val time = varchar("time", 256)
    val dateEnd = varchar("dateEnd", 256)
    val timeEnd = varchar("timeEnd", 256)
    val operator = varchar("operator", 256)
    val cipher1 = varchar("cipher1", 256)
    val cipher2 = varchar("cipher2", 256)
    val cipher3 = varchar("cipher3", 256)
    val productNumber1 = varchar("productNumber1", 256)
    val productNumber2 = varchar("productNumber2", 256)
    val productNumber3 = varchar("productNumber3", 256)
    val temp11 = varchar("temp11", 99999999)
    val temp12 = varchar("temp12", 99999999)
    val temp13 = varchar("temp13", 99999999)
    val temp14 = varchar("temp14", 99999999)
    val temp15 = varchar("temp15", 99999999)
    val temp16 = varchar("temp16", 99999999)
    val temp21 = varchar("temp21", 99999999)
    val temp22 = varchar("temp22", 99999999)
    val temp23 = varchar("temp23", 99999999)
    val temp24 = varchar("temp24", 99999999)
    val temp25 = varchar("temp25", 99999999)
    val temp26 = varchar("temp26", 99999999)
    val temp31 = varchar("temp31", 99999999)
    val temp32 = varchar("temp32", 99999999)
    val temp33 = varchar("temp33", 99999999)
    val temp34 = varchar("temp34", 99999999)
    val temp35 = varchar("temp35", 99999999)
    val temp36 = varchar("temp36", 99999999)

    var NUMBER_DATE_ATTESTATION = varchar("NUMBER_DATE_ATTESTATION", 512)
    var NAME_OF_OPERATION = varchar("NAME_OF_OPERATION", 512)
    var NUMBER_CONTROLLER = varchar("NUMBER_CONTROLLER", 512)
    var T1 = varchar("T1", 512)
    var T2 = varchar("T2", 512)
    var T3 = varchar("T3", 512)
    var T4 = varchar("T4", 512)
    var T5 = varchar("T5", 512)
    var T6 = varchar("T6", 512)
    var T7 = varchar("T7", 512)
    var T8 = varchar("T8", 512)
    var T9 = varchar("T9", 512)
    var T10 = varchar("T10", 512)
    var T11 = varchar("T11", 512)
    var T12 = varchar("T12", 512)
    var T13 = varchar("T13", 512)
    var T14 = varchar("T14", 512)
    var T15 = varchar("T15", 512)
    var T16 = varchar("T16", 512)
    var T17 = varchar("T17", 512)
    var T18 = varchar("T18", 512)

    var unixTimeStartProtocol11 = varchar("unixTimeStartProtocol11", 256)
    var unixTimeStartProtocol12 = varchar("unixTimeStartProtocol12", 256)
    var unixTimeStartProtocol13 = varchar("unixTimeStartProtocol13", 256)
    var unixTimeStartProtocol14 = varchar("unixTimeStartProtocol14", 256)
    var unixTimeStartProtocol15 = varchar("unixTimeStartProtocol15", 256)
    var unixTimeStartProtocol16 = varchar("unixTimeStartProtocol16", 256)

    var unixTimeWorkProtocol11 = varchar("unixTimeWorkProtocol11", 256)
    var unixTimeWorkProtocol12 = varchar("unixTimeWorkProtocol12", 256)
    var unixTimeWorkProtocol13 = varchar("unixTimeWorkProtocol13", 256)
    var unixTimeWorkProtocol14 = varchar("unixTimeWorkProtocol14", 256)
    var unixTimeWorkProtocol15 = varchar("unixTimeWorkProtocol15", 256)
    var unixTimeWorkProtocol16 = varchar("unixTimeWorkProtocol16", 256)

    var unixTimeEndProtocol11 = varchar("unixTimeEndProtocol11", 256)
    var unixTimeEndProtocol12 = varchar("unixTimeEndProtocol12", 256)
    var unixTimeEndProtocol13 = varchar("unixTimeEndProtocol13", 256)
    var unixTimeEndProtocol14 = varchar("unixTimeEndProtocol14", 256)
    var unixTimeEndProtocol15 = varchar("unixTimeEndProtocol15", 256)
    var unixTimeEndProtocol16 = varchar("unixTimeEndProtocol16", 256)

    var unixTimeStartProtocol21 = varchar("unixTimeStartProtocol21", 256)
    var unixTimeStartProtocol22 = varchar("unixTimeStartProtocol22", 256)
    var unixTimeStartProtocol23 = varchar("unixTimeStartProtocol23", 256)
    var unixTimeStartProtocol24 = varchar("unixTimeStartProtocol24", 256)
    var unixTimeStartProtocol25 = varchar("unixTimeStartProtocol25", 256)
    var unixTimeStartProtocol26 = varchar("unixTimeStartProtocol26", 256)

    var unixTimeWorkProtocol21 = varchar("unixTimeWorkProtocol21", 256)
    var unixTimeWorkProtocol22 = varchar("unixTimeWorkProtocol22", 256)
    var unixTimeWorkProtocol23 = varchar("unixTimeWorkProtocol23", 256)
    var unixTimeWorkProtocol24 = varchar("unixTimeWorkProtocol24", 256)
    var unixTimeWorkProtocol25 = varchar("unixTimeWorkProtocol25", 256)
    var unixTimeWorkProtocol26 = varchar("unixTimeWorkProtocol26", 256)

    var unixTimeEndProtocol21 = varchar("unixTimeEndProtocol21", 256)
    var unixTimeEndProtocol22 = varchar("unixTimeEndProtocol22", 256)
    var unixTimeEndProtocol23 = varchar("unixTimeEndProtocol23", 256)
    var unixTimeEndProtocol24 = varchar("unixTimeEndProtocol24", 256)
    var unixTimeEndProtocol25 = varchar("unixTimeEndProtocol25", 256)
    var unixTimeEndProtocol26 = varchar("unixTimeEndProtocol26", 256)

    var unixTimeStartProtocol31 = varchar("unixTimeStartProtocol31", 256)
    var unixTimeStartProtocol32 = varchar("unixTimeStartProtocol32", 256)
    var unixTimeStartProtocol33 = varchar("unixTimeStartProtocol33", 256)
    var unixTimeStartProtocol34 = varchar("unixTimeStartProtocol34", 256)
    var unixTimeStartProtocol35 = varchar("unixTimeStartProtocol35", 256)
    var unixTimeStartProtocol36 = varchar("unixTimeStartProtocol36", 256)

    var unixTimeWorkProtocol31 = varchar("unixTimeWorkProtocol31", 256)
    var unixTimeWorkProtocol32 = varchar("unixTimeWorkProtocol32", 256)
    var unixTimeWorkProtocol33 = varchar("unixTimeWorkProtocol33", 256)
    var unixTimeWorkProtocol34 = varchar("unixTimeWorkProtocol34", 256)
    var unixTimeWorkProtocol35 = varchar("unixTimeWorkProtocol35", 256)
    var unixTimeWorkProtocol36 = varchar("unixTimeWorkProtocol36", 256)

    var unixTimeEndProtocol31 = varchar("unixTimeEndProtocol31", 256)
    var unixTimeEndProtocol32 = varchar("unixTimeEndProtocol32", 256)
    var unixTimeEndProtocol33 = varchar("unixTimeEndProtocol33", 256)
    var unixTimeEndProtocol34 = varchar("unixTimeEndProtocol34", 256)
    var unixTimeEndProtocol35 = varchar("unixTimeEndProtocol35", 256)
    var unixTimeEndProtocol36 = varchar("unixTimeEndProtocol36", 256)
}

class Protocol(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Protocol>(ProtocolsTable)

    var date by ProtocolsTable.date
    var time by ProtocolsTable.time
    var dateEnd by ProtocolsTable.dateEnd
    var timeEnd by ProtocolsTable.timeEnd
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

    var NUMBER_DATE_ATTESTATION by ProtocolsTable.NUMBER_DATE_ATTESTATION
    var NAME_OF_OPERATION by ProtocolsTable.NAME_OF_OPERATION
    var NUMBER_CONTROLLER by ProtocolsTable.NUMBER_CONTROLLER
    var T1 by ProtocolsTable.T1
    var T2 by ProtocolsTable.T2
    var T3 by ProtocolsTable.T3
    var T4 by ProtocolsTable.T4
    var T5 by ProtocolsTable.T5
    var T6 by ProtocolsTable.T6
    var T7 by ProtocolsTable.T7
    var T8 by ProtocolsTable.T8
    var T9 by ProtocolsTable.T9
    var T10 by ProtocolsTable.T10
    var T11 by ProtocolsTable.T11
    var T12 by ProtocolsTable.T12
    var T13 by ProtocolsTable.T13
    var T14 by ProtocolsTable.T14
    var T15 by ProtocolsTable.T15
    var T16 by ProtocolsTable.T16
    var T17 by ProtocolsTable.T17
    var T18 by ProtocolsTable.T18

        var unixTimeStartProtocol11 by ProtocolsTable.unixTimeStartProtocol11
        var unixTimeStartProtocol12 by ProtocolsTable.unixTimeStartProtocol12
        var unixTimeStartProtocol13 by ProtocolsTable.unixTimeStartProtocol13
        var unixTimeStartProtocol14 by ProtocolsTable.unixTimeStartProtocol14
        var unixTimeStartProtocol15 by ProtocolsTable.unixTimeStartProtocol15
        var unixTimeStartProtocol16 by ProtocolsTable.unixTimeStartProtocol16
        var unixTimeWorkProtocol11 by ProtocolsTable.unixTimeWorkProtocol11
        var unixTimeWorkProtocol12 by ProtocolsTable.unixTimeWorkProtocol12
        var unixTimeWorkProtocol13 by ProtocolsTable.unixTimeWorkProtocol13
        var unixTimeWorkProtocol14 by ProtocolsTable.unixTimeWorkProtocol14
        var unixTimeWorkProtocol15 by ProtocolsTable.unixTimeWorkProtocol15
        var unixTimeWorkProtocol16 by ProtocolsTable.unixTimeWorkProtocol16
        var unixTimeEndProtocol11 by ProtocolsTable.unixTimeEndProtocol11
        var unixTimeEndProtocol12 by ProtocolsTable.unixTimeEndProtocol12
        var unixTimeEndProtocol13 by ProtocolsTable.unixTimeEndProtocol13
        var unixTimeEndProtocol14 by ProtocolsTable.unixTimeEndProtocol14
        var unixTimeEndProtocol15 by ProtocolsTable.unixTimeEndProtocol15
        var unixTimeEndProtocol16 by ProtocolsTable.unixTimeEndProtocol16

    var unixTimeStartProtocol21 by ProtocolsTable.unixTimeStartProtocol21
    var unixTimeStartProtocol22 by ProtocolsTable.unixTimeStartProtocol22
    var unixTimeStartProtocol23 by ProtocolsTable.unixTimeStartProtocol23
    var unixTimeStartProtocol24 by ProtocolsTable.unixTimeStartProtocol24
    var unixTimeStartProtocol25 by ProtocolsTable.unixTimeStartProtocol25
    var unixTimeStartProtocol26 by ProtocolsTable.unixTimeStartProtocol26
    var  unixTimeWorkProtocol21 by  ProtocolsTable.unixTimeWorkProtocol21
    var  unixTimeWorkProtocol22 by  ProtocolsTable.unixTimeWorkProtocol22
    var  unixTimeWorkProtocol23 by  ProtocolsTable.unixTimeWorkProtocol23
    var  unixTimeWorkProtocol24 by  ProtocolsTable.unixTimeWorkProtocol24
    var  unixTimeWorkProtocol25 by  ProtocolsTable.unixTimeWorkProtocol25
    var  unixTimeWorkProtocol26 by  ProtocolsTable.unixTimeWorkProtocol26
    var   unixTimeEndProtocol21 by   ProtocolsTable.unixTimeEndProtocol21
    var   unixTimeEndProtocol22 by   ProtocolsTable.unixTimeEndProtocol22
    var   unixTimeEndProtocol23 by   ProtocolsTable.unixTimeEndProtocol23
    var   unixTimeEndProtocol24 by   ProtocolsTable.unixTimeEndProtocol24
    var   unixTimeEndProtocol25 by   ProtocolsTable.unixTimeEndProtocol25
    var   unixTimeEndProtocol26 by   ProtocolsTable.unixTimeEndProtocol26

    var unixTimeStartProtocol31 by ProtocolsTable.unixTimeStartProtocol31
    var unixTimeStartProtocol32 by ProtocolsTable.unixTimeStartProtocol32
    var unixTimeStartProtocol33 by ProtocolsTable.unixTimeStartProtocol33
    var unixTimeStartProtocol34 by ProtocolsTable.unixTimeStartProtocol34
    var unixTimeStartProtocol35 by ProtocolsTable.unixTimeStartProtocol35
    var unixTimeStartProtocol36 by ProtocolsTable.unixTimeStartProtocol36
    var  unixTimeWorkProtocol31 by  ProtocolsTable.unixTimeWorkProtocol31
    var  unixTimeWorkProtocol32 by  ProtocolsTable.unixTimeWorkProtocol32
    var  unixTimeWorkProtocol33 by  ProtocolsTable.unixTimeWorkProtocol33
    var  unixTimeWorkProtocol34 by  ProtocolsTable.unixTimeWorkProtocol34
    var  unixTimeWorkProtocol35 by  ProtocolsTable.unixTimeWorkProtocol35
    var  unixTimeWorkProtocol36 by  ProtocolsTable.unixTimeWorkProtocol36
    var   unixTimeEndProtocol31 by   ProtocolsTable.unixTimeEndProtocol31
    var   unixTimeEndProtocol32 by   ProtocolsTable.unixTimeEndProtocol32
    var   unixTimeEndProtocol33 by   ProtocolsTable.unixTimeEndProtocol33
    var   unixTimeEndProtocol34 by   ProtocolsTable.unixTimeEndProtocol34
    var   unixTimeEndProtocol35 by   ProtocolsTable.unixTimeEndProtocol35
    var   unixTimeEndProtocol36 by   ProtocolsTable.unixTimeEndProtocol36

    override fun toString(): String {
        return "$id"
    }
}
