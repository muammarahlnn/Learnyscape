package com.muammarahlnn.learnyscape.core.common.decoder

import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file StringCodec, 29/08/2023 11.21 by Muammar Ahlan Abimanyu
 */
object StringCodec {

    private val urlCharacterEncoding = UTF_8.name()

    fun encode(string: String): String = URLEncoder.encode(string, urlCharacterEncoding)

    fun decode(string: String) : String = URLDecoder.decode(string, urlCharacterEncoding)
}