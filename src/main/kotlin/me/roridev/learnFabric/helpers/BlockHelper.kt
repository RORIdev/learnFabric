package me.roridev.learnFabric.helpers

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

fun getXpDropped(state: BlockState, world: World) : Pair<Int,Boolean> {
    return if (state.block == Blocks.COAL_ORE) {
        Pair(MathHelper.nextInt(world.random, 0, 2),true)
    } else if (state.block == Blocks.DIAMOND_ORE) {
        Pair(MathHelper.nextInt(world.random, 3, 7),true)
    } else if (state.block == Blocks.EMERALD_ORE) {
        Pair(MathHelper.nextInt(world.random, 3, 7),true)
    } else if (state.block == Blocks.LAPIS_ORE) {
        Pair(MathHelper.nextInt(world.random, 2, 5),true)
    } else if (state.block == Blocks.NETHER_QUARTZ_ORE) {
        Pair(MathHelper.nextInt(world.random, 2, 5),true)
    } else if (state.block == Blocks.REDSTONE_ORE ) {
        Pair(1 + world.random.nextInt(5),true)
    } else if (state.block == Blocks.SPAWNER){
        Pair(15 + world.random.nextInt(15) + world.random.nextInt(15),true)
    }
    else {
        if (state.block == Blocks.NETHER_GOLD_ORE) Pair(MathHelper.nextInt(world.random, 0, 1),true) else Pair(0,false)
    }
}