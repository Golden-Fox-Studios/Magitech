package com.superkooks.magitech.blocks;

import javax.annotation.Nullable;

import com.superkooks.magitech.MTBlocks;
import com.superkooks.magitech.Magitech;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBrewPipe extends Block {
	public static final PropertyEnum<BlockBrewPipe.EnumAttachPosition> NORTH = PropertyEnum.<BlockBrewPipe.EnumAttachPosition>create("north", BlockBrewPipe.EnumAttachPosition.class);
    public static final PropertyEnum<BlockBrewPipe.EnumAttachPosition> EAST = PropertyEnum.<BlockBrewPipe.EnumAttachPosition>create("east", BlockBrewPipe.EnumAttachPosition.class);
    public static final PropertyEnum<BlockBrewPipe.EnumAttachPosition> SOUTH = PropertyEnum.<BlockBrewPipe.EnumAttachPosition>create("south", BlockBrewPipe.EnumAttachPosition.class);
    public static final PropertyEnum<BlockBrewPipe.EnumAttachPosition> WEST = PropertyEnum.<BlockBrewPipe.EnumAttachPosition>create("west", BlockBrewPipe.EnumAttachPosition.class);
	
	public BlockBrewPipe(String name, Material material) {
		super(material);
		System.out.println(this.blockState.getBaseState());
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, BlockBrewPipe.EnumAttachPosition.NONE).withProperty(EAST, BlockBrewPipe.EnumAttachPosition.NONE).withProperty(SOUTH, BlockBrewPipe.EnumAttachPosition.NONE).withProperty(WEST, BlockBrewPipe.EnumAttachPosition.NONE));
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Magitech.tabMagitech);
	}
	
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		state = state.withProperty(WEST, this.getAttachPosition(world, pos, EnumFacing.WEST));
        state = state.withProperty(EAST, this.getAttachPosition(world, pos, EnumFacing.EAST));
        state = state.withProperty(NORTH, this.getAttachPosition(world, pos, EnumFacing.NORTH));
        state = state.withProperty(SOUTH, this.getAttachPosition(world, pos, EnumFacing.SOUTH));
        return state;
	}
	
	private BlockBrewPipe.EnumAttachPosition getAttachPosition(IBlockAccess world, BlockPos pos, EnumFacing direction) {
        BlockPos blockpos = pos.offset(direction);

        if (canConnectTo(world.getBlockState(blockpos), direction, world, blockpos)) {
        	return BlockBrewPipe.EnumAttachPosition.CONNECTED;
        } else {
            return BlockBrewPipe.EnumAttachPosition.NONE;
        }
    }
	
	protected static boolean canConnectTo(IBlockState blockState, @Nullable EnumFacing side, IBlockAccess world, BlockPos pos) {
		Block block = blockState.getBlock();
		
		if (block == MTBlocks.blockBrewPipe) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		
		System.out.println("Pressed");
		
		return true;
	}
	
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, SOUTH, WEST});
	}
	
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}
	
	public void registerModels() {
		Magitech.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
	static enum EnumAttachPosition implements IStringSerializable {
        CONNECTED("connected"),
        NONE("none");

        private final String name;

        private EnumAttachPosition(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this.name;
        }
    }
}
