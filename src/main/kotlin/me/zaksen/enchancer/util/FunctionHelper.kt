package me.zaksen.enchancer.util

import com.mojang.logging.LogUtils
import net.minecraft.command.CommandExecutionContext
import net.minecraft.command.ReturnValueConsumer
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.function.*
import net.minecraft.util.profiler.Profilers
import org.slf4j.Logger
import java.util.function.Supplier

object FunctionHelper {

    private const val FUNC_PERM_LEVEL: Int = 4
    private val LOGGER: Logger = LogUtils.getLogger()

    fun callFunction(function: CommandFunction<ServerCommandSource>, args: NbtCompound?, sourceServer: MinecraftServer) {
        val source = sourceServer.getCommandSource().withLevel(FUNC_PERM_LEVEL).withSilent();
        val profiler = Profilers.get()
        profiler.push((Supplier { "function " + function.id() }))

        try {
            val procedure: Procedure<ServerCommandSource> = function.withMacroReplaced(args, sourceServer.commandManager.dispatcher)
            CommandManager.callWithContext(source) { context -> CommandExecutionContext.enqueueProcedureCall(
                context,
                procedure,
                source,
                ReturnValueConsumer.EMPTY
            )}
        } catch (e: Exception) {
            LOGGER.warn("Failed to execute function {}", function.id(), e)
        } finally {
            profiler.pop()
        }
    }
}