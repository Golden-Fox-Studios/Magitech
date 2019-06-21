package com.superkooks.magitech.blocks;

import com.superkooks.magitech.Magitech;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockSmallPot extends Block {

	public BlockSmallPot(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Magitech.tabMagitech);
	}
	
	public void registerModels() {
		Magitech.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
