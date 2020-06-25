package com.superkooks.magitech;

import com.superkooks.magitech.items.ItemBasic;
import com.superkooks.magitech.items.ItemCatalyst;
import com.superkooks.magitech.items.ItemMortarAndPestle;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class MTItems {
	public static Item itemLuminux;
	public static Item itemMoonSugar;
	public static Item itemLunarstoneDust;
	public static Item itemMortarAndPestle;
	public static Item itemSpoiledBrew;
	
	public static void initItems() {
		// Fired in pre-init
		
		itemLuminux = new ItemBasic("luminux");
		itemMoonSugar = new ItemCatalyst("moonSugar");
		itemLunarstoneDust = new ItemBasic("lunarstoneDust");
		itemMortarAndPestle = new ItemMortarAndPestle("mortarAndPestle");
		itemSpoiledBrew = new ItemBasic("spoiledBrew");
	}
	
	//
	// Events
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(itemLuminux);
		event.getRegistry().register(itemMoonSugar);
		event.getRegistry().register(itemLunarstoneDust);
		event.getRegistry().register(itemMortarAndPestle);
		event.getRegistry().register(itemSpoiledBrew);
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		((ItemBasic)itemLuminux).registerModels();
		((ItemBasic)itemMoonSugar).registerModels();
		((ItemBasic)itemLunarstoneDust).registerModels();
		((ItemBasic)itemMortarAndPestle).registerModels();
		((ItemBasic)itemSpoiledBrew).registerModels();
	}
}
