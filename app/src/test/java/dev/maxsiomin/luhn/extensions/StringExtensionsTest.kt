package dev.maxsiomin.luhn.extensions

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `not null works properly`() {
        assertThat(null.notNull()).isEqualTo("")
        assertThat("".notNull()).isEqualTo("")
        assertThat("com".notNull()).isEqualTo("com")
    }
}
