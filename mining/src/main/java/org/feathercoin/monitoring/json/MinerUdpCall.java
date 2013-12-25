package org.feathercoin.monitoring.json;/*
 *
 * Copyright (C) Andrew Smith 2012-2013
 *
 * Usage: java API command ip port
 *
 * If any are missing or blank they use the defaults:
 *
 *	command = 'summary'
 *	ip	= '127.0.0.1'
 *	port	= '4028'
 *
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MinerUdpCall implements Serializable
{
    static private final int MAXRECEIVESIZE = 65535;
    private InetAddress ip;
    private int port;

    public InetAddress getIp() {
        return ip;
    }

    private void closeAll(Socket socket)
    {
        if (socket != null)
        {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String process(String cmd)
    {
        StringBuilder sb = new StringBuilder();
        char buf[] = new char[MAXRECEIVESIZE];
        int len = 0;
        Socket socket = null;

        try
        {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 4000);
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.print(cmd.toLowerCase().toCharArray());
            ps.flush();

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            while (true)
            {
                len = isr.read(buf, 0, MAXRECEIVESIZE);
                if (len < 1)
                    break;
                sb.append(buf, 0, len);
                if (buf[len-1] == '\0')
                    break;
            }

        }
        catch (IOException ioe)
        {
            throw new RuntimeException(ioe);
        } finally {
            closeAll(socket);
        }

        String result = sb.toString();
        return result.substring(0,result.lastIndexOf("}")+1);
    }

    public MinerUdpCall(){

    }

    public MinerUdpCall(String _ip, int _port)
    {

        port = _port;

        try
        {
            ip = InetAddress.getByName(_ip);
        }
        catch (UnknownHostException uhe)
        {
            throw new RuntimeException(uhe);
        }
    }
}