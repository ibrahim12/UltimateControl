
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ibrahim
 */
public class HeadPhone implements Runnable {

    Thread t;
    Player player;
    UltimateControl UC;
    ByteArrayInputStream in;
    HeadPhone(UltimateControl UC)
    {
        
        this.UC = UC;
        t = new Thread(this);
        t.start();
    }

    public void Play(byte b[]){
        try
        {
            in = new ByteArrayInputStream(b);
            player = Manager.createPlayer(in, "audio/basic");
            player.prefetch();
            //in = getClass().getResourceAsStream( "/media.wav" );
            //player = Manager.createPlayer( in, "audio/wav" );
            //player.realize();

            VolumeControl vc = (VolumeControl) player.getControl("VolumeControl");
            vc.setLevel(100);

            player.prefetch();
            player.start();

            
        }catch(Exception e)
        {
            UC.BG.append(e.toString());
        }
}

    public void run() 
    {
       
        try {
            while(!UC.clientInfo.ScreenON)
            {
                Thread.sleep(5000);
            }
        
            player.stop();
        } catch (Exception ex) {
            UC.write(ex.toString());
        }


    }


}

