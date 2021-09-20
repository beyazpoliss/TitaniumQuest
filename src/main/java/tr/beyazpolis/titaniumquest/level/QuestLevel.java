package tr.beyazpolis.titaniumquest.level;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.TitaniumQuest;
import tr.beyazpolis.titaniumquest.type.QuestType;

public interface QuestLevel {

  /**
   * @return Quest amount.
   */
  public double getQuestAmount();

  /**
   * @param amount added.
   */
  public void addQuestAmount(final double amount);

  /**
   * @param amount removed.
   */
  public void removeQuestAmount(final double amount);

  /**
   * @param amount is set amount.
   */
  public void setQuestAmount(final double amount);

  /**
   * @return Returns true when the task is finished.
   */
  public boolean hasItOver(@NotNull final TitaniumQuest titaniumQuest, @NotNull final Player player);

  /**
   * @return QuestType object.
   */
  public QuestType getQuestType();

  /**
   * @param amount is bar width.
   * @return Progress Bar String.
   */
  @NotNull
  public String getProgressBar(final int amount,
                               final int max,
                               final int size,
                               final char barChar,
                               final String fillColor,
                               final String emptyColor);

}
