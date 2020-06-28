package com.superkooks.magitech.tiles;

import com.superkooks.magitech.MTBrews;
import com.superkooks.magitech.brews.IBrew;

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
	private IBrew currentBrew = MTBrews.brewLuminux;
	
	public void update() {
		
	}
	
	public ItemStack getInfusionItem() {
		return infusionItem;
	}
	
	public IBrew getCurrentBrew() {
		return currentBrew;
	}
	
	public boolean hasBrew() {
		if (currentBrew != null) {
			return true;
		} else {
			return false;
		}
	}

	public void interact(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side) {
		// TODO Auto-generated method stub
		
	}
}
