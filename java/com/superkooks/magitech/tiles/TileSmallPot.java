package com.superkooks.magitech.tiles;

import java.util.ArrayList;

import com.superkooks.magitech.MTBrews;
import com.superkooks.magitech.brews.Brew;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.Console;

public class TileSmallPot extends TileEntity implements ITickable {
	public ArrayList<ItemStack> contents = new ArrayList<ItemStack>();
	public Brew brew = null;
	public boolean hasWater = false;
	public boolean boiling = false;
	public boolean spoiled = false;
	
	private int ticksToBoiling = 100;
	
	public boolean interact(World world, BlockPos pos, IBlockState blockstate, EntityPlayer player, EnumHand hand) {
		ItemStack heldItem = player.getHeldItem(hand);
		
		// Are you holding anything?
		if (heldItem.isEmpty()) {
			return false;
		}
		
		TileSmallPot pot = null;
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileSmallPot) {
			pot = (TileSmallPot) te;
		}
		
		// Do you have a water bucket?
		if (heldItem.isItemEqual(new ItemStack(Items.WATER_BUCKET))) {
			Console.println("Player is holding water bucket");
			
			if (pot.hasWater) {
				// The pot already has water, no point adding more
				Console.println("Pot already has water!");
				return false;
			}
			
			pot.hasWater = true;
			player.setHeldItem(hand, new ItemStack(Items.BUCKET)); // Empty Bucket
			Console.println("Added water to pot, emptied bucket");
			
			return true;
		}
		
		// Does the pot have water?
		if (!pot.hasWater) {
			// If the doesn't have water, you can't add anything
			Console.println("Pot has no water!");
			return false;
			
		} else {
			Console.println("The pot **has** water!");
			
			// The pot has water, add the item the player is currently holding
			// Better hope the pot is boiling!
			pot.contents.add(heldItem);
			player.setHeldItem(hand, new ItemStack(Items.AIR));
			
			Console.println("The pot contains:");
			Console.println(pot.contents);
			
			if (MTBrews.checkForBrew(pot.contents) == null) {
				return true;
			} else {
				pot.brew = MTBrews.checkForBrew(pot.contents);
				pot.contents.clear();
			}
		}
		
		return false;
	}

	@Override
	public void update() {
		// Update whether the pot is boiling
		if (!this.boiling) {
			boolean hasFireAndWater = world.getBlockState(pos.down()).getBlock().equals(Blocks.FIRE) && this.hasWater;
			
			if (this.ticksToBoiling > 0 && hasFireAndWater) {
				this.ticksToBoiling--;
			}
			
			if (this.ticksToBoiling < 1 && !this.boiling) {
				this.boiling = true;
				Console.println("The pot is boiling!");
			}
		} else {
			if (!world.getBlockState(pos.down()).getBlock().equals(Blocks.FIRE)) {
				this.boiling = false;
				this.ticksToBoiling = 100;
			}
		}
		
		if (!this.boiling && (this.brew != null || !this.contents.isEmpty())) {
			// If the pot is not boiling and there is a brew in the pot, then it is spoiled 
			this.spoiled = true;
			this.brew = null;
		}
		
		if (MTBrews.hasCatalyst(this.contents) && MTBrews.checkForBrew(contents) == null) {
			// If the pot has a catalyst but there is no brew (wrong ingredients) then the brew is spoiled
			this.spoiled = true;
		}
	}
}
