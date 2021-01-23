import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Client
{
    private MessageHandler messageHandler;

    private class MessageHandler extends Thread
    {
        private SocketWrapper socketWrapper;
        private boolean active;
       
        private class SocketWrapper
        {
            private Socket socket;
            private BufferedReader fromServer;
            private PrintWriter toServer;

            public SocketWrapper(String pServerIP, int pServerPort)
            {
                try
                {
                    socket = new Socket(pServerIP, pServerPort);
                    toServer = new PrintWriter(socket.getOutputStream(), true);
                    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }
                catch (IOException e)
                {
                    socket = null;
                    toServer = null;
                    fromServer = null;
                }
            }

            public String receive()
            {
                if(fromServer != null)
                    try
                    {
                        return fromServer.readLine();
                    }
                    catch (IOException e)
                    {
                    }
                return(null);
            }

            public void send(String pMessage)
            {
                if(toServer != null)
                {
                    toServer.println(pMessage);
                }
            }

            public void close()
            {
                if(socket != null)
                    try
                    {
                        socket.close();
                    }
                    catch (IOException e)
                    {
                    }
            }
        }
        
        private MessageHandler(String pServerIP, int pServerPort)
        {
            socketWrapper = new SocketWrapper(pServerIP, pServerPort);
            start();
            if(socketWrapper.socket != null)
            	active = true;
        }

        public void run()
        {
            String message = null;
            while (active)
            {
                message = socketWrapper.receive();
                if (message != null)
                    processMessage(message);
                else
                    close();
            }
        }

        private void send(String pMessage)
        {
            if(active)
                socketWrapper.send(pMessage);
        }

        private void close()
        {
            if(active)
            {
                active = false;
                socketWrapper.close();
            }
        }
    }

    public Client(String pServerIP, int pServerPort)
    {
        messageHandler = new MessageHandler(pServerIP, pServerPort);
    }

    public boolean isConnected()
    {
   		return(messageHandler.active);
    }
    
    public void send(String pMessage)
    {
        messageHandler.send(pMessage);
    }
    
    public void close()
    {
        messageHandler.close();
    }

    public abstract void processMessage(String pMessage);

}
