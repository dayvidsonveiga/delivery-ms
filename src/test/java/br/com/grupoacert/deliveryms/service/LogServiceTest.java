package br.com.grupoacert.deliveryms.service;

import br.com.grupoacert.deliveryms.domain.Log;
import br.com.grupoacert.deliveryms.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LogServiceTest {

    @InjectMocks
    private LogService logService;

    @Mock
    private LogRepository logRepository;

    @Test
    public void SalvarComSucesso() {
        String msg = "Teste";
        LocalDateTime dataErro = LocalDateTime.now();
        Log log = Log.builder().id(1L).msgErro(msg).dataErro(dataErro).build();

        when(logRepository.save(any(Log.class))).thenReturn(log);

        Log retorno = logService.salvar(msg);

        verify(logRepository).save(any(Log.class));

        assertNotNull(retorno);
        assertEquals(msg, retorno.getMsgErro());
        assertEquals(dataErro, retorno.getDataErro());

    }

}