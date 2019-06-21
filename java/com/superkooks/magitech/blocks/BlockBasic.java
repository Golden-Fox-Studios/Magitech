package com.superkooks.magitech.blocks;

import com.superkooks.magitech.Magitech;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockBasic extends Block {
	// Basic block for blocks without functionality
	public BlockBasic(String name, Material material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Magitech.tabMagitech);
	}
		
	public void registerModels() {
		Magitech.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
