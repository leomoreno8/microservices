package br.edu.utfpr.fraud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FraudCheckService {

    private final FraudCheckHistoryRepository repository;
    private final BrokerService brokerService;

    public boolean isFraudCustomer(Integer customerId) {
        log.info("Check if {} isFraudster", customerId);
        brokerService.send(repository.save(
                FraudCheckHistory.builder().
                        customerId(customerId).
                        isFraudster(false).
                        createdAt(LocalDateTime.now()).
                        build()
        ));
        return false;
    }
}
