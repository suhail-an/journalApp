package net.padhaikarle.journalApp.service;

import net.padhaikarle.journalApp.entity.User;
import net.padhaikarle.journalApp.repository.JournalEntryRepository;
import net.padhaikarle.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private JournalEntryRepository journalEntryRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getEntryById(ObjectId id){
        return userRepository.findById(id);
    }

    public void delete(ObjectId Id){
        userRepository.deleteById(Id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
