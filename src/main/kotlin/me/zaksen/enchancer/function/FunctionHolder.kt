package me.zaksen.enchancer.function

import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.function.CommandFunction

class FunctionHolder {
    val itemUseFunctions = mutableListOf<CommandFunction<ServerCommandSource>>()

    val entityUseFunctions = mutableListOf<CommandFunction<ServerCommandSource>>()
    val entityAttackedFunctions = mutableListOf<CommandFunction<ServerCommandSource>>()

    val entityDeathFunctions = mutableListOf<CommandFunction<ServerCommandSource>>()
    val entitySpawnFunctions = mutableListOf<CommandFunction<ServerCommandSource>>()

    fun clearFunctions() {
        itemUseFunctions.clear()

        entityUseFunctions.clear()
        entityAttackedFunctions.clear()

        entityDeathFunctions.clear()
        entitySpawnFunctions.clear()
    }
}