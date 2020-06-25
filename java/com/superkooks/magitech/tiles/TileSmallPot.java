package com.superkooks.magitech.tiles;

import java.util.ArrayList;

import com.superkooks.magitech.MTBrews;
import com.superkooks.magitech.MTItems;
import com.superkooks.magitech.blocks.BlockSmallPot;
import com.superkooks.magitech.brews.IBrew;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.Console;

public class TileSmallPot extends TileEntity implements ITickable {
	private ArrayList<ItemStack> contents = new ArrayList<ItemStack>();
	private IBrew brew = null;
	private boolean hasWater = false;
	private boolean boiling = false;
	private boolean spoiled = false;
	
	private int liquidLeft = 0;
	private int ticksToBoiling = 100;
	
	public boolean interact(World world, BlockPos pos, IBlockState blockstate, EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			ItemStack heldItem = player.getHeldItem(hand);
			TileSmallPot pot = null;
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileSmallPot) {
				pot = (TileSmallPot) te;
			}
			
			// Are you holding anything?
			if (heldItem.isEmpty()) {
				if (player.isSneaking()) {
					// Clear the pot
					pot.hasWater = false;
					pot.boiling = false;
					pot.spoiled = false;
					pot.brew = null;
					pot.contents.clear();
					pot.ticksToBoiling = 100;
					pot.liquidLeft = 0;
					
					BlockSmallPot.setState("nowater", this.world, pos);
					return true;
				}
				
				return false;
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
				
				BlockSmallPot.setState("water", pot.world, pos); // Update blockstate
				
				return true;
			}
			
			// Do you have a empty bottle?
			if (heldItem.isItemEqual(new ItemStack(Items.GLASS_BOTTLE))) {
				Console.println("Player is holding empty bottle");
				
				if (pot.spoiled || pot.brew != null && pot.liquidLeft > 0) {
					// There is something that can be bottled, bottle it
					if (heldItem.getCount() > pot.liquidLeft) {
						
						if (pot.spoiled) {
							player.addItemStackToInventory(new ItemStack(MTItems.itemSpoiledBrew, pot.liquidLeft));
						} else {
							player.addItemStackToInventory(new ItemStack(pot.brew.getBrewItem(), pot.liquidLeft));
						}
	
						player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE, heldItem.getCount()-pot.liquidLeft));
						
						pot.liquidLeft = 0;
						
					} else {
						if (pot.spoiled) {
							player.setHeldItem(hand, new ItemStack(MTItems.itemSpoiledBrew, heldItem.getCount()));
						} else {
							player.setHeldItem(hand, new ItemStack(pot.brew.getBrewItem(), heldItem.getCount()));
						}
						
						pot.liquidLeft -= heldItem.getCount();
					}
					
					if (pot.liquidLeft < 1) {
						// Clear the pot
						pot.hasWater = false;
						pot.boiling = false;
						pot.spoiled = false;
						pot.brew = null;
						pot.contents.clear();
						pot.ticksToBoiling = 100;
						pot.liquidLeft = 0;
						
						// Just delete the tile entity and recreate it, much eazier, no bug
						BlockSmallPot.setState("nowater", this.world, this.getPos());
						this.invalidate();
						world.removeTileEntity(this.getPos());
//						world.addTileEntity(MTBlocks.blockSmallPot.createTileEntity(world, MTBlocks.blockSmallPot.getDefaultState()));
					}
					
