package com.chocomint.asudyog.application.reports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import com.chocomint.asudyog.util.Constants;

public class ReportViewer {
	private static JDialog viewer;
	private static JOptionPane pane;
	private static Box contents;
	private static KeyStroke keyStroke;
	private static JRootPane rootPane;
	private static String path;
	
	public static void displayReport(String fileName) {
		if(!fileName.equals("")) {
			show(fileName);
		} else {
			JOptionPane.showMessageDialog(null, "Invoice report doesn't exist!", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void show(String fileName) {
		SwingController controller = new SwingController();
        SwingViewBuilder factory = new SwingViewBuilder(controller);
        JPanel viewerComponentPanel = factory.buildViewerPanel();
        controller.getDocumentViewController().setAnnotationCallback(
                new MyAnnotationCallback(
                        controller.getDocumentViewController()));
		pane = new JOptionPane();
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		contents= Box.createVerticalBox();
		contents.setBorder(new EmptyBorder(5,5,5,5));
		contents.add(viewerComponentPanel);
		path = Constants.invoicesPath + fileName;
		path = path.replaceAll("%20", " ");
		path = path.replace('\\', '/');
		controller.openDocument(path);
		pane.setMessage(viewerComponentPanel);
		viewer = pane.createDialog("View Invoice : " + fileName);
		rootPane = viewer.getRootPane();
		rootPane.registerKeyboardAction(al, keyStroke,JComponent.WHEN_IN_FOCUSED_WINDOW);
		viewer.setSize(660,640);
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
