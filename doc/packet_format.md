# Packet Format 数据包格式

[TOC]

```
   0     1     2     3     4     5     6     7
+-----+-----+-----+-----+-----+-----+-----+-----+
|         Packet        |   Header  |  Protocol |
|         Length        |   Length  |  Version  |
+-----+-----+-----+-----+-----+-----+-----+-----+
|       Operation       |     	Sequence Id     |
+-----+-----+-----+-----+-----+-----+-----+-----+
|                    Payload                    |
+-----+-----+-----+-----+-----+-----+-----+-----+

```

## 数据说明

所有数据（除Payload以外）均为 **Big Endian** 整数类型

## Packet Length 数据包长度

Packet Length = Header Length + Payload Length



## Header Length 头部长度

Header Length 固定为 16



## Protocol Version 协议版本

Protocol Version 决定了 Payload 的格式，Protocol Version 的取值为一下四种情况：

### Protocol Version 0

当 Protocol Version 为 0 时，Payload 内容为普通 JSON

### Protocol Version 1

当 Protocol Version 为 1 时，Payload 内容为 **Big Endian** int 类型数据，数值为房间当前人气值。

### Protocol Version 2

当 Protocol Version 为 2 时，Payload 内容为通过 zlib-deflate 压缩过的数据，使用时需要先通过 zlib-inflate 进行解压缩。解压后的数据为一个完整的数据包，而非 Payload，因此需要重新解析。

注：当 Protocol Version 为 2 时，zlib-inflate 解压缩后的数据可能包含不止一个完整的数据包。

### Protocol Version 3

当 Protocol Version 为 3 时，Payload 内容为通过 Brotli 压缩过的数据，使用时需要先通过解压缩。解压后的数据为一个完整的数据包，而非 Payload，因此需要重新解析。

注：当 Protocol Version 为 3 时，zlib-inflate 解压缩后的数据可能包含不止一个完整的数据包。

## Operation 操作类型

Operation 指明了数据包类型。

### Operation 2

心跳，由客户端发送，超过 70 秒没有发送会断开连接，Protocol Version 为 1，Payload 内容固定为

```
[object Object]
```

### Operation 3

心跳回应，由服务端发送，Payload 内容为 **Big Endian** int 类型数据，数值为房间当前人气值。

### Operation 5

通知信息，由服务器发送。

### Operation 7

进入房间验证，由客户端发送，进入房间超过 30 秒未发送验证的话会断开连接，Protocol Version 为 1，Payload 内容为

```json
```

### Operation 8

进入房间验证相应，由服务器发送。





## Sequence Id 序号

Sequence Id 固定为 1