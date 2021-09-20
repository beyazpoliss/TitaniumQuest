package tr.beyazpolis.titaniumquest.type;

public enum QuestTypes {

  KILL_MOBS(1,"Kill Mobs"),
  RUN_DISTANCE(2,"Run Distance"),
  USE_COMMAND(3,"Use Command"),
  BREAK_BLOCK(4,"Break Blocks"),
  PLACE_BLOCK(5,"Place Block");

  private final int level;
  private final String name;

  private QuestTypes(final int level,final String name) {
    this.level = level;
    this.name = name;
  }

  public static String getQuestTypeByLevel(final int level){
    for (var type : values()){
      if (type.getLevel() == level)
        return type.getName();
    }
    return "&4Quest Not found.";
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }
}
