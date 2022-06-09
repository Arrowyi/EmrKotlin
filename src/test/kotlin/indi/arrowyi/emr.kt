/*
 * Copyright 2022-2022 [Arrowyi]
 * email:arrowyi@gmail.com
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package indi.arrowyi

import indi.arrowyi.emr.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class EmrTest {
    @BeforeEach
    fun setUp() {
        Emr(object : LogPrintInterface {
            override fun print(tag: String, msg: String) {
                println("$tag : $msg")
            }
        })
    }

    @Test
    fun testNotNull() {
        class A(val valueA: Int)
        class B(val valueB: Int, val a: A?)
        class C(val valueC: Int, val b: B?)

        val c: C? = C(3, B(2, A(1)))

        notNull(c)
            ?.notNull { b }
            ?.notNull { a }
            ?.let {
                println("valueA = ${it.valueA}")
                assertEquals(1, it.valueA)
            }

        val c2: C? = C(3, B(2, null))
        notNull(c2)
            ?.notNull { b }
            ?.notNull { a }
            ?.let {
                println("valueA = ${it.valueA}")
                assertFails("should not call here") {}
            }

        notNull(c2) {
            //do something with c2
            assertNotNull(c2)
        }

    }

    @Test
    fun testBeTrue() {
        class A(val valueA: Boolean)
        class B(val valueB: Boolean, val a: A?)
        class C(val valueC: Boolean, val b: B?)

        val c: C? = C(true, B(false, A(false)))
        notNull(c)
            ?.beTrue { valueC }
            ?.notNull { b }
            ?.beTrue { valueB }
            ?.notNull { a }
            ?.beTrue { valueA }
            ?.run {
                println("valueA = $valueA")
                assertFails("should not call here") {

                }
            }

        val cond = true

        beTrue(cond) {
            //do something
        }
    }


}