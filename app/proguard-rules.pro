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
#-dontskipnonpubliclibraryclasses  #如果应用程序引入的有jar包，并且想混淆jar包里面的class
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

-keep class com.czlapp.lingyu.czlapp.util.PhoneUtils.**{*;}
-keep class com.czlapp.lingyu.czlapp.service.**
-keep class com.czlapp.lingyu.czlapp.service.**{*;}
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

#极光代码混淆
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
# bugly
-dontwarn com.tencent.bugly.**
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
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keep class com.czlapp.lingyu.czlapp.database.**
-keep class com.czlapp.lingyu.czlapp.database.**{*;}

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

# 讯飞语音
-dontwarn com.iflytek.**
-keep class com.iflytek.**
-keep class com.iflytek.** {*;}
-keep class com.iflytek.thirdparty.**
-keep class com.iflytek.thirdparty.**{*;}
-keep class com.iflytek.thirdparty.a.**
-keep class com.iflytek.thirdparty.b.**
-keep class com.iflytek.thirdparty.e.**
-keep class com.iflytek.thirdparty.h.**
-keep class com.iflytek.thirdparty.a.**{*;}
-keep class com.iflytek.thirdparty.b.**{*;}
-keep class com.iflytek.thirdparty.e.**{*;}
-keep class com.iflytek.thirdparty.h.**{*;}
-keep class com.iflytek.common.**
-keep class com.iflytek.common.**{*;}
-keep class com.iflytek.common.c.**
-keep class com.iflytek.common.c.**{*;}
-keep class com.iflytek.common.push.impl.PushImpl.**
-keep class com.iflytek.common.push.impl.PushImpl.**{*;}

#netty
-dontwarn io.netty.**
-dontwarn org.jboss.**
-dontnote org.jboss.**
-keep class org.jboss.**
-keep class org.jboss.**{*;}
-keep class io.netty.**
-keep class io.netty.**{*;}
-keep class com.czlapp.lingyu.czlapp.socket.**
-keep class com.czlapp.lingyu.czlapp.socket.**{*;}
-keep class com.czlapp.lingyu.czlapp.socket.SocketLoginMannager.**
-keep class com.czlapp.lingyu.czlapp.socket.SocketLoginMannager.**{*;}
-keep class com.czlapp.lingyu.czlapp.socket.SocketNSHandler.**
-keep class com.czlapp.lingyu.czlapp.socket.SocketNSHandler.**{*;}
-keep class com.czlapp.lingyu.czlapp.socket.SocketNSManager.**
-keep class com.czlapp.lingyu.czlapp.socket.SocketNSManager.**{*;}
-keep class com.czlapp.lingyu.czlapp.util.UpdateUtil.**
-keep class com.czlapp.lingyu.czlapp.util.UpdateUtil.**{*;}
-keep class com.czlapp.lingyu.czlapp.socket.SocketSendService.**
-keep class com.czlapp.lingyu.czlapp.socket.SocketSendService.**{*;}
-keep class com.czlapp.lingyu.czlapp.socket.SimpleSocketLisenter.**
-keep class com.czlapp.lingyu.czlapp.socket.SimpleSocketLisenter.**{*;}

-dontwarn com.czlapp.lingyu.czlapp.database.**
-keep class com.czlapp.lingyu.czlapp.database.**
-keep class com.czlapp.lingyu.czlapp.database.**{*;}
-dontwarn com.czlapp.lingyu.czlapp.domain.**
-keep class com.czlapp.lingyu.czlapp.domain.**
-keep class com.czlapp.lingyu.czlapp.domain.**{*;}
-keep class com.czlapp.lingyu.czlapp.database.**{*;}

#-keep class com.czlapp.lingyu.czlapp.api.**
#-keep class com.czlapp.lingyu.czlapp.api.**{*;}
#-keep class com.czlapp.lingyu.czlapp.api.ApiManager.**
#-keep class com.czlapp.lingyu.czlapp.api.ApiManager.**{*;}

