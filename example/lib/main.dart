import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:serial_port_flutter_plugin/serial_port_flutter_plugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class Btn extends StatefulWidget {
  @override
  createState() => new BtnState();
}

class BtnState extends State<Btn> {
  @override
  Widget build(BuildContext context) {
    return new Text("wordPair.asPascalCase");
  }
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String weight = "0";

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  void getWeight() async {
    String w = await SerialPortFlutterPlugin.platformVersion;
    setState(() {
      weight = w;
    });
  }

  void BTInit() async {
    String res = await SerialPortFlutterPlugin.BTInit;
    print(res);
  }

  void printer() async {
    await SerialPortFlutterPlugin.BTPrinter;
  }

  void BTScan() async {
    await SerialPortFlutterPlugin.BTScan;
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    if (!mounted) return;

    setState(() {
      // _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Text('Running on: $_platformVersion\n'),
                Text('重量：$weight'),
                RaisedButton(onPressed: getWeight, child: Text('获取重量')),
                RaisedButton(onPressed: BTScan, child: Text('蓝牙扫描')),
                RaisedButton(onPressed: BTInit, child: Text('打印机初始化')),
                RaisedButton(onPressed: printer, child: Text('打印'))
              ]),
        ),
      ),
    );
  }
}
