java-apns-gae
=======

Java APNS library that works on Google App Engine.

```java
new PushNotification()
  .setAlert("You got your emails.")
  .setBadge(9)
  .setSound("bingbong.aiff")
  .setDeviceTokens("777d2ac490a17bb1d4c8a6ec7c50d4b1b9a36499acd45bf5fcac103cde038eff");
```
For documentation and additional information see the [the website][1].


Download
--------

Download [the latest JAR][2] or grab via Maven:
```xml
<dependency>
    <groupId>com.zsoltsafrany</groupId>
    <artifactId>java-apns-gae</artifactId>
    <version>1.2.0</version>
</dependency>
```


License
--------

    The MIT License (MIT)

    Copyright (c) 2014 Zsolt Safrany

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.


 [1]: https://zsoltsafrany.github.io/java-apns-gae
 [2]: http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.zsoltsafrany&a=java-apns-gae&v=LATEST
