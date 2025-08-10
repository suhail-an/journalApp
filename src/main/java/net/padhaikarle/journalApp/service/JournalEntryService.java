package net.padhaikarle.journalApp.service;

import net.padhaikarle.journalApp.entity.JournalEntry;
import net.padhaikarle.journalApp.entity.User;
import net.padhaikarle.journalApp.repository.JournalEntryRepository;
import net.padhaikarle.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void save(JournalEntry journalEntry, String username){
        try{
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            List<JournalEntry> existingEntries = user.getJournalEntries();
            if (existingEntries == null) {
                existingEntries = new ArrayList<>();
            }
            existingEntries.add(saved);
            user.setJournalEntries(existingEntries);
            userService.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void delete(ObjectId Id, String username){
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        List<JournalEntry> existingEntries = user.getJournalEntries();
        if (existingEntries != null) {
            existingEntries.removeIf(entry -> entry.getId().equals(Id));
            user.setJournalEntries(existingEntries);
            userService.save(user);
        }
        journalEntryRepository.deleteById(Id);
    }

}


//controller-> service -> repository