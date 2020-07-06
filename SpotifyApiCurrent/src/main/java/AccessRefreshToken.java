import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URI;

public class AccessRefreshToken {
    //Create variables for API instance
    private static final String clientId = "cf11d71b136b4eadae60594486df773f";
    private static final String clientSecret = "58175bf56bdd460eaf4120cbd7101c78";
    //Dummy URI
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/callback/");
    private static final String code = "AQBQqWOWtn2Wo7nIKQRLZEfWUYjzeC30QbMC7y31oJvhANBD8QpsRZGSTcS1fLRT2DIzySf7E9eLKJtBjWIatCn7X_bPq117IyU8YWEqH0MjEnOJM6lXk45cgsFQZ69WfLEK2n0N65k5hlSaqKSa9cYJkKQtSJzKI59ImyaayQG_x3h88aAMPo02kNQ2Q70xWjQYpBzGnOOANLo26GiB_A";

    //Build spotAPI instance with initialized variables
    private static final SpotifyApi spotApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    //Build authCodeRequest with code pulled from AuthCodeUri
    private static final AuthorizationCodeRequest authCodeRequest = spotApi.authorizationCode(code)
            .build();

    public static void authorizationCode() {
        try {
            //Object assigned data from authCodeRequest
            final AuthorizationCodeCredentials authCodeCredentials = authCodeRequest.execute();

            // Set access and refresh tokens for spotApi instance given authCodeCredentials results
            spotApi.setAccessToken(authCodeCredentials.getAccessToken());
            spotApi.setRefreshToken(authCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authCodeCredentials.getExpiresIn());
            //Multi-Exception Catch
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        authorizationCode();
    }

}
