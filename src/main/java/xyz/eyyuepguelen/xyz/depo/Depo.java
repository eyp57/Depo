package xyz.eyyuepguelen.xyz.depo;

import com.hakan.core.HCore;
import com.hakan.core.utils.yaml.HYaml;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.eyyuepguelen.xyz.depo.commands.DepoCMD;
import xyz.eyyuepguelen.xyz.depo.events.PlayerJoin;

import java.util.List;

public final class Depo extends JavaPlugin {
    private static HYaml data;
    private static Depo instance;
    private static Economy econ;
    @Override
    public void onEnable() {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&eDepo eklentisi yükleniyor..."));
        data = HYaml.create(this, "data.yml", "data.yml");
        instance = this;
        if(!setupEconomy()) {
            System.out.println(getDescription().getName() + " - Vault Ekonomi bulunamadı!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        HCore.registerCommands(new DepoCMD());
        saveDefaultConfig();
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&aDepo eklentisi yüklendi."));
    }

    @Override
    public void onDisable() {

    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static HYaml getData() {
        return data;
    }

    public static Depo getInstance() {
        return instance;
    }

    public void createPlayerData(Player player) {
        if(getData().get(player.getName()) == null) {
            getData().set(player.getName() + ".level", 0);
            getData().set(player.getName() + ".password", "-");
            getData().save();
        } return;
    }
    public void saveInventoryContents(Player player, Inventory inventory) {
        for(int i = 0 ; i < inventory.getSize() ; i++) {
            if(inventory.getItem(i) != null) {
                getData().set(player.getName() + ".contents." + i, inventory.getItem(i));
            }
            if(getData().get(player.getName() + ".contents." + i) != null && inventory.getItem(i) == null) {
                getData().set(player.getName() + ".contents." + i, null);
            }
            getData().save();
        }
    }
    public void getInventoryContents(Player player) {

    }
}
