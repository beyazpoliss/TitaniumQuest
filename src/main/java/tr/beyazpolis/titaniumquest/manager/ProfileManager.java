package tr.beyazpolis.titaniumquest.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.TitaniumQuest;
import tr.beyazpolis.titaniumquest.database.Database;
import tr.beyazpolis.titaniumquest.level.PlayerLevel;
import tr.beyazpolis.titaniumquest.profile.PlayerProfile;
import tr.beyazpolis.titaniumquest.profile.Profile;
import tr.beyazpolis.titaniumquest.quest.types.PlayerQuest;

public class ProfileManager {

  @NotNull
  private final HashMap<UUID, Optional<Profile>> profiles;

  @NotNull
  private final Database database;

  @NotNull
  private final TitaniumQuest titaniumQuest;

  public ProfileManager(@NotNull final TitaniumQuest titaniumQuest, @NotNull final Database database) {
    this.profiles = new HashMap<>();
    this.database = database;
    this.titaniumQuest = titaniumQuest;
  }

  @NotNull
  public Optional<Profile> getOrCreateProfile(@NotNull final UUID uuid){
    return Optional.ofNullable(profiles.get(uuid)).orElseGet(() -> {
      final var profile = this.getOrCreate(uuid);
      profiles.put(uuid, profile);
      return profile;
    });
  }

  private Optional<Profile> getOrCreate(@NotNull final UUID uuid) {
    return Optional.of(Optional.ofNullable(database.load(uuid)).orElseGet(() -> {
      final var profile = new PlayerProfile(uuid);
      titaniumQuest.getQuestManager().all().forEach(questType -> {
        final var questLevel = new PlayerLevel(0.0,questType);
        final var quest = new PlayerQuest(questType.getQuestName(), questType.getQuestTypes(), questLevel);
        profile.addQuest(quest);
      });
      return profile;
    }));
  }

  public void saveAll(){
    CompletableFuture.runAsync(() -> profiles.forEach((uuid, profile) -> database.save(uuid)));
  }

  public Collection<Optional<Profile>> profiles(){
    return profiles.values();
  }

  @NotNull
  public TitaniumQuest getTitaniumQuest() {
    return titaniumQuest;
  }

  @NotNull
  public Database getDatabase() {
    return database;
  }
}
