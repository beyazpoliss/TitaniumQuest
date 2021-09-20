package tr.beyazpolis.titaniumquest.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class ItemCreator {

  private final ItemStack item;

  public ItemCreator(final ItemStack item) {
    this.item = item;
  }

  public ItemCreator(final Material mt)  {
    this(mt, 1);
  }

  public ItemCreator(final Material mt, final int amount)  {
    item = new ItemStack(mt, amount);
  }

  public ItemCreator setEnchant(Enchantment enchantment, int level) {
    final ItemMeta meta = item.getItemMeta();
    if (level <= 0) meta.removeEnchant(enchantment);
    else meta.addEnchant(enchantment, level, true);
    item.setItemMeta(meta);
    return this;
  }

  public ItemCreator visibleEnchant(){
    final var meta = item.getItemMeta();
    meta.addEnchant(Enchantment.DURABILITY,1,true);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    item.setItemMeta(meta);
    return this;
  }

  public ItemCreator setName(String name) {
    final ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(color(name));
    item.setItemMeta(meta);
    return this;
  }

  public ItemCreator setLore(String... lore) {
    final ItemMeta meta = item.getItemMeta();
    List<String> loreList = new ArrayList<>();
    for (String str : lore){
      loreList.add(color(str));
    }
    meta.setLore(loreList);
    item.setItemMeta(meta);
    return this;
  }

  public ItemCreator setLore(String lore) {
    final ItemMeta meta = item.getItemMeta();
    List<String> loreList = new ArrayList<>();
    loreList.add(color(lore));
    meta.setLore(loreList);
    item.setItemMeta(meta);
    return this;
  }

  public ItemCreator setLore(List<String> list){
    final ItemMeta meta = item.getItemMeta();
    final List<String> colored_lore = new ArrayList<>();
    for (String lore:list) {
      colored_lore.add(color(lore));
    }
    meta.setLore(colored_lore);
    item.setItemMeta(meta);
    return this;
  }

  public ItemStack toItemStack() {
    return item.clone();
  }

  public static String color(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }
}
