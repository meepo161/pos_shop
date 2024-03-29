package ru.avem.posshop.view

import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.posshop.controllers.MainViewController
import ru.avem.posshop.database.entities.User
import ru.avem.posshop.database.entities.Users
import ru.avem.posshop.database.entities.Users.fullName
import tornadofx.*
import tornadofx.controlsfx.confirmNotification
import tornadofx.controlsfx.warningNotification
import java.awt.Desktop
import java.nio.file.Paths
import kotlin.system.exitProcess

class AuthorizationView : View("Авторизация") {
    private var loginProperty = SimpleStringProperty("")
    private val passwordProperty = SimpleStringProperty("")

    lateinit var users: List<User>

    var comboboxUser: ComboBox<User> by singleAssign()
    val mainController: MainViewController by inject()

    private var img: ImageView by singleAssign()

    override fun onDock() {
//        modalStage!!.fullScreenExitKeyCombination = KeyCombination.NO_MATCH
//        modalStage!!.isFullScreen = true
//        modalStage!!.isMaximized = true
//        modalStage!!.isResizable = false
        comboboxUser.items = transaction {
            User.all().toList().asObservable()
        }
    }

    override val root = anchorpane {
        img = imageview(Image("splash.png"))
        prefWidth = 1200.0
        prefHeight = 700.0

        vbox(spacing = 16.0) {
            anchorpaneConstraints {
                topAnchor = 0.0
                bottomAnchor = 0.0
                leftAnchor = 0.0
                rightAnchor = 0.0
            }

            alignmentProperty().set(Pos.CENTER)

            hbox(spacing = 24.0) {
                alignmentProperty().set(Pos.CENTER)
                comboboxUser = combobox {
                    alignment = Pos.CENTER
                    promptText = "ФИО"
                    prefWidth = 400.0
                }
            }

            hbox(spacing = 16.0) {
                alignmentProperty().set(Pos.CENTER)
                passwordfield {
                    alignment = Pos.CENTER
                    prefWidth = 400.0

                    onTouchReleased = EventHandler {
                        Desktop.getDesktop()
                            .open(Paths.get("C:/Program Files/Common Files/Microsoft Shared/ink/TabTip.exe").toFile())
                        requestFocus()
                    }
                    promptText = "Пароль"
                }.bind(passwordProperty)
            }

            button("Вход") {
                isDefaultButton = true
                anchorpaneConstraints {
                    leftAnchor = 16.0
                    rightAnchor = 16.0
                    topAnchor = 270.0
                }

                onAction = EventHandler {
                    loginProperty = SimpleStringProperty(comboboxUser.selectedItem?.fullName)
                    if (loginProperty.value.isNullOrEmpty() || passwordProperty.value.isNullOrEmpty()) {
                        warningNotification(
                            "Пустой логин или пароль",
                            "Заполните все поля",
                            Pos.BOTTOM_CENTER,
                            hideAfter = 3.seconds
                        )
                        return@EventHandler
                    }
                    transaction {
                        users = User.find {
                            (fullName eq loginProperty.value) and (Users.password eq passwordProperty.value)
                        }.toList()
                        if (users.isEmpty()) {
                            warningNotification(
                                "Неправильный логин или пароль", "Проверьте данные для входа и повторите снова.",
                                Pos.BOTTOM_CENTER, hideAfter = 3.seconds
                            )
                        } else {
                            mainController.position1 = loginProperty.value
                            confirmNotification(
                                "Авторизация",
                                "Вы вошли как: ${loginProperty.value}",
                                Pos.BOTTOM_CENTER
                            )
                            replaceWith<MainView>()
                        }
                    }
                }
            }.addClass(Styles.stopStart)

            button("Выход") {
                action {
                    exitProcess(0)
                }
            }.addClass(Styles.stopStart)
        }
    }.addClass(Styles.hard, Styles.blueTheme)
}