					return true;
				}
				
				return false;
			}
			
			// Does the pot have water?
			if (!pot.hasWater) {
				// If the pot doesn't have water, you can't add anything
				Console.println("Pot has no water!");
				return true;
				
			} else {
				Console.println("The pot **has** water!");
				
				if (pot.brew != null || pot.spoiled) {
					// Can't add an item if there is a brew or spoiled brew
					return false;
				}
				
				// The pot has water and is not spoiled or has a brew, add the item the player is currently holding
				// Better hope the pot is boiling!
				pot.contents.add(heldItem);
				player.setHeldItem(hand, new ItemStack(Items.AIR));
				
				if (MTBrews.checkForBrew(pot.contents) == null) {
					BlockSmallPot.setState("ingredients", pot.world, pos); // Update blockstate
					return true;
				} else {
					pot.brew = MTBrews.checkForBrew(pot.contents);
					pot.contents.clear();
					pot.liquidLeft = pot.brew.getAmountOfLiquid();
					
					BlockSmallPot.setState("brew", pot.world, pos); // Update blockstate
					return true;
				}
			}
		} else {
			return true;
		}
	}

	public void update() {
		if (!this.world.isRemote) {			
			// Update whether the pot is boiling
			if (!this.boiling) {
				boolean hasFireAndWater = world.getBlockState(this.getPos().down()).getBlock().equals(Blocks.FIRE) && this.hasWater;
				
				if (this.ticksToBoiling > 0 && hasFireAndWater) {
					this.ticksToBoiling -= 1;
				}
				
				if (this.ticksToBoiling < 1 && !this.boiling && hasFireAndWater) {
					this.boiling = true;
					Console.println("The pot is boiling!");
					
					if (this.spoiled) {
						Console.println("Setting blockstate to spoiled_boiling");
						BlockSmallPot.setState("spoiled_boiling", this.world, this.pos);
						return; // We must return after setting a blockstate other nasty
					} else {
						BlockSmallPot.setState("water_boiling", this.world, this.pos);
						return;  // We must return after setting a blockstate other nasty
					}
				}
				
			} else {
				if (!world.getBlockState(this.getPos().down()).getBlock().equals(Blocks.FIRE)) {
					this.boiling = false;
					this.ticksToBoiling = 100;
					
					if (this.spoiled) {
						Console.println("Setting blockstate to spoiled after cooling");
						BlockSmallPot.setState("spoiled", this.world, this.pos);
						return; // We must return after setting a blockstate other nasty
					} else {
						BlockSmallPot.setState("water", this.world, this.pos);
						return;  // We must return after setting a blockstate other nasty
					}
				}
			}
			
			if (!this.boiling && (this.brew != null || !this.contents.isEmpty())) {
				// If the pot is not boiling and there is a brew in the pot, then it is spoiled 
				this.spoiled = true;
				this.contents.clear();
				this.brew = null;
				this.liquidLeft = 2;
				Console.println("Not boiling with ingredients or brew in! SPOILED!");
			}
			
			if (MTBrews.hasCatalyst(this.contents) && MTBrews.checkForBrew(contents) == null) {
				// If the pot has a catalyst but there is no brew (wrong ingredients) then the brew is spoiled
				this.spoiled = true;
				this.contents.clear();
				this.brew = null;
				this.liquidLeft = 2;
				Console.println("The pot has a catalyst but no brew! SPOILED!");
			}
			
			
			if (this.spoiled && !this.boiling) {
				BlockSmallPot.setState("spoiled", this.world, this.pos);
			} else if (this.spoiled && this.boiling) {
				BlockSmallPot.setState("spoiled_boiling", this.world, this.pos);
			} else if (this.hasWater && !this.spoiled && !this.boiling) {
				BlockSmallPot.setState("water", this.world, this.pos);
			}
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		// Need to save otherwise things might spoil
		compound.setBoolean("boiling", this.boiling);
		
		compound.setBoolean("hasWater", this.hasWater);
		compound.setBoolean("spoiled", this.spoiled);
		
		compound.setInteger("liquidLeft", this.liquidLeft);
		
		if (this.brew != null) {
			compound.setString("brew", MTBrews.getIdOfBrew(this.brew));
		}
		TileSmallPot.saveAllItems(compound, this.contents);
		return compound;
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.boiling = compound.getBoolean("boiling");
		
		this.hasWater = compound.getBoolean("hasWater");
		this.spoiled = compound.getBoolean("spoiled");
		
		this.liquidLeft = compound.getInteger("liquidLeft");
		
		if (compound.getString("brew") == "") {
				this.brew = null;
		} else {
			this.brew = MTBrews.getBrew(compound.getString("brew"));
		}
		
		this.contents = new ArrayList<ItemStack>();
		TileSmallPot.loadAllItems(compound, this.contents);
	}
	
	private static void loadAllItems(NBTTagCompound tag, ArrayList<ItemStack> list) {
        NBTTagList nbttaglist = tag.getTagList("contents", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            list.add(new ItemStack(nbttagcompound));
        }
    }
	
	private static NBTTagCompound saveAllItems(NBTTagCompound tag, ArrayList<ItemStack> list) {
		NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < list.size(); ++i)
        {
            ItemStack itemstack = list.get(i);

            if (!itemstack.isEmpty())
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                itemstack.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        tag.setTag("contents", nbttaglist);

        return tag;
	}
}
