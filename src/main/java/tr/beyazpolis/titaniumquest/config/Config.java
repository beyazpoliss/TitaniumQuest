package tr.beyazpolis.titaniumquest.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

  private final Plugin plugin;

  private final File file;
  private final File folder;

  private FileConfiguration configuration;

  public Config(final Plugin plugin, final String ymlName) {
    this.plugin = plugin;
    this.folder = new File(this.plugin.getDataFolder().getPath());
    this.file = new File(this.plugin.getDataFolder().getPath(), ymlName);
  }

  private void createFile(){
    if (!folder.exists()){
      try {
        folder.mkdirs();
        folder.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void loadYML() {
    createFile();
    if (!file.exists()){
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.configuration = YamlConfiguration.loadConfiguration(file);
  }

  public List<String> getKeys(final String path, final boolean deep){
    if (!isSet(path)) return new ArrayList<>();
    return new ArrayList<>(configuration.getConfigurationSection(path).getKeys(deep));
  }

  public void saveYML(){

    try {
      this.configuration.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isSet(final String path){
    return configuration.isSet(path);
  }

  public void set(final String path,final Object o){
    configuration.set(path,o);
  }

  public void setIfNotExists(final String path,final Object o){
    if (!isSet(path)){
      configuration.set(path, o);
    }
  }

  public FileConfiguration getConfiguration() {
    return configuration;
  }

  public Plugin getPlugin() {
    return plugin;
  }

}
