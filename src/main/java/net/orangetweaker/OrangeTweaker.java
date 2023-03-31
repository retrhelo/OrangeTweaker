package net.orangetweaker;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.subblocksystem.SubBlockSystem;
import com.teamacronymcoders.base.util.OreDictUtils;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static net.orangetweaker.OrangeTweaker.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS)
public class OrangeTweaker extends BaseModFoundation<OrangeTweaker> {
  public static final String MOD_ID = "orangetweaker";
  public static final String MOD_NAME = "OrangeTweaker";
  public static final String VERSION = "0.1.0";
  public static final String DEPENDS = "required-after:base@[0.0.0,);" +
                                       "required-after:crafttweaker;";

  @Instance(MOD_ID)
  public static OrangeTweaker instance;

  public static boolean scriptsSuccessful;

  public OrangeTweaker() {
    super(MOD_ID, MOD_NAME, VERSION, null, false);
    this.subBlockSystem = new SubBlockSystem(this);
    OreDictUtils.addDefaultModId(MOD_ID);
  }

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);
  }

  @Override
  public void afterModuleHandlerInit(FMLPreInitializationEvent event) {
    scriptsSuccessful = CraftTweakerAPI.tweaker.loadScript(false,
                                                           "orangetweaker");
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    super.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    super.postInit(event);
  }

  @Override
  public boolean addOBJDomain() {
    return true;
  }

  @Override
  public boolean hasExternalResources() {
    return true;
  }

  @Override
  public OrangeTweaker getInstance() {
    return this;
  }
}
