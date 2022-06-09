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

package indi.arrowyi.emr

private lateinit var log: LogPrintInterface

class Emr(logPrintInterface: LogPrintInterface) {
    init {
        log = logPrintInterface
    }

}

object SafeCallEmr {
    operator fun invoke(msg: String = "", tag: String = "") {
        log.print(tag, "expectation miss record : SafeCall failed -> $msg at : ${getFileNameAndLineNumber()}")
    }
}

fun <T> beTrue(
    value: Boolean,
    msg: String = "", tag: String = "",
    action: () -> T
): T? {
    return if (value) action() else {
        log.print(tag, " expectation miss record : be true failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

fun <T> beFalse(
    value: Boolean,
    msg: String = "", tag: String = "",
    action: () -> T
): T? {
    return if (!value) action() else {
        log.print(tag, " expectation miss record : be false failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

fun <T, R> notNull(
    value: T,
    msg: String = "", tag: String = "",
    action: (T) -> R
): R? {
    return if (value != null) action(value) else {
        log.print(tag, " expectation miss record : not null check failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

fun <T, R> beNull(
    value: T,
    msg: String = "", tag: String = "",
    action: () -> R
): R? {
    return if (value == null) action() else {
        log.print(tag, " expectation miss record : should null check failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

fun <T> notNull(
    value: T,
    msg: String = "", tag: String = "",
): T? {
    return if (value != null) value else {
        log.print(tag, " expectation miss record : not null check failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

@JvmName("notNull1")
fun <T, R> T.notNull(
    msg: String = "", tag: String = "",
    check: T.() -> R
): R? {
    val v = check(this)
    return if (v != null) v else {
        log.print(tag, " expectation miss record : not null check failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}


fun <T> T.beTrue(
    msg: String = "", tag: String = "",
    check: T.() -> Boolean
): T? {
    return if (check(this)) this else {
        log.print(tag, "expectation miss record : be true failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

fun <T> T.beFalse(
    msg: String = "", tag: String = "",
    check: T.() -> Boolean
): T? {
    return if (!check(this)) this else {
        log.print(tag, "expectation miss record : be false failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}


private fun getFileNameAndLineNumber(): String {
    val throwable = Throwable()
    val element: StackTraceElement = throwable.stackTrace[3]
    val sb = StringBuffer()
    for (i in throwable.stackTrace.indices) {
        sb.append(throwable.stackTrace[i])
        sb.append(System.lineSeparator())
        if (i > 6) break
    }

    return "${element.fileName} , ${element.className}, ${element.methodName}, ${element.lineNumber} ${System.lineSeparator()} $sb"

}
