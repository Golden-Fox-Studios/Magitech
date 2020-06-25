package com.superkooks.magitech;

import java.util.ArrayList;

import com.google.common.collect.HashBiMap;
import com.superkooks.magitech.brews.BrewLuminux;
import com.superkooks.magitech.brews.IBrew;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.Console;

public class MTBrews {
	public static BrewLuminux brewLuminux;
	
	private final static HashBiMap<String, IBrew> brews = HashBiMap.create();
	
	public static void initBrews() {
		brewLuminux = new BrewLuminux();
		
		MTBrews.addBrew("magitech:luminux", brewLuminux);
	}
	
	public static void addBrew(String id, IBrew brew) {
		brews.put(id, brew);
	}
	
	public static IBrew getBrew(String id) {
		return brews.get(id);
	}
	
	public static String getIdOfBrew(IBrew brew) {
		return brews.inverse().get(brew);
	}
	
	public static IBrew checkForBrew(ArrayList<ItemStack> contents) {
		if (!MTBrews.hasCatalyst(contents)) {
			// There is no brew without a catalyst
			return null;
		}
		
		IBrew brew = brewLuminux.checkForBrew(contents);
		
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
	
	public static boolean compareIngredients(ArrayList<ItemStack> ingredients, ItemStack catalyst, ArrayList<ItemStack> potContents) {
		// The ingredients list must represent the totalled amounts (no double stacks)
		
		ArrayList<ItemStack> totalledContents = new ArrayList<ItemStack>(); // Represents the contents with no double stacks
		ArrayList<Item> totalledContentsItems = new ArrayList<Item>(); // Make sure we don't do double stacks
		
		for (ItemStack item: potContents) {
			boolean itemContained = false;
			for (Item item2: totalledContentsItems) {
				if (item.getItem().equals(item2)) {
					itemContained = true;
					break;
				}
			}
			if (!itemContained) {
				// We have not added this type of item yet
				totalledContents.add(item);
				totalledContentsItems.add(item.getItem());
			} else {
				// This item already exists, double stacks exist, fix it
				int firstItemIndex = totalledContentsItems.indexOf(item.getItem());
				ItemStack firstItem = totalledContents.get(firstItemIndex);
				
				// Add the amount of items together
				ItemStack newItem = new ItemStack(item.getItem(), firstItem.getCount() + item.getCount()); 
				totalledContents.set(firstItemIndex, newItem); // Replace it in the list
			}
		}
		
		ArrayList<ItemStack> ingredientsAndCatalyst = new ArrayList<ItemStack>();
		ingredientsAndCatalyst.addAll(ingredients);
		ingredientsAndCatalyst.add(catalyst);
		
		for (ItemStack item: totalledContents) {
			boolean itemContained = false;
			for (ItemStack item2: ingredientsAndCatalyst) {
				if (ItemStack.areItemStacksEqual(item, item2)) {
					itemContained = true;
					ingredientsAndCatalyst.remove(item2);
					break;
				}
			}
			
			if (!itemContained) {
				// Could not find an item from the pot in the recipe
				// There is an extra ingredient, this must be punished
				Console.println("Extra ingredient. :(");
				return false;
			}
		}
		
		if (ingredientsAndCatalyst.size() > 0) {
			// An ingredient is missing
			Console.println("An ingredient is missing :(");
			Console.println(ingredientsAndCatalyst);
			return false;
		}

		return true;
	}
}
