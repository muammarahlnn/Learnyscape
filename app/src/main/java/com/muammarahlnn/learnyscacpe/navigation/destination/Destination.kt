package com.muammarahlnn.learnyscacpe.navigation.destination

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Destination, 07/08/2023 15.41 by Muammar Ahlan Abimanyu
 */

interface Destination {

    @get:DrawableRes
    val selectedIconId: Int

    @get:DrawableRes
    val unselectedIconId: Int

    @get:StringRes
    val iconTextId: Int
}