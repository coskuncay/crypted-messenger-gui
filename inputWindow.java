import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class inputWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextField usernameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inputWindow frame = new inputWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public inputWindow() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 408, 152);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnteruser = new JLabel("Enter user name:");
		lblEnteruser.setBounds(73, 28, 164, 16);
		contentPane.add(lblEnteruser);
		usernameField = new JTextField();
		usernameField.setBounds(67, 48, 335, 34);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createUser(usernameField.getText());
				setVisible(false);
			}
		});
		btnOK.setBounds(92, 94, 117, 29);
		contentPane.add(btnOK);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		btnCancel.setBounds(209, 94, 117, 29);
		contentPane.add(btnCancel);
		JLabel lblImage = new JLabel("");
		lblImage.setBounds(21, 28, 40, 40);
		ImageIcon img = new ImageIcon("/questionMark.png");
		getContentPane().setLayout(null);
		lblImage.setIcon(img);
		getContentPane().add(lblImage);
		
	}


	public void createUser(String name) {
		if(Server.serverCounter==1) {
			Server.lblConnectionStatus.setText("Connected: " + name);
			Server.btnConnect.setEnabled(false);
			Server.btnDisconnect.setEnabled(true);
			Server.name=name;
		}
		else if(Client.clientCounter ==1) {
			Client.lblConnectionStatus.setText("Connected: " + name);
			Client.btnConnect.setEnabled(false);
			Client.btnDisconnect.setEnabled(true);
			Client.name=name;
		}
		
		
	}
	
	
}
