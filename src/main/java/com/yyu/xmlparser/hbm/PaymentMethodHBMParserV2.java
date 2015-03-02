package com.yyu.xmlparser.hbm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.yyu.fwk.util.MapFileUtil;
import com.yyu.xmlparser.Parser;
import com.yyu.xmlparser.hbm.attributeparser.ColumnParser;
import com.yyu.xmlparser.hbm.attributeparser.ManyToOneParser;
import com.yyu.xmlparser.hbm.attributeparser.MetaParser;
import com.yyu.xmlparser.hbm.attributeparser.PropertyParser;

public class PaymentMethodHBMParserV2 extends HBMParserBase {

    public final static Map<String,String> methodTypeFields = new HashMap<String,String>();
    public final static Map<String, Set<String>> fieldMapMethodType = new HashMap<String, Set<String>>();
    static{
        methodTypeFields.put("CreditCard", new String("creditCardType,creditCardNumber,bankIdentificationNumber,creditCardMaskNumber,creditCardExpirationYear,creditCardExpirationMonth,creditCardHolderName,creditCardAddress1,creditCardAddress2,creditCardCountry,creditCardState,creditCardCity,creditCardPostalCode"));
        
        methodTypeFields.put("DebitCard", new String("creditCardType,creditCardNumber,bankIdentificationNumber,creditCardMaskNumber,creditCardExpirationYear,creditCardExpirationMonth,creditCardHolderName,creditCardAddress1,creditCardAddress2,creditCardCountry,creditCardState,creditCardCity,creditCardPostalCode"));
        
        methodTypeFields.put("ACH", new String("achBankABACode,achBankAccountNumber,achBankAccountNumberMask,achBankAccountType,achBankName,achBankAccountName"));
        
        methodTypeFields.put("PayPal",new String("paypalType,paypalPreapprovalKey,paypalBaid,paypalEmail"));
        
        methodTypeFields.put("CreditCardReferenceTransaction", new String("ccRefTxnNumber,ccRefTxnPnrefID,secondTokenId,creditCardType,creditCardNumber,creditCardHolderName,creditCardExpirationYear,creditCardExpirationMonth,ccReftxnPnrefCreationtime,creditCardAddress1,creditCardAddress2,creditCardCountry,creditCardState,creditCardCity,creditCardPostalCode"));
        
        methodTypeFields.put("BankTransfer", new String("bankTransferType,bankTransferAccountType,country,bankCode,bankName,bankBranchCode,bankCheckDigit,bankAccountNumber,bankAccountNumberMask,bankAccountName,bankStreetName,bankStreetNumber,bankPostalCode,bankCity,IBAN,mandateId,mandateReceivedStatus,mandateCreationDate,mandateUpdateDate,firstName,lastName,streetName,streetNumber,postalCode,city,state,businessIdentificationCode,existingMandateStatus,ccRefTxnPnrefID"));
        
        for (Entry<String, String> entry : methodTypeFields.entrySet()) {
            String type = entry.getKey();
            String[] fieldsArray = entry.getValue().split(",");
            for (String field : fieldsArray) {
                field = field.trim();
                Set<String> types = fieldMapMethodType.get(field);
                if (types == null) {
                    types = new HashSet<String>();
                    fieldMapMethodType.put(field, types);
                }
                types.add(type);
            }
        }
    }
    
    public PaymentMethodHBMParserV2(String hbmFilePath) {
        super(hbmFilePath);
    }
    
    @Override
    public List<Parser> getParsers() {
        List<Parser> parsers = new ArrayList<Parser>();
        parsers.add(new PropertyParser(getReader()));
        parsers.add(new ManyToOneParser(getReader()));
        parsers.add(new ColumnParser(getReader()));
        parsers.add(new MetaParser(getReader(), MetaParser.API_FIELD_NAME));
        parsers.add(new MetaParser(getReader(), MetaParser.API_ACCESS));
        parsers.add(new MetaParser(getReader(), MetaParser.API_FIELD_MAX_VERSION));
        parsers.add(new MetaParser(getReader(), MetaParser.API_FIELD_MIN_VERSION));
        parsers.add(new MetaParser(getReader(), MetaParser.API_PERMISSION));
        return parsers;
    }
    
    @Override
    public List<String> getNeedSkipElementNames() {
        List<String> es = new ArrayList<String>();
        es.add("one-to-one");
        return es;
    }
    
    public static void main(String[] args) throws Exception {
        String xmlFilePath = "/Users/yangyu/Documents/zuora/main_workspace/main_webapp/src/com/zuora/zbilling/paymentmethod/model/PaymentMethod.hbm.xml";
//        String xmlFilePath = "/Users/yangyu/Desktop/test.xml";
        PaymentMethodHBMParserV2 parser = new PaymentMethodHBMParserV2(xmlFilePath);
        
        List<Map<String, String>> fieldsInfo = parser.getFiledsInfo();
        for (Map<String, String> field : fieldsInfo) {
            String javaName = field.get("javaName");
            Set<String> types = fieldMapMethodType.get(javaName);
            if (types != null) {
                String typeString = types.toString().replaceAll("\\[", "").replaceAll("\\]", "");
                field.put("PaymentMethodType", typeString);
            }
        }
        
        Set<String> titles = parser.getTitles();
        titles.add("PaymentMethodType");
        
        MapFileUtil.writeToFile(Arrays.asList(titles.toArray(new String[titles.size()])), fieldsInfo, "/Users/yangyu/Desktop/pm.csv", false);
        System.out.println("done");
        
        
    }
}
