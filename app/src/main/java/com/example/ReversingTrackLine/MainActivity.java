package com.example.ReversingTrackLine;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tracelinedemo.R;

import java.nio.FloatBuffer;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends Activity {

    private static final String TAG = "dingww";

    private GLSurfaceView glSurfaceView;

    LinearLayout l1;  // 新加的容器
    TextView show1;   // 显示顺时针转动的角度
    TextView show2;   // 显示逆时针转动的角度
    SeekBar seekBar1; // 顺时针转动的滑动条
    SeekBar seekBar2; // 逆时针转动的滑动条
    private float mAngle = 0f;
    private float mD = 0.5f;  // 车尾到车后轮的距离
    private float mL = 2.8f;  // 车前后轮的轴距
    private float mW = 1.2f;  // 车的轴长
    private float mH = 1.5f;  // camera的高度
    // 注意：实际x和y的取值有范围:
    // -mL * cot  < x < -mL * cot
    //  camera能看到的地面的Y的最小值 < y < mL * cot + mW / 2 - mD
    float mArrayX[] = new float[]{10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f,
            10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f,
            10f, 10f, 10f, 10f, 10f}; // 地面关键点位置的X坐标

    float mArrayYR[] = new float[]{0.6f, 0.55f, 0.5f, 0.45f, 0.4f, 0.35f,
            0.3f, 0.25f, 0.2f, 0.15f, 0.1f, 0.05f, 0.05f, 0.1f, 0.15f,
            0.2f, 0.25f, 0.3f, 0.35f, 0.4f, 0.45f, 0.5f, 0.55f, 0.6f}; // 保存屏幕上Y轴的坐标Yr

    // 地面关键点位置的Y坐标..要考虑到camera能看到的地面的Y的最小值（边界问题）
    float mArrayY[] = new float[]{4f, 3.75f, 3.5f, 3.25f, 3f, 2.75f, 2.5f,
            2.25f, 2f, 1.75f, 1.5f, 1.25f, 1.25f, 1.5f, 1.75f, 2f, 2.25f,
            2.5f, 2.75f, 3f, 3.25f, 3.5f, 3.75f, 4f};

    //顶点数组
    private float[] mArray = {

            mArrayX[0], mArrayYR[0] * 2 - 1, 0f,
            mArrayX[1], mArrayYR[1] * 2 - 1, 0f,

            mArrayX[1], mArrayYR[1] * 2 - 1, 0f,
            mArrayX[2], mArrayYR[2] * 2 - 1, 0f,

            mArrayX[2], mArrayYR[2] * 2 - 1, 0f,
            mArrayX[3], mArrayYR[3] * 2 - 1, 0f,

            mArrayX[3], mArrayYR[3] * 2 - 1, 0f,
            mArrayX[4], mArrayYR[4] * 2 - 1, 0f,

            mArrayX[4], mArrayYR[4] * 2 - 1, 0f,
            mArrayX[5], mArrayYR[5] * 2 - 1, 0f,

            mArrayX[5], mArrayYR[5] * 2 - 1, 0f,
            mArrayX[6], mArrayYR[6] * 2 - 1, 0f,

            mArrayX[6], mArrayYR[6] * 2 - 1, 0f,
            mArrayX[7], mArrayYR[7] * 2 - 1, 0f,

            mArrayX[7], mArrayYR[7] * 2 - 1, 0f,
            mArrayX[8], mArrayYR[8] * 2 - 1, 0f,

            mArrayX[8], mArrayYR[8] * 2 - 1, 0f,
            mArrayX[9], mArrayYR[9] * 2 - 1, 0f,

            mArrayX[9], mArrayYR[9] * 2 - 1, 0f,
            mArrayX[10], mArrayYR[10] * 2 - 1, 0f,

            mArrayX[10], mArrayYR[10] * 2 - 1, 0f,
            mArrayX[11], mArrayYR[11] * 2 - 1, 0f,


            mArrayX[12], mArrayYR[12] * 2 - 1, 0f,
            mArrayX[13], mArrayYR[13] * 2 - 1, 0f,

            mArrayX[13], mArrayYR[13] * 2 - 1, 0f,
            mArrayX[14], mArrayYR[14] * 2 - 1, 0f,

            mArrayX[14], mArrayYR[14] * 2 - 1, 0f,
            mArrayX[15], mArrayYR[15] * 2 - 1, 0f,

            mArrayX[15], mArrayYR[15] * 2 - 1, 0f,
            mArrayX[16], mArrayYR[16] * 2 - 1, 0f,

            mArrayX[16], mArrayYR[16] * 2 - 1, 0f,
            mArrayX[17], mArrayYR[17] * 2 - 1, 0f,

            mArrayX[17], mArrayYR[17] * 2 - 1, 0f,
            mArrayX[18], mArrayYR[18] * 2 - 1, 0f,

            mArrayX[18], mArrayYR[18] * 2 - 1, 0f,
            mArrayX[19], mArrayYR[19] * 2 - 1, 0f,

            mArrayX[19], mArrayYR[19] * 2 - 1, 0f,
            mArrayX[20], mArrayYR[20] * 2 - 1, 0f,

            mArrayX[20], mArrayYR[20] * 2 - 1, 0f,
            mArrayX[21], mArrayYR[21] * 2 - 1, 0f,

            mArrayX[21], mArrayYR[21] * 2 - 1, 0f,
            mArrayX[22], mArrayYR[22] * 2 - 1, 0f,

            mArrayX[22], mArrayYR[22] * 2 - 1, 0f,
            mArrayX[23], mArrayYR[23] * 2 - 1, 0f,

            // 静态区域 (手动配)
            -0.1f, 0.2f, 0f,
            -0.2f, -0.9f, 0f,  // 左边

            -0.2f, -0.9f, 0f,
            0.2f, -0.9f, 0f,  // 底边

            0.2f, -0.9f, 0f,
            0.1f, 0.2f, 0f,  // 右边

            0.1f, 0.2f, 0f,
            -0.1f, 0.2f, 0f,  // 上边
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 进行一些初始化操作
        initRenderer();
        // 添加新的容器，含有文本框，滑动条
        addNewLayout();
    }


    public void addNewLayout() {
        // 添加新的容器，含有文本框，滑动条
        l1 = new LinearLayout(this);
        l1.setX(100);
        l1.setY(100);
        l1.setOrientation(LinearLayout.VERTICAL);

        seekBar1 = new SeekBar(this);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAngle = progress / 100f * 30f;
                int arrayIndex = 0;
                for (int index = 0; index < mArrayX.length; index++) {
                    if (index < mArrayX.length / 2) {
                        Log.d(TAG, "onProgressChanged: index = " + index);
                        mArrayX[index] = ComputerXin(mAngle, mArrayY[index]);
                        Log.d(TAG, "onProgressChanged:顺时针 mArrayX[" + index + "] =" + mArrayX[index]);
                        // 更新mArray顶点数组
                        if ((index == 0) || (index == mArrayX.length / 2 - 1)) {
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                            Log.d(TAG, "onProgressChanged:  mArray[" + arrayIndex + "] = " + mArray[arrayIndex]);
                        } else {
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                        }
                    } else {
                        mArrayX[index] = ComputerXOut(mAngle, mArrayY[index]);
                        Log.d(TAG, "onProgressChanged:顺时针 mArrayX" + index + "=" + mArrayX[index]);
                        // 更新mArray顶点数组
                        if ((index == mArrayX.length - 1) || (index == mArrayX.length / 2)) {
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                        } else {
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                        }
                    }
                }
                show1.setText("顺时针当前角度：" + mAngle);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBar2 = new SeekBar(this);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAngle = progress / 100f * 30f;
                int arrayIndex = 0;
                for (int index = 0; index < mArrayX.length; index++) {
                    if (index < mArrayX.length / 2) {
                        mArrayX[index] = -ComputerXOut(mAngle, mArrayY[index]);
                        // 更新mArray顶点数组
                        if ((index == 0) || (index == mArrayX.length / 2 - 1)) {
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                        } else {
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                        }
                        Log.d(TAG, "onProgressChanged:逆时针 mArrayX" + index + "=" + mArrayX[index]);
                    } else {
                        mArrayX[index] = -ComputerXin(mAngle, mArrayY[index]);
                        Log.d(TAG, "onProgressChanged:逆时针 mArrayX" + index + "=" + mArrayX[index]);
                        // 更新mArray顶点数组
                        if ((index == mArrayX.length - 1) || (index == mArrayX.length / 2)) {
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                        } else {
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                            mArray[arrayIndex] = mArrayX[index];
                            arrayIndex = arrayIndex + 3;
                        }
                    }
                }
                show2.setText("逆时针当前角度：" + mAngle);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        show1 = new TextView(this);
        show1.setText("顺时针角度");
        show2 = new TextView(this);
        show2.setText("逆时针角度");

        l1.addView(seekBar1);
        l1.addView(seekBar2);
        l1.addView(show1);
        l1.addView(show2);
        addContentView(l1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    public void initRenderer() {
        // 如果本设备支持OpenGl ES 2.0
        if (IsSupported()) {
            // 先建GLSurfaceView实例
            glSurfaceView = (GLSurfaceView) findViewById(R.id.glSurfaceView);
            // 如果findViewById返回的是空，那就重新创建一个GLSurfaceView实例吧
            if (glSurfaceView == null) {
                glSurfaceView = new GLSurfaceView(this);
                Log.d(TAG, "onCreate: findViewById failed");
            }
            // 创建渲染器实例
            MyRenderer1 mRenderer = new MyRenderer1();
            // 设置渲染器
            glSurfaceView.setRenderer(mRenderer);
            // 显示SurfaceView
            setContentView(glSurfaceView);
        }
    }

    // 是否支持OpenGl ES 2.0
    private boolean IsSupported() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;
        return supportsEs2;
    }

    public float ComputerXOut(float angle, float y) {
        // L, D, W 已知，
        float firstStep = Square(mL * Cot(angle) + mW / 2) - Square(y + mD);
        float secondStep = (float) Math.pow(firstStep, 0.5);
        float x = secondStep - mL * Cot(angle);
        // 新加入第一视角，需要参数:屏幕宽W，camera的张角2a
        float a = 30f;  // 当a越大，两条线越靠近，变化趋势相对平缓；a越小，两条线越远离，变化趋势相对剧烈
        float W = 0.8f;  // 屏幕的宽度
        return (float) ((x * W) / ((Math.pow((Square(y) + Square(mH)), 0.5) * Tan(a) * 2)));
//        return ( x * W)/(y * Tan(a) * 2);
//        return  secondStep - mL * Cot(angle);
//        return x * W;

    }

    public float ComputerXin(float angle, float y) {
        // L, D, W 已知，
        float firstStep = Square(mL * Cot(angle) - mW / 2) - Square(y + mD);
        float secondStep = (float) Math.pow(firstStep, 0.5);
        float x = secondStep - mL * Cot(angle);
        // 新加入第一视角，需要参数屏幕宽W，camera的张角2a大小
        float a = 30f; // 当a越大，两条线越靠近，变化趋势相对平缓；a越小，两条线越远离，变化趋势相对剧烈
        float W = 0.8f; // 屏幕的宽度
        return (float) ((x * W) / ((Math.pow((Square(y) + Square(mH)), 0.5) * Tan(a) * 2)));
//        return ( x * W)/(y * Tan(a) * 2);
//        return  secondStep - mL * Cot(angle);
//        return x * W;
    }

    // 求反切值 默认参数为弧度，需要角度转弧度。
    public float Cot(float angle) {
        float cot = 1 / (float) Math.tan(angle * Math.PI / 180);
        return cot;
    }

    // 求平方
    public float Square(float number) {
        return (float) Math.pow(number, 2);
    }

    // 求Tan 默认的参数为弧度，需要角度转弧度
    public float Tan(float degreef) {
        return (float) Math.tan(Math.toRadians(degreef));
    }

    public void UpdateArray() {
    }

    public class MyRenderer1 implements GLSurfaceView.Renderer {

        // 顶点数组缓冲区
        private FloatBuffer mBuffer;
        // 顶点颜色数组缓冲区
        private FloatBuffer mColorBuffer;


        public MyRenderer1() {
//            //获取浮点型缓冲数据（顶点）
//            mBuffer = Utils.getFloatBuffer(mArray);
//         获取负浮点型缓冲数据（顶点颜色）
//                  mColorBuffer = Utils.getFloatBuffer(mColorArray);
        }

        // Surface创建的时候调用
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.d(TAG, "onSurfaceCreated:  创建");
            // 设置清屏颜色为白色
            gl.glClearColor(1f, 1f, 1f, 0f);

            gl.glShadeModel(GL10.GL_LINE_SMOOTH);
        }

        // Surface改变的的时候调用
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d(TAG, "onSurfaceChanged: 窗口改变");
            // 设置窗口大小
            gl.glViewport(0, 0, width, height);
        }

        // 在Surface上绘制的时候调用
        @Override
        public void onDrawFrame(GL10 gl) {

            //获取浮点型缓冲数据（顶点）
            mBuffer = Utils.getFloatBuffer(mArray);

            Log.d(TAG, "onDrawFrame: " + Arrays.toString(mArray));
            Log.d(TAG, "onDrawFrame: 画");
            // 清除屏幕
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            // 允许设置顶点 // GL10.GL_VERTEX_ARRAY顶点数组
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            // 设置顶点
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBuffer);
            //设置点的颜色为红色
            gl.glColor4f(1f, 0f, 0f, 1f);

            //设置点大小
            gl.glPointSize(10);
            //设置线的宽度
            gl.glLineWidth(10);
            // 绘制封闭的图线,[绘制类型(封闭的图线) ，，顶点个数]
            gl.glDrawArrays(GL10.GL_LINES, 0, 52);
            // 取消顶点设置
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }
}
