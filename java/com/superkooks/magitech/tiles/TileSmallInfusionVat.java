package com.superkooks.magitech.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileSmallInfusionVat extends TileEntity implements ITickable {
	private ItemStack infusionItem = new ItemStack(Items.CARROT_ON_A_STICK);
	
	public void update() {
		
	}
	
	public ItemStack getInfusionItem() {
		return infusionItem;
	}

	public void interact(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side) {
		// TODO Auto-generated method stub
		
	}
}
