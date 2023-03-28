package net.astrospud.dl_copper.mixin;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.items.copper_armor.modules.boots.FlamewakerModuleItem;
import net.astrospud.dl_copper.items.copper_armor.modules.plating.LightningModuleItem;
import net.astrospud.dl_copper.registration.DLC_Items;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
	public void dlcIsInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		PlayerInventory inv = player.getInventory();

		for(int i = 0; i < inv.size(); ++i) {
			ItemStack itemStack = inv.getStack(i);
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

	@Inject(at = @At("HEAD"), method = "attack")
	public void dlcAttack(Entity target, CallbackInfo cir) {
		PlayerEntity player = (PlayerEntity) (Object) this;

		if (!target.isAttackable()) {
			return;
		}
		if (target.handleAttack(player)) {
			return;
		}

		PlayerInventory inv = player.getInventory();
		int lightningCount = 0;

		for(int i = 0; i < inv.size()&& lightningCount <= 3; ++i) {
			ItemStack itemStack = inv.getStack(i);
			if (itemStack.getItem() instanceof LightningModuleItem) {
				lightningCount++;
				int finalI = i;
				itemStack.damage(32, player, (p) -> {
					p.getInventory().setStack(finalI, ItemStack.EMPTY);
				});
			}
		}

		for (int i = 0; i < 3 * lightningCount; i++) {
			double targetX = (target.getX() - (target.getBoundingBox().getXLength() / 2) + 1.2 * target.getBoundingBox().getXLength() * player.getRandom().nextFloat());
			double targetY = (target.getY() + 1.2 * target.getBoundingBox().getYLength() * player.getRandom().nextFloat());
			double targetZ = (target.getZ() - (target.getBoundingBox().getZLength() / 2) + 1.2 * target.getBoundingBox().getZLength() * player.getRandom().nextFloat());

			double velX = player.getRandom().nextFloat() * MathHelper.sign(target.getX() - targetX);
			double velY = player.getRandom().nextFloat() * MathHelper.sign(target.getY() - targetY);
			double velZ = player.getRandom().nextFloat() * MathHelper.sign(target.getZ() - targetZ);

			target.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK,
					targetX - velX,
					targetY - velY,
					targetZ - velZ,
					velX, velY, velZ);
		}

		target.damage(player.getDamageSources().lightningBolt(), 2 * lightningCount);
	}
}
