package temp;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.yyu.fwk.util.HttpUtil;

public class URLTest extends TestCase {
    
    @Test
    public void testBillingAccountDetailPage() throws Exception {
        String login = "http://localhost:8080/apps/newlogin.do";
        Map<String, String> params = new HashMap<String, String>();
        params.put("method", "login");
        params.put("password", "123456");
        params.put("remember", "false");
        String response = HttpUtil.request(login, params);
        System.out.println(response);
        
        
        String accountDetail = HttpUtil.request(viewAccount, new HashMap<String, String>());
        System.out.println(accountDetail);
    }
}
