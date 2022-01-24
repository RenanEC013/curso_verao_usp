package br.usp.ime.verao.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemeController {

    @Autowired
    MemeRepository repository;

    @GetMapping("/memes")
    public List<Meme> getAllMemes() {
        return repository.findAll();
    }

    @GetMapping("/memes/{id}")
    public Meme getMemeById(@PathVariable Long id) {
        return repository.getById(id);
    }

    @PutMapping("/memes/{id}")
    Meme replaceMeme(@RequestBody Meme newMeme, @PathVariable Long id) {

        return repository.findById(id)
                .map(meme -> {
                    meme.setName(newMeme.getName());
                    meme.setKeywords(newMeme.getKeywords());
                    meme.setMidia(newMeme.getMidia());
                    return repository.save(meme);
                })
                .orElseGet(() -> {
                    newMeme.setId(id);
                    return repository.save(newMeme);
                });
    }

    @PostMapping("/memes")
    public Meme saveMeme(@RequestBody Meme meme) {
        return repository.save(meme);
    }

    @DeleteMapping("/memes/{id}")
    public void deleteMeme(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
