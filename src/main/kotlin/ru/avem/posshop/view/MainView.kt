package ru.avem.posshop.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.shape.Circle
import javafx.stage.Modality
import ru.avem.posshop.controllers.MainViewController
import ru.avem.posshop.entities.*
import ru.avem.posshop.view.Styles.Companion.extraHard
import ru.avem.posshop.view.Styles.Companion.megaHard
import ru.avem.posshop.view.Styles.Companion.superExtraHard
import tornadofx.*
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.exitProcess

class MainView : View("Комплексный стенд для испытания ПОС лопасти несущего винта") {
    override val configPath: Path = Paths.get("./app.conf")

    private val controller: MainViewController by inject()

    var mainMenuBar: MenuBar by singleAssign()
    var comIndicate: Circle by singleAssign()

    var comboBoxTests: ComboBox<Test> by singleAssign()
    var comboBoxTestItem: ComboBox<TestItem> by singleAssign()


    var initTableTest1: TableView<TableValuesTest1> by singleAssign()
    var initTableTest2: TableView<TableValuesTest2> by singleAssign()

    var centralStation: VBox by singleAssign()

    var vBoxLog: VBox by singleAssign()

    var labelTimeRemaining: Label by singleAssign()
    var labelTestStatus: Label by singleAssign()
    var labelTestStatusEnd1: Label by singleAssign()

    var buttonStop: Button by singleAssign()

    val t1 = vbox(spacing = 64.0) {
        hbox(spacing = 16.0) {
            alignmentProperty().set(Pos.CENTER)
            tableview(controller.tableValuesTest21) {
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                    margin = insets(100, 50, 100, 50)
                }
                minHeight = 96.0
                maxHeight = 96.0
                columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                mouseTransparentProperty().set(true)
                column("U, В", TableValuesTest21::voltage.getter)
                column("I, А", TableValuesTest21::ampere.getter)
            }
            tableview(controller.tableValuesTest22) {
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                    margin = insets(100, 50, 100, 50)
                }
                minHeight = 96.0
                maxHeight = 96.0
                columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                mouseTransparentProperty().set(true)
                column("U, В", TableValuesTest22::voltage.getter)
                column("I, А", TableValuesTest22::ampere.getter)
            }
            tableview(controller.tableValuesTest23) {
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                    margin = insets(100, 50, 100, 50)
                }
                minHeight = 96.0
                maxHeight = 96.0
                columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                mouseTransparentProperty().set(true)
                column("U, В", TableValuesTest23::voltage.getter)
                column("I, А", TableValuesTest23::ampere.getter)
            }
        }
    }

    val t2 = vbox(spacing = 64.0) {
        hbox(spacing = 16.0) {
            alignmentProperty().set(Pos.CENTER)
            tableview(controller.tableValuesTestHV1) {
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                    margin = insets(100)
                }
                minHeight = 197.0
                maxHeight = 197.0
                columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                mouseTransparentProperty().set(true)
                column("Место", TableValuesTestHV::descriptor.getter)
                column("U заданное, В", TableValuesTestHV::voltageSpec.getter)
                column("U ОИ, В", TableValuesTestHV::voltage.getter)
                column("I заданное, А", TableValuesTestHV::amperageSpec.getter)
                column("I ОИ, А", TableValuesTestHV::amperage.getter)
                column("t заданное, с", TableValuesTestHV::timeSpec.getter)
                column("t ОИ, с", TableValuesTestHV::time.getter)
            }
        }
    }

    val testsProp = SimpleObjectProperty<Test>()

    var buttonStart: Button by singleAssign()

    var checkBoxPlace1: CheckBox by singleAssign()
    var checkBoxPlace2: CheckBox by singleAssign()
    var checkBoxPlace3: CheckBox by singleAssign()

    private val placeTestItem = SimpleObjectProperty<TestItem>().apply {
        onChange { ti ->
            controller.tableValuesTest1.forEach {
                it.place1temp.value = ti?.t1Temp.toString()
                it.place1time.value = ti?.t1Time.toString()
                it.place1voltage.value = ti?.t1Voltage.toString()
            }
            controller.tableValuesTest2.forEach {
                it.place1amperage.value = ti?.t2Amperage.toString()
                it.place1time.value = ti?.t2Time.toString()
                it.place1voltage.value = ti?.t2Voltage.toString()
            }
        }
    }

    val place1Prop = SimpleBooleanProperty()
    val place2Prop = SimpleBooleanProperty()
    val place3Prop = SimpleBooleanProperty()

    override val root = borderpane {
        prefWidth = 1920.0
        prefHeight = 1000.0
        top {
            mainMenuBar = menubar {
                menu("Меню") {
                    item("Выход") {
                        action {
                            exitProcess(0)
                        }
                    }
                }
                menu("База данных") {
                    item("Протоколы") {
                        action {
                            find<ProtocolListWindow>().openModal(
                                modality = Modality.WINDOW_MODAL,
                                escapeClosesWindow = true,
                                resizable = false,
                                owner = this@MainView.currentWindow
                            )
                        }
                    }
                    item("Протоколы проверки изоляции") {
                        action {
                            find<ProtocolInsulationListWindow>().openModal(
                                modality = Modality.WINDOW_MODAL,
                                escapeClosesWindow = true,
                                resizable = false,
                                owner = this@MainView.currentWindow
                            )
                        }
                    }
                }
                menu("Информация") {
                    item("Версия ПО") {
                        action {
                            controller.showAboutUs()
                        }
                    }
                }
            }.addClass(megaHard)
        }
        center {
            centralStation = vbox(spacing = 16.0) {
                paddingAll = 16.0
                alignmentProperty().set(Pos.CENTER)

                hbox(spacing = 16.0) {
                    alignmentProperty().set(Pos.CENTER)
                    label("Выберите испытание:").addClass(superExtraHard)
                    comboBoxTests = combobox(values = tests, property = testsProp) {
                        useMaxWidth = true
                        alignment = Pos.CENTER

                        selectionModel.selectFirst()
                    }.addClass(extraHard)
                }

                hbox(spacing = 16.0) {
                    alignmentProperty().set(Pos.CENTER)
                    label("Выберите объект испытания :").addClass(superExtraHard)
                    comboBoxTestItem = combobox<TestItem>(values = testItems, property = placeTestItem) {
                    }.addClass(extraHard)
                }




                hbox(spacing = 16.0) {
                    alignmentProperty().set(Pos.CENTER)
                    vbox(spacing = 16.0) {
                        alignmentProperty().set(Pos.CENTER)
                        checkbox { }.isVisible = false
                        initTableTest1 = tableview(controller.tableValuesTest1) {
                            vboxConstraints {
                                vGrow = Priority.ALWAYS
                                margin = insets(0, 0, 0, 0)
                            }
                            minHeight = 104.0
                            maxHeight = 104.0
                            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                            column("t, °C", TableValuesTest1::place1temp.getter).makeEditable()
                            column("t, м", TableValuesTest1::place1time.getter).makeEditable()
                            column("U, В", TableValuesTest1::place1voltage.getter).makeEditable()
                        }.removeWhen {
                            testsProp.isNotEqualTo(tests[0])
                        }
                        initTableTest2 = tableview(controller.tableValuesTest2) {
                            vboxConstraints {
                                vGrow = Priority.ALWAYS
                                margin = insets(0, 0, 0, 0)
                            }
                            minHeight = 104.0
                            maxHeight = 104.0
                            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                            column("I, мА", TableValuesTest2::place1amperage.getter).makeEditable()
                            column("t, с", TableValuesTest2::place1time.getter).makeEditable()
                            column("U, В", TableValuesTest2::place1voltage.getter).makeEditable()
                        }.removeWhen {
                            testsProp.isNotEqualTo(tests[1])
                        }
                    }







                    vbox(spacing = 16.0) {
                        hboxConstraints {
                            hGrow = Priority.ALWAYS
                        }
                        alignmentProperty().set(Pos.CENTER)
                        checkBoxPlace1 = checkbox(property = place1Prop) {}.addClass(extraHard)
                        tableview(controller.tableValuesPlace1Test1) {
                            vboxConstraints {
                                vGrow = Priority.ALWAYS
                                margin = insets(0, 0, 0, 0)
                            }
                            minHeight = 346.0
                            maxHeight = 346.0
                            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                            mouseTransparentProperty().set(true)
                            column("Секция", TableValuesPlace1Test1::descriptor.getter)
                            column("t, °C", TableValuesPlace1Test1::place1temp.getter)
                            column("U, В", TableValuesPlace1Test1::place1voltage.getter)
                            column("I, A", TableValuesPlace1Test1::place1amperage.getter)
                        }.removeWhen {
                            testsProp.isNotEqualTo(tests[0])
                        }.disableWhen {
                            !place1Prop
                        }
                        tableview(controller.tableValuesPlace1Test2) {
                            vboxConstraints {
                                vGrow = Priority.ALWAYS
                                margin = insets(0, 0, 0, 0)
                            }
                            minHeight = 346.0
                            maxHeight = 346.0
                            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                            mouseTransparentProperty().set(true)
                            column("I, мА", TableValuesPlace1Test2::place1amperage.getter)
                            column("U, В", TableValuesPlace1Test2::place1voltage.getter)
                        }.removeWhen {
                            testsProp.isNotEqualTo(tests[1])
                        }.disableWhen {
                            !place1Prop
                        }
                    }





                    vbox(spacing = 16.0) {
                        hboxConstraints {
                            hGrow = Priority.ALWAYS
                        }
                        alignmentProperty().set(Pos.CENTER)
                        checkBoxPlace2 = checkbox(property = place2Prop) {
                        }.addClass(extraHard)
                        tableview(controller.tableValuesPlace2Test1) {
                            vboxConstraints {
                                vGrow = Priority.ALWAYS
                                margin = insets(0, 0, 0, 0)
                            }
                            minHeight = 346.0
                            maxHeight = 346.0
                            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                            mouseTransparentProperty().set(true)
                            column("Секция", TableValuesPlace2Test1::descriptor.getter)
                            column("t, °C", TableValuesPlace2Test1::place2temp.getter)
                            column("U, В", TableValuesPlace2Test1::place2voltage.getter)
                            column("I, A", TableValuesPlace2Test1::place2amperage.getter)
                        }.removeWhen {
                            testsProp.isNotEqualTo(tests[0])
                        }.disableWhen {
                            !place2Prop
                        }
                        tableview(controller.tableValuesPlace2Test2) {
                            vboxConstraints {
                                vGrow = Priority.ALWAYS
                                margin = insets(0, 0, 0, 0)
                            }
                            minHeight = 346.0
                            maxHeight = 346.0
                            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                            mouseTransparentProperty().set(true)
                            column("I, мА", TableValuesPlace2Test2::place2amperage.getter)
                            column("U, В", TableValuesPlace2Test2::place2voltage.getter)
                        }.removeWhen {
                            testsProp.isNotEqualTo(tests[1])
                        }.disableWhen {
                            !place2Prop
                        }
                    }
                    vbox(spacing = 16.0) {
                        hboxConstraints {
                            hGrow = Priority.ALWAYS
                        }
                        alignmentProperty().set(Pos.CENTER)
                        checkBoxPlace3 = checkbox(property = place3Prop) {
                        }.addClass(extraHard)
                        tableview(controller.tableValuesPlace3Test1) {
                            vboxConstraints {
                                vGrow = Priority.ALWAYS
                                margin = insets(0, 0, 0, 0)
                            }
                            useMaxWidth = true
                            minHeight = 346.0
                            maxHeight = 346.0
                            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                            mouseTransparentProperty().set(true)
                            column("Секция", TableValuesPlace3Test1::descriptor.getter)
                            column("t, °C", TableValuesPlace3Test1::place3temp.getter)
                            column("U, В", TableValuesPlace3Test1::place3voltage.getter)
                            column("I, A", TableValuesPlace3Test1::place3amperage.getter)
                        }.removeWhen {
                            testsProp.isNotEqualTo(tests[0])
                        }.disableWhen {
                            !place3Prop
                        }
                        tableview(controller.tableValuesPlace3Test2) {
                            vboxConstraints {
                                vGrow = Priority.ALWAYS
                                margin = insets(0, 0, 0, 0)
                            }
                            useMaxWidth = true
                            minHeight = 346.0
                            maxHeight = 346.0
                            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                            mouseTransparentProperty().set(true)
                            column("I, мА", TableValuesPlace3Test2::place3amperage.getter)
                            column("U, В", TableValuesPlace3Test2::place3voltage.getter)
                        }.removeWhen {
                            testsProp.isNotEqualTo(tests[1])
                        }.disableWhen {
                            !place3Prop
                        }
                    }
                }

                vbox {
                    hbox {
                        prefHeight = 150.0
                        vboxConstraints {
                            vGrow = Priority.ALWAYS
                        }
                        alignmentProperty().set(Pos.CENTER)
                        anchorpane {
                            hboxConstraints {
                                hGrow = Priority.ALWAYS
                            }
                            scrollpane {
                                anchorpaneConstraints {
                                    leftAnchor = 300.0
                                    topAnchor = 0.0
                                    rightAnchor = 300.0
                                    bottomAnchor = 0.0
                                }
                                vBoxLog = vbox {
                                }.addClass(megaHard)

                                vvalueProperty().bind(vBoxLog.heightProperty())
                            }
                        }
                    }
                    hbox(spacing = 16) {
                        alignment = Pos.CENTER
                        labelTestStatusEnd1 = label("")
                        labelTestStatus = label("")
                        labelTimeRemaining = label("")
                    }.addClass(extraHard)
                    hbox(spacing = 16) {
                        alignment = Pos.CENTER
                        buttonStart = button("Запустить") {
                            prefWidth = 640.0
                            prefHeight = 128.0
                            action {
                                controller.handleStartTest()
                            }
                        }.addClass(Styles.stopStart)
                        buttonStop = button("Остановить") {
                            prefWidth = 640.0
                            prefHeight = 128.0
                            action {
                                controller.handleStopTest()
                            }
                        }.addClass(Styles.stopStart)
                    }
                }
            }
        }
        bottom = hbox(spacing = 32) {
            alignment = Pos.CENTER_LEFT
            comIndicate = circle(radius = 18) {
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                    marginLeft = 8.0
                    marginBottom = 8.0
                }
                fill = c("cyan")
                stroke = c("black")
                isSmooth = true
            }
            label(" Связь со стендом") {
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                    marginBottom = 8.0
                }
            }
        }
    }.addClass(Styles.blueTheme, megaHard)
}
