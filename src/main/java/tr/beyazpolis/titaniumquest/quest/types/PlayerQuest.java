package tr.beyazpolis.titaniumquest.quest.types;

import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.quest.Quest;
import tr.beyazpolis.titaniumquest.level.QuestLevel;

public class PlayerQuest implements Quest {

  @NotNull
  private final String questName;

  private final int questType;

  @NotNull
  private final QuestLevel questLevel;

  public PlayerQuest(@NotNull final String questName,
                     final int questType,
                     @NotNull final QuestLevel questLevel) {
    this.questName = questName;
    this.questType = questType;
    this.questLevel = questLevel;
  }

  @Override
  @NotNull
  public String getQuestName() {
    return questName;
  }

  @Override
  public int getQuestTypes() {
    return questType;
  }

  @Override
  public @NotNull QuestLevel getQuestLevel() {
    return questLevel;
  }
}
