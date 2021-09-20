package tr.beyazpolis.titaniumquest.type;

import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface QuestType {

  /**
   * @return Quest types.
   */
  public int getQuestTypes();

  /**
   * @return Finish quest amount.
   */
  public double getOverAmount();


  /**
   * @return Prize commands.
   */
  public Collection<String> getRewardsForCommand();

  /**
   *
   * @return Mob Name, Block name...
   */
  public List<String> getQuestMaterials();

  /**
   * @return quest name.
   */
  @NotNull
  public String getQuestName();
}

