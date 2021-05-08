package com.example.serial_port_flutter_plugin.util;

import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

public class SerialPortUtil {

    public static String TAG = "SerialPortUtil";

    /**
     * 标记当前串口状态(true:打开,false:关闭)
     **/
    public static boolean isFlagSerial = false;

    public static SerialPort serialPort = null;
    public static InputStream inputStream = null;
    public static OutputStream outputStream = null;
    public static Thread receiveThread = null;
    public static String strData = "";
    public static Handler mHandler;

    /**
     * 打开串口
     */
    public static boolean open(String pathName, int baudrate, int flags) {
        boolean isopen = false;
        if (isFlagSerial) {
            Log.e(TAG, "串口已经打开");
            return false;
        }
        try {
            // 9600
            serialPort = new SerialPort(new File(pathName), baudrate, flags);
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            // receive();
            isopen = true;
            isFlagSerial = true;
            Log.e(TAG, "串口成功开启");
        } catch (IOException e) {
            e.printStackTrace();
            isopen = false;
        }
        return isopen;
    }

    /**
     * 关闭串口
     */
    public static boolean close() {
        if (isFlagSerial) {
            Log.e(TAG, "串口关闭失败");
            return false;
        }
        boolean isClose = false;
        Log.e(TAG, "关闭串口");
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            isClose = true;
            isFlagSerial = false;//关闭串口时，连接状态标记为false
        } catch (IOException e) {
            e.printStackTrace();
            isClose = false;
        }
        return isClose;
    }

    /**
     * 发送串口指令
     */
    public static void sendString(String data, Handler handler) {
        // mHandler = handler;
        if (!isFlagSerial) {
            Log.e(TAG, "串口未打开,发送失败" + data);
            return;
        }
        try {
            outputStream.write(ByteUtil.hex2byte(data));
            outputStream.flush();
            Log.e(TAG, "sendSerialData:" + data);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "发送指令出现异常");
        }
    }

    /**
     * 接收串口数据的方法
     */
    public static void receive() {
        if (receiveThread != null && !isFlagSerial) {
            return;
        }
        receiveThread = new Thread() {
            @Override
            public void run() {
                while (isFlagSerial) {
                    try {
                        byte[] readData = new byte[32];
                        if (inputStream == null) {
                            return;
                        }
                        int size = inputStream.read(readData);
                        if (size > 0 && isFlagSerial) {
                            strData = ByteUtil.byteToStr(readData, size);
                            Log.e(TAG, "readSerialData:" + strData);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        receiveThread.start();
    }
}