package com.chocomint.asudyog.application.system;
import javax.swing.ImageIcon;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

public class GenerateBarcode {
	@SuppressWarnings("finally")
	public static ImageIcon drawingBarcodeDirectToGraphics(String invoice_no) {
		Barcode barcode;
		ImageIcon i = null;
		try {
			barcode = BarcodeFactory.createCodabar(invoice_no);
			i = new ImageIcon(BarcodeImageHandler.getImage(barcode));
		} catch (BarcodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return i;
		}
	}
}
