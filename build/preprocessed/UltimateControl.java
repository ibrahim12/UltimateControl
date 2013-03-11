// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 9/8/2009 8:29:12 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   UltimateControl.java

import java.io.*;
import java.util.Vector;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class UltimateControl extends MIDlet implements CommandListener, Runnable
{
    ConnectServer C_S;
    public StreamConnection con;
    public Display display;
    public Form BG;
    public Form Settings;
    public Form frmDevices;
    public Command Exit;
    public Command Start;
    public Command Connect;
    public Command Send;
    public Command ServerList;
    public Command Canvas;
    public Command Cancel;
    public Command configCmd;
    public Command Back;
    public Command DeviceList;
    public Command ServerOn;
    public Command ServerOFF;
    public ReadThread rth;
    public String sms;
    public boolean toSend;
    public DataOutputStream out;
    public Thread t;
    public int mode;
    public TextField chatBox;
    public Image img;
    public static final UUID uuid = new UUID("102030405060708090A0B0C0D0E0F010", false);
    public ServerInfo serverInfo;
    public ClientInfo clientInfo;
    public ScreenCanvas canvas;
    public DataInputStream in;
    private SocketConnection sc;
    public boolean connection;

    public UltimateControl()
    {
        toSend = false;
        mode = 0x9e8b33;
        connection = false;
        BG = new Form("UltimateControl");
        BG.append("Click Connect to Start");
        Settings = new Form("Settings");
        frmDevices = new Form("Devices List");
        Back = new Command("Back", 2, 1);
        Exit = new Command("Exit", 7, 0);
        Start = new Command("Start", 1, 1);
        Connect = new Command("Connect Server", 1, 2);
        DeviceList = new Command("DeviceCahce", 1, 1);
        Cancel = new Command("Cancel", 1, 0);
        Canvas = new Command("Screen", 1, 1);
        configCmd = new Command("Settings", 1, 0);
        ServerOn = new Command("ServerON", 1, 0);
        ServerOFF = new Command("ServerOFF", 1, 0);
        Settings.addCommand(Back);
        Settings.setCommandListener(this);
        frmDevices.setCommandListener(this);
        frmDevices.addCommand(Back);
        BG.addCommand(Exit);
        BG.addCommand(Connect);
        BG.setCommandListener(this);
    }

    protected void startApp()
    {
        display = Display.getDisplay(this);
        display.setCurrent(BG);
    }

    protected void pauseApp()
    {
    }

    protected void destroyApp(boolean unconditional)
    {
        notifyDestroyed();
    }

    public void commandAction(Command c, Displayable d)
    {
        if(c == Connect)
        {
            t = new Thread(this);
            t.setPriority(10);
            t.start();
            BG.removeCommand(Connect);
        } else
        if(c == Exit)
            destroyApp(false);
        else
        if(c == Cancel)
            display.setCurrent(BG);
        else
        if(c == Canvas)
            display.setCurrent(canvas);
        else
        if(c == configCmd)
        {
            Settings.deleteAll();
            Settings.append("Client Informations\n\n");
            Settings.append("Client Height :" + clientInfo.getHeight() + '\n');
            Settings.append("Client Width:" + clientInfo.getWidth() + '\n');
            Settings.append("Client Platform:" + clientInfo.getPlatform() + '\n');
            Settings.append("Clinet MouseX :" + clientInfo.getClientMouseX() + '\n');
            Settings.append("Client MouseY:" + clientInfo.getClientMouseY() + '\n');
            Settings.append("Server Informations\n\n");
            Settings.append("Server Height :" + serverInfo.getServerHeight() + '\n');
            Settings.append("Server Width:" + serverInfo.getServerWidth() + '\n');
            Settings.append("Server MouseX :" + serverInfo.getServerMosueX() + '\n');
            Settings.append("Server MouseY:" + serverInfo.getServerMosueY() + '\n');
            display.setCurrent(Settings);
        } else
        if(c == Back)
            display.setCurrent(BG);
        else
        if(c != DeviceList)
            if(c == ServerOn)
            {
                write("ServerON");
                serverInfo.setServerON(true);
                canvas.repaint();
                BG.addCommand(ServerOFF);
            } else
            if(c == ServerOFF)
            {
                write("ServerOFF");
                serverInfo.setServerON(false);
                BG.addCommand(ServerOn);
            }
    }

    public void write(String s)
    {
        try
        {
            out.writeUTF(s);
            out.flush();
        }
        catch(IOException ex)
        {
            BG.append(ex.toString());
        }
    }

    public void BlueConnection()
    {
        BG.deleteAll();
        UUID uuidset[] = {
            uuid
        };
        C_S = new ConnectServer(mode, uuidset, BG);
        try
        {
            C_S.cs.join();
        }
        catch(Exception ex)
        {
            BG.append(ex.toString());
        }
        ConnectServer _tmp = C_S;
        if(ConnectServer.services.size() != 0)
        {
            serverInfo = new ServerInfo();
            clientInfo = new ClientInfo();
            serverInfo.setServerBluetoothAddress(C_S.BTAddress);
            BG.append("Server BT ADD :" + C_S.BTAddress + '\n');
            BG.append("Clearing Canvas...");
            try
            {
                Thread.sleep(1000L);
                BG.deleteAll();
                ServiceRecord r = C_S.getFirstDiscoveredService();
                String url = r.getConnectionURL(0, false);
                BG.append(url + '\n');
                con = (StreamConnection)Connector.open(url, 3, true);
                out = con.openDataOutputStream();
                in = con.openDataInputStream();
                BG.append("Server is Connected\n");
                BG.addCommand(configCmd);
                ReadServerInfo();
                canvas = new ScreenCanvas(this);
                BG.addCommand(Canvas);
                BG.append("Press Left Soft Key to Load canvas\n");
                //(new ReadThread(in, BG, canvas, serverInfo, clientInfo)).thr.join();
                (new ReadThread(this)).thr.join();
                BG.removeCommand(Connect);
                con.close();
                BG.append("Server is Disconnected\n");
            }
            catch(Exception ex)
            {
                BG.append(ex.toString());
                BG.append("In Main\n");
                try
                {
                    con.close();
                    BG.append("Server is Disconnected\n");
                    Thread.sleep(5000L);
                }
                catch(Exception ex1)
                {
                    BG.append(ex1.toString());
                }
                BG.append("Server is Disconnected\n");
            }
        } else
        {
            BG.append("No Server Found\n");
            BG.deleteAll();
            BG.append("Try to Connect Again...");
            BG.addCommand(Connect);
        }
    }

    public void WefiConnection()
    {
        try
        {
            sc = (SocketConnection)Connector.open("socket://169.254.1.5:34000");
        }
        catch(Exception e)
        {
            BG.append(e.toString());
        }
        if(sc != null)
        {
            try
            {
                out = sc.openDataOutputStream();
                in = sc.openDataInputStream();
                serverInfo = new ServerInfo();
                clientInfo = new ClientInfo();
                BG.append("Server is Connected\n");
                BG.addCommand(configCmd);
                ReadServerInfo();
                canvas = new ScreenCanvas(this);
                BG.addCommand(Canvas);
                BG.append("Press Left Soft Key to Load canvas\n");
                //(new ReadThread(in, BG, canvas, serverInfo, clientInfo)).thr.join();
                (new ReadThread(this)).thr.join();
                BG.removeCommand(Connect);
                con.close();
                BG.append("Server is Disconnected\n");
            }
            catch(Exception ex)
            {
                BG.append(ex.toString());
                BG.append("In Main\n");
                try
                {
                    con.close();
                    BG.append("Server is Disconnected\n");
                    Thread.sleep(5000L);
                }
                catch(Exception ex1)
                {
                    BG.append(ex1.toString());
                }
                BG.append("Server is Disconnected\n");
            }
        } else
        {
            BG.append("No Server Found\n");
            BG.deleteAll();
            BG.append("Try to Connect Again...");
            BG.addCommand(Connect);
        }
    }

    public void run()
    {
        if(connection)
            BlueConnection();
        else
            WefiConnection();
    }

    public void ReadServerInfo()
        throws IOException
    {
        serverInfo.setServerHeight(in.readInt());
        serverInfo.setServerWidth(in.readInt());
        serverInfo.setServerMosuseX(in.readInt());
        serverInfo.setServerMouseY(in.readInt());
    }

    
}
