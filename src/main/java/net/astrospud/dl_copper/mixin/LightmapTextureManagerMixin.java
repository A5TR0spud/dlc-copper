package net.astrospud.dl_copper.mixin;

import net.astrospud.dl_copper.items.copper_armor.modules.helmet.NightVisionModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @Inject(at = @At(value = "HEAD"), method = "getBrightness", cancellable = true)
    private static void dlcGetBrightness(DimensionType type, int lightLevel, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null)
            return;
        boolean wearing = false;
        for (ItemStack itemStack : player.getArmorItems()) {
            if (itemStack.getItem() == DLC_Items.COPPER_HELMET) {
                wearing = true;
                break;
            }
        }
        if (!wearing)
            return;
        float f = (float)lightLevel / 15.0f;
        float g = f / (4.0f - 3.0f * f);
        f = MathHelper.lerp(type.ambientLight(), g, 1.0f);
        if (    player.getInventory().containsAny((ItemStack stack) -> stack.getItem() instanceof NightVisionModuleItem)
                && f < 3f / 15f) {
            cir.setReturnValue(3f / 15f);
        }
    }
}