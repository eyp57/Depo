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
        String prefix = Depo.getInstance().getConfig().getString("Ayarlar.prefix");
        int level = Depo.getData().getInt(player.getName() + ".level");
        int ucret = Depo.getInstance().getConfig().getInt("Ayarlar.baslangicFiyat") * (level + 1) * Depo.getInstance().getConfig().getInt("Ayarlar.seviyeFiyatCarpmaOrani");
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
        levelUpItemLore.add("&8⌊ &7Ücret: &e" + (ucret));
        ItemStack levelUpItem = new HItemBuilder(XMaterial.FIREWORK_ROCKET.parseMaterial())
                .name("&eSeviye Yükselt")
                .lores(true, levelUpItemLore)
                .build();
        if(level < 1) {
            borderItem.setType(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial());
            this.fill(borderItem);
            super.setItem(13, levelUpItem, event -> {
                event.getWhoClicked().closeInventory();
                if(Depo.getEconomy().has(player, ucret)) {
                    Depo.getData().set(player.getName() + ".level", level + 1);
                    Depo.getEconomy().withdrawPlayer(player, ucret);
                    Depo.getData().save();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + Depo.getInstance().getConfig().getString("Dil.seviyeYukseltildi")));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + Depo.getInstance().getConfig().getString("Dil.yetersizBakiye").replaceAll("%ihtiyac%", String.valueOf(ucret - Depo.getEconomy().getBalance(player)))));
                }
            });
        } else if(level < 6) {
            this.fill(borderItem);
            super.setItem(14, levelUpItem, event -> {
                event.getWhoClicked().closeInventory();
                if(Depo.getEconomy().has(player, ucret)) {
                    Depo.getData().set(player.getName() + ".level", level + 1);
                    Depo.getEconomy().withdrawPlayer(player, ucret);
                    Depo.getData().save();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + Depo.getInstance().getConfig().getString("Dil.seviyeYukseltildi")));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + Depo.getInstance().getConfig().getString("Dil.yetersizBakiye").replaceAll("%ihtiyac%", String.valueOf(ucret - Depo.getEconomy().getBalance(player)))));
                }
            });
            List<String> openDepoItemLore = new LinkedList<>();
            openDepoItemLore.add("&8");
            openDepoItemLore.add("&8⌈ &eDepo Bilgisi:");
            openDepoItemLore.add("&8|");
            openDepoItemLore.add("&8| &7Sonraki Seviye: &e" + (level + 1)+ " Svy.");
            openDepoItemLore.add("&8|");
            openDepoItemLore.add("&8⌊ &eDeponu açmak için tıkla!");
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
            levelUpItemLore = new LinkedList<>();
            levelUpItemLore.add("&8");
            levelUpItemLore.add("&8⌈ &eDepo Yükseltme:");
            levelUpItemLore.add("&8|");
            levelUpItemLore.add("&8| &7Şuanki Seviye: &e" + level + " Svy.");
            levelUpItemLore.add("&8| &7Sonraki Seviye: &e&k" + (level + 1)+ " Svy.");
            levelUpItemLore.add("&8|");
            levelUpItemLore.add("&8⌊ &7Ücret: &e" + (ucret));
            levelUpItem = new HItemBuilder(XMaterial.FIREWORK_ROCKET.parseMaterial())
                    .name("&eSeviye Yükselt")
                    .lores(true, levelUpItemLore)
                    .build();
            super.setItem(14, levelUpItem, event -> {
                event.getWhoClicked().closeInventory();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + Depo.getInstance().getConfig().getString("Dil.zatenSonSeviye")));
            });
            List<String> openDepoItemLore = new LinkedList<>();
            openDepoItemLore.add("&8");
            openDepoItemLore.add("&8⌈ &eDepo Bilgisi:");
            openDepoItemLore.add("&8|");
            openDepoItemLore.add("&8| &7Seviye: &e" + (level) + " Svy.");
            openDepoItemLore.add("&8|");
            openDepoItemLore.add("&8⌊ &eDeponu açmak için tıkla!");
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
