package de.hysky.skyblocker.skyblock.slayers;

import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.skyblock.tabhud.widget.Widget;
import de.hysky.skyblocker.skyblock.tabhud.widget.component.PlainTextComponent;
import de.hysky.skyblocker.utils.ItemUtils;
import de.hysky.skyblocker.utils.SlayerUtils;
import it.unimi.dsi.fastutil.doubles.DoubleBooleanPair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;
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
        Integer totalValue = 0;
        for (Map.Entry<ItemStack, Integer> entry : SlayerProfitHud.droppedItemCount.entrySet()) {
            Integer value = entry.getValue();
            String sbApiID = entry.getKey().getSkyblockApiId();
            if (!sbApiID.isEmpty()) {
                DoubleBooleanPair priceData = ItemUtils.getItemPrice(sbApiID);
                // should this check the completeness boolean (priceData.rightBoolean)?
                value = (int) (value * priceData.leftDouble());
            } else {
                break;
            }
            totalValue += value;
            addSimpleIcoText(entry.getKey(), entry.getValue() + "x " + entry.getKey().getName().getSiblings().getFirst().getString() + ": ", getFormattingColor(value), "" + getShortenedNumber(value));
        }
        addComponent(new PlainTextComponent(Text.literal("Spawn Costs: -" + getShortenedNumber(SlayerProfitHud.spawnCost)).formatted(getFormattingColor(-SlayerProfitHud.spawnCost))));
        totalValue = totalValue - SlayerProfitHud.spawnCost;
        addComponent(new PlainTextComponent(Text.literal("Mob Kill Coins: " + getShortenedNumber(SlayerProfitHud.mobKillCoins)).formatted(Formatting.DARK_GREEN)));
        totalValue = totalValue + SlayerProfitHud.mobKillCoins;
        addComponent(new PlainTextComponent(Text.literal("Profit: " + getShortenedNumber(totalValue)).formatted(Formatting.GOLD)));
    }

    private Formatting getFormattingColor(Integer value) {
        if (value > 100000) {
            return Formatting.GREEN;
        } else if (value > 0) {
            return Formatting.YELLOW;
        }
        return Formatting.RED;
    }

    private String getShortenedNumber(Integer val) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (val > 1000000000) { // 1b
            double v = (double) val / 1000000000;
            return df.format(v) + "b";
        } else if (val > 1000000) { // 1m
            double v = (double) val / 1000000;
            return df.format(v) + "m";
        } else if (val > 1000) { // 1k
            double v = (double) val / 1000;
            return df.format(v) + "k";
        }
        return val + "";
    }
}
