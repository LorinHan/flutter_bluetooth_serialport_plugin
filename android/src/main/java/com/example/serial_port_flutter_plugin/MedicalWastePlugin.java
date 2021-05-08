package com.example.serial_port_flutter_plugin;

import android.content.Context;

import androidx.annotation.NonNull;

import com.clj.fastble.BleManager;
import com.example.serial_port_flutter_plugin.util.SerialPortUtil;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.StandardMessageCodec;

public class MedicalWastePlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;
    private Context context;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "medical_waste_plugin");
        channel.setMethodCallHandler(this);
        context = flutterPluginBinding.getApplicationContext();
        BlueTooth.messageChannel = new BasicMessageChannel(flutterPluginBinding.getBinaryMessenger(), "bt_scanner_message", StandardMessageCodec.INSTANCE);
        BlueTooth.context = context;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            SerialPort.getWeight();
        } else if (call.method.equals("BTScan")) {
            BlueTooth.BTScan();
        } else if (call.method.equals("BTInit")) {
            BlueTooth.BTMac = call.argument("mac");
            int res = BlueTooth.reConnectPrinter();
            if (res == 0) {
                result.success("连接成功");
            } else {
                result.error("500", "连接失败", null);
            }
        } else if (call.method.equals("BTPrinter")) {
            boolean ok = BlueTooth.print(call.argument("hospitalName").toString(), call.argument("url").toString(), call.argument("code").toString(), call.argument("departmentName").toString(), call.argument("userName").toString(), call.argument("mode").toString(), call.argument("weight").toString(), call.argument("time").toString());
            if (ok) {
                result.success("ok");
            } else {
                result.error("500", "打印失败", null);
            }
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
