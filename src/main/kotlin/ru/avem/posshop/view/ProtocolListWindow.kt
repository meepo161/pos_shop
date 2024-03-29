package ru.avem.posshop.view

import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.stage.FileChooser
import javafx.stage.Modality
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.posshop.database.entities.Protocol
import ru.avem.posshop.database.entities.ProtocolRotorBlade
import ru.avem.posshop.database.entities.ProtocolsTable
import ru.avem.posshop.protocol.saveProtocolAsWorkbook
import ru.avem.posshop.utils.Singleton
import ru.avem.posshop.utils.Toast
import ru.avem.posshop.utils.callKeyBoard
import tornadofx.*
import tornadofx.controlsfx.confirmNotification
import tornadofx.controlsfx.errorNotification
import java.awt.Desktop
import java.io.File

class ProtocolListWindow : View("Протоколы графиков") {
    private var tableViewProtocols: TableView<Protocol> by singleAssign()
    private lateinit var protocols: ObservableList<Protocol>
    override fun onDock() {
        protocols = transaction {
            Protocol.all().toList().asObservable()
        }
        tableViewProtocols.items = protocols
    }


    override val root = anchorpane {
        prefWidth = 1800.0
        prefHeight = 900.0

        vbox(spacing = 16.0) {
            anchorpaneConstraints {
                leftAnchor = 16.0
                rightAnchor = 16.0
                topAnchor = 16.0
                bottomAnchor = 16.0
            }

            alignmentProperty().set(Pos.CENTER)

            textfield {
                callKeyBoard()
                prefWidth = 600.0

                promptText = "Фильтр"
                alignment = Pos.CENTER

                onKeyReleased = EventHandler {
                    if (!text.isNullOrEmpty()) {
                        tableViewProtocols.items = protocols.filter {
                            it.date.contains(text)
                                    || it.time.contains(text)
                                    || it.operator.contains(text)
                                    || it.productNumber1.contains(text)
                                    || it.productNumber2.contains(text)
                                    || it.productNumber3.contains(text)
                        }.asObservable()
                    } else {
                        tableViewProtocols.items = protocols
                    }
                }
            }

            tableViewProtocols = tableview {
                protocols = transaction {
                    Protocol.all().toList().asObservable()
                }
                items = protocols
                prefHeight = 700.0
                columnResizePolicyProperty().set(TableView.CONSTRAINED_RESIZE_POLICY)
                column("№ изделия 1", Protocol::productNumber1)
                column("№ изделия 2", Protocol::productNumber2)
                column("№ изделия 3", Protocol::productNumber3)
                column("Дата", Protocol::date)
                column("Время", Protocol::time)
                column("Оператор", Protocol::operator)
            }

            hbox(spacing = 16.0) {
                alignmentProperty().set(Pos.CENTER)

                button("Печать") {
                    action {
                        if (tableViewProtocols.selectedItem != null) {
                            Singleton.currentProtocol = transaction {
                                Protocol.find {
                                    ProtocolsTable.id eq tableViewProtocols.selectedItem!!.id
                                }.toList().asObservable()
                            }.first()
                            saveProtocolAsWorkbook(Singleton.currentProtocol)
                            close()
//                            openFile(File("protocol.xlsx"))
                            Desktop.getDesktop().print(File("protocol.xlsx"))
                        }
                        runLater {
                            Toast.makeText("Началась печать протокола").show(Toast.ToastType.INFORMATION)
                        }
                    }
                }
                button("Открыть таблицу") {
                    action {
                        if (tableViewProtocols.selectedItem != null) {
                            Singleton.currentProtocol = transaction {
                                Protocol.find {
                                    ProtocolsTable.id eq tableViewProtocols.selectedItem!!.id
                                }.toList().asObservable()
                            }.first()


                            find<GraphHistoryWindow>().openModal(
                                modality = Modality.WINDOW_MODAL, escapeClosesWindow = true,
                                resizable = true, owner = this@ProtocolListWindow.currentWindow
                            )
                        }
                    }
                }
                button("Сохранить как") {
                    action {
                        if (tableViewProtocols.selectedItem != null) {
                            val files = chooseFile(
                                "Выберите директорию для сохранения",
                                arrayOf(FileChooser.ExtensionFilter("XLSX Files (*.xlsx)", "*.xlsx")),
                                FileChooserMode.Save,
                                this@ProtocolListWindow.currentWindow
                            ) {
                                this.initialDirectory = File(System.getProperty("user.home"))
                            }

                            if (files.isNotEmpty()) {
                                saveProtocolAsWorkbook(tableViewProtocols.selectedItem!!, files.first().absolutePath)

                                Platform.runLater {
                                    confirmNotification(
                                        "Готово",
                                        "Успешно сохранено",
                                        Pos.BOTTOM_CENTER,
                                        owner = this@ProtocolListWindow.currentWindow
                                    )
                                }
                            }
                        }
                    }
                }
                button("Печать 1 лопасть") {
                    action {
                        saveRotorBlade(1)
                    }
                }
                button("Печать 2 лопасть") {
                    action {
                        saveRotorBlade(2)
                    }
                }
                button("Печать 3 лопасть") {
                    action {
                        saveRotorBlade(3)
                    }
                }
                button("Сохранить все") {
                    action {
                        if (tableViewProtocols.items.size > 0) {
                            val dir = chooseDirectory(
                                "Выберите директорию для сохранения",
                                File(System.getProperty("user.home")),
                                this@ProtocolListWindow.currentWindow
                            )

                            if (dir != null) {
                                tableViewProtocols.items.forEach {
                                    val file = File(dir, "${it.id.value}.xlsx")
                                    saveProtocolAsWorkbook(it, file.absolutePath)
                                }
                                Platform.runLater {
                                    confirmNotification(
                                        "Готово",
                                        "Успешно сохранено",
                                        Pos.BOTTOM_CENTER,
                                        owner = this@ProtocolListWindow.currentWindow
                                    )
                                }
                            }
                        }
                    }
                }
                button("Удалить") {
                    action {
                        if (tableViewProtocols.selectedItem != null) {
                            transaction {
                                ProtocolsTable.deleteWhere {
                                    ProtocolsTable.id eq tableViewProtocols.selectedItem!!.id
                                }
                            }

                            tableViewProtocols.items = transaction {
                                Protocol.all().toList().asObservable()
                            }
                        }
                    }
                }
            }
        }
    }.addClass(Styles.hard)

