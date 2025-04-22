
package mephi.b22901.ae.lab3;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonHandler implements Handler {
    private Handler nextHandler;
    private MonsterStorage monsterStorage = new MonsterStorage();

    @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

   
    @Override
    public List<Monster> importData(String filePath) {
        
        List<Monster> monsters = new ArrayList<>();

        if (!filePath.toLowerCase().endsWith(".json")) {
            if (nextHandler != null) {
                return nextHandler.importData(filePath); 
            } else {
                throw new IllegalArgumentException("Формат файла не поддерживается: " + filePath);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File(filePath);

            
            Map<String, Object> jsonData = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});

      
            List<Map<String, Object>> parsedMonsters = (List<Map<String, Object>>) jsonData.get("Монстры");


            for (Map<String, Object> monsterData : parsedMonsters) {
                Monster monster = new Monster();
                monster.setInfoType("JSON");

                monster.setName((String) monsterData.get("Имя"));
                monster.setInfo((String) monsterData.get("Описание"));
                monster.setDanger(((Number) monsterData.get("Опасность")).intValue()); 
                monster.setResidence((String) monsterData.get("Обитание"));
                monster.setFirstMention((String) monsterData.get("ПервоеУпоминание"));
                monster.setVulnerability((String) monsterData.get("УязвимостьКМагии"));
                monster.setHeight((String) monsterData.get("РостМ")); 
                monster.setWeight((String) monsterData.get("ВесКг")); 
                monster.setImmunity((String) monsterData.get("Иммунитеты"));
                monster.setActivityTime((String) monsterData.get("ВремяАктивности"));
                monster.setRecipe((String) monsterData.get("РецептЯда"));
                monster.setTime(((Number) monsterData.get("ВремяПриготовленияМин")).intValue()); 
                monster.setEfficiency((String) monsterData.get("Эффективность"));

                monsters.add(monster);
            }
            
            monsterStorage.addMonsters(monsters);
            
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при импорте данных из JSON-файла: " + e.getMessage(), e);
        }

        return monsters;
    }

    
    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        
        if (!filePath.toLowerCase().endsWith(".json")) {
            if (nextHandler != null) {
                nextHandler.exportData(filePath, monsters); 
                return;
            } else {
                throw new IllegalArgumentException("Формат файла не поддерживается: " + filePath);
            }
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileWriter writer = new FileWriter(filePath)) {

            Map<String, Object> jsonData = Map.of("Монстры", monsters);


            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, jsonData);

            System.out.println("Данные успешно экспортированы в файл: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при экспорте данных в JSON-файл: " + e.getMessage(), e);
        }
    }
}