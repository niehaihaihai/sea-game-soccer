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
#-dontpreverify
# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes Signature
# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class * implements java.io.Serializable {*;}
-keep class * implements android.os.Parcelable {*;}


-dontwarn rx.**
-keep class rx.** {*;}

-dontwarn com.aliyun.**
-keep class com.aliyun.** { *;}

-dontwarn com.alibaba.**
-keep class com.alibaba.** { *;}

-dontwarn demo.**
-keep class demo.**{*;}

-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.**{*;}

-dontwarn org.apache.**
-keep class org.apache.**{*;}

-dontwarn bolts.**
-keep class bolts.**{*;}

-dontwarn com.baidu.**
-keep class com.baidu.**{*;}
-dontwarn com.duowan.**
-keep class com.duowan.**{*;}

-dontwarn com.facebook.**
-keep class com.facebook.**{*;}

-dontwarn com.iflytek.**
-keep class com.iflytek.**{*;}

-dontwarn com.facebook.**
-keep class com.facebook.**{*;}

-dontwarn u.aly.**
-keep class u.aly.**{*;}

-dontwarn com.umeng.analytics.**

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}

-dontwarn okio.**
-keep class okio.**{*;}

-dontwarn android.**
-keep class android.**{*;}

-dontwarn java.util.**
-keep class java.util.**{*;}

-dontwarn retrofit2.**
-keep class retrofit2.**{*;}

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}


-keep class org.apache.cordova.**{*;}
-keep class org.bsc.cordova.**{*;}
-keep class com.coocaa.cordova.plugin.**{*;}
-keep class com.coocaa.powerwebview.**{*;}
-keep class com.lampa.startapp.**{*;}
-keep class com.coocaa.x.xforothersdk.**{*;}

-dontwarn com.skyworth.**

-dontwarn coocaa.webactivitysdk.*
-keep class coocaa.webactivitysdk.** {*;}