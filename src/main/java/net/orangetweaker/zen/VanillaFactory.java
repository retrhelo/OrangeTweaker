package net.orangetweaker.zen;

import crafttweaker.annotations.ZenRegister;
import net.orangetweaker.zen.representation.ItemRepresentation;
import net.orangetweaker.zen.representation.ItemSwordRepresentation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.orangetweaker.VanillaFactory")
public class VanillaFactory {
  @ZenMethod
  public static ItemRepresentation createItem(String unlocalizedName) {
    ItemRepresentation represent = new ItemRepresentation();
    represent.unlocalizedName = unlocalizedName;
    return represent;
  }

  @ZenMethod
  public static ItemSwordRepresentation createItemSword(
      String unlocalizedName) {
    ItemSwordRepresentation represent = new ItemSwordRepresentation();
    represent.unlocalizedName = unlocalizedName;
    return represent;
  }
}
