package com.superkooks.magitech.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.superkooks.magitech.Magitech;
import com.superkooks.magitech.tesr.SmallInfusionVatTESR;
import com.superkooks.magitech.tiles.TileSmallInfusionVat;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSmallInfusionVat extends BlockContainer {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB AABB_FSOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
	protected static final AxisAlignedBB AABB_FNORTH = new AxisAlignedBB(0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0.5D);
	protected static final AxisAlignedBB AABB_FEAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_FWEST = new AxisAlignedBB(1.0D, 0.0D, 0.0D, 050D, 1.0D, 1.0D);
	
	public BlockSmallInfusionVat(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Magitech.tabMagitech);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumFacing = EnumFacing.getFront(meta);

        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumFacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }
	
	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new
				ModelResourceLocation(getRegistryName(), "inventory"));
		// Bind our TESR to our tile entity
		ClientRegistry.bindTileEntitySpecialRenderer(TileSmallInfusionVat.class, new SmallInfusionVatTESR());
	}
	
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSmallInfusionVat();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState blockState) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	private TileSmallInfusionVat getTE(World world, BlockPos pos) {
		return (TileSmallInfusionVat) world.getTileEntity(pos);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
		case NORTH:
			return AABB_FNORTH;
		case SOUTH:
			return AABB_FSOUTH;
		case EAST:
			return AABB_FEAST;
		default:
			// WEST
			return AABB_FWEST;
		}
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!isActualState) {
            state = state.getActualState(worldIn, pos);
        }
		
		switch (state.getValue(FACING)) {
		case NORTH:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_FNORTH);
			break;
		case SOUTH:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_FSOUTH);
			break;
		case EAST:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_FEAST);
			break;
		default:
			// WEST
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_FWEST);
			break;
		}
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileSmallInfusionVat te = getTE(world, pos);
			te.interact(world, pos, state, player, hand, side);
		}
		
		return true;
	}
}
