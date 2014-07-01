ART Bench
=========
ART Bench is a simple demonstration of the performance difference between the new Android Runtime (ART) and the current Dalvik Virtual Machine.  To fully illustrate these differences, I've employed the [New Relic Android agent](http://newrelic.com/android-app-optimization) to collect performance metrics and send them to back to New Relic.


Getting Started
---------------
First, you'll need a New Relic account.  If you don't have one, you can always [sign up for free](http://newrelic.com/signup).  Next, just click on "Mobile Apps" and then "Add a new app".  Follow the instructions for Gradle and add your application token to the ```onCreate``` method in ```MyActivity```.

Now you'll need a device configured with ART.  You'll find the option in your Developer Options menu:
![enabling ART](http://i-cdn.phonearena.com/images/articles/100048-thumb/Screenshot-2.jpg)

You can use a physical device or an emulator such as [Genymotion](http://www.genymotion.com/).


Benchmarking
------------
There are two ways to observe the benchmark results.  You can just tail the device logs (run ```adb logcat``` or open the pane in Android Studio) or wait a minute for the results to post to New Relic.  Of course, with New Relic you'll be able to see individual benchmark traces.  Feel free to replace the spectral norm code with your own benchmark.  You can find more excellent examples [here](http://benchmarksgame.alioth.debian.org/u64q/java.php).


License
-------
BSD
