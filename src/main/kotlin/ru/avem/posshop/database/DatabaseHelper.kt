package ru.avem.posshop.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.posshop.database.entities.*
import ru.avem.posshop.database.entities.Users.fullName
import java.sql.Connection

fun validateDB() {
    Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {
        SchemaUtils.create(
            Users,
            ProtocolsTable,
            ProtocolsTableInsulation,
            ProtocolsSingleTable,
            ProtocolsRotorBladeTable,
            ProtocolVarsTable
        )
    }

    transaction {
        if (User.all().count() < 2) {
            val admin = User.find {
                fullName eq "admin"
            }

            if (admin.empty()) {
                User.new {
                    fullName = "admin"
                    password = "avem"
                }
            }

            if (ProtocolVars.all().count() < 1) {
                ProtocolVars.new {
                    NUMBER_DATE_ATTESTATION = "номер и дата аттестации"
                    NAME_OF_OPERATION = "Наименование и шифр технологического процесса"
                    NUMBER_CONTROLLER = "1"
                    T1 = "1"
                    T2 = "2"
                    T3 = "3"
                    T4 = "4"
                    T5 = "5"
                    T6 = "6"
                    T7 = "7"
                    T8 = "8"
                    T9 = "9"
                    T10 = "10"
                    T11 = "11"
                    T12 = "12"
                    T13 = "13"
                    T14 = "14"
                    T15 = "15"
                    T16 = "16"
                    T17 = "17"
                    T18 = "18"
                }

                ProtocolSingle.new {
                    date = "10.03.2020"
                    time = "11:30:00"
                    section = "1 лопасть 1 секция"
                    temp = "[1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0]"
                }

                ProtocolRotorBlade.new {
                    NUMBER_DATE_ATTESTATION = "номер и дата аттестации"
                    NAME_OF_OPERATION = "Наименование и шифр технологического процесса"
                    NUMBER_CONTROLLER = "1"
                    date = "10.03.2020"
                    time = "11:30:00"
                    dateEnd = "10.03.2021"
                    timeEnd = "15:25:35"
                    cipher = "#666"
                    productName = "123456789"
                    operator = "Иванов И.И."
                    temp1 = "[1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0]"
                    temp2 = "[1,0, 2,0, 3,0, 4,0, 5,0, 6,0, 7,0, 8,0, 9,0, 10,0, 11,0, 12,0]"
                    temp3 = "[0,1, 0,2, 0,4, 0,8, 1,6, 3,2, 6,4, 12,8, 25,6, 51,2, 102,4, 204,8]"
                    temp4 = "[1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0, 1,0]"
                    temp5 = "[1,0, 2,0, 3,0, 4,0, 5,0, 6,0, 7,0, 8,0, 9,0, 10,0, 11,0, 12,0]"
                    temp6 = "[0,1, 0,2, 0,4, 0,8, 1,6, 3,2, 6,4, 12,8, 25,6, 51,2, 102,4, 204,8]"
                    T1 = "1"
                    T2 = "2"
                    T3 = "3"
                    T4 = "4"
                    T5 = "5"
                    T6 = "6"
                    unixTimeStartProtocol1 = "11:30:01"
                    unixTimeStartProtocol2 = "11:30:02"
                    unixTimeStartProtocol3 = "11:30:03"
                    unixTimeStartProtocol4 = "11:30:04"
                    unixTimeStartProtocol5 = "11:30:05"
                    unixTimeStartProtocol6 = "11:30:06"
                     unixTimeWorkProtocol1 = "11:30:07"
                     unixTimeWorkProtocol2 = "11:30:08"
                     unixTimeWorkProtocol3 = "11:30:09"
                     unixTimeWorkProtocol4 = "11:30:10"
                     unixTimeWorkProtocol5 = "11:30:11"
                     unixTimeWorkProtocol6 = "11:30:12"
                      unixTimeEndProtocol1 = "11:30:13"
                      unixTimeEndProtocol2 = "11:30:14"
                      unixTimeEndProtocol3 = "11:30:15"
                      unixTimeEndProtocol4 = "11:30:16"
                      unixTimeEndProtocol5 = "11:30:17"
                      unixTimeEndProtocol6 = "11:30:18"
                }

                Protocol.new {
                    date = "10.03.2020"
                    time = "11:30:00"
                    dateEnd = "10.03.2021"
                    timeEnd = "15:25:35"
                    cipher1 = "#1616161"
                    cipher2 = "#2626262"
                    cipher3 = "#3636363"
                    productNumber1 = "11111111111"
                    productNumber2 = "22222222222"
                    productNumber3 = "33333333333"
                    operator = "Иванов И.И."
                    val list1 = mutableListOf<String>()
                    val list2 = mutableListOf<String>()
                    val list3 = mutableListOf<String>()
                    for (i in 0..10000) {
                        list1.add("1")
                        list2.add((i).toString())
                        list3.add((i * i).toString())
                    }
                    temp11 = list1.toString()
                    temp12 = list2.toString()
                    temp13 = list3.toString()
                    temp14 = list1.toString()
                    temp15 = list2.toString()
                    temp16 = list3.toString()
                    temp21 = list1.toString()
                    temp22 = list2.toString()
                    temp23 = list3.toString()
                    temp24 = list1.toString()
                    temp25 = list2.toString()
                    temp26 = list3.toString()
                    temp31 = list1.toString()
                    temp32 = list2.toString()
                    temp33 = list3.toString()
                    temp34 = list1.toString()
                    temp35 = list2.toString()
                    temp36 = list3.toString()

                    NUMBER_DATE_ATTESTATION = "Номер и дата аттестации"
                    NAME_OF_OPERATION = "Наименование и шифр технологического процесса"
                    NUMBER_CONTROLLER = "№666-777-1337"
                    T1 = "1"
                    T2 = "1"
                    T3 = "1"
                    T4 = "1"
                    T5 = "1"
                    T6 = "1"
                    T7 = "1"
                    T8 = "1"
                    T9 = "1"
                    T10 = "1"
                    T11 = "1"
                    T12 = "1"
                    T13 = "1"
                    T14 = "1"
                    T15 = "1"
                    T16 = "1"
                    T17 = "1"
                    T18 = "1"

                    unixTimeStartProtocol11 = "11:30:01"
                    unixTimeStartProtocol12 = "11:30:02"
                    unixTimeStartProtocol13 = "11:30:03"
                    unixTimeStartProtocol14 = "11:30:04"
                    unixTimeStartProtocol15 = "11:30:05"
                    unixTimeStartProtocol16 = "11:30:06"
                     unixTimeWorkProtocol11 = "11:30:07"
                     unixTimeWorkProtocol12 = "11:30:08"
                     unixTimeWorkProtocol13 = "11:30:09"
                     unixTimeWorkProtocol14 = "11:30:10"
                     unixTimeWorkProtocol15 = "11:30:11"
                     unixTimeWorkProtocol16 = "11:30:12"
                      unixTimeEndProtocol11 = "11:30:13"
                      unixTimeEndProtocol12 = "11:30:14"
                      unixTimeEndProtocol13 = "11:30:15"
                      unixTimeEndProtocol14 = "11:30:16"
                      unixTimeEndProtocol15 = "11:30:17"
                      unixTimeEndProtocol16 = "11:30:18"

                    unixTimeStartProtocol21 = "12:30:01"
                    unixTimeStartProtocol22 = "12:30:02"
                    unixTimeStartProtocol23 = "12:30:03"
                    unixTimeStartProtocol24 = "12:30:04"
                    unixTimeStartProtocol25 = "12:30:05"
                    unixTimeStartProtocol26 = "12:30:06"
                     unixTimeWorkProtocol21 = "12:30:07"
                     unixTimeWorkProtocol22 = "12:30:08"
                     unixTimeWorkProtocol23 = "12:30:09"
                     unixTimeWorkProtocol24 = "12:30:10"
                     unixTimeWorkProtocol25 = "12:30:11"
                     unixTimeWorkProtocol26 = "12:30:12"
                      unixTimeEndProtocol21 = "12:30:13"
                      unixTimeEndProtocol22 = "12:30:14"
                      unixTimeEndProtocol23 = "12:30:15"
                      unixTimeEndProtocol24 = "12:30:16"
                      unixTimeEndProtocol25 = "12:30:17"
                      unixTimeEndProtocol26 = "12:30:18"

                    unixTimeStartProtocol31 = "13:30:01"
                    unixTimeStartProtocol32 = "13:30:02"
                    unixTimeStartProtocol33 = "13:30:03"
                    unixTimeStartProtocol34 = "13:30:04"
                    unixTimeStartProtocol35 = "13:30:05"
                    unixTimeStartProtocol36 = "13:30:06"
                     unixTimeWorkProtocol31 = "13:30:07"
                     unixTimeWorkProtocol32 = "13:30:08"
                     unixTimeWorkProtocol33 = "13:30:09"
                     unixTimeWorkProtocol34 = "13:30:10"
                     unixTimeWorkProtocol35 = "13:30:11"
                     unixTimeWorkProtocol36 = "13:30:12"
                      unixTimeEndProtocol31 = "13:30:13"
                      unixTimeEndProtocol32 = "13:30:14"
                      unixTimeEndProtocol33 = "13:30:15"
                      unixTimeEndProtocol34 = "13:30:16"
                      unixTimeEndProtocol35 = "13:30:17"
                      unixTimeEndProtocol36 = "13:30:18"
                }

                ProtocolInsulation.new {
                    date = "10.03.2020"
                    time = "11:30:00"
                    dateEnd = "10.03.2021"
                    timeEnd = "15:25:35"
                    cipher1 = "#000"
                    productNumber1 = "1"
                    cipher2 = "#111"
                    productNumber2 = "2"
                    cipher3 = "#222"
                    productNumber3 = "3"
                    operator = "Иванов И.И"

                    val list1 = mutableListOf<String>()
                    val list2 = mutableListOf<String>()
                    val list3 = mutableListOf<String>()
                    for (i in 0..10000) {
                        list1.add("1")
                        list2.add((i).toString())
                        list3.add((i * i).toString())
                    }

                    voltage = list1.toString()
                    amperage1 = list2.toString()
                    amperage2 = list3.toString()
                    amperage3 = list1.toString()
                    NUMBER_DATE_ATTESTATION = "Номер и дата аттестации"
                    NAME_OF_OPERATION = "Наименование и шифр технологического процесса"
                    NUMBER_CONTROLLER = "№666-777-1337"
                }
            }
        }
    }
}
