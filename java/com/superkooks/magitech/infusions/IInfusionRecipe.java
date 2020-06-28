package com.superkooks.magitech.infusions;

import com.superkooks.magitech.brews.IBrew;

import net.minecraft.item.ItemStack;

public interface IInfusionRecipe {
	public ItemStack getResult();
	public ItemStack getUninfusedItem();
	public IBrew getBrew();
	public int getInfusionTier();
}
