package com.superkooks.magitech.brews;

import java.awt.Color;

import net.minecraft.item.Item;

public interface IBrew {
	public Item getBrewItem();
	public int getAmountOfLiquid();
	public Color getColor();
}
