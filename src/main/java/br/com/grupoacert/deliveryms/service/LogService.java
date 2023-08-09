package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Log;
import br.com.grupoacert.deliveryms.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;


    public Log salvar(String msgErro) {

        Log log = Log.builder()
                .msgErro(msgErro)
                .dataErro(LocalDateTime.now())
                .build();

        return logRepository.save(log);

    }

}
