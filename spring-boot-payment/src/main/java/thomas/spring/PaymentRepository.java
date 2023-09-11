package thomas.spring;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentRepository {

    private final static Map<Integer, Payment> DATA = new HashMap<>() ;

    static {
        for(int i = 0; i < 10; i++){
            DATA.put(i, new Payment(i, "Payment " + i, 100 * i));
        }
    }

    public Payment findPaymentById(int id){
        return DATA.get(id);
    }

    public List<Payment> findAllPayments(){
        return DATA.values().stream().toList();
    }
}
