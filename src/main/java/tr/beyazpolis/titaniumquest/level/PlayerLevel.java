package tr.beyazpolis.titaniumquest.level;

import com.google.common.base.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.TitaniumQuest;
import tr.beyazpolis.titaniumquest.type.QuestType;
import tr.beyazpolis.titaniumquest.util.ItemCreator;

public class PlayerLevel implements QuestLevel {

  private double amount;
  private final QuestType questType;

  public PlayerLevel(final double amount,
                     final QuestType questType) {
    this.amount = amount;
    this.questType = questType;
  }

  @Override
  public double getQuestAmount() {
    return amount;
  }

  @Override
  public void addQuestAmount(final double amount) {
    this.amount += amount;
  }

  @Override
  public void removeQuestAmount(final double amount) {
    this.amount -= amount;
  }

  @Override
  public void setQuestAmount(final double amount) {
    this.amount = amount;
  }

  @Override
  public boolean hasItOver(@NotNull final TitaniumQuest titaniumQuest, @NotNull final Player player) {
    if (this.amount >= this.getQuestType().getOverAmount()) {
      this.setQuestAmount(0);
      this.getQuestType().getRewardsForCommand().forEach(command -> {
        final var replaceString = command.replace("%questPlayer%", player.getName());
        Bukkit.getScheduler().runTask(titaniumQuest, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaceString));
      });
      Bukkit.broadcastMessage(ItemCreator.color("&a" + player.getName() + " successfully completed the quest named " + getQuestType().getQuestName() + "."));
      player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER,5,1.5F);
      return true;
    }
    return false;
  }

  @Override
  public QuestType getQuestType() {
    return questType;
  }

  @Override
  public @NotNull String getProgressBar(final int amount,final int max,final int size,final char barChar, final String fillColor, final String emptyColor) {
    return bar(amount, max, size, barChar,fillColor,emptyColor);
  }

  private String bar(final double current, final double max, final int size, final char barChar, final String fillColor, final String emptyColor) {
    final double ratio = Math.max(0, Math.min(current / max, 1));
    final int filledBarAmount = (int) (size * ratio);
    final int emptyBarAmount = size - filledBarAmount;
    return (filledBarAmount > 0 ? fillColor : "") + Strings.repeat(String.valueOf(barChar), filledBarAmount) + (emptyBarAmount > 0 ? emptyColor : "") + Strings.repeat(String.valueOf(barChar), emptyBarAmount);
  }
}
