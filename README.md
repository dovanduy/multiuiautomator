MultiUIAutomator
=======================

This library helps creating functional UI tests that span multiple Android 
devices.

The library provides a API similar to the standard UIAutomator API included in 
the Android SDK.

The tests however are not run on the device itself, but instead on a host 
computer (using JUnit for Java SE). The provided API takes care of forwarding 
the commands to the different devices that are currently under test.

The library provides a new base class for tests, called `UiAutomatorTestCase`
that provides methods to setup emulator instances where the test will be run. 

To use the testcase you need to pass the `ANDROID_SDK` variable to java.
This variable must point to the path where it is possible to find a Android SDK.
The variable can be passed to java as follows:

```  
java -jar mytest.jar -DANDROID_SDK=/opt/adt/sdk
```

You can find an example of using `UiAutomatorTestCase` in the folder `examples`.

The package also contains the class `SdkTools` that helps with creating emulator
instances and control android devices.

Internally the remote procedure calls are forwarded to a (JSON-RPC server)[1] 
running on the devices using the library (jsonrpc4j)[2].

[1]: https://github.com/xiaocong/android-uiautomator-jsonrpcserver
[2]: https://code.google.com/p/jsonrpc4j/