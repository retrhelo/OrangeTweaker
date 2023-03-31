package net.orangetweaker.zen.representation;

import java.util.Map;

import com.teamacronymcoders.base.registrysystem.ItemRegistry;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
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
    ItemContent itemContent = new ItemContent(this);

    OrangeTweaker.instance.getRegistry(ItemRegistry.class, "ITEM")
                          .register(itemContent);

    // Add this item to BracketHandlerItem's item list, so it can be referenced
    // by brackets in other scripts code.
    Map<String, Item> itemNames = BracketHandlerItem.getItemNames();
    if (null != itemNames.get(this.getUnlocalizedName())) {
      throw new RuntimeException("Re-register existing ItemRepresentation '" +
                                 this.getUnlocalizedName() + "'");
    } else {
      itemNames.put(this.getUnlocalizedName(), itemContent);
    }
  }
}
