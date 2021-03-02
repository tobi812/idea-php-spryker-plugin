package com.github.tobi812.sprykerplugin.ui

import com.github.tobi812.sprykerplugin.config.SprykerPluginConfig
import com.github.tobi812.sprykerplugin.ui.handler.FormHandlerInterface
import com.github.tobi812.sprykerplugin.ui.validator.FormValidatorInterface
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.table.JBTable
import java.awt.Color
import java.awt.event.ActionListener
import java.util.ArrayList
import javax.swing.*
import javax.xml.bind.ValidationException

class SprykerFileCreatorForm(
    val project: Project,
    formValidator: FormValidatorInterface,
    formHandler: FormHandlerInterface
) : JFrame() {
    private var contentPane: JPanel = JPanel()
    private val selectApplicationBox: JComboBox<*> = ComboBox<String>()
    private val generateFiles: JButton = JButton()
    private val cancelButton: JButton = JButton()
    private val bundleTextField: JTextField = JBTextField()
    private var bundleLabel: JLabel = JLabel()
    private val fileSelectionTable: JTable = JBTable()
    private val validationError: JLabel = JBLabel()
    private val classSelectionItems: MutableList<ClassSelectionItem> = ArrayList()
    private val formValidator: FormValidatorInterface
    private val formHandler: FormHandlerInterface

    init {
        this.contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10))
        this.setContentPane(this.contentPane)
        this.setTitle("Select Spryker Application")
        this.validationError.setVisible(false)
        val appNames = SprykerPluginConfig.getInstance(this.project).appNames

        for (app in appNames) {
            this.selectApplicationBox.addItem(app)
        }

        selectApplicationBox.setSelectedItem("Yves")
        classSelectionItems.add(ClassSelectionItem(true, "Controller", false, SprykerConstants.YVES_CONTROLLER))
        classSelectionItems.add(ClassSelectionItem(true, "Factory", false, SprykerConstants.YVES_FACTORY))
        fileSelectionTable.setModel(ClassSelectionModel(classSelectionItems))
        fileSelectionTable.setOpaque(false)
        fileSelectionTable.setCellSelectionEnabled(false)
        fileSelectionTable.setRowSelectionAllowed(false)
        fileSelectionTable.setColumnSelectionAllowed(false)
        fileSelectionTable.setGridColor(Color(0, 0, 0, 0))
        fileSelectionTable.setFocusable(false)
        generateFiles.setFocusPainted(true)
        generateFiles.setDefaultCapable(true)
        contentPane.getRootPane().setDefaultButton(generateFiles)
        (fileSelectionTable.getDefaultRenderer(Any::class.java) as DefaultTableCellRenderer).setOpaque(false)
        selectApplicationBox.addActionListener(ActionListener { e: ActionEvent? ->
            val selectedApplication: Any = selectApplicationBox.getSelectedItem()
            if (selectedApplication === "Zed") {
                classSelectionItems.clear()
                classSelectionItems.add(ClassSelectionItem(true, "Controller", false, SprykerConstants.ZED_CONTROLLER))
                classSelectionItems.add(
                    ClassSelectionItem(
                        true,
                        "CommunicationFactory",
                        false,
                        SprykerConstants.ZED_COMMUNICATION_FACTORY
                    )
                )
                fileSelectionTable.setModel(ClassSelectionModel(classSelectionItems))
            }
            if (selectedApplication === "Yves") {
                classSelectionItems.clear()
                classSelectionItems.add(ClassSelectionItem(true, "Controller", true, SprykerConstants.YVES_CONTROLLER))
                classSelectionItems.add(ClassSelectionItem(true, "Factory", false, SprykerConstants.YVES_FACTORY))
                fileSelectionTable.setModel(ClassSelectionModel(classSelectionItems))
            }
        })
        generateFiles.addActionListener(ActionListener { e: ActionEvent? -> handleForm(project) })
        cancelButton.addActionListener(ActionListener { e: ActionEvent? -> dispose() })
        pack()
        setLocationRelativeTo(null)
    }
    private fun handleForm(project: Project) {
        try {
            formValidator.validate(this)
            formHandler.handle(this)
            dispose()
        } catch (exception: ValidationException) {
            this.validationError.setText(exception.message)
            this.validationError.setVisible(true)
        }
    }

    private fun showErrorDialog(message: String) {}
    val bundleName: String
        get() = bundleTextField.getText()
    val selectedApplication: String
        get() = selectApplicationBox.getSelectedItem().toString()

    fun getClassSelectionItems(): List<ClassSelectionItem> {
        return classSelectionItems
    }

    private fun createUIComponents() {
        contentPane = JPanel()
        bundleLabel = JLabel("Define bundle")
    }
}