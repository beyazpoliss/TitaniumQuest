package tr.beyazpolis.titaniumquest.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.Optional;
import java.util.UUID;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.TitaniumQuest;
import tr.beyazpolis.titaniumquest.level.PlayerLevel;
import tr.beyazpolis.titaniumquest.profile.PlayerProfile;
import tr.beyazpolis.titaniumquest.profile.Profile;
import tr.beyazpolis.titaniumquest.quest.Quest;
import tr.beyazpolis.titaniumquest.quest.types.PlayerQuest;

public class MongoDB implements Database {

  private final MongoClient mongoClient;

  private final MongoDatabase database;

  private final MongoCollection<Document> collection;

  private final TitaniumQuest titaniumQuest;

  public MongoDB(@NotNull final TitaniumQuest titaniumQuest,  final MongoClient mongoClient, final MongoDatabase database) {
    this.titaniumQuest = titaniumQuest;
    this.mongoClient = mongoClient;
    this.database = database;
    this.collection = database.getCollection("PlayerProfiles");
  }

  @Override
  public Profile load(final @NotNull UUID uuid) {
    final var found =
      this.collection
        .find(Filters.eq("uuid", uuid.toString())).first();

    if (found == null) {
      System.out.println("returned empty");
      return null;
    }

    System.out.println("uuid bulundu gorevler yükleniyor #1");

    final var profile = new PlayerProfile(uuid);
    titaniumQuest.getQuestManager().all().forEach(questType -> {
      final var questLevel = new PlayerLevel(Optional.ofNullable(found.getDouble(questType.getQuestName())).orElse(0.0),questType);
      final Quest quest = new PlayerQuest(questType.getQuestName(), questType.getQuestTypes(), questLevel);
      System.out.println(quest.getQuestName());
      profile.addQuest(quest);
    });

    return profile;
  }

  @Override
  public void save(final @NotNull UUID uuid) {
    final var found = this.getCollection().find(Filters.eq("uuid", uuid.toString())).first();
    if (found == null){
      final var document = new Document("uuid",uuid.toString());
      titaniumQuest.getProfileManager()
        .getOrCreateProfile(uuid)
        .ifPresent(profile -> profile.all().forEach(quest -> {
          document.append(quest.getQuestName(),quest.getQuestLevel().getQuestAmount());
        }));
      this.getCollection().insertOne(document);
    } else {
      final var updateValue = new Document();
      titaniumQuest.getProfileManager()
        .getOrCreateProfile(uuid)
        .ifPresent(profile -> profile.all().forEach(quest -> {
          updateValue.append(quest.getQuestName(),quest.getQuestLevel().getQuestAmount());
        }));
      final var updater = new Document("$set",updateValue);
      this.getCollection().updateOne(found, updater);
    }
  }

  public MongoClient getMongoClient() {
    return mongoClient;
  }

  public MongoDatabase getDatabase() {
    return database;
  }

  public MongoCollection<Document> getCollection() {
    return collection;
  }

  public TitaniumQuest getTitaniumQuest() {
    return titaniumQuest;
  }
}
