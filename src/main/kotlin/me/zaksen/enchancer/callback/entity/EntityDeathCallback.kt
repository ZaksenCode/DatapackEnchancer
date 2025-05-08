package me.zaksen.enchancer.callback.entity

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.Entity
import net.minecraft.util.ActionResult

fun interface EntityDeathCallback {
    fun process(entity: Entity, reason: Entity.RemovalReason): ActionResult

    companion object {
        val EVENT: Event<EntityDeathCallback> = EventFactory.createArrayBacked(EntityDeathCallback::class.java) { listeners: Array<EntityDeathCallback> ->
            EntityDeathCallback { entity, reason ->
                listeners.forEach {
                    val result = it.process(entity, reason)

                    if (result != ActionResult.PASS) {
                        return@EntityDeathCallback result
                    }
                }
                return@EntityDeathCallback ActionResult.PASS
            }
        }
    }
}