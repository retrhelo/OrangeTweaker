package net.orangetweaker.vanilla.items;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.GeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.IGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.ModelType;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.util.files.templates.TemplateFile;
import com.teamacronymcoders.base.util.files.templates.TemplateManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.orangetweaker.zen.representation.ItemRepresentation;

/**
 * The backend data structure for ItemRepresentation.
 */
public class ItemContent extends ItemBase {
  private ItemRepresentation itemRepresentation;
  private EnumRarity rarity;

  public ItemContent(ItemRepresentation itemRepresentation) {
    super(itemRepresentation.unlocalizedName);
    this.itemRepresentation = itemRepresentation;
    checkFields();
    setFields();
  }

  private void checkFields() {
    if (null == this.itemRepresentation.unlocalizedName) {
      throw new RuntimeException(
          "Content ItemRepresentation is missing fields ['unlocalizedName']");
    }
  }

  private void setFields() {
    this.setUnlocalizedName(itemRepresentation.unlocalizedName);
    if (null != this.itemRepresentation.creativeTab) {
      Object creativeTab = this.itemRepresentation.creativeTab.getInternal();
      if (creativeTab instanceof CreativeTabs) {
        this.setCreativeTab((CreativeTabs)creativeTab);
      }
    } else {
      // Setting to CreativeTab MISC by default.
      this.setCreativeTab(CreativeTabs.MISC);
    }
    this.setMaxStackSize(this.itemRepresentation.maxStackSize);
    this.rarity = Enum.valueOf(EnumRarity.class,
                               this.itemRepresentation.rarity);
  }

  @Override
  public String getUnlocalizedName() {
    return this.itemRepresentation.getUnlocalizedName();
  }

  @Override
  public EnumRarity getRarity(ItemStack itemStack) {
    return this.rarity;
  }

  @Override
  public float getSmeltingExperience(ItemStack itemStack) {
    return this.itemRepresentation.smeltingExperience;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack itemStack) {
    return this.itemRepresentation.isGlowing || super.hasEffect(itemStack);
  }

  /**
   * Generate the path to Item's model.
   */
  @Override
  public List<IGeneratedModel> getGeneratedModels() {
    List<IGeneratedModel> models = Lists.newArrayList();
    TemplateFile templateFile = TemplateManager.getTemplateFile("item_model");
    Map<String, String> replacements = Maps.newHashMap();

    replacements.put(
        "texture", "orangetweaker:items/" + this.getUnlocalizedName());
    templateFile.replaceContents(replacements);
    models.add(new GeneratedModel(this.getUnlocalizedName(),
                                  ModelType.ITEM_MODEL,
                                  templateFile.getFileContents()));
    return models;
  }
}
