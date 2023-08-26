/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.service;

import br.com.siemens.configmodule.entidade.Usuario;
import br.com.siemens.configmodule.enumeracao.ValidacaoUsuario;
import br.com.siemens.configmodule.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * salva um usuario.
     *
     * @param usuario que sera salvo
     *
     * @return se não ocorrer erro na validacao de nenhum campo retorna uma
     *         lista vazia, senao ele retorna uma lista com todos os erros
     */
    @Override
    public List<ValidacaoUsuario> salvarUsuario(Usuario usuario) {
        List<ValidacaoUsuario> validacao = this.validarUsuarioNovo(usuario);
        if (validacao.isEmpty()) {
            this.criptografarSenhaSalvarUsuario(usuario);
        }
        return validacao;
    }

    /**
     * altera um usuario.
     *
     * @param usuario que sera salvo
     *
     * @return se não ocorrer erro na validacao de nenhum campo retorna uma
     *         lista vazia, senao ele retorna uma lista com todos os erros
     */
    @Override
    public List<ValidacaoUsuario> alterarUsuario(Usuario usuario) {
        List<ValidacaoUsuario> validacao = this.validarUsuarioExistenteSendoAlterado(usuario);
        if (validacao.isEmpty()) {
            this.usuarioRepository.save(usuario);
        }
        return validacao;
    }

    @Override
    public List<ValidacaoUsuario> alterarSenha(Usuario usuario) {
        List<ValidacaoUsuario> validacao = this.validarAlterarSenha(usuario);
        if (validacao.isEmpty()) {
            this.criptografarSenhaSalvarUsuario(usuario);
        }
        return validacao;
    }

    /**
     * pesquisa todos os usuarios.
     *
     * @return todos os usuarios do banco-de-dados
     */
    @Override
    public List<Usuario> pesquisarTodosUsuarios() {
        return this.usuarioRepository.findAll();
    }

    /**
     * exclui um usuario pelo id.
     *
     * @param id do usuario que sera excluido
     */
    @Override
    public void excluirUsuario(Integer id) {
        this.usuarioRepository.delete(id);
    }

    /**
     * pesquisa por uma lista de usuarios baseado em um campo especifico.
     * @param campoSendoPesquisado      campo utilizado na pesquisa
     * @param valorCampoSendoPesquisado valor do campo
     * @return lista com usuarios pesquisados
     */
    @Override
    public List<Usuario> pesquisarUsuarios(String campoSendoPesquisado, String valorCampoSendoPesquisado) {        
        return this.usuarioRepository.pesquisarUsuarioPorCampo(campoSendoPesquisado, valorCampoSendoPesquisado);
    }

    /**
     * O metodo valida os campos em comum que tem que ser validados tanto para um usuario novo que esta sendo
     * cadastrado, como para um usario que ja existe no sistema e esta sendo alterado.
     *
     * @param usuario que sera validado
     *
     * @return se não ocorrer erro na validacao de nenhum campo retorna uma
     *         lista vazia, senao ele retorna uma lista com todos os erros
     */
    private List<ValidacaoUsuario> validarUsuarioNovo(Usuario usuario) {
        List<ValidacaoUsuario> validacao = new ArrayList<>();
        String login = usuario.getLogin();
        String email = usuario.getEmail();
        if (this.usuarioRepository.findByLogin(login) != null) {
            validacao.add(ValidacaoUsuario.LOGIN_EXISTE);
        }
        if (this.usuarioRepository.findByEmail(email) != null) {
            validacao.add(ValidacaoUsuario.EMAIL_EXISTE);
        }
        if (this.senhaDiferenteConfirmaSenha(usuario)) {
            validacao.add(ValidacaoUsuario.SENHA_DIFERENTE_SENHA_CONFIRMACAO);
        }
        return validacao;
    }

    /**
     * Valida um usuario existente no sistema que esta sendo alterado.
     *
     * @param usuario que sera validado
     *
     * @return se não ocorrer erro na validacao de nenhum campo retorna uma
     *         lista vazia, senao ele retorna uma lista com todos os erros
     */
    private List<ValidacaoUsuario> validarUsuarioExistenteSendoAlterado(Usuario usuario) {
        List<ValidacaoUsuario> validacao = new ArrayList<>();
        Integer id = usuario.getId();
        String email = usuario.getEmail();
        String login = usuario.getLogin();
        if (this.usuarioRepository.findByLoginAndIdNot(login, id) != null) {
            validacao.add(ValidacaoUsuario.LOGIN_EXISTE);
        }
        if (this.usuarioRepository.findByEmailAndIdNot(email, id) != null) {
            validacao.add(ValidacaoUsuario.EMAIL_EXISTE);
        }
        return validacao;
    }

    private List<ValidacaoUsuario> validarAlterarSenha(Usuario usuario) {
        List<ValidacaoUsuario> validacao = new ArrayList<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Usuario usuarioRetornado = this.usuarioRepository.findOne(usuario.getId());
        if (!passwordEncoder.matches(usuario.getSenhaAntiga(), usuarioRetornado.getSenha())) {
            validacao.add(ValidacaoUsuario.SENHA_ANTIGA_INVALIDA);
        }
        if (this.senhaAntigaIqualSenhaNova(usuario)) {
            validacao.add(ValidacaoUsuario.SENHA_ANTIGA_IGUAL_NOVA);
        }
        if (this.senhaDiferenteConfirmaSenha(usuario)) {
            validacao.add(ValidacaoUsuario.SENHA_DIFERENTE_SENHA_CONFIRMACAO);
        }
        return validacao;
    }

    private void criptografarSenhaSalvarUsuario(Usuario usuario) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        this.usuarioRepository.save(usuario);
    }

    /**
     * Metodo que testa se a senha é diferente da senha de confirmacao.
     *
     * @param usuario que a senha sera validada
     *
     * @return true se as senhas sao diferentes
     */
    private boolean senhaDiferenteConfirmaSenha(Usuario usuario) {
        return !usuario.getSenha().equals(usuario.getConfirmaSenha());
    }

    private boolean senhaAntigaIqualSenhaNova(Usuario usuario) {
        return usuario.getSenha().equals(usuario.getSenhaAntiga());
    }

    @Override
    public Usuario pesquisarPorId(Integer id) {
        return this.usuarioRepository.findOne(id);
    }

    @Override
    public Usuario pesquisarPorLogin(String login) {
        return this.usuarioRepository.findByLogin(login);
    }
}
