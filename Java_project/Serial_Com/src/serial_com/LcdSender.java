package serial_com;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.fazecast.jSerialComm.SerialPort;

public class LcdSender {
	

	public void send_data_to_lcd(String s) throws InterruptedException {
	    SerialPort sp = SerialPort.getCommPort("COM9"); // device name TODO: must be changed
	    sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
	    sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
	    
	    if (sp.openPort()) {
	      System.out.println("Port is open :)");
	    } else {
	      System.out.println("Failed to open port :(");
	      return;
	    }   
	    
	    if (sp.openPort()) {
	      System.out.println("Port is open :)");
	    } else {
	      System.out.println("Failed to open port :(");
	      return;
	    }        
	    
//	    for (Integer i = 0; i < 5; ++i) {            
//	      sp.getOutputStream().write(i.byteValue());
//	      sp.getOutputStream().flush();
//	      System.out.println("Sent number: " + i);
//	      Thread.sleep(1000);
//	    } 
	    //for (Integer i = 0; i < 10; ++i) {            
	    PrintWriter output = new PrintWriter(sp.getOutputStream());
	    output.print(s);
	    output.flush();
	    Thread.sleep(1000);
	  //  }
	  
	    if (sp.closePort()) {
	      System.out.println("Port is closed :)");
	    } else {
	      System.out.println("Failed to close port :(");
	      return;
	    }
	    
	  }
	}