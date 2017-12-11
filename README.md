## what is syuio
syuio 是一个处理socket消息，针对socket消息提供打包以及解包，达到安全的目的，
并能通过注解自动映射到逻辑处理；
且适用于`websocket` `TCP` `UDP` 的消息工具。

## Preface
* syuio 基于`jdk1.8`开发
* syuio 写入buffer时的字节顺序为`ByteOrder.BIG_ENDIAN`,即为`大端`
* syuio 单次发送量最大为`30kb`
* syuio 内置缓冲区容量为`128kb`
* syuio 目前为测试阶段

## 协议消息
1.针对socket消息处理分为协议消息的`解包`/`打包`,称为`Protocol` <br>
2.根据协议头部信息自动匹配处理逻辑类，称为`ProtocolService` <br>
3.自带4种单位类分别为 `Uint8` ，`Uint16` ， `Uint32` ， `Uint64` <br>
4.支持Java `String` ， `Integer` , `Short` , `Long`

## 容器
内置了容器，接口类`Cr`中提供了一系列接口，您可`syuio.getIoc`以完成您的操作，
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

### `@BasisProtocol`
注明此类包含有协议内部类
```
@BasisProtocol
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
@com.syuio.annotation.ProtocolService
public class ProtocolService {
    //逻辑处理方法
    
}
```

### `@Itinerary`
注明此`方法`具体逻辑处理方法，`mType`对应`Protocol mType`，且参数为`Protocol`类
```
@com.syuio.annotation.ProtocolService
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

### `syuio.properties`

```
syuio.basePackage=com.syuio //保留参数
syuio.protocol.servicePackage=com.model //逻辑处理类package
syuio.protocol.protocolPackage=com.model //协议类 package
syuio.scan.recursively=true //是否根据package 逐层遍历
syuio.port=9876 //保留参数
```

### `启动`
```
Syuio syuio = Syuio.syuio();
syuio.initialize();

```

### `接收消息`

```
Syuio syuio = Syuio.syuio();
byte[] b = ...;
ProtoBuffer protoBuffer = new ProtoBuffer();
ByteBuffer byteBuffer =ByteBuffer.wrap(b);
protoBuffer.addBuff(byteBuffer);
//处理接收的消息，解包验证成功之后将自动反射逻辑处理
syuio.createProtocolApp().receive(protoBuffer);
```

### `打包`
```
Syuio syuio = Syuio.syuio();
//传入协议类返回byte[]
byte[] b = syuio.createProtocolApp().pack(user);

```

