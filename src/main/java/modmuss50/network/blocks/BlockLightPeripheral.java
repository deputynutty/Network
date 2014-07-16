package modmuss50.network.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modmuss50.network.NetworkCore;
import modmuss50.network.api.IPeripheral;
import modmuss50.network.blocks.tileentities.TileEntityLightPeripheral;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourceteam.mods.core.client.ClientInit;
import sourceteam.mods.lib.api.ColoredNetworkBlock;

import java.awt.*;

public class BlockLightPeripheral extends ColoredNetworkBlock implements IPeripheral , ITileEntityProvider {

	public BlockLightPeripheral() {
		super(Material.rock, "network:lightPeripheral");
       // super(Material.rock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iba, int x, int y, int z) {
		TileEntity te = iba.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityLightPeripheral) {
			TileEntityLightPeripheral telp = (TileEntityLightPeripheral) te;

			return new Color(telp.red, telp.green, telp.blue).getRGB();
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean canConnectViaWireless() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityLightPeripheral();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem();
		Item item = stack != null ? stack.getItem() : null;
		if (item == null || (item != null && item != Items.dye)) {
			player.openGui(NetworkCore.instance, 1, world, x, y, z);
			TileEntity te = world.getTileEntity(x, y, z);
			TileEntityLightPeripheral telp = (TileEntityLightPeripheral) te;
            telp.getDescriptionPacket();
		}
		else
			if (item != null && item == Items.dye && stack.stackSize > 1) {
				TileEntity te = world.getTileEntity(x, y, z);
				if (te != null && te instanceof TileEntityLightPeripheral) {
					TileEntityLightPeripheral telp = (TileEntityLightPeripheral) te;
					if (telp.red == 255 || telp.green == 255 || telp.blue == 255) {
						setColourRGB(telp, stack.getItemDamage() == 1 ? 0 : telp.red, stack.getItemDamage() == 2 ? 0 : telp.green, stack.getItemDamage() == 4 ? 0 : telp.blue);
					}
					if (player.isSneaking()) {
						if (telp.red > 11 & telp.green > 11 & telp.blue > 11) {
							if (stack.getItemDamage() == 1) {
								setColourRGB(telp, telp.red - 10, telp.green, telp.blue);
							}
							else
								if (stack.getItemDamage() == 2) {
									setColourRGB(telp, telp.red, telp.green - 10, telp.blue);
								}
								else
									if (stack.getItemDamage() == 4) {
										setColourRGB(telp, telp.red, telp.green, telp.blue + 10);
									}
						}
						else {
							if (telp.red != 255 & telp.green != 255 & telp.blue != 255) {
								if (stack.getItemDamage() == 1) {
									setColourRGB(telp, telp.red + 10, telp.green, telp.blue);
								}
								else
									if (stack.getItemDamage() == 2) {
										setColourRGB(telp, telp.red, telp.green + 10, telp.blue);
									}
									else
										if (stack.getItemDamage() == 4) {
											setColourRGB(telp, telp.red, telp.green, telp.blue + 10);
										}
							}

						}
					}
					else {
						if (telp.red < 244 || telp.green < 244 || telp.blue < 244) {
							if (stack.getItemDamage() == 1) {
								setColourRGB(telp, telp.red + 10, telp.green, telp.blue);
							}
							else
								if (stack.getItemDamage() == 2) {
									setColourRGB(telp, telp.red, telp.green + 10, telp.blue);
								}
								else
									if (stack.getItemDamage() == 4) {
										setColourRGB(telp, telp.red, telp.green, telp.blue + 10);
									}
						}
						else {
							if (telp.red != 255 || telp.green != 255 || telp.blue != 255) {
								if (stack.getItemDamage() == 1) {
									setColourRGB(telp, telp.red + 10, telp.green, telp.blue);
								}
								else
									if (stack.getItemDamage() == 2) {
										setColourRGB(telp, telp.red, telp.green + 10, telp.blue);
									}
									else
										if (stack.getItemDamage() == 4) {
											setColourRGB(telp, telp.red, telp.green, telp.blue + 10);
										}
							}

						}

					}
				}
			}
			else
				if (item != null && item == Items.dye && stack.stackSize == 1) {
					TileEntity te = world.getTileEntity(x, y, z);
					if (te != null && te instanceof TileEntityLightPeripheral) {
						TileEntityLightPeripheral telp = (TileEntityLightPeripheral) te;
						setColorToMatchDye(player.getHeldItem().getItemDamage(), telp);
					}
				}
		return true;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		/*
		 * ItemStack stack = player.getHeldItem(); Item item = stack != null ?
		 * stack.getItem() : null; if(item == null || (item != null && item !=
		 * Items.dye)) { player.openGui(NetworkCore.instance, 1, world, x, y,
		 * z); } else if(item != null && item == Items.dye &&
		 * !player.isSneaking()) { TileEntity te = world.getTileEntity(x, y, z);
		 * if(te != null && te instanceof TileEntityLightPeripheral) {
		 * TileEntityLightPeripheral telp = (TileEntityLightPeripheral) te;
		 * if(stack.getItemDamage() == 1) { setColourRGB(telp, telp.red + 1,
		 * telp.green, telp.blue); } else if(stack.getItemDamage() == 2) {
		 * setColourRGB(telp, telp.red, telp.green + 1, telp.blue); } else
		 * if(stack.getItemDamage() == 4) { setColourRGB(telp, telp.red,
		 * telp.green, telp.blue + 1); } } } else if(item != null && item ==
		 * Items.dye && player.isSneaking()) { TileEntity te =
		 * world.getTileEntity(x, y, z); if(te != null && te instanceof
		 * TileEntityLightPeripheral) { TileEntityLightPeripheral telp =
		 * (TileEntityLightPeripheral) te;
		 * setColorToMatchDye(player.getHeldItem().getItemDamage(), telp); } }
		 */
	}

