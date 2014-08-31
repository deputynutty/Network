package sourceteam.network.blocks.tileentities;

public class TileEntityMovingBlock extends BaseTile {

    int renderticks = 0;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (renderticks == 20) {
            renderticks = 0;
        } else {
            renderticks += 1;
        }

    }

}
