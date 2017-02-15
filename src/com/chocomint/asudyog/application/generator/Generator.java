package com.chocomint.asudyog.application.generator;

import javax.swing.JPanel;


public class Generator extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7917250434905428624L;
	protected static Header h;
	protected static About a;
	protected static Item i;
	protected static DeliveryDetails dl;
	protected static List l;
	
	public Generator() {
		createComponents();
		addComponents();
		setUpComponents();
	}
	
	private void createComponents() {
		h = new Header();
		a = new About();
		dl = new DeliveryDetails();
		i = new Item();
		l = new List();
	}
	
	private void addComponents() {
		add(h);
		add(a);
		add(dl);
		add(i);
		add(l);
	}
	
	private void setUpComponents() {
		setLayout(null);
	}
	
	public void loadNewInvoice() {
		a.loadNewAbout();
		dl.loadNewDeliveryDetails();
		i.loadNewItem(true);
		l.loadNewList();
	}

	public boolean validateFields() {
		boolean flag = true;
		boolean b[] = new boolean[3];
		b[0] = a.validateAbout();
		b[1] = dl.validateDeliveryDetails();
		b[2] = l.validateList();
		for(boolean x : b) {
			if(x == false) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		return flag;
	}
	
	public void lockFields(boolean f) {
		a.lockFields(f);
		dl.lockFields(f);
		i.lockFields(f);
		l.lockFields(f);
	}
}
