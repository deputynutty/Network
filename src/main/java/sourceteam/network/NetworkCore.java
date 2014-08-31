package sourceteam.network;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sourceteam.network.Fmp.Multipart;
import sourceteam.network.app.AppManager;
import sourceteam.network.client.gui.GuiHandler;
import sourceteam.network.dataSystems.itemSystem.ItemSystem;
import sourceteam.network.entity.minecart.ServerCart;
import sourceteam.network.event.DropItemEvent;
import sourceteam.network.init.initBlocks;
import sourceteam.network.init.initCommand;
import sourceteam.network.init.initConfig;
import sourceteam.network.init.initItems;
import sourceteam.network.items.NetworkItems;
import sourceteam.network.netty.ChannelHandler;
import sourceteam.network.netty.PacketPipeline;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import sourceteam.mods.core.client.BaseModGui;
import sourceteam.mods.core.mod.ModRegistry;
import sourceteam.mods.lib.mod.ISourceMod;

import java.util.logging.Logger;

@Mod(modid = "network", name = "Network", version = "0.01", dependencies = "required-after:sourcecore;required-after:CodeChickenCore;required-after:NotEnoughItems;required-after:ForgeMultipart")
public class NetworkCore implements ISourceMod {

    public static final PacketPipeline packetPipeline = new PacketPipeline();
    @SidedProxy(clientSide = "sourceteam.network.ClientProxy", serverSide = "sourceteam.network.CommonProxy")
    public static CommonProxy proxy;
    @Instance("network")
    public static NetworkCore instance;
    public static Logger networkLog = Logger.getLogger("Network");

    public static CreativeTabs Network = new CreativeTabs("network") {
        @Override
        public Item getTabIconItem() {
            return NetworkItems.tablet;
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }

        ;
    }.setBackgroundImageName("item_search.png");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        networkLog.info("Starting Network");

        initConfig.loadConfig(event.getSuggestedConfigurationFile());

        ModRegistry.registerMod(this);

        int entityID;
        entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(ServerCart.class, "DiamondCart", entityID);
        EntityRegistry.registerModEntity(ServerCart.class, "DiamondCart", 2, instance, 64, 5, true);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new DropItemEvent());

        ItemSystem.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.initRenderers();

        packetPipeline.initalise();
        ChannelHandler.setChannels(NetworkRegistry.INSTANCE.newChannel("Network_Sim", new ChannelHandler()));

        initItems.loadItems();

        initBlocks.loadBlocks();

        AppManager.init();

        Multipart.init();

        initItems.Recipes();

        ItemSystem.init(event);

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        packetPipeline.postInitialise();
        ItemSystem.postInit(event);
    }

    @EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        initCommand.loadcommands(event);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BaseModGui settingsScreen() {
        return null;
    }

    @Override
    public String modId() {
        return "network";
    }

    @Override
    public String modName() {
        return "Network";
    }

    @Override
    public String modVersion() {
        return "0.01";
    }

    @Override
    public String recomenedMinecraftVeriosion() {
        return "1.7.10";
    }
}
