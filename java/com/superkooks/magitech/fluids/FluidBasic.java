package com.superkooks.magitech.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidBasic extends Fluid {
	protected static int mapColor = 0xFFFFFFFF;
	protected static float overlayAlpha = 0.2F;
    protected static SoundEvent emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
    protected static SoundEvent fillSound = SoundEvents.ITEM_BUCKET_FILL;
    protected static Material material = Material.WATER;

    public FluidBasic(String fluidName, ResourceLocation still, ResourceLocation flowing) {
    	super(fluidName, still, flowing);
    }
   
    @Override
    public int getColor() {
    	return mapColor;
    }
    
    public FluidBasic setColor(int parColor) {
    	mapColor = parColor;
    	return this;
    }
    
    public float getAlpha() {
    	return overlayAlpha;
    }
    
    public FluidBasic setAlpha(float parOverlayAlpha) {
    	overlayAlpha = parOverlayAlpha;
    	return this;
    }
    
    @Override
    public FluidBasic setEmptySound(SoundEvent parSound)
    {
        emptySound = parSound;
        return this;
    }
 
    @Override
    public SoundEvent getEmptySound()
    {
        return emptySound;
    }
 
    @Override
    public FluidBasic setFillSound(SoundEvent parSound)
    {
        fillSound = parSound;
        return this;
    }
 
    @Override
    public SoundEvent getFillSound()
    {
        return fillSound;
    }
 
    public FluidBasic setMaterial(Material parMaterial)
    {
        material = parMaterial;
        return this;
    }
 
    public Material getMaterial()
    {
        return material;
    }
 
    @Override
    public boolean doesVaporize(FluidStack fluidStack)
    {
        if (block == null)
            return false;
        return block.getDefaultState().getMaterial() == getMaterial();
    }
}
