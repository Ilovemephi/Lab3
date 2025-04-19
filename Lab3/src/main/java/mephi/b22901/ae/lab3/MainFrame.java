
package mephi.b22901.ae.lab3;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
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

        // Создаем панель для кнопок
        JPanel buttonPanel = new JPanel();
        JButton importButton = new JButton("Импортировать файлы");
        JButton exportButton = new JButton("Экспортировать файлы");

        importButton.addActionListener(new ImportActionListener());
        exportButton.addActionListener(new ExportActionListener());

        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);

        // Создаем дерево монстров
        root = new DefaultMutableTreeNode("Монстры");
        monsterTree = new JTree(root);
        monsterTree.addMouseListener(new TreeClickListener());

        // Добавляем компоненты на главную панель
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(monsterTree), BorderLayout.CENTER);

        add(mainPanel);
    }

    private class ImportActionListener implements java.awt.event.ActionListener {
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

    private class ExportActionListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showSaveDialog(MainFrame.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File directory = fileChooser.getSelectedFile();
                Handler handlerChain = createHandlerChain();
                List<Monster> allMonsters = monsterStorage.getAllMonsters();
                handlerChain.exportData(directory.getAbsolutePath() + "/monsters.json", allMonsters);
                handlerChain.exportData(directory.getAbsolutePath() + "/monsters.xml", allMonsters);
                handlerChain.exportData(directory.getAbsolutePath() + "/monsters.yaml", allMonsters);
                JOptionPane.showMessageDialog(MainFrame.this, "Экспорт завершен!");
            }
        }
    }

    private class TreeClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { // Двойной клик
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) monsterTree.getLastSelectedPathComponent();
                if (node != null && node.getUserObject() instanceof Monster) {
                    Monster selectedMonster = (Monster) node.getUserObject();
                    new MonsterPanel(selectedMonster).setVisible(true);
                }
            }
        }
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
            DefaultMutableTreeNode monsterNode = new DefaultMutableTreeNode(monster);
            root.add(monsterNode);
        }
        ((DefaultTreeModel) monsterTree.getModel()).reload();
    }

   
}
