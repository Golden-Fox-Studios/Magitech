package com.superkooks.magitech;

import java.util.ArrayList;

import com.superkooks.magitech.brews.Brew;
import com.superkooks.magitech.brews.BrewLuminux;

import net.minecraft.item.ItemStack;

public class MTBrews {
	public static BrewLuminux brewLuminux;
	
	public static void initBrews() {
		brewLuminux = new BrewLuminux();
	}
	
	public static Brew checkForBrew(ArrayList<ItemStack> contents) {
		Brew brew = (Brew) brewLuminux.checkForBrew(contents);
		
		return brew;
	}
	
	public static boolean hasCatalyst(ArrayList<ItemStack> contents) {
		for (ItemStack item: contents) {
			if (item.getItem().equals(MTItems.itemMoonSugar)) {
				return true;
			}
		}
		
		return false;
	}
}
