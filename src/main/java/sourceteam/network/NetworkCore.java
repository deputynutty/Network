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
import sourceteam.network.multiparts.Multipart;
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

//Give some of the basic info about the mod here
@Mod(modid = "network", name = "Network", version = "0.01", dependencies = "required-after:sourcecore;required-after:CodeChickenCore;required-after:NotEnoughItems;required-after:ForgeMultipart")
public class NetworkCore implements ISourceMod {

    //this is the main packet handler
    public static final PacketPipeline packetPipeline = new PacketPipeline();

    //load the proxy s here
    @SidedProxy(clientSide = "sourceteam.network.ClientProxy", serverSide = "sourceteam.network.CommonProxy")
    public static CommonProxy proxy;

    //The network insance
    @Instance("network")
    public static NetworkCore instance;

    //The network logger
    public static Logger networkLog = Logger.getLogger("Network");

    //This is the creative tab, it has a custom search bar
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
    }.setBackgroundImageName("item_search.png");//setting the background to the vanilla search one here

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        networkLog.info("Starting Network");//Log out to the logger saying that the mod is starting to init

        initConfig.loadConfig(event.getSuggestedConfigurationFile());//load the config

        ModRegistry.registerMod(this);//register the mod with source core

        //some of the entity registry
        int entityID;
        entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(ServerCart.class, "DiamondCart", entityID);
        EntityRegistry.registerModEntity(ServerCart.class, "DiamondCart", 2, instance, 64, 5, true);

        //register the gui handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        //register the drop item event for the tablets
        MinecraftForge.EVENT_BUS.register(new DropItemEvent());

        //call the item system
        ItemSystem.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        //load the renderes
        proxy.initRenderers();

        //load the 2 packet handlers
        packetPipeline.initalise();
        ChannelHandler.setChannels(NetworkRegistry.INSTANCE.newChannel("Network_Sim", new ChannelHandler()));

        //load the items
        initItems.loadItems();

        //load the blocks
        initBlocks.loadBlocks();

        //load the app manager
        AppManager.init();

        //load the multiparts
        Multipart.init();

        //load the recipes
        initItems.Recipes();

        //load the item system
        ItemSystem.init(event);

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        //post the packet handler
        packetPipeline.postInitialise();
        //post the item system
        ItemSystem.postInit(event);
    }

    @EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        //load the commands
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
