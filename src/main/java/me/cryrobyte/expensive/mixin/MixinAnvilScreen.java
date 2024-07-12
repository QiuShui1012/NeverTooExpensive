package me.cryrobyte.expensive.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AnvilScreen.class)
public abstract class MixinAnvilScreen extends ForgingScreen<AnvilScreenHandler> {

    @Shadow @Final private PlayerEntity player;

    public MixinAnvilScreen(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title, texture);
    }

    /**
     * @author Mojang - CryroByte - QiuShui1012
     * @reason Needed to overwrite due to the int J value not being set correctly
     */
    @Overwrite
    public void drawForeground(DrawContext context, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.drawForeground(context, mouseX, mouseY);

        int i = this.handler.getLevelCost();
        if (i > 0) {
            int j = 8453920;
            Text text;
            if (!this.handler.getSlot(2).hasStack()) {
                text = null;
            } else {
                text = Text.translatable("container.repair.cost", i);
                if (!this.handler.getSlot(2).canTakeItems(this.player)) {
                    j = 16736352;
                }
            }

            if (text != null) {
                int k = this.backgroundWidth - 8 - this.textRenderer.getWidth(text) - 2;
                context.fill(k - 2, 67, this.backgroundWidth - 8, 79, 1325400064);
                context.drawTextWithShadow(this.textRenderer, text, k, 69, j);
            }
        }
    }

}
