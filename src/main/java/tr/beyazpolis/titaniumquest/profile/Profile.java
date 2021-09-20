package tr.beyazpolis.titaniumquest.profile;

import java.util.Collection;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.quest.Quest;

public interface Profile {

  /**
   * @return player uuid.
   */
  @NotNull
  public UUID getUUID();

  /**
   * @param quest is quest object to be added.
   */
  public void addQuest(@NotNull final Quest quest);

  /**
   *
   * @param name quest name that wants to be deleted.
   */
  public void removeQuest(@NotNull final String name);

  /**
   * @return all player quests.
   */
  public Collection<Quest> all();

  /**
   * @return all quest name.
   */
  public Collection<String> getAllQuestName();

  /**
   * @return DistanceProfile.
   */
  public DistanceProfile getDistanceProfile();

  /**
   * @param distanceProfile set the object.
   */
  public void setDistanceProfile(@NotNull final DistanceProfile distanceProfile);

}
