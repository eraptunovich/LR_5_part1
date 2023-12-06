import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListManagementApp extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> mainList;
    private JList<String> secondList;
    private JCheckBox oddCheckBox;
    private JCheckBox evenCheckBox;

    public ListManagementApp(String title) {
        super(title);
        listModel = new DefaultListModel<>();
        mainList = new JList<>(listModel);
        secondList = new JList<>(new DefaultListModel<>());
        oddCheckBox = new JCheckBox("Выбрать нечетные строки");
        evenCheckBox = new JCheckBox("Выбрать четные строки");

        // Set layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components
        add(new JScrollPane(mainList));
        add(new JScrollPane(secondList));
        add(oddCheckBox);
        add(evenCheckBox);

        // Add buttons
        JButton updateButton = new JButton("Обновить");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLists();
            }
        });
        add(updateButton);

        JButton fillButton = new JButton("Заполнить первый список");
        fillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillMainList();
            }
        });
        add(fillButton);

        JButton clearButton = new JButton("Очистить второй список");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSecondList();
            }
        });
        add(clearButton);

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set frame properties
        setSize(400, 400);
        setLocationRelativeTo(null);

        // Initialize the main list
        fillMainList();
    }

    private void updateLists() {
        DefaultListModel<String> secondListModel = (DefaultListModel<String>) secondList.getModel();
        secondListModel.clear();

        if (oddCheckBox.isSelected() && evenCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Выберите только один флажок.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!oddCheckBox.isSelected() && !evenCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Выберите хотя бы один флажок.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = listModel.size() - 1; i >= 0; i--) {
            String item = listModel.getElementAt(i);

            if (oddCheckBox.isSelected() && i % 2 != 0) {
                // Remove odd items from the main list
                listModel.remove(i);
            }

            if (evenCheckBox.isSelected() && i % 2 != 0) {
                // Move even items to the second list
                secondListModel.addElement(item);
            }
        }

        if (secondListModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Второй список пуст.", "Информация", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void fillMainList() {
        listModel.clear();
        for (int i = 1; i <= 20; i++) {
            listModel.addElement("Строка " + i);
        }
    }

    private void clearSecondList() {
        DefaultListModel<String> secondListModel = (DefaultListModel<String>) secondList.getModel();
        secondListModel.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ListManagementApp app = new ListManagementApp("Управление списком");
                app.setVisible(true);
            }
        });
    }
}

