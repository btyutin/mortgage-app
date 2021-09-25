package com.wavesplatform.we.app.mortgage.contract.impl

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun sha256(value: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val encodedhash = digest.digest(value.toByteArray(StandardCharsets.UTF_8))
    return bytesToHex(encodedhash)
}

fun bytesToHex(hash: ByteArray): String {
    val hexString = StringBuilder(2 * hash.size)
    for (i in hash.indices) {
        val hex = Integer.toHexString(0xff and hash[i].toInt())
        if (hex.length == 1) {
            hexString.append('0')
        }
        hexString.append(hex)
    }
    return hexString.toString()
}