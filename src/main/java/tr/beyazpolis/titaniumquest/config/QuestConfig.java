package tr.beyazpolis.titaniumquest.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import tr.beyazpolis.titaniumquest.type.DefaultType;
import tr.beyazpolis.titaniumquest.type.QuestType;

public class QuestConfig extends Config{

  public QuestConfig(final Plugin plugin, final String ymlName) {
    super(plugin, ymlName);
  }

  public void startConfig(){
    this.loadYML();
    if (!isSet("Database.mongodb")){
      this.set("Database.mongodb", true);
      this.set("Database.mongodb.password_link", "");
      this.set("Database.mongodb.db_name", "PlayerProfiles");
    }

    if (!isSet("Quests")){
      this.set("Quests.Mining.type",4);
      this.set("Quests.Mining.questName","Miner");

      this.set("Quests.Mining.settings.amount", 30);

      this.set("Quests.Mining.settings.questMaterials", Arrays.asList(
        "STONE",
        "COAL_ORE",
        "EMERALD_ORE"));

      this.set("Quests.Mining.settings.rewards", Arrays.asList(
        "/give %questPlayer% emerald 8",
        "/give %questPlayer% diamond 8",
        "/give %questPlayer% coal 16"));

      //

      this.set("Quests.Commander.type",3);
      this.set("Quests.Commander.questName","Commander");

      this.set("Quests.Commander.settings.amount", 3);

      this.set("Quests.Commander.settings.questMaterials", Arrays.asList(
        "/quests",
        "/test"));

      this.set("Quests.Commander.settings.rewards", Collections.singletonList(
        "/give %questPlayer% barrier"));

      //

      this.set("Quests.Killer.type",1);
      this.set("Quests.Killer.questName","Killer");

      this.set("Quests.Killer.settings.amount", 10);

      this.set("Quests.Killer.settings.questMaterials", Arrays.asList(
        "CREEPER",
        "COW"));

      this.set("Quests.Killer.settings.rewards", Collections.singletonList(
        "/give %questPlayer% barrier"));

      //

      this.set("Quests.Builder.type",5);
      this.set("Quests.Builder.questName","Builder");

      this.set("Quests.Builder.settings.amount", 10);

      this.set("Quests.Builder.settings.questMaterials", Arrays.asList(
        "GRASS",
        "COAL_ORE"));

      this.set("Quests.Builder.settings.rewards", Collections.singletonList(
        "/give %questPlayer% barrier"));

      //

      this.set("Quests.Runner.type",2);
      this.set("Quests.Runner.questName","Runner");

      this.set("Quests.Runner.settings.amount", 30);

      this.set("Quests.Runner.settings.questMaterials", Collections.singletonList(
        "ALL"));

      this.set("Quests.Runner.settings.rewards", Collections.singletonList(
        "/give %questPlayer% barrier"));

    }

    this.saveYML();
  }

  public Collection<QuestType> getQuests(){
     HashMap<String, QuestType> questTypes = new HashMap<>();
     for (String name : this.getKeys("Quests",false)){
       System.out.println(name);
       final var type = this.getConfiguration().getInt("Quests." + name + ".type");
       final var questName = this.getConfiguration().getString("Quests." + name + ".questName");

       final var amount = this.getConfiguration().getDouble("Quests." + name + ".settings.amount");
       final var rewards = this.getConfiguration().getStringList("Quests." + name + ".settings.rewards");
       final var materials = this.getConfiguration().getStringList("Quests." + name + ".settings.questMaterials");

       final QuestType questType = new DefaultType(type,amount,rewards,materials,questName);
       final String qName = questType.getQuestName();

       if (questTypes.get(qName) != null){
         continue;
       }
       questTypes.put(qName,questType);
     }
     return questTypes.values();
  }
}
