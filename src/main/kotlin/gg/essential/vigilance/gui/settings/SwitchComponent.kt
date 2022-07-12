package gg.essential.vigilance.gui.settings

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.constraints.AspectConstraint
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.PixelConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.state.BasicState
import gg.essential.elementa.state.toConstraint
import gg.essential.universal.USound
import gg.essential.vigilance.gui.VigilancePalette
import gg.essential.vigilance.utils.onLeftClick
import java.awt.Color

class SwitchComponent(initialState: Boolean) : SettingComponent() {

    internal var enabled = initialState

    private val background by UIBlock(getSwitchColor()).constrain {
        width = 100.percent
        height = 100.percent
    } childOf this

    private val switchBox = UIBlock(VigilancePalette.componentBackground).constrain {
        x = getSwitchPosition()
        y = CenterConstraint()
        width = AspectConstraint()
        height = 100.percent - 2.pixels
    } childOf this

    init {
        constrain {
            width = 20.pixels
            height = 11.pixels
        }

        onLeftClick {
            USound.playButtonPress()
            enabled = !enabled
            changeValue(enabled)

            background.setColor(getSwitchColor().toConstraint())
            switchBox.animate {
                setXAnimation(Animations.OUT_EXP, 0.5f, getSwitchPosition())
            }
        }

        onMouseEnter {
            switchBox.animate {
                setColorAnimation(Animations.OUT_EXP, .25f, VigilancePalette.componentBackground.map { it.brighter() }.toConstraint())
            }
        }

        onMouseLeave {
            switchBox.animate {
                setColorAnimation(Animations.OUT_EXP, .25f, VigilancePalette.componentBackground.toConstraint())
            }
        }
    }

    private fun getSwitchColor(): BasicState<Color> = if (enabled) VigilancePalette.primary else VigilancePalette.text

    private fun getSwitchPosition(): PixelConstraint = if (enabled) 1.pixels(alignOpposite = true) else 1.pixels
}
