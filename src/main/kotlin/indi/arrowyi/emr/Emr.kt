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
    operator fun invoke(tag: String = "", msg: String = "") {
        log.print(tag, "expectation miss record : SafeCall failed -> $msg at : ${getFileNameAndLineNumber()}")
    }
}

fun <T> T.takeIfEmr(
    tag: String = "", msg: String = "",
    predicate: (T) -> Boolean
): T? {
    return if (predicate(this)) this else {
        log.print(tag, "expectation miss record : take if failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

fun <T> T.takeUnlessEmr(
    tag: String = "", msg: String = "",
    predicate: (T) -> Boolean
): T? {
    return if (!predicate(this)) this else {
        log.print(tag, "expectation miss record : take unless failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

fun <T> beTure(value: Boolean,
    tag: String = "", msg: String = "",
     action: () -> T
): T? {
    return if (value) action() else {
        log.print(tag, " expectation miss record : be true failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

fun <T> beFalse(value: Boolean,
    tag: String = "", msg: String = "",
     action: () -> T
): T? {
    return if (!value) action() else {
        log.print(tag, "expectation miss record : be false failed -> $msg at : ${getFileNameAndLineNumber()}")
        null
    }
}

private fun getFileNameAndLineNumber(): String {
    val throwable = Throwable()
    val element: StackTraceElement = throwable.stackTrace[3]

    return "${element.fileName} , ${element.className}, ${element.methodName}, ${element.lineNumber} ${System.lineSeparator()} ${throwable.stackTraceToString()}"

}
