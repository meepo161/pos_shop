package ru.avem.posshop.protocol

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.usermodel.charts.AxisPosition
import org.apache.poi.ss.usermodel.charts.ChartDataSource
import org.apache.poi.ss.usermodel.charts.DataSources
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFChart
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean
import ru.avem.posshop.app.Pos
import ru.avem.posshop.database.entities.Protocol
import ru.avem.posshop.database.entities.ProtocolInsulation
import ru.avem.posshop.database.entities.ProtocolRotorBlade
import ru.avem.posshop.database.entities.ProtocolSingle
import ru.avem.posshop.utils.Toast
import ru.avem.posshop.utils.copyFileFromStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException

var TO_DESIRED_ROW = 0

fun saveProtocolAsWorkbook(protocol: Protocol, path: String = "protocol.xlsx") {
    val template = File(path)
    copyFileFromStream(Pos::class.java.getResource("protocol.xlsx").openStream(), template)

    try {
        XSSFWorkbook(template).use { wb ->
            val sheet = wb.getSheetAt(0)
            for (iRow in 0 until 100) {
                val row = sheet.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 100) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {
                            when (cell.stringCellValue) {
                                "#PROTOCOL_NUMBER#" -> cell.setCellValue(protocol.id.toString())
                                "#DATE#" -> cell.setCellValue(protocol.date)
                                "#DATE_END#" -> cell.setCellValue(protocol.dateEnd)
                                "#TIME#" -> cell.setCellValue(protocol.time)
                                "#TIME_END#" -> cell.setCellValue(protocol.timeEnd)
                                "#CIPHER1#" -> cell.setCellValue(protocol.cipher1)
                                "#NUMBER_PRODUCT1#" -> cell.setCellValue(protocol.productNumber1)
                                "#CIPHER2#" -> cell.setCellValue(protocol.cipher2)
                                "#NUMBER_PRODUCT2#" -> cell.setCellValue(protocol.productNumber2)
                                "#CIPHER3#" -> cell.setCellValue(protocol.cipher3)
                                "#NUMBER_PRODUCT3#" -> cell.setCellValue(protocol.productNumber3)
                                "#OPERATOR#" -> cell.setCellValue(protocol.operator)

                                "#NUMBER_DATE_ATTESTATION#" -> cell.setCellValue(protocol.NUMBER_DATE_ATTESTATION)
                                "#NAME_OF_OPERATION#" -> cell.setCellValue(protocol.NAME_OF_OPERATION)
                                "#NUMBER_CONTROLLER#" -> cell.setCellValue(protocol.NUMBER_CONTROLLER)
                                "#T1#" -> cell.setCellValue(protocol.T1)
                                "#T2#" -> cell.setCellValue(protocol.T2)
                                "#T3#" -> cell.setCellValue(protocol.T3)
                                "#T4#" -> cell.setCellValue(protocol.T4)
                                "#T5#" -> cell.setCellValue(protocol.T5)
                                "#T6#" -> cell.setCellValue(protocol.T6)
                                "#T7#" -> cell.setCellValue(protocol.T7)
                                "#T8#" -> cell.setCellValue(protocol.T8)
                                "#T9#" -> cell.setCellValue(protocol.T9)
                                "#T10#" -> cell.setCellValue(protocol.T10)
                                "#T11#" -> cell.setCellValue(protocol.T11)
                                "#T12#" -> cell.setCellValue(protocol.T12)
                                "#T13#" -> cell.setCellValue(protocol.T13)
                                "#T14#" -> cell.setCellValue(protocol.T14)
                                "#T15#" -> cell.setCellValue(protocol.T15)
                                "#T16#" -> cell.setCellValue(protocol.T16)
                                "#T17#" -> cell.setCellValue(protocol.T17)
                                "#T18#" -> cell.setCellValue(protocol.T18)

                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val sheet2 = wb.getSheetAt(1)
            for (iRow in 0 until 1000) {
                val row = sheet2.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 1000) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {
                            when (cell.stringCellValue) {
                                "#PROTOCOL_NUMBER#" -> cell.setCellValue(protocol.id.toString())
                                "#DATE#" -> cell.setCellValue(protocol.date)
                                "#TIME#" -> cell.setCellValue(protocol.time)
                                "#DATE_END#" -> cell.setCellValue(protocol.dateEnd)
                                "#TIME_END#" -> cell.setCellValue(protocol.timeEnd)
                                "#CIPHER1#" -> cell.setCellValue(protocol.cipher1)
                                "#CIPHER2#" -> cell.setCellValue(protocol.cipher2)
                                "#CIPHER3#" -> cell.setCellValue(protocol.cipher3)
                                "#NUMBER_PRODUCT1#" -> cell.setCellValue(protocol.productNumber1)
                                "#NUMBER_PRODUCT2#" -> cell.setCellValue(protocol.productNumber2)
                                "#NUMBER_PRODUCT3#" -> cell.setCellValue(protocol.productNumber3)
                                "#OPERATOR#" -> cell.setCellValue(protocol.operator)
                                "#NUMBER_DATE_ATTESTATION#" -> cell.setCellValue(protocol.NUMBER_DATE_ATTESTATION)
                                "#NAME_OF_OPERATION#" -> cell.setCellValue(protocol.NAME_OF_OPERATION)
                                "#NUMBER_CONTROLLER#" -> cell.setCellValue(protocol.NUMBER_CONTROLLER)

                                "#TIMESTART11#" -> cell.setCellValue(protocol.unixTimeStartProtocol11)
                                "#TIMESTART12#" -> cell.setCellValue(protocol.unixTimeStartProtocol12)
                                "#TIMESTART13#" -> cell.setCellValue(protocol.unixTimeStartProtocol13)
                                "#TIMESTART14#" -> cell.setCellValue(protocol.unixTimeStartProtocol14)
                                "#TIMESTART15#" -> cell.setCellValue(protocol.unixTimeStartProtocol15)
                                "#TIMESTART16#" -> cell.setCellValue(protocol.unixTimeStartProtocol16)
                                "#TIMESTART21#" -> cell.setCellValue(protocol.unixTimeStartProtocol21)
                                "#TIMESTART22#" -> cell.setCellValue(protocol.unixTimeStartProtocol22)
                                "#TIMESTART23#" -> cell.setCellValue(protocol.unixTimeStartProtocol23)
                                "#TIMESTART24#" -> cell.setCellValue(protocol.unixTimeStartProtocol24)
                                "#TIMESTART25#" -> cell.setCellValue(protocol.unixTimeStartProtocol25)
                                "#TIMESTART26#" -> cell.setCellValue(protocol.unixTimeStartProtocol26)
                                "#TIMESTART31#" -> cell.setCellValue(protocol.unixTimeStartProtocol31)
                                "#TIMESTART32#" -> cell.setCellValue(protocol.unixTimeStartProtocol32)
                                "#TIMESTART33#" -> cell.setCellValue(protocol.unixTimeStartProtocol33)
                                "#TIMESTART34#" -> cell.setCellValue(protocol.unixTimeStartProtocol34)
                                "#TIMESTART35#" -> cell.setCellValue(protocol.unixTimeStartProtocol35)
                                "#TIMESTART36#" -> cell.setCellValue(protocol.unixTimeStartProtocol36)

                                "#TIMEEND11#" -> cell.setCellValue(protocol.unixTimeEndProtocol11)
                                "#TIMEEND12#" -> cell.setCellValue(protocol.unixTimeEndProtocol12)
                                "#TIMEEND13#" -> cell.setCellValue(protocol.unixTimeEndProtocol13)
                                "#TIMEEND14#" -> cell.setCellValue(protocol.unixTimeEndProtocol14)
                                "#TIMEEND15#" -> cell.setCellValue(protocol.unixTimeEndProtocol15)
                                "#TIMEEND16#" -> cell.setCellValue(protocol.unixTimeEndProtocol16)
                                "#TIMEEND21#" -> cell.setCellValue(protocol.unixTimeEndProtocol21)
                                "#TIMEEND22#" -> cell.setCellValue(protocol.unixTimeEndProtocol22)
                                "#TIMEEND23#" -> cell.setCellValue(protocol.unixTimeEndProtocol23)
                                "#TIMEEND24#" -> cell.setCellValue(protocol.unixTimeEndProtocol24)
                                "#TIMEEND25#" -> cell.setCellValue(protocol.unixTimeEndProtocol25)
                                "#TIMEEND26#" -> cell.setCellValue(protocol.unixTimeEndProtocol26)
                                "#TIMEEND31#" -> cell.setCellValue(protocol.unixTimeEndProtocol31)
                                "#TIMEEND32#" -> cell.setCellValue(protocol.unixTimeEndProtocol32)
                                "#TIMEEND33#" -> cell.setCellValue(protocol.unixTimeEndProtocol33)
                                "#TIMEEND34#" -> cell.setCellValue(protocol.unixTimeEndProtocol34)
                                "#TIMEEND35#" -> cell.setCellValue(protocol.unixTimeEndProtocol35)
                                "#TIMEEND36#" -> cell.setCellValue(protocol.unixTimeEndProtocol36)

                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            fillParameters18(
                wb,
                protocol.temp11,
                protocol.temp12,
                protocol.temp13,
                protocol.temp14,
                protocol.temp15,
                protocol.temp16,
                protocol.temp21,
                protocol.temp22,
                protocol.temp23,
                protocol.temp24,
                protocol.temp25,
                protocol.temp26,
                protocol.temp31,
                protocol.temp32,
                protocol.temp33,
                protocol.temp34,
                protocol.temp35,
                protocol.temp36,

                protocol.voltage11,
                protocol.voltage12,
                protocol.voltage13,
                protocol.voltage14,
                protocol.voltage15,
                protocol.voltage16,
                protocol.voltage21,
                protocol.voltage22,
                protocol.voltage23,
                protocol.voltage24,
                protocol.voltage25,
                protocol.voltage26,
                protocol.voltage31,
                protocol.voltage32,
                protocol.voltage33,
                protocol.voltage34,
                protocol.voltage35,
                protocol.voltage36,

                protocol.amperage11,
                protocol.amperage12,
                protocol.amperage13,
                protocol.amperage14,
                protocol.amperage15,
                protocol.amperage16,
                protocol.amperage21,
                protocol.amperage22,
                protocol.amperage23,
                protocol.amperage24,
                protocol.amperage25,
                protocol.amperage26,
                protocol.amperage31,
                protocol.amperage32,
                protocol.amperage33,
                protocol.amperage34,
                protocol.amperage35,
                protocol.amperage36,
                0, 15
            )
            drawLineChart36(wb, protocol)

            sheet.protectSheet("avem")
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: Exception) {
        Toast.makeText("Не удалось сохранить протокол на диск")
    }
}

fun saveProtocolAsWorkbook(protocol: ProtocolInsulation, path: String = "protocolInsulation.xlsx") {
    val template = File(path)
    copyFileFromStream(Pos::class.java.getResource("protocolInsulation.xlsx").openStream(), template)

    try {
        XSSFWorkbook(template).use { wb ->
            val sheet = wb.getSheetAt(0)
            for (iRow in 0 until 100) {
                val row = sheet.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 100) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {
                            when (cell.stringCellValue) {
                                "#PROTOCOL_NUMBER#" -> cell.setCellValue(protocol.id.toString())
                                "#DATE#" -> cell.setCellValue(protocol.date)
                                "#DATE_END#" -> cell.setCellValue(protocol.dateEnd)
                                "#TIME#" -> cell.setCellValue(protocol.time)
                                "#TIME_END#" -> cell.setCellValue(protocol.timeEnd)
                                "#NUMBER_DATE_ATTESTATION#" -> cell.setCellValue(protocol.NUMBER_DATE_ATTESTATION)
                                "#NAME_OF_OPERATION#" -> cell.setCellValue(protocol.NAME_OF_OPERATION)
                                "#NUMBER_CONTROLLER#" -> cell.setCellValue(protocol.NUMBER_CONTROLLER)
                                "#CIPHER1#" -> cell.setCellValue(protocol.cipher1)
                                "#NUMBER_PRODUCT1#" -> cell.setCellValue(protocol.productNumber1)
                                "#CIPHER2#" -> cell.setCellValue(protocol.cipher2)
                                "#NUMBER_PRODUCT2#" -> cell.setCellValue(protocol.productNumber2)
                                "#CIPHER3#" -> cell.setCellValue(protocol.cipher3)
                                "#NUMBER_PRODUCT3#" -> cell.setCellValue(protocol.productNumber3)
                                "#OPERATOR#" -> cell.setCellValue(protocol.operator)

                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val sheet2 = wb.getSheetAt(1)
            for (iRow in 0 until 1000) {
                val row = sheet2.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 1000) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {
                            when (cell.stringCellValue) {
                                "#PROTOCOL_NUMBER#" -> cell.setCellValue(protocol.id.toString())
                                "#DATE#" -> cell.setCellValue(protocol.date)
                                "#DATE_END#" -> cell.setCellValue(protocol.dateEnd)
                                "#TIME#" -> cell.setCellValue(protocol.time)
                                "#TIME_END#" -> cell.setCellValue(protocol.timeEnd)
                                "#NUMBER_DATE_ATTESTATION#" -> cell.setCellValue(protocol.NUMBER_DATE_ATTESTATION)
                                "#NAME_OF_OPERATION#" -> cell.setCellValue(protocol.NAME_OF_OPERATION)
                                "#NUMBER_CONTROLLER#" -> cell.setCellValue(protocol.NUMBER_CONTROLLER)
                                "#CIPHER1#" -> cell.setCellValue(protocol.cipher1)
                                "#NUMBER_PRODUCT1#" -> cell.setCellValue(protocol.productNumber1)
                                "#CIPHER2#" -> cell.setCellValue(protocol.cipher2)
                                "#NUMBER_PRODUCT2#" -> cell.setCellValue(protocol.productNumber2)
                                "#CIPHER3#" -> cell.setCellValue(protocol.cipher3)
                                "#NUMBER_PRODUCT3#" -> cell.setCellValue(protocol.productNumber3)
                                "#OPERATOR#" -> cell.setCellValue(protocol.operator)
                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            fillParameters4(
                wb,
                protocol.voltage,
                protocol.amperage1,
                protocol.amperage2,
                protocol.amperage3,
                0, 15
            )
            drawLineChart4(wb)
            sheet.protectSheet("avem")
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: Exception) {
        Toast.makeText("Не удалось сохранить протокол на диск")
    }
}

fun saveProtocolAsWorkbook(protocolRotorBlade: ProtocolRotorBlade, path: String = "protocol1RotorBlade.xlsx") {
    val template = File(path)
    copyFileFromStream(Pos::class.java.getResource("protocol1RotorBlade.xlsx").openStream(), template)
    try {
        XSSFWorkbook(template).use { wb ->
            val sheet = wb.getSheetAt(0)
            for (iRow in 0 until 100) {
                val row = sheet.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 100) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {
                            when (cell.stringCellValue) {
                                "#PROTOCOL_NUMBER#" -> cell.setCellValue(protocolRotorBlade.id.toString())
                                "#DATE#" -> cell.setCellValue(protocolRotorBlade.date)
                                "#DATE_END#" -> cell.setCellValue(protocolRotorBlade.dateEnd)
                                "#TIME#" -> cell.setCellValue(protocolRotorBlade.time)
                                "#TIME_END#" -> cell.setCellValue(protocolRotorBlade.timeEnd)
                                "#CIPHER#" -> cell.setCellValue(protocolRotorBlade.cipher)
                                "#NUMBER_PRODUCT#" -> cell.setCellValue(protocolRotorBlade.productName)
                                "#OPERATOR#" -> cell.setCellValue(protocolRotorBlade.operator)
                                "#NUMBER_DATE_ATTESTATION#" -> cell.setCellValue(protocolRotorBlade.NUMBER_DATE_ATTESTATION)
                                "#NAME_OF_OPERATION#" -> cell.setCellValue(protocolRotorBlade.NAME_OF_OPERATION)
                                "#NUMBER_CONTROLLER#" -> cell.setCellValue(protocolRotorBlade.NUMBER_CONTROLLER)
                                "#T1#" -> cell.setCellValue(protocolRotorBlade.T1)
                                "#T2#" -> cell.setCellValue(protocolRotorBlade.T2)
                                "#T3#" -> cell.setCellValue(protocolRotorBlade.T3)
                                "#T4#" -> cell.setCellValue(protocolRotorBlade.T4)
                                "#T5#" -> cell.setCellValue(protocolRotorBlade.T5)
                                "#T6#" -> cell.setCellValue(protocolRotorBlade.T6)
                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            val sheet2 = wb.getSheetAt(1)
            for (iRow in 0 until 1000) {
                val row = sheet2.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 1000) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {
                            when (cell.stringCellValue) {
                                "#PROTOCOL_NUMBER#" -> cell.setCellValue(protocolRotorBlade.id.toString())
                                "#DATE#" -> cell.setCellValue(protocolRotorBlade.date)
                                "#DATE_END#" -> cell.setCellValue(protocolRotorBlade.dateEnd)
                                "#TIME#" -> cell.setCellValue(protocolRotorBlade.time)
                                "#TIME_END#" -> cell.setCellValue(protocolRotorBlade.timeEnd)
                                "#CIPHER#" -> cell.setCellValue(protocolRotorBlade.cipher)
                                "#NUMBER_PRODUCT#" -> cell.setCellValue(protocolRotorBlade.productName)
                                "#OPERATOR#" -> cell.setCellValue(protocolRotorBlade.operator)
                                "#NUMBER_DATE_ATTESTATION#" -> cell.setCellValue(protocolRotorBlade.NUMBER_DATE_ATTESTATION)
                                "#NAME_OF_OPERATION#" -> cell.setCellValue(protocolRotorBlade.NAME_OF_OPERATION)
                                "#NUMBER_CONTROLLER#" -> cell.setCellValue(protocolRotorBlade.NUMBER_CONTROLLER)
                                "#T1#" -> cell.setCellValue(protocolRotorBlade.T1)
                                "#T2#" -> cell.setCellValue(protocolRotorBlade.T2)
                                "#T3#" -> cell.setCellValue(protocolRotorBlade.T3)
                                "#T4#" -> cell.setCellValue(protocolRotorBlade.T4)
                                "#T5#" -> cell.setCellValue(protocolRotorBlade.T5)
                                "#T6#" -> cell.setCellValue(protocolRotorBlade.T6)
                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            fillParameters6(
                wb,
                protocolRotorBlade.temp1,
                protocolRotorBlade.temp2,
                protocolRotorBlade.temp3,
                protocolRotorBlade.temp4,
                protocolRotorBlade.temp5,
                protocolRotorBlade.temp6,
                protocolRotorBlade.voltage1,
                protocolRotorBlade.voltage2,
                protocolRotorBlade.voltage3,
                protocolRotorBlade.voltage4,
                protocolRotorBlade.voltage5,
                protocolRotorBlade.voltage6,
                protocolRotorBlade.amperage1,
                protocolRotorBlade.amperage2,
                protocolRotorBlade.amperage3,
                protocolRotorBlade.amperage4,
                protocolRotorBlade.amperage5,
                protocolRotorBlade.amperage6,
                0, 15
            ) //TODO
            drawLineChart6(wb, protocolRotorBlade)
            sheet.protectSheet("avem")
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: Exception) {
        Toast.makeText("Не удалось сохранить протокол на диск")
    }
}

fun saveProtocolAsWorkbook(protocolSingle: ProtocolSingle, path: String = "protocol.xlsx", start: Int, end: Int) {
    val template = File(path)
    copyFileFromStream(Pos::class.java.getResource("protocol.xlsx").openStream(), template)

    try {
        XSSFWorkbook(template).use { wb ->
            val sheet = wb.getSheetAt(0)
            for (iRow in 0 until 100) {
                val row = sheet.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 100) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {
                            when (cell.stringCellValue) {
                                "#PROTOCOL_NUMBER#" -> cell.setCellValue(protocolSingle.id.toString())
                                "#DATE#" -> cell.setCellValue(protocolSingle.date)
                                "#TIME#" -> cell.setCellValue(protocolSingle.time)

                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            fillParameters(wb, protocolSingle.temp, start, end)
            drawLineChart(wb, protocolSingle.section)
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: FileNotFoundException) {
        Toast.makeText("Не удалось сохранить протокол на диск")
    }
}

fun fillParameters18(
    wb: XSSFWorkbook,
    dots11: String,
    dots12: String,
    dots13: String,
    dots14: String,
    dots15: String,
    dots16: String,
    dots21: String,
    dots22: String,
    dots23: String,
    dots24: String,
    dots25: String,
    dots26: String,
    dots31: String,
    dots32: String,
    dots33: String,
    dots34: String,
    dots35: String,
    dots36: String,

    voltage11: String,
    voltage12: String,
    voltage13: String,
    voltage14: String,
    voltage15: String,
    voltage16: String,
    voltage21: String,
    voltage22: String,
    voltage23: String,
    voltage24: String,
    voltage25: String,
    voltage26: String,
    voltage31: String,
    voltage32: String,
    voltage33: String,
    voltage34: String,
    voltage35: String,
    voltage36: String,

    amperage11: String,
    amperage12: String,
    amperage13: String,
    amperage14: String,
    amperage15: String,
    amperage16: String,
    amperage21: String,
    amperage22: String,
    amperage23: String,
    amperage24: String,
    amperage25: String,
    amperage26: String,
    amperage31: String,
    amperage32: String,
    amperage33: String,
    amperage34: String,
    amperage35: String,
    amperage36: String,
    columnNumber: Int, rawNumber: Int
) {
    val values11 = dots11.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values12 = dots12.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values13 = dots13.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values14 = dots14.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values15 = dots15.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values16 = dots16.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values21 = dots21.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values22 = dots22.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values23 = dots23.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values24 = dots24.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values25 = dots25.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values26 = dots26.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values31 = dots31.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values32 = dots32.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values33 = dots33.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values34 = dots34.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values35 = dots35.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val values36 = dots36.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)

    val valuesVoltage11 = voltage11.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage12 = voltage12.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage13 = voltage13.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage14 = voltage14.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage15 = voltage15.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage16 = voltage16.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage21 = voltage21.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage22 = voltage22.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage23 = voltage23.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage24 = voltage24.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage25 = voltage25.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage26 = voltage26.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage31 = voltage31.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage32 = voltage32.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage33 = voltage33.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage34 = voltage34.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage35 = voltage35.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage36 = voltage36.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)

    val valuesAmperage11 = amperage11.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage12 = amperage12.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage13 = amperage13.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage14 = amperage14.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage15 = amperage15.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage16 = amperage16.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage21 = amperage21.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage22 = amperage22.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage23 = amperage23.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage24 = amperage24.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage25 = amperage25.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage26 = amperage26.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage31 = amperage31.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage32 = amperage32.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage33 = amperage33.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage34 = amperage34.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage35 = amperage35.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage36 = amperage36.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)

    val valuesForExcel11 = arrayListOf<Double>()
    val valuesForExcel12 = arrayListOf<Double>()
    val valuesForExcel13 = arrayListOf<Double>()
    val valuesForExcel14 = arrayListOf<Double>()
    val valuesForExcel15 = arrayListOf<Double>()
    val valuesForExcel16 = arrayListOf<Double>()
    val valuesForExcel21 = arrayListOf<Double>()
    val valuesForExcel22 = arrayListOf<Double>()
    val valuesForExcel23 = arrayListOf<Double>()
    val valuesForExcel24 = arrayListOf<Double>()
    val valuesForExcel25 = arrayListOf<Double>()
    val valuesForExcel26 = arrayListOf<Double>()
    val valuesForExcel31 = arrayListOf<Double>()
    val valuesForExcel32 = arrayListOf<Double>()
    val valuesForExcel33 = arrayListOf<Double>()
    val valuesForExcel34 = arrayListOf<Double>()
    val valuesForExcel35 = arrayListOf<Double>()
    val valuesForExcel36 = arrayListOf<Double>()

    val valuesForExcelVoltage11 = arrayListOf<Double>()
    val valuesForExcelVoltage12 = arrayListOf<Double>()
    val valuesForExcelVoltage13 = arrayListOf<Double>()
    val valuesForExcelVoltage14 = arrayListOf<Double>()
    val valuesForExcelVoltage15 = arrayListOf<Double>()
    val valuesForExcelVoltage16 = arrayListOf<Double>()
    val valuesForExcelVoltage21 = arrayListOf<Double>()
    val valuesForExcelVoltage22 = arrayListOf<Double>()
    val valuesForExcelVoltage23 = arrayListOf<Double>()
    val valuesForExcelVoltage24 = arrayListOf<Double>()
    val valuesForExcelVoltage25 = arrayListOf<Double>()
    val valuesForExcelVoltage26 = arrayListOf<Double>()
    val valuesForExcelVoltage31 = arrayListOf<Double>()
    val valuesForExcelVoltage32 = arrayListOf<Double>()
    val valuesForExcelVoltage33 = arrayListOf<Double>()
    val valuesForExcelVoltage34 = arrayListOf<Double>()
    val valuesForExcelVoltage35 = arrayListOf<Double>()
    val valuesForExcelVoltage36 = arrayListOf<Double>()

    val valuesForExcelAmperage11 = arrayListOf<Double>()
    val valuesForExcelAmperage12 = arrayListOf<Double>()
    val valuesForExcelAmperage13 = arrayListOf<Double>()
    val valuesForExcelAmperage14 = arrayListOf<Double>()
    val valuesForExcelAmperage15 = arrayListOf<Double>()
    val valuesForExcelAmperage16 = arrayListOf<Double>()
    val valuesForExcelAmperage21 = arrayListOf<Double>()
    val valuesForExcelAmperage22 = arrayListOf<Double>()
    val valuesForExcelAmperage23 = arrayListOf<Double>()
    val valuesForExcelAmperage24 = arrayListOf<Double>()
    val valuesForExcelAmperage25 = arrayListOf<Double>()
    val valuesForExcelAmperage26 = arrayListOf<Double>()
    val valuesForExcelAmperage31 = arrayListOf<Double>()
    val valuesForExcelAmperage32 = arrayListOf<Double>()
    val valuesForExcelAmperage33 = arrayListOf<Double>()
    val valuesForExcelAmperage34 = arrayListOf<Double>()
    val valuesForExcelAmperage35 = arrayListOf<Double>()
    val valuesForExcelAmperage36 = arrayListOf<Double>()

    var step = 1
    if (values11.size > 1000) {
        step = (values11.size - values11.size % 1000) / 1000
    }
    for (i in values11.indices step step) {
        valuesForExcel11.add(values11[i])
        valuesForExcel12.add(values12[i])
        valuesForExcel13.add(values13[i])
        valuesForExcel14.add(values14[i])
        valuesForExcel15.add(values15[i])
        valuesForExcel16.add(values16[i])
        valuesForExcel21.add(values21[i])
        valuesForExcel22.add(values22[i])
        valuesForExcel23.add(values23[i])
        valuesForExcel24.add(values24[i])
        valuesForExcel25.add(values25[i])
        valuesForExcel26.add(values26[i])
        valuesForExcel31.add(values31[i])
        valuesForExcel32.add(values32[i])
        valuesForExcel33.add(values33[i])
        valuesForExcel34.add(values34[i])
        valuesForExcel35.add(values35[i])
        valuesForExcel36.add(values36[i])

        valuesForExcelVoltage11.add(valuesVoltage11[i])
        valuesForExcelVoltage12.add(valuesVoltage12[i])
        valuesForExcelVoltage13.add(valuesVoltage13[i])
        valuesForExcelVoltage14.add(valuesVoltage14[i])
        valuesForExcelVoltage15.add(valuesVoltage15[i])
        valuesForExcelVoltage16.add(valuesVoltage16[i])
        valuesForExcelVoltage21.add(valuesVoltage21[i])
        valuesForExcelVoltage22.add(valuesVoltage22[i])
        valuesForExcelVoltage23.add(valuesVoltage23[i])
        valuesForExcelVoltage24.add(valuesVoltage24[i])
        valuesForExcelVoltage25.add(valuesVoltage25[i])
        valuesForExcelVoltage26.add(valuesVoltage26[i])
        valuesForExcelVoltage31.add(valuesVoltage31[i])
        valuesForExcelVoltage32.add(valuesVoltage32[i])
        valuesForExcelVoltage33.add(valuesVoltage33[i])
        valuesForExcelVoltage34.add(valuesVoltage34[i])
        valuesForExcelVoltage35.add(valuesVoltage35[i])
        valuesForExcelVoltage36.add(valuesVoltage36[i])

        valuesForExcelAmperage11.add(valuesAmperage11[i])
        valuesForExcelAmperage12.add(valuesAmperage12[i])
        valuesForExcelAmperage13.add(valuesAmperage13[i])
        valuesForExcelAmperage14.add(valuesAmperage14[i])
        valuesForExcelAmperage15.add(valuesAmperage15[i])
        valuesForExcelAmperage16.add(valuesAmperage16[i])
        valuesForExcelAmperage21.add(valuesAmperage21[i])
        valuesForExcelAmperage22.add(valuesAmperage22[i])
        valuesForExcelAmperage23.add(valuesAmperage23[i])
        valuesForExcelAmperage24.add(valuesAmperage24[i])
        valuesForExcelAmperage25.add(valuesAmperage25[i])
        valuesForExcelAmperage26.add(valuesAmperage26[i])
        valuesForExcelAmperage31.add(valuesAmperage31[i])
        valuesForExcelAmperage32.add(valuesAmperage32[i])
        valuesForExcelAmperage33.add(valuesAmperage33[i])
        valuesForExcelAmperage34.add(valuesAmperage34[i])
        valuesForExcelAmperage35.add(valuesAmperage35[i])
        valuesForExcelAmperage36.add(valuesAmperage36[i])
    }

    val sheet = wb.getSheetAt(0)
    var row: Row
    val cellStyle: XSSFCellStyle = generateStyles(wb) as XSSFCellStyle
    var rowNum = rawNumber
    row = sheet.createRow(rowNum)
    var dot = 0
    for (i in valuesForExcel11.indices) {
        fillOneCell(row, columnNumber, cellStyle, dot / 60)
        fillOneCell(row, columnNumber + 1, cellStyle, valuesForExcel11[i])
        fillOneCell(row, columnNumber + 2, cellStyle, valuesForExcel12[i])
        fillOneCell(row, columnNumber + 3, cellStyle, valuesForExcel13[i])
        fillOneCell(row, columnNumber + 4, cellStyle, valuesForExcel14[i])
        fillOneCell(row, columnNumber + 5, cellStyle, valuesForExcel15[i])
        fillOneCell(row, columnNumber + 6, cellStyle, valuesForExcel16[i])
        fillOneCell(row, columnNumber + 7, cellStyle, valuesForExcel21[i])
        fillOneCell(row, columnNumber + 8, cellStyle, valuesForExcel22[i])
        fillOneCell(row, columnNumber + 9, cellStyle, valuesForExcel23[i])
        fillOneCell(row, columnNumber + 10, cellStyle, valuesForExcel24[i])
        fillOneCell(row, columnNumber + 11, cellStyle, valuesForExcel25[i])
        fillOneCell(row, columnNumber + 12, cellStyle, valuesForExcel26[i])
        fillOneCell(row, columnNumber + 13, cellStyle, valuesForExcel31[i])
        fillOneCell(row, columnNumber + 14, cellStyle, valuesForExcel32[i])
        fillOneCell(row, columnNumber + 15, cellStyle, valuesForExcel33[i])
        fillOneCell(row, columnNumber + 16, cellStyle, valuesForExcel34[i])
        fillOneCell(row, columnNumber + 17, cellStyle, valuesForExcel35[i])
        fillOneCell(row, columnNumber + 18, cellStyle, valuesForExcel36[i])

        fillOneCell(row, columnNumber + 19, cellStyle, valuesForExcelVoltage11[i])
        fillOneCell(row, columnNumber + 20, cellStyle, valuesForExcelVoltage12[i])
        fillOneCell(row, columnNumber + 21, cellStyle, valuesForExcelVoltage13[i])
        fillOneCell(row, columnNumber + 22, cellStyle, valuesForExcelVoltage14[i])
        fillOneCell(row, columnNumber + 23, cellStyle, valuesForExcelVoltage15[i])
        fillOneCell(row, columnNumber + 24, cellStyle, valuesForExcelVoltage16[i])
        fillOneCell(row, columnNumber + 25, cellStyle, valuesForExcelVoltage21[i])
        fillOneCell(row, columnNumber + 26, cellStyle, valuesForExcelVoltage22[i])
        fillOneCell(row, columnNumber + 27, cellStyle, valuesForExcelVoltage23[i])
        fillOneCell(row, columnNumber + 28, cellStyle, valuesForExcelVoltage24[i])
        fillOneCell(row, columnNumber + 29, cellStyle, valuesForExcelVoltage25[i])
        fillOneCell(row, columnNumber + 30, cellStyle, valuesForExcelVoltage26[i])
        fillOneCell(row, columnNumber + 31, cellStyle, valuesForExcelVoltage31[i])
        fillOneCell(row, columnNumber + 32, cellStyle, valuesForExcelVoltage32[i])
        fillOneCell(row, columnNumber + 33, cellStyle, valuesForExcelVoltage33[i])
        fillOneCell(row, columnNumber + 34, cellStyle, valuesForExcelVoltage34[i])
        fillOneCell(row, columnNumber + 35, cellStyle, valuesForExcelVoltage35[i])
        fillOneCell(row, columnNumber + 36, cellStyle, valuesForExcelVoltage36[i])

        fillOneCell(row, columnNumber + 37, cellStyle, valuesForExcelAmperage11[i])
        fillOneCell(row, columnNumber + 38, cellStyle, valuesForExcelAmperage12[i])
        fillOneCell(row, columnNumber + 39, cellStyle, valuesForExcelAmperage13[i])
        fillOneCell(row, columnNumber + 40, cellStyle, valuesForExcelAmperage14[i])
        fillOneCell(row, columnNumber + 41, cellStyle, valuesForExcelAmperage15[i])
        fillOneCell(row, columnNumber + 42, cellStyle, valuesForExcelAmperage16[i])
        fillOneCell(row, columnNumber + 43, cellStyle, valuesForExcelAmperage21[i])
        fillOneCell(row, columnNumber + 44, cellStyle, valuesForExcelAmperage22[i])
        fillOneCell(row, columnNumber + 45, cellStyle, valuesForExcelAmperage23[i])
        fillOneCell(row, columnNumber + 46, cellStyle, valuesForExcelAmperage24[i])
        fillOneCell(row, columnNumber + 47, cellStyle, valuesForExcelAmperage25[i])
        fillOneCell(row, columnNumber + 48, cellStyle, valuesForExcelAmperage26[i])
        fillOneCell(row, columnNumber + 49, cellStyle, valuesForExcelAmperage31[i])
        fillOneCell(row, columnNumber + 50, cellStyle, valuesForExcelAmperage32[i])
        fillOneCell(row, columnNumber + 51, cellStyle, valuesForExcelAmperage33[i])
        fillOneCell(row, columnNumber + 52, cellStyle, valuesForExcelAmperage34[i])
        fillOneCell(row, columnNumber + 53, cellStyle, valuesForExcelAmperage35[i])
        fillOneCell(row, columnNumber + 54, cellStyle, valuesForExcelAmperage36[i])
        row = sheet.createRow(++rowNum)
        dot += step
    }
}

fun fillParameters4(
    wb: XSSFWorkbook,
    dots11: String,
    dots12: String,
    dots13: String,
    dots14: String,
    columnNumber: Int, rawNumber: Int
) {
    val values11 = dots11.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values12 = dots12.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values13 = dots13.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values14 = dots14.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)

    val valuesForExcel11 = arrayListOf<Double>()
    val valuesForExcel12 = arrayListOf<Double>()
    val valuesForExcel13 = arrayListOf<Double>()
    val valuesForExcel14 = arrayListOf<Double>()

    var step = 1
    if (values11.size > 200) {
        step = (values11.size - values11.size % 200) / 200
    }
    for (i in values11.indices step step) {
        valuesForExcel11.add(values11[i])
        valuesForExcel12.add(values12[i])
        valuesForExcel13.add(values13[i])
        valuesForExcel14.add(values14[i])
    }
    val sheet = wb.getSheetAt(0)
    var row: Row
    val cellStyle: XSSFCellStyle = generateStyles(wb) as XSSFCellStyle
    var rowNum = rawNumber
    row = sheet.createRow(rowNum)
    var dot = 0
    for (i in valuesForExcel11.indices) {
        fillOneCell(row, columnNumber, cellStyle, dot)
        fillOneCell(row, columnNumber + 1, cellStyle, valuesForExcel11[i])
        fillOneCell(row, columnNumber + 2, cellStyle, valuesForExcel12[i])
        fillOneCell(row, columnNumber + 3, cellStyle, valuesForExcel13[i])
        fillOneCell(row, columnNumber + 4, cellStyle, valuesForExcel14[i])
        row = sheet.createRow(++rowNum)
        dot += step
    }
}

fun fillParameters6(
    wb: XSSFWorkbook,
    dots11: String,
    dots12: String,
    dots13: String,
    dots14: String,
    dots15: String,
    dots16: String,
    voltage11: String,
    voltage12: String,
    voltage13: String,
    voltage14: String,
    voltage15: String,
    voltage16: String,
    amperage11: String,
    amperage12: String,
    amperage13: String,
    amperage14: String,
    amperage15: String,
    amperage16: String,
    columnNumber: Int, rawNumber: Int
) {
    val values11 = dots11.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values12 = dots12.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values13 = dots13.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values14 = dots14.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values15 = dots15.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values16 = dots16.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)

    val valuesVoltage11 = voltage11.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage12 = voltage12.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage13 = voltage13.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage14 = voltage14.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage15 = voltage15.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesVoltage16 = voltage16.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)

    val valuesAmperage11 = amperage11.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage12 = amperage12.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage13 = amperage13.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage14 = amperage14.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage15 = amperage15.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)
    val valuesAmperage16 = amperage16.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map { it.replace("null", "0.0") }.map(String::toDouble)

    val valuesForExcel11 = arrayListOf<Double>()
    val valuesForExcel12 = arrayListOf<Double>()
    val valuesForExcel13 = arrayListOf<Double>()
    val valuesForExcel14 = arrayListOf<Double>()
    val valuesForExcel15 = arrayListOf<Double>()
    val valuesForExcel16 = arrayListOf<Double>()

    val valuesForExcelVoltage11 = arrayListOf<Double>()
    val valuesForExcelVoltage12 = arrayListOf<Double>()
    val valuesForExcelVoltage13 = arrayListOf<Double>()
    val valuesForExcelVoltage14 = arrayListOf<Double>()
    val valuesForExcelVoltage15 = arrayListOf<Double>()
    val valuesForExcelVoltage16 = arrayListOf<Double>()

    val valuesForExcelAmperage11 = arrayListOf<Double>()
    val valuesForExcelAmperage12 = arrayListOf<Double>()
    val valuesForExcelAmperage13 = arrayListOf<Double>()
    val valuesForExcelAmperage14 = arrayListOf<Double>()
    val valuesForExcelAmperage15 = arrayListOf<Double>()
    val valuesForExcelAmperage16 = arrayListOf<Double>()

    var step = 1
    if (values11.size > 1000) {
        step = (values11.size - values11.size % 1000) / 1000
    }

    for (i in values11.indices step step) {
        valuesForExcel11.add(values11[i])
        valuesForExcel12.add(values12[i])
        valuesForExcel13.add(values13[i])
        valuesForExcel14.add(values14[i])
        valuesForExcel15.add(values15[i])
        valuesForExcel16.add(values16[i])

        valuesForExcelVoltage11.add(valuesVoltage11[i])
        valuesForExcelVoltage12.add(valuesVoltage12[i])
        valuesForExcelVoltage13.add(valuesVoltage13[i])
        valuesForExcelVoltage14.add(valuesVoltage14[i])
        valuesForExcelVoltage15.add(valuesVoltage15[i])
        valuesForExcelVoltage16.add(valuesVoltage16[i])

        valuesForExcelAmperage11.add(valuesAmperage11[i])
        valuesForExcelAmperage12.add(valuesAmperage12[i])
        valuesForExcelAmperage13.add(valuesAmperage13[i])
        valuesForExcelAmperage14.add(valuesAmperage14[i])
        valuesForExcelAmperage15.add(valuesAmperage15[i])
        valuesForExcelAmperage16.add(valuesAmperage16[i])
    }
    val sheet = wb.getSheetAt(0)
    var row: Row
    val cellStyle: XSSFCellStyle = generateStyles(wb) as XSSFCellStyle
    var rowNum = rawNumber
    row = sheet.createRow(rowNum)
    var dot = 0
    for (i in valuesForExcel11.indices) {
        fillOneCell(row, columnNumber, cellStyle, dot / 60)
        fillOneCell(row, columnNumber + 1, cellStyle, valuesForExcel11[i])
        fillOneCell(row, columnNumber + 2, cellStyle, valuesForExcel12[i])
        fillOneCell(row, columnNumber + 3, cellStyle, valuesForExcel13[i])
        fillOneCell(row, columnNumber + 4, cellStyle, valuesForExcel14[i])
        fillOneCell(row, columnNumber + 5, cellStyle, valuesForExcel15[i])
        fillOneCell(row, columnNumber + 6, cellStyle, valuesForExcel16[i])
        fillOneCell(row, columnNumber + 7, cellStyle, valuesForExcelVoltage11[i])
        fillOneCell(row, columnNumber + 8, cellStyle, valuesForExcelVoltage12[i])
        fillOneCell(row, columnNumber + 9, cellStyle, valuesForExcelVoltage13[i])
        fillOneCell(row, columnNumber + 10, cellStyle, valuesForExcelVoltage14[i])
        fillOneCell(row, columnNumber + 11, cellStyle, valuesForExcelVoltage15[i])
        fillOneCell(row, columnNumber + 12, cellStyle, valuesForExcelVoltage16[i])
        fillOneCell(row, columnNumber + 13, cellStyle, valuesForExcelAmperage11[i])
        fillOneCell(row, columnNumber + 14, cellStyle, valuesForExcelAmperage12[i])
        fillOneCell(row, columnNumber + 15, cellStyle, valuesForExcelAmperage13[i])
        fillOneCell(row, columnNumber + 16, cellStyle, valuesForExcelAmperage14[i])
        fillOneCell(row, columnNumber + 17, cellStyle, valuesForExcelAmperage15[i])
        fillOneCell(row, columnNumber + 18, cellStyle, valuesForExcelAmperage16[i])
        row = sheet.createRow(++rowNum)
        dot += step
    }

}

private fun fillOneCell(row: Row, columnNum: Int, cellStyle: XSSFCellStyle, points: Double): Int {
    val cell: Cell = row.createCell(columnNum)
    cell.cellStyle = cellStyle
    cell.setCellValue(points)
    return columnNum + 1
}

private fun fillOneCell(row: Row, columnNum: Int, cellStyle: XSSFCellStyle, points: Int): Int {
    val cell: Cell = row.createCell(columnNum)
    cell.cellStyle = cellStyle
    cell.setCellValue(points.toString())
    return columnNum + 1
}

private fun generateStyles(wb: XSSFWorkbook): CellStyle {
    val headStyle: CellStyle = wb.createCellStyle()
    headStyle.wrapText = true
    headStyle.borderBottom = BorderStyle.THIN
    headStyle.borderTop = BorderStyle.THIN
    headStyle.borderLeft = BorderStyle.THIN
    headStyle.borderRight = BorderStyle.THIN
    headStyle.alignment = HorizontalAlignment.CENTER
    headStyle.verticalAlignment = VerticalAlignment.CENTER
    val font = wb.createFont()
    font.fontHeightInPoints = 14.toShort()
    headStyle.setFont(font)
    return headStyle
}

private fun drawLineChart36(workbook: XSSFWorkbook, protocol: Protocol) {
    val sheet = workbook.getSheet("Sheet1")
    val sheet2 = workbook.getSheet("Sheet2")
    val lastRowIndex = sheet.lastRowNum - 4

    var i = 0
    val timeData = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, i, i))
    val valueData11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData21 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData22 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData23 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData24 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData25 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData26 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData31 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData32 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData33 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData34 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData35 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData36 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))

    val valueDataVoltage11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage21 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage22 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage23 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage24 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage25 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage26 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage31 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage32 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage33 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage34 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage35 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage36 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))

    val valueDataAmperage11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage21 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage22 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage23 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage24 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage25 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage26 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage31 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage32 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage33 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage34 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage35 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage36 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))

    var lastRowForGraph = 1
    val graphHeight = 41
    val graphSpace = graphHeight + 9
    val lineChart11 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart11, timeData, valueData11, valueDataVoltage11, valueDataAmperage11,
        "Время, мин.    1 лоп 1 секция, " +
                "Начало: ${protocol.unixTimeStartProtocol11}, " +
                "Режим: ${protocol.unixTimeWorkProtocol11}, " +
                "Конец: ${protocol.unixTimeEndProtocol11}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart12 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart12,
        timeData,
        valueData12,
        valueDataVoltage12,
        valueDataAmperage12,
        "Время, мин.    1 лопасть 2 секция " +
                "Начало: ${protocol.unixTimeStartProtocol12}, " +
                "Режим: ${protocol.unixTimeWorkProtocol12}, " +
                "Конец: ${protocol.unixTimeEndProtocol12}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart13 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart13,
        timeData,
        valueData13,
        valueDataVoltage13,
        valueDataAmperage13,
        "Время, мин.    1 лопасть 3 секция " +
                "Начало: ${protocol.unixTimeStartProtocol13}, " +
                "Режим: ${protocol.unixTimeWorkProtocol13}, " +
                "Конец: ${protocol.unixTimeEndProtocol13}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart14 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart14, timeData, valueData14,
        valueDataVoltage14,
        valueDataAmperage14,
        "Время, мин.    1 лопасть 4 секция " +
                "Начало: ${protocol.unixTimeStartProtocol14}, " +
                "Режим: ${protocol.unixTimeWorkProtocol14}, " +
                "Конец: ${protocol.unixTimeEndProtocol14}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart15 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart15, timeData, valueData15,
        valueDataVoltage15,
        valueDataAmperage15, "Время, мин.    1 лопасть 5 секция " +
                "Начало: ${protocol.unixTimeStartProtocol15}, " +
                "Режим: ${protocol.unixTimeWorkProtocol15}, " +
                "Конец: ${protocol.unixTimeEndProtocol15}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart16 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart16, timeData, valueData16,
        valueDataVoltage16,
        valueDataAmperage16, "Время, мин.    1 лопасть 6 секция " +
                "Начало: ${protocol.unixTimeStartProtocol16}, " +
                "Режим: ${protocol.unixTimeWorkProtocol16}, " +
                "Конец: ${protocol.unixTimeEndProtocol16}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart21 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart21, timeData, valueData21,
        valueDataVoltage21,
        valueDataAmperage21, "Время, мин.    2 лопасть 1 секция " +
                "Начало: ${protocol.unixTimeStartProtocol21}, " +
                "Режим: ${protocol.unixTimeWorkProtocol21}, " +
                "Конец: ${protocol.unixTimeEndProtocol21}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart22 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart22, timeData, valueData22,
        valueDataVoltage22,
        valueDataAmperage22, "Время, мин.    2 лопасть 2 секция " +
                "Начало: ${protocol.unixTimeStartProtocol22}, " +
                "Режим: ${protocol.unixTimeWorkProtocol22}, " +
                "Конец: ${protocol.unixTimeEndProtocol22}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart23 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart23, timeData, valueData23,
        valueDataVoltage23,
        valueDataAmperage23, "Время, мин.    2 лопасть 3 секция " +
                "Начало: ${protocol.unixTimeStartProtocol23}, " +
                "Режим: ${protocol.unixTimeWorkProtocol23}, " +
                "Конец: ${protocol.unixTimeEndProtocol23}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart24 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart24, timeData, valueData24,
        valueDataVoltage24,
        valueDataAmperage24, "Время, мин.    2 лопасть 4 секция " +
                "Начало: ${protocol.unixTimeStartProtocol24}, " +
                "Режим: ${protocol.unixTimeWorkProtocol24}, " +
                "Конец: ${protocol.unixTimeEndProtocol24}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart25 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart25, timeData, valueData25,
        valueDataVoltage25,
        valueDataAmperage25, "Время, мин.    2 лопасть 5 секция " +
                "Начало: ${protocol.unixTimeStartProtocol25}, " +
                "Режим: ${protocol.unixTimeWorkProtocol25}, " +
                "Конец: ${protocol.unixTimeEndProtocol25}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart26 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart26, timeData, valueData26,
        valueDataVoltage26,
        valueDataAmperage26, "Время, мин.    2 лопасть 6 секция " +
                "Начало: ${protocol.unixTimeStartProtocol26}, " +
                "Режим: ${protocol.unixTimeWorkProtocol26}, " +
                "Конец: ${protocol.unixTimeEndProtocol26}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart31 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart31, timeData, valueData31,
        valueDataVoltage31,
        valueDataAmperage31, "Время, мин.    3 лопасть 1 секция " +
                "Начало: ${protocol.unixTimeStartProtocol31}, " +
                "Режим: ${protocol.unixTimeWorkProtocol31}, " +
                "Конец: ${protocol.unixTimeEndProtocol31}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart32 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart32, timeData, valueData32,
        valueDataVoltage32,
        valueDataAmperage32, "Время, мин.    3 лопасть 2 секция " +
                "Начало: ${protocol.unixTimeStartProtocol32}, " +
                "Режим: ${protocol.unixTimeWorkProtocol32}, " +
                "Конец: ${protocol.unixTimeEndProtocol32}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart33 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart33, timeData, valueData33,
        valueDataVoltage33,
        valueDataAmperage33, "Время, мин.    3 лопасть 3 секция " +
                "Начало: ${protocol.unixTimeStartProtocol33}, " +
                "Режим: ${protocol.unixTimeWorkProtocol33}, " +
                "Конец: ${protocol.unixTimeEndProtocol33}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart34 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart34, timeData, valueData34,
        valueDataVoltage34,
        valueDataAmperage34, "Время, мин.    3 лопасть 4 секция " +
                "Начало: ${protocol.unixTimeStartProtocol34}, " +
                "Режим: ${protocol.unixTimeWorkProtocol34}, " +
                "Конец: ${protocol.unixTimeEndProtocol34}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart35 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart35, timeData, valueData35,
        valueDataVoltage35,
        valueDataAmperage35, "Время, мин.    3 лопасть 5 секция " +
                "Начало: ${protocol.unixTimeStartProtocol35}, " +
                "Режим: ${protocol.unixTimeWorkProtocol35}, " +
                "Конец: ${protocol.unixTimeEndProtocol35}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart36 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart36, timeData, valueData36,
        valueDataVoltage36,
        valueDataAmperage36, "Время, мин.    3 лопасть 6 секция " +
                "Начало: ${protocol.unixTimeStartProtocol36}, " +
                "Режим: ${protocol.unixTimeWorkProtocol36}, " +
                "Конец: ${protocol.unixTimeEndProtocol36}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
}

private fun drawLineChart4(workbook: XSSFWorkbook) {
    val sheet = workbook.getSheet("Sheet1")
    val sheet2 = workbook.getSheet("Sheet2")
    val lastRowIndex = sheet.lastRowNum - 1

    var i = 0
    val timeData = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, i, i))
    val valueData11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))


    var lastRowForGraph = 1
    val graphHeight = 41
    val graphSpace = graphHeight + 9
    val lineChart11 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(lineChart11, timeData, valueData11, valueData11, valueData11, "Время, сек", "U, В")
    lastRowForGraph += graphSpace
    val lineChart12 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(lineChart12, timeData, valueData12, valueData12, valueData12, "Время, сек", "I1, мА")
    lastRowForGraph += graphSpace
    val lineChart13 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(lineChart13, timeData, valueData13, valueData13, valueData13, "Время, сек", "I2, мА")
    lastRowForGraph += graphSpace
    val lineChart14 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(lineChart14, timeData, valueData14, valueData14, valueData14, "Время, сек", "I3, мА")
}


private fun drawLineChart6(workbook: XSSFWorkbook, protocol: ProtocolRotorBlade) {
    val sheet = workbook.getSheet("Sheet1")
    val sheet2 = workbook.getSheet("Sheet2")
    val lastRowIndex = sheet.lastRowNum - 1
    var i = 0

    val timeData = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, i, i))
    val valueData11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueData16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataVoltage16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    val valueDataAmperage16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, ++i, i))
    var lastRowForGraph = 1
    val graphHeight = 41
    val graphSpace = graphHeight + 9
    val lineChart11 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart11, timeData, valueData11, valueDataVoltage11, valueDataAmperage11,
        "Время, мин.    1 секция, " +
                "Начало: ${protocol.unixTimeStartProtocol1}, " +
                "Режим: ${protocol.unixTimeWorkProtocol1}, " +
                "Конец: ${protocol.unixTimeEndProtocol1}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart12 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart12, timeData, valueData12, valueDataVoltage12, valueDataAmperage12, "Время, мин.   2 секция " +
                "Начало: ${protocol.unixTimeStartProtocol2}, " +
                "Режим: ${protocol.unixTimeWorkProtocol2}, " +
                "Конец: ${protocol.unixTimeEndProtocol2}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart13 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart13, timeData, valueData13, valueDataVoltage13, valueDataAmperage13, "Время, мин.   3 секция " +
                "Начало: ${protocol.unixTimeStartProtocol3}, " +
                "Режим: ${protocol.unixTimeWorkProtocol3}, " +
                "Конец: ${protocol.unixTimeEndProtocol3}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart14 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart14, timeData, valueData14, valueDataVoltage14, valueDataAmperage14, "Время, мин.   4 секция " +
                "Начало: ${protocol.unixTimeStartProtocol4}, " +
                "Режим: ${protocol.unixTimeWorkProtocol4}, " +
                "Конец: ${protocol.unixTimeEndProtocol4}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart15 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart15, timeData, valueData15, valueDataVoltage15, valueDataAmperage15, "Время, мин.   5 секция " +
                "Начало: ${protocol.unixTimeStartProtocol5}, " +
                "Режим: ${protocol.unixTimeWorkProtocol5}, " +
                "Конец: ${protocol.unixTimeEndProtocol5}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
    lastRowForGraph += graphSpace
    val lineChart16 = createLineChart(sheet2, lastRowForGraph, lastRowForGraph + graphHeight)
    drawLineChart36(
        lineChart16, timeData, valueData16, valueDataVoltage16, valueDataAmperage16, "Время, мин.   6 секция " +
                "Начало: ${protocol.unixTimeStartProtocol6}, " +
                "Режим: ${protocol.unixTimeWorkProtocol6}, " +
                "Конец: ${protocol.unixTimeEndProtocol6}",
        "Ток, А                                              Температура, °C                                   Напряжение, В"
    )
}

private fun createLineChart(sheet: XSSFSheet, rowStart: Int, rowEnd: Int, col1: Int = 1, col2: Int = 19): XSSFChart {
    val drawing = sheet.createDrawingPatriarch()
    val anchor = drawing.createAnchor(0, 0, 0, 0, col1, rowStart, col2, rowEnd)

    return drawing.createChart(anchor)
}

private fun createLineChartInsulation(sheet: XSSFSheet, rowStart: Int, rowEnd: Int): XSSFChart {
    val drawing = sheet.createDrawingPatriarch()
    val anchor = drawing.createAnchor(0, 0, 0, 0, 6, rowStart, 19, rowEnd)

    return drawing.createChart(anchor)
}

private fun createLineChart(sheet: XSSFSheet): XSSFChart {
    val drawing = sheet.createDrawingPatriarch()
    val anchor = drawing.createAnchor(0, 0, 0, 0, 3, 16, 36, 26)

    return drawing.createChart(anchor)
}

private fun drawLineChart36(
    lineChart: XSSFChart,
    xAxisData: ChartDataSource<Number>,
    yAxisData1: ChartDataSource<Number>,
    yAxisData2: ChartDataSource<Number>,
    yAxisData3: ChartDataSource<Number>,
    nameOfOI: String,
    yAxisTitle: String = "T, °C"
) {
    val data = lineChart.chartDataFactory.createLineChartData()

    val xAxis = lineChart.chartAxisFactory.createCategoryAxis(AxisPosition.BOTTOM)
    val yAxis = lineChart.createValueAxis(AxisPosition.LEFT)
    yAxis.crosses = org.apache.poi.ss.usermodel.charts.AxisCrosses.AUTO_ZERO

    val series = data.addSeries(xAxisData, yAxisData1)
    if (yAxisData1 != yAxisData2) {
        data.addSeries(xAxisData, yAxisData2)
        data.addSeries(xAxisData, yAxisData3)
    }
    series.setTitle("График")
    lineChart.plot(data, xAxis, yAxis)

    lineChart.axes[0].setTitle(nameOfOI)
    lineChart.axes[1].setTitle(yAxisTitle)
    lineChart.axes[1].majorUnit = 2.0

    val plotArea = lineChart.ctChart.plotArea
    plotArea.lineChartArray[0].smooth
    val ctBool = CTBoolean.Factory.newInstance()
    ctBool.`val` = false
    plotArea.lineChartArray[0].smooth = ctBool
    for (series in plotArea.lineChartArray[0].serArray) {
        series.smooth = ctBool
    }
}

fun fillParameters(wb: XSSFWorkbook, dots: String, start: Int, end: Int) {
    var values = dots.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesForExcel = arrayListOf<Double>()
    for (i in values.indices) {
        valuesForExcel.add(values[i])
    }
    val sheet = wb.getSheetAt(0)
    var row: Row
    var cellStyle: XSSFCellStyle = generateStyles(wb) as XSSFCellStyle
    var rowNum = sheet.lastRowNum + 1
    row = sheet.createRow(rowNum)
    var columnNum = 0
    for (i in values.indices) {
        columnNum = fillOneCell(row, columnNum, cellStyle, i + start)
        columnNum = fillOneCell(row, columnNum, cellStyle, values[i])
        row = sheet.createRow(++rowNum)
        columnNum = 0
    }
}

private fun drawLineChart(workbook: XSSFWorkbook, section: String) {
    val sheet = workbook.getSheet("Sheet1")
    val lastRowIndex = sheet.lastRowNum - 1
    val timeData = DataSources.fromNumericCellRange(sheet, CellRangeAddress(16, lastRowIndex, 0, 0))
    val valueData = DataSources.fromNumericCellRange(sheet, CellRangeAddress(16, lastRowIndex, 1, 1))

    var lineChart = createLineChart(sheet)
    drawLineChart(lineChart, timeData, valueData, section, "T, °C")
}

private fun drawLineChart(
    lineChart: XSSFChart,
    xAxisData: ChartDataSource<Number>,
    yAxisData: ChartDataSource<Number>,
    section: String,
    yAxisTitle: String
) {
    val data = lineChart.chartDataFactory.createLineChartData()

    val xAxis = lineChart.chartAxisFactory.createCategoryAxis(AxisPosition.BOTTOM)
    val yAxis = lineChart.createValueAxis(AxisPosition.LEFT)
    yAxis.crosses = org.apache.poi.ss.usermodel.charts.AxisCrosses.AUTO_ZERO

    val series = data.addSeries(xAxisData, yAxisData)
    series.setTitle("График")
    lineChart.plot(data, xAxis, yAxis)
    lineChart.axes[0].setTitle(section)
    lineChart.axes[1].setTitle(yAxisTitle)

    val plotArea = lineChart.ctChart.plotArea
    plotArea.lineChartArray[0].smooth
    val ctBool = CTBoolean.Factory.newInstance()
    ctBool.`val` = false
    plotArea.lineChartArray[0].smooth = ctBool
    for (series in plotArea.lineChartArray[0].serArray) {
        series.smooth = ctBool
    }
}