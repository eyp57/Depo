package xyz.eyyuepguelen.xyz.depo.guis;

import com.hakan.core.ui.inventory.HInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.eyyuepguelen.xyz.depo.Depo;

import javax.annotation.Nonnull;

public class DepoGUI extends HInventory {
    public DepoGUI(@Nonnull String id, @Nonnull String title, int size, @Nonnull InventoryType type) {
        super(id, title, size, type);
        super.removeOption(Option.CANCEL_TOP_CLICK);
        super.removeOption(Option.CANCEL_DOWN_CLICK);
    }

    @Override
    protected void onOpen(@Nonnull Player player) {
        Inventory inventory = this.toInventory();
        /*for(int i = 0 ; i < inventory.getSize() ; i++) {
            if(Depo.getData().get(player.getName() + ".contents." + i) != null) {
                ItemStack item = (ItemStack) Depo.getData().get(player.getName() + ".contents." + i);
                super.setItem(i, item);
            }
        }*/
        if(Depo.getData().get(player.getName() + ".contents") != null) {
            for (String x : Depo.getData().getConfigurationSection(player.getName() + ".contents").getKeys(false)) {
                ItemStack item = (ItemStack) Depo.getData().get(player.getName() + ".contents." + x);
                super.setItem(Integer.parseInt(x), item);
            }
        }
    }

    @Override
    protected void onClose(@Nonnull Player player) {
        Inventory inventory = this.toInventory();
        Depo.getInstance().saveInventoryContents(player, inventory);
    }
}
