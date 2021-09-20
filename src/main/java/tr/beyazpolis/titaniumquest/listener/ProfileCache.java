package tr.beyazpolis.titaniumquest.listener;

import java.util.concurrent.CompletableFuture;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import tr.beyazpolis.titaniumquest.TitaniumQuest;
import tr.beyazpolis.titaniumquest.profile.DistanceProfile;
import tr.beyazpolis.titaniumquest.util.ItemCreator;

public class ProfileCache implements Listener {

  @NotNull
  private final TitaniumQuest titaniumQuest;

  public ProfileCache(@NotNull final TitaniumQuest titaniumQuest) {
    this.titaniumQuest = titaniumQuest;
  }

  @EventHandler
  public void onJoin(AsyncPlayerPreLoginEvent event){
    final var uuid = event.getUniqueId();
    titaniumQuest.getProfileManager().getOrCreateProfile(uuid);
  }

  @EventHandler
  public void onDisable(PlayerQuitEvent event){
    final var uuid = event.getPlayer().getUniqueId();
    CompletableFuture.runAsync(() -> titaniumQuest.getProfileManager().getDatabase().save(uuid));
  }

  @EventHandler
  public void onClick(InventoryClickEvent event){
    if (!(event.getWhoClicked() instanceof Player)) return;
    final var player = event.getWhoClicked();
    if (event.getClickedInventory() == null) return;
    if (event.getView().getTitle().equalsIgnoreCase(ItemCreator.color("&nQuests Menu.")))
      event.setCancelled(true);
  }

  @EventHandler
  public void onKillEntity(@NotNull final EntityDeathEvent event){
    if (event.isCancelled()) return;

    final var entity = event.getEntity();
    final var player = event.getEntity().getKiller();

    if (player == null) return;

    CompletableFuture.runAsync(() -> titaniumQuest
      .getProfileManager()
      .getOrCreateProfile(player.getUniqueId())
      .ifPresent(profile -> profile.all().forEach(quest -> {
        if (quest.getQuestTypes() != 1) return;
        for (final var materialNames : quest.getQuestLevel().getQuestType().getQuestMaterials()) {
          if (materialNames.toUpperCase().equalsIgnoreCase("ALL")) {
            quest.getQuestLevel().addQuestAmount(1);
            quest.getQuestLevel().hasItOver(titaniumQuest,player);
            return;
          }
          if (entity.getType().name().equalsIgnoreCase(materialNames.toUpperCase())) {
            quest.getQuestLevel().addQuestAmount(1);
            quest.getQuestLevel().hasItOver(titaniumQuest,player);
            return;
          }}
      })));
  }

  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    CompletableFuture.runAsync(() ->
      titaniumQuest.getProfileManager()
        .profiles()
        .forEach(profiles -> profiles
          .ifPresent(profile -> profile
            .all()
            .forEach(quest -> {
              if (quest.getQuestTypes() != 2) return;
              final var player = Bukkit.getPlayer(profile.getUUID());
              if (player == null) return;
              if (!player.isOnline()) return;

              if (profile.getDistanceProfile() == null)
                profile.setDistanceProfile(new DistanceProfile(player.getLocation(),player.getLocation()));

              profile.getDistanceProfile().setLocation(event.getFrom());
              profile.getDistanceProfile().setLastLocation(event.getTo());
            })
          )
        )
    );
  }

  @EventHandler
  public void onUseCommand(@NotNull final PlayerCommandPreprocessEvent event){
    if (event.isCancelled()) return;

    final var player = event.getPlayer();

    CompletableFuture.runAsync(() -> titaniumQuest
      .getProfileManager()
      .getOrCreateProfile(player.getUniqueId())
      .ifPresent(profile -> profile.all().forEach(quest -> {
        if (quest.getQuestTypes() != 3) return;

        for (final var materialNames : quest.getQuestLevel().getQuestType().getQuestMaterials()) {
          if (materialNames.toUpperCase().equalsIgnoreCase("ALL")) {
            quest.getQuestLevel().addQuestAmount(1);
            quest.getQuestLevel().hasItOver(titaniumQuest,player);
            return;
          }
          if (event.getMessage().equalsIgnoreCase(materialNames.toUpperCase())) {
            quest.getQuestLevel().addQuestAmount(1);
            quest.getQuestLevel().hasItOver(titaniumQuest,player);
            return;
          }}
      })));

  }

  @EventHandler
  public void onBreak(@NotNull final BlockBreakEvent event){
    if (event.isCancelled()) return;

    final var block = event.getBlock().getBlockData().getMaterial();
    final var uuid = event.getPlayer().getUniqueId();
    CompletableFuture.runAsync(() -> titaniumQuest
      .getProfileManager()
      .getOrCreateProfile(uuid)
      .ifPresent(profile -> profile.all().forEach(quest -> {
        if (quest.getQuestTypes() != 4) return;

        final var player = Bukkit.getPlayer(uuid);
        if (player == null) return;

        for (final var materialNames : quest.getQuestLevel().getQuestType().getQuestMaterials()) {
          if (materialNames.toUpperCase().equalsIgnoreCase("ALL")) {
            quest.getQuestLevel().addQuestAmount(1);
            quest.getQuestLevel().hasItOver(titaniumQuest,player);
            return;
          }
          if (Material.getMaterial(materialNames) == null) {
            continue;
          }
          if (block.equals(Material.getMaterial(materialNames))) {
            quest.getQuestLevel().addQuestAmount(1);
            quest.getQuestLevel().hasItOver(titaniumQuest,player);
            return;
          }
        }
      })));
  }

  @EventHandler
  public void onPlace(@NotNull final BlockPlaceEvent event){
    if (event.isCancelled()) return;

    final var block = event.getBlock().getBlockData().getMaterial();
    final var uuid = event.getPlayer().getUniqueId();

    CompletableFuture.runAsync(() -> titaniumQuest
      .getProfileManager()
      .getOrCreateProfile(uuid)
      .ifPresent(profile -> profile.all().forEach(quest -> {
        if (quest.getQuestTypes() != 5) return;
        final var player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        for (final var materialNames : quest.getQuestLevel().getQuestType().getQuestMaterials()) {
          if (materialNames.toUpperCase().equalsIgnoreCase("ALL")) {
            quest.getQuestLevel().addQuestAmount(1);
            quest.getQuestLevel().hasItOver(titaniumQuest,player);
            return;
          }
          if (Material.getMaterial(materialNames) == null) {
            continue;
          }
          if (block.equals(Material.getMaterial(materialNames))) {
            quest.getQuestLevel().addQuestAmount(1);
            quest.getQuestLevel().hasItOver(titaniumQuest,player);
            return;
          }
        }
      })));

  }

  @NotNull
  public TitaniumQuest getTitaniumQuest() {
    return titaniumQuest;
  }
}
