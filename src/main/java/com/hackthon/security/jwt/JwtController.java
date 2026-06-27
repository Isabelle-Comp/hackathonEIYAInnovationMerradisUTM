package com.hackthon.security.jwt;


import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(value = Constants.ENDPOINT_Auth)
@Slf4j
@Tag(name = "connection(JWT)")
public class JwtController {


    private final JwtUtils jwtUtils;
    private final CompteUtilisateurRepository compteUtilisateurRepository ;


    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
        try {
            System.out.println("Email reçu : " + jwtRequest.getEmail());
            CompteUtilisateur utilisateur = compteUtilisateurRepository.findByEmail(jwtRequest.getEmail()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouvé"));
            System.out.println(utilisateur.getId());
            Authentication authentication = logUser(jwtRequest.getEmail(), jwtRequest.getPassword());
            String jwt = jwtUtils.generateToken(authentication);
            System.out.println("salut");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + jwt);
            System.out.println(utilisateur.getId());

            Object principal = authentication.getPrincipal();//object a comprendre
            System.out.println(utilisateur.getId());

            return new ResponseEntity<>(new JwtResponse(principal, jwt, utilisateur ), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication logUser(String email, String password) {

        try {


            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            System.out.println(authentication.getName());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authentication;
        }
        catch (Exception e){
            System.out.println("Authentication failed: " + e.getMessage());
            e.printStackTrace(); // Affiche la trace de l'exception pour plus de détails
            return null;
        }

    }

}
