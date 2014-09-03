package sourceteam.network.dataSystems.itemSystem.items;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Mark on 29/08/2014.
 */
public class itemFilter extends Item {

    //TODO give this a gui so you can selcet what blocks can go in the chest, also need a whilelist and black list version

    public itemFilter() {
        super();
        setMaxStackSize(1);
    }


}
