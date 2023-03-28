package net.astrospud.dl_copper.entities;

import net.astrospud.dl_copper.registration.DLC_Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class MoltenCopperEntity extends Entity {
    public MoltenCopperEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    public MoltenCopperEntity(LivingEntity owner, World world) {
        super(DLC_Entities.MOLTEN_COPPER, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (age % 2 == 0 && random.nextBetween(0, age/2) == 0) {
            world.addParticle(ParticleTypes.FLAME,
                    getX() + random.nextFloat() * getBoundingBox().getXLength() - getBoundingBox().getXLength() / 2,
                    getY() + 0.125,
                    getZ() + random.nextFloat() * getBoundingBox().getZLength() - getBoundingBox().getZLength() / 2,
                    0, 0, 0);
            world.addParticle(ParticleTypes.FALLING_LAVA,
                    getX() + random.nextFloat() * getBoundingBox().getXLength() - getBoundingBox().getXLength() / 2,
                    getY() + 0.125,
                    getZ() + random.nextFloat() * getBoundingBox().getZLength() - getBoundingBox().getZLength() / 2,
                    random.nextFloat() - 0.5, 0, random.nextFloat() - 0.5);
        }

        if (age >= 60) {
            this.discard();
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

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
