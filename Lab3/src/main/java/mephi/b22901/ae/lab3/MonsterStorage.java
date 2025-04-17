
package mephi.b22901.ae.lab3;

import java.util.ArrayList;
import java.util.List;


public class MonsterStorage {
    
    private List<Monster> YAMLMonsters = new ArrayList<>();
    private List<Monster> XMLMonsters = new ArrayList<>();
    private List<Monster> JSONMonsters = new ArrayList<>();
    
    public void addYAMLMonsters (List<Monster> monsters){
        for (Monster monster : monsters){
            YAMLMonsters.add(monster);
        }
    }
    
    public void addXMLMonsters (List<Monster> monsters){
        for (Monster monster : monsters){
            XMLMonsters.add(monster);
        }

    }
    
    public void addJSONMonsters (List<Monster> monsters){
        for (Monster monster : monsters){
            JSONMonsters.add(monster);
        }
    }
    
    public List<Monster> getMonsters() {
        return XMLMonsters;
    }
    
    
    public boolean updateMonster(String name, Monster updatedMonster) {
    for (List<Monster> monsterList : List.of(YAMLMonsters, XMLMonsters, JSONMonsters)) {
        for (Monster monster : monsterList) {
            if (monster.getName().equals(name)) {
                monster.setInfo(updatedMonster.getInfo());
                monster.setDanger(updatedMonster.getDanger());
                monster.setResidence(updatedMonster.getResidence());
                monster.setFirstMention(updatedMonster.getFirstMention());
                monster.setVulnerability(updatedMonster.getVulnerability());
                monster.setHeight(updatedMonster.getHeight());
                monster.setWeight(updatedMonster.getWeight());
                monster.setImmunity(updatedMonster.getImmunity());
                monster.setActivityTime(updatedMonster.getActivityTime());
                monster.setRecipe(updatedMonster.getRecipe());
                monster.setTime(updatedMonster.getTime());
                monster.setEfficiency(updatedMonster.getEfficiency());
                return true; 
            }
        }
    }
    return false; 
    }
    
    
}
