package net.astrospud.dl_copper.mixin;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.items.copper_armor.modules.boots.FlamewakerModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Objects;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	BlockPos oldPos;
	@Inject(at = @At("HEAD"), method = "isInvulnerableTo", cancellable = true)
	public void isInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		PlayerInventory inv = player.getInventory();

		for(int i = 0; i < inv.size(); ++i) {
			ItemStack itemStack = inv.getStack(i);
			if (itemStack.getItem().equals(DLC_Items.FLAMEWAKER_MODULE_BOOTS)) {
				if (itemStack.getItem() instanceof FlamewakerModuleItem fmi && itemStack.getNbt() != null && itemStack.getNbt().contains("DLC_INDEX")) {
					int index = itemStack.getNbt().getInt("DLC_INDEX");
					fmi = (FlamewakerModuleItem) itemStack.getItem();
					int finalI = i;
					if (index == 0) {
						if (Objects.equals(damageSource.getName(), "hotFloor")) {
							cir.setReturnValue(true);
							if (oldPos != player.getBlockPos()) {
								itemStack.damage(1, player, (p) -> {
									p.getInventory().setStack(finalI, DLC_Items.EMPTY_MODULE.getDefaultStack());
								});
							}
							oldPos = player.getBlockPos();
						}
					}
					if (index == 1) {
						if (Objects.equals(damageSource.getName(), "inFire")) {
							cir.setReturnValue(true);
							fmi.setCounter(fmi.getCounter()+1);
							if (fmi.getCounter() >= 20) {
								itemStack.damage(1, player, (p) -> {
									p.getInventory().setStack(finalI, DLC_Items.EMPTY_MODULE.getDefaultStack());
								});
								fmi.setCounter(0);
							}
						}
					}
					if (index == 2) {
						if (Objects.equals(damageSource.getName(), "onFire")) {
							cir.setReturnValue(true);
							fmi.setCounter(fmi.getCounter()+1);
							if (fmi.getCounter() >= 60) {
								itemStack.damage(1, player, (p) -> {
									p.getInventory().setStack(finalI, DLC_Items.EMPTY_MODULE.getDefaultStack());
								});
								fmi.setCounter(0);
							}
						}
					}
				}
			}
		}
	}
}
