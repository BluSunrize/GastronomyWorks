package blusunrize.gastronomyworks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class GWTags
{
	//ITEMS
	public static final TagKey<Item> flourWheat = TagKey.create(Registries.ITEM, forgeLoc("flour/wheat"));
	public static final TagKey<Item> vegetables = TagKey.create(Registries.ITEM, forgeLoc("vegetables"));
	public static final TagKey<Item> cookedMeats = TagKey.create(Registries.ITEM, forgeLoc("cooked_meats"));

	// FLUIDS
	public static final TagKey<Fluid> fluidDough = TagKey.create(Registries.FLUID, forgeLoc("dough"));
	public static final TagKey<Fluid> fluidSourdough = TagKey.create(Registries.FLUID, forgeLoc("sourdough"));
	public static final TagKey<Fluid> fluidMilkdough = TagKey.create(Registries.FLUID, forgeLoc("milkdough"));
	public static final TagKey<Fluid> fluidCustard = TagKey.create(Registries.FLUID, forgeLoc("custard"));
	public static final TagKey<Fluid> fluidStew = TagKey.create(Registries.FLUID, forgeLoc("stew"));


	private static ResourceLocation forgeLoc(String path)
	{
		return new ResourceLocation("forge", path);
	}
}
