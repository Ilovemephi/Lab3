
package mephi.b22901.ae.lab3;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Set;
import javax.swing.tree.DefaultTreeModel;

public class MainFrame extends JFrame {
    private DefaultMutableTreeNode root;
    private JTree monsterTree;
    private MonsterStorage monsterStorage;

    public MainFrame() {
        setTitle("Лабораторная работа №3");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        monsterStorage = new MonsterStorage();

        JPanel buttonPanel = new JPanel();
        JButton importButton = new JButton("Импортировать файлы");
        JButton exportButton = new JButton("Экспортировать файл");

        importButton.addActionListener(new ImportActionListener());
        exportButton.addActionListener(new ExportActionListener());

        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);

        root = new DefaultMutableTreeNode("Монстры");
        monsterTree = new JTree(root);
        monsterTree.addMouseListener(new TreeClickListener());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(monsterTree), BorderLayout.CENTER);

        add(mainPanel);
    }

    private class ImportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setMultiSelectionEnabled(true);
            int result = fileChooser.showOpenDialog(MainFrame.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();
                Handler handlerChain = createHandlerChain();
                for (File file : selectedFiles) {
                    try {
                        List<Monster> monsters = handlerChain.importData(file.getAbsolutePath());
                        monsterStorage.addMonsters(monsters);
                        updateTree(monsters);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage());
                    }
                }
            }
        }
    }

    private class ExportActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        List<Monster> allMonsters = monsterStorage.getAllMonsters();
        if (allMonsters.isEmpty()) {
            JOptionPane.showMessageDialog(
                MainFrame.this,
                "Нет данных для экспорта!",
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }


        String fileType = showFormatSelectionDialog();
        if (fileType == null) {
            return; 
        }

        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Сохранить файл как...");
        fileChooser.setSelectedFile(new File("monsters." + fileType.toLowerCase()));

        int result = fileChooser.showSaveDialog(MainFrame.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = ensureExtension(selectedFile.getAbsolutePath(), "." + fileType.toLowerCase());

            try {
                switch (fileType.toUpperCase()) {
                    case "JSON":
                        new JsonHandler().exportData(filePath, allMonsters);
                        break;
                    case "XML":
                        new XmlHandler().exportData(filePath, allMonsters);
                        break;
                    case "YAML":
                        new YamlHandler().exportData(filePath, allMonsters);
                        break;
                }

                JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Экспорт завершен! Файл сохранен: " + filePath,
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Ошибка при экспорте: " + ex.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}

    private class TreeClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { 
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) monsterTree.getLastSelectedPathComponent();
                if (node != null && node.getUserObject() instanceof Monster) {
                    Monster selectedMonster = (Monster) node.getUserObject();
                    new MonsterPanel(selectedMonster,  monsterStorage).setVisible(true);
                }
            }
        }
    }
    
    
    
    
    private String showFormatSelectionDialog() {
    Set<String> importedFormats = monsterStorage.getImportedFormats();
    
    String[] options = importedFormats.toArray(new String[0]);

    String selectedFormat = (String) JOptionPane.showInputDialog(
        this,
        "Выберите формат для экспорта:",
        "Экспорт данных",
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options.length > 0 ? options[0] : null
    );
    
    return selectedFormat;
}



    private Handler createHandlerChain() {
        Handler jsonHandler = new JsonHandler();
        Handler xmlHandler = new XmlHandler();
        Handler yamlHandler = new YamlHandler();

        jsonHandler.setNext(xmlHandler);
        xmlHandler.setNext(yamlHandler);

        return jsonHandler;
    }

    private void updateTree(List<Monster> monsters) {
        for (Monster monster : monsters) {
            String infoType = monster.getInfoType();
            DefaultMutableTreeNode typeNode = findOrCreateNode(root, infoType); 
            DefaultMutableTreeNode monsterNode = new DefaultMutableTreeNode(monster); 
            typeNode.add(monsterNode); 
        }

        ((DefaultTreeModel) monsterTree.getModel()).reload();
    }

    private DefaultMutableTreeNode findOrCreateNode(DefaultMutableTreeNode parentNode, String nodeName) {
        for (int i = 0; i < parentNode.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) parentNode.getChildAt(i);
            if (childNode.getUserObject().equals(nodeName)) {
                return childNode;
            }
        }

        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);
        parentNode.add(newNode);
        return newNode;
    }

    private String ensureExtension(String filePath, String extension) {
        if (!filePath.toLowerCase().endsWith(extension)) {
            return filePath + extension;
        }
        return filePath;
    }
}