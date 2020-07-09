import java.io.IOException;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import org.apache.hc.core5.http.ParseException;

public class AuthRefresh {
    private static final String clientId = "cf11d71b136b4eadae60594486df773f";
    private static final String clientSecret = "58175bf56bdd460eaf4120cbd7101c78";
    private static final String refreshToken = "AQB2LKklgnc8H_DlHR9nG7muZuZ3KJL9b2jrqTJBTkWRa19QnpxiACPZwj3AtssVG0Qvz_w3-P2d-YTOPJ67t9hDdhU9YYvQNc-E-ft2WSmUxv0CPkVcSEbIPqa2NVzy8gc";
    private static final String accessToken = "BQDglNEWUOGV_cocwdQ3X10OGAzWJMTslWlD2q_eaPxxWy24_jb4MAVnY6RMann4Iw802_SEQHGXBaYr5aIYt69XOKJUkjpcDAuYUEXeyE8ii5IOM68jf4i_kSS4Jo3xTqd0f2Z533lDgnljOBsk";

    //Build spotApi instance
    private static final SpotifyApi spotApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRefreshToken(refreshToken)
            .setAccessToken(accessToken)
            .build();

    //Build authCodeRefreshRequest
    private static final AuthorizationCodeRefreshRequest authCodeRefreshRequest = spotApi.authorizationCodeRefresh()
            .build();


    public static void authCodeRefresh() {
        try {
            final AuthorizationCodeCredentials authCodeCredentials = authCodeRefreshRequest.execute();

            // Set access and refresh tokens for spotApi instance given authCodeRefresh results
            spotApi.setAccessToken(authCodeCredentials.getAccessToken());
            spotApi.setRefreshToken(authCodeCredentials.getRefreshToken());

        }
        //Multi-Exception Catch
        catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //CurrentlyPlaying type currentSong object
    private static final GetUsersCurrentlyPlayingTrackRequest currentSong = spotApi.getUsersCurrentlyPlayingTrack()
            .additionalTypes("track,episode")
            .build();


    public static void main(String[] args) {
        int maxCount = 10000;

        for(int i = 0; i < maxCount; i++) {
            try {
                String artistList = "";
                //Execute currentSong to type Track, iterate over array to convert to String
                Track track = (Track) currentSong.execute().getItem();
                ArtistSimplified[] artists = track.getArtists().clone();
                for(int j = 0; j < track.getArtists().length; j++) {
                    artistList = (artistList + artists[j].getName() + ", ");
                }
                System.out.println("Song Name: " + track.getName() + " " + "Artist Name: " + artistList + " " + "Album Name: " + track.getAlbum().getName());
            }
            catch (IOException | SpotifyWebApiException | ParseException e) {
                e.getMessage();
            }

            authCodeRefresh();
                //Sleep between requests
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
