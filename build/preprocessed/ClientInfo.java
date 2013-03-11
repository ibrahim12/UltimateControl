// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 9/8/2009 8:26:38 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ClientInfo.java


public class ClientInfo
{

    ClientInfo()
    {
        Rotation = false;
        Zoom = false;
        ScreenON = true;
        FileManagerON = false;
        looping = true;
    }

    public boolean getZoom()
    {
        return Zoom;
    }

    public void setZoom(boolean z)
    {
        Zoom = z;
    }

    public void setRotation(boolean r)
    {
        Rotation = r;
    }

    public boolean getRotation()
    {
        return Rotation;
    }

    public int getHeight()
    {
        return Height;
    }

    public int getWidth()
    {
        return Width;
    }

    public void setHeight(int h)
    {
        Height = h;
    }

    public void setWidth(int w)
    {
        Width = w;
    }

    public void setPlatform(String s)
    {
        Platform = s;
    }

    public String getPlatform()
    {
        return Platform;
    }

    public void setClientMouseX(int x)
    {
        clientMouseX = x;
    }

    public int getClientMouseX()
    {
        return clientMouseX;
    }

    public void setClientMouseY(int y)
    {
        clientMouseY = y;
    }

    public int getClientMouseY()
    {
        return clientMouseY;
    }

    private int Height;
    private int Width;
    private int clientMouseX;
    private int clientMouseY;
    private String Platform;
    private boolean Rotation;
    private boolean Zoom;
    public boolean ScreenON;
    public boolean FileManagerON;
    public boolean looping;
}
