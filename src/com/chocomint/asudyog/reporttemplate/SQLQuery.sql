SELECT
     Goods.Serial_No AS Goods_Serial_No,
     Goods.Description AS Goods_Description,
     Goods.Quantity AS Goods_Quantity,
     Goods.Rate AS Goods_Rate,
     Goods.Per AS Goods_Per,
     Goods.Value_ AS Goods_Value_,
     Goods.VAT AS Goods_VAT,
     Goods.Tax AS Goods_Tax,
     Goods.Sub_Total AS Goods_Sub_Total,
     Invoice.Bill_No AS Invoice_Bill_No,
     Invoice.VAT_No AS Invoice_VAT_No,
     Invoice.Bill_Date AS Invoice_Bill_Date,
     Invoice.Messers AS Invoice_Messers,
     Invoice.Buyers_VAT_No AS Invoice_Buyers_VAT_No,
     Invoice.Address AS Invoice_Address,
     Invoice.Buyers_Order_No AS Invoice_Buyers_Order_No,
     Invoice.Date_1 AS Invoice_Date_1,
     Invoice.Challan_No AS Invoice_Challan_No,
     Invoice.Date_2 AS Invoice_Date_2,
     Invoice.Total_Item AS Invoice_Total_Items,
     Invoice.Grand_Total AS Invoice_Grand_Total
FROM
     Goods,
     Invoice
WHERE
      Invoice.Bill_No = '19461031043' and Goods.Bill_No_ = '19461031043'