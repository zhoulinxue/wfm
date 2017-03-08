# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\androidSdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
-optimizationpasses 5  #指定代码的压缩级别 0 - 7
-dontusemixedcaseclassnames  #是否使用大小写混合
-dontskipnonpubliclibraryclasses  #如果应用程序引入的有jar包，并且想混淆jar包里面的class
-dontpreverify  #混淆时是否做预校验（可去掉加快混淆速度）
-verbose #混淆时是否记录日志（混淆后生产映射文件 map 类名 -> 转化后类名的映射
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  #淆采用的算法
-ignorewarnings
-keep public class * extends android.app.Activity  #所有activity的子类不要去混淆
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends java.lang.Thread
-keep public class * extends android.os.AsyncTask
-keep public class com.android.vending.licensing.ILicensingService #指定具体类不要去混淆
-dontwarn android.support.v4.**
-dontwarn **CompatHoneycomb
-dontwarn **CompatHoneycombMR2
-dontwarn **CompatCreatorHoneycombMR2
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-dontnote android.app.**
-keep class android.app.**

-keep class com.github.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;  #保持 native 的方法不去混淆
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);  #保持自定义控件类不被混淆，指定格式的构造方法不去混淆
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View); #保持指定规则的方法不被混淆（Android layout 布局文件中为控件配置的onClick方法不能混淆）
}

