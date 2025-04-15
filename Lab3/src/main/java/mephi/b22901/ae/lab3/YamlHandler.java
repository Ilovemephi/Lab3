
package mephi.b22901.ae.lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;


public class YamlHandler implements Handler {
    private Handler nextHandler;
    private List<Monster> monsters = new ArrayList<>();


     @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(String filePath) {
        if (filePath.endsWith(".yaml")){
            try {
                parseYamlFile(filePath);
            } catch (IOException ex) {
                System.err.println("Ошибка при парсинге Yaml-файла: " + ex.getMessage());
            }
        }
        else if (nextHandler != null){
            nextHandler.handle(filePath);
        }
        else {
            System.err.println("Файл не может быть обработан: " + filePath);
        }     
            
    }

    private void parseYamlFile(String filePath) throws IOException {
        Yaml yaml = new Yaml();
        try (FileInputStream fis = new FileInputStream(filePath)){
        Map<String, Object> yamlData = yaml.load(fis);
        List<Map<String, Object>> parsedMonsters = (List<Map<String, Object>>) yamlData.get("Монстры");
        for (Map<String, Object> monsterData : parsedMonsters) {
            Monster monster = new Monster();
            monster.setInfoType("YAML");

            if (monsterData.containsKey("Имя")) {
                monster.setName((String) monsterData.get("Имя"));
            }
            if (monsterData.containsKey("Описание")) {
                monster.setInfo((String) monsterData.get("Описание"));
            }
            if (monsterData.containsKey("Опасность")) {
                monster.setDanger(((Integer) monsterData.get("Опасность")));
            }
            if (monsterData.containsKey("Обитание")) {
                monster.setResidence((String) monsterData.get("Обитание"));
            }
            if (monsterData.containsKey("ПервоеУпоминание")) {
                monster.setFirstMention((String) monsterData.get("ПервоеУпоминание"));
            }
            if (monsterData.containsKey("УязвимостькМагии")) {
                monster.setVulnerability((String) monsterData.get("УязвимостькМагии"));
            }
            if (monsterData.containsKey("РостМ")) {
                monster.setHeight(((Integer) monsterData.get("РостМ")));
            }
            if (monsterData.containsKey("ВесКг")) {
                monster.setWeight(((Integer) monsterData.get("ВесКг")));
            }
            if (monsterData.containsKey("Иммунитеты")) {
                monster.setImmunity((String) monsterData.get("Иммунитеты"));
            }
            if (monsterData.containsKey("ВремяАктивности")) {
                monster.setActivityTime((String) monsterData.get("ВремяАктивности"));
            }
            if (monsterData.containsKey("РецептЯда")) {
                monster.setRecipe((String) monsterData.get("РецептЯда"));
            }
            if (monsterData.containsKey("ВремяПриготовленияМин")) {
                monster.setTime(((Integer) monsterData.get("ВремяПриготовленияМин")));
            }
            if (monsterData.containsKey("Эффективность")) {
                monster.setEfficiency((String) monsterData.get("Эффективность"));
            }

            monsters.add(monster);
        }
         
    }
    }
    
    public List<Monster> getMonsters() {
        return monsters;
    }
    
}
