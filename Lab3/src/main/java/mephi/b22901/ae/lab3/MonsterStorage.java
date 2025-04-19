
package mephi.b22901.ae.lab3;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

public class MonsterStorage {
    private List<Monster> monsters = new ArrayList<>();

    
    public void addMonsters(List<Monster> newMonsters) {
        monsters.addAll(newMonsters);
    }

    
    public List<Monster> getAllMonsters() {
        return new ArrayList<>(monsters); 
    }
    
    public Monster findMonsterByName(String name) {
        for (Monster monster : monsters) {
            if (monster.getName().equals(name)) {
                return monster;
            }
        }
        return null; 
    }

    
    public void clearStorage() {
        monsters.clear();
    }
}