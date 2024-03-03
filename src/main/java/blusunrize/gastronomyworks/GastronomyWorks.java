package blusunrize.gastronomyworks;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
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

        // we want milk as a fluid
        NeoForgeMod.enableMilkFluid();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MODID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
}
