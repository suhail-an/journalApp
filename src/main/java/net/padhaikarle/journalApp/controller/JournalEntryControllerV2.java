package net.padhaikarle.journalApp.controller;

import net.padhaikarle.journalApp.entity.JournalEntry;
import net.padhaikarle.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> entry = journalEntryService.getEntryById(myId);
        if(entry.isPresent()){
            return new ResponseEntity <>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity <>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllJournalEntries() {
        List<JournalEntry> all = journalEntryService.getAll();
        if(all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/id")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.save(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
         } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry){
        Optional<JournalEntry> oldEntry = journalEntryService.getEntryById(id);
        if(oldEntry.isPresent()){
            if(updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()){
                oldEntry.get().setTitle(updatedEntry.getTitle());
            }
            if(updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty()){
                oldEntry.get().setContent(updatedEntry.getContent());
            }
            journalEntryService.save(oldEntry.get());
            return new ResponseEntity<>(oldEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
         journalEntryService.delete(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
