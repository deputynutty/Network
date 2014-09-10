package modmuss50.network.blocks.tileentities;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankRestricted extends FluidTank {
    String[] name;

    public FluidTankRestricted(int capacity, String[] name) {
        super(capacity);
        this.name = name;
    }

    public int fill(FluidStack resource, boolean doFill) {
        if (this.fluid == null) {
            String str = FluidRegistry.getFluidName(resource);

            for (int i = 0; i < this.name.length; i++) {
                if (this.name[i].equals(str)) {
                    return super.fill(resource, doFill);
                }
            }
            return 0;
        }

        return super.fill(resource, doFill);
    }

}
