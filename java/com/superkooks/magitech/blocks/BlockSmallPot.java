package com.superkooks.magitech.blocks;

import java.util.Random;

import com.superkooks.magitech.MTBlocks;
import com.superkooks.magitech.Magitech;
import com.superkooks.magitech.particles.ParticlePotBubble;
import com.superkooks.magitech.tiles.TileSmallPot;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Console;

public class BlockSmallPot extends BlockContainer {

	private boolean boiling;
	public BlockSmallPot(String name, Material material, Boolean state) {
		super(material);
		this.setDefaultState(this.blockState.getBaseState());
		boiling = state;
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
	
	public static void setState(String state, World worldIn, BlockPos pos) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (state == "nowater") {
            worldIn.setBlockState(pos, MTBlocks.blockSmallPot.getDefaultState(), 3);
            worldIn.setBlockState(pos, MTBlocks.blockSmallPot.getDefaultState(), 3);
        } else if (state == "water") {
            worldIn.setBlockState(pos, MTBlocks.blockSmallPot_water.getDefaultState(), 3);
            worldIn.setBlockState(pos, MTBlocks.blockSmallPot_water.getDefaultState(), 3);
        } else if (state == "water_boiling") {
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_water_boiling.getDefaultState(), 3);
            worldIn.setBlockState(pos, MTBlocks.blockSmallPot_water_boiling.getDefaultState(), 3);
        } else if (state == "brew") {
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_brew.getDefaultState(), 3);
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_brew.getDefaultState(), 3);
        } else if (state == "ingredients") {
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_ingredients.getDefaultState(), 3);
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_ingredients.getDefaultState(), 3);
        } else if (state == "spoiled") {
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_spoiled.getDefaultState(), 3);
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_spoiled.getDefaultState(), 3);
        } else if (state == "spoiled_boiling") {
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_spoiled_boiling.getDefaultState(), 3);
        	worldIn.setBlockState(pos, MTBlocks.blockSmallPot_spoiled_boiling.getDefaultState(), 3);
        } else {
        	Console.println("wtf state is that? " + state);
        }

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {		
		if (boiling) {
			double xPos = (double)pos.getX()+rand.nextDouble();
			double zPos = (double)pos.getZ()+rand.nextDouble();
			
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticlePotBubble(worldIn, xPos, pos.getY() + 1.0, zPos, 0, 0.05, 0));
		}
	}
		
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState();
    }
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MTBlocks.blockSmallPot);
    }
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
	
	public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSmallPot();
    }
}
