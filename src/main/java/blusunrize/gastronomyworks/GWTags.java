package blusunrize.gastronomyworks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class GWTags
{
	public static final TagKey<Item> flourWheat = TagKey.create(Registries.ITEM, forgeLoc("flour/wheat"));
	public static final TagKey<Fluid> fluidDough = TagKey.create(Registries.FLUID, forgeLoc("dough"));
	public static final TagKey<Fluid> fluidSourdough = TagKey.create(Registries.FLUID, forgeLoc("sourdough"));


	private static ResourceLocation forgeLoc(String path)
	{
		return new ResourceLocation("forge", path);
	}
}
