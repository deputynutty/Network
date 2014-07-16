package modmuss50.network.blocks

import net.minecraft.block.Block
import net.minecraft.block.material.Material

abstract class testBlock extends Block {
  protected def this(met: Material) {
    this()
    `super`(met)
  }

}

