package me.roridev.learnFabric.mixins;

import kotlin.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

import static me.roridev.learnFabric.helpers.BlockHelperKt.getXpDropped;
import static me.roridev.learnFabric.helpers.MendingHelperKt.applyMending;
import static net.minecraft.block.Block.dropStack;

@Mixin(Block.class)
public abstract class MixinBlock  {
    @Shadow
    public static List<ItemStack> getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack) {
        return null;
    }


    /**
     * @author wffirilat
     */
    @Overwrite
    public static void dropStacks(BlockState state, World world, BlockPos pos, @Nullable BlockEntity blockEntity, Entity entity, ItemStack stack) {
        if (world instanceof ServerWorld) {
            assert getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, stack) != null;
            //(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack)
            Pair<Integer, Boolean> xpDropped = getXpDropped(state, world);
            getDroppedStacks(state, (ServerWorld)world, pos, blockEntity,entity,stack).forEach((itemStack) -> {
                if(entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity)entity;
                    player.inventory.offerOrDrop(world, itemStack);

                    applyMending(player, xpDropped.component1());
                } else {
                    dropStack(world, pos, itemStack);
                }
            });
            if(!xpDropped.component2()) state.onStacksDropped(world,pos,stack);
        }
    }

}
