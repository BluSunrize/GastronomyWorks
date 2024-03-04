package blusunrize.gastronomyworks.datagen;

import blusunrize.gastronomyworks.GWRegistration;
import blusunrize.gastronomyworks.GWRegistration.Items.BakedGood;
import blusunrize.gastronomyworks.GWTags;
import blusunrize.immersiveengineering.api.EnumMetals;
import blusunrize.immersiveengineering.api.IETags;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.data.recipes.builder.BlueprintCraftingRecipeBuilder;
import blusunrize.immersiveengineering.data.recipes.builder.BottlingMachineRecipeBuilder;
import blusunrize.immersiveengineering.data.recipes.builder.CrusherRecipeBuilder;
import blusunrize.immersiveengineering.data.recipes.builder.MixerRecipeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.fluids.FluidType;

import static blusunrize.gastronomyworks.GastronomyWorks.rl;

public class Recipes extends RecipeProvider
{
	private static final int half_bucket = FluidType.BUCKET_VOLUME/2;
	private static final int quarter_bucket = FluidType.BUCKET_VOLUME/4;

	public Recipes(PackOutput packOutput)
	{
		super(packOutput);
	}

	@Override
	protected void buildRecipes(RecipeOutput consumer)
	{
		Item hammer = BuiltInRegistries.ITEM.get(new ResourceLocation("immersiveengineering", "hammer"));
		Item rodMold = BuiltInRegistries.ITEM.get(new ResourceLocation("immersiveengineering", "mold_rod"));


		BlueprintCraftingRecipeBuilder.builder()
				.category("molds")
				.output(GWRegistration.Items.LOAF_PAN.asItem())
				.input(new IngredientWithSize(IETags.getTagsFor(EnumMetals.STEEL).plate, 3))
				.input(hammer)
				.build(consumer, rl("blueprint/mold_box"));

		CrusherRecipeBuilder.builder()
				.output(GWTags.flourWheat, 2)
				.input(Items.WHEAT)
				.setEnergy(800)
				.build(consumer, rl("crusher/flour"));

		MixerRecipeBuilder.builder()
				.output(GWRegistration.Fluids.DOUGH.get(), quarter_bucket)
				.fluidInput(FluidTags.WATER, quarter_bucket)
				.input(GWTags.flourWheat, 2)
				.setEnergy(800)
				.build(consumer, rl("mixer/dough"));

		MixerRecipeBuilder.builder()
				.output(GWRegistration.Fluids.SOURDOUGH.get(), quarter_bucket)
				.fluidInput(FluidTags.WATER, quarter_bucket)
				.input(GWTags.flourWheat, 2)
				.input(GWRegistration.Items.SOURDOUGH_STARTER, 1)
				.setEnergy(1200)
				.build(consumer, rl("mixer/sourdough_from_starter"));
		MixerRecipeBuilder.builder()
				.output(GWRegistration.Fluids.SOURDOUGH.get(), half_bucket)
				.fluidInput(GWTags.fluidSourdough, quarter_bucket)
				.input(GWTags.flourWheat, 2)
				.setEnergy(800)
				.build(consumer, rl("mixer/sourdough"));

		BottlingMachineRecipeBuilder.builder()
				.output(GWRegistration.Items.BAGUETTE.raw())
				.output(rodMold)
				.fluidInput(GWTags.fluidDough, quarter_bucket)
				.input(rodMold)
				.build(consumer, rl("bottling/baguette"));

		BottlingMachineRecipeBuilder.builder()
				.output(GWRegistration.Items.BREAD.raw())
				.output(GWRegistration.Items.LOAF_PAN)
				.fluidInput(GWTags.fluidDough, quarter_bucket)
				.input(GWRegistration.Items.LOAF_PAN)
				.build(consumer, rl("bottling/bread"));

		BottlingMachineRecipeBuilder.builder()
				.output(GWRegistration.Items.SOURDOUGH_BREAD.raw())
				.output(GWRegistration.Items.LOAF_PAN)
				.fluidInput(GWTags.fluidSourdough, quarter_bucket)
				.input(GWRegistration.Items.LOAF_PAN)
				.build(consumer, rl("bottling/sourdough_bread"));

		for(BakedGood bakedGood : GWRegistration.Items.BAKED_GOODS)
			addFoodCookingRecipe(consumer, bakedGood.raw(), bakedGood.baked());
	}

	private static String name(ItemLike item)
	{
		return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
	}

	private static void addFoodCookingRecipe(RecipeOutput consumer, ItemLike input, ItemLike output)
	{
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.FOOD, output, 0.35f, 200)
				.unlockedBy("has_"+name(input), has(input))
				.save(consumer, rl("cooking/"+name(output)));
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(input), RecipeCategory.FOOD, output, 0.35f, 100)
				.unlockedBy("has_"+name(input), has(input))
				.save(consumer, rl("cooking/"+name(output)+"_from_smoking"));
	}
}