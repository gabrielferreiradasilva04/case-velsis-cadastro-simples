package com.backend.backend.service;

import com.backend.backend.domain.UserEntity;
import com.backend.backend.dto.UserRequestDTO;
import com.backend.backend.dto.UserResponseDTO;
import com.backend.backend.repository.UserRepository;
import com.backend.backend.util.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
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
        UserEntity userEntity = this.userMapper.requestDtoToEntity(dto);
        this.normalize(userEntity);
        return this.userMapper.entityToResponseDto(this.userRepository.save(userEntity));
    }

    public List<UserResponseDTO> getAll(){
        List<UserEntity> userList = this.userRepository.findAll();
        return userList.stream().map(this.userMapper::entityToResponseDto).toList();
    }

    public void update(UserRequestDTO dto, UUID userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

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
}
