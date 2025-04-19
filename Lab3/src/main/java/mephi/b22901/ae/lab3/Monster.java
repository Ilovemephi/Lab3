
package mephi.b22901.ae.lab3;


public class Monster {
    
    private String infoType;
    private String name;
    private String info;
    private int danger;
    private String residence;
    private String firstMention;
    private String vulnerability;
    private String height;
    private String weight;
    private String immunity;
    private String activityTime;
    private String recipe;
    private int time;
    private String efficiency;
    
    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public void setFirstMention(String firstMention) {
        this.firstMention = firstMention;
    }
    
    public void setVulnerability(String vulnerability) {
        this.vulnerability = vulnerability;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setImmunity(String immunity) {
        this.immunity = immunity;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
    
    public void setTime(int time) {
        this.time = time;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }
    
    public String getInfoType() { 
        return infoType; 
    }
    
    public String getName() { 
        return name; 
    }
    public String getInfo() { 
        return info; 
    }
    
    public int getDanger() { 
        return danger; 
    }
    
    public String getResidence() { 
        return residence; 
    }
    
    public String getFirstMention() { 
        return firstMention; 
    }
    
    public String getVulnerability() { 
        return vulnerability; 
    }
    
    public String getHeight() { 
        return height; 
    }
    
    public String getWeight() { 
        return weight; 
    }
    
    public String getImmunity() { 
        return immunity; 
    }
    
    public String getActivityTime() { 
        return activityTime; 
    }
    
    public String getRecipe() { 
        return recipe; 
    }
    
    public int getTime() { 
        return time; 
    }
    
    public String getEfficiency() { 
        return efficiency; 
    }
    
    @Override
    public String toString() {
        return "Monster{name='" + name + "', description='" + info + "', dangerLevel=" + danger +
                ", habitat='" + residence + "', firstMention='" + firstMention + "', magicVulnerability='" + vulnerability +
                "', height='" + height + "', weight='" + weight + "', immunities=" + immunity +
                ", activityTime='" + activityTime + "', poisonRecipe='" + recipe + "', preparationTime=" + time +
                ", effectiveness='" + efficiency + "', infoType='" + infoType + "'}";
    }
       
}
