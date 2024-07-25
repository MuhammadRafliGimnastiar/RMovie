-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

-keep class com.gimnastiar.core.** {*;}
-dontwarn com.gimnastiar.core.**
-keepattributes Exceptions, Signature, InnerClasses


-dontobfuscate