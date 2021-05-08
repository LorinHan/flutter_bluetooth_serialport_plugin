import 'dart:async';

import 'package:flutter/services.dart';

class SerialPortFlutterPlugin {
  static const MethodChannel _channel =
      const MethodChannel('medical_waste_plugin');

  static const BasicMessageChannel _messageChannel =
      const BasicMessageChannel('bt_scanner_message', StandardMessageCodec());

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get BTInit async {
    return await _channel.invokeMethod('BTInit', <String, dynamic>{'mac': 'FD:58:FA:A1:DF:5A'});
  }

  static Future<String> get BTPrinter async {
    await _channel.invokeMethod('BTPrinter', <String, dynamic>{
      'hospitalName': '福建省厦门市协和医院',
      'url': 'https://www.baidu.com?code=Q0000000003',
      'code': 'Q0000000003', 'departmentName': '药剂科',
      'userName': '张三', 'mode': '感染性',
      'weight': '1.21KG', 'time': '2021-05-07'
    });
  }

  static Future<String> get BTScan async {
    _messageChannel.setMessageHandler(_handleMessage);
    await _channel.invokeMethod('BTScan');
  }

  static Future<String> _handleMessage(message) async {
    print("receive");
    print(message);
  }
}
