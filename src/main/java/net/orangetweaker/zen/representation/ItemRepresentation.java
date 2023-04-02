package net.orangetweaker.zen.representation;

import com.teamacronymcoders.base.registrysystem.ItemRegistry;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.creativetabs.ICreativeTab;
import net.minecraft.item.EnumRarity;
import net.orangetweaker.OrangeTweaker;
import net.orangetweaker.vanilla.items.ItemContent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ZenClass("mods.orangetweaker.Item")
public class ItemRepresentation implements IRepresentation {
  @ZenProperty
  public String unlocalizedName;
  @ZenProperty
  public int maxStackSize = 64;
  @ZenProperty
  public String rarity = EnumRarity.COMMON.toString();
  @ZenProperty
  public ICreativeTab creativeTab = null;
  @ZenProperty
  public float smeltingExperience = -1;
  @ZenProperty
  public boolean isGlowing = false;
  // TODO(Yiming Liu): Add itemGetContainerItem. Leave this empty until we're
  //                   creating recipes.

  @Override
  public String getUnlocalizedName() {
    return unlocalizedName;
  }

  @Override
  @ZenMethod
  public void register() {
    OrangeTweaker.instance.getRegistry(ItemRegistry.class, "ITEM")
                          .register(new ItemContent(this));
  }
}
