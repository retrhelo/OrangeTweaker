package net.orangetweaker.zen.representation;

import java.util.Map;

import com.teamacronymcoders.base.registrysystem.ItemRegistry;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.orangetweaker.OrangeTweaker;
import net.orangetweaker.vanilla.items.ItemSwordContent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ZenClass("mods.orangetweaker.ItemSword")
public class ItemSwordRepresentation implements IRepresentation {
  @ZenProperty
  public String unlocalizedName;
  @ZenProperty
  public float attackDamage = 0.0f;
  @ZenProperty
  public int maxDamage = -1;
  @ZenProperty
  public String rarity = EnumRarity.COMMON.toString();
  @ZenProperty
  public ICreativeTab creativeTab = null;
  @ZenProperty
  public boolean isGlowing = false;

  @Override
  public String getUnlocalizedName() {
    return unlocalizedName;
  }

  @Override
  @ZenMethod
  public void register() {
    ItemSwordContent content = new ItemSwordContent(this);

    OrangeTweaker.instance.getRegistry(ItemRegistry.class, "ITEM")
                          .register(content);

    // Add this item to BracketHandlerItem's item list, so it can be referenced
    // by brackets later in the script.
    Map<String, Item> itemNames = BracketHandlerItem.getItemNames();
    if (null != itemNames.get(this.getUnlocalizedName())) {
      throw new RuntimeException("Re-register existing ItemRepresentation '" +
                                 this.getUnlocalizedName() + "'");
    } else {
      itemNames.put(this.getUnlocalizedName(), content);
    }
  }
}
