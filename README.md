# EmrKotlin
The kotlin lib for Expectation miss record pattern

This the kotlin lib for the [pattern : Expectation miss record](https://github.com/Arrowyi/EMRJava)

## Download lib
### Gradle
```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}

```
```groovy
dependencies {
    implementation 'com.github.Arrowyi:EmrKotlin:main-SNAPSHOT'
}
```
### Maven
```java
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```java
<dependency>
    <groupId>com.github.Arrowyi</groupId>
    <artifactId>EmrKotlin</artifactId>
    <version>main-SNAPSHOT</version>
</dependency>
```

## How to use
### For null check
**There two method for notNull(), one is the globe function, one is the scope function,the scope function pass this and return the lambda returned value**.

like this:
```kotlin
fun testNotNull2() {
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
        ?.let { println("valueA = ${it.valueA}") }

}
```
If you test the above case, the output like this:

```java
        valueA = 1
        :  expectation miss record : not null check failed ->  at : emr.kt , indi.arrowyi.EmrTest, testNotNull2, 80
        indi.arrowyi.emr.EmrKt.getFileNameAndLineNumber(Emr.kt:124)
        indi.arrowyi.emr.EmrKt.notNull1(Emr.kt:96)
        indi.arrowyi.emr.EmrKt.notNull1$default(Emr.kt:90)
        indi.arrowyi.EmrTest.testNotNull2(emr.kt:80)
        java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        java.base/java.lang.reflect.Method.invoke(Method.java:566)
```
see, the call chain of **c** is normal to print the "valueA = 1", and the **c2** call chain is failed, and output the log automatically, we can locate the error immediately **a** is null.

Most time, you just tend to simple null check for protection, in java like this:
```java
if(c != null)
{
    //do something with c
}

```
now, you can do it like this with **expectation miss record**:
```kotlin
notNull(c2) {
    //do something with c2
    assertNotNull(c2)
}

```

### For the condition check
**beTrue** also have the globe and the scope function, the globe function return the lambda value, and the scope function return the value itself.

Here shows the scope function of beTrue

```kotlin
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
```

And most time, we could use the globe function like this:
```kotlin
val cond = true
beTrue(cond) {
    //do something 
}
```
## Conclusion
| Function             | Object reference |   Return value | Is extend function
|----------------------|:----------------:|---------------:| ---------------:|
| T.beTrue / T.beFalse |       this       | Context object | Yes |
| T.notNull            |       this       |   lambda value | Yes |
| beTrue / beFalse     |       N/A        |   lambda value | No  |
| notNull              |       it         |   lambda value | No  |

More usage you could check the test code , or the source code , it is simple, but could make your life easier as I wish.
