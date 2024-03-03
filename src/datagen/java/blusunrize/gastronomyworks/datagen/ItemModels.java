package blusunrize.gastronomyworks.datagen;

import blusunrize.gastronomyworks.GWRegistration;
import blusunrize.gastronomyworks.GWRegistration.Fluids.FluidEntry;
import blusunrize.gastronomyworks.GWRegistration.Items;
import blusunrize.gastronomyworks.GastronomyWorks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider
{
	public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper)
	{
		super(output, GastronomyWorks.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		basicItem(Items.FLOUR.asItem());
		basicItem(Items.BAGUETTE.asItem());

		for(FluidEntry fluid : GWRegistration.Fluids.ALL_FLUIDS)
			withExistingParent(fluid.bucket().getId().getPath(), new ResourceLocation("neoforge", "item/bucket"))
					.customLoader(DynamicFluidContainerModelBuilder::begin)
					.fluid(fluid.get());
	}
}