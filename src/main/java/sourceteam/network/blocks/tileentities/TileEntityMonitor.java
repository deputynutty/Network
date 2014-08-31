package sourceteam.network.blocks.tileentities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 02/03/14 Time: 19:28
 */
public class TileEntityMonitor extends TileEntityShell {
    public List<String> textLines = new ArrayList();
    public int textRotation;

    public TileEntityMonitor() {
        this.textLines.add("Hello");
        this.textLines.add("this is so cool");
    }

    public List<String> getTextLines() {
        return this.textLines;
    }

    public void updateEntity() {
        super.updateEntity();
        textLines.clear();
        if (this.getSerX() != 0 && this.getSerY() != 0 && this.getSerZ() != 0) {
            int p = ((TileEntityPowerSink) this.worldObj.getTileEntity(this.getSerX(), this.getSerY(), this.getSerZ())).getNetworkNetUnits();
            if (p != 0) {
                textLines.add("Power: " + Integer.toString(p));
            } else {
                textLines.add("Power: 0");

            }
        } else {
            textLines.add("Power: 0");

        }

    }

}
