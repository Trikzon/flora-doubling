/*
 * This file is part of Flourish.
 * A copy of this program can be found at https://github.com/Trikzon/flourish.
 * Copyright (C) 2023 Dion Tryban
 *
 * Flourish is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Flourish is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Flourish. If not, see <https://www.gnu.org/licenses/>.
 */

package com.diontryban.flourish.mixin;

import com.diontryban.flourish.Flourish;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FlowerBlock.class)
public abstract class FlowerBlockMixin extends BushBlock implements BonemealableBlock {
    protected FlowerBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.is(BlockTags.SMALL_FLOWERS)
                && (Flourish.OPTIONS.get().witherRose || state.getBlock() != Blocks.WITHER_ROSE)
                && (Flourish.OPTIONS.get().torchflower || state.getBlock() != Blocks.TORCHFLOWER);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (Flourish.OPTIONS.get().useTallFlowerBehavior) {
            flourish$tallFlowerBehavior(level, pos);
        } else {
            flourish$bedrockEditionBehavior(level, random, pos);
        }
    }

    @Unique
    private void flourish$tallFlowerBehavior(ServerLevel level, BlockPos pos) {
        Block.popResource(level, pos, new ItemStack(this, 1));
    }

    @Unique
    private void flourish$bedrockEditionBehavior(ServerLevel level, RandomSource random, BlockPos pos) {
        final int maxSuccesses = random.nextIntBetweenInclusive(1, 7);
        int successCounter = 0;
        for (int i = 0; i < 64 && successCounter < maxSuccesses; i++) {
            BlockPos newPos = pos;
            for (int j = 0; j < i / 22 + 1; j++) {
                newPos = newPos.offset(
                        random.nextIntBetweenInclusive(-1, 1),
                        0,
                        random.nextIntBetweenInclusive(-1, 1)
                );
            }
            newPos = newPos.offset(0, random.nextIntBetweenInclusive(-1, 1), 0);
            final BlockPos below = newPos.below();
            if (this.mayPlaceOn(level.getBlockState(below), level, below)) {
                if (level.getBlockState(newPos).isAir()) {
                    level.setBlock(newPos, this.defaultBlockState(), 1 | 2);
                    flourish$addGrowthParticles(level, newPos, random.nextInt(14));
                    successCounter++;
                }
            }
        }
    }

    // Copied from BoneMealItem and modified to work from the server side.
    @Unique
    private static void flourish$addGrowthParticles(ServerLevel pLevel, BlockPos pPos, int pData) {
        if (pData == 0) {
            pData = 15;
        }

        BlockState blockstate = pLevel.getBlockState(pPos);
        double d0 = 0.5D;
        double d1;
        if (blockstate.is(Blocks.WATER)) {
            pData *= 3;
            d1 = 1.0D;
            d0 = 3.0D;
        } else if (blockstate.isSolidRender()) {
            pPos = pPos.above();
            pData *= 3;
            d0 = 3.0D;
            d1 = 1.0D;
        } else {
            d1 = blockstate.getShape(pLevel, pPos).max(Direction.Axis.Y);
        }

        pLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        RandomSource randomsource = pLevel.getRandom();

        for(int i = 0; i < pData; ++i) {
            double d2 = randomsource.nextGaussian() * 0.02D;
            double d3 = randomsource.nextGaussian() * 0.02D;
            double d4 = randomsource.nextGaussian() * 0.02D;
            double d9 = randomsource.nextGaussian() * 0.02D;
            double d5 = 0.5D - d0;
            double d6 = (double)pPos.getX() + d5 + randomsource.nextDouble() * d0 * 2.0D;
            double d7 = (double)pPos.getY() + randomsource.nextDouble() * d1;
            double d8 = (double)pPos.getZ() + d5 + randomsource.nextDouble() * d0 * 2.0D;
            if (!pLevel.getBlockState(BlockPos.containing(d6, d7, d8).below()).isAir()) {
                pLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, 1, d2, d3, d4, d9);
            }
        }
    }
}
