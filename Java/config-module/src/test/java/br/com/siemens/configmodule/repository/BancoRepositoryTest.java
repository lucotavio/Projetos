/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.repository;

import br.com.siemens.configmodule.entidade.Banco;
import br.com.siemens.dbunit.DbUnitHelper;
import br.com.siemens.dbunit.DriverClass;
import java.util.List;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
public class BancoRepositoryTest {
    
    @Autowired
    private BancoRepository bancoRepository;
    
    public BancoRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        DbUnitHelper dbUnitHelperTipoBanco = new DbUnitHelper("DbUnit", "Banco.xml", DriverClass.ORACLE_6, 
                "jdbc:oracle:thin:@192.168.3.4:1521/desenv", "CONFIG_MODULE_TESTE", "NgY0523", 
                new PostgresqlDataTypeFactory(), "CONFIG_MODULE_TESTE");
        dbUnitHelperTipoBanco.excluirTodos();
        dbUnitHelperTipoBanco.inserir();
        
       
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFindByAlias() {
        Banco banco = this.bancoRepository.findByAliasIgnoreCase("fRAuDE");
        assertNotNull(banco);
    }

    @Test
    public void testFindByAliasAndIdNot() {
       Banco banco = this.bancoRepository.findByAliasIgnoreCaseAndIdNot("SCO_TESTE", 3);
       assertNotNull(banco);
    }

    @Test
    public void testFindByAliasContainingIgnoreCase() {
        List<Banco> listabanco = this.bancoRepository.findByAliasContainingIgnoreCase("TeSTe");
        assertEquals(3, listabanco.size());
    }


    
}
