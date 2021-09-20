package tr.beyazpolis.titaniumquest.profile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr.beyazpolis.titaniumquest.quest.Quest;

public class PlayerProfile implements Profile{

  @NotNull
  private final UUID uuid;

  @Nullable
  private DistanceProfile distanceProfile;

  private final Map<String, Quest> questMap;

  public PlayerProfile(@NotNull final UUID uuid) {
    this.uuid = uuid;
    this.questMap = new HashMap<>();
    this.distanceProfile = null;
  }

  @Override
  public void addQuest(final @NotNull Quest quest) {
    questMap.put(quest.getQuestName(),quest);
  }

  @Override
  public void removeQuest(final @NotNull String name) {
    questMap.remove(name);
  }

  @Override
  public @NotNull Collection<Quest> all() {
    return questMap.values();
  }

  @Override
  public @NotNull UUID getUUID() {
    return uuid;
  }

  @Override
  public Collection<String> getAllQuestName() {
    return questMap.keySet();
  }

  @Override
  public void setDistanceProfile(@NotNull final DistanceProfile distanceProfile){
    this.distanceProfile = distanceProfile;
  }

  @Override
  @Nullable
  public DistanceProfile getDistanceProfile() {
    return distanceProfile;
  }
}
