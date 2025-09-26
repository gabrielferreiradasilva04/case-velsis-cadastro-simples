package com.backend.backend.service;

import com.backend.backend.domain.UserEntity;
import com.backend.backend.dto.UserRequestDTO;
import com.backend.backend.dto.UserResponseDTO;
import com.backend.backend.repository.UserRepository;
import com.backend.backend.util.exception.DuplicateDocumentException;
import com.backend.backend.util.exception.InvalidDocumentException;
import com.backend.backend.util.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO save(UserRequestDTO dto){
        if (!validarDocumento(dto.document())) {
            throw new InvalidDocumentException("Documento inválido");
        }
        boolean exists = userRepository.existsByDocument(dto.document());
        if (exists) {
            throw new DuplicateDocumentException("Documento já cadastrado");
        }

        UserEntity userEntity = this.userMapper.requestDtoToEntity(dto);
        this.normalize(userEntity);
        return this.userMapper.entityToResponseDto(this.userRepository.save(userEntity));
    }

    public Page<UserResponseDTO> getAll(Pageable pageable, String searchTerm){
        if(searchTerm == null || searchTerm.isEmpty()){
            return this.userRepository.findAll(pageable).map(this.userMapper::entityToResponseDto);
        }
        return this.userRepository
                .findByNameContainingIgnoreCase(searchTerm, pageable)
                .map(this.userMapper::entityToResponseDto);

    }

    public void update(UserRequestDTO dto, UUID userId){
        if (!validarDocumento(dto.document())) {
            throw new InvalidDocumentException("Documento inválido");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        //Evita duplicidade de documentos
        if(!user.getDocument().equals(dto.document())){
            if (userRepository.existsByDocument(dto.document())) {
                throw new DuplicateDocumentException("Documento já cadastrado");
            }
        }

        user.setName(dto.name());
        user.setDocument(dto.document());
        user.setAddressNumber(dto.addressNumber());
        user.setAddressLine(dto.addressLine());
        user.setCity(dto.city());
        user.setState(dto.state());
        user.setZip(dto.zip());

        this.userRepository.save(user);
    }
    public void delete(UUID userId){
        Optional<UserEntity> userOptional = this.userRepository.findById(userId);

        if(userOptional.isEmpty()) throw  new EntityNotFoundException("Usuário não encontrado");

        this.userRepository.delete(userOptional.get());
    }

    /***
     * Metodo destinado a normalização dos dados do usuario recebidos nas requisicoes
     * @param user usuario recebido da requisicao
     */
    private void normalize(UserEntity user){
        user.setName(user.getName().trim());
        user.setDocument(user.getDocument().trim());
        user.setAddressLine(user.getAddressLine().trim());
        user.setCity(user.getCity().trim());
        user.setState(user.getState().trim().toUpperCase());
        user.setZip(user.getZip().trim());
    }

    public boolean validarCPF(String cpf) {
        //verifica se não é nulo
        if (cpf == null) return false;

        //Remove tudo o que não for número do CPF
        cpf = cpf.replaceAll("[^\\d]", "");

        //Deve conter ao menos 11 caracteres
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) return false;

        //Inicia o algoritmo de validacao padrão com os dígitos verificadores
        try {
            //primeiro digito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstVerifier = sum % 11 < 2 ? 0 : 11 - (sum % 11);

            //segundo digito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondVerifier = sum % 11 < 2 ? 0 : 11 - (sum % 11);

            //compara os verificadores verdadeiros com os que foram passados
            return firstVerifier == Character.getNumericValue(cpf.charAt(9))
                    && secondVerifier == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validarRG(String rg) {
        if (rg == null) return false;
        //apenas remove tudo o que não for numerico
        rg = rg.replaceAll("[^\\d]", "");
        return rg.length() >= 5 && rg.length() <= 12;
    }

    //validar documento genérico
    public boolean validarDocumento(String documento) {
        if (documento == null) return false;

        String numeros = documento.replaceAll("[^\\d]", "");

        if (numeros.length() == 11) {
            return validarCPF(documento);
        } else if (numeros.length() >= 5 && numeros.length() <= 12) {
            return validarRG(documento);
        } else {
            return false;
        }
    }
}
