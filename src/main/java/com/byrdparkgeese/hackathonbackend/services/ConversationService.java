package com.byrdparkgeese.hackathonbackend.services;

import com.byrdparkgeese.hackathonbackend.data.entities.ConversationsEntity;
import com.byrdparkgeese.hackathonbackend.data.entities.UsersEntity;
import com.byrdparkgeese.hackathonbackend.data.repositories.ConversationsRepository;
import com.byrdparkgeese.hackathonbackend.data.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationService {

    @Autowired
    AiService aiService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ConversationsRepository conversationsRepository;

    public UsersEntity loadOrCreateUserEntity(String phoneNumber) {
        UsersEntity user;

        Optional<UsersEntity> foundUser = usersRepository.findByPhoneNumber(phoneNumber);

        if (foundUser.isPresent()) {
            user = foundUser.get();
        } else {
            UsersEntity createdUser = new UsersEntity();
            createdUser.setPhoneNumber(phoneNumber);
            user = createdUser;
        }
        return user;
    }

    public ConversationsEntity loadOrCreateConversation(long userId, String status){
        Optional<ConversationsEntity> foundConvo = conversationsRepository.findByUserIdAndStatus(userId, status);

        return foundConvo.orElseGet(ConversationsEntity::new);
    }

    public void saveUser(UsersEntity usersEntity){
        usersRepository.save(usersEntity);
    }

}
