package com.superkooks.magitech.blocks;

import com.superkooks.magitech.Magitech;
import com.superkooks.magitech.fluids.FluidBasic;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluid extends BlockFluidClassic {
	public BlockFluid(String name, FluidBasic fluid) {
		super(fluid, Material.WATER);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setCreativeTab(Magitech.tabMagitech);
	}
	
	@SideOnly(Side.CLIENT)
	public void render() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
	}
}
