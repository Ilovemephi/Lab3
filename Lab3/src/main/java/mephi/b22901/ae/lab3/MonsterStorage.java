
package mephi.b22901.ae.lab3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonsterStorage {
    private List<Monster> monsters = new ArrayList<>();
    private Set<String> importedFormats = new HashSet<>();
    private Set<String> monsterID = new HashSet<>();    

    
    public void addMonsters(List<Monster> newMonsters) {
    for (Monster monster : newMonsters) {
        String id = monster.getName() + "|" + monster.getInfoType();
        if (!monsterID.contains(id)) {
            monsters.add(monster);
            monsterID.add(id);
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
                monsters.get(i).setInfo(updatedMonster.getInfo());
            }
        }
    }
    
    public List<Monster> getMonstersByInfoType(String infoType) {
        List<Monster> filteredMonsters = new ArrayList<>();
        for (Monster monster : monsters) {
            if (monster.getInfoType().equalsIgnoreCase(infoType)) {
                filteredMonsters.add(monster);
            }
        }
        return filteredMonsters;
    }

    
    public void clearStorage() {
        monsters.clear();
    }
}