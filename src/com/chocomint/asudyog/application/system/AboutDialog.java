package com.chocomint.asudyog.application.system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class AboutDialog {

	private static JDialog viewer;
	private static JOptionPane pane;
	private static KeyStroke keyStroke;
	private static JRootPane rootPane;
	public static void show() {
		pane = new JOptionPane();
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		pane.setMessage(new Info());
		viewer = pane.createDialog("About Invoice Generator");
		rootPane = viewer.getRootPane();
		rootPane.registerKeyboardAction(al, keyStroke,JComponent.WHEN_IN_FOCUSED_WINDOW);
		viewer.setSize(340,280);
		viewer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		viewer.setLocationRelativeTo(null);
		viewer.setVisible(true);
	}
	private static ActionListener al = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			viewer.dispose();
		}
		
	};
}
