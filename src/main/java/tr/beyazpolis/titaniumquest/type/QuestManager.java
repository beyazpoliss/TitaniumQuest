package tr.beyazpolis.titaniumquest.type;

import java.util.Collection;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface QuestManager {

  /**
   * @param name quest name.
   * @return QuestType.
   */
  @Nullable
  public Optional<QuestType> getQuestByName(@NotNull final String name);

  /**
   * @return all quests.
   */
  public Collection<QuestType> all();

  /**
   * @param questType added quest object.
   */
  public void add(@NotNull final QuestType questType);


}

