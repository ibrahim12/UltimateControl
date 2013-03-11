// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 9/8/2009 8:27:30 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConnectServer.java

import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.*;
import javax.microedition.lcdui.Form;
import javax.microedition.rms.RecordStore;

public class ConnectServer implements Runnable
{

    public static Vector devices = new Vector();
    public static Vector deviceClasses = new Vector();
    public static Vector services = new Vector();
    public static int selectedDevice = -1;
    public int deviceReturnCode;
    public int serviceReturnCode;
    public boolean DeviceSearchComplete;
    public boolean ServiceSearchComplete;
    public UUID serviceUUIDs[];
    private LocalDevice device;
    private DiscoveryAgent agent;
    private int DiscoveryMode;
    String BTAddress;
    Thread cs;
    boolean found;
    public boolean Inq_result;
    Form f;

    class Listener implements DiscoveryListener
    {

        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass deviceClass)
        {
            try
            {
                RecordStore deviceRecord = RecordStore.openRecordStore("DeviceCached", true);
                byte b[] = String.valueOf(btDevice).getBytes();
                deviceRecord.addRecord(b, 0, b.length);
                deviceRecord.closeRecordStore();
            }
            catch(Exception ex)
            {
                f.append(ex.toString());
            }
            ConnectServer.devices.addElement(btDevice);
            ConnectServer.deviceClasses.addElement(deviceClass);
        }

        public void servicesDiscovered(int transID, ServiceRecord servRecord[])
        {
            for(int i = 0; i < servRecord.length; i++)
            {
                ServiceRecord record = servRecord[i];
                ConnectServer.services.addElement(record);
            }

            if(ConnectServer.services.size() > 0)
                found = true;
        }

        public void serviceSearchCompleted(int transID, int complete)
        {
            serviceReturnCode = complete;
            ServiceSearchComplete = true;
            ShowFoundServices();
        }

        public void inquiryCompleted(int complete)
        {
            deviceReturnCode = complete;
            DeviceSearchComplete = true;
            ShowFoundDevices();
        }

        public Listener()
        {
        }
    }


    ConnectServer(int mode, UUID uuid[], Form f)
    {
        DeviceSearchComplete = false;
        ServiceSearchComplete = false;
        serviceUUIDs = null;
        found = false;
        DiscoveryMode = mode;
        serviceUUIDs = uuid;
        this.f = f;
        cs = new Thread(this);
        cs.start();
    }

    public String StartDeviceSearch()
        throws BluetoothStateException, InterruptedException
    {
        devices.removeAllElements();
        deviceClasses.removeAllElements();
        device = LocalDevice.getLocalDevice();
        device.setDiscoverable(DiscoveryMode);
        BTAddress = device.getBluetoothAddress();
        f.append("Initializing Local Device\n");
        f.append(BTAddress + '\n');
        f.append(device.getFriendlyName() + '\n');
        agent = device.getDiscoveryAgent();
        Inq_result = agent.startInquiry(DiscoveryMode, new Listener());
        return "Device Search Started";
    }

    public void ShowFoundDevices()
    {
        RemoteDevice rd[] = getDiscoveredDevices();
        f.append("Device Found :" + rd.length + "" + '\n');
        for(int i = 0; i < rd.length; i++)
            try
            {
                f.append((i + 1) + " :" + rd[i].getFriendlyName(false) + '\n');
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }

    }

    public void ShowFoundServices()
    {
        ServiceRecord s[] = getDiscoveredServices();
        f.append("Service Found :" + s.length + "" + '\n');
        for(int i = 0; i < s.length; i++)
            f.append((i + 1) + ": " + s[i].toString() + '\n');

    }

    public String StartServiceSearch()
    {
        RemoteDevice rdevice = null;
        int selected = -1;
        services.removeAllElements();
        for(int i = 0; i < devices.size(); i++)
        {
            if(found)
                continue;
            RemoteDevice rd = (RemoteDevice)devices.elementAt(i);
            try
            {
                agent.searchServices(null, serviceUUIDs, rd, new Listener());
            }
            catch(Exception ex)
            {
                f.append(ex.toString());
            }
        }

        return "Service Search Started";
    }

    public ServiceRecord[] getDiscoveredServices()
    {
        ServiceRecord r[] = new ServiceRecord[services.size()];
        services.copyInto(r);
        return r;
    }

    public RemoteDevice[] getDiscoveredDevices()
    {
        RemoteDevice rd[] = new RemoteDevice[devices.size()];
        devices.copyInto(rd);
        return rd;
    }

    public ServiceRecord getFirstDiscoveredService()
    {
        if(services.size() > 0)
            return (ServiceRecord)services.elementAt(0);
        else
            return null;
    }

    public void run()
    {
        try
        {
            f.append('\n' + StartDeviceSearch() + "");
            while(!DeviceSearchComplete) 
            {
                Thread.sleep(1000L);
                f.append(".");
            }
            f.append("Device Search Completed\n");
            f.append('\n' + StartServiceSearch() + "");
            while(!ServiceSearchComplete) 
            {
                Thread.sleep(1000L);
                f.append(".");
            }
        }
        catch(Exception ex)
        {
            f.append(ex.toString());
        }
    }

  
}
