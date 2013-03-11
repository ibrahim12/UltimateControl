// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 9/8/2009 8:28:24 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ScreenCanvas.java

import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.lcdui.*;

class ScreenCanvas extends Canvas implements CommandListener
{
    public String keyValue;
    DataOutputStream out;
    Image im;
    Form f;
    Form FileManager;
    Display display;
    CanvasMouse canvasMouse;
    Command Form;
    Command FManagerON;
    Command Screen;
    Command RotateON;
    Command RotateOFF;
    Command getKeyboard;
    Command getMouse;
    Command ZoomON;
    Command ZoomOFF;
    Command Exit;
    Command ServerOn;
    Command ServerOFF;
    Command HeadPhoneOn;
    Command HeadPhoneOff;
    UltimateControl UC;
    ClientInfo clientInfo;
    ServerInfo serverInfo;
    private int i;
    ScreenCanvas(UltimateControl UC)
    {
        i = 0;
        try
        {
            this.UC = UC;
            out = new DataOutputStream(UC.out);
            f = UC.BG;
            display = UC.display;
            serverInfo = UC.serverInfo;
            clientInfo = UC.clientInfo;
            ServerOn = UC.ServerOn;
            ServerOFF = UC.ServerOFF;
            setFullScreenMode(true);
            Form = new Command("Log", 1, 3);
            FileManager = new Form("FileManager");
            FManagerON = new Command("Open FileManager", 1, 2);
            HeadPhoneOn =  new Command("HeadPhone On", 1, 2);
            HeadPhoneOff =  new Command("HeadPhone Off", 1, 3);
            Screen = new Command("Go to Desktop", 1, 2);
            RotateON = new Command("RotateOn", 1, 1);
            RotateOFF = new Command("RotateOFF", 1, 1);
            ZoomON = new Command("ZoomON", 1, 0);
            ZoomOFF = new Command("ZoomOFF", 1, 0);
            getKeyboard = new Command("getKeyboard", 1, 2);
            getMouse = new Command("getMouse", 1, 2);
            Exit = new Command("Exit", 7, 4);
            clientInfo.setHeight(getHeight());
            clientInfo.setWidth(getWidth());
           // f.addCommand(ServerOFF);
            addCommand(ServerOFF);
            addCommand(FManagerON);
            FileManager.addCommand(Exit);
            FileManager.addCommand(Screen);
            FileManager.addCommand(new Command("Back", 2, 1));
            FileManager.setCommandListener(this);
            canvasMouse = new CanvasMouse(serverInfo, clientInfo);
            addCommand(Form);
            addCommand(RotateON);
            addCommand(ZoomON);
            addCommand(getKeyboard);
            addCommand(Exit);
            addCommand(HeadPhoneOn);
            setCommandListener(this);
            display.setCurrent(this);
            WriteClientInfo();
        }
        catch(Exception ex)
        {
            f.append(ex.toString());
        }
    }

    public void callPaint()
    {
        repaint();
    }

    public void setImage(Image im)
    {
        this.im = Image.createImage(im);
        repaint();
    }

    protected void paint(Graphics g)
    {
        if(serverInfo.getServerOn() && clientInfo.ScreenON)
        {
            if(im != null)
            {
                g.drawImage(im, clientInfo.getWidth() / 2, clientInfo.getHeight() / 2, 3);
                canvasMouse.UpadateCursorPosition();
                canvasMouse.createCursorImage(g);
            } else
            {
                g.drawString("No Image Found", getWidth() / 2, getHeight() / 2, 3);
            }
        } else
        {
            g.setColor(55, 0, 0);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(55, 100, 20);
            if(i == getWidth())
                i = 0;
            i++;
            g.drawString("Server is OFF , Please ON the Server\n", getWidth() / 2 + i, getHeight() / 2, 3);
        }
    }

    public void Write(String s)
    {
        try
        {
            out.writeUTF(s);
            out.flush();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void KeyWrite(int keyCode, char c)
    {
        if(serverInfo.getServerOn())
            switch(keyCode)
            {
            case 48: // '0'
                Write("0" + c + "");
                break;

            case 49: // '1'
                Write("1" + c + "");
                break;

            case 50: // '2'
                Write("2" + c + "");
                break;

            case 51: // '3'
                Write("3" + c + "");
                break;

            case 52: // '4'
                Write("4" + c + "");
                break;

            case 53: // '5'
                Write("5" + c + "");
                break;

            case 54: // '6'
                Write("6" + c + "");
                break;

            case 55: // '7'
                Write("7" + c + "");
                break;

            case 56: // '8'
                Write("8" + c + "");
                break;

            case 57: // '9'
                Write("9" + c + "");
                break;

            case 42: // '*'
                Write("*" + c + "");
                break;

            case 35: // '#'
                Write("#" + c + "");
                break;

            case 1: // '\001'
                Write("U" + c + "");
                break;

            case 6: // '\006'
                Write("D" + c + "");
                break;

            case 2: // '\002'
                Write("L" + c + "");
                break;

            case 5: // '\005'
                Write("R" + c + "");
                break;

            case 8: // '\b'
                Write("F" + c + "");
                break;

            case 3: // '\003'
            case 4: // '\004'
            case 7: // '\007'
            case 9: // '\t'
            case 10: // '\n'
            case 11: // '\013'
            case 12: // '\f'
            case 13: // '\r'
            case 14: // '\016'
            case 15: // '\017'
            case 16: // '\020'
            case 17: // '\021'
            case 18: // '\022'
            case 19: // '\023'
            case 20: // '\024'
            case 21: // '\025'
            case 22: // '\026'
            case 23: // '\027'
            case 24: // '\030'
            case 25: // '\031'
            case 26: // '\032'
            case 27: // '\033'
            case 28: // '\034'
            case 29: // '\035'
            case 30: // '\036'
            case 31: // '\037'
            case 32: // ' '
            case 33: // '!'
            case 34: // '"'
            case 36: // '$'
            case 37: // '%'
            case 38: // '&'
            case 39: // '\''
            case 40: // '('
            case 41: // ')'
            case 43: // '+'
            case 44: // ','
            case 45: // '-'
            case 46: // '.'
            case 47: // '/'
            default:
                Write(getKeyName(keyCode) + c + "");
                break;
            }
    }

    protected void keyReleased(int keyCode)
    {
        keyValue = getKeyName(keyCode);
        KeyWrite(keyCode, 'r');
    }

    protected void keyPressed(int keyCode)
    {
        keyValue = getKeyName(keyCode);
        KeyWrite(keyCode, 'p');
        canvasMouse.MousePressed();
        repaint();
    }
    public void outUTF(String s)
    {
         try
         {
            out.writeUTF(s);
            out.flush();
         }
         catch(Exception ex)
         {
            f.append(ex.toString());
         }
    }
    public void commandAction(Command c, Displayable d)
    {
        if(c == Form)
            display.setCurrent(f);
        else if(c == HeadPhoneOn)
        {
            this.removeCommand(HeadPhoneOn);
            this.addCommand(HeadPhoneOff);
            
            outUTF("HeadPhoneOn");
            clientInfo.ScreenON = false;
            
            
        }
        else if(c == HeadPhoneOff)
        {
            this.removeCommand(HeadPhoneOff);
            this.addCommand(HeadPhoneOn);
            outUTF("HeadPhoneOff");
            clientInfo.ScreenON = true;
        }
        else if(c == FManagerON)
        {
            outUTF("FileManager");
            clientInfo.FileManagerON = true;
            clientInfo.ScreenON = false;
            display.setCurrent(FileManager);
        }
        else if(c == Screen)
        {
                outUTF("FileManagerOFF");
            
                clientInfo.FileManagerON = false;
                clientInfo.ScreenON = true;
                display.setCurrent(this);
        }
        else if(c == RotateON)
        {
            clientInfo.setRotation(true);
            outUTF("rotate");
            removeCommand(RotateON);
            addCommand(RotateOFF);
        } 
        else if(c == RotateOFF)
        {
            clientInfo.setRotation(false);
            outUTF("rotate");
            removeCommand(RotateOFF);
            addCommand(RotateON);
        } 
        else if(c == ZoomON)
        {
            clientInfo.setZoom(true);
            removeCommand(ZoomON);
            addCommand(ZoomOFF);
            outUTF("Zoom");
                
        } 
        else if(c == ZoomOFF)
        {
            clientInfo.setZoom(false);
            removeCommand(ZoomOFF);
            addCommand(ZoomON);
           outUTF("Zoom");
                
        } 
        else if(c == getKeyboard)
        {
            outUTF("ChangeKeyMode");
            removeCommand(getKeyboard);
            addCommand(getMouse);
        } 
        else if(c == getMouse)
        {
            outUTF("ChangeKeyMode");    
            removeCommand(getMouse);
            addCommand(getKeyboard);
        } 
        else if(c == Exit)
            UC.notifyDestroyed();
        else
        if(c == ServerOFF)
        {
            outUTF("ServerOFF");
            UC.serverInfo.setServerON(false);
            removeCommand(ServerOFF);
            addCommand(ServerOn);
            repaint();
        } else
        if(c == ServerOn)
        {
            outUTF("ServerON");
            UC.serverInfo.setServerON(true);
            removeCommand(ServerOn);
            addCommand(ServerOFF);
            repaint();
        }
    }

    public void WriteClientInfo()
        throws IOException
    {
        out.writeInt(clientInfo.getHeight());
        out.writeInt(clientInfo.getWidth());
        f.append("Client Info");
        out.flush();
    }

    
}
