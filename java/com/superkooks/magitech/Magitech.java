package com.superkooks.magitech;

import com.superkooks.magitech.proxy.CommonProxy;
import com.superkooks.magitech.tiles.TileSmallPot;
import com.superkooks.magitech.world.WorldGen;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Magitech.MODID, name = Magitech.NAME, version = Magitech.VERSION)
public class Magitech {
	public static final String MODID = "magitech";
	public static final String NAME = "Magitech";
	public static final String VERSION = "0.1";
	
	@SidedProxy(clientSide = "com.superkooks.magitech.proxy.ClientProxy", serverSide = "com.superkooks.magitech.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MTBlocks.initBlocks();
		MTItems.initItems();
		
		GameRegistry.registerWorldGenerator(new WorldGen(), 5);
		GameRegistry.registerTileEntity(TileSmallPot.class, new ModelResourceLocation("smallpot"));
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public static CreativeTabs tabMagitech = new CreativeTabs("tabMagitech") {
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(MTBlocks.blockSmallKettle));
		}
	};
}
