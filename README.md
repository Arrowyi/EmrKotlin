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
### For the kotlin SafeCall
If you call the kotlin safe call, you could use the below pattern to record the expectation miss (using the elvis)
```kotlin
shouldNotNullValue?.takeIf {checkTheCondition()}?.let{doAction()}?:SafeCallEmr()
```
if any of the safecall on the chain failed, we can get the log like this:
```java
: expectation miss record : SafeCall failed ->  at : Emr.kt , indi.arrowyi.emr.SafeCallEmr, invoke$default, 13 
 java.lang.Throwable
	at indi.arrowyi.emr.EmrKt.getFileNameAndLineNumber(Emr.kt:59)
	at indi.arrowyi.emr.EmrKt.access$getFileNameAndLineNumber(Emr.kt:1)
	at indi.arrowyi.emr.SafeCallEmr.invoke(Emr.kt:14)
	at indi.arrowyi.emr.SafeCallEmr.invoke$default(Emr.kt:13)
	at indi.arrowyi.EmrTest.testPrint(emr.kt:26)
```
so, we can get info from the log, and do the further investigation.

### For the condition check, we could do it like this  :
```kotlin
beTrue(a.value){
    println("a.vlaue is true")
    a.intValue
}?.let { println("$it") }
```
If the a.value is true (which we expect it is true), than do the following action, or the beTrue() will return null and outputting the log.

More usage you could check the test code , or the source code , it is simple, but could make your life easier as I wish.
