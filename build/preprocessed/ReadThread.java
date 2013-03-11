// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 9/8/2009 8:27:53 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ReadThread.java

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;

public class ReadThread implements Runnable
{

    UltimateControl UC;
    //ReadThread(DataInputStream in, Form f, ScreenCanvas sc, ServerInfo serverInfo, ClientInfo clientInfo)
    ReadThread(UltimateControl UC)
    {
        try
        {
            this.sc = UC.canvas;
            this.in = UC.in;
            this.clientInfo = UC.clientInfo;
            form = UC.BG;
            this.serverInfo = UC.serverInfo;
            thr = new Thread(this);
            thr.setPriority(7);
            thr.start();
        }
        catch(Exception ex)
        {
            form.append(ex.toString());
            form.append("Failed to Open in");
        }
    }

    public void ReadScreen()
        throws IOException, InterruptedException
    {
        int len = 0;
        len = in.readInt();
        if(len != 0)
        {
            form.append("Size :" + len + " ");
            byte b[] = new byte[len];
            for(int i = 0; i < len; i++)
                b[i] = (byte)in.read();

            im = Image.createImage(b, 0, len);
            if(im != null)
            {
                form.deleteAll();
                sc.setImage(im);
            } else
            {
                form.append("Image is null");
            }
        }
    }

    public void ReadLine()
        throws IOException
    {
        String x = in.readUTF();
        if(x != null)
            form.append(x + '\n');
    }

    public void UpdateServerInfo()
        throws IOException
    {
        serverInfo.setServerMosuseX(in.readInt());
        serverInfo.setServerMouseY(in.readInt());
    }

    public void ReadFileSystem()
        throws IOException
    {
        sc.FileManager.append("Reading FileSystem");
        String Drives = in.readUTF();
        sc.FileManager.append(Drives);
    }

    public void run()
    {
        try
        {
            form.deleteAll();
            form.append("Session Started\n");
            do
            {
                
                    if(clientInfo.ScreenON)
                    {
                        ReadScreen();
                        UpdateServerInfo();
                        form.append("Mouse X : " + serverInfo.getServerMosueX() + '\n');
                        form.append("Mouse Y : " + serverInfo.getServerMosueY() + '\n');
                        form.append("ServerH : " + serverInfo.getServerHeight() + '\n');
                        form.append("ServerW : " + serverInfo.getServerWidth() + '\n');
                    }
                    else if(!clientInfo.FileManagerON || !clientInfo.looping)
                    {
                        ReadFileSystem();
                        clientInfo.looping = false;
                    }
                    else
                    {
                        HeadPhone hp = new HeadPhone(UC);

                        
                        while(!clientInfo.ScreenON)
                        {
                            byte b[] = new byte[1024];
                            in.read(b);
                            if(b!=null)
                            {
                                hp.Play(b);
                            }

                        }
                            
                    }
            } while(true);
        }
        catch(Exception ex)
        {
            form.append(ex.toString());
        }
        try
        {
            in.close();
        }
        catch(IOException ex)
        {
            form.append(ex.toString());
            form.append("In Read Thead");
        }
    }

    DataInputStream in;
    Thread thr;
    Form form;
    private Image im;
    ScreenCanvas sc;
    ServerInfo serverInfo;
    ClientInfo clientInfo;
}
