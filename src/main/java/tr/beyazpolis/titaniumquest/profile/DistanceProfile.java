package tr.beyazpolis.titaniumquest.profile;

import org.bukkit.Location;

public class DistanceProfile {

  private Location location;
  private Location lastLocation;

  public DistanceProfile(final Location location, final Location lastLocation) {
    this.location = location;
    this.lastLocation = lastLocation;
  }

  public Location getLocation() {
    return location;
  }

  public Location getLastLocation() {
    return lastLocation;
  }

  public void setLocation(final Location location) {
    this.location = location;
  }

  public void setLastLocation(final Location lastLocation) {
    this.lastLocation = lastLocation;
  }

  public boolean isMoved(){
    return location.getBlock().getX() != lastLocation.getBlock().getX()
      || location.getBlock().getY() != lastLocation.getBlock().getY()
      || location.getBlock().getZ() != lastLocation.getBlock().getZ();
  }
}
