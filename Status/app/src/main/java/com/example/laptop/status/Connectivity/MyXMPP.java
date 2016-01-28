package com.example.laptop.status.Connectivity;

import android.content.Context;
import android.os.AsyncTask;

import com.example.laptop.status.ToastMessage;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.dns.HostAddress;

import java.io.IOException;

/**
 * Created by Laptop on 27-Jan-16.
 */
public class MyXMPP {

    String server, host,port;
    private XMPPConnection xmppConnection;
    Context context;
    String success="";

    public MyXMPP(Context context,String servername,String hostname, String portname)
    {
        this.context = context;
        server = servername;
        host = hostname;
        port = portname;
        asynctask asynctask = new asynctask();
        asynctask.execute();
    }

    public class asynctask extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] params) {

            XMPPTCPConnectionConfiguration.Builder configBuilder  = XMPPTCPConnectionConfiguration.builder();
            configBuilder.setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled);
            // configBuilder.setResource("testServices");
            configBuilder.setServiceName(server);
            configBuilder.setHost(host);
            configBuilder.setPort(Integer.parseInt(port));
            configBuilder.setCompressionEnabled(false);

            AbstractXMPPConnection xmppConnection = new XMPPTCPConnection(configBuilder.build());

            try {
                xmppConnection.connect();
                success = "successfully connect";
            } catch (SmackException e) {
                success = e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                success = e.toString();

            } catch (XMPPException e) {
                e.printStackTrace();
                success = e.toString();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ToastMessage.showToastMessage(context, success);


        }
    }

}
