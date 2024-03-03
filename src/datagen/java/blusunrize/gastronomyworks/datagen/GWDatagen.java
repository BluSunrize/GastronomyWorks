package blusunrize.gastronomyworks.datagen;

import blusunrize.gastronomyworks.GastronomyWorks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = GastronomyWorks.MODID, bus = Bus.MOD)
public class GWDatagen
{
	@SubscribeEvent
	public static void generate(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(event.includeClient(), new ItemModels(packOutput, event.getExistingFileHelper()));

		generator.addProvider(event.includeServer(), new Recipes(packOutput));

		BlockTags blockTags = new BlockTags(packOutput, lookupProvider, event.getExistingFileHelper());
		generator.addProvider(event.includeServer(), blockTags);
		generator.addProvider(event.includeServer(), new ItemTags(packOutput, lookupProvider, blockTags.contentsGetter(), event.getExistingFileHelper()));
		generator.addProvider(event.includeServer(), new FluidTags(packOutput, lookupProvider, event.getExistingFileHelper()));
	}
}