-keepclasseswithmembers class io.netty.** { *;}
-keep class io.netty.util.**
-keep class io.netty.util.internal.**
-keep class io.netty.util.internal.ConcurrentCircularArrayQueue.**
-keep class io.netty.util.internal.ConcurrentCircularArrayQueue.**{*;}
-keep class io.netty.util.internal.JavassistTypeParameterMatcherGenerator.**
-keep class io.netty.util.internal.JavassistTypeParameterMatcherGenerator.**{*;}
-keep class io.netty.util.internal.MpscArrayQueueConsumerField.**
-keep class io.netty.util.internal.MpscArrayQueueConsumerField.**{*;}
-keepclasseswithmembers class org.jboss.netty.util.internal.LinkedTransferQueue$Node {*;}

-keepclasseswithmembers class org.jboss.netty.util.internal.LinkedTransferQueue {
    volatile transient org.jboss.netty.util.internal.LinkedTransferQueue$Node head;
    volatile transient org.jboss.netty.util.internal.LinkedTransferQueue$Node tail;
    volatile transient int sweepVotes;
}
# sharesdk 项目
-dontwarn com.czlapp.lingyu.sharesdk.**
-keep class com.czlapp.lingyu.sharesdk.**
-keep class com.czlapp.lingyu.sharesdk.**{*;}
-keep class assets.**{*;}
# 友盟
-dontwarn com.umeng.**
-keep class com.umeng.** {*; }
-keep class com.mob.**
-keep class com.umeng.analytics.b.**
-keep class com.umeng.analytics.b.** {*;}
-keep class com.mob.**{*;}
-keep class com.mob.commons.**
-keep class com.mob.commons.** {*; }
-keep class cn.sharesdk.**
-keep class cn.sharesdk.**{*;}
-keep class cn.sharesdk.tencent.qq.**
-keep class cn.sharesdk.tencent.qq.**{*;}
-keep class cn.sharesdk.framwork.**
-keep class cn.sharesdk.framwork.**{*;}
-keep class cn.sharesdk.sina.weibo.**
-keep class cn.sharesdk.sina.weibo.**{*;}
-keep class cn.sharesdk.wechat.friends.**
-keep class cn.sharesdk.wechat.friends.**{*;}
-keep class cn.sharesdk.wechat.utils.**{*;}
-keep class cn.sharesdk.wechat.favorite.**
-keep class cn.sharesdk.wechat.favorite.**{*;}
-keep class cn.sharesdk.wechat.moments.**
-keep class cn.sharesdk.wechat.moments.**{*;}
-keep class cn.sharesdk.framework.**
-keep class cn.sharesdk.framework.**{*;}
#3D 地图
-dontwarn  com.amap.api.**
-dontnote  com.amap.api.**
-keep   class com.amap.api.services.**
-keep   class com.amap.api.services.**{*;}
-keep   class com.**
-keep   class com.autonavi.**
-keep   class com.autonavi.**{*;}
-keep   class com.autonavi.amap.mapcore.MapCore.**
-keep   class com.autonavi.amap.mapcore.MapCore.**{*;}
-keep   class com.amap.api.**
-keep   class com.amap.api.**{*;}
-keep   class com.amap.api.**{*;}
-keep class assets.**{*;}
# zxing
-dontwarn com.google.zxing.**
-keep  class com.google.zxing.client.android.**
-keep  class com.google.zxing.client.android.**{*;}
-keep  class com.google.zxing.**{*;}
-keep  class com.journeyapps.barcodescanner.**
-keep  class com.journeyapps.barcodescanner.**{*;}
# ptr
-dontwarn com.chanven.lib.cptr.**
-keep  class com.chanven.lib.cptr.**
-keep  class com.chanven.lib.cptr.**{*;}
# Mp3
-dontwarn com.buihha.audiorecorder.**
-keep  class com.buihha.audiorecorder.**
-keep  class com.buihha.audiorecorder.**{*;}









