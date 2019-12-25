package com.example.ReversingTrackLine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Utils {
    /**
     * @param vertexs int数组
     * @return 获取整形缓冲数据
     */
    public static IntBuffer getIntBuffer(int[] vertexs) {
        IntBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asIntBuffer();
        buffer.put(vertexs);
        buffer.position(0);
        return buffer;
    }
    /**
     * @param vertexs float 数组
     * @return 获取浮点形缓冲数据
     */
    public static FloatBuffer getFloatBuffer(float[] vertexs) {
        FloatBuffer buffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        buffer = qbb.asFloatBuffer();
        buffer.put(vertexs);
        buffer.position(0);
        return buffer;
    }

    /**
     * @param vertexs Byte 数组
     * @return 获取字节型缓冲数据
     */
    public static ByteBuffer getByteBuffer(byte[] vertexs) {
        ByteBuffer buffer = null;
        buffer = ByteBuffer.allocateDirect(vertexs.length);
        buffer.put(vertexs);
        buffer.position(0);
        return buffer;
    }
//
}