    private fun saveRotorBlade(rotorBlade: Int) {
        if (tableViewProtocols.selectedItem != null) {
            val protocol = tableViewProtocols.selectedItem!!
            when (rotorBlade) {
                1 -> {
                    val protocolRotorBlade = transaction {
                        ProtocolRotorBlade.new {
                            date = protocol.date
                            time = protocol.time
                            dateEnd = protocol.dateEnd
                            timeEnd = protocol.timeEnd
                            cipher = protocol.cipher1
                            productName = protocol.productNumber1
                            operator = protocol.operator
                            temp1 = protocol.temp11
                            temp2 = protocol.temp12
                            temp3 = protocol.temp13
                            temp4 = protocol.temp14
                            temp5 = protocol.temp15
                            temp6 = protocol.temp16
                            voltage1 = protocol.voltage11
                            voltage2 = protocol.voltage12
                            voltage3 = protocol.voltage13
                            voltage4 = protocol.voltage14
                            voltage5 = protocol.voltage15
                            voltage6 = protocol.voltage16
                            amperage1 = protocol.amperage11
                            amperage2 = protocol.amperage12
                            amperage3 = protocol.amperage13
                            amperage4 = protocol.amperage14
                            amperage5 = protocol.amperage15
                            amperage6 = protocol.amperage16
                            NUMBER_DATE_ATTESTATION = protocol.NUMBER_DATE_ATTESTATION
                            NAME_OF_OPERATION = protocol.NAME_OF_OPERATION
                            NUMBER_CONTROLLER = protocol.NUMBER_CONTROLLER
                            T1 = protocol.T1
                            T2 = protocol.T2
                            T3 = protocol.T3
                            T4 = protocol.T4
                            T5 = protocol.T5
                            T6 = protocol.T6

                            unixTimeStartProtocol1 = protocol.unixTimeStartProtocol11
                            unixTimeStartProtocol2 = protocol.unixTimeStartProtocol12
                            unixTimeStartProtocol3 = protocol.unixTimeStartProtocol13
                            unixTimeStartProtocol4 = protocol.unixTimeStartProtocol14
                            unixTimeStartProtocol5 = protocol.unixTimeStartProtocol15
                            unixTimeStartProtocol6 = protocol.unixTimeStartProtocol16
                            unixTimeWorkProtocol1 = protocol.unixTimeWorkProtocol11
                            unixTimeWorkProtocol2 = protocol.unixTimeWorkProtocol12
                            unixTimeWorkProtocol3 = protocol.unixTimeWorkProtocol13
                            unixTimeWorkProtocol4 = protocol.unixTimeWorkProtocol14
                            unixTimeWorkProtocol5 = protocol.unixTimeWorkProtocol15
                            unixTimeWorkProtocol6 = protocol.unixTimeWorkProtocol16
                            unixTimeEndProtocol1 = protocol.unixTimeEndProtocol11
                            unixTimeEndProtocol2 = protocol.unixTimeEndProtocol12
                            unixTimeEndProtocol3 = protocol.unixTimeEndProtocol13
                            unixTimeEndProtocol4 = protocol.unixTimeEndProtocol14
                            unixTimeEndProtocol5 = protocol.unixTimeEndProtocol15
                            unixTimeEndProtocol6 = protocol.unixTimeEndProtocol16
                        }
                    }
                    saveProtocolAsWorkbook(protocolRotorBlade)
                    Desktop.getDesktop().print(File("protocol1RotorBlade.xlsx"))
                }
                2 -> {
                    val protocolRotorBlade = transaction {
                        ProtocolRotorBlade.new {
                            date = protocol.date
                            time = protocol.time
                            dateEnd = protocol.dateEnd
                            timeEnd = protocol.timeEnd
                            cipher = protocol.cipher2
                            productName = protocol.productNumber2
                            operator = protocol.operator
                            temp1 = protocol.temp21
                            temp2 = protocol.temp22
                            temp3 = protocol.temp23
                            temp4 = protocol.temp24
                            temp5 = protocol.temp25
                            temp6 = protocol.temp26
                            voltage1 = protocol.voltage21
                            voltage2 = protocol.voltage22
                            voltage3 = protocol.voltage23
                            voltage4 = protocol.voltage24
                            voltage5 = protocol.voltage25
                            voltage6 = protocol.voltage26
                            amperage1 = protocol.amperage21
                            amperage2 = protocol.amperage22
                            amperage3 = protocol.amperage23
                            amperage4 = protocol.amperage24
                            amperage5 = protocol.amperage25
                            amperage6 = protocol.amperage26
                            NUMBER_DATE_ATTESTATION = protocol.NUMBER_DATE_ATTESTATION
                            NAME_OF_OPERATION = protocol.NAME_OF_OPERATION
                            NUMBER_CONTROLLER = protocol.NUMBER_CONTROLLER
                            T1 = protocol.T7
                            T2 = protocol.T8
                            T3 = protocol.T9
                            T4 = protocol.T10
                            T5 = protocol.T11
                            T6 = protocol.T12
                            unixTimeStartProtocol1 = protocol.unixTimeStartProtocol21
                            unixTimeStartProtocol2 = protocol.unixTimeStartProtocol22
                            unixTimeStartProtocol3 = protocol.unixTimeStartProtocol23
                            unixTimeStartProtocol4 = protocol.unixTimeStartProtocol24
                            unixTimeStartProtocol5 = protocol.unixTimeStartProtocol25
                            unixTimeStartProtocol6 = protocol.unixTimeStartProtocol26
                            unixTimeWorkProtocol1 = protocol.unixTimeWorkProtocol21
                            unixTimeWorkProtocol2 = protocol.unixTimeWorkProtocol22
                            unixTimeWorkProtocol3 = protocol.unixTimeWorkProtocol23
                            unixTimeWorkProtocol4 = protocol.unixTimeWorkProtocol24
                            unixTimeWorkProtocol5 = protocol.unixTimeWorkProtocol25
                            unixTimeWorkProtocol6 = protocol.unixTimeWorkProtocol26
                            unixTimeEndProtocol1 = protocol.unixTimeEndProtocol21
                            unixTimeEndProtocol2 = protocol.unixTimeEndProtocol22
                            unixTimeEndProtocol3 = protocol.unixTimeEndProtocol23
                            unixTimeEndProtocol4 = protocol.unixTimeEndProtocol24
                            unixTimeEndProtocol5 = protocol.unixTimeEndProtocol25
                            unixTimeEndProtocol6 = protocol.unixTimeEndProtocol26
                        }
                    }
                    saveProtocolAsWorkbook(protocolRotorBlade)
                    Desktop.getDesktop().print(File("protocol1RotorBlade.xlsx"))
                }
                3 -> {
                    val protocolRotorBlade = transaction {
                        ProtocolRotorBlade.new {
                            date = protocol.date
                            time = protocol.time
                            dateEnd = protocol.dateEnd
                            timeEnd = protocol.timeEnd
                            cipher = protocol.cipher3
                            productName = protocol.productNumber3
                            operator = protocol.operator
                            temp1 = protocol.temp31
                            temp2 = protocol.temp32
                            temp3 = protocol.temp33
                            temp4 = protocol.temp34
                            temp5 = protocol.temp35
                            temp6 = protocol.temp36
                            voltage1 = protocol.voltage31
                            voltage2 = protocol.voltage32
                            voltage3 = protocol.voltage33
                            voltage4 = protocol.voltage34
                            voltage5 = protocol.voltage35
                            voltage6 = protocol.voltage36
                            amperage1 = protocol.amperage31
                            amperage2 = protocol.amperage32
                            amperage3 = protocol.amperage33
                            amperage4 = protocol.amperage34
                            amperage5 = protocol.amperage35
                            amperage6 = protocol.amperage36
                            NUMBER_DATE_ATTESTATION = protocol.NUMBER_DATE_ATTESTATION
                            NAME_OF_OPERATION = protocol.NAME_OF_OPERATION
                            NUMBER_CONTROLLER = protocol.NUMBER_CONTROLLER
                            T1 = protocol.T13
                            T2 = protocol.T14
                            T3 = protocol.T15
                            T4 = protocol.T16
                            T5 = protocol.T17
                            T6 = protocol.T18
                            unixTimeStartProtocol1 = protocol.unixTimeStartProtocol31
                            unixTimeStartProtocol2 = protocol.unixTimeStartProtocol32
                            unixTimeStartProtocol3 = protocol.unixTimeStartProtocol33
                            unixTimeStartProtocol4 = protocol.unixTimeStartProtocol34
                            unixTimeStartProtocol5 = protocol.unixTimeStartProtocol35
                            unixTimeStartProtocol6 = protocol.unixTimeStartProtocol36
                            unixTimeWorkProtocol1 = protocol.unixTimeWorkProtocol31
                            unixTimeWorkProtocol2 = protocol.unixTimeWorkProtocol32
                            unixTimeWorkProtocol3 = protocol.unixTimeWorkProtocol33
                            unixTimeWorkProtocol4 = protocol.unixTimeWorkProtocol34
                            unixTimeWorkProtocol5 = protocol.unixTimeWorkProtocol35
                            unixTimeWorkProtocol6 = protocol.unixTimeWorkProtocol36
                            unixTimeEndProtocol1 = protocol.unixTimeEndProtocol31
                            unixTimeEndProtocol2 = protocol.unixTimeEndProtocol32
                            unixTimeEndProtocol3 = protocol.unixTimeEndProtocol33
                            unixTimeEndProtocol4 = protocol.unixTimeEndProtocol34
                            unixTimeEndProtocol5 = protocol.unixTimeEndProtocol35
                            unixTimeEndProtocol6 = protocol.unixTimeEndProtocol36
                        }
                    }
                    saveProtocolAsWorkbook(protocolRotorBlade)
                    Desktop.getDesktop().print(File("protocol1RotorBlade.xlsx"))

                }
            }
            runLater {
                Toast.makeText("Началась печать протокола").show(Toast.ToastType.INFORMATION)
            }
        } else {
            runLater {
                errorNotification(
                    "Ошибка",
                    "Выберите из какого протокола сохранять",
                    Pos.BOTTOM_CENTER,
                    owner = this@ProtocolListWindow.currentWindow
                )
            }
        }
    }

}