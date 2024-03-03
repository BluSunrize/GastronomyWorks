package blusunrize.gastronomyworks.datagen;

import blusunrize.gastronomyworks.GWRegistration;
import blusunrize.gastronomyworks.GWTags;
import blusunrize.gastronomyworks.GastronomyWorks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class FluidTags extends FluidTagsProvider
{
	public FluidTags(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, GastronomyWorks.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider)
	{
		tag(GWTags.fluidDough).add(GWRegistration.Fluids.DOUGH.get());
	}
}
