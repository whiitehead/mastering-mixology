package work.fking.masteringmixology;

import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Graphics2D;

import static work.fking.masteringmixology.PotionComponent.AGA;
import static work.fking.masteringmixology.PotionComponent.LYE;
import static work.fking.masteringmixology.PotionComponent.MOX;

public class InventoryPotionOverlay extends WidgetItemOverlay {
    private final MasteringMixologyPlugin plugin;
    private final MasteringMixologyConfig config;

    @Inject
    private SpriteManager spriteManager;

    @Inject
    InventoryPotionOverlay(MasteringMixologyPlugin plugin, MasteringMixologyConfig config) {
        this.plugin = plugin;
        this.config = config;
        showOnInventory();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics2D, int itemId, WidgetItem widgetItem) {
        if (!plugin.isInLab() || !config.identifyPotions()) {
            return;
        }

        var modifiedPotion = false;

        if (30020 < itemId) {
            modifiedPotion = true;
            itemId -= 10;
        }

        var potion = PotionType.fromItemId(itemId);

        if (potion == null) {
            return;
        }

        var bounds = widgetItem.getCanvasBounds();
        graphics2D.setFont(FontManager.getRunescapeSmallFont());
        var recipeChars = potion.recipe().toCharArray();

        var x = bounds.x - 1;
        var y = bounds.y + 10;

        // id: 5672, 5673, 5674
        var image = spriteManager.getSprite(5672, 0);
        graphics2D.drawImage(image, x, y, null);

        for (int i = 0; i < recipeChars.length; i++) {
            // I have no idea why this draws a bunch of black shit off to the right.
            // graphics2D.setColor(Color.BLACK);
            // graphics2D.drawChars(recipeChars, i, 1, x, y);
            switch (recipeChars[i]) {
                case 'M':
                    graphics2D.setColor(Color.decode("#" + MOX.color()));
                    graphics2D.drawChars(recipeChars, i, 1, x, y);
                    x += 8;
                    break;
                case 'A':
                    graphics2D.setColor(Color.decode("#" + AGA.color()));
                    graphics2D.drawChars(recipeChars, i, 1, x, y);
                    x += 7;
                    break;
                case 'L':
                    graphics2D.setColor(Color.decode("#" + LYE.color()));
                    graphics2D.drawChars(recipeChars, i, 1, x, y);
                    x += 5;
                    break;
                default: break;
            }
        }


    }
}
