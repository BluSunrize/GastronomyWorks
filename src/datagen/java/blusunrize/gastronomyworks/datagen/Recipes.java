package blusunrize.gastronomyworks.datagen;

import blusunrize.gastronomyworks.GWRegistration;
import blusunrize.gastronomyworks.GWTags;
import blusunrize.immersiveengineering.common.register.IEItems;
import blusunrize.immersiveengineering.data.recipes.builder.BottlingMachineRecipeBuilder;
import blusunrize.immersiveengineering.data.recipes.builder.CrusherRecipeBuilder;
import blusunrize.immersiveengineering.data.recipes.builder.MixerRecipeBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.concurrent.CompletableFuture;

import static blusunrize.gastronomyworks.GastronomyWorks.rl;

public class Recipes extends RecipeProvider
{
	private static final int quarter_bucket = FluidType.BUCKET_VOLUME/4;

	public Recipes(PackOutput packOutput, CompletableFuture<Provider> lookupProvider)
	{
		super(packOutput, lookupProvider);
	}

	@Override
	protected void buildRecipes(RecipeOutput consumer)
	{

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

		BottlingMachineRecipeBuilder.builder()
				.output(GWRegistration.Items.BAGUETTE)
				.output(IEItems.Molds.MOLD_ROD)
				.fluidInput(GWTags.fluidDough, quarter_bucket)
				.input(IEItems.Molds.MOLD_ROD)
				.build(consumer, rl("bottling/baguette"));
	}
}