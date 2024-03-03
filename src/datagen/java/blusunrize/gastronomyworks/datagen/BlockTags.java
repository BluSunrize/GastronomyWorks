package blusunrize.gastronomyworks.datagen;

import blusunrize.gastronomyworks.GastronomyWorks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlockTags extends BlockTagsProvider
{
	public BlockTags(PackOutput output, CompletableFuture<Provider> lookupProvider,
					 ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, GastronomyWorks.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider)
	{
	}
}
