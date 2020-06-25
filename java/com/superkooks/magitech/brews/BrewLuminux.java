package com.superkooks.magitech.brews;

import java.util.ArrayList;

import com.superkooks.magitech.MTBrews;
import com.superkooks.magitech.MTItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class BrewLuminux implements IBrew {
	static ItemStack ingredients[] = new ItemStack[] { new ItemStack(Items.REDSTONE, 2), new ItemStack(Blocks.TORCH, 4) };
	
	// Don't worry about the warnings, eclipse couldn't even fix them
	@SuppressWarnings("unchecked")
	public static final ArrayList<ItemStack> INGREDIENTS = new ArrayList<ItemStack>(Arrays.asList(ingredients));
	public static final ItemStack CATALYST = new ItemStack(MTItems.itemMoonSugar);
	
	public static final ItemStack OUTPUT = new ItemStack(MTItems.itemLuminux, 2);
	
	
	public BrewLuminux() {
	}
	
	public IBrew checkForBrew(ArrayList<ItemStack> contents) {
		if (MTBrews.compareIngredients(INGREDIENTS, CATALYST, contents)) {
			return (IBrew) this;
		}

		return null;
	}
	
	public Item getBrewItem() {
		return OUTPUT.getItem();
	}
	
	public int getAmountOfLiquid() {
		return OUTPUT.getCount();
	}
}
