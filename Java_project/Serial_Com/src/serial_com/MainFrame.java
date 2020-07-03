package serial_com;

import java.awt.BorderLayout;
import java.util.Scanner;

import com.fazecast.jSerialComm.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONException;

import javax.swing.JLabel;

public class MainFrame extends JFrame {
	static SerialPort chosenPort;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() throws SQLException {

		JFrame window = new JFrame();
		Processor p = new Processor();
		LcdSender ls= new LcdSender();

		window.setTitle("Arduino Medical Information System");
		window.setSize(468, 315);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.getContentPane().setLayout(null);


		JComboBox<String> portList = new JComboBox<String>();
		JButton connectButton = new JButton("Connect");
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 458, 35);
		topPanel.add(portList);
		topPanel.add(connectButton);
		window.getContentPane().add(topPanel);
		
		JButton sendToLCD = new JButton("Send To LCD");

		topPanel.add(sendToLCD);
		
		JTextArea txtLog = new JTextArea();
		txtLog.setBounds(26, 96, 400, 148);
		window.getContentPane().add(txtLog);

		JLabel lblLogs = new JLabel("\tLogs :");
		lblLogs.setBounds(31, 65, 72, 42);
		window.getContentPane().add(lblLogs);

		JButton btnAdd = new JButton("Adauga");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFrame addFrame;
				try {
					addFrame = new AddFrame();
					addFrame.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnAdd.setBounds(76, 48, 97, 25);
		window.getContentPane().add(btnAdd);

		JButton btnSearch = new JButton("Cauta");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchFrame searchFrame = new SearchFrame();
				searchFrame.setVisible(true);
			}
		});
		btnSearch.setBounds(197, 48, 97, 25);
		window.getContentPane().add(btnSearch);

		JButton btnDelete = new JButton("Sterge");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteFrame deleteFrame;
				try {
					deleteFrame = new DeleteFrame();
					deleteFrame.setVisible(true);
				} catch (JSONException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnDelete.setBounds(317, 48, 97, 25);
		window.getContentPane().add(btnDelete);

		// populate the drop-down box
		SerialPort[] portNames = SerialPort.getCommPorts();
		for (int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());

		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (connectButton.getText().equals("Connect")) {
					
					chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if (chosenPort.openPort()) {
						connectButton.setText("Disconnect");
						portList.setEnabled(false);
						btnAdd.setEnabled(false);
						btnSearch.setEnabled(false);
						btnDelete.setEnabled(false);
						txtLog.setText("Succesfully Connected to port ");
						System.out.println("Succesfully Connected to port " + portList.getSelectedItem().toString());
						Thread thread_logs = new Thread() {
							@Override
							public void run() {
								String data_to_send = "Nothing found";
								// wait after connecting, so the bootloader can finish
								try {
									Thread.sleep(100);
								} catch (Exception e) {
								}
								Scanner data = new Scanner(chosenPort.getInputStream());
								txtLog.setText("Looking for available cards");
								while (data.hasNextLine()) {
									System.out.println("FOUND CARD : " + data.nextLine());
									data_to_send = data.nextLine();
									txtLog.setText(data_to_send);
									try {;
										p.process(data.nextLine());
										
									} catch (JSONException e) {
										e.printStackTrace();
									}

								}
								try {
									Thread.sleep(100);
								} catch (Exception e) {
								}

							}
						};
						thread_logs.start();
					}
				} else {
					// disconnect from the serial port
					chosenPort.closePort();
					portList.setEnabled(true);
					btnAdd.setEnabled(true);
					btnSearch.setEnabled(true);
					btnDelete.setEnabled(true);
					
					connectButton.setText("Connect");
//					txtLog.setText("Cannot connect to the selected port");
//					System.out.println("Cannot connect to the selected port " + portList.getSelectedItem().toString());
				}
			}
		});
		sendToLCD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String s = txtLog.getText();
					ls.send_data_to_lcd(s);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
