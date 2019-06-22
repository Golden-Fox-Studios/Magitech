package com.superkooks.magitech.brews;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.superkooks.magitech.MTItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BrewLuminux extends Brew {
	public static final Stream<ItemStack> INGREDIENTS = Stream.of(new ItemStack(Items.REDSTONE, 2), new ItemStack(Blocks.TORCH, 4));
	public static final ItemStack CATALYST = new ItemStack(MTItems.itemMoonSugar);
	
	public static final ItemStack OUTPUT = new ItemStack(MTItems.itemLuminux);
	
	public BrewLuminux checkForBrew(ArrayList<ItemStack> contents) {
		return null;
	}
}
