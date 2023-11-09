package ca.crit.hungryhamster.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import ca.crit.hungryhamster.GameHandler;

public class GameSounds {
    private final Music music;
    private static final Sound victory = Gdx.audio.newSound(Gdx.files.internal("Sounds/Effects/achievement.ogg"));;
    private static final Sound spell = Gdx.audio.newSound(Gdx.files.internal("Sounds/Effects/spell.ogg"));
    private static final Sound jump = Gdx.audio.newSound(Gdx.files.internal("Sounds/Effects/jump.mp3"));
    private static final Sound eat = Gdx.audio.newSound(Gdx.files.internal("Sounds/Effects/eat.mp3"));
    private static final Sound gamefinish = Gdx.audio.newSound(Gdx.files.internal("Sounds/Effects/gamefinish.mp3"));

    public GameSounds(){
        music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Music/happytown.ogg"));
        music.setVolume(GameHandler.musicVolume);
    }

    public void create(){
        music.setLooping(true);
        music.play();
    }

    public static void win() {
        long id = victory.play(GameHandler.musicVolume);
        victory.setPitch(id, 1);
        victory.setLooping(id, false);
    }

    public static void megaWin() {
        long id = gamefinish.play(GameHandler.musicVolume);
        gamefinish.setPitch(id, 1);
        gamefinish.setLooping(id, false);
    }

    public static void spell() {
        long id = spell.play(GameHandler.effectsVolume);
        spell.setPitch(id, 1);
        spell.setLooping(id, false);
    }

    public static void jump() {
        long id = jump.play(GameHandler.effectsVolume);
        jump.setPitch(id, 1);
        jump.setLooping(id, false);
    }

    public static void eat() {
        long id = eat.play(GameHandler.effectsVolume + 0.5f);
        eat.setPitch(id, 1);;
        eat.setLooping(id, false);
    }

    public void dispose(){
        music.dispose();
    }
}

