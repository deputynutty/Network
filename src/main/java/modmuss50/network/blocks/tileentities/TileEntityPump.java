package modmuss50.network.blocks.tileentities;

import modmuss50.network.Fmp.Multipart;
import modmuss50.network.Fmp.PartPipeLine;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

//todo when powernet is recoded add some boost's when using power
public class TileEntityPump extends BaseTile {

    @Override
    public void updateEntity() {
        if (true == true) {
            return;
        }
        for (int i = 0; i < ForgeDirection.values().length; i++) {
            TileEntity tile = worldObj.getTileEntity(this.xCoord + ForgeDirection.values()[i].offsetX, this.yCoord + ForgeDirection.values()[i].offsetY, this.zCoord + ForgeDirection.values()[i].offsetY);
            if (tile != null && tile instanceof IFluidHandler && !Multipart.hasPartPipe(tile)) {
                System.out.println("Can pump from " + tile.toString());
                PartPipeLine pipe = Multipart.getPipe(worldObj.getTileEntity(this.xCoord + ForgeDirection.values()[i].getOpposite().offsetX, this.yCoord + ForgeDirection.values()[i].getOpposite().offsetY, this.zCoord + ForgeDirection.values()[i].getOpposite().offsetY));
                if (pipe != null && tile != null) {
                    if (((IFluidHandler) tile).getTankInfo(ForgeDirection.values()[i])[0] != null && ((IFluidHandler) tile).getTankInfo(ForgeDirection.values()[i])[0].fluid.amount != 0) {
                        for (int j = 0; j < ((IFluidHandler) tile).getTankInfo(ForgeDirection.values()[i]).length; j++) {
                            if (pipe.canFill(ForgeDirection.values()[j].getOpposite(), ((IFluidHandler) tile).getTankInfo(ForgeDirection.values()[i].getOpposite())[0].fluid.getFluid())) {
                                pipe.fill(((IFluidHandler) tile).drain(ForgeDirection.values()[i].getOpposite(), 10, true), true);
                            }
                        }
                    }
                }
            }
        }
    }


}
