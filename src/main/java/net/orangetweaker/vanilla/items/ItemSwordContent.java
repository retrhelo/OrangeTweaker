package net.orangetweaker.vanilla.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.tools.ItemSwordBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.orangetweaker.zen.representation.ItemSwordRepresentation;
import com.google.common.collect.Multimap;

public class ItemSwordContent extends ItemSwordBase {
  private ItemSwordRepresentation represent;
  private EnumRarity rarity;

  public ItemSwordContent(ItemSwordRepresentation represent) {
    super(Item.ToolMaterial.WOOD, represent.getUnlocalizedName());
    this.represent = represent;
    checkFields();
    setFields();
  }

  private void checkFields() {
    List<String> missingFields = new ArrayList<String>();
    // Check if there's any unassigned fields.
    if (null == this.represent.getUnlocalizedName()) {
      missingFields.add("unlocalizedName");
    }
    if (-1 == this.represent.attackDamage) {
      missingFields.add("attackDamage");
    }

    if (!missingFields.isEmpty()) {
      throw new RuntimeException("Content ItemSword is missing fields " +
                                 missingFields.toString());
    }
  }

  private void setFields() {
    this.setUnlocalizedName(represent.getUnlocalizedName());
    if (null != this.represent.creativeTab) {
      Object creativeTab = this.represent.creativeTab.getInternal();
      if (creativeTab instanceof CreativeTabs) {
        this.setCreativeTab((CreativeTabs)creativeTab);
      }
    } else {
      this.setCreativeTab(CreativeTabs.COMBAT);
    }

    this.rarity = Enum.valueOf(EnumRarity.class, this.represent.rarity);
    if (this.represent.maxDamage > 0) {
      this.setMaxDamage(this.represent.maxDamage - 1);
    }
  }

  @Override
  public Multimap<String, AttributeModifier> getItemAttributeModifiers(
      EntityEquipmentSlot slot) {
    Multimap<String, AttributeModifier> multimap =
        super.getItemAttributeModifiers(slot);
    if (EntityEquipmentSlot.MAINHAND == slot) {
      // Clear previous stored attackDamage.
      if (multimap.containsKey(
          SharedMonsterAttributes.ATTACK_DAMAGE.getName())) {
        multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
      }
      // Add attackDamage modifier.
      multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
          new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier",
                                (double)this.represent.attackDamage - 1, 0));
    }

    return multimap;
  }

  @Override
  public String getUnlocalizedName() {
    return this.represent.getUnlocalizedName();
  }

  @Override
  @Nonnull
  public EnumRarity getRarity(@Nonnull ItemStack itemStack) {
    return this.rarity;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack itemStack) {
    return this.represent.isGlowing || super.hasEffect(itemStack);
  }
}
