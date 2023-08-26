/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.service;

import br.com.siemens.configmodule.entidade.Banco;
import br.com.siemens.configmodule.entidade.TipoBanco;
import br.com.siemens.configmodule.enumeracao.ValidacaoBanco;
import br.com.siemens.configmodule.repository.BancoRepository;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.mockito.AdditionalMatchers.not;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
public class BancoServiceImplTest {
    
    @Mock
    private BancoRepository bancoRepositoryMock;
    
    
    @Autowired
    @InjectMocks
    private BancoService bancoService;
    
    private final Banco bancoRetornado =  new Banco();
    
    public BancoServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.bancoRetornado.setId(1);
        this.bancoRetornado.setAlias("SCO");
        this.bancoRetornado.setUrl("jdbc:postgresql://localhost:5432/SCO");
        this.bancoRetornado.setTipoBanco(new TipoBanco());
        this.bancoRetornado.setUsuario("luciano");
        this.bancoRetornado.setSenha("12312345");
        MockitoAnnotations.initMocks(this);
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of alterarBanco method, of class BancoServiceImpl.
     */
    @Test
    public void testAlterarBanco() {
//        when(this.bancoRepositoryMock.findByAliasIgnoreCaseAndIdNot(Matchers.eq("SCO"), not(Matchers.eq( 1 )))).thenReturn(this.bancoRetornado);
//        when(this.bancoService.testarConexaoBancoFalha(new Banco())).thenReturn(true);
//        Banco banco =  new Banco();
//        banco.setId(1);
//        banco.setAlias("SCO");
//        List<ValidacaoBanco> validacao = this.bancoService.alterarBanco(banco);
//        assertEquals(0, validacao.size());
      
    }
}
