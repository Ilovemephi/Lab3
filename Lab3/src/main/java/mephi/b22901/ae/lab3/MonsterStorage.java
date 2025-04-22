
package mephi.b22901.ae.lab3;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonsterStorage {
    private List<Monster> monsters = new ArrayList<>();
    private Set<String> importedFormats = new HashSet<>();
    private Set<String> monsterNames = new HashSet<>();    

    
    public void addMonsters(List<Monster> newMonsters) {
    for (Monster monster : newMonsters) {
        if (!monsterNames.contains(monster.getName())) {
            monsters.add(monster);
            monsterNames.add(monster.getName());
            importedFormats.add(monster.getInfoType()); 
        }
    }
}

    
    public List<Monster> getAllMonsters() {
        return new ArrayList<>(monsters); 
    }
    
    public Set<String> getImportedFormats() {
        return importedFormats; 
    }

    
    public Monster findMonsterByName(String name) {
        for (Monster monster : monsters) {
            if (monster.getName().equals(name)) {
                return monster;
            }
        }
        return null; 
    }
    
    
    public void updateMonster(Monster updatedMonster) {
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getName().equals(updatedMonster.getName())) {
                monsters.set(i, updatedMonster);
                break;
            }
        }
    }

    
    public void clearStorage() {
        monsters.clear();
    }
}