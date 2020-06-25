package com.superkooks.magitech.items;

import com.superkooks.magitech.MTItems;

import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends ItemBasic {
	public ItemMortarAndPestle(String name) {
		super(name);
		this.setContainerItem(MTItems.itemMortarAndPestle);
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(MTItems.itemMortarAndPestle);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack itemStack) {
		return true;
	}
}
