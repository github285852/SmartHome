package com.example.android.project;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;




/*以下所有打印均为调试专用，该程序主要是接收来自STM32发来的数据，通过socket接收，在由http发送给web服务器
* 若web有响应则将数据发给STM32*/
public class MainActivity extends AppCompatActivity {
    private int flag=0; //反馈开门信息标志
    private Handler handler;
    private Button cnn,Close;
    private EditText ip,http;
    private String  ipaddr="192.168.0.2";
    private String  httpaddr="http://192.168.0.14/SmartHome/servlet/AddDataServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        cnn = (Button)findViewById(R.id.button);
        Close = (Button)findViewById(R.id.button2);
        setSupportActionBar(toolbar);
        ip = (EditText)findViewById(R.id.editText);
        http = (EditText)findViewById(R.id.editText3);

/*handlerMessage主要处理来自socket线程发送过来的消息，通过字符串匹配发送给web服务器*/
                handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String temper, card_id, shake;

                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        client.setConnectTimeout(8000);//设置连接超时时间
                        client.setResponseTimeout(80000);//设置web服务器响应时间

                        String str = (String) msg.obj;

                        System.out.println((String) msg.obj);
                        /*字符串匹配，提取温度，ID，地震信息*/
                            if ((temper = Searchstring.getString(str, "[0-9][0-9]\\.[0-9]+")) != null) {
                                params.add("temperature", temper);
                                System.out.println("*******send temperature:" + temper + "**********");
                            }
                            if ((card_id = Searchstring.getString(str, "[0-9]{8,15}")) != null) {
                                params.add("card_id", card_id);
                                System.out.println("*******send card_id:" + card_id + "**********");
                            }
                            if ((shake = Searchstring.getString(str, "shake")) != null) {
                                params.add("shake", shake);
                                System.out.println("*******send shake:" + shake + "**********");
                            }
                            if (temper != null || card_id != null || shake != null) {
                                client.post(httpaddr, params, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                        /*连接服务器成功后接收来自web服务器的响应存储在bytes中*/
                                        System.out.println(new String(bytes));
                                        String resp = new String(bytes);
                                        String open;
                                        /*匹配字符open，一旦成功，将flag置1，然后向STM32发送open指令开门*/
                                        if ((open = Searchstring.getString(resp, "open")) != null && open.equals("open")) {
                                            System.out.println("*******open********");
                                            flag = 1;
                                        }

                                    }

                                    @Override
                                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                        System.out.println(new String(bytes));
                                    }
                                });
                            }
                        }

                };


        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();                    //close按钮按下，结束activity
            }
        });


        cnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ipaddr=ip.getText().toString();
//                httpaddr=http.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Socket client = new Socket();
                        int len,i;
                        byte[] rd = new byte[4096];
                        InetSocketAddress socketAddress = new InetSocketAddress(ipaddr, 8086);
                        try {
                                client.connect(socketAddress, 8000);//设置连接超时8秒
                                if (client.isConnected()) {        //判断是否连接成功
                                    System.out.println("Connect!");
                                }
                                else
                                    System.out.println("failed Connect!");
                                OutputStream out = client.getOutputStream();
                                InputStream in = client.getInputStream();
                                //默认情况下发送数据是采用Negale算法。Negale算法是指发送方数据不会立刻发送出去，而是先放在缓冲区内，等待缓冲区满了，在发出去
                                client.setTcpNoDelay(true);
                                /*当设置为true的时候，TCP会实现监控连接是否有效，当连接处于空闲状态的时候，
                                 超过了2个小时，本地的TCP实现会发送一个数据包给远程的socket，
                                 如果远程没有发回响应，TCP会持续尝试11分钟，
                                 知道响应为止，如果在12分钟的时候还没响应，TCP尝试关闭socket连接。*/
                                client.setKeepAlive(true);
                                //设置输入数据的缓冲区的大小
                                client.setReceiveBufferSize(4096);
                                //如果对方连接状态30秒没有收到数据的话强制断开客户端
                                client.setSoTimeout(30000);
                                byte[] wr = "open".getBytes();
                                while ((len = in.read(rd, 0, rd.length)) != -1) {
                                    Message msg = handler.obtainMessage();
                                    if (flag == 1) {          //如果flag=1，发送“open”字符串给socket服务端，通知开门
                                        flag = 0;
                                        for (i = 0; i < 3; i++) {
                                            out.write(wr, 0, wr.length);//发送open字符串
                                            out.flush();
                                        }
                                    }
                                    String str = new String(rd, 0, len, "utf-8");
                                    if (str != null) {
                                        msg.obj = str;
                                        handler.sendMessage(msg);//发送消息给handleMessage处理
                                    }
                                }
                            in.close();
                            out.close();
                            client.close();
                            System.out.println("the client closed!");
                        }catch (IOException e) {
                            System.out.println("socket client failed!");
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
