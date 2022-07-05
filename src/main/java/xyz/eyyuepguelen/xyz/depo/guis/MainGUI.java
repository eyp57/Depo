package xyz.eyyuepguelen.xyz.depo.guis;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.hakan.core.item.HItemBuilder;
import com.hakan.core.ui.inventory.HInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import xyz.eyyuepguelen.xyz.depo.Depo;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class MainGUI extends HInventory {
    public MainGUI(@Nonnull String id, @Nonnull String title, int size, @Nonnull InventoryType type) {
        super(id, title, size, type);
    }

    @Override
    protected void onOpen(@Nonnull Player player) {
        int i = 0;
        int level = Depo.getData().getInt(player.getName() + ".level");
        ItemStack borderItem = new HItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial())
                .glow(true)
                .name("&c")
                .build();
        List<String> levelUpItemLore = new LinkedList<>();
        levelUpItemLore.add("&8");
        levelUpItemLore.add("&8⌈ &eDepo Yükseltme:");
        levelUpItemLore.add("&8|");
        levelUpItemLore.add("&8| &7Şuanki Seviye: &e" + level + " Svy.");
        levelUpItemLore.add("&8| &7Sonraki Seviye: &e" + (level + 1)+ " Svy.");
        levelUpItemLore.add("&8|");
        levelUpItemLore.add("&8⌊ &7Ücret: &e" + (1500 * level * 2));
        ItemStack levelUpItem = new HItemBuilder(XMaterial.FIREWORK_ROCKET.parseMaterial())
                .name("&eSeviye Yükselt")
                .lores(true, levelUpItemLore)
                .build();
        if(level < 1) {
            borderItem.setType(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial());
            this.fill(borderItem);
            super.setItem(13, levelUpItem, event -> {
                event.getWhoClicked().closeInventory();
                if(Depo.getEconomy().has(player, 1500 * level * 2)) {
                    Depo.getData().set(player.getName() + ".level", level + 1);
                    Depo.getEconomy().withdrawPlayer(player, 1500 * level *2);
                    Depo.getData().save();
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&nDepo&8 » &e" + ((1500*level*2) - Depo.getEconomy().getBalance(player)) + " &cBakiyeye ihtiyacınız var."));
                }
            });
        } else if(level < 6) {
            this.fill(borderItem);
            super.setItem(14, levelUpItem, event -> {
                event.getWhoClicked().closeInventory();
                if(Depo.getEconomy().has(player, 1500 * level * 2)) {
                    Depo.getData().set(player.getName() + ".level", level + 1);
                    Depo.getEconomy().withdrawPlayer(player, 1500 * level *2);
                    Depo.getData().save();
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&nDepo&8 » &e" + ((1500*level*2) - Depo.getEconomy().getBalance(player)) + " &cBakiyeye ihtiyacınız var."));
                }
            });
            List<String> openDepoItemLore = new LinkedList<>();
            levelUpItemLore.add("&8");
            levelUpItemLore.add("&8⌈ &eDepo Bilgisi:");
            levelUpItemLore.add("&8|");
            levelUpItemLore.add("&8| &7Seviye: &e" + (level)+ " Svy.");
            levelUpItemLore.add("&8|");
            levelUpItemLore.add("&8⌊ &eDeponu açmak için tıkla!");
            ItemStack openDepoItem = new HItemBuilder(XMaterial.CHEST.parseMaterial())
                    .name("&eDepoyu aç")
                    .lores(true, openDepoItemLore)
                    .build();
            super.setItem(12, openDepoItem, event -> {
                HInventory depo = new DepoGUI("0", ChatColor.translateAlternateColorCodes('&', "&cDepo"), level, InventoryType.CHEST);
                player.closeInventory();
                depo.open(player);
            });
        } else {
            this.fill(borderItem);
            super.setItem(14, levelUpItem, event -> {
                event.getWhoClicked().closeInventory();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6nDepo&8 » &bZaten Son seviyeye ulaştınız."));
            });
            List<String> openDepoItemLore = new LinkedList<>();
            levelUpItemLore.add("&8");
            levelUpItemLore.add("&8⌈ &eDepo Bilgisi:");
            levelUpItemLore.add("&8|");
            levelUpItemLore.add("&8| &7Seviye: &e" + (level)+ " Svy.");
            levelUpItemLore.add("&8|");
            levelUpItemLore.add("&8⌊ &eDeponu açmak için tıkla!");
            ItemStack openDepoItem = new HItemBuilder(XMaterial.CHEST.parseMaterial())
                    .name("&eDepoyu aç")
                    .lores(true, openDepoItemLore)
                    .build();
            super.setItem(12, openDepoItem, event -> {
                HInventory depo = new DepoGUI("0", ChatColor.translateAlternateColorCodes('&', "&cDepo"), level, InventoryType.CHEST);
                player.closeInventory();
                depo.open(player);
            });
        }
    }
}
