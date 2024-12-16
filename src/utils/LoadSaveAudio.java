package utils;

public class LoadSaveAudio {

    public static final String FIRE_CLIP = "audio/fire.wav";
    public static final String LEVEL_INTO_CLIP = "audio/level-intro.wav";

    public static void LoadAudioClips(String fileName) {
        /*s
        InputStream inputStream = LoadSaveAudio.class.getResourceAsStream("/" + fileName);

        try {

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            AudioFormat audioFormat = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

         */
    }



}
