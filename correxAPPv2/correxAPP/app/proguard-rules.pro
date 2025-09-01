-keepattributes *Annotation*,Signature,InnerClasses

# Hilt
-keep class dagger.hilt.internal.aggregatedroot.codegen.*
-keep class com.correxapp.Hilt_CorrexApplication
-keep class hilt_aggregated_deps.*
-dontwarn dagger.hilt.processor.internal.aggregateddeps.AggregatedDeps

# Room
-keep class androidx.room.** { *; }
-keep class com.correxapp.data.database.entity.** { *; }

# Kotlinx Serialization & Coroutines
-keepclassmembers class kotlinx.serialization.internal.** { *; }
-keep class com.correxapp.data.** { *; }
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable <fields>;
}
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory
-keepclassmembers class kotlinx.coroutines.internal.MainDispatcherFactory {
    kotlin.coroutines.MainCoroutineDispatcher createDispatcher(java.util.List);
}

# ML Kit (Reflection)
-keep class com.google.android.gms.internal.mlkit_vision_text_common.** {*;}
-keep class com.google.mlkit.vision.text.** {*;}
-dontwarn com.google.android.gms.**

# ZXing
-keep class com.google.zxing.** { *; }
-keep class com.journeyapps.barcodescanner.** { *; }

# Camera
-keep class androidx.camera.** { *; }
