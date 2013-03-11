// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 9/8/2009 8:26:16 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CanvasMouse.java

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class CanvasMouse
{

    CanvasMouse(ServerInfo serverInfo, ClientInfo clientInfo)
        throws IOException
    {
        pressing = false;
        this.serverInfo = serverInfo;
        this.clientInfo = clientInfo;
        UpadateCursorPosition();
        cursorImage = Image.createImage("/cursor.png");
        RcursorImage = Image.createImage("/Rcursor.png");
    }

    public void UpadateCursorPosition()
    {
        if(clientInfo.getZoom())
        {
            if(clientInfo.getRotation())
            {
                cursorX = clientInfo.getHeight() / 2;
                cursorY = clientInfo.getWidth() / 2;
            } else
            {
                cursorX = clientInfo.getWidth() / 2;
                cursorY = clientInfo.getHeight() / 2;
            }
        } else
        if(!clientInfo.getRotation())
        {
            cursorX = (clientInfo.getWidth() * serverInfo.getServerMosueX()) / serverInfo.getServerWidth();
            cursorY = (clientInfo.getHeight() * serverInfo.getServerMosueY()) / serverInfo.getServerHeight();
        } else
        {
            cursorX = (clientInfo.getHeight() * serverInfo.getServerMosueX()) / serverInfo.getServerWidth();
            cursorY = (clientInfo.getWidth() * serverInfo.getServerMosueY()) / serverInfo.getServerHeight();
            cursorY = clientInfo.getWidth() - cursorY;
        }
        clientInfo.setClientMouseX(cursorX);
        clientInfo.setClientMouseY(cursorY);
    }

    public void MousePressed()
    {
        pressing = !pressing;
    }

    public void createCursorImage(Graphics g)
    {
        if(clientInfo.getRotation())
            g.drawImage(RcursorImage, cursorY, cursorX, 24);
        else
            g.drawImage(cursorImage, cursorX, cursorY, 20);
    }

    private boolean pressing;
    private int cursorX;
    private int cursorY;
    private ServerInfo serverInfo;
    private ClientInfo clientInfo;
    private Image cursorImage;
    private Image RcursorImage;
}
