package com.superkooks.magitech.blocks;

import java.util.ArrayList;
import java.util.List;

import com.superkooks.magitech.Magitech;
import com.superkooks.magitech.brews.Brew;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockSmallKettle extends Block {
	
	public BlockSmallKettle(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Magitech.tabMagitech);
	}
	
	public void registerModels() {
		Magitech.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
