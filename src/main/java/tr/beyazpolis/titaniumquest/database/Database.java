package tr.beyazpolis.titaniumquest.database;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.profile.Profile;

public interface Database {

  public Profile load(@NotNull final UUID uuid);

  public void save(@NotNull final UUID uuid);

}
