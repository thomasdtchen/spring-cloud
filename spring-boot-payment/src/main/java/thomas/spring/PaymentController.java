package thomas.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    @Autowired
    public PaymentController(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
    @GetMapping("/get/{id}")
    public Mono<Payment> getPayment(@PathVariable int id){
        return Mono.just(this.paymentRepository.findPaymentById(id));
    }

    @GetMapping("/lb")
    public Flux<Payment> lbPayment(){
        return Flux.fromIterable(this.paymentRepository.findAllPayments());
    }
}
