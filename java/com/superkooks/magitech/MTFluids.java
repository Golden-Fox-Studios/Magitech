package com.superkooks.magitech;

import com.superkooks.magitech.fluids.FluidBasic;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class MTFluids {
	public static FluidBasic fluidLuminux;
	
	public static void initFluids() {
		fluidLuminux = (FluidBasic) new FluidBasic("luminux", new ResourceLocation(Magitech.MODID, "luminux"), new ResourceLocation(Magitech.MODID, "luminux"))
		.setDensity(1000)
		.setGaseous(false)
		.setLuminosity(15)
		.setViscosity(1000)
		.setTemperature(300);
	
		// Fluids must be registered here because BlockFluid depends on it
		FluidRegistry.registerFluid(fluidLuminux);
		FluidRegistry.addBucketForFluid(fluidLuminux);
	}
}
