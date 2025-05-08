package me.zaksen.enchancer.callback.entity

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.Entity
import net.minecraft.entity.SpawnReason
import net.minecraft.util.ActionResult

fun interface EntitySpawnCallback {
    fun process(entity: Entity, reason: SpawnReason): ActionResult

    companion object {
        val EVENT: Event<EntitySpawnCallback> = EventFactory.createArrayBacked(EntitySpawnCallback::class.java) { listeners: Array<EntitySpawnCallback> ->
            EntitySpawnCallback { entity, reason ->
                listeners.forEach {
                    val result = it.process(entity, reason)

                    if (result != ActionResult.PASS) {
                        return@EntitySpawnCallback result
                    }
                }
                return@EntitySpawnCallback ActionResult.PASS
            }
        }
    }
}