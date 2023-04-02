package net.orangetweaker.zen.representation;

import com.teamacronymcoders.base.registrysystem.ItemRegistry;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.creativetabs.ICreativeTab;
import net.minecraft.item.EnumRarity;
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
  public float attackSpeed = 1.6f;
  @ZenProperty
  public float additionalReach = 0.0f;
  @ZenProperty
  public int maxDamage = -1;
  @ZenProperty
  public String rarity = EnumRarity.COMMON.toString();
  @ZenProperty
  public ICreativeTab creativeTab = null;
  @ZenProperty
  public boolean isGlowing = false;
  @ZenProperty
  public int enchantability = 15;   // By default as material wood.
  @ZenProperty
  public boolean twoHand = false;
  @ZenProperty
  public boolean canDisableShield = false;  // Break through entity's shield.
  @ZenProperty
  public boolean canSetFire = false;  // Burn your enemy on hit.

  @Override
  public String getUnlocalizedName() {
    return unlocalizedName;
  }

  @Override
  @ZenMethod
  public void register() {
    OrangeTweaker.instance.getRegistry(ItemRegistry.class, "ITEM")
                          .register(new ItemSwordContent(this));
  }
}
