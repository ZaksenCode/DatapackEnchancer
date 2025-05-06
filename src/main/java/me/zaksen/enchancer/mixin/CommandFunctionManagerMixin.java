package me.zaksen.enchancer.mixin;

import me.zaksen.enchancer.Enchancer;
import me.zaksen.enchancer.function.FunctionHolder;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.server.function.FunctionLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.zaksen.enchancer.Enchancer.MOD_ID;

@Mixin(CommandFunctionManager.class)
public class CommandFunctionManagerMixin {

    @Unique private static final Identifier ITEM_USE_TAG_ID = Identifier.of(MOD_ID, "item_use");

    @Unique private static final Identifier ENTITY_USE_TAG_ID = Identifier.of(MOD_ID, "entity_use");
    @Unique private static final Identifier ENTITY_ATTACKED_TAG_ID = Identifier.of(MOD_ID, "entity_attacked");

    @Unique private static final Identifier ENTITY_DEATH_TAG_ID = Identifier.of(MOD_ID, "entity_death");
    @Unique private static final Identifier ENTITY_SPAWN_TAG_ID = Identifier.of(MOD_ID, "entity_death");
    @Unique private static final Identifier ENTITY_DAMAGED_TAG_ID = Identifier.of(MOD_ID, "entity_death");

    @Inject(method = "load", at = @At("TAIL"))
    private void whenLoad(FunctionLoader loader, CallbackInfo ci) {
        FunctionHolder holder = Enchancer.Companion.getFunctionHolder();
        holder.clearFunctions();

        holder.getItemUseFunctions().addAll(loader.getTagOrEmpty(ITEM_USE_TAG_ID));

        holder.getEntityUseFunctions().addAll(loader.getTagOrEmpty(ENTITY_USE_TAG_ID));
        holder.getEntityAttackedFunctions().addAll(loader.getTagOrEmpty(ENTITY_ATTACKED_TAG_ID));

        holder.getEntityDeathFunctions().addAll(loader.getTagOrEmpty(ENTITY_DEATH_TAG_ID));
        holder.getEntitySpawnFunctions().addAll(loader.getTagOrEmpty(ENTITY_SPAWN_TAG_ID));
        holder.getEntityDamagedFunctions().addAll(loader.getTagOrEmpty(ENTITY_DAMAGED_TAG_ID));
    }
}
