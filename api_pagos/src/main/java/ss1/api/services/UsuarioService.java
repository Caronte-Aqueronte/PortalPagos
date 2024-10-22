/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import java.time.LocalDateTime;
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
import ss1.api.models.request.EditarPasswordRequest;
import ss1.api.models.request.EditarPerfilRequest;
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
    @Autowired
    private SaldoService saldoService;

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

    public Usuario getUsuarioById(Usuario usuarioId) throws BadRequestException, NotFoundException {
        // Validar el modelo (esto asume que tienes un método para validar el modelo)
        validarId(usuarioId, "Id del usuario invalido");
        return this.usuarioRepository.findById(usuarioId.getId()).orElseThrow(() -> new NotFoundException("Usuario no encontrado."));
    }

    /**
     * Elimina el usuario autenticado del sistema, si y solo si no tiene saldo
     * disponible en su cuenta.
     *
     * Este método valida que el usuario esté autenticado y que el usuario a
     * eliminar sea el mismo usuario autenticado. Antes de proceder con la
     * eliminación, también verifica que el usuario no tenga saldo en su cuenta.
     *
     * Excepciones: - {@link BadRequestException}: Se lanza si hay un problema
     * con la solicitud. - {@link ConflictException}: Se lanza si el usuario
     * tiene saldo en su cuenta y no puede ser eliminado. -
     * {@link NotFoundException}: Se lanza si el usuario no es encontrado en el
     * sistema. - {@link UnauthorizedException}: Se lanza si el usuario
     * autenticado no tiene los permisos necesarios para realizar esta
     * operación.
     *
     * @param usuarioEliminar El objeto Usuario que contiene los datos del
     * usuario que se intenta eliminar.
     * @return Un mensaje que indica que el usuario ha sido eliminado
     * exitosamente.
     * @throws BadRequestException Si hay algún problema en la solicitud o datos
     * inválidos.
     * @throws ConflictException Si el usuario tiene saldo disponible en su
     * cuenta.
     * @throws NotFoundException Si el usuario a eliminar no existe.
     * @throws UnauthorizedException Si el usuario autenticado no tiene
     * autorización para realizar esta operación.
     */
    public String eliminarUsuario(Usuario usuarioEliminar) throws BadRequestException, ConflictException, NotFoundException, UnauthorizedException {
        // Obtener el usuario que se intenta eliminar
        Usuario usuario = this.getUsuarioById(usuarioEliminar);
        // Verificar que el usuario autenticado coincide con el usuario que se intenta eliminar
        this.verificarUsuarioJwt(usuario);
        return this.eliminarUsuarioAbstraccion(usuarioEliminar);
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

    public Usuario getMiUsuario(Long id) throws BadRequestException, ConflictException, NotFoundException, UnauthorizedException {
        //traer el usuario por id
        Usuario usuario = this.getUsuarioById(new Usuario(id));
        // Verificar que el usuario autenticado coincide con el usuario que se intenta eliminar
        this.verificarUsuarioJwt(usuario);
        return usuario;
    }

    public Usuario editarPassword(EditarPasswordRequest usuarioEditar) throws BadRequestException,
            NotFoundException, UnauthorizedException {
        //validamos el objeto
        this.validarModelo(usuarioEditar);
        //traer el usuario por id
        Usuario usuario = this.getUsuarioById(new Usuario(usuarioEditar.getId()));
        // Verificar que el usuario autenticado coincide con el usuario que se intenta eliminar
        this.verificarUsuarioJwt(usuario);
        //si tiene permisos entonces comparamos si la password que ingreso el cliente es la misma que esta en la bd
        boolean compararPassword = this.encriptador.compararPassword(usuarioEditar.getOldPassword(), usuario.getPassword());
        if (!compararPassword) {
            throw new UnauthorizedException("Password incorrecta.");
        }
        // Encriptar la contraseña
        usuario.setPassword(this.encriptador.encriptar(usuarioEditar.getNewPassword()));
        return this.usuarioRepository.save(usuario);
    }

    public Usuario editarPerfil(EditarPerfilRequest usuarioEditar) throws BadRequestException,
            NotFoundException, UnauthorizedException {
        //validamos el objeto
        this.validarModelo(usuarioEditar);
        //traer el usuario por id
        Usuario usuario = this.getUsuarioById(new Usuario(usuarioEditar.getId()));
        // Verificar que el usuario autenticado coincide con el usuario que se intenta eliminar
        this.verificarUsuarioJwt(usuario);
        //si tiene permisos entonces debemos editar la informacion

        usuario.setNit(usuarioEditar.getNit());
        usuario.setNombres(usuarioEditar.getNombres());
        usuario.setApellidos(usuarioEditar.getApellidos());

        return this.usuarioRepository.save(usuario);
    }

    private String eliminarUsuarioAbstraccion(Usuario usuarioEliminar)
            throws BadRequestException, ConflictException, NotFoundException, UnauthorizedException {

        //traer el usuario por id
        Usuario usuario = this.getUsuarioById(usuarioEliminar);

        //verificar que no tenga fondos el usuario
        if (this.saldoService.usuarioTieneSaldo(usuario) == true) {
            throw new ConflictException("El usuario tiene saldo disponible en su cuenta, no se puede eliminar.");
        }
        //eliminar el usuario
        usuario.setDeleted(true);
        usuario.setDeletedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);
        return "Usuario eliminado con exito.";
    }

    /**
     * Obtiene un objeto {@link Usuario} basado en el correo electrónico
     * extraído del token JWT.
     *
     * @return El usuario asociado con el correo electrónico extraído del JWT.
     * @throws NotFoundException Si ocurre un error al obtener el correo
     * electrónico del JWT o si el usuario no es encontrado.
     */
    public Usuario getUsuarioUseJwt() throws NotFoundException {
        String gmailUsuarioPorJwt = this.getEmaiJwt();
        return this.getByEmail(gmailUsuarioPorJwt);
    }

    /**
     * obtiene un usuario a partir de un correo electronico
     *
     * @param email
     * @return
     */
    public Usuario getByEmail(String email)
            throws BadRequestException, NotFoundException {
        Usuario usuario = usuarioRepository.findOneByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email no encontrado."));
        this.isDeleted(usuario);
        return usuario;
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }
}
