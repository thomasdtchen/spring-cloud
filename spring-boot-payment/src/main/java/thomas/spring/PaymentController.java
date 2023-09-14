package thomas.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    private final PaymentRepository paymentRepository;
    @Autowired
    public PaymentController(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Payment getPayment(@PathVariable int id){
        log.info("== getPayment ==");
        return this.paymentRepository.findPaymentById(id);
    }

    @GetMapping("/lb")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Payment> lbPayment(){
        return this.paymentRepository.findAllPayments();
    }
}
