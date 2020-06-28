package com.superkooks.magitech;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class MTMaterials {
	public static Material luminux;
	
	public static void initMaterials() {
		luminux = new MaterialLiquid(MapColor.YELLOW_STAINED_HARDENED_CLAY);
	}
}
