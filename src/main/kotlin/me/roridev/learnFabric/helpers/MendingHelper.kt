package me.roridev.learnFabric.helpers

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import kotlin.math.min


fun applyMending(player : PlayerEntity , xp:Int) {
    val entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.MENDING,player,ItemStack::isDamaged)
    if(entry != null){
        val itemStack = entry.value
        val value = xp - mendingRepairCost(xp)
        if(!itemStack.isEmpty && itemStack.isDamaged){
            val i = min(mendingRepairAmount(xp),itemStack.damage)
            itemStack.damage -= i
        }
        player.addExperience(value)
    }
}
private fun mendingRepairCost(repairAmount : Int) : Int{
    return repairAmount / 2
}
private fun mendingRepairAmount(experience : Int) : Int {
    return experience * 2
}

