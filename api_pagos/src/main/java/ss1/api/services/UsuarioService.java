/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ss1.api.excepciones.BadRequestException;
import ss1.api.excepciones.ConflictException;
import ss1.api.excepciones.NotFoundException;
import ss1.api.excepciones.UnauthorizedException;
import ss1.api.models.Rol;
import ss1.api.models.dto.LoginDTO;
import ss1.api.models.Usuario;
import ss1.api.models.request.LoginRequest;
import ss1.api.repositories.RolRepository;
import ss1.api.repositories.UsuarioRepository;
import ss1.api.services.auth.AuthenticationService;
import ss1.api.services.auth.JwtGeneratorService;
import ss1.api.tools.Encriptador;

/**
 * La clase {@code UsuarioService} proporciona servicios relacionados con la
 * gestión de usuarios en el sistema. Incluye operaciones como la autenticación
 * de usuarios y la creación de nuevos usuarios.
 *
 * @author luid
 */
@Service
public class UsuarioService extends ss1.api.services.Service {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtGeneratorService jwtGenerator;
    @Autowired
    private Encriptador encriptador;
    @Autowired
    private RolRepository rolRepository;

    /**
     * Inicia la sesión de un usuario autenticándolo con su email y contraseña.
     *
     * Este método valida el modelo de la solicitud de inicio de sesión
     * ({@link LoginRequest}), verifica que el usuario con el email
     * proporcionado exista en el sistema, comprueba si el usuario ha sido
     * eliminado, autentica al usuario y genera un token JWT si la autenticación
     * es exitosa.
     *
     * Si el usuario no existe, se lanza una excepción de tipo
     * {@link NotFoundException}. Si las credenciales son incorrectas, se lanza
     * una excepción de tipo {@link UnauthorizedException}. Si los datos de la
     * solicitud no son válidos, se lanza una excepción de tipo
     * {@link BadRequestException}.
     *
     * @param log El objeto {@link LoginRequest} que contiene las credenciales
     * del usuario (email y contraseña).
     * @return Un objeto {@link LoginDTO} que contiene los datos del usuario
     * autenticado y el token JWT generado.
     * @throws BadRequestException Si los datos del usuario no cumplen con los
     * requisitos de validación.
     * @throws NotFoundException Si no se encuentra un usuario con el email
     * proporcionado.
     * @throws UnauthorizedException Si las credenciales del usuario son
     * incorrectas o la autenticación falla.
     */
    public LoginDTO iniciarSesion(LoginRequest log) throws BadRequestException, NotFoundException, UnauthorizedException {
        try {
            // Validar el modelo de la solicitud de inicio de sesión
            this.validarModelo(log);

            // Verificar si el usuario existe en la base de datos
            Usuario usuario = usuarioRepository.findOneByEmail(log.getEmail()).orElseThrow(
                    () -> new NotFoundException("Email no encontrado."));

            // Verificar si el usuario ha sido eliminado
            this.isDeleted(usuario);

            // Autenticar al usuario utilizando su email y contraseña encriptada
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(log.getEmail(), log.getPassword()));

            // Cargar el usuario por el email para obtener detalles adicionales
            UserDetails userDetails = authenticationService.loadUserByUsername(log.getEmail());

            // Generar el token JWT para el usuario autenticado
            String jwt = jwtGenerator.generateToken(userDetails);

            return new LoginDTO(usuario, jwt);
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException(ex.getMessage());
        }
    }

    /**
     * Crea un nuevo usuario en el sistema después de validar los datos
     * proporcionados.
     *
     * Este método realiza las siguientes acciones:
     * <ul>
     * <li>Valida el modelo del usuario utilizando el método
     * {@code validarModelo}.</li>
     * <li>Busca el rol "CLIENTE" en la base de datos, lanzando una excepción
     * {@link NotFoundException} si el rol no existe.</li>
     * <li>Guarda el usuario en la base de datos llamando al método
     * {@code guardarUsuario}.</li>
     * </ul>
     *
     * Si los datos del usuario no cumplen con los requisitos de validación, se
     * lanza una excepción de tipo {@link BadRequestException}. Si el rol
     * "CLIENTE" no se encuentra en la base de datos, se lanza una excepción de
     * tipo {@link NotFoundException}. Si todo es correcto, el usuario es
     * guardado en la base de datos y el objeto {@link Usuario} creado se
     * retorna.
     *
     * @param usuario El objeto {@link Usuario} que contiene los datos del
     * usuario a crear.
     * @return El objeto {@link Usuario} que fue creado y guardado en la base de
     * datos.
     * @throws BadRequestException Si los datos del usuario no cumplen con los
     * requisitos de validación.
     * @throws ConflictException Si ya existe un usuario con el mismo NIT o
     * email en el sistema (en caso de validaciones adicionales).
     * @throws NotFoundException Si no se encuentra el rol "CLIENTE" en la base
     * de datos.
     */
    public LoginDTO crearUsuario(Usuario usuario) throws BadRequestException, ConflictException, NotFoundException {
        // Validar el modelo (esto asume que tienes un método para validar el modelo)
        validarModelo(usuario);
        Rol rol = this.rolRepository.findOneByNombre("CLIENTE").orElseThrow(
                () -> new NotFoundException("Rol 'CLIENTE' no encontrado."));
        usuario.setRol(rol);
        //guardamos el usuario
        Usuario usuarioCreado = this.guardarUsuario(usuario);
        // Generar el JWT para el usuario creado
        UserDetails userDetails = authenticationService.loadUserByUsername(usuario.getEmail());
        String jwt = jwtGenerator.generateToken(userDetails);
        return new LoginDTO(usuarioCreado, jwt);
    }

    /**
     * Crea un nuevo usuario en el sistema después de validar los datos
     * proporcionados.
     *
     * Este método primero valida el modelo del usuario y luego verifica si ya
     * existe un usuario con el mismo NIT o email. Si se detecta un conflicto
     * (es decir, si el NIT o el email ya están registrados en el sistema), se
     * lanza una excepción de tipo {@link ConflictException}. Si el modelo del
     * usuario es inválido, se lanza una excepción de tipo
     * {@link BadRequestException}. En caso de que todo esté correcto, el
     * usuario es guardado en la base de datos y se retorna el objeto
     * {@link Usuario} creado.
     *
     * @param usuario El objeto {@link Usuario} que contiene los datos del
     * usuario a crear.
     * @return El objeto {@link Usuario} que fue creado y guardado en la base de
     * datos.
     * @throws BadRequestException Si los datos del usuario no cumplen con los
     * requisitos de validación.
     * @throws ConflictException Si ya existe un usuario con el mismo NIT o
     * email en el sistema.
     */
    @Transactional(rollbackOn = Exception.class)
    private Usuario guardarUsuario(Usuario usuario) throws BadRequestException, ConflictException {
        // Verificar si ya existe un usuario con el mismo NIT
        if (usuarioRepository.findOneByNit(usuario.getNit()).isPresent()) {
            throw new ConflictException("El NIT ya está en uso");
        }
        // Verificar si ya existe un usuario con el mismo email
        if (usuarioRepository.findOneByEmail(usuario.getEmail()).isPresent()) {
            throw new ConflictException("El email ya está en uso");
        }
        // Encriptar la contraseña
        usuario.setPassword(this.encriptador.encriptar(usuario.getPassword()));
        // Guardar el usuario
        return this.usuarioRepository.save(usuario);
    }
}
