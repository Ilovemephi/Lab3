
package mephi.b22901.ae.lab3;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;

public class YamlHandler implements Handler {
    private Handler nextHandler;
    private MonsterStorage monsterStorage = new MonsterStorage();

    @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public List<Monster> importData(String filePath) {
        List<Monster> monsters = new ArrayList<>();
        if (!filePath.toLowerCase().endsWith(".yaml")) {
            if (nextHandler != null) {
                return nextHandler.importData(filePath);
            } else {
                throw new IllegalArgumentException("Формат файла не поддерживается");
            }
        }

        Yaml yaml = new Yaml();
        try (FileInputStream fis = new FileInputStream(filePath)) {

            Map<String, Object> yamlData = yaml.load(fis);


            List<Map<String, Object>> parsedMonsters = (List<Map<String, Object>>) yamlData.get("Монстры");


            for (Map<String, Object> monsterData : parsedMonsters) {
                Monster monster = new Monster();
                monster.setInfoType("YAML");

                monster.setName((String) monsterData.get("Имя"));
                monster.setInfo((String) monsterData.get("Описание"));
                monster.setDanger((Integer) monsterData.get("Опасность"));
                monster.setResidence((String) monsterData.get("Обитание"));
                monster.setFirstMention((String) monsterData.get("ПервоеУпоминание").toString());
                monster.setVulnerability((String) monsterData.get("УязвимостьКМагии"));
                monster.setHeight((String) monsterData.get("РостМ")); 
                monster.setWeight((String) monsterData.get("ВесКг")); 
                monster.setImmunity((String) monsterData.get("Иммунитеты"));
                monster.setActivityTime((String) monsterData.get("ВремяАктивности"));
                monster.setRecipe((String) monsterData.get("РецептЯда"));
                monster.setTime((Integer) monsterData.get("ВремяПриготовленияМин"));
                monster.setEfficiency((String) monsterData.get("Эффективность"));

                monsters.add(monster);
            }
            
            monsterStorage.addMonsters(monsters);
            
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге YAML-файла: " + e.getMessage());
        }

        return monsters;
    }

    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        
        if (!filePath.toLowerCase().endsWith(".yaml")) {
            if (nextHandler != null) {
                nextHandler.exportData(filePath, monsters); 
                return;
            } else {
                throw new IllegalArgumentException("Формат файла не поддерживается: " + filePath);
            }
        }
        
        Yaml yaml = createYamlWithBlockStyle();
        try (FileWriter writer = new FileWriter(filePath)) {
            List<Map<String, Object>> yamlMonsters = new ArrayList<>();

            for (Monster monster : monsters) {
                Map<String, Object> yamlMonster = new LinkedHashMap<>();
                yamlMonster.put("Имя", monster.getName());
                yamlMonster.put("Описание", monster.getInfo());
                yamlMonster.put("Опасность", monster.getDanger());
                yamlMonster.put("Обитание", monster.getResidence());
                yamlMonster.put("ПервоеУпоминание", monster.getFirstMention());
                yamlMonster.put("УязвимостьКМагии", monster.getVulnerability());
                yamlMonster.put("РостМ", monster.getHeight());
                yamlMonster.put("ВесКг", monster.getWeight());
                yamlMonster.put("Иммунитеты", monster.getImmunity());
                yamlMonster.put("ВремяАктивности", monster.getActivityTime());
                yamlMonster.put("РецептЯда", monster.getRecipe());
                yamlMonster.put("ВремяПриготовленияМин", monster.getTime());
                yamlMonster.put("Эффективность", monster.getEfficiency());

                yamlMonsters.add(yamlMonster);
            }
            
            Map<String, Object> yamlData = new LinkedHashMap<>();
            yamlData.put("Монстры", yamlMonsters);
            


            yaml.dump(yamlData, writer);

            System.out.println("Данные успешно экспортированы в файл: " + filePath);
        } catch (Exception e) {
            System.err.println("Ошибка при экспорте данных в YAML: " + e.getMessage());
        }
    }
    
    
    private Yaml createYamlWithBlockStyle() {
    DumperOptions options = new DumperOptions();
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); 
    options.setPrettyFlow(true); 
    return new Yaml(options);
    }
}