package modmuss50.network.client.gui;

import modmuss50.network.NetworkCore;
import modmuss50.network.blocks.tileentities.TileEntityTeleporter;
import modmuss50.network.netty.packets.PacketSetTeleporterFQ;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import sourceteam.mods.lib.Location;

public class GuiTeleporter extends GuiScreen {

	private static final ResourceLocation lightPeripheralTexture = new ResourceLocation("network:textures/gui/lightPeripheral.png");
	private GuiTextField Value;

	private TileEntityTeleporter te;

	public GuiTeleporter(TileEntityTeleporter te) {
		this.te = te;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		RenderManager.instance.renderEngine.bindTexture(lightPeripheralTexture);
		int k = (this.width - 256) / 2;
		int b0 = (this.height - 128) / 2;
		this.drawTexturedModalRect(k, b0, 0, 0, 256, 128);
		Value.drawTextBox();
	}

	@Override
	public void initGui() {
		Value = new GuiTextField(this.fontRendererObj, ((this.width - 256) / 2) + 128 - 100, 100, 50, 20);
		Value.setMaxStringLength(999);
		Value.setText("" + te.fq);
	}

	@Override
	public void mouseClicked(int par1, int par2, int par3) {
		this.Value.mouseClicked(par1, par2, par3);

	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (Value.textboxKeyTyped(par1, par2)) {

		} else {
			super.keyTyped(par1, par2);
		}

		NetworkCore.packetPipeline.sendToAll(new PacketSetTeleporterFQ(new Location(te.xCoord, te.yCoord, te.zCoord), getFQ()));
		NetworkCore.packetPipeline.sendToServer(new PacketSetTeleporterFQ(new Location(te.xCoord, te.yCoord, te.zCoord), getFQ()));
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		NetworkCore.packetPipeline.sendToAll(new PacketSetTeleporterFQ(new Location(te.xCoord, te.yCoord, te.zCoord), getFQ()));
		NetworkCore.packetPipeline.sendToServer(new PacketSetTeleporterFQ(new Location(te.xCoord, te.yCoord, te.zCoord), getFQ()));

	}


	public int getFQ() {
		if (Value.getText() == "" || Value.getText().length() == 0)
			return 0;

		return Integer.parseInt(Value.getText());

	}


}
