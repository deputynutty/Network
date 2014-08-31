package sourceteam.network.api;

import net.minecraft.item.Item;

public class IWirelessItem extends Item {

    public String User;

    public IWirelessItem() {
        super();
    }

	/*
     * @Override public void onCreated(ItemStack itemstack, World world,
	 * EntityPlayer player) { itemstack.stackTagCompound = new NBTTagCompound();
	 * itemstack.stackTagCompound.setString("User", player.getDisplayName()); }
	 */

	/*
     * @Override public void onUpdate(ItemStack itemstack, World world, Entity
	 * entity, int k, boolean j) { itemstack.stackTagCompound = new
	 * NBTTagCompound(); if(itemstack.stackTagCompound != null) { User =
	 * itemstack.stackTagCompound.getString("User"); } }
	 */

	/*
	 * @Override public ItemStack onItemRightClick(ItemStack par1ItemStack,
	 * World par2World,EntityPlayer par3EntityPlayer) {
	 * 
	 * 
	 * 
	 * par1ItemStack.stackTagCompound = new NBTTagCompound();
	 * par1ItemStack.stackTagCompound.setString("User",
	 * par3EntityPlayer.getDisplayName()); User =
	 * par1ItemStack.stackTagCompound.getString("User");
	 * System.out.println(User);
	 * 
	 * 
	 * par1ItemStack.setTagInfo(User, par1ItemStack.getTagCompound()); return
	 * par1ItemStack;
	 * 
	 * }
	 */

	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack,
	 * EntityPlayer player, List list,boolean par4) { if(stack.stackTagCompound
	 * != null) { String Userinfo = stack.stackTagCompound.getString("User");
	 * list.add("\u00a7r" + "Propety of: " + Userinfo); } }
	 */

}
