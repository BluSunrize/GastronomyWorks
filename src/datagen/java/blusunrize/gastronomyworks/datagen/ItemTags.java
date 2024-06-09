package blusunrize.gastronomyworks.datagen;

import blusunrize.gastronomyworks.GWRegistration;
import blusunrize.gastronomyworks.GWTags;
import blusunrize.gastronomyworks.GastronomyWorks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider
{
	public ItemTags(PackOutput output, CompletableFuture<Provider> lookupProvider,
					CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider,
					ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, blockTagProvider, GastronomyWorks.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider)
	{
		tag(GWTags.flourWheat).add(GWRegistration.Items.FLOUR.get());
		tag(GWTags.vegetables)
				.add(Items.BEETROOT, Items.CARROT, Items.POTATO)
				.add(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
		tag(GWTags.cookedMeats)
				.add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT)
				.add(Items.COOKED_COD, Items.COOKED_SALMON); //yeah sure, fish is allowed
	}
}
