package net.orangetweaker.vanilla;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static net.orangetweaker.OrangeTweaker.MOD_ID;

@Module(MOD_ID)
public class VanillaModule extends ModuleBase {
  @Override
  public String getName() {
    return "Vanilla OrangeTweaker";
  }

  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);

    // Register BracketHandlers.
  }
}
