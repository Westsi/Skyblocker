package de.hysky.skyblocker.skyblock.slayers;

import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.injected.SkyblockerStack;
import de.hysky.skyblocker.skyblock.tabhud.util.Ico;
import de.hysky.skyblocker.skyblock.tabhud.widget.Widget;
import de.hysky.skyblocker.skyblock.tabhud.widget.component.PlainTextComponent;
import de.hysky.skyblocker.utils.SlayerUtils;
import de.hysky.skyblocker.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;


public class SlayerProfitHudWidget extends Widget {
    public static final SlayerProfitHudWidget INSTANCE = new SlayerProfitHudWidget();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public SlayerProfitHudWidget() {
        super(Text.literal(SlayerUtils.getSlayerType() + " " + SlayerUtils.getSlayerTier() + " Profit")
                .formatted(Formatting.YELLOW, Formatting.BOLD),
                Formatting.GOLD.getColorValue());
        setX(SkyblockerConfigManager.get().slayers.slayerProfitHud.x);
        setY(SkyblockerConfigManager.get().slayers.slayerProfitHud.y);
        update();
    }

    @Override
    public void updateContent() {
        if (client.player == null) return;
        for (Map.Entry<ItemStack, Integer> entry : SlayerProfitHud.droppedItemCount.entrySet()) {
            Integer totalValue = entry.getValue();
            addSimpleIcoText(entry.getKey(), "THING: ", getFormattingColor(totalValue), "" + totalValue);
        }
        addComponent(new PlainTextComponent(Text.literal("Spawn Costs: " + -SlayerProfitHud.spawnCost).formatted(getFormattingColor(-SlayerProfitHud.spawnCost))));
    }

    private Formatting getFormattingColor(Integer value) {
        if (value > 1000000) {
            return Formatting.GREEN;
        } else if (value > 0) {
            return Formatting.YELLOW;
        }
        return Formatting.RED;
    }
}
