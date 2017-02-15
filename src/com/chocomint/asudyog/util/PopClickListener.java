package com.chocomint.asudyog.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class PopClickListener  extends MouseAdapter {
	private JPopupMenu pop;
	private JTable source;
	public int row;
	public int column;
	
	public PopClickListener(JPopupMenu pop, JTable source) {
		this.pop = pop;
		this.source = source;
	}
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
    	 source = (JTable)e.getSource();
    	 row = source.rowAtPoint( e.getPoint() );
         column = source.columnAtPoint( e.getPoint() );

         if (! source.isRowSelected(row))
             source.changeSelection(row, column, false, false);
        pop.show(e.getComponent(), e.getX(), e.getY());
    }
}
