
package mephi.b22901.ae.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonsterPanel extends JFrame {
    private Monster monster;

    public MonsterPanel(Monster monster) {
        this.monster = monster;
        setTitle("Детальная информация о монстре: " + monster.getName());
        setSize(500, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        // Поля для отображения информации
        JLabel nameLabel = new JLabel("Имя: " + monster.getName());
        JLabel dangerLabel = new JLabel("Уровень опасности: " + monster.getDanger());
        JLabel residenceLabel = new JLabel("Место обитания: " + monster.getResidence());
        JLabel firstMentionLabel = new JLabel("Первое упоминание: " + monster.getFirstMention());
        JLabel vulnerabilityLabel = new JLabel("Уязвимость к магии: " + monster.getVulnerability());
        JLabel heightLabel = new JLabel("Рост (м): " + monster.getHeight());
        JLabel weightLabel = new JLabel("Вес (кг): " + monster.getWeight());
        JLabel immunityLabel = new JLabel("Иммунитеты: " + monster.getImmunity());
        JLabel activityTimeLabel = new JLabel("Время активности: " + monster.getActivityTime());
        JLabel recipeLabel = new JLabel("Рецепт яда: " + monster.getRecipe());
        JLabel timeLabel = new JLabel("Время приготовления (мин): " + monster.getTime());
        JLabel efficiencyLabel = new JLabel("Эффективность: " + monster.getEfficiency());

        // Поле для редактирования описания
        JTextArea infoTextArea = new JTextArea(monster.getInfo(), 5, 20);
        JScrollPane infoScrollPane = new JScrollPane(infoTextArea);

        JButton saveButton = new JButton("Сохранить изменения");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newInfo = infoTextArea.getText();
                monster.setInfo(newInfo); // Обновляем описание монстра
                JOptionPane.showMessageDialog(MonsterPanel.this, "Изменения сохранены!");
            }
        });

        panel.add(nameLabel);
        panel.add(dangerLabel);
        panel.add(residenceLabel);
        panel.add(firstMentionLabel);
        panel.add(vulnerabilityLabel);
        panel.add(heightLabel);
        panel.add(weightLabel);
        panel.add(immunityLabel);
        panel.add(activityTimeLabel);
        panel.add(recipeLabel);
        panel.add(timeLabel);
        panel.add(efficiencyLabel);
        panel.add(new JLabel("Описание:"));
        panel.add(infoScrollPane);
        panel.add(saveButton);

        add(panel);
    }
}