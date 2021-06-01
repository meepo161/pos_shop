package ru.avem.posshop.view

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.ComboBox
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.posshop.database.entities.ProtocolSingle
import ru.avem.posshop.protocol.saveProtocolAsWorkbook
import ru.avem.posshop.utils.Singleton
import ru.avem.posshop.utils.callKeyBoard
import tornadofx.*
import tornadofx.controlsfx.confirmNotification
import tornadofx.controlsfx.errorNotification
import java.io.File
import java.text.SimpleDateFormat

class GraphHistoryWindow : View("История графика") {
    private var lineChart: LineChart<Number, Number> by singleAssign()
    private var tfOt: TextField by singleAssign()
    private var tfDo: TextField by singleAssign()
    private var values = Singleton.currentProtocol.temp11.removePrefix("[").removeSuffix("]")
        .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
    var comboBoxTestItem: ComboBox<String> by singleAssign()
    private var selectedItemProperty = SimpleStringProperty()
    var sliderOt: Slider by singleAssign()
    var sliderDo: Slider by singleAssign()

    private val sections = observableListOf(
        "1 лопасть 1 секция",
        "1 лопасть 2 секция",
        "1 лопасть 3 секция",
        "1 лопасть 4 секция",
        "1 лопасть 5 секция",
        "1 лопасть 6 секция",
        "2 лопасть 1 секция",
        "2 лопасть 2 секция",
        "2 лопасть 3 секция",
        "2 лопасть 4 секция",
        "2 лопасть 5 секция",
        "2 лопасть 6 секция",
        "3 лопасть 1 секция",
        "3 лопасть 2 секция",
        "3 лопасть 3 секция",
        "3 лопасть 4 секция",
        "3 лопасть 5 секция",
        "3 лопасть 6 секция"
    )
    private var listOfValues = mutableListOf<String>()

    override fun onDock() {
        comboBoxTestItem.selectionModel.selectFirst()
        createLineChart("1 лопасть 1 секция")
        values = Singleton.currentProtocol.temp11.removePrefix("[").removeSuffix("]")
            .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
        selectedItemProperty.onChange {
            createLineChart(it)
            handleOk()
        }
        tfOt.text = "0"
        tfDo.text = (values.size).toString()
        handleOk()
    }

    override val root = anchorpane {
        prefWidth = 1800.0
        prefHeight = 1000.0
        hbox(spacing = 16.0) {
            alignmentProperty().set(Pos.CENTER)
            anchorpaneConstraints {
                leftAnchor = 16.0
                rightAnchor = 16.0
                topAnchor = 16.0
                bottomAnchor = 16.0
            }
            vbox(spacing = 16.0) {
                alignmentProperty().set(Pos.CENTER)
                hbox(spacing = 16.0) {
                    alignmentProperty().set(Pos.CENTER)
                    label("Отобразить график в диапазоне с")
                    tfOt = textfield {
                        callKeyBoard()
                        prefWidth = 100.0
                    }
                    label("секунды до")
                    tfDo = textfield {
                        callKeyBoard()
                        prefWidth = 100.0
                    }
                    button("Ок") {
                        isDefaultButton = true
                        action {
                            handleOk()
                        }
                    }
                    button("Сброс") {
                        action {
                            createLineChart(comboBoxTestItem.selectedItem.toString())
                        }
                    }
                    comboBoxTestItem = combobox(selectedItemProperty) {
                        items = sections
                    }
                    button("Сохранить") {
                        action {
                            save()
                        }
                    }
                }
                hbox(spacing = 16.0) {
                    alignmentProperty().set(Pos.CENTER)
                    val numberAxis = NumberAxis()
                    numberAxis.isForceZeroInRange = false
                    lineChart = linechart("", numberAxis, NumberAxis()) {
                        prefWidth = 1800.0
                        prefHeight = 1200.0
                        animated = false
                        createSymbols = false
                        isLegendVisible = false
                    }
                }
                hbox(spacing = 16.0) {
                    alignmentProperty().set(Pos.CENTER)
                    vbox(spacing = 64.0) {
                        alignmentProperty().set(Pos.CENTER)
                        sliderOt = slider {
                            prefWidth = 1800.0
                            isShowTickLabels = true
                            isShowTickMarks = true
                            onMouseReleased = EventHandler {
                                tfOt.text = value.toInt().toString()
                                handleOk()
                            }
                        }
                        sliderDo = slider {
                            prefWidth = 1800.0
                            isShowTickLabels = true
                            isShowTickMarks = true
                            onMouseReleased = EventHandler {
                                tfDo.text = value.toInt().toString()
                                handleOk()
                            }
                        }
                    }
                }
            }
        }
    }.addClass(Styles.hard)

