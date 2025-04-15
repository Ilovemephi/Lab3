
package mephi.b22901.ae.lab3;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JsonHandler implements Handler {
    private Handler nextHandler;
    private List<Monster> monsters = new ArrayList<>();
    
    @Override
    public void setNext(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(String filePath) {
        if (filePath.endsWith(".json")){
            try {
                parseJsonFile(filePath);
            } catch (IOException ex) {
                 System.err.println("Ошибка при парсинге JSON-файла: " + ex.getMessage());
            }
        }
        else if (nextHandler != null){
            nextHandler.handle(filePath);
        }
        else {
            System.err.println("Файл не может быть обработан: " + filePath);
        }     
            
    }

    private void parseJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File(filePath);

        List<Monster> parsedMonsters = objectMapper.readValue(jsonFile, new TypeReference<List<Monster>>() {});

        for (Monster monster : parsedMonsters) {
            monster.setInfoType("JSON"); 
            monsters.add(monster);
        }

       
    }
    
    public List<Monster> getMonsters() {
        return monsters;
    }
    
    
    
}
