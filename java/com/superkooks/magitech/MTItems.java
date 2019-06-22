package com.superkooks.magitech;

import com.superkooks.magitech.items.ItemBasic;
import com.superkooks.magitech.items.ItemCatalyst;

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
	
	public static void initItems() {
		// Fired in pre-init
		
		itemLuminux = new ItemBasic("luminux");
		itemMoonSugar = new ItemCatalyst("moonSugar");
		itemLunarstoneDust = new ItemBasic("lunarstoneDust");
		itemMortarAndPestle = new ItemBasic("mortarAndPestle");
		
	}
	
	//
	// Events
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(itemLuminux);
		event.getRegistry().register(itemMoonSugar);
		event.getRegistry().register(itemLunarstoneDust);
		event.getRegistry().register(itemMortarAndPestle);
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		((ItemBasic)itemLuminux).registerModels();
		((ItemBasic)itemMoonSugar).registerModels();
		((ItemBasic)itemLunarstoneDust).registerModels();
		((ItemBasic)itemMortarAndPestle).registerModels();
	}
}
