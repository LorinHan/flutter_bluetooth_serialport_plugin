package com.example.serial_port_flutter_plugin;

import android.app.Application;
import android.content.Context;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpcl.PrinterHelper;
import io.flutter.plugin.common.BasicMessageChannel;

public class BlueTooth {
    private static boolean isOpening;
    public static BasicMessageChannel messageChannel;
    public static Context context;
    public static String BTMac;

    public static void BTScan() {
        BleManager instance = BleManager.getInstance();
        BTInit(instance);
        instance.scan(scanner());
    }

    private static void BTInit(BleManager instance) {
        instance.init((Application) context);
        instance.enableLog(false);
        if (!instance.isBlueEnable() && !isOpening) {
            instance.enableBluetooth();
            isOpening = true;
            for (int i = 0; i < 6; i++) {
                if (instance.isBlueEnable()) {
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            isOpening = false;
        }
    }

    private static BleScanCallback scanner() {
        return new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {

            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                Map<String, Object> data = new HashMap<>();
                data.put("mac", bleDevice.getMac());
                data.put("name", bleDevice.getName());
                data.put("rssi", bleDevice.getRssi());
                messageChannel.send(data);
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {

            }
        };
    }

    public static boolean print(String hospitalName, String url, String code, String departmentName, String userName, String mode, String weight, String time) {
        try {
            if (PrinterHelper.getstatus() == -1) {
                reConnectPrinter();
            }
            PrinterHelper.printAreaSize("0", "200", "220", "340", "1");
            PrinterHelper.SetBold("1");
            PrinterHelper.SetMag("2", "2");
            PrinterHelper.Align(PrinterHelper.CENTER);
            PrinterHelper.Text(PrinterHelper.TEXT, "0", "0", "0", "20", hospitalName);
            PrinterHelper.SetBold("0");
            PrinterHelper.SetMag("1", "1");
            PrinterHelper.Align(PrinterHelper.LEFT);
            PrinterHelper.PrintQR(PrinterHelper.BARCODE, "75", "110", "2", "6", url);
            PrinterHelper.Text(PrinterHelper.TEXT, "0", "0", "95", "300", code);
            PrinterHelper.SetBold("1");
            PrinterHelper.Text(PrinterHelper.TEXT, "7", "0", "320", "120", "科室：" + departmentName);
            PrinterHelper.Text(PrinterHelper.TEXT, "7", "0", "320", "160", "医废收集人：" + userName);
            PrinterHelper.Text(PrinterHelper.TEXT, "7", "0", "320", "200", "医废类型：" + mode);
            PrinterHelper.Text(PrinterHelper.TEXT, "7", "0", "320", "240", "重量：" + weight);
            PrinterHelper.Text(PrinterHelper.TEXT, "7", "0", "320", "280", "登记日期：" + time);
            PrinterHelper.Form();
            PrinterHelper.Print();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            reConnectPrinter();
            return false;
        }
    }

    public static int connectPrinter() {
        BTInit(BleManager.getInstance());
        try {
            return PrinterHelper.portOpenBT(context, BTMac);
        } catch (Exception e) {
            e.printStackTrace();
            return -9;
        }
    }

    public static int reConnectPrinter() {
        try {
            PrinterHelper.portClose();
            return connectPrinter();
        } catch (Exception e) {
            e.printStackTrace();
            return -10;
        }
    }
}
