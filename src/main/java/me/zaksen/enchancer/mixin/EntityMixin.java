package me.zaksen.enchancer.mixin;

import me.zaksen.enchancer.callback.entity.EntityDeathCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "remove", at = @At("TAIL"))
    private void onRemoved(Entity.RemovalReason reason, CallbackInfo ci) {
        EntityDeathCallback.Companion.getEVENT().invoker().process((Entity) (Object) this, reason);
    }
}
