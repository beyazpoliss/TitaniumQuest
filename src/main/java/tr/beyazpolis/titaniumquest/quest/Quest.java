package tr.beyazpolis.titaniumquest.quest;

import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.level.QuestLevel;

public interface Quest {

  /**
   * @return Quest name.
   */
  @NotNull
  public String getQuestName();

  /**
   * @return QuestTypes.
   */
  public int getQuestTypes();

  /**
   * @return QuestLevel.
   */
  @NotNull
  public QuestLevel getQuestLevel();




}
