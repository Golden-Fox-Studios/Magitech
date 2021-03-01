package com.superkooks.magitech.tesr;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.superkooks.magitech.blocks.BlockSmallInfusionVat;
import com.superkooks.magitech.tiles.TileSmallInfusionVat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SmallInfusionVatTESR extends TileEntitySpecialRenderer<TileSmallInfusionVat> {
	
	@Override
	public void render(TileSmallInfusionVat te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        
        // Render the block
        renderVat(te);
        
        // Render our item
        renderItem(te);
        
        if (te.hasBrew()) {
	        // Render the liquid
	        renderLiquid(te);
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
	}
	
	private void renderLiquid(TileSmallInfusionVat te) {
		GlStateManager.pushMatrix();
		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthMask(false);

        // Get direction
        IBlockState state = te.getWorld().getBlockState(te.getPos());
        switch (state.getValue(BlockSmallInfusionVat.FACING)) {
        case NORTH:
        	GlStateManager.translate(1, 0, 1);
            GlStateManager.rotate(180, 0, 180, 0);
            break;
        case WEST:
        	GlStateManager.translate(1, 0, 0);
        	GlStateManager.rotate(-90, 0, 90, 0);
        	break;
        case EAST:
        	GlStateManager.translate(0, 0, 1);
            GlStateManager.rotate(-90, 0, -90, 0);
        	break;
		default:
			// SOUTH
			break;
        }
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        
        Color brewColor = te.getCurrentBrew().getColor();
        
        double LOW = 2/16D;
        double HIGH = 1-2/16D;
        double LIQHEIGHT = 0.7D;
        
        buffer.pos(LOW, LIQHEIGHT, 0.05).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(HIGH, LIQHEIGHT, 0.05).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(HIGH, LOW, 0.05).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(LOW, LOW, 0.05).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        
        buffer.pos(LOW, LOW, 0.45).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(HIGH, LOW, 0.45).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(HIGH, LIQHEIGHT, 0.45).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(LOW, LIQHEIGHT, 0.45).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        
        buffer.pos(LOW, LIQHEIGHT, 0.45).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(HIGH, LIQHEIGHT, 0.45).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(HIGH, LIQHEIGHT, 0.05).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        buffer.pos(LOW, LIQHEIGHT, 0.05).color(brewColor.getRed(), brewColor.getGreen(), brewColor.getBlue(), brewColor.getAlpha()).endVertex();
        
        tessellator.draw();

        GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.popMatrix();
	}

	private void renderVat(TileSmallInfusionVat te) {
        GlStateManager.pushMatrix();

        RenderHelper.disableStandardItemLighting();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = te.getWorld();
        
        // Translate back to local view coordinates so that we can do the acual rendering here
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state = world.getBlockState(te.getPos());
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
	
    private void renderItem(TileSmallInfusionVat te) {
        ItemStack stack = te.getInfusionItem();
        if (!stack.isEmpty()) {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();
            GlStateManager.pushMatrix();
            
            // Get direction
            IBlockState state = te.getWorld().getBlockState(te.getPos());
            switch (state.getValue(BlockSmallInfusionVat.FACING)) {
            case NORTH:
            	GlStateManager.translate(.5, .3, .75);
                GlStateManager.rotate(180, 0, 180, 0);
                break;
            case SOUTH:
            	GlStateManager.translate(.5, .3, .25);
            	break;
            case EAST:
            	GlStateManager.translate(.25, .3, .5);
                GlStateManager.rotate(-90, 0, -90, 0);
            	break;
			default:
				// WEST
				GlStateManager.translate(.75, .3, .5);
				GlStateManager.rotate(-90, 0, 90, 0);
				break;
            }
            
            GlStateManager.scale(.4f, .4f, .4f);

            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

            GlStateManager.popMatrix();
        }
    }
}
