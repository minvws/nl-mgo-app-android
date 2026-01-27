# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.eclipsesource.v8.** { *; }
-keep class nl.rijksoverheid.mgo.data.uiSchema.** {  *; }
-keep class nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory { *; }
# Keep all iText classes to prevent issues due to reflection or stripped classes
-keep class com.itextpdf.** { *; }
-dontwarn com.itextpdf.**

# Keep interfaces and annotations used at runtime
-keep @interface com.itextpdf.**
-keep interface com.itextpdf.**

# Keep font classes (important if using custom fonts)
-keep class com.itextpdf.io.font.** { *; }

# Required for layout (tables, paragraphs, document model)
-keep class com.itextpdf.layout.** { *; }

