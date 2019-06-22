package com.superkooks.magitech.blocks;

import com.superkooks.magitech.Magitech;
import com.superkooks.magitech.tiles.TileSmallPot;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSmallPot extends BlockContainer implements ITileEntityProvider {

	public BlockSmallPot(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Magitech.tabMagitech);
	}
	
	public void registerModels() {
		Magitech.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        
        TileSmallPot pot = null;
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileSmallPot) {
			pot = (TileSmallPot) te;
		}
		
        return pot.interact(world, pos, state, player, hand);
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return (TileEntity) new TileSmallPot();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return (TileEntity) new TileSmallPot();
	}
}
