package net.astrospud.dl_copper.entities;

import net.astrospud.dl_copper.registration.DLC_Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class MoltenCopperEntity extends Entity {
    UUID ownerID;
    PlayerEntity owner;
    public MoltenCopperEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    public MoltenCopperEntity(PlayerEntity owner, World world) {
        super(DLC_Entities.MOLTEN_COPPER, world);
        this.ownerID = owner.getUuid();
        this.owner = owner;
    }

    @Override
    public void tick() {
        super.tick();

        if (age % 2 == 0) {
            if (random.nextBetween(0, age / 6) == 0 && age % 4 == 0) {
                world.addParticle(ParticleTypes.FALLING_LAVA,
                        getX() + random.nextFloat() * getBoundingBox().getXLength() - getBoundingBox().getXLength() / 2,
                        getY() + 0.125,
                        getZ() + random.nextFloat() * getBoundingBox().getZLength() - getBoundingBox().getZLength() / 2,
                        random.nextFloat() - 0.5, 0, random.nextFloat() - 0.5);
            }
            if (random.nextBetween(0, age / 2) == 0) {
                world.addImportantParticle(ParticleTypes.FLAME,
                        getX() + random.nextFloat() * getBoundingBox().getXLength() - getBoundingBox().getXLength() / 2,
                        getY() + 0.125,
                        getZ() + random.nextFloat() * getBoundingBox().getZLength() - getBoundingBox().getZLength() / 2,
                        0, 0, 0);
            }
        }

        List<Entity> list = this.world.getOtherEntities(owner, this.getBoundingBox());
        for (Entity entity : list) {
            if (!(entity instanceof LivingEntity))
                continue;
            if (entity.damage(this.getDamageSources().inFire(), (60 - age) / 20f))
                entity.setOnFireFor((60 - age) / 20);
        }

        if (age >= 60) {
            this.playExtinguishSound();
            world.addParticle(ParticleTypes.SMOKE,
                    getX() + getBoundingBox().getXLength() / 2 + random.nextFloat() - 0.5,
                    getY() + 0.125,
                    getZ() + getBoundingBox().getZLength() / 2 + random.nextFloat() - 0.5,
                    0, 0, 0);
            this.discard();
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("owner_uuid")) {
            this.ownerID = nbt.getUuid("owner_uuid");
            this.owner = world.getPlayerByUuid(ownerID);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (ownerID != null)
            nbt.putUuid("owner_uuid", ownerID);
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }


    @Override
    public boolean shouldRenderName() {
        return false;
    }
}
