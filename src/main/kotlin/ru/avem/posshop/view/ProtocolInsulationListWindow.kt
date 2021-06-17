package ru.avem.posshop.view

import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.stage.FileChooser
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.posshop.database.entities.ProtocolInsulation
import ru.avem.posshop.database.entities.ProtocolsTable
import ru.avem.posshop.database.entities.ProtocolsTableInsulation
import ru.avem.posshop.protocol.saveProtocolAsWorkbook
import ru.avem.posshop.utils.Singleton
import ru.avem.posshop.utils.Toast
import ru.avem.posshop.utils.callKeyBoard
import tornadofx.*
import tornadofx.controlsfx.confirmNotification
import java.awt.Desktop
import java.io.File

class ProtocolInsulationListWindow : View("Протоколы графиков") {
    private var tableViewProtocols: TableView<ProtocolInsulation> by singleAssign()
    private lateinit var protocols: ObservableList<ProtocolInsulation>
    override fun onDock() {
        protocols = transaction {
            ProtocolInsulation.all().toList().asObservable()
        }
        tableViewProtocols.items = protocols
    }


    override val root = anchorpane {
        prefWidth = 900.0
        prefHeight = 500.0

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
                        tableViewProtocols.items = protocols.filter { it.date.contains(text) }.asObservable()
                    } else {
                        tableViewProtocols.items = protocols
                    }
                }
            }

            tableViewProtocols = tableview {
                protocols = transaction {
                    ProtocolInsulation.all().toList().asObservable()
                }
                items = protocols
                prefHeight = 700.0
                columnResizePolicyProperty().set(TableView.CONSTRAINED_RESIZE_POLICY)
                column("Дата", ProtocolInsulation::date)
                column("Время", ProtocolInsulation::time)
            }

            hbox(spacing = 16.0) {
                alignmentProperty().set(Pos.CENTER)

                button("Печать") {
                    action {
                        if (tableViewProtocols.selectedItem != null) {
                            Singleton.currentProtocolInsulation = transaction {
                                ProtocolInsulation.find {
                                    ProtocolsTableInsulation.id eq tableViewProtocols.selectedItem!!.id
                                }.toList().asObservable()
                            }.first()
                            saveProtocolAsWorkbook(Singleton.currentProtocolInsulation)
                            close()
//                            openFile(File("protocolInsulation.xlsx"))
                            Desktop.getDesktop().print(File("protocolInsulation.xlsx"))
                        }
                        runLater {
                            Toast.makeText("Началась печать протокола").show(Toast.ToastType.INFORMATION)
                        }
                    }
                }
//                button("Открыть таблицу") {
//                    action {
//                        if (tableViewProtocols.selectedItem != null) {
//                            Singleton.currentProtocolInsulation = transaction {
//                                ProtocolInsulation.find {
//                                    ProtocolsTableInsulation.id eq tableViewProtocols.selectedItem!!.id
//                                }.toList().asObservable()
//                            }.first()
//
//                            find<GraphHistoryWindow>().openModal(
//                                modality = Modality.WINDOW_MODAL, escapeClosesWindow = true,
//                                resizable = true, owner = this@ProtocolInsulationListWindow.currentWindow
//                            )
//                        }
//                    }
//                }
                button("Сохранить как") {
                    action {
                        if (tableViewProtocols.selectedItem != null) {
                            val files = chooseFile(
                                "Выберите директорию для сохранения",
                                arrayOf(FileChooser.ExtensionFilter("XSLX Files (*.xlsx)", "*.xlsx")),
                                FileChooserMode.Save,
                                this@ProtocolInsulationListWindow.currentWindow
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
                                        owner = this@ProtocolInsulationListWindow.currentWindow
                                    )
                                }
                            }
                        }
                    }
                }
                button("Сохранить все") {
                    action {
                        if (tableViewProtocols.items.size > 0) {
                            val dir = chooseDirectory(
                                "Выберите директорию для сохранения",
                                File(System.getProperty("user.home")),
                                this@ProtocolInsulationListWindow.currentWindow
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
                                        owner = this@ProtocolInsulationListWindow.currentWindow
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
                                ProtocolInsulation.all().toList().asObservable()
                            }
                        }
                    }
                }
            }
        }
    }.addClass(Styles.hard)
}
