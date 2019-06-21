package com.superkooks.magitech.items;

import com.superkooks.magitech.MTItems;
import com.superkooks.magitech.Magitech;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends Item {
	// Annoyingly this has to be in a separate file so it is not used in crafting recipes
	
	public ItemMortarAndPestle(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Magitech.tabMagitech);
	}
	
	public void registerModels() {
		Magitech.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public boolean hasContainerItem(ItemStack item) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack item) {
		return new ItemStack(MTItems.itemMortarAndPestle);
	}
}
