1. 使用开源的`socket-mqtt`作为基础。详细请访问。[https://github.com/daoshenzzg/socket-mqtt](https://github.com/daoshenzzg/socket-mqtt)。
2. 编写一个CRC16校验的类，加入校验工具方法以及将数据转数字和字符串的工具方法。代码如下：
```java
package com.yb.socket.service.modbus;

public class CRC16M {
  static final String HEXES = "0123456789ABCDEF";
  byte uchCRCHi = (byte) 0xFF;
  byte uchCRCLo = (byte) 0xFF;
  private static byte[] auchCRCHi = { 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
      (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
      (byte) 0x81, (byte) 0x40 };

  private static byte[] auchCRCLo = { (byte) 0x00, (byte) 0xC0, (byte) 0xC1, (byte) 0x01, (byte) 0xC3, (byte) 0x03,
      (byte) 0x02, (byte) 0xC2, (byte) 0xC6, (byte) 0x06, (byte) 0x07, (byte) 0xC7, (byte) 0x05, (byte) 0xC5,
      (byte) 0xC4, (byte) 0x04, (byte) 0xCC, (byte) 0x0C, (byte) 0x0D, (byte) 0xCD, (byte) 0x0F, (byte) 0xCF,
      (byte) 0xCE, (byte) 0x0E, (byte) 0x0A, (byte) 0xCA, (byte) 0xCB, (byte) 0x0B, (byte) 0xC9, (byte) 0x09,
      (byte) 0x08, (byte) 0xC8, (byte) 0xD8, (byte) 0x18, (byte) 0x19, (byte) 0xD9, (byte) 0x1B, (byte) 0xDB,
      (byte) 0xDA, (byte) 0x1A, (byte) 0x1E, (byte) 0xDE, (byte) 0xDF, (byte) 0x1F, (byte) 0xDD, (byte) 0x1D,
      (byte) 0x1C, (byte) 0xDC, (byte) 0x14, (byte) 0xD4, (byte) 0xD5, (byte) 0x15, (byte) 0xD7, (byte) 0x17,
      (byte) 0x16, (byte) 0xD6, (byte) 0xD2, (byte) 0x12, (byte) 0x13, (byte) 0xD3, (byte) 0x11, (byte) 0xD1,
      (byte) 0xD0, (byte) 0x10, (byte) 0xF0, (byte) 0x30, (byte) 0x31, (byte) 0xF1, (byte) 0x33, (byte) 0xF3,
      (byte) 0xF2, (byte) 0x32, (byte) 0x36, (byte) 0xF6, (byte) 0xF7, (byte) 0x37, (byte) 0xF5, (byte) 0x35,
      (byte) 0x34, (byte) 0xF4, (byte) 0x3C, (byte) 0xFC, (byte) 0xFD, (byte) 0x3D, (byte) 0xFF, (byte) 0x3F,
      (byte) 0x3E, (byte) 0xFE, (byte) 0xFA, (byte) 0x3A, (byte) 0x3B, (byte) 0xFB, (byte) 0x39, (byte) 0xF9,
      (byte) 0xF8, (byte) 0x38, (byte) 0x28, (byte) 0xE8, (byte) 0xE9, (byte) 0x29, (byte) 0xEB, (byte) 0x2B,
      (byte) 0x2A, (byte) 0xEA, (byte) 0xEE, (byte) 0x2E, (byte) 0x2F, (byte) 0xEF, (byte) 0x2D, (byte) 0xED,
      (byte) 0xEC, (byte) 0x2C, (byte) 0xE4, (byte) 0x24, (byte) 0x25, (byte) 0xE5, (byte) 0x27, (byte) 0xE7,
      (byte) 0xE6, (byte) 0x26, (byte) 0x22, (byte) 0xE2, (byte) 0xE3, (byte) 0x23, (byte) 0xE1, (byte) 0x21,
      (byte) 0x20, (byte) 0xE0, (byte) 0xA0, (byte) 0x60, (byte) 0x61, (byte) 0xA1, (byte) 0x63, (byte) 0xA3,
      (byte) 0xA2, (byte) 0x62, (byte) 0x66, (byte) 0xA6, (byte) 0xA7, (byte) 0x67, (byte) 0xA5, (byte) 0x65,
      (byte) 0x64, (byte) 0xA4, (byte) 0x6C, (byte) 0xAC, (byte) 0xAD, (byte) 0x6D, (byte) 0xAF, (byte) 0x6F,
      (byte) 0x6E, (byte) 0xAE, (byte) 0xAA, (byte) 0x6A, (byte) 0x6B, (byte) 0xAB, (byte) 0x69, (byte) 0xA9,
      (byte) 0xA8, (byte) 0x68, (byte) 0x78, (byte) 0xB8, (byte) 0xB9, (byte) 0x79, (byte) 0xBB, (byte) 0x7B,
      (byte) 0x7A, (byte) 0xBA, (byte) 0xBE, (byte) 0x7E, (byte) 0x7F, (byte) 0xBF, (byte) 0x7D, (byte) 0xBD,
      (byte) 0xBC, (byte) 0x7C, (byte) 0xB4, (byte) 0x74, (byte) 0x75, (byte) 0xB5, (byte) 0x77, (byte) 0xB7,
      (byte) 0xB6, (byte) 0x76, (byte) 0x72, (byte) 0xB2, (byte) 0xB3, (byte) 0x73, (byte) 0xB1, (byte) 0x71,
      (byte) 0x70, (byte) 0xB0, (byte) 0x50, (byte) 0x90, (byte) 0x91, (byte) 0x51, (byte) 0x93, (byte) 0x53,
      (byte) 0x52, (byte) 0x92, (byte) 0x96, (byte) 0x56, (byte) 0x57, (byte) 0x97, (byte) 0x55, (byte) 0x95,
      (byte) 0x94, (byte) 0x54, (byte) 0x9C, (byte) 0x5C, (byte) 0x5D, (byte) 0x9D, (byte) 0x5F, (byte) 0x9F,
      (byte) 0x9E, (byte) 0x5E, (byte) 0x5A, (byte) 0x9A, (byte) 0x9B, (byte) 0x5B, (byte) 0x99, (byte) 0x59,
      (byte) 0x58, (byte) 0x98, (byte) 0x88, (byte) 0x48, (byte) 0x49, (byte) 0x89, (byte) 0x4B, (byte) 0x8B,
      (byte) 0x8A, (byte) 0x4A, (byte) 0x4E, (byte) 0x8E, (byte) 0x8F, (byte) 0x4F, (byte) 0x8D, (byte) 0x4D,
      (byte) 0x4C, (byte) 0x8C, (byte) 0x44, (byte) 0x84, (byte) 0x85, (byte) 0x45, (byte) 0x87, (byte) 0x47,
      (byte) 0x46, (byte) 0x86, (byte) 0x82, (byte) 0x42, (byte) 0x43, (byte) 0x83, (byte) 0x41, (byte) 0x81,
      (byte) 0x80, (byte) 0x40 };

  public int value;

  public CRC16M() {
    value = 0;

  }

  public void update(byte[] puchMsg, int usDataLen) {

    int uIndex;
    // int i = 0;
    for (int i = 0; i < usDataLen; i++) {
      uIndex = (uchCRCHi ^ puchMsg[i]) & 0xff;

      uchCRCHi = (byte) (uchCRCLo ^ auchCRCHi[uIndex]);
      uchCRCLo = auchCRCLo[uIndex];
    }
    value = ((((int) uchCRCHi) << 8 | (((int) uchCRCLo) & 0xff))) & 0xffff;

    return;
  }

  public void reset() {
    value = 0;
    uchCRCHi = (byte) 0xff;
    uchCRCLo = (byte) 0xff;
  }

  public int getValue() {
    return value;
  }

  private static byte uniteBytes(byte src0, byte src1) {
    byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
    _b0 = (byte) (_b0 << 4);
    byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
    byte ret = (byte) (_b0 ^ _b1);
    return ret;
  }

  private static byte[] HexString2Buf(String src) {
    int len = src.length();
    byte[] ret = new byte[len / 2 + 2];
    byte[] tmp = src.getBytes();
    for (int i = 0; i < len; i += 2) {
      ret[i / 2] = uniteBytes(tmp[i], tmp[i + 1]);
    }
    return ret;
  }

  public static byte[] getSendBuf(String toSend) {
    byte[] bb = HexString2Buf(toSend);
    CRC16M crc16 = new CRC16M();
    crc16.update(bb, bb.length - 2);
    int ri = crc16.getValue();
    bb[bb.length - 1] = (byte) (0xff & ri);
    bb[bb.length - 2] = (byte) ((0xff00 & ri) >> 8);
    return bb;
  }

  public static boolean checkBuf(byte[] bb) {
    CRC16M crc16 = new CRC16M();
    crc16.update(bb, bb.length - 2);
    int ri = crc16.getValue();
    if (bb[bb.length - 1] == (byte) (ri & 0xff) && bb[bb.length - 2] == (byte) ((0xff00 & ri) >> 8))
      return true;
    return false;
  }

  public static String getBufHexStr(byte[] raw) {
    if (raw == null) {
      return null;
    }
    final StringBuilder hex = new StringBuilder(2 * raw.length);
    for (final byte b : raw) {
      hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
    }
    return hex.toString();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    byte[] sbuf = CRC16M.getSendBuf("010300170006");
    System.out.println(CRC16M.getBufHexStr(sbuf));
  }
}
```
3. 安装DTU设备针对Modbus的数据传输格式，定义请求描述对象ModbusRequest
```java
package com.yb.socket.service.modbus;

import java.io.Serializable;

import com.yb.socket.pojo.BaseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
class ModbusRequest extends BaseMessage implements Serializable {

  private static final long serialVersionUID = 2797211194925775356L;
  // 地址
  private String address;
  // 功能码
  private String funCode;
  // 开始地址
  private String beginAddress;
  // 寄存器数量
  private String registerNum;
  // CRC校验码
  private String crcCode;

  @Override
  public String toString() {
    return address + funCode + beginAddress + registerNum + crcCode;
  }
}
```
4. 安装DTU设备针对Modbus的数据传输格式，定义响应描述对象ModbusResponse
```java
package com.yb.socket.service.modbus;

import java.io.Serializable;

import com.yb.socket.pojo.BaseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
class ModbusResponse extends BaseMessage implements Serializable {

  private static final long serialVersionUID = 6849503848030330888L;
  // 地址
  private String address;
  // 命令
  private String funCode;
  // 数据长度
  private String dataSize;
  // 数据段
  private String dataSegment;
  // CRC校验码
  private String crcCode;

  @Override
  public String toString() {
    return address + funCode + dataSize + dataSegment + crcCode;
  }
}
```
5. 编写Modbus编码器，代码如下：
```java
package com.yb.socket.service.modbus;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yb.socket.pojo.BaseMessage;
import com.yb.socket.pojo.Heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageEncoder;

class ModbusEncoder extends MessageToMessageEncoder<BaseMessage> {

  private static final String HEARTBEAT = "heartbeat";

  @Override
  protected void encode(ChannelHandlerContext ctx, BaseMessage msg, List<Object> out) throws Exception {
    if (msg instanceof ModbusRequest) {
      ModbusRequest request = (ModbusRequest) msg;
      byte[] data = CRC16M.getSendBuf(request.toString());
      ByteBuf buf = Unpooled.copiedBuffer(data);
      out.add(buf);
    } else if (msg instanceof ModbusResponse) {
      ModbusResponse resp = (ModbusResponse) msg;
      byte[] data = CRC16M.getSendBuf(resp.toString());
      ByteBuf buf = Unpooled.copiedBuffer(data);
      out.add(buf);
    } else if (msg instanceof Heartbeat) {
      JSONObject json = new JSONObject();
      json.put("type", HEARTBEAT);
      ByteBuf buf = Unpooled.copiedBuffer(json.toString().getBytes());
      out.add(buf);
    } else {
      throw new CodecException("不知道的消息类型: " + msg);
    }
  }

}
```
6. Modbus解码器，代码如下：
```java
package com.yb.socket.service.modbus;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;

class ModbusDecoder extends MessageToMessageDecoder<ByteBuf> {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    try {
      byte[] tmp = new byte[msg.readableBytes()];
      msg.readBytes(tmp);

      String str = CRC16M.getBufHexStr(tmp);
      System.out.println(str);

    } catch (Exception ex) {
      throw new CodecException(ex);
    }
  }

}
```
7. 编写组方法，测试客户端传输数据
```java
package com.yb.socket.service.modbus;

import com.yb.socket.service.server.Server;

public class ServerTest {
  public static void main(String[] args) {
    Server server = new Server();
    server.setPort(8000);
    server.setCheckHeartbeat(false);
    server.addChannelHandler("decoder", new ModbusDecoder());
    server.addChannelHandler("encoder", new ModbusEncoder());
    server.bind();
  }
}
```
