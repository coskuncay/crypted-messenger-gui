import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.EventQueue;
import javax.crypto.SecretKey;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 
 * 
 * 
 * 
 * 
 * 
 * Emre Coskuncay 21526806
 * Baturalp Karamus 21527137
 *
 *BBM 465 - Crypt Messenger
 *
 *
 *Create a server and socket in line 301
 *
 *
 */
public class Server extends JFrame implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int serverCounter = 0;
	ServerSocket ss;
	Socket s;
	PrintWriter pw;
	BufferedReader br;
	Thread th;
	JTextPane textPane = new JTextPane();
	static JTextArea msgText = new JTextArea();
	static JButton btnConnect;
	static JButton btnDisconnect;
	static JLabel lblConnectionStatus;
	public static String name;
	public static SecretKey DESkey;
	JTextPane cryptedPane;
	JRadioButton rdbtnAes;
	JRadioButton rdbtnDes;
	JRadioButton rdbtnCBC;
	JRadioButton rdbtnOFB;
	String cryptedText;
	String decryptedText;
	ButtonGroup methodGroup;
	ButtonGroup modeGroup; 
	
	 public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Server frame = new Server();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}
	 
	 
	public Server() {
		setTitle("SipsCrypted v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 481, 542);
		JPanel contentPane = new JPanel();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 481, 22);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblServer = new JLabel("Server");
		lblServer.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblServer.setBounds(5, 4, 58, 16);
		panel.add(lblServer);
		lblServer.setBackground(Color.LIGHT_GRAY);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(0, 21, 481, 64);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverCounter++;
				inputWindow newUser = new inputWindow();
				newUser.setVisible(true);
			}
		});
		btnConnect.setEnabled(true);
		btnConnect.setBounds(6, 17, 117, 29);
		panel_1.add(btnConnect);
		
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverCounter--;
				Server.msgText.setText("");
				btnDisconnect.setEnabled(false);
				btnConnect.setEnabled(true);
				methodGroup.clearSelection();
				modeGroup.clearSelection();
				lblConnectionStatus.setText("Not Connection");
			}
		});
		btnDisconnect.setEnabled(false);
		btnDisconnect.setBounds(119, 17, 117, 29);
		panel_1.add(btnDisconnect);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane.setBounds(234, 6, 108, 52);
		panel_1.add(layeredPane);
		
		rdbtnAes = new JRadioButton("AES");
		rdbtnAes.setBounds(0, 23, 55, 23);
		layeredPane.add(rdbtnAes);
		
		rdbtnDes = new JRadioButton("DES");
		rdbtnDes.setBounds(47, 23, 56, 23);
		layeredPane.add(rdbtnDes);
		
		methodGroup = new ButtonGroup();
		methodGroup.add(rdbtnAes);
		methodGroup.add(rdbtnDes);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane_1.setBounds(349, 6, 117, 52);
		panel_1.add(layeredPane_1);
		
		rdbtnCBC = new JRadioButton("CBC");
		rdbtnCBC.setBounds(0, 23, 63, 23);
		layeredPane_1.add(rdbtnCBC);
		
		rdbtnOFB = new JRadioButton("OFB");
		rdbtnOFB.setBounds(55, 23, 56, 23);
		layeredPane_1.add(rdbtnOFB);
		
		modeGroup = new ButtonGroup();
		modeGroup.add(rdbtnCBC);
		modeGroup.add(rdbtnOFB);
		
		JPanel panelConnection = new JPanel();
		panelConnection.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelConnection.setBackground(Color.LIGHT_GRAY);
		panelConnection.setBounds(0, 493, 481, 27);
		contentPane.add(panelConnection);
		panelConnection.setLayout(null);
		
		lblConnectionStatus = new JLabel();
		lblConnectionStatus.setBounds(6, 6, 120, 16);
		lblConnectionStatus.setText("Not Connection");
		panelConnection.add(lblConnectionStatus);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 404, 481, 90);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		textPane.setBounds(6, 6, 150, 78);
		panel_2.add(textPane);
		
		cryptedPane = new JTextPane();
		cryptedPane.setBounds(168, 6, 150, 78);
		panel_2.add(cryptedPane);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cryptedText;
				if(rdbtnAes.isSelected()) {
					if(rdbtnCBC.isSelected()) {
						cryptedText = methodAES.encrypt(textPane.getText(), "CBC");
						cryptedPane.setText(cryptedText);
					}
					else if(rdbtnOFB.isSelected()) {
						cryptedText = methodAES.encrypt(textPane.getText(), "OFB");
						cryptedPane.setText(cryptedText);
					}
				}
				else if(rdbtnDes.isSelected()) {
					if(rdbtnCBC.isSelected()) {
						cryptedText = methodDES.encryptDES(textPane.getText(),"CBC");
						cryptedPane.setText(cryptedText);
					}
					else if(rdbtnOFB.isSelected()) {
						cryptedText = methodDES.encryptDES(textPane.getText(),"OFB");
						cryptedPane.setText(cryptedText);
					}
				}
			}
		});
		btnEncrypt.setBounds(320, 38, 74, 29);
		panel_2.add(btnEncrypt);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(serverCounter<1) {
					JFrame errorFrame = new JFrame();
					errorFrame.setBounds(100, 100, 408, 152);
					JLabel lblError = new JLabel("Please log in");
					lblError.setBounds(73, 28, 164, 16);
					errorFrame.getContentPane().add(lblError);
					errorFrame.setVisible(true);
				}
				else {
						if(!rdbtnAes.isSelected() && !rdbtnDes.isSelected()) {
							JFrame errorFrame = new JFrame();
							errorFrame.setBounds(180, 100, 408, 152);
							JLabel lblError = new JLabel("Please select Method!");
							lblError.setBounds(73, 28, 164, 16);
							errorFrame.getContentPane().add(lblError);
							errorFrame.setVisible(true);
						}
						else if(!rdbtnCBC.isSelected() && !rdbtnOFB.isSelected()) {
							JFrame errorFrame = new JFrame();
							errorFrame.setBounds(180, 100, 408, 152);
							JLabel lblError = new JLabel("Please select Mode!");
							lblError.setBounds(73, 28, 164, 16);
							errorFrame.getContentPane().add(lblError);
							errorFrame.setVisible(true);
						}
						else {
							if(cryptedPane.getText().isEmpty()) {
								JFrame errorFrame = new JFrame();
								errorFrame.setBounds(180, 200, 408, 152);
								JLabel lblError = new JLabel("Please Encrypt your message!");
								lblError.setBounds(73, 28, 164, 16);
								errorFrame.getContentPane().add(lblError);
								errorFrame.setVisible(true);
							}
							else {
								pw.println(Server.name+">"+cryptedPane.getText());
								Server.msgText.append(cryptedPane.getText()+"\n");
								Server.msgText.append(Server.name+" > "+textPane.getText()+"\n");
								textPane.setText("");
								cryptedPane.setText("");
							}
						}
				}
			}
		});
		btnSend.setBounds(391, 21, 84, 63);
		panel_2.add(btnSend);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(0, 84, 481, 320);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBounds(0, 0, 481, 320);
		panel_3.add(panel_4);
		panel_4.setLayout(null);
		
		msgText.setBounds(6, 6, 469, 308);
		panel_4.add(msgText);
		
		try {
			ss = new ServerSocket(12001);
			s = ss.accept();
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw=new PrintWriter(s.getOutputStream(),true);
		} 
		catch (IOException e1){
			
		}
		  th=new Thread(this);
		  th.setDaemon(true);
		  th.start();
		  
		 
	}
	public void run() {
		while(true)
		  {
			try{
				   String s=br.readLine();
				   String[] cipherText = s.split(">");
				   Server.msgText.append(cipherText[1]+"\n");
				   if(rdbtnAes.isSelected()) {
						if(rdbtnCBC.isSelected()) {
							decryptedText = methodAES.decrypt(cipherText[1], "CBC");
						}
						else if(rdbtnOFB.isSelected()) {
							decryptedText = methodAES.decrypt(cipherText[1], "OFB");
						}
					}
					else if(rdbtnDes.isSelected()) {
						if(rdbtnCBC.isSelected()) {
							decryptedText = methodDES.decryptDes(cipherText[1],"CBC");
						}
						else if(rdbtnOFB.isSelected()) {
							decryptedText = methodDES.decryptDes(cipherText[1],"OFB");
						}
					}
				   Server.msgText.append(cipherText[0]+" > "+decryptedText+"\n"); 
			   }catch(Exception e) {
			   
		   }
		  }
	}
}