    private fun createLineChart(it: String?) {
        when (it) {
            "1 лопасть 1 секция" ->
                values = Singleton.currentProtocol.temp11.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "1 лопасть 2 секция" ->
                values = Singleton.currentProtocol.temp12.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "1 лопасть 3 секция" ->
                values = Singleton.currentProtocol.temp13.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "1 лопасть 4 секция" ->
                values = Singleton.currentProtocol.temp14.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "1 лопасть 5 секция" ->
                values = Singleton.currentProtocol.temp15.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "1 лопасть 6 секция" ->
                values = Singleton.currentProtocol.temp16.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "2 лопасть 1 секция" ->
                values = Singleton.currentProtocol.temp21.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "2 лопасть 2 секция" ->
                values = Singleton.currentProtocol.temp22.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "2 лопасть 3 секция" ->
                values = Singleton.currentProtocol.temp23.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "2 лопасть 4 секция" ->
                values = Singleton.currentProtocol.temp24.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "2 лопасть 5 секция" ->
                values = Singleton.currentProtocol.temp25.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "2 лопасть 6 секция" ->
                values = Singleton.currentProtocol.temp26.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "3 лопасть 1 секция" ->
                values = Singleton.currentProtocol.temp31.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "3 лопасть 2 секция" ->
                values = Singleton.currentProtocol.temp32.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "3 лопасть 3 секция" ->
                values = Singleton.currentProtocol.temp33.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "3 лопасть 4 секция" ->
                values = Singleton.currentProtocol.temp34.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "3 лопасть 5 секция" ->
                values = Singleton.currentProtocol.temp35.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
            "3 лопасть 6 секция" ->
                values = Singleton.currentProtocol.temp36.removePrefix("[").removeSuffix("]")
                    .split(", ").map { it.replace(',', '.') }.map(String::toDouble)
        }

        sliderOt.max = values.size.toDouble()
        sliderDo.max = values.size.toDouble()

        var series = XYChart.Series<Number, Number>()
        series.data.clear()
        lineChart.data.clear()
        lineChart.title = comboBoxTestItem.selectedItem.toString()

        var realTime = 0.0

        var needAddSeriesToChart = true
        for (i in values.indices) {
            if (values[i].isNaN()) {
                series = XYChart.Series()
                needAddSeriesToChart = true
                realTime += 1
            } else {
                series.data.add(XYChart.Data(realTime, values[i]))
                realTime += 1

                if (needAddSeriesToChart) {
                    lineChart.data.add(series)
                    needAddSeriesToChart = false
                }
            }
        }
        lineChart.xAxis.label = "Время, с"
    }

    private fun handleOk() {
        if (!tfOt.text.isNullOrEmpty() || !tfDo.text.isNullOrEmpty()) {
            var series = XYChart.Series<Number, Number>()
            var ot1 = 0.0
            var do1 = 0.0
            try {
                ot1 = if (tfOt.text.replace(',', '.').toDouble() < 0) {
                    0.0
                } else {
                    tfOt.text.replace(',', '.').toDouble()
                }
                do1 = tfDo.text.replace(',', '.').toDouble()
            } catch (e: Exception) {
                Platform.runLater {
                    errorNotification(
                        "Ошибка",
                        "Проверьте правильность введенных данных",
                        Pos.BOTTOM_CENTER,
                        owner = this@GraphHistoryWindow.currentWindow
                    )
                }
            }
            series.data.clear()
            lineChart.data.clear()
            var realTime = ot1
            if (do1 > values.size) {
                do1 = values.size.toDouble()
                tfDo.text = (values.size).toString()
            }
            var needAddSeriesToChart = true
            for (i in ot1.toInt() until do1.toInt()) {
                if (values[i].isNaN()) {
                    series = XYChart.Series()
                    needAddSeriesToChart = true
                    realTime += 1
                } else {
                    series.data.add(XYChart.Data(realTime, values[i]))
                    realTime += 1

                    if (needAddSeriesToChart) {
                        lineChart.data.add(series)
                        needAddSeriesToChart = false
                    }
                }
            }
            lineChart.xAxis.isAutoRanging = true
            sliderOt.value = tfOt.text.toDouble()
            sliderDo.value = tfDo.text.toDouble()
        } else {
            Platform.runLater {
                errorNotification(
                    "Ошибка",
                    "Пустые поля. Заполните данные",
                    Pos.BOTTOM_CENTER,
                    owner = this@GraphHistoryWindow.currentWindow
                )
            }
        }
    }

    private fun save() {
        if (!tfOt.text.isNullOrEmpty() || !tfDo.text.isNullOrEmpty()) {
            try {
                var ot1 = 0.0
                var do1 = 0.0
                ot1 = if (tfOt.text.replace(',', '.').toDouble() < 0) {
                    0.0
                } else {
                    tfOt.text.replace(',', '.').toDouble()
                }
                do1 = tfDo.text.replace(',', '.').toDouble()
                if (do1 > values.size) {
                    do1 = values.size.toDouble()
                    tfDo.text = (values.size).toString()
                }
                for (i in ot1.toInt() until do1.toInt()) {
                    listOfValues.add(String.format("%.1f", values[i]))
                }

                val dateFormatter = SimpleDateFormat("dd.MM.y")
                val timeFormatter = SimpleDateFormat("HH:mm:ss")
                val unixTime = System.currentTimeMillis()

                val protocolSingle = transaction {
                    ProtocolSingle.new {
                        date = dateFormatter.format(unixTime).toString()
                        time = timeFormatter.format(unixTime).toString()
                        section = comboBoxTestItem.selectedItem.toString()
                        temp = listOfValues.toString()
                    }
                }

                val files = chooseFile(
                    "Выберите директорию для сохранения",
                    arrayOf(FileChooser.ExtensionFilter("XLSX Files (*.xlsx)", "*.xlsx")),
                    FileChooserMode.Save,
                    this@GraphHistoryWindow.currentWindow
                ) {
                    this.initialDirectory = File(System.getProperty("user.home"))
                }

                if (files.isNotEmpty()) {
                    saveProtocolAsWorkbook(
                        protocolSingle,
                        files.first().absolutePath,
                        ot1.toInt(),
                        do1.toInt()
                    )

                    Platform.runLater {
                        confirmNotification(
                            "Готово",
                            "Успешно сохранено",
                            Pos.BOTTOM_CENTER,
                            owner = this@GraphHistoryWindow.currentWindow
                        )
                    }
                }
            } catch (e: Exception) {
                Platform.runLater {
                    errorNotification(
                        "Ошибка",
                        "Проверьте правильность введенных данных",
                        Pos.BOTTOM_CENTER,
                        owner = this@GraphHistoryWindow.currentWindow
                    )
                }
            }
        }
    }
}