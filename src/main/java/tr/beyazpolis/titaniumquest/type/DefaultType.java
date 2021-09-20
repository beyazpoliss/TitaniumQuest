package tr.beyazpolis.titaniumquest.type;

import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class DefaultType implements QuestType {

  private final int questType;
  private final double overAmount;

  private final Collection<String> rewardCommands;
  private final List<String> questMaterials;

  private final String questName;

  public DefaultType(final int questType,
                     final double overAmount,
                     final Collection<String> rewardCommands,
                     final List<String> questMaterials,
                     final String questName) {
    this.questType = questType;
    this.overAmount = overAmount;
    this.rewardCommands = rewardCommands;
    this.questMaterials = questMaterials;
    this.questName = questName;
  }

  @Override
  public int getQuestTypes() {
    return questType;
  }

  @Override
  public double getOverAmount() {
    return overAmount;
  }

  @Override
  public Collection<String> getRewardsForCommand() {
    return rewardCommands;
  }

  @Override
  public List<String> getQuestMaterials() {
    return questMaterials;
  }

  @Override
  public @NotNull String getQuestName() {
    return questName;
  }
}
