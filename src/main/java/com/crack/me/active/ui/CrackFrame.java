package com.crack.me.active.ui;

import com.crack.me.active.Crack;
import com.crack.me.active.Crackable;
import com.crack.me.active.RSAKey;
import com.crack.me.active.ui.core.PropertyWriter;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class CrackFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -4884505908308759981L;
	private StringBuffer helpMessage = new StringBuffer()
			.append("Fllow Orders.\n")
			.append("0. Close MyEclipse Application(if you wanna replace jar file).\n")
			.append("1. Fill usercode\n")
			.append("2. Fill systemid\n")
			.append("    *. myeclipse active dialog show\n")
			.append("    *. Press Button to Generate id\n")
			.append("3. Tools->RebuildKey {Create or Replace [private/public]Key.bytes (if not exists in current folder).}\n")
			.append("4. Press Active Buttom\n")
			.append("belows not options\n")
			.append("5. Tools->ReplaceJarFile\n")
			.append("    *. choose MyEclipseFolder->Common->plugins \n")
			.append("    *. it will replace SignatureVerifier.class and publicKey.bytes in jars under plugins folder, opreation will do together.\n")
			.append("6. Tools->SaveProperites\n")
			.append("    *. MyEclipse Startup Will Read This File to Active Product.\n")
			.append("7. Open MyEclipse Application.\n\n");

	JComboBox cbLicenceType = new JComboBox(new Object[] { "PROFESSIONAL",
			"STANDARD", "BLUE", "SPRING", "BLING" });

	JTextField tfUsercode = new JTextField();
	JTextField tfSystemId = new JTextField();
	JButton jbGenerateSi = new JButton("SystemId...");
	JButton jbActive = new JButton("Active");
	final JTextArea taInfo = new JTextArea(this.helpMessage.toString());

	JMenuItem generateSystemIdMenu = new JMenuItem("GenerateSystemId...");
	JMenuItem runMenu = new JMenuItem("RunActive");
	JMenuItem rebuildKeyMenu = new JMenuItem("0.RebuildKey...");
	JMenuItem saveProperitiesMenu = new JMenuItem("1.SaveProperities");
	JMenuItem exitMenu = new JMenuItem("Exit");

	private Loggable log = new Loggable() {
		public void log(String message) {
			CrackFrame.this.taInfo.append(message + "\n");
		}
	};

	private Crackable crack = new Crack();

	private RSAKey rsaKey = new RSAKey();

	private PropertyWriter propertyWriter = new PropertyWriter(this.log);

	private String[][] lastCrackData = null;

	public CrackFrame() {
		super("MyEclipse 9.x Crack");
		initFrame();
		setDefaultCloseOperation(3);
		setSize(800, 600);
		setVisible(true);
	}

	public void initFrame() {
		initMenu();
		initTopPanel();
		initCenterPanel();
	}

	public void initMenu() {
		JMenuBar menubar = new JMenuBar();

		JMenu menu1 = new JMenu("Opreate");
		JMenu menu2 = new JMenu("Tools");

		menu1.add(this.generateSystemIdMenu);
		menu1.add(this.runMenu);
		menu1.addSeparator();
		menu1.add(this.exitMenu);

		menu2.add(this.rebuildKeyMenu);
		menu2.addSeparator();
		menu2.add(this.saveProperitiesMenu);

		this.generateSystemIdMenu.addActionListener(this);
		this.runMenu.addActionListener(this);
		this.exitMenu.addActionListener(this);
		this.rebuildKeyMenu.addActionListener(this);
		this.saveProperitiesMenu.addActionListener(this);

		menubar.add(menu1);
		menubar.add(menu2);

		setJMenuBar(menubar);
	}

	public void initCenterPanel() {
		JScrollPane jsp = new JScrollPane(this.taInfo);
		jsp.setBounds(100, 90, 450, 300);
		getContentPane().add(jsp, "Center");
	}

	public void initTopPanel() {
		JPanel toppanel = new JPanel();
		toppanel.setLayout(null);
		JLabel lUserCode = new JLabel("Usercode:", 4);
		JLabel lSystemId = new JLabel("SystemId:", 4);

		lUserCode.setBounds(10, 10, 85, 30);
		this.tfUsercode.setBounds(100, 10, 300, 30);
		this.cbLicenceType.setBounds(410, 10, 170, 30);
		lSystemId.setBounds(10, 50, 85, 25);
		this.tfSystemId.setBounds(100, 50, 300, 30);
		this.jbGenerateSi.setBounds(410, 50, 100, 30);
		this.jbActive.setBounds(500, 50, 80, 30);

		this.jbGenerateSi.addActionListener(this);
		this.jbActive.addActionListener(this);

		toppanel.add(lUserCode);
		toppanel.add(this.tfUsercode);
		toppanel.add(this.cbLicenceType);
		toppanel.add(lSystemId);
		toppanel.add(this.tfSystemId);
		toppanel.add(this.jbGenerateSi);
		toppanel.add(this.jbActive);

		JPanel tmpPanel = new JPanel();
		tmpPanel.add(toppanel, "Center");

		toppanel.setPreferredSize(new Dimension(600, 90));
		getContentPane().add(tmpPanel, "North");
	}

	public void actionPerformed(ActionEvent paramActionEvent) {
		Object source = paramActionEvent.getSource();
		if ((source == this.jbGenerateSi)
				|| (source == this.generateSystemIdMenu)) {
			try {
				this.tfSystemId.setText(this.crack.getSystemId());
			} catch (Throwable e) {
				e.printStackTrace();
				this.taInfo.append(new Date().toLocaleString() + "\n"
						+ e.getCause().getMessage() + "\n\n");
			}
		} else if ((source == this.jbActive) || (source == this.runMenu)) {
			String uid = this.tfUsercode.getText().trim();
			String systemId = this.tfSystemId.getText().trim();
			if (("".equals(uid)) || ("".equals(systemId))) {
				this.taInfo.append(new Date().toLocaleString()
						+ "\nusercode or systemid is empty!\n\n");
				return;
			}
			int licenceType = getLicenceType();
			int licenceNumber = 0;
			String ret = "";
			try {
				this.lastCrackData = this.crack.crackme(uid, licenceType,
						licenceNumber, systemId);
				ret = showStringArray(this.lastCrackData);
			} catch (Throwable e) {
				e.printStackTrace();
				ret = e.getMessage() + "\n";
			}
			this.taInfo.append(new Date().toLocaleString() + "\n" + ret + "\n");
		} else if (source == this.saveProperitiesMenu) {
			if (this.lastCrackData != null) {
				this.propertyWriter.write(this.lastCrackData);
			}
		} else if (source == this.exitMenu) {
			System.exit(0);
		} else if (source == this.rebuildKeyMenu) {
			this.log.log("rebuild privateKey.bytes");
			this.log.log("rebuild publicKey.bytes");
			this.rsaKey.generateKeyFile();
		}
	}

	public String showStringArray(String[][] arrs) {
		if ((arrs == null) || (arrs.length == 0)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		String[][] arrayOfString;
		int j = (arrayOfString = arrs).length;
		for (int i = 0; i < j; i++) {
			String[] string = arrayOfString[i];

			sb.append(string[0] + "\n\t" + string[1] + "\n");
		}

		return sb.toString();
	}

	public int getLicenceType() {
		String selectValue = this.cbLicenceType.getSelectedItem().toString();
		if ("STANDARD".equals(selectValue))
			return 0;
		if ("PROFESSIONAL".equals(selectValue))
			return 1;
		if ("BLUE".equals(selectValue))
			return 2;
		if ("SPRING".equals(selectValue))
			return 3;
		if ("BLING".equals(selectValue)) {
			return 4;
		}
		return 1;
	}

	public static void main(String[] args) throws Exception {
		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		new CrackFrame();
	}
}
