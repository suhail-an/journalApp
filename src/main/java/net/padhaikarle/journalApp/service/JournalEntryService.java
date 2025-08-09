package net.padhaikarle.journalApp.service;

import net.padhaikarle.journalApp.entity.JournalEntry;
import net.padhaikarle.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void save(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntryById(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }

    public void delete(ObjectId Id){
        journalEntryRepository.deleteById(Id);
    }

}


//controller-> service -> repository