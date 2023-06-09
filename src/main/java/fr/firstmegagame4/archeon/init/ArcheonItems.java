package fr.firstmegagame4.archeon.init;

import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import com.mmodding.mmodding_lib.library.items.*;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.settings.ItemFinishUsing;
import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalKeyItem;
import fr.firstmegagame4.archeon.Archeon;
import fr.firstmegagame4.archeon.entities.NatureCoreEntity;
import fr.firstmegagame4.archeon.items.PouchItem;
import fr.firstmegagame4.archeon.materials.armor.ApafloriteArmor;
import fr.firstmegagame4.archeon.materials.armor.ClothesTunic;
import fr.firstmegagame4.archeon.materials.armor.FaeliteArmor;
import fr.firstmegagame4.archeon.materials.tool.ApafloriteTool;
import fr.firstmegagame4.archeon.materials.tool.FaeliteTool;
import fr.firstmegagame4.archeon.materials.tool.MassacreDaggerTool;
import fr.firstmegagame4.archeon.materials.tool.QolmTool;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ArcheonItems implements ElementsInitializer {

	public static final CustomSquaredPortalKeyItem WAND_OF_NATURE = new CustomSquaredPortalKeyItem(new AdvancedItemSettings(), Archeon.createId("portal"), SoundEvents.ITEM_FLINTANDSTEEL_USE);

	public static final CustomPickaxeItem QOLM_PICK = new CustomPickaxeItem(QolmTool.INSTANCE, 1, -2.5f, new AdvancedItemSettings().fireproof());

	public static final CustomSwordItem MASSACRE_DAGGER = new CustomSwordItem(MassacreDaggerTool.INSTANCE, 3, -0.5f,
		new AdvancedItemSettings().fireproof().itemPostHit((stack, target, attacker) -> {
			World world = target.getWorld();
			world.syncWorldEvent(2001, target.getBlockPos(), Block.getRawIdFromState(Blocks.FIRE_CORAL_BLOCK.getDefaultState()));
			target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 60));
			target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60));
			world.syncWorldEvent(2001, target.getBlockPos(), Block.getRawIdFromState(Blocks.FIRE_CORAL_BLOCK.getDefaultState()));
		})
	);

	public static final CustomItem POWER_KEY = new CustomItem(new AdvancedItemSettings().maxCount(1).rarity(Rarity.RARE).itemUseOnBlock(context -> {
		BlockState state = context.getWorld().getBlockState(context.getBlockPos());
		if (state.isOf(ArcheonBlocks.POWER_KEYSTONE) && context.getPlayer() != null) {
			NatureCoreEntity natureCoreEntity = new NatureCoreEntity(ArcheonEntities.NATURE_CORE, context.getWorld());
			float x = context.getBlockPos().getX() + 0.5f;
			float y = context.getBlockPos().getY() + 2.5f;
			float z = context.getBlockPos().getZ() + 0.5f;
			natureCoreEntity.setPosition(x, y, z);
			natureCoreEntity.onSummoned();
			context.getWorld().spawnEntity(natureCoreEntity);
			if(!context.getPlayer().isCreative()) context.getPlayer().getInventory().removeOne(context.getPlayer().getStackInHand(context.getHand()));
		}
	}));

	public static final CustomSwordItem NECLANE_SWORD = new CustomSwordItem(ToolMaterials.WOOD, 3, -2.4f, new AdvancedItemSettings());
	public static final CustomPickaxeItem NECLANE_PICKAXE = new CustomPickaxeItem(ToolMaterials.WOOD, 1, -2.8f, new AdvancedItemSettings());
	public static final CustomAxeItem NECLANE_AXE = new CustomAxeItem(ToolMaterials.WOOD, 6.0f, -3.2f, new AdvancedItemSettings());
	public static final CustomShovelItem NECLANE_SHOVEL = new CustomShovelItem(ToolMaterials.WOOD, 1.5f, -3.0f, new AdvancedItemSettings());
	public static final CustomHoeItem NECLANE_HOE = new CustomHoeItem(ToolMaterials.WOOD, 0, -3.0f, new AdvancedItemSettings());

	public static final CustomSwordItem CYPRESS_SWORD = new CustomSwordItem(ToolMaterials.WOOD, 3, -2.4f, new AdvancedItemSettings());
	public static final CustomPickaxeItem CYPRESS_PICKAXE = new CustomPickaxeItem(ToolMaterials.WOOD, 1, -2.8f, new AdvancedItemSettings());
	public static final CustomAxeItem CYPRESS_AXE = new CustomAxeItem(ToolMaterials.WOOD, 6.0f, -3.2f, new AdvancedItemSettings());
	public static final CustomShovelItem CYPRESS_SHOVEL = new CustomShovelItem(ToolMaterials.WOOD, 1.5f, -3.0f, new AdvancedItemSettings());
	public static final CustomHoeItem CYPRESS_HOE = new CustomHoeItem(ToolMaterials.WOOD, 0, -3.0f, new AdvancedItemSettings());

	public static final CustomSwordItem VUXANCIA_SWORD = new CustomSwordItem(ToolMaterials.WOOD, 3, -2.4f, new AdvancedItemSettings());
	public static final CustomPickaxeItem VUXANCIA_PICKAXE = new CustomPickaxeItem(ToolMaterials.WOOD, 1, -2.8f, new AdvancedItemSettings());
	public static final CustomAxeItem VUXANCIA_AXE = new CustomAxeItem(ToolMaterials.WOOD, 6.0f, -3.2f, new AdvancedItemSettings());
	public static final CustomShovelItem VUXANCIA_SHOVEL = new CustomShovelItem(ToolMaterials.WOOD, 1.5f, -3.0f, new AdvancedItemSettings());
	public static final CustomHoeItem VUXANCIA_HOE = new CustomHoeItem(ToolMaterials.WOOD, 0, -3.0f, new AdvancedItemSettings());

	public static final CustomSwordItem SOUTHSTONE_SWORD = new CustomSwordItem(ToolMaterials.STONE, 3, -2.4f, new AdvancedItemSettings());
	public static final CustomPickaxeItem SOUTHSTONE_PICKAXE = new CustomPickaxeItem(ToolMaterials.STONE, 1, -2.8f, new AdvancedItemSettings());
	public static final CustomAxeItem SOUTHSTONE_AXE = new CustomAxeItem(ToolMaterials.STONE, 7.0f, -3.2f, new AdvancedItemSettings());
	public static final CustomShovelItem SOUTHSTONE_SHOVEL = new CustomShovelItem(ToolMaterials.STONE, 1.5f, -3.0f, new AdvancedItemSettings());
	public static final CustomHoeItem SOUTHSTONE_HOE = new CustomHoeItem(ToolMaterials.STONE, -1, -2.0f, new AdvancedItemSettings());

	public static final CustomSwordItem APAFLORITE_SABER = new CustomSwordItem(ApafloriteTool.INSTANCE, 5, -0.8f, new AdvancedItemSettings());
	public static final CustomPickaxeItem APAFLORITE_PICKAXE = new CustomPickaxeItem(ApafloriteTool.INSTANCE, 1, -3.1f, new AdvancedItemSettings());
	public static final CustomAxeItem APAFLORITE_AXE = new CustomAxeItem(ApafloriteTool.INSTANCE, 6, -3.1f, new AdvancedItemSettings());
	public static final CustomShovelItem APAFLORITE_SHOVEL = new CustomShovelItem(ApafloriteTool.INSTANCE, 1, -3.1f, new AdvancedItemSettings());
	public static final CustomHoeItem APAFLORITE_HOE = new CustomHoeItem(ApafloriteTool.INSTANCE, 1, -3.1f, new AdvancedItemSettings());

	public static final CustomArmorItem APAFLORITE_HELMET = new CustomArmorItem(ApafloriteArmor.INSTANCE, EquipmentSlot.HEAD, new AdvancedItemSettings());
	public static final CustomArmorItem APAFLORITE_CHESTPLATE = new CustomArmorItem(ApafloriteArmor.INSTANCE, EquipmentSlot.CHEST, new AdvancedItemSettings());
	public static final CustomArmorItem APAFLORITE_LEGGINGS = new CustomArmorItem(ApafloriteArmor.INSTANCE, EquipmentSlot.LEGS, new AdvancedItemSettings());
	public static final CustomArmorItem APAFLORITE_BOOTS = new CustomArmorItem(ApafloriteArmor.INSTANCE, EquipmentSlot.FEET, new AdvancedItemSettings());

	public static final CustomSwordItem FAELITE_SWORD = new CustomSwordItem(FaeliteTool.INSTANCE, 7, -2.4f, new AdvancedItemSettings());
	public static final CustomPickaxeItem FAELITE_PICKAXE = new CustomPickaxeItem(FaeliteTool.INSTANCE, 1, -3.1f, new AdvancedItemSettings());
	public static final CustomAxeItem FAELITE_AXE = new CustomAxeItem(FaeliteTool.INSTANCE, 8, -3.1f, new AdvancedItemSettings());
	public static final CustomShovelItem FAELITE_SHOVEL = new CustomShovelItem(FaeliteTool.INSTANCE, 1, -3.1f, new AdvancedItemSettings());
	public static final CustomHoeItem FAELITE_HOE = new CustomHoeItem(FaeliteTool.INSTANCE, 1, -3.1f, new AdvancedItemSettings());

	public static final CustomArmorItem FAELITE_HELMET = new CustomArmorItem(FaeliteArmor.INSTANCE, EquipmentSlot.HEAD, new AdvancedItemSettings());
	public static final CustomArmorItem FAELITE_CHESTPLATE = new CustomArmorItem(FaeliteArmor.INSTANCE, EquipmentSlot.CHEST, new AdvancedItemSettings());
	public static final CustomArmorItem FAELITE_LEGGINGS = new CustomArmorItem(FaeliteArmor.INSTANCE, EquipmentSlot.LEGS, new AdvancedItemSettings());
	public static final CustomArmorItem FAELITE_BOOTS = new CustomArmorItem(FaeliteArmor.INSTANCE, EquipmentSlot.FEET, new AdvancedItemSettings());

	public static final CustomFishingRodItem EXYRIANE_FISHING_ROD = new CustomFishingRodItem(new AdvancedItemSettings());

	public static final CustomArmorItem CLOTHES_CHESTPLATE = new CustomArmorItem(ClothesTunic.INSTANCE, EquipmentSlot.CHEST, new AdvancedItemSettings());
	public static final CustomArmorItem CLOTHES_LEGGINGS = new CustomArmorItem(ClothesTunic.INSTANCE, EquipmentSlot.LEGS, new AdvancedItemSettings());

	public static final CustomItem MANUSCRIPT = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem APAFLORITE_GEMSTONE = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem EXYRIANE_CRYSTAL = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem RAW_FAELITE = new CustomItem(new AdvancedItemSettings());
	public static final CustomItem FAELITE_INGOT = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem PLANT_FIBER = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem MOSS_BALL = new CustomItem(new AdvancedItemSettings().food(1, 0.5f));

	public static final CustomItem BLUE_SHELL = new CustomItem(new AdvancedItemSettings());
	public static final CustomItem PINK_SHELL = new CustomItem(new AdvancedItemSettings());
	public static final CustomItem YELLOW_SHELL = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem GOBLET = new CustomItem(new AdvancedItemSettings());

	public static final ItemFinishUsing GOBLET_USE = (stack, world, user) -> (stack.isEmpty() ? new ItemStack(ArcheonItems.GOBLET) : stack);

	public static final CustomItem GOBLET_WATER = new CustomItem(new AdvancedItemSettings().maxCount(1)
		.food(0, 0.0f, false, true).drinkable().itemFinishUsing(GOBLET_USE));

	public static final CustomItem GOBLET_COCONUT_MILK = new CustomItem(new AdvancedItemSettings().maxCount(1)
		.food(0, 0.0f, false, true).drinkable().itemFinishUsing((stack, world, user) -> {
			if (!world.isClient()) {
				user.clearStatusEffects();
			}
			return GOBLET_USE.apply(stack, world, user);
		}));

	public static final CustomItem GOBLET_BLOOD_ORANGE_JUICE = new CustomItem(new AdvancedItemSettings().maxCount(1)
		.food(4, 0.5f).drinkable().itemFinishUsing(GOBLET_USE));

	public static final CustomItem JAM_POT = new CustomItem(new AdvancedItemSettings());

	public static final ItemFinishUsing JAM_POT_USE = (stack, world, user) -> (stack.isEmpty() ? new ItemStack(ArcheonItems.JAM_POT) : stack);

	public static final CustomItem ORANGE_LYCORIS_JAM_POT = new CustomItem(new AdvancedItemSettings().maxCount(1).food(
		new FoodComponent.Builder()
			.hunger(15)
			.saturationModifier(3.0f)
			.alwaysEdible()
			.snack()
			.statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 1000), 1.0f)
			.build()
	).drinkable().itemFinishUsing(JAM_POT_USE));

	public static final CustomItem RED_LYCORIS_JAM_POT = new CustomItem(new AdvancedItemSettings().maxCount(1).food(
		new FoodComponent.Builder()
			.hunger(15)
			.saturationModifier(3.0f)
			.alwaysEdible()
			.snack()
			.statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 1000, 1), 1.0f)
			.build()
	).drinkable().itemFinishUsing(JAM_POT_USE));

	public static final CustomItem PINK_LYCORIS_JAM_POT = new CustomItem(new AdvancedItemSettings().maxCount(1).food(
		new FoodComponent.Builder()
			.hunger(15)
			.saturationModifier(3.0f)
			.alwaysEdible()
			.snack()
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1000, 1), 1.0f)
			.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1000, 1), 1.0f)
			.build()
	).drinkable().itemFinishUsing(JAM_POT_USE));

	public static final CustomItem ORANGE_LYCORIS_PETAL = new CustomItem(new AdvancedItemSettings().food(3, 0.2f));

	public static final CustomItem RED_LYCORIS_PETAL = new CustomItem(new AdvancedItemSettings().food(3, 0.2f));

	public static final CustomItem PINK_LYCORIS_PETAL = new CustomItem(new AdvancedItemSettings().food(3, 0.2f));

	public static final CustomMusicDiscItem PE_4K = new CustomMusicDiscItem(new AdvancedItemSettings().maxCount(1).rarity(Rarity.RARE),
		new SoundEvent(Archeon.createId("pe_4k")), 0, 116);

	public static final CustomMusicDiscItem GEOMETRIC_FALL = new CustomMusicDiscItem(new AdvancedItemSettings().maxCount(1).rarity(Rarity.RARE),
		new SoundEvent(Archeon.createId("geometric_fall")), 0, 74);

	public static final CustomMusicDiscItem RISING = new CustomMusicDiscItem(new AdvancedItemSettings().maxCount(1).rarity(Rarity.RARE),
		new SoundEvent(Archeon.createId("rising")), 0, 90);

	public static final CustomMusicDiscItem PARALLELIFY = new CustomMusicDiscItem(new AdvancedItemSettings().maxCount(1).rarity(Rarity.RARE),
		new SoundEvent(Archeon.createId("parallelify")), 0, 101);

	public static final CustomMusicDiscItem RESIDE = new CustomMusicDiscItem(new AdvancedItemSettings().maxCount(1).rarity(Rarity.RARE),
		new SoundEvent(Archeon.createId("reside")), 0, 165);

	public static final CustomMusicDiscItem ARCHEON_CHRISTMAS = new CustomMusicDiscItem(new AdvancedItemSettings().maxCount(1).rarity(Rarity.RARE),
		new SoundEvent(Archeon.createId("archeon_christmas")), 0, 165);

	public static final CustomMusicDiscItem DEVOURING = new CustomMusicDiscItem(new AdvancedItemSettings().maxCount(1).rarity(Rarity.RARE),
		new SoundEvent(Archeon.createId("glaive")), 0, 176);

	public static final CustomItem RECORD_FRAGMENT = new CustomItem(new AdvancedItemSettings().maxCount(16));

	public static final CustomItem SALT = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem RAW_HEIFER = new CustomItem(new AdvancedItemSettings().food(2, 0.3f, true));
	public static final CustomItem COOKED_HEIFER = new CustomItem(new AdvancedItemSettings().food(4, 1.5f, true));

	public static final CustomItem RAW_SUNSTRADIVER_CHOP = new CustomItem(new AdvancedItemSettings().food(2, 0.3f, true));
	public static final CustomItem COOKED_SUNSTRADIVER_CHOP = new CustomItem(new AdvancedItemSettings().food(7, 1.0f, true));

	public static final CustomItem PUMPKIN_PIE_WITH_LYCORIS_JAM = new CustomItem(new AdvancedItemSettings().maxCount(1).food(20, 20.0f));

	public static final CustomItem BLOOD_ORANGE = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem GRAPE = new CustomItem(new AdvancedItemSettings().food(1, 0.3f));

	public static final CustomItem GOLDEN_GRAPE = new CustomItem(new AdvancedItemSettings().food(
		new FoodComponent.Builder()
			.hunger(5)
			.saturationModifier(2.5f)
			.alwaysEdible()
			.statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 160, 1), 1.0f)
			.statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 0, 3), 1.0f)
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 160, 1), 1.0f)
			.build()
	));

	public static final CustomItem GEAR = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem HERBICIDE = new CustomItem(new AdvancedItemSettings().maxCount(1).eatable()
		.descriptionLines(Text.of("§7Don't use this.")).itemDropped((stack, world, user, droppedItem) -> world.createExplosion(
				droppedItem,
				droppedItem.getX(),
				droppedItem.getY(),
				droppedItem.getZ(),
				3,
				Explosion.DestructionType.NONE
		)));

	public static final CustomItem INK_BOTTLE = new CustomItem(new AdvancedItemSettings().maxCount(16));

	// TODO : Pouch GUI
	public static final PouchItem POUCH = new PouchItem(new AdvancedItemSettings());

	public static final CustomItem SUNSTRADIVER_FEATHER = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem SNAIL_SHELL = new CustomItem(new AdvancedItemSettings());

	public static final CustomItem AURORA_CRYSTAL = new CustomItem(new AdvancedItemSettings());

	public static final CustomSpawnEggItem SNAIL_SPAWN_EGG = new CustomSpawnEggItem(ArcheonEntities.SNAIL, 7578818, 1982583, new AdvancedItemSettings());
	public static final CustomSpawnEggItem SUNSTRADIVER_SPAWN_EGG = new CustomSpawnEggItem(ArcheonEntities.SUNSTRADIVER, 52429, 13210, new AdvancedItemSettings());
	public static final CustomSpawnEggItem HEIFER_SPAWN_EGG = new CustomSpawnEggItem(ArcheonEntities.HEIFER, 4478842, 3097203, new AdvancedItemSettings());

	@Override
	public void register() {
		WAND_OF_NATURE.register(Archeon.createId("wand_of_nature"));
		QOLM_PICK.register(Archeon.createId("qolm_pick"));
		MASSACRE_DAGGER.register(Archeon.createId("massacre_dagger"));
		POWER_KEY.register(Archeon.createId("power_key"));
		NECLANE_SWORD.register(Archeon.createId("neclane_sword"));
		NECLANE_PICKAXE.register(Archeon.createId("neclane_pickaxe"));
		NECLANE_AXE.register(Archeon.createId("neclane_axe"));
		NECLANE_SHOVEL.register(Archeon.createId("neclane_shovel"));
		NECLANE_HOE.register(Archeon.createId("neclane_hoe"));
		CYPRESS_SWORD.register(Archeon.createId("cypress_sword"));
		CYPRESS_PICKAXE.register(Archeon.createId("cypress_pickaxe"));
		CYPRESS_AXE.register(Archeon.createId("cypress_axe"));
		CYPRESS_SHOVEL.register(Archeon.createId("cypress_shovel"));
		CYPRESS_HOE.register(Archeon.createId("cypress_hoe"));
		VUXANCIA_SWORD.register(Archeon.createId("vuxancia_sword"));
		VUXANCIA_PICKAXE.register(Archeon.createId("vuxancia_pickaxe"));
		VUXANCIA_AXE.register(Archeon.createId("vuxancia_axe"));
		VUXANCIA_SHOVEL.register(Archeon.createId("vuxancia_shovel"));
		VUXANCIA_HOE.register(Archeon.createId("vuxancia_hoe"));
		SOUTHSTONE_SWORD.register(Archeon.createId("southstone_sword"));
		SOUTHSTONE_PICKAXE.register(Archeon.createId("southstone_pickaxe"));
		SOUTHSTONE_AXE.register(Archeon.createId("southstone_axe"));
		SOUTHSTONE_SHOVEL.register(Archeon.createId("southstone_shovel"));
		SOUTHSTONE_HOE.register(Archeon.createId("southstone_hoe"));
		APAFLORITE_SABER.register(Archeon.createId("apaflorite_saber"));
		APAFLORITE_PICKAXE.register(Archeon.createId("apaflorite_pickaxe"));
		APAFLORITE_AXE.register(Archeon.createId("apaflorite_axe"));
		APAFLORITE_SHOVEL.register(Archeon.createId("apaflorite_shovel"));
		APAFLORITE_HOE.register(Archeon.createId("apaflorite_hoe"));
		APAFLORITE_HELMET.register(Archeon.createId("apaflorite_helmet"));
		APAFLORITE_CHESTPLATE.register(Archeon.createId("apaflorite_chestplate"));
		APAFLORITE_LEGGINGS.register(Archeon.createId("apaflorite_leggings"));
		APAFLORITE_BOOTS.register(Archeon.createId("apaflorite_boots"));
		FAELITE_SWORD.register(Archeon.createId("faelite_sword"));
		FAELITE_PICKAXE.register(Archeon.createId("faelite_pickaxe"));
		FAELITE_AXE.register(Archeon.createId("faelite_axe"));
		FAELITE_SHOVEL.register(Archeon.createId("faelite_shovel"));
		FAELITE_HOE.register(Archeon.createId("faelite_hoe"));
		FAELITE_HELMET.register(Archeon.createId("faelite_helmet"));
		FAELITE_CHESTPLATE.register(Archeon.createId("faelite_chestplate"));
		FAELITE_LEGGINGS.register(Archeon.createId("faelite_leggings"));
		FAELITE_BOOTS.register(Archeon.createId("faelite_boots"));
		EXYRIANE_FISHING_ROD.register(Archeon.createId("exyriane_fishing_rod"));
		CLOTHES_CHESTPLATE.register(Archeon.createId("clothes_chestplate"));
		CLOTHES_LEGGINGS.register(Archeon.createId("clothes_leggings"));
		MANUSCRIPT.register(Archeon.createId("manuscript"));
		APAFLORITE_GEMSTONE.register(Archeon.createId("apaflorite_gemstone"));
		EXYRIANE_CRYSTAL.register(Archeon.createId("exyriane_crystal"));
		RAW_FAELITE.register(Archeon.createId("raw_faelite"));
		FAELITE_INGOT.register(Archeon.createId("faelite_ingot"));
		PLANT_FIBER.register(Archeon.createId("plant_fiber"));
		MOSS_BALL.register(Archeon.createId("moss_ball"));
		BLUE_SHELL.register(Archeon.createId("blue_shell"));
		PINK_SHELL.register(Archeon.createId("pink_shell"));
		YELLOW_SHELL.register(Archeon.createId("yellow_shell"));
		GOBLET.register(Archeon.createId("goblet"));
		GOBLET_WATER.register(Archeon.createId("goblet_water"));
		GOBLET_COCONUT_MILK.register(Archeon.createId("goblet_coconut_milk"));
		GOBLET_BLOOD_ORANGE_JUICE.register(Archeon.createId("goblet_blood_orange_juice"));
		JAM_POT.register(Archeon.createId("jam_pot"));
		ORANGE_LYCORIS_JAM_POT.register(Archeon.createId("orange_lycoris_jam_pot"));
		RED_LYCORIS_JAM_POT.register(Archeon.createId("red_lycoris_jam_pot"));
		PINK_LYCORIS_JAM_POT.register(Archeon.createId("pink_lycoris_jam_pot"));
		ORANGE_LYCORIS_PETAL.register(Archeon.createId("orange_lycoris_petal"));
		RED_LYCORIS_PETAL.register(Archeon.createId("red_lycoris_petal"));
		PINK_LYCORIS_PETAL.register(Archeon.createId("pink_lycoris_petal"));
		PE_4K.register(Archeon.createId("pe_4k"));
		GEOMETRIC_FALL.register(Archeon.createId("geometric_fall"));
		RISING.register(Archeon.createId("rising"));
		PARALLELIFY.register(Archeon.createId("parallelify"));
		RESIDE.register(Archeon.createId("reside"));
		ARCHEON_CHRISTMAS.register(Archeon.createId("archeon_christmas"));
		DEVOURING.register(Archeon.createId("devouring"));
		RECORD_FRAGMENT.register(Archeon.createId("record_fragment"));
		SALT.register(Archeon.createId("salt"));
		RAW_HEIFER.register(Archeon.createId("raw_heifer"));
		COOKED_HEIFER.register(Archeon.createId("cooked_heifer"));
		RAW_SUNSTRADIVER_CHOP.register(Archeon.createId("raw_sunstradiver_chop"));
		COOKED_SUNSTRADIVER_CHOP.register(Archeon.createId("cooked_sunstradiver_chop"));
		PUMPKIN_PIE_WITH_LYCORIS_JAM.register(Archeon.createId("pumpkin_pie_with_lycoris_jam"));
		BLOOD_ORANGE.register(Archeon.createId("blood_orange"));
		GRAPE.register(Archeon.createId("grape"));
		GOLDEN_GRAPE.register(Archeon.createId("golden_grape"));
		GEAR.register(Archeon.createId("gear"));
		HERBICIDE.register(Archeon.createId("herbicide"));
		INK_BOTTLE.register(Archeon.createId("ink_bottle"));
		POUCH.register(Archeon.createId("pouch"));
		SUNSTRADIVER_FEATHER.register(Archeon.createId("sunstradiver_feather"));
		SNAIL_SHELL.register(Archeon.createId("snail_shell"));
		AURORA_CRYSTAL.register(Archeon.createId("aurora_crystal"));
		SNAIL_SPAWN_EGG.register(Archeon.createId("snail_spawn_egg"));
		SUNSTRADIVER_SPAWN_EGG.register(Archeon.createId("sunstradiver_spawn_egg"));
		HEIFER_SPAWN_EGG.register(Archeon.createId("heifer_spawn_egg"));
	}
}
