package net.padhaikarle.journalApp.controller;

import net.padhaikarle.journalApp.entity.JournalEntry;
import net.padhaikarle.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.getEntryById(myId);
    }

    @GetMapping("/all")
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryService.getAll();
    }

    @PostMapping("/id")
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.save(myEntry);
        return myEntry;
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry){
        JournalEntry oldEntry = journalEntryService.getEntryById(id);
        if(oldEntry != null){
            if(updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()){
                oldEntry.setTitle(updatedEntry.getTitle());
            }
            if(updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty()){
                oldEntry.setContent(updatedEntry.getContent());
            }
            journalEntryService.save(oldEntry);
        }
        return oldEntry;
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteEntry(@PathVariable ObjectId myId){
         journalEntryService.delete(myId);
         return true;
    }

}
