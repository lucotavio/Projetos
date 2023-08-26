/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.repository;

import br.com.siemens.configmodule.entidade.Usuario;
import br.com.siemens.dbunit.DbUnitHelper;
import br.com.siemens.dbunit.DriverClass;
import java.util.List;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
public class UsuarioRepositoryTest{
    
    @Autowired
    private UsuarioRepository UsuarioRepository;
 
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        DbUnitHelper dbUnitHelper = new DbUnitHelper("DbUnit", "Usuario.xml", DriverClass.ORACLE_6, 
                "jdbc:oracle:thin:@192.168.3.4:1521/desenv", "CONFIG_MODULE_TESTE", "NgY0523", 
                new OracleDataTypeFactory(), "CONFIG_MODULE_TESTE");
        dbUnitHelper.excluirTodos();
        dbUnitHelper.inserir();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFindByLoginAndSenha() {
        Usuario usuario = this.UsuarioRepository.findByLoginAndSenha("luciano.otavio.oliveira","12312345");
        assertNotNull(usuario);
    }

    @Test
    public void testFindByLogin() {
        Usuario usuario = this.UsuarioRepository.findByLogin("ricardo");
        assertNotNull(usuario);
  
    }

    @Test
    public void testFindByLoginAndIdNot() {
        Usuario usuario = this.UsuarioRepository.findByLoginAndIdNot("paulo", 6);
        assertNotNull(usuario);
    }

    @Test
    public void testFindByEmail() {
        Usuario usuario = this.UsuarioRepository.findByEmail("marta@gmail.com");
        assertNotNull(usuario);
    }

    @Test
    public void testFindByEmailAndIdNot() {
        Usuario usuario = this.UsuarioRepository.findByEmailAndIdNot("marta@gmail.com", 6);
        assertNotNull(usuario);
    }

}
