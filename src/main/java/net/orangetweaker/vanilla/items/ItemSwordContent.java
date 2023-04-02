package net.orangetweaker.vanilla.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.tools.ItemSwordBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.orangetweaker.vanilla.VanillaAPI;
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
    // Check if there's any invalid fields.
    if (null == this.represent.getUnlocalizedName()) {
      missingFields.add("unlocalizedName");
    }
    if (-1 == this.represent.attackDamage) {
      missingFields.add("attackDamage");
    }
    if (!(this.represent.attackSpeed < 4.0F)) {
      missingFields.add("attackSpeed");
    }
    if (!(this.represent.additionalReach >= 0.0f)) {
      missingFields.add("addtionalReach");
    }

    if (!missingFields.isEmpty()) {
      throw new RuntimeException("Content ItemSword has invalid fields " +
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
      // Clear previous stored attackDamage and attackSpeed.
      // These attributes are added in ItemSword, so we have to remove them in
      // advance for our own attributes to take effects.
      if (multimap.containsKey(SharedMonsterAttributes.ATTACK_DAMAGE.getName())) {
        multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
      }
      if (multimap.containsKey(SharedMonsterAttributes.ATTACK_SPEED.getName())) {
        multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
      }

      multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
          new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier",
                                (double)this.represent.attackDamage - 1, 0));
      multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
          new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier",
                                (double)this.represent.attackSpeed - 4.0D, 0));
      multimap.put(EntityPlayer.REACH_DISTANCE.getName(),
          new AttributeModifier(VanillaAPI.ATTACK_REACH_MODIFIER,
                                "Weapon modifier",
                                (double)this.represent.additionalReach, 0));
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

  @Override
  public int getItemEnchantability() {
    return this.represent.enchantability;
  }

  // Add support for Two-hand weapons. When twoHand is set to true, the sword
  // disables the use of item held on offhand.

  @Override
  public EnumAction getItemUseAction(ItemStack stack) {
    return this.represent.twoHand ? EnumAction.BOW :
                                    super.getItemUseAction(stack);
  }

  @Override
  public int getMaxItemUseDuration(ItemStack stack) {
    return this.represent.twoHand ? 72000 :
                                    super.getMaxItemUseDuration(stack);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn,
                                                  EntityPlayer playerIn,
                                                  EnumHand handIn) {
    if (this.represent.twoHand) {
      // Disable the use of offhand item when marked as twoHand.
      if (EnumHand.MAIN_HAND == handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,
                                           playerIn.getHeldItem(handIn));
      } else {
        return new ActionResult<ItemStack>(EnumActionResult.FAIL,
                                           playerIn.getHeldItem(handIn));
      }
    } else {
      return super.onItemRightClick(worldIn, playerIn, handIn);
    }
  }

  @Override
  public void onPlayerStoppedUsing(ItemStack stack, World worldIn,
                                   EntityLivingBase entityLiving,
                                   int timeLeft) {
    int charge = this.getMaxItemUseDuration(stack) - timeLeft;
    // 20 is minimum time that's required to finish the charge. It's
    // approximately the time required to finish the BOW aiming annimation.
    if (this.represent.twoHand && charge >= 20) {
      entityLiving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20));
    }
  }

  // The ability to set target that it hits on fire.
  @Override
  public boolean hitEntity(ItemStack stack,
                           EntityLivingBase target,
                           EntityLivingBase attacker) {
    if (this.represent.canSetFire) {
      target.setFire(5);
    }
    return super.hitEntity(stack, target, attacker);
  }

  // The ability to disable enemy's shield.
  @Override
  public boolean canDisableShield(ItemStack stack,
                                  ItemStack shield,
                                  EntityLivingBase entity,
                                  EntityLivingBase attacker) {
    return this.represent.canDisableShield;
  }
}
