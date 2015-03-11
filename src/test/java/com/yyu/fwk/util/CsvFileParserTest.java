package com.yyu.fwk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class CsvFileParserTest {
    
    @Test
    public void testExtraValueFromCSV() throws Exception {
        Map<String, Double> invoiceAppliedPaymentAmount = new HashMap<String, Double>();
        
        CsvFileParser parser = new CsvFileParser("/Users/yangyu/Downloads/payment_invoice.csv", ',');
        while(parser.hasNext()) {
            Map<String, String> values = parser.next();
            String invoice_id = values.get("invoice_id");
            String pi_payment_amount = values.get("pi_payment_amount");
            
            Double amount = invoiceAppliedPaymentAmount.get(invoice_id);
            if (amount == null) {
                amount = 0.0;
            }
            amount += Double.valueOf(pi_payment_amount);
            invoiceAppliedPaymentAmount.put(invoice_id, amount);
        }
        
        List<String> invoiceIdList = new ArrayList<String>();
        
        for (String invoiceId : invoiceAppliedPaymentAmount.keySet()) {
            invoiceIdList.add(invoiceId);
        }
        
        Collections.sort(invoiceIdList);
        
        for(String invoiceId : invoiceIdList) {
            Double appliedAmount = invoiceAppliedPaymentAmount.get(invoiceId);
            System.out.println(invoiceId + "," + appliedAmount);
        }
        
        parser.close();
    }
}
