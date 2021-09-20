package tr.beyazpolis.titaniumquest.cmd;

import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.TitaniumQuest;
import tr.beyazpolis.titaniumquest.type.QuestTypes;
import tr.beyazpolis.titaniumquest.util.ItemCreator;

public class QuestCommand implements CommandExecutor {

  @NotNull
  private final TitaniumQuest titaniumQuest;

  public QuestCommand(@NotNull final TitaniumQuest titaniumQuest) {
    this.titaniumQuest = titaniumQuest;
  }

  @Override
  public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final @NotNull String[] args) {
   if (!(sender instanceof Player)){
     return true;
   }

   final var inventory = Bukkit.createInventory(null,9*3, ItemCreator.color("&nQuests Menu."));
   final var player = (Player) sender;

   titaniumQuest.getProfileManager()
     .getOrCreateProfile(player.getUniqueId())
     .ifPresent(profile -> profile.all().forEach(quest -> {
       final var stack = new ItemCreator(getMaterialByTypes(quest.getQuestTypes()))
         .setName(quest.getQuestName())
         .visibleEnchant()
         .setLore(
           "",
           " &aQuest Type: &e" + QuestTypes.getQuestTypeByLevel(quest.getQuestTypes()),
           "",
           "  &aProgress Bar:",
           " " +
           quest.getQuestLevel()
             .getProgressBar
               ((int) quest.getQuestLevel().getQuestAmount(),
               (int) quest.getQuestLevel().getQuestType().getOverAmount(),
               40,
               '|',
               "§e",
               "§8"),
           "  &7[&e" + quest.getQuestLevel().getQuestAmount() + " &7/" + "&e" + quest.getQuestLevel().getQuestType().getOverAmount() + "&7]",
           "")
         .toItemStack();
       inventory.addItem(stack);
   }));
   player.openInventory(inventory);
   return true;
  }

  @NotNull
  public TitaniumQuest getTitaniumQuest() {
    return titaniumQuest;
  }

  private Material getMaterialByTypes(final int types){
    switch (types){
      case 1:
        return Material.GOLDEN_SWORD;
      case 2:
        return Material.LEATHER_BOOTS;
      case 3:
        return Material.WRITTEN_BOOK;
      case 4:
        return Material.NETHERITE_PICKAXE;
      case 5:
        return Material.GRASS;
    }
    return Material.BARRIER;
  }
}
