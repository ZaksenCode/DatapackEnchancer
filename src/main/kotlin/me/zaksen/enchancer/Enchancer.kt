package me.zaksen.enchancer

import me.zaksen.enchancer.callback.EnchancerCallbacks
import me.zaksen.enchancer.function.FunctionHolder
import net.fabricmc.api.ModInitializer

class Enchancer : ModInitializer {

    private val callbacks = EnchancerCallbacks()

    override fun onInitialize() {
        callbacks.registerCallbacks()
    }

    companion object {
        private val functionHolder = FunctionHolder()

        fun getFunctionHolder(): FunctionHolder {
            return functionHolder
        }
    }
}

object Constants {
    const val MOD_ID: String = "enchancer"
}