import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

public class AuthCodeUri {
    //Create variables for API instance
        private static final String clientId = "cf11d71b136b4eadae60594486df773f";
        private static final String clientSecret = "58175bf56bdd460eaf4120cbd7101c78";
        //Dummy URI
        private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/callback/");

        //Build spotAPI instance with initialized variables
        private static final SpotifyApi spotApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();

        //Build authUriRequest with declared scope & spotApi instance for clientID and clientSecret
        private static final AuthorizationCodeUriRequest authUriRequest = spotApi.authorizationCodeUri()
                .scope("user-read-currently-playing")
                .build();

        //Pulls URI Response from authUriRequest
        public static void authorizationCodeUri() {
            final URI uriResponse = authUriRequest.execute();

            System.out.println("URI: " + uriResponse.toString());
        }

        public static void main(String[] args) {
            authorizationCodeUri();
        }
    }

