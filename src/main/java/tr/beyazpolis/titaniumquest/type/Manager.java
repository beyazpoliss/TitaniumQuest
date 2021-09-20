package tr.beyazpolis.titaniumquest.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class Manager implements QuestManager {

  private final Map<String, QuestType> questTypeMap;

  public Manager() {
    this.questTypeMap = new HashMap<>();
  }

  @Override
  public Collection<QuestType> all() {
    return questTypeMap.values();
  }

  @Override
  public void add(final @NotNull QuestType questType) {
    var questName = questType.getQuestName();
    if (questTypeMap.get(questName) == null)
      questTypeMap.put(questType.getQuestName(), questType);
  }

  @Override
  public Optional<QuestType> getQuestByName(final @NotNull String name) {
    final var questType = questTypeMap.get(name);
    return Optional.ofNullable(questType);
  }
}
