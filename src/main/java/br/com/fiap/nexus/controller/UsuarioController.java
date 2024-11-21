package br.com.fiap.nexus.controller;

import br.com.fiap.nexus.entity.Email;
import br.com.fiap.nexus.entity.Usuario;
import br.com.fiap.nexus.repository.UsuarioRepository;
import br.com.fiap.nexus.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService; // Injeção do EmailService

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "form";  // Verifique se este template está correto
    }

    @GetMapping("/req/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "signup";  // Atualizado para o caminho correto
    }

    @PostMapping("/req/signup")
    public String registerUser(Usuario usuario) {
        // Verifica se o usuário já existe
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            return "redirect:/req/signup?error=usuarioExistente";
        }

        // Criptografa a senha antes de salvar
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioRepository.save(usuario);

            // Cria e envia o email de boas-vindas
            Email emailModel = new Email();
            emailModel.setOwnerRef(usuario.getUsername());
            emailModel.setEmailFrom("seu_email"); // Altere para seu email
            emailModel.setEmailTo(usuario.getEmail()); // Adicione o campo de email na classe Usuario
            emailModel.setSubject("Bem-vindo!");
            emailModel.setText("Obrigado por se registrar em nosso site!");
            emailService.sendEmail(emailModel); // Envia o email
        } else {
            return "redirect:/req/signup?error=senhaObrigatoria";
        }

        return "redirect:/req/login";
    }

    @PostMapping("/req/login")
    public String loginUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home"; // Redireciona para a página home após login
        } catch (Exception e) {
            return "redirect:/req/login?error=loginInvalido"; // Redireciona em caso de erro
        }
    }

    @GetMapping("/req/login")
    public String showLoginForm() {
        return "login";  // Atualizado para o caminho correto
    }

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        return "home"; // Certifique-se de que o caminho para home.html está correto
    }
}
