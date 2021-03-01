package com.superkooks.magitech;

import com.superkooks.magitech.blocks.BlockBasic;
import com.superkooks.magitech.blocks.BlockBrewPipe;
import com.superkooks.magitech.blocks.BlockFluid;
import com.superkooks.magitech.blocks.BlockSmallInfusionVat;
import com.superkooks.magitech.blocks.BlockSmallPot;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class MTBlocks {
	public static Block blockSmallInfusionVat;
	public static Block blockSmallPot;
	public static Block blockLunarstone;
	public static Block blockBrewPipe;

	public static Block blockSmallPot_water;
	public static Block blockSmallPot_water_boiling;
	public static Block blockSmallPot_ingredients;
	public static Block blockSmallPot_spoiled;
	public static Block blockSmallPot_spoiled_boiling;
	public static Block blockSmallPot_brew;
	
	public static Block blockFluidLuminux;
	
	public static Item itemBlockSmallInfusionVat;
	public static Item itemBlockSmallPot;
	public static Item itemBlockLunarstone;
	public static Item itemBlockBrewPipe;
	
	public static void initBlocks() {
		// Fired during pre-init
		
		blockSmallInfusionVat = new BlockSmallInfusionVat("smallInfusionVat", Material.IRON);
		blockSmallPot = new BlockSmallPot("smallPot", Material.IRON, false);
		blockLunarstone = new BlockBasic("lunarstone", Material.ROCK);
		blockBrewPipe = new BlockBrewPipe("brewPipe", Material.IRON);
		
		blockSmallPot_water = new BlockSmallPot("smallPot_water", Material.IRON, false);
		blockSmallPot_water_boiling = new BlockSmallPot("smallPot_water_boiling", Material.IRON, true);
		blockSmallPot_ingredients = new BlockSmallPot("smallPot_ingredients", Material.IRON, true);
		blockSmallPot_spoiled = new BlockSmallPot("smallPot_spoiled", Material.IRON, false);
		blockSmallPot_spoiled_boiling = new BlockSmallPot("smallPot_spoiled_boiling", Material.IRON, true);
		blockSmallPot_brew = new BlockSmallPot("smallPot_brew", Material.IRON, true);
		
		blockFluidLuminux = new BlockFluid("luminux", MTFluids.fluidLuminux);
		
		itemBlockSmallInfusionVat = new ItemBlock(blockSmallInfusionVat).setRegistryName("smallinfusionvat");
		itemBlockSmallPot = new ItemBlock(blockSmallPot).setRegistryName("smallpot");
		itemBlockLunarstone = new ItemBlock(blockLunarstone).setRegistryName("lunarstone");
		itemBlockBrewPipe = new ItemBlock(blockBrewPipe).setRegistryName("brewpipe");
	}
	
	public static void renderFluids() {
		((BlockFluid) blockFluidLuminux).render();
	}
	
	//
	// Events
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(blockSmallInfusionVat);
		event.getRegistry().register(blockSmallPot);
		event.getRegistry().register(blockLunarstone);
		event.getRegistry().register(blockBrewPipe);
		
		event.getRegistry().register(blockSmallPot_water);
		event.getRegistry().register(blockSmallPot_water_boiling);
		event.getRegistry().register(blockSmallPot_ingredients);
		event.getRegistry().register(blockSmallPot_spoiled);
		event.getRegistry().register(blockSmallPot_spoiled_boiling);
		event.getRegistry().register(blockSmallPot_brew);
		
		event.getRegistry().register(blockFluidLuminux);
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(itemBlockSmallInfusionVat);
		event.getRegistry().register(itemBlockSmallPot);
		event.getRegistry().register(itemBlockLunarstone);
		event.getRegistry().register(itemBlockBrewPipe);
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		((BlockSmallInfusionVat) blockSmallInfusionVat).initModel();
		((BlockSmallPot) blockSmallPot).registerModels();
		((BlockBasic) blockLunarstone).registerModels();
		((BlockBrewPipe) blockBrewPipe).registerModels();
	}
}
