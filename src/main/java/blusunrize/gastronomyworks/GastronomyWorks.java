package blusunrize.gastronomyworks;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GastronomyWorks.MODID)
public class GastronomyWorks
{
	public static final String MODID = "gastronomyworks";
	public static final Logger LOGGER = LogUtils.getLogger();

	public GastronomyWorks(IEventBus modEventBus)
	{
		GWRegistration.init(modEventBus);

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(GWRegistration::addCreative);
		NeoForge.EVENT_BUS.register(this);

		// we want milk as a fluid
		NeoForgeMod.enableMilkFluid();
	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	private void commonSetup(final FMLCommonSetupEvent event)
	{
	}

	@SubscribeEvent
	public void villagerTrades(VillagerTradesEvent event)
	{
		if(event.getType()==VillagerProfession.FARMER)
			event.getTrades().get(3).add((pTrader, pRandom) -> new MerchantOffer(
					new ItemStack(Items.EMERALD, 6),
					GWRegistration.Items.SOURDOUGH_STARTER.toStack(),
					1, 40, .05f
			));
	}
}
