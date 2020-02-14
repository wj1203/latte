package com.leap.latte;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws IOException {
//        assertEquals(4, 2 + 2);

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("www.baidu.com",8080));
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        System.out.println(socket.isConnected());


    }
}