// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 9/8/2009 8:28:47 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ServerInfo.java


public class ServerInfo
{

    ServerInfo()
    {
        isAlive = false;
        ServerON = true;
    }

    public void setServerON(boolean f)
    {
        ServerON = f;
    }

    public boolean getServerOn()
    {
        return ServerON;
    }

    public void setServerStatus(boolean f)
    {
        isAlive = f;
    }

    public boolean getServerStatus()
    {
        return isAlive;
    }

    public void setServerBluetoothAddress(String s)
    {
        ServerBluetoothAddress = s;
    }

    public String getServerBluetoothAddress()
    {
        return ServerBluetoothAddress;
    }

    public void setServerName(String s)
    {
        ServerName = s;
    }

    public String getServerName()
    {
        return ServerName;
    }

    public void setServerWidth(int w)
    {
        ServerWidth = w;
    }

    public void setServerHeight(int h)
    {
        ServerHeight = h;
    }

    public int getServerWidth()
    {
        return ServerWidth;
    }

    public int getServerHeight()
    {
        return ServerHeight;
    }

    public void setServerMosuseX(int x)
    {
        ServerMouseX = x;
    }

    public void setServerMouseY(int y)
    {
        ServerMouseY = y;
    }

    public int getServerMosueX()
    {
        return ServerMouseX;
    }

    public int getServerMosueY()
    {
        return ServerMouseY;
    }

    private int ServerWidth;
    private int ServerHeight;
    private int ServerMouseX;
    private int ServerMouseY;
    private String ServerBluetoothAddress;
    private String ServerName;
    private boolean isAlive;
    private boolean ServerON;
}