	private void setColorToMatchDye(int dyeDmg, TileEntityLightPeripheral telp) {
		switch (dyeDmg) {
			case 0: {
				setColourHex(telp, 0x191919);
				break;
			}
			case 1: {
				setColourHex(telp, 0x993333);
				break;
			}
			case 2: {
				setColourHex(telp, 0x667F33);
				break;
			}
			case 3: {
				setColourHex(telp, 0x664C33);
				break;
			}
			case 4: {
				setColourHex(telp, 0x334CB2);
				break;
			}
			case 5: {
				setColourHex(telp, 0x7F3FB2);
				break;
			}
			case 6: {
				setColourHex(telp, 0x4C7F99);
				break;
			}
			case 7: {
				setColourHex(telp, 0x999999);
				break;
			}
			case 8: {
				setColourHex(telp, 0x4C4C4C);
				break;
			}
			case 9: {
				setColourHex(telp, 0xF27FA5);
				break;
			}
			case 10: {
				setColourHex(telp, 0x7FCC19);
				break;
			}
			case 11: {
				setColourHex(telp, 0xE5E533);
				break;
			}
			case 12: {
				setColourHex(telp, 0x6699D8);
				break;
			}
			case 13: {
				setColourHex(telp, 0xB24CD8);
				break;
			}
			case 14: {
				setColourHex(telp, 0xD87F33);
				break;
			}
			case 15: {
				setColourHex(telp, 0xFFFFFF);
				break;
			}
		}
	}

	private void setColourRGB(TileEntityLightPeripheral telp, int red, int green, int blue) {
		telp.red = red;
		telp.green = green;
		telp.blue = blue;

		telp.updateBlock();
	}

	private void setColourHex(TileEntityLightPeripheral telp, int hex) {
		Color color = new Color(hex);
		telp.red = color.getRed() / 255;
		telp.green = color.getGreen() / 255;
		telp.blue = color.getBlue() / 255;

		telp.updateBlock();
	}

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ClientInit.render;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

}
