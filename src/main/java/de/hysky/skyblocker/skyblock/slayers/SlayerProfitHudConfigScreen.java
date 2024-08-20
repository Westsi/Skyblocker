package de.hysky.skyblocker.skyblock.slayers;

import de.hysky.skyblocker.config.HudConfigScreen;
import de.hysky.skyblocker.config.SkyblockerConfig;
import de.hysky.skyblocker.skyblock.tabhud.widget.Widget;
import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class SlayerProfitHudConfigScreen extends HudConfigScreen {
    public SlayerProfitHudConfigScreen(Screen parent) {
        super(Text.literal("Slayer Profit Hud Config"), parent, SlayerProfitHudWidget.INSTANCE);
    }

    @Override
    protected List<IntIntMutablePair> getConfigPos(SkyblockerConfig config) {
        return List.of(
                IntIntMutablePair.of(config.slayers.slayerProfitHud.x, config.slayers.slayerProfitHud.y)
        );
    }

    @Override
    protected void savePos(SkyblockerConfig configManager, List<Widget> widgets) {
        configManager.slayers.slayerProfitHud.x = widgets.getFirst().getX();
        configManager.slayers.slayerProfitHud.y = widgets.getFirst().getY();
    }
}
