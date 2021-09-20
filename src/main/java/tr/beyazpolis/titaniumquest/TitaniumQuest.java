package tr.beyazpolis.titaniumquest;

import java.util.Objects;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.beyazpolis.titaniumquest.cmd.QuestCommand;
import tr.beyazpolis.titaniumquest.config.QuestConfig;
import tr.beyazpolis.titaniumquest.listener.ProfileCache;
import tr.beyazpolis.titaniumquest.manager.DatabaseManager;
import tr.beyazpolis.titaniumquest.manager.ProfileManager;
import tr.beyazpolis.titaniumquest.type.Manager;
import tr.beyazpolis.titaniumquest.type.QuestManager;

/**
 * @Author BeyazPolis#1044
 * @since 20.09.2021
 */
public class TitaniumQuest extends JavaPlugin {

  private static TitaniumQuest INSTANCE;

  @Nullable
  private QuestConfig config;

  @Nullable
  private DatabaseManager databaseManager;

  @Nullable
  private QuestManager questManager;

  @Nullable
  private ProfileManager profileManager;

  private String passString;

  @Override
  public void onEnable() {
    // Plugin startup logic
    INSTANCE = null;

    this.config = new QuestConfig(this, "quests.yml");
    this.questManager = new Manager();

    config.startConfig();
    config.getQuests().forEach(questType -> questManager.add(questType));

    this.passString = config.getConfiguration().getString("Database.mongodb.password_link");
    this.databaseManager = new DatabaseManager(this, Optional.ofNullable(passString).orElseThrow(() -> new NullPointerException("MongoDB password link is null")));
    this.profileManager = new ProfileManager(this, databaseManager.getDatabase());

    Bukkit.getPluginManager().registerEvents(new ProfileCache(this), this);
    Objects.requireNonNull(this.getCommand("quests")).setExecutor(new QuestCommand(this));

    Bukkit.getScheduler().runTaskTimerAsynchronously(this, () ->
      getProfileManager()
        .profiles()
        .forEach(profiles -> profiles
          .ifPresent(profile -> {
            profile.all().forEach(quest -> {
              if (quest.getQuestTypes() != 2) return;
              final var player = Bukkit.getPlayer(profile.getUUID());

              if (player == null) return;
              if (!player.isOnline()) return;

              if (profile.getDistanceProfile() == null) return;
              if (profile.getDistanceProfile().isMoved()) {
                quest.getQuestLevel().addQuestAmount(1);
                quest.getQuestLevel().hasItOver(getInstance(), player);
              }
            });
          })), 5, 5);
  }

  private TitaniumQuest getInstance() {
    return this;
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    this.getProfileManager().saveAll();
  }

  public ProfileManager getProfileManager() {
    return Optional.ofNullable(profileManager).orElseGet(() -> profileManager = new ProfileManager(this,this.getDatabaseManager().getDatabase()));
  }

  @NotNull
  public QuestManager getQuestManager() {
    return Optional.ofNullable(questManager).orElseGet(() -> questManager = new Manager());
  }

  @NotNull
  public DatabaseManager getDatabaseManager() {
    return Optional.ofNullable(databaseManager).orElseGet(() -> databaseManager = new DatabaseManager(this,passString));
  }

  @NotNull
  public QuestConfig getQuestConfig() {
    return Optional.ofNullable(config).orElse(config = new QuestConfig(this,"quests.yml"));
  }

  @Nullable
  public static TitaniumQuest getINSTANCE() {
    return INSTANCE;
  }
}
