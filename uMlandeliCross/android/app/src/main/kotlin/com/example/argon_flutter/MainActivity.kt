package com.example.argon_flutter
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

class MainActivity: FlutterActivity() {
    private val CHANNEL = "samples.flutter.dev/battery"
    @Override
        public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
                GeneratedPluginRegistrant.registerWith(flutterEngine);
                new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                        .setMethodCallHandler(
                                    (call, result) -> {
                                    // Your existing code
                            }
                );
            }
}
