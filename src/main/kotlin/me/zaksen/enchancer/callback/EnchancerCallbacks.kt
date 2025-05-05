package me.zaksen.enchancer.callback

import me.zaksen.enchancer.Enchancer
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.world.GameMode

class EnchancerCallbacks {
    fun registerCallbacks() {
        UseItemCallback.EVENT.register { player, _, hand ->
            if(player !is ServerPlayerEntity) {
                return@register ActionResult.PASS
            }

            if(player.server == null || player.gameMode == GameMode.SPECTATOR) {
                return@register ActionResult.PASS
            }

            val handStack = player.getStackInHand(hand)

            if(handStack.isEmpty) {
                return@register ActionResult.PASS
            }

            val args = NbtCompound()
            args.put("item", handStack.toNbt(player.registryManager))
            args.putInt("hand", hand.ordinal)

            Enchancer.getFunctionHolder().itemUseFunctions.forEach {
                it.withMacroReplaced(args, player.server!!.commandManager.dispatcher)
            }

            return@register ActionResult.PASS
        }
    }
}