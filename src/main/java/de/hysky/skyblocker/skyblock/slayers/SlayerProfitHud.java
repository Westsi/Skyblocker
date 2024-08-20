package de.hysky.skyblocker.skyblock.slayers;

import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.events.HudRenderEvents;
import de.hysky.skyblocker.skyblock.itemlist.ItemRepository;
import de.hysky.skyblocker.skyblock.itemlist.ItemStackBuilder;
import de.hysky.skyblocker.skyblock.tabhud.util.Ico;
import de.hysky.skyblocker.utils.SlayerUtils;
import de.hysky.skyblocker.utils.Utils;
import io.github.moulberry.repo.data.NEUItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.BuiltinRegistries;
import org.checkerframework.checker.units.qual.N;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class SlayerProfitHud {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlayerProfitHud.class);
    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static final HashMap<ItemStack, Integer> droppedItemCount = new HashMap<>();
    public static Integer spawnCost = 0;
    public static Integer mobKillCoins = 0;


    public static void init() {
        HudRenderEvents.AFTER_MAIN_HUD.register((context, tickCounter) -> {
            if (!shouldRender()) {return;}
//            droppedItemCount.clear();
            droppedItemCount.put(ItemRepository.getItemStack("ENDER_PEARL"), 137400);
            droppedItemCount.put(ItemRepository.getItemStack("SUMMONING_EYE"), 18);
            droppedItemCount.put(ItemRepository.getItemStack("ENCHANTED_ENDER_PEARL"), 3700);
            SlayerProfitHudWidget.INSTANCE.update();
            SlayerProfitHudWidget.INSTANCE.render(context, SkyblockerConfigManager.get().uiAndVisuals.tabHud.enableHudBackground);
        });
    }

    private static boolean shouldRender() {
        return SkyblockerConfigManager.get().slayers.slayerProfitHud.enableHud
                && client.player != null
                && SlayerUtils.isInSlayerArea()
                && SlayerUtils.isInSlayerQuest();
    }

}
