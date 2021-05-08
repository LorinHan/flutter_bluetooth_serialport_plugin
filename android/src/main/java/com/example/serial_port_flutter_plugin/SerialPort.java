package com.example.serial_port_flutter_plugin;

import com.example.serial_port_flutter_plugin.util.SerialPortUtil;

public class SerialPort {
    public static void getWeight() {
        // "/dev/ttyS0"
        SerialPortUtil.open("/dev/ttyO3", 9600, 04000);
        // try {
        //     // System.out.println(SerialPortUtil.inputStream.available());
        //     // if (SerialPortUtil.inputStream.available() <= 0) {
        //     //     result.success("0");
        //     //     return;
        //     // }
        //     byte[] data = {0x16, 0x52, 0x00};
        //     SerialPortUtil.sendString(ByteUtil.getSum16(data, data.length), null);
        //     System.out.println("write ok");
        //     byte[] readData = new byte[32];
        //     int size = SerialPortUtil.inputStream.read(readData);
        //     System.out.println("read ok");
        //     if (size != 0) {
        //         String strData = ByteUtil.byteToStr(readData, size);
        //         result.success(strData);
        //     } else {
        //         result.success("0");
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        //     result.success("0");
        // }

        // SerialPortFinder s = new SerialPortFinder();
        // String[] allDevices = s.getAllDevices();
        // System.out.println(Arrays.toString(allDevices));
        // byte[] data = {0x16, 0x52, 0x00};
        // SerialPortUtil.sendString(ByteUtil.getSum16(data, data.length), null);
    }
}
