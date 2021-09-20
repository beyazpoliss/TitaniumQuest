package tr.beyazpolis.titaniumquest.manager;

import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.TitaniumQuest;
import tr.beyazpolis.titaniumquest.database.Database;
import tr.beyazpolis.titaniumquest.database.MongoDB;

public class DatabaseManager {

  @NotNull
  private final Database database;

  @NotNull
  private final String passString;

  @NotNull
  private final TitaniumQuest titaniumQuest;

  public DatabaseManager(@NotNull final TitaniumQuest titaniumQuest, @NotNull final String passString) {
    this.titaniumQuest = titaniumQuest;
    this.passString = passString;
    final var mongoClient = MongoClients.create(passString);
    final var base = mongoClient.getDatabase("PlayerProfile");
    this.database = new MongoDB(titaniumQuest,mongoClient,base);
  }

  @NotNull
  public Database getDatabase() {
    return database;
  }

  @NotNull
  public String getPassString() {
    return passString;
  }

  @NotNull
  public TitaniumQuest getTitaniumQuest() {
    return titaniumQuest;
  }
}