-keep public class * extends android.view.View {  #保持自定义控件指定规则的方法不被混淆
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclassmembers enum * {  #保持枚举 enum 不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {  #保持 Parcelable 不被混淆（aidl文件不能去混淆）
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable #需要序列化和反序列化的类不能被混淆（注：Java反射用到的类也不能被混淆）

-keepclassmembers class * implements java.io.Serializable { #保护实现接口Serializable的类中，指定规则的类成员不被混淆
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keepclassmembers class **.R$* {
    public static <fields>;
}

# rxjava
-keep class rx.**

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# okhttp
-dontwarn okhttp3.**
-keep class okhttp3.*
-keep class okhttp3.internal.*
-keep class okhttp3.internal.http.*
-keep class okio.*

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-keep class **.Webview2JsInterface { *; }  #保护WebView对HTML页面的API不被混淆
-keepclassmembers class * extends android.webkit.WebViewClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String);
}
#对WebView的简单说明下：经过实战检验,做腾讯QQ登录，如果引用他们提供的jar，若不加防止WebChromeClient混淆的代码，oauth认证无法回调，反编译基代码后可看到他们有用到WebChromeClient，加入此代码即可。

-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.**{*;}
-keep class net.sourceforge.pinyin4j.format.**{*;}
-keep class net.sourceforge.pinyin4j.format.exception.**{*;}

-dontwarn com.alibaba.fastjson.**
-dontnote com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keep class com.alibaba.fastjson.annotation.** { *; }
-keep class com.alibaba.fastjson.asm.**{*;}
-keep class com.alibaba.fastjson.parser.**
-keep class com.alibaba.fastjson.parser.**{*;}
-keep class com.alibaba.fastjson.parser.deserializer.**{*;}
-keep class com.alibaba.fastjson.serializer** { *; }
-keep class com.alibaba.fastjson.support.spring.**{*;}
-keep class com.alibaba.fastjson.util.** { *; }

-keep class com.chinaMoblie.** { *; }
-keep class com.iflytek.** { *; }
-keep class io.netty.** { *; }
-keep class com.alibaba.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class com.ut.device.UTDevice.**
-keep class com.ut.device.UTDevice.**{*;}
-keep class com.google.gson.**{*;}
-keep class com.android.volley.**{*;}
-keepclasseswithmembers class io.netty.** {*;}
-keepnames class io.netty.** { *;}
-keep class org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl.**
-keep class org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl.**{*;}

-dontwarn com.google.**
-dontnote com.google.**
-keep class com.google.**
-keep class com.google.**{*;}

#极光代码混淆
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
# bugly
-dontwarn com.tencent.bugly.**
-dontnote com.tencent.bugly.**
-keep class com.tencent.bugly.**
-keep class com.tencent.bugly.**{*;}
-keep class com.tencent.bugly.crashreport.**
-keep class com.tencent.bugly.crashreport.**{*;}




#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# # -------------------------------------------
# #  ######## greenDao混淆  ##########
# # -------------------------------------------
-dontwarn de.greenrobot.dao.**
-keep class de.greenrobot.**
-keep class de.greenrobot.**{*;}
-keep class de.greenrobot.dao.** {*;}
-keep class de.greenrobot.dao.internal.**
-keep class de.greenrobot.dao.internal.**{*;}
-keep class de.greenrobot.dao.internal.DaoConfig.**{*;}
-keep class de.greenrobot.daogenerator.**
-keep class de.greenrobot.daogenerator.**{*;}
-keep class de.greenrobot.daogenerator.DaoGenerator.**
-keep class de.greenrobot.daogenerator.DaoGenerator.**{*;}

-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn javax.servlet.**
-dontnote javax.servlet.**
-keep class javax.servlet.**
-keep class javax.servlet.**{*;}
## ----------------------------------
##   ########## Gson混淆    ##########
## ----------------------------------
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.Android.model.** { *; }
# OkHttp3
-dontwarn com.squareup.okhttp3.**
-dontwarn rx.internal.**
-keep class rx.internal.**
-keep class rx.internal.**{*;}
-keep class khttp.internal.Platform.connectSocket.**
-keep class khttp.internal.Platform.connectSocket.**{*;}
-keep class retrofit.RxJavaCallAdapterFactory.**
-keep class retrofit.RxJavaCallAdapterFactory.**{*;}
-keep class com.squareup.okhttp3.** { *;}

-keep class rx.schedulers.Schedulers.**
-keep class rx.schedulers.Schedulers.**{*;}
-keep class rx.internal.util.**
-keep class rx.internal.util.**{*;}
-keep class rx.android.schedulers.AndroidSchedulers.**
-keep class rx.android.schedulers.AndroidSchedulers.**{*;}
# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-dontwarn okio.Source.**
-keep class com.squareup.okhttp.RequestBody.**
-keep class com.squareup.okhttp.RequestBody.**{*;}
-keep class okio.Source.**
-keep class okio.Source.**{*;}
-keep class okio.Buffer.**
-keep class okio.Buffer.**{*;}
-keep class okio.BufferedSink.**
-keep class okio.BufferedSink.**{*;}

-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

#15 retrofit2
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
-dontnote java.nio.**
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain declared checked exceptions for use by a Proxy instance.
-dontwarn retrofit.**
-keep class retrofit.**
-keep class retrofit.** { *; }
-dontnote java.nio.**
-keep class java.nio.**{ *; }
-dontnote rx.**
-keep class rx.**
-keep class rx.**{ *; }
-dontnote retrofit.RequestBuilder.**
-keep class retrofit.RequestBuilder.**
-keep class retrofit.RequestBuilder.**{*;}
-dontnote okio.ForwardingSource.**
-keep class okio.ForwardingSource.**
-keep class okio.ForwardingSource.**{*;}
-keep class retrofit.Platform.**
-keep class retrofit.Platform.**{*;}

-dontwarn sun.misc.Unsafe.**
-keep class sun.misc.Unsafe.**
-keep class sun.misc.Unsafe.**{*;}

-dontwarn org.kymjs.kjframe.**
-keep class org.kymjs.kjframe.**
-keep class org.kymjs.kjframe.**{*;}

-dontnote com.squareup.retrofit.**
-keep class com.squareup.retrofit.**
-keep class com.squareup.retrofit.**{*;}


-dontnote com.github.CymChad.**
-keep class com.github.CymChad.**
-keep class com.github.CymChad.**{*;}

-dontwarn io.reactivex.**
-keep class io.reactivex.**
-keep class io.reactivex.**{ *; }


-dontwarn de.hdodenhof.**
-keep class de.hdodenhof.**
-keep class de.hdodenhof.**{*;}

-dontwarn com.bumptech.glide.Glide.**
-keep class com.bumptech.glide.Glide.**
-keep class com.bumptech.glide.Glide.**{*;}

-dontwarn com.zx.wfm.bean.**
-keep class com.zx.wfm.bean.**
-keep class com.zx.wfm.bean.**{*;}
-dontwarn com.zx.wfm.dao.**
-keep class com.zx.wfm.dao.**
-keep class com.zx.wfm.dao.**{*;}
-keep class com.zx.wfm.dao.**{*;}
-dontwarn com.zx.wfm.GeneratorTool.**
-keep class com.zx.wfm.GeneratorTool.**{*;}

# ptr
-dontwarn com.aspsine.swipetoloadlayout.**
-keep  class com.aspsine.swipetoloadlayout.**
-keep  class com.aspsine.swipetoloadlayout.**{*;}

-dontwarn com.avos.**
-dontnote com.avos.**
-keep class com.avos.**
-keep class com.avos.**{*;}
-keep class com.avos.avoscloud.**
-keep class com.avos.avoscloud.**{*;}

-dontwarn org.**
-keep class org.**
-keep class org.**{*;}
-keep class org.jaxen.**
-keep class org.jaxen.**{*;}
-dontnote org.apache.**
-keep class org.apache.**
-keep class org.apache.**{*;}
-keep class org.apache.commons.**
-keep class org.apache.log4j.**
-keep class org.apache.log4j.**{*;}
-dontnote org.apache.commons.**
-keep class org.apache.commons.**{*;}

-dontwarn com.loopj.**
-keep class com.loopj.**
-keep  class com.loopj.**{*;}

-dontwarn org.jivesoftware.smack.**
-keep class org.jivesoftware.smack.**
-keep class org.jivesoftware.smack.**{*;}

-dontnote android.net.http.SslError
-dontnote android.webkit.WebViewClient
-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient

-dontwarn sun.security.**
-keep class sun.security.** { *; }

-dontwarn com.jcraft.jzlib.**
-keep class com.jcraft.jzlib.**{*;}

-dontwarn sun.misc.**
-keep class sun.misc.**{*;}


-dontwarn org.slf4j.**
-keep class org.slf4j.**
-keep class org.slf4j.**{*;}
-dontwarn org.dom4j.**
-keep class org.dom4j.**
-keep class org.dom4j.**{*;}

-dontwarn org.xbill.**
-keep class org.xbill.**
-keep class org.xbill.**{*;}
-dontwarn org.apache.log4j.**
-keep class org.apache.log4j.**
-keep class org.apache.log4j.**{*;}

-dontwarn freemarker.**
-keep class freemarker.**{*;}
-dontwarn freemarker.ext.**
-keep class freemarker.ext.**
-keep class freemarker.ext.**{*;}

-dontnote com.gongwen.**
-keep class com.gongwen.**
-keep class com.gongwen.**{*;}
-keep class com.gongwen.marqueen.MarqueeFactory.**
-keep class com.gongwen.marqueen.MarqueeFactory.**{*;}
-dontnote org.jsoup.**
-dontwarn org.jsoup.**
-keep class org.jsoup.**
-keep class org.jsoup.**{*;}

-dontwarn com.romainpiel.shimmer.**
-dontwarn com.romainpiel.**
-keep class com.romainpiel.**
-keep class com.romainpiel.**{*;}
-keep class com.romainpiel.shimmer.**
-keep class com.romainpiel.shimmer.**{*;}
-keep class com.romainpiel.shimmer.Shimmer.**
-keep class com.romainpiel.shimmer.Shimmer.**{*;}

-dontwarn butterknife.internal.**
-dontwarn butterknife.ButterKnife.**
-dontnote butterknife.ButterKnife.**
-keep class butterknife.ButterKnife.**
-keep class butterknife.ButterKnife.**{*;}
-keep class butterknife.BindView.**
-keep class butterknife.BindView.**{*;}
-dontnote butterknife.internal.**
-keep class butterknife.internal.**
-keep class butterknife.internal.**{*;}
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-dontwarn com.yalantis.contextmenu.lib.**
-keep class com.yalantis.contextmenu.lib.**
-keep class com.yalantis.contextmenu.lib.**{*;}

-dontwarn com.nineoldandroids.**
-dontwarn com.nineoldandroids.animation.**
-keep class com.nineoldandroids.**
-keep class com.nineoldandroids.**{*;}
-keep class com.nineoldandroids.animation.**
-keep class com.nineoldandroids.animation.**{*;}

-dontwarn com.tencent.**
-keep class com.tencent.**
-keep class com.tencent.**{*;}

-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient

-dontwarn pub.devrel.easypermissions.**
-keep class pub.devrel.easypermissions.EasyPermissions.**
-keep class pub.devrel.easypermissions.EasyPermissions.**{*;}

























