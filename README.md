## what is prototransl
prototransl 全称 protocol translate , 中文意思：协议转换 <br>

prototransl 是一个处理socket消息，针对socket消息提供打包以及解包，达到安全的目的，
并能通过注解自动映射到逻辑处理；
且适用于`websocket` `TCP` `UDP` <br>

* 在网络传输环境中，我们称每条消息为`消息协议`，既然是协议，就避免不了协议的解包与打包，此类工作对于开发者来说是毫无意义可言。
而protocol transl 则是解决这一问题而产生，自动为您所定义的协议载体提供解包与打包工作。
* 既然是消息，那么就会存在被人抓包解读，prototransl 将对您的消息载体字段逐个打包转换，保证消息传输安全，除非您的载体代码公开。

> [一个根据`apache mina` + `prototransl` 开发的TCP服务端例子](https://github.com/AlexJialene/imina)<br>
> [prototransl js版本 ](https://github.com/AlexJialene/ProtoTranslJs)

## Preface
* prototransl 基于`jdk1.8`开发
* prototransl 写入buffer时的字节顺序为`ByteOrder.BIG_ENDIAN`,即为`大端`
* 单次发送的最大容量默认为`30kb`
* prototransl 内置缓冲区容量为`128kb`
* prototransl 目前为测试阶段

部分的默认配置可在prototransl.properties文件里设置

## 协议消息
1.针对socket消息处理分为协议消息的`解包`/`打包`,称为`Protocol` <br>
2.根据协议头部信息自动匹配处理逻辑类，称为`ProtocolService` <br>
3.自带4种单位类分别为 `Uint8` ，`Uint16` ， `Uint32` ， `Uint64` <br>
4.支持Java `String` ， `Integer` , `Short` , `Long` , `Boolean`

## 容器
内置了容器，接口类`Cr`中提供了一系列接口，您可`transl.getIoc`以完成您的操作，
默认单例。

## 注解
### `@Protocol`
注明此类是一个协议类，`mType`为协议唯一的标识
```
@Protocol(mType = 1)
public class User {
    private String name;
    private Uint32 phone;
    //忽略getter setter
}
```
或者在内部类使用

```
public class User {
    @Protocol(mType = 1)
    public static class User2{
        private Uint8 ret;
    }
}
```

### `@ProtocolService`
注明此类为逻辑处理类
```
@com.prototransl.annotation.ProtocolService
public class ProtocolService {
    //逻辑处理方法
    
}
```

### `@Itinerary`
注明此`方法`具体逻辑处理方法，`mType`对应`Protocol mType`，且参数为`Protocol`类
```
@com.prototransl.annotation.ProtocolService
public class ProtocolService {
    @Itinerary(mType=1)
    public void proto1(User user){
        System.out.println(user.getName());
        System.out.println(user.getPhone());

    }
}
```

## 缓冲区
内置缓冲区`ProtoBuffer` 依赖Java `ByteBuffer`，每接收一条进制消息都需先添加进缓冲区，
如果是服务端，建议每一个User配一个缓冲区

## 快速开始

### `maven 配置`
1.引入项目仓库
```
<repositories>
    <repository>
        <id>lamkeizyi-repo</id>
        <url>https://raw.githubusercontent.com/AlexJialene/maven-repo/master/repository</url>
    </repository>
</repositories>
```
2.下载所需要的包
```
<dependency>
    <groupId>prototransl</groupId>
    <artifactId>com.prototransl</artifactId>
    <version>1.0-beta-3</version>
</dependency>

```

### `prototransl.properties`

```
prototransl.basePackage=com.prototransl //保留参数
prototransl.protocol.servicePackage=com.model //逻辑处理类package
prototransl.protocol.protocolPackage=com.model //协议类 package
prototransl.scan.recursively=true //是否根据package 逐层遍历
prototransl.pack.capacity=1024 //单次打包的最大容量
prototransl.buffer.littleEndian=false //写入buffer的字节顺序，默认为false
prototransl.port=9876 //保留参数
```

### `启动`
```
Transl transl = Transl.transl();
transl.initialize();

```

### `接收消息`

```
Transl transl = Transl.transl();
byte[] b = ...;
ProtoBuffer protoBuffer = new ProtoBuffer();
protoBuffer.addBuff(b);
//处理接收的消息，解包验证成功之后将自动反射逻辑处理
transl.createProtocolApp().receive(protoBuffer);
```

### `打包`
```
Transl transl = Transl.transl();
//传入协议类返回byte[]
byte[] b = transl.createProtocolApp().pack(user);

```

## 进阶

这里介绍一下prototransl的不同的用法，如自带`Uint`系列类的用法

### `Uint 系列`
> 上面已经介绍了一个协议载体类声明方法，这里介绍的是协议类里面的参数

prototransl 支持java自带的 `String` ， `Integer` , `Short` , `Long`，其他的java自带类型暂时不支持，
除了String之外其他3个均可以由prototransl 的Uint类代替，prototransl也建议您使用Uint系列的代替java自带的类型；
这样可能会给你的系统带来一点效率，特别是一条消息正在解包的时候。<br>

支持的java类型对应Uint表

java类型 | 对应的Uint类 
--- | ---
Integer | Uint32
Short | Uint16
Long | Uint64
Boolean | Uint8
String |  

科普一下：1 byte(字节) = 8 bit
* Uint8 占1字节
* Uint16 占2字节
* Uint32 占4字节
* Uint64 占8字节

根据需要选择您要用的类型 <br>

例子
```
@Protocol(mType = 1)
public class User {
    private String name;
    private Uint32 phone;
    private Uint8 sex；
    private Uint16 field1；
    private Uint64 field2；
    //忽略getter setter
}
```

### `Pack and Unpack`
prototransl除自动扫描您的协议载体进行解包和打包之外还提供一下两个接口类：

* PackProtocol
* UnpackProtocol

这样的方式会带来一点效率以及您额外的逻辑编写，`缺点`：解包时候的顺序必须是您打包的顺序，默认按字段上下排序；<br>
可实现这两个接口来达到您的目的：<br>

1. Unpack
```
@Protocol(mType = 2)
public class User2 implements UnpackProtocol{
    private Uint8 ret;
    private Uint32 phone;
    //忽略构造方法
    @Override
    public boolean unpackProto(Unpack proto) {
        this.ret = proto.popUint8();
        this.phone = proto.popUint32();
        return true;
    }
}
```

2. Pack
```
@Protocol(mType = 2)
public class User2 implements PackProtocol{
    private Uint8 ret;
    private Uint32 phone;
    //忽略构造方法
    @Override
    public void packProto(Pack proto) {
        proto.push(ret);
        proto.push(phone);
    }
}
```


