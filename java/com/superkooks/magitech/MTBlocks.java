package com.superkooks.magitech;

import com.superkooks.magitech.blocks.BlockBasic;
import com.superkooks.magitech.blocks.BlockSmallKettle;
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
	public static Block blockSmallKettle;
	public static Block blockSmallPot;
	public static Block blockLunarstone;
	
	public static Item itemBlockSmallKettle;
	public static Item itemBlockSmallPot;
	public static Item itemBlockLunarstone;
	
	public static void initBlocks() {
		// Fired during pre-init
		
		blockSmallKettle = new BlockSmallKettle("smallKettle", Material.IRON);
		blockSmallPot = new BlockSmallPot("smallPot", Material.IRON);
		blockLunarstone = new BlockBasic("lunarstone", Material.ROCK);
		
		itemBlockSmallKettle = new ItemBlock(blockSmallKettle).setRegistryName("smallkettle");
		itemBlockSmallPot = new ItemBlock(blockSmallPot).setRegistryName("smallpot");
		itemBlockLunarstone = new ItemBlock(blockLunarstone).setRegistryName("lunarstone");
	}
	
	//
	// Events
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(blockSmallKettle);
		event.getRegistry().register(blockSmallPot);
		event.getRegistry().register(blockLunarstone);
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(itemBlockSmallKettle);
		event.getRegistry().register(itemBlockSmallPot);
		event.getRegistry().register(itemBlockLunarstone);
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		((BlockSmallKettle) blockSmallKettle).registerModels();
		((BlockSmallPot) blockSmallPot).registerModels();
		((BlockBasic) blockLunarstone).registerModels();
	}
}
