package com.superkooks.magitech.items;

import com.superkooks.magitech.Magitech;

import net.minecraft.item.Item;

public class ItemBasic extends Item {
	// For simple items that have no functionality
	public ItemBasic(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Magitech.tabMagitech);
	}
	
	public void registerModels() {
		Magitech.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
