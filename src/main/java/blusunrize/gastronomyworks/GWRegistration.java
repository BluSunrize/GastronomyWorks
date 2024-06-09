package blusunrize.gastronomyworks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Flowing;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Source;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static blusunrize.gastronomyworks.GastronomyWorks.rl;

public class GWRegistration
{
	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GastronomyWorks.MODID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register("tab", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup."+GastronomyWorks.MODID))
			.withTabsBefore(CreativeModeTabs.COMBAT)
			.icon(() -> Items.BAGUETTE.raw().asItem().getDefaultInstance())
			.displayItems((parameters, output) -> output.acceptAll(Items.BASIC_ITEMS.stream().map(deferred -> deferred.asItem().getDefaultInstance()).collect(Collectors.toList())))
			.build()
	);

	public static class Blocks
	{
		public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(GastronomyWorks.MODID);

		private static void init(IEventBus modEventBus)
		{
			REGISTER.register(modEventBus);
		}
	}

	public static class Items
	{
		public static final List<DeferredItem<Item>> BASIC_ITEMS = new ArrayList<>();
		public static final List<BakedGood> BAKED_GOODS = new ArrayList<>();
		public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(GastronomyWorks.MODID);
		public static final DeferredItem<Item> FLOUR = makeItem("flour", new Item.Properties());
		public static final DeferredItem<Item> SOURDOUGH_STARTER = makeItem("sourdough_starter", new Item.Properties());
		public static final BakedGood BAGUETTE = BakedGood.make("baguette", 5, 0.6f);

		public static final BakedGood BREAD = new BakedGood( //vanilla bread; nutrition 5, saturation 0.6f
				makeItem("bread_raw", new Properties()),
				net.minecraft.world.item.Items.BREAD
		);
		public static final BakedGood BREADROLL = BakedGood.make("breadroll", 3, 0.6f);
		public static final BakedGood SOURDOUGH_BREAD = BakedGood.make("sourdough_bread", 5, 1f);
		public static final BakedGood SOURDOUGH_BREADROLL = BakedGood.make("sourdough_breadroll", 3, 1f);

		public static final BakedGood MILK_BREAD = BakedGood.make("milk_bread", 6, 0.1f);

		public static final BakedGood CAKE_BASE = BakedGood.make("cake_base", 6, 0.1f);

		public static final DeferredItem<Item> LOAF_PAN = makeItem("loaf_pan", new Item.Properties().stacksTo(1));

		private static void init(IEventBus modEventBus)
		{
			REGISTER.register(modEventBus);
		}

		private static DeferredItem<Item> makeItem(String name, Item.Properties properties)
		{
			DeferredItem<Item> deferredItem = REGISTER.registerSimpleItem(name, properties);
			BASIC_ITEMS.add(deferredItem);
			return deferredItem;
		}

		public record BakedGood(ItemLike raw, ItemLike baked)
		{
			public BakedGood
			{
				BAKED_GOODS.add(this);
			}

			public static BakedGood make(String name, int nutrition, float saturation)
			{
				return new BakedGood(
						makeItem(name+"_raw", new Properties()),
						makeItem(name, new Properties().food(new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation).build()))
				);
			}
		}
	}

	public static class Fluids
	{
		public static final List<FluidEntry> ALL_FLUIDS = new ArrayList<>();

		public static final DeferredRegister<Fluid> REGISTER = DeferredRegister.create(BuiltInRegistries.FLUID, GastronomyWorks.MODID);

		public static final DeferredRegister<FluidType> TYPE_REGISTER = DeferredRegister.create(
				NeoForgeRegistries.Keys.FLUID_TYPES, GastronomyWorks.MODID
		);

		public static final FluidEntry DOUGH = FluidEntry.make("dough", doughProperties());
		public static final FluidEntry SOURDOUGH = FluidEntry.make("sourdough", doughProperties());
		public static final FluidEntry MILKDOUGH = FluidEntry.make("milkdough", doughProperties());
		public static final FluidEntry CUSTARD = FluidEntry.make("custard", doughProperties());


		private static void init(IEventBus modEventBus)
		{
			REGISTER.register(modEventBus);
			TYPE_REGISTER.register(modEventBus);
		}

		private static FluidType.Properties doughProperties()
		{
			return FluidType.Properties.create()
					.canSwim(false).canDrown(false).pathType(BlockPathTypes.STICKY_HONEY)
					.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
					.density(4000).viscosity(4000).lightLevel(0);
		}

		public record FluidEntry(
				DeferredHolder<FluidType, FluidType> type,
				DeferredHolder<Fluid, Fluid> still,
				DeferredHolder<Fluid, Fluid> flowing,
				DeferredItem<BucketItem> bucket
		)
		{

			public Fluid get()
			{
				return this.still.get();
			}

			private static FluidEntry make(String name, FluidType.Properties typeProperties)
			{
				return make("custard", rl("block/fluid/"+name), rl("block/fluid/"+name), typeProperties);
			}

			private static FluidEntry make(
					String name,
					ResourceLocation stillTex, ResourceLocation flowingTex,
					FluidType.Properties typeProperties
			)
			{
				DeferredHolder<FluidType, FluidType> type = TYPE_REGISTER.register(name, () -> new FluidType(typeProperties)
				{

					@Override
					public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
					{
						consumer.accept(new IClientFluidTypeExtensions()
						{
							@Override
							public ResourceLocation getStillTexture()
							{
								return stillTex;
							}

							@Override
							public ResourceLocation getFlowingTexture()
							{
								return flowingTex;
							}
						});
					}
				});
				final DeferredHolder<Fluid, Fluid> still = DeferredHolder.create(REGISTER.getRegistryKey(), rl(name));
				final DeferredHolder<Fluid, Fluid> flowing = DeferredHolder.create(REGISTER.getRegistryKey(), rl("flowing_"+name));
				DeferredItem<BucketItem> bucket = Items.REGISTER.register(
						name+"_bucket",
						() -> new BucketItem(still, new Properties().stacksTo(1).craftRemainder(net.minecraft.world.item.Items.BUCKET))
				);
				BaseFlowingFluid.Properties fluidProperties = new BaseFlowingFluid.Properties(type::value, still::value, flowing::value).bucket(bucket);
				FluidEntry entry = new FluidEntry(
						type,
						REGISTER.register(still.getId().getPath(), () -> new Source(fluidProperties)),
						REGISTER.register(flowing.getId().getPath(), () -> new Flowing(fluidProperties)),
						bucket
				);
				ALL_FLUIDS.add(entry);
				return entry;
			}

		}
	}

	public static void init(IEventBus modEventBus)
	{
		CREATIVE_TABS.register(modEventBus);
		Items.init(modEventBus);
		Fluids.init(modEventBus);
	}

	static void addCreative(BuildCreativeModeTabContentsEvent event)
	{
		if(event.getTabKey()==CreativeModeTabs.FOOD_AND_DRINKS)
			event.acceptAll(Items.BAKED_GOODS.stream().map(bakedGood -> bakedGood.baked().asItem().getDefaultInstance()).collect(Collectors.toList()));
		if(event.getTabKey()==CreativeModeTabs.INGREDIENTS)
			event.acceptAll(Fluids.ALL_FLUIDS.stream().map(fluidEntry -> fluidEntry.bucket.asItem().getDefaultInstance()).collect(Collectors.toList()));
	}
}
