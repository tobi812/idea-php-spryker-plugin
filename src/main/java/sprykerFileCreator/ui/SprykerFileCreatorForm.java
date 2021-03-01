package pav.sprykerFileCreator.ui;

import com.intellij.openapi.project.Project;
import pav.sprykerFileCreator.model.generator.SprykerConstants;
import pav.sprykerFileCreator.ui.handler.FormHandlerInterface;
import pav.sprykerFileCreator.ui.validator.FormValidatorInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.xml.bind.ValidationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SprykerFileCreatorForm extends JFrame {
    private JPanel contentPane;
    private JComboBox selectApplicationBox;
    private JButton generateFiles;
    private JButton cancelButton;
    private JTextField bundleTextField;
    private JLabel bundleLabel;
    private JTable fileSelectionTable;
    private JLabel validationError;
    private List<ClassSelectionItem> classSelectionItems = new ArrayList<ClassSelectionItem>();
    private FormValidatorInterface formValidator;
    private FormHandlerInterface formHandler;

    public SprykerFileCreatorForm(
            Project project,
            FormValidatorInterface formValidator,
            FormHandlerInterface formHandler
    ) {
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        setTitle("Select Spryker Application");
        validationError.setVisible(false);
        this.formHandler = formHandler;
        this.formValidator = formValidator;

        String[] items = new String[] {"Yves", "Zed", "Client"};

        for ( String item : items) {
            selectApplicationBox.addItem(item);
        }

        selectApplicationBox.setSelectedItem("Yves");

        classSelectionItems.add(new ClassSelectionItem(true, "Controller", false, SprykerConstants.YVES_CONTROLLER));
        classSelectionItems.add(new ClassSelectionItem(true, "Factory", false, SprykerConstants.YVES_FACTORY));

        fileSelectionTable.setModel(new ClassSelectionModel(classSelectionItems));
        fileSelectionTable.setOpaque(false);
        fileSelectionTable.setCellSelectionEnabled(false);
        fileSelectionTable.setRowSelectionAllowed(false);
        fileSelectionTable.setColumnSelectionAllowed(false);
        fileSelectionTable.setGridColor(new Color(0,0,0,0));
        fileSelectionTable.setFocusable(false);

        generateFiles.setFocusPainted(true);
        generateFiles.setDefaultCapable(true);

        contentPane.getRootPane().setDefaultButton(generateFiles);




        ((DefaultTableCellRenderer)fileSelectionTable.getDefaultRenderer(Object.class)).setOpaque(false);



        selectApplicationBox.addActionListener(e -> {

            Object selectedApplication = selectApplicationBox.getSelectedItem();

            if (selectedApplication == "Zed") {
                classSelectionItems.clear();

                classSelectionItems.add(new ClassSelectionItem(true, "Controller", false, SprykerConstants.ZED_CONTROLLER));
                classSelectionItems.add(new ClassSelectionItem(true, "CommunicationFactory", false, SprykerConstants.ZED_COMMUNICATION_FACTORY));

                fileSelectionTable.setModel(new ClassSelectionModel(classSelectionItems));
            }

            if (selectedApplication == "Yves") {
                classSelectionItems.clear();

                classSelectionItems.add(new ClassSelectionItem(true, "Controller", true, SprykerConstants.YVES_CONTROLLER));
                classSelectionItems.add(new ClassSelectionItem(true, "Factory", false, SprykerConstants.YVES_FACTORY));

                fileSelectionTable.setModel(new ClassSelectionModel(classSelectionItems));
            }

        });

        generateFiles.addActionListener(e -> {
            this.handleForm(project);
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

        pack();
        setLocationRelativeTo(null);

    }

    private void handleForm(Project project) {
        try {
            this.formValidator.validate(this);
            this.formHandler.handle(this);
            dispose();
        } catch (ValidationException exception) {
            validationError.setText(exception.getMessage());
            validationError.setVisible(true);
        }
    }

    private void showErrorDialog(String message) {

    }

    public String getBundleName() {
        return bundleTextField.getText();
    }

    public String getSelectedApplication() {
        return selectApplicationBox.getSelectedItem().toString();
    }

    public List<ClassSelectionItem> getClassSelectionItems() {
        return classSelectionItems;
    }

    private void createUIComponents() {
        contentPane = new JPanel();
        bundleLabel = new JLabel("Define bundle");
    }
}
