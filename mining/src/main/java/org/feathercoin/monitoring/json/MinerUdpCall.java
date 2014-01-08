package org.feathercoin.monitoring.json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * MinerUdpCall handles the communication to a miner via the UDP protocol (assuming you're using cgminer).
 * CGMiner have to be configured to allow UDP calls. E.g. via command line parameters --api-listen --api-network (for
 * more details please check the <a href="https://github.com/ckolivas/cgminer">cgminer online documentation)</a>.
 *
 * All possible parameters are described in the cgminer documentation too.
 */
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

    /**
     * Executes a UDP call with the given command
     * @param cmd A command to be passed via UDP to your cgminer (see cgminer documentation for details on possible
     *            commands)
     * @return String - result of the UDP call
     * @throws java.lang.RuntimeException for any failing UDP call
     */
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

    /**
     * Default constructor - necessary for Serialization/Deserialization
     */
    public MinerUdpCall(){

    }

    /**
     *
     * @param _ip Ip to connect to your cgminer
     * @param _port Port to connect to your cgminer (default is port 4028)
     */
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