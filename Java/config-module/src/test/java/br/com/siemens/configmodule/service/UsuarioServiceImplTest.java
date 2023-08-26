/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.service;

import br.com.siemens.configmodule.entidade.Usuario;
import br.com.siemens.configmodule.enumeracao.ValidacaoUsuario;
import br.com.siemens.configmodule.repository.UsuarioRepository;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.not;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
public class UsuarioServiceImplTest {
    
    @Mock
    private UsuarioRepository usuarioRepositoryMock;
    
    @Autowired
    @InjectMocks
    private UsuarioService usuarioService;
    
    private final Usuario usuarioRetornado = new Usuario();
    
    public UsuarioServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.usuarioRetornado.setId(1);
        this.usuarioRetornado.setNome("Luciano Otavio");
        this.usuarioRetornado.setLogin("luciano");
        this.usuarioRetornado.setEmail("luciano@gmail.com");
        this.usuarioRetornado.setSenha("123123123");
        this.usuarioRetornado.setConfirmaSenha("123123123");
        this.usuarioRetornado.setSenhaAntiga("dfgdfgfdgdfgfdgdgdfg");
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSalvarUsuario() {
        when(this.usuarioRepositoryMock.findByLogin("luciano")).thenReturn(this.usuarioRetornado);
        when(this.usuarioRepositoryMock.findByEmail("luciano@gmail.com")).thenReturn(this.usuarioRetornado);
        Usuario usuario = new Usuario();
        usuario.setLogin("andre");
        usuario.setEmail("andre@gmail.com");
        usuario.setSenha("12312312");
        usuario.setConfirmaSenha("12312312");
        List<ValidacaoUsuario> validacao = this.usuarioService.salvarUsuario(usuario);
        assertEquals(0, validacao.size());

    }

    @Test
    public void testAlterarUsuario() {
        when(this.usuarioRepositoryMock.findByLoginAndIdNot(Matchers.eq( "luciano" ),not(Matchers.eq(1)))).thenReturn(this.usuarioRetornado);
        when(this.usuarioRepositoryMock.findByEmailAndIdNot(Matchers.eq( "luciano@gmail.com" ), not(Matchers.eq( 1 )))).thenReturn(this.usuarioRetornado);
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setLogin("luciano");
        usuario.setEmail("luciano@gmail.com");
        List<ValidacaoUsuario> validacao = this.usuarioService.alterarUsuario(usuario);
        assertEquals(0, validacao.size());
    }
    
    @Test
    public void testAlterarSenha() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setLogin("luciano");
        usuario.setEmail("luciano@gmail.com");
        usuario.setSenhaAntiga("123456789");
        usuario.setSenha("$2a$10$IbaEn5CqiNmEdzkaY4/5jeitKdMAr8K3WsKNdhaST9380QjtyFI/C");
        usuario.setConfirmaSenha("$2a$10$IbaEn5CqiNmEdzkaY4/5jeitKdMAr8K3WsKNdhaST9380QjtyFI/C");
        when(this.usuarioRepositoryMock.findOne(1)).thenReturn(usuario);      
        List<ValidacaoUsuario> validacao = this.usuarioService.alterarSenha(usuario);
        assertEquals(0, validacao.size());
    }


}
