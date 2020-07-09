import java.io.IOException;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.IPlaylistItem;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import org.apache.hc.core5.http.ParseException;

public class AuthRefresh {
    private static final String clientId = "xxxxxxxxxxxxxxxxxxxxxxx";
    private static final String clientSecret = "zzzzzzzzzzzzzzzzzzzzzzzz";
    private static final String refreshToken = "aaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static final String accessToken = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbb";

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
        IPlaylistItem curSong;

        for(int i = 0; i < maxCount; i++) {

            try {
                //Returns context of played song, i.e. artist or playlist page
                System.out.println(currentSong.execute().getContext().getHref());
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                e.getMessage();
            }

            authCodeRefresh();

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
