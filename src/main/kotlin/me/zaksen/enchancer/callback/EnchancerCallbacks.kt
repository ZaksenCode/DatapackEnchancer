package me.zaksen.enchancer.callback

import me.zaksen.enchancer.Enchancer
import me.zaksen.enchancer.callback.entity.EntityDeathCallback
import me.zaksen.enchancer.callback.entity.EntitySpawnCallback
import me.zaksen.enchancer.util.FunctionHelper
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.event.player.UseEntityCallback
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
            args.putString("player", player.nameForScoreboard)
            args.put("item", handStack.toNbt(player.registryManager))
            args.putInt("hand", hand.ordinal)

            Enchancer.getFunctionHolder().itemUseFunctions.forEach {
                FunctionHelper.callFunction(it, args, player.server)
            }

            return@register ActionResult.PASS
        }
        UseEntityCallback.EVENT.register { player, _, hand, entity, _ ->
            if(player !is ServerPlayerEntity) {
                return@register ActionResult.PASS
            }

            if(player.server == null || player.gameMode == GameMode.SPECTATOR) {
                return@register ActionResult.PASS
            }

            val args = NbtCompound()
            args.putString("player", player.nameForScoreboard)
            args.put("entity", entity.writeNbt(NbtCompound()))
            args.putInt("hand", hand.ordinal)

            Enchancer.getFunctionHolder().entityUseFunctions.forEach {
                FunctionHelper.callFunction(it, args, player.server)
            }

            return@register ActionResult.PASS
        }
        AttackEntityCallback.EVENT.register { player, _, hand, entity, _ ->
            if(player !is ServerPlayerEntity) {
                return@register ActionResult.PASS
            }

            if(player.server == null || player.gameMode == GameMode.SPECTATOR) {
                return@register ActionResult.PASS
            }

            val args = NbtCompound()
            args.putString("player", player.nameForScoreboard)
            args.put("entity", entity.writeNbt(NbtCompound()))
            args.putInt("hand", hand.ordinal)

            Enchancer.getFunctionHolder().entityAttackedFunctions.forEach {
                FunctionHelper.callFunction(it, args, player.server)
            }

            return@register ActionResult.PASS
        }
        EntityDeathCallback.EVENT.register { entity, reason ->
            if(entity.server == null) {
                return@register ActionResult.PASS
            }

            val args = NbtCompound()
            args.put("entity", entity.writeNbt(NbtCompound()))
            args.putInt("reason", reason.ordinal)

            Enchancer.getFunctionHolder().entityDeathFunctions.forEach {
                FunctionHelper.callFunction(it, args, entity.server!!)
            }
            return@register ActionResult.PASS
        }
        EntitySpawnCallback.EVENT.register { entity, reason ->
            if(entity.server == null) {
                return@register ActionResult.PASS
            }

            val args = NbtCompound()
            args.put("entity", entity.writeNbt(NbtCompound()))
            args.putInt("reason", reason.ordinal)

            Enchancer.getFunctionHolder().entityDeathFunctions.forEach {
                FunctionHelper.callFunction(it, args, entity.server!!)
            }
            return@register ActionResult.PASS
        }
    }
}