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
                                "#TIME#" -> cell.setCellValue(protocol.time)

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
            drawLineChart36(wb)
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: FileNotFoundException) {
        Toast.makeText("Не удалось сохранить протокол на диск")
    }
}

fun saveProtocolAsWorkbook(protocol: ProtocolInsulation, path: String = "protocol.xlsx") {
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
                                "#TIME#" -> cell.setCellValue(protocol.time)

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
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: FileNotFoundException) {
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
            drawLineChart(wb)
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: FileNotFoundException) {
        Toast.makeText("Не удалось сохранить протокол на диск")
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
        columnNum = fillOneCell(row, columnNum, cellStyle, i  + start)
        columnNum = fillOneCell(row, columnNum, cellStyle, values[i])
        row = sheet.createRow(++rowNum)
        columnNum = 0
    }
}

private fun drawLineChart(workbook: XSSFWorkbook) {
    val sheet = workbook.getSheet("Sheet1")
    val lastRowIndex = sheet.lastRowNum - 1
    val timeData = DataSources.fromNumericCellRange(sheet, CellRangeAddress(16, lastRowIndex, 0, 0))
    val valueData = DataSources.fromNumericCellRange(sheet, CellRangeAddress(16, lastRowIndex, 1, 1))

    var lineChart = createLineChart(sheet)
    drawLineChart(lineChart, timeData, valueData)
}

private fun drawLineChart(
    lineChart: XSSFChart,
    xAxisData: ChartDataSource<Number>,
    yAxisData: ChartDataSource<Number>
) {
    val data = lineChart.chartDataFactory.createLineChartData()

    val xAxis = lineChart.chartAxisFactory.createCategoryAxis(AxisPosition.BOTTOM)
    val yAxis = lineChart.createValueAxis(AxisPosition.LEFT)
    yAxis.crosses = org.apache.poi.ss.usermodel.charts.AxisCrosses.AUTO_ZERO

    val series = data.addSeries(xAxisData, yAxisData)
    series.setTitle("График")
    lineChart.plot(data, xAxis, yAxis)

    val plotArea = lineChart.ctChart.plotArea
    plotArea.lineChartArray[0].smooth
    val ctBool = CTBoolean.Factory.newInstance()
    ctBool.`val` = false
    plotArea.lineChartArray[0].smooth = ctBool
    for (series in plotArea.lineChartArray[0].serArray) {
        series.smooth = ctBool
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
    val values21 = dots21.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values22 = dots22.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values23 = dots23.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values24 = dots24.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values25 = dots25.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values26 = dots26.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values31 = dots31.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values32 = dots32.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values33 = dots33.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values34 = dots34.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values35 = dots35.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val values36 = dots36.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)

    val valuesAmperage11 = amperage11.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage12 = amperage12.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage13 = amperage13.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage14 = amperage14.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage15 = amperage15.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage16 = amperage16.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage21 = amperage21.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage22 = amperage22.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage23 = amperage23.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage24 = amperage24.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage25 = amperage25.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage26 = amperage26.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage31 = amperage31.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage32 = amperage32.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage33 = amperage33.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage34 = amperage34.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage35 = amperage35.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    val valuesAmperage36 = amperage36.removePrefix("[").removePrefix("'").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)

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

    val valuesAmperageForExcel11 = arrayListOf<Double>()
    val valuesAmperageForExcel12 = arrayListOf<Double>()
    val valuesAmperageForExcel13 = arrayListOf<Double>()
    val valuesAmperageForExcel14 = arrayListOf<Double>()
    val valuesAmperageForExcel15 = arrayListOf<Double>()
    val valuesAmperageForExcel16 = arrayListOf<Double>()
    val valuesAmperageForExcel21 = arrayListOf<Double>()
    val valuesAmperageForExcel22 = arrayListOf<Double>()
    val valuesAmperageForExcel23 = arrayListOf<Double>()
    val valuesAmperageForExcel24 = arrayListOf<Double>()
    val valuesAmperageForExcel25 = arrayListOf<Double>()
    val valuesAmperageForExcel26 = arrayListOf<Double>()
    val valuesAmperageForExcel31 = arrayListOf<Double>()
    val valuesAmperageForExcel32 = arrayListOf<Double>()
    val valuesAmperageForExcel33 = arrayListOf<Double>()
    val valuesAmperageForExcel34 = arrayListOf<Double>()
    val valuesAmperageForExcel35 = arrayListOf<Double>()
    val valuesAmperageForExcel36 = arrayListOf<Double>()
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

        valuesAmperageForExcel11.add(valuesAmperage11[i])
        valuesAmperageForExcel12.add(valuesAmperage12[i])
        valuesAmperageForExcel13.add(valuesAmperage13[i])
        valuesAmperageForExcel14.add(valuesAmperage14[i])
        valuesAmperageForExcel15.add(valuesAmperage15[i])
        valuesAmperageForExcel16.add(valuesAmperage16[i])
        valuesAmperageForExcel21.add(valuesAmperage21[i])
        valuesAmperageForExcel22.add(valuesAmperage22[i])
        valuesAmperageForExcel23.add(valuesAmperage23[i])
        valuesAmperageForExcel24.add(valuesAmperage24[i])
        valuesAmperageForExcel25.add(valuesAmperage25[i])
        valuesAmperageForExcel26.add(valuesAmperage26[i])
        valuesAmperageForExcel31.add(valuesAmperage31[i])
        valuesAmperageForExcel32.add(valuesAmperage32[i])
        valuesAmperageForExcel33.add(valuesAmperage33[i])
        valuesAmperageForExcel34.add(valuesAmperage34[i])
        valuesAmperageForExcel35.add(valuesAmperage35[i])
        valuesAmperageForExcel36.add(valuesAmperage36[i])
    }
    val sheet = wb.getSheetAt(0)
    var row: Row
    val cellStyle: XSSFCellStyle = generateStyles(wb) as XSSFCellStyle
    var rowNum = rawNumber
    row = sheet.createRow(rowNum)
    for (i in valuesForExcel11.indices) {
        fillOneCell(row, columnNumber, cellStyle, i)
        fillOneCell(row, columnNumber + 1, cellStyle, valuesForExcel11[i])
        fillOneCell(row, columnNumber + 2, cellStyle, i)
        fillOneCell(row, columnNumber + 3, cellStyle, valuesForExcel12[i])
        fillOneCell(row, columnNumber + 4, cellStyle, i)
        fillOneCell(row, columnNumber + 5, cellStyle, valuesForExcel13[i])
        fillOneCell(row, columnNumber + 6, cellStyle, i)
        fillOneCell(row, columnNumber + 7, cellStyle, valuesForExcel14[i])
        fillOneCell(row, columnNumber + 8, cellStyle, i)
        fillOneCell(row, columnNumber + 9, cellStyle, valuesForExcel15[i])
        fillOneCell(row, columnNumber + 10, cellStyle, i)
        fillOneCell(row, columnNumber + 11, cellStyle, valuesForExcel16[i])
        fillOneCell(row, columnNumber + 12, cellStyle, i)
        fillOneCell(row, columnNumber + 13, cellStyle, valuesForExcel21[i])
        fillOneCell(row, columnNumber + 14, cellStyle, i)
        fillOneCell(row, columnNumber + 15, cellStyle, valuesForExcel22[i])
        fillOneCell(row, columnNumber + 16, cellStyle, i)
        fillOneCell(row, columnNumber + 17, cellStyle, valuesForExcel23[i])
        fillOneCell(row, columnNumber + 18, cellStyle, i)
        fillOneCell(row, columnNumber + 19, cellStyle, valuesForExcel24[i])
        fillOneCell(row, columnNumber + 20, cellStyle, i)
        fillOneCell(row, columnNumber + 21, cellStyle, valuesForExcel25[i])
        fillOneCell(row, columnNumber + 22, cellStyle, i)
        fillOneCell(row, columnNumber + 23, cellStyle, valuesForExcel26[i])
        fillOneCell(row, columnNumber + 24, cellStyle, i)
        fillOneCell(row, columnNumber + 25, cellStyle, valuesForExcel31[i])
        fillOneCell(row, columnNumber + 26, cellStyle, i)
        fillOneCell(row, columnNumber + 27, cellStyle, valuesForExcel32[i])
        fillOneCell(row, columnNumber + 28, cellStyle, i)
        fillOneCell(row, columnNumber + 29, cellStyle, valuesForExcel33[i])
        fillOneCell(row, columnNumber + 30, cellStyle, i)
        fillOneCell(row, columnNumber + 31, cellStyle, valuesForExcel34[i])
        fillOneCell(row, columnNumber + 32, cellStyle, i)
        fillOneCell(row, columnNumber + 33, cellStyle, valuesForExcel35[i])
        fillOneCell(row, columnNumber + 34, cellStyle, i)
        fillOneCell(row, columnNumber + 35, cellStyle, valuesForExcel36[i])
        fillOneCell(row, columnNumber + 36, cellStyle, i)
        fillOneCell(row, columnNumber + 37, cellStyle, valuesAmperageForExcel11[i])
        fillOneCell(row, columnNumber + 38, cellStyle, i)
        fillOneCell(row, columnNumber + 39, cellStyle, valuesAmperageForExcel12[i])
        fillOneCell(row, columnNumber + 40, cellStyle, i)
        fillOneCell(row, columnNumber + 41, cellStyle, valuesAmperageForExcel13[i])
        fillOneCell(row, columnNumber + 42, cellStyle, i)
        fillOneCell(row, columnNumber + 43, cellStyle, valuesAmperageForExcel14[i])
        fillOneCell(row, columnNumber + 44, cellStyle, i)
        fillOneCell(row, columnNumber + 45, cellStyle, valuesAmperageForExcel15[i])
        fillOneCell(row, columnNumber + 46, cellStyle, i)
        fillOneCell(row, columnNumber + 47, cellStyle, valuesAmperageForExcel16[i])
        fillOneCell(row, columnNumber + 48, cellStyle, i)
        fillOneCell(row, columnNumber + 49, cellStyle, valuesAmperageForExcel21[i])
        fillOneCell(row, columnNumber + 50, cellStyle, i)
        fillOneCell(row, columnNumber + 51, cellStyle, valuesAmperageForExcel22[i])
        fillOneCell(row, columnNumber + 52, cellStyle, i)
        fillOneCell(row, columnNumber + 53, cellStyle, valuesAmperageForExcel23[i])
        fillOneCell(row, columnNumber + 54, cellStyle, i)
        fillOneCell(row, columnNumber + 55, cellStyle, valuesAmperageForExcel24[i])
        fillOneCell(row, columnNumber + 56, cellStyle, i)
        fillOneCell(row, columnNumber + 57, cellStyle, valuesAmperageForExcel25[i])
        fillOneCell(row, columnNumber + 58, cellStyle, i)
        fillOneCell(row, columnNumber + 59, cellStyle, valuesAmperageForExcel26[i])
        fillOneCell(row, columnNumber + 60, cellStyle, i)
        fillOneCell(row, columnNumber + 61, cellStyle, valuesAmperageForExcel31[i])
        fillOneCell(row, columnNumber + 62, cellStyle, i)
        fillOneCell(row, columnNumber + 63, cellStyle, valuesAmperageForExcel32[i])
        fillOneCell(row, columnNumber + 64, cellStyle, i)
        fillOneCell(row, columnNumber + 65, cellStyle, valuesAmperageForExcel33[i])
        fillOneCell(row, columnNumber + 66, cellStyle, i)
        fillOneCell(row, columnNumber + 67, cellStyle, valuesAmperageForExcel34[i])
        fillOneCell(row, columnNumber + 68, cellStyle, i)
        fillOneCell(row, columnNumber + 69, cellStyle, valuesAmperageForExcel35[i])
        fillOneCell(row, columnNumber + 70, cellStyle, i)
        fillOneCell(row, columnNumber + 71, cellStyle, valuesAmperageForExcel36[i])
        row = sheet.createRow(++rowNum)
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
    if (values11.size > 1000) {
        step = (values11.size - values11.size % 1000) / 1000
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
    for (i in valuesForExcel11.indices) {
        fillOneCell(row, columnNumber, cellStyle, i)
        fillOneCell(row, columnNumber + 1, cellStyle, valuesForExcel11[i])
        fillOneCell(row, columnNumber + 2, cellStyle, i)
        fillOneCell(row, columnNumber + 3, cellStyle, valuesForExcel12[i])
        fillOneCell(row, columnNumber + 4, cellStyle, i)
        fillOneCell(row, columnNumber + 5, cellStyle, valuesForExcel13[i])
        fillOneCell(row, columnNumber + 6, cellStyle, i)
        fillOneCell(row, columnNumber + 7, cellStyle, valuesForExcel14[i])
        fillOneCell(row, columnNumber + 8, cellStyle, i)
        row = sheet.createRow(++rowNum)
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
    return headStyle
}

private fun drawLineChart36(workbook: XSSFWorkbook) {
    val sheet = workbook.getSheet("Sheet1")
    val lastRowIndex = sheet.lastRowNum - 1

    val timeData11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 0, 0))
    val valueData11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 1, 1))

    val timeData12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 2, 2))
    val valueData12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 3, 3))

    val timeData13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 4, 4))
    val valueData13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 5, 5))

    val timeData14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 6, 6))
    val valueData14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 7, 7))

    val timeData15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 8, 8))
    val valueData15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 9, 9))

    val timeData16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 10, 10))
    val valueData16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 11, 11))

    val timeData21 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 12, 12))
    val valueData21 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 13, 13))

    val timeData22 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 14, 14))
    val valueData22 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 15, 15))

    val timeData23 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 16, 16))
    val valueData23 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 17, 17))

    val timeData24 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 18, 18))
    val valueData24 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 19, 19))

    val timeData25 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 20, 20))
    val valueData25 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 21, 21))

    val timeData26 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 22, 22))
    val valueData26 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 23, 23))

    val timeData31 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 24, 24))
    val valueData31 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 25, 25))

    val timeData32 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 26, 26))
    val valueData32 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 27, 27))

    val timeData33 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 28, 28))
    val valueData33 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 29, 29))

    val timeData34 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 30, 30))
    val valueData34 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 31, 31))

    val timeData35 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 32, 32))
    val valueData35 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 33, 33))

    val timeData36 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 34, 34))
    val valueData36 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 35, 35))

    val timeDataAmper11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 36, 36))
    val amperData11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 37, 37))

    val timeDataAmper12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 38, 38))
    val amperData12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 39, 39))

    val timeDataAmper13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 40, 40))
    val amperData13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 41, 41))

    val timeDataAmper14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 42, 42))
    val amperData14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 43, 43))

    val timeDataAmper15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 44, 44))
    val amperData15 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 45, 45))

    val timeDataAmper16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 46, 46))
    val amperData16 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 47, 47))

    val timeDataAmper21 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 48, 48))
    val amperData21 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 49, 49))

    val timeDataAmper22 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 50, 50))
    val amperData22 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 51, 51))

    val timeDataAmper23 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 52, 52))
    val amperData23 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 53, 53))

    val timeDataAmper24 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 54, 54))
    val amperData24 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 55, 55))

    val timeDataAmper25 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 56, 56))
    val amperData25 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 57, 57))

    val timeDataAmper26 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 58, 58))
    val amperData26 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 59, 59))

    val timeDataAmper31 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 60, 60))
    val amperData31 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 61, 61))

    val timeDataAmper32 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 62, 62))
    val amperData32 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 63, 63))

    val timeDataAmper33 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 64, 64))
    val amperData33 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 65, 65))

    val timeDataAmper34 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 66, 66))
    val amperData34 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 67, 67))

    val timeDataAmper35 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 68, 68))
    val amperData35 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 69, 69))

    val timeDataAmper36 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 70, 70))
    val amperData36 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 71, 71))

    val lineChart11 = createLineChart(sheet, 16, 26)
    drawLineChart36(lineChart11, timeData11, valueData11)
    val lineChart12 = createLineChart(sheet, 27, 37)
    drawLineChart36(lineChart12, timeData12, valueData12)
    val lineChart13 = createLineChart(sheet, 38, 48)
    drawLineChart36(lineChart13, timeData13, valueData13)
    val lineChart14 = createLineChart(sheet, 49, 59)
    drawLineChart36(lineChart14, timeData14, valueData14)
    val lineChart15 = createLineChart(sheet, 60, 70)
    drawLineChart36(lineChart15, timeData15, valueData15)
    val lineChart16 = createLineChart(sheet, 71, 81)
    drawLineChart36(lineChart16, timeData16, valueData16)
    val lineChart21 = createLineChart(sheet, 82, 92)
    drawLineChart36(lineChart21, timeData21, valueData21)
    val lineChart22 = createLineChart(sheet, 93, 103)
    drawLineChart36(lineChart22, timeData22, valueData22)
    val lineChart23 = createLineChart(sheet, 104, 114)
    drawLineChart36(lineChart23, timeData23, valueData23)
    val lineChart24 = createLineChart(sheet, 115, 125)
    drawLineChart36(lineChart24, timeData24, valueData24)
    val lineChart25 = createLineChart(sheet, 126, 136)
    drawLineChart36(lineChart25, timeData25, valueData25)
    val lineChart26 = createLineChart(sheet, 137, 147)
    drawLineChart36(lineChart26, timeData26, valueData26)
    val lineChart31 = createLineChart(sheet, 148, 158)
    drawLineChart36(lineChart31, timeData31, valueData31)
    val lineChart32 = createLineChart(sheet, 159, 169)
    drawLineChart36(lineChart32, timeData32, valueData32)
    val lineChart33 = createLineChart(sheet, 170, 180)
    drawLineChart36(lineChart33, timeData33, valueData33)
    val lineChart34 = createLineChart(sheet, 181, 191)
    drawLineChart36(lineChart34, timeData34, valueData34)
    val lineChart35 = createLineChart(sheet, 192, 202)
    drawLineChart36(lineChart35, timeData35, valueData35)
    val lineChart36 = createLineChart(sheet, 203, 213)
    drawLineChart36(lineChart36, timeData36, valueData36)

    val lineChartAmper11 = createLineChart(sheet, 214, 224)
    drawLineChart36(lineChartAmper11, timeDataAmper11, amperData11)
    val lineChartAmper12 = createLineChart(sheet, 235, 245)
    drawLineChart36(lineChartAmper12, timeDataAmper12, amperData12)
    val lineChartAmper13 = createLineChart(sheet, 246, 256)
    drawLineChart36(lineChartAmper13, timeDataAmper13, amperData13)
    val lineChartAmper14 = createLineChart(sheet, 257, 267)
    drawLineChart36(lineChartAmper14, timeDataAmper14, amperData14)
    val lineChartAmper15 = createLineChart(sheet, 268, 278)
    drawLineChart36(lineChartAmper15, timeDataAmper15, amperData15)
    val lineChartAmper16 = createLineChart(sheet, 279, 289)
    drawLineChart36(lineChartAmper16, timeDataAmper16, amperData16)
    val lineChartAmper21 = createLineChart(sheet, 290, 300)
    drawLineChart36(lineChartAmper21, timeDataAmper21, amperData21)
    val lineChartAmper22 = createLineChart(sheet, 301, 311)
    drawLineChart36(lineChartAmper22, timeDataAmper22, amperData22)
    val lineChartAmper23 = createLineChart(sheet, 312, 322)
    drawLineChart36(lineChartAmper23, timeDataAmper23, amperData23)
    val lineChartAmper24 = createLineChart(sheet, 323, 333)
    drawLineChart36(lineChartAmper24, timeDataAmper24, amperData24)
    val lineChartAmper25 = createLineChart(sheet, 334, 344)
    drawLineChart36(lineChartAmper25, timeDataAmper25, amperData25)
    val lineChartAmper26 = createLineChart(sheet, 345, 355)
    drawLineChart36(lineChartAmper26, timeDataAmper26, amperData26)
    val lineChartAmper31 = createLineChart(sheet, 356, 366)
    drawLineChart36(lineChartAmper31, timeDataAmper31, amperData31)
    val lineChartAmper32 = createLineChart(sheet, 367, 377)
    drawLineChart36(lineChartAmper32, timeDataAmper32, amperData32)
    val lineChartAmper33 = createLineChart(sheet, 378, 388)
    drawLineChart36(lineChartAmper33, timeDataAmper33, amperData33)
    val lineChartAmper34 = createLineChart(sheet, 389, 399)
    drawLineChart36(lineChartAmper34, timeDataAmper34, amperData34)
    val lineChartAmper35 = createLineChart(sheet, 400, 410)
    drawLineChart36(lineChartAmper35, timeDataAmper35, amperData35)
    val lineChartAmper36 = createLineChart(sheet, 411, 421)
    drawLineChart36(lineChartAmper36, timeDataAmper36, amperData36)
}

private fun drawLineChart4(workbook: XSSFWorkbook) {
    val sheet = workbook.getSheet("Sheet1")
    val lastRowIndex = sheet.lastRowNum - 1

    val timeData11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 0, 0))
    val valueData11 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 1, 1))

    val timeData12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 2, 2))
    val valueData12 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 3, 3))

    val timeData13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 4, 4))
    val valueData13 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 5, 5))

    val timeData14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 6, 6))
    val valueData14 = DataSources.fromNumericCellRange(sheet, CellRangeAddress(15, lastRowIndex, 7, 7))

    val lineChart11 = createLineChartInsulation(sheet, 16, 26)
    drawLineChart36(lineChart11, timeData11, valueData11)
    val lineChart12 = createLineChartInsulation(sheet, 27, 37)
    drawLineChart36(lineChart12, timeData12, valueData12)
    val lineChart13 = createLineChartInsulation(sheet, 38, 48)
    drawLineChart36(lineChart13, timeData13, valueData13)
    val lineChart14 = createLineChartInsulation(sheet, 49, 59)
    drawLineChart36(lineChart14, timeData14, valueData14)
}

private fun createLineChart(sheet: XSSFSheet, rowStart: Int, rowEnd: Int): XSSFChart {
    val drawing = sheet.createDrawingPatriarch()
    val anchor = drawing.createAnchor(0, 0, 0, 0, 72, rowStart, 82, rowEnd)

    return drawing.createChart(anchor)
}

private fun createLineChartInsulation(sheet: XSSFSheet, rowStart: Int, rowEnd: Int): XSSFChart {
    val drawing = sheet.createDrawingPatriarch()
    val anchor = drawing.createAnchor(0, 0, 0, 0, 9, rowStart, 19, rowEnd)

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
    yAxisData: ChartDataSource<Number>
) {
    val data = lineChart.chartDataFactory.createLineChartData()

    val xAxis = lineChart.chartAxisFactory.createCategoryAxis(AxisPosition.BOTTOM)
    val yAxis = lineChart.createValueAxis(AxisPosition.LEFT)
    yAxis.crosses = org.apache.poi.ss.usermodel.charts.AxisCrosses.AUTO_ZERO

    val series = data.addSeries(xAxisData, yAxisData)
    series.setTitle("График")
    lineChart.plot(data, xAxis, yAxis)

    val plotArea = lineChart.ctChart.plotArea
    plotArea.lineChartArray[0].smooth
    val ctBool = CTBoolean.Factory.newInstance()
    ctBool.`val` = false
    plotArea.lineChartArray[0].smooth = ctBool
    for (series in plotArea.lineChartArray[0].serArray) {
        series.smooth = ctBool
    }
}
