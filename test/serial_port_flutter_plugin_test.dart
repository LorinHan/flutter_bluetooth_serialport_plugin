import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:serial_port_flutter_plugin/serial_port_flutter_plugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('serial_port_flutter_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await SerialPortFlutterPlugin.platformVersion, '42');
  });
}
