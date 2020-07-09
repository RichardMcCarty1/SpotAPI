import java.io.IOException;
import java.net.URI;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.apache.hc.core5.http.ParseException;



public class AccessRefreshToken {
    //Create variables for API instance
    private static final String clientId = "cf11d71b136b4eadae60594486df773f";
    private static final String clientSecret = "58175bf56bdd460eaf4120cbd7101c78";
    //Dummy URI
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/callback/");
    private static final String code = "AQCpQE-GfwHCKMb2qPxjrgSoi6rX2-lZjLcYvYaF829cC-Ce9NJxmJ13se-qlPlNWMbUkKtQWoxM6VFbdcPEJk2YngKyo0wA1ROY4FOb_dI1DfFcVQJvzXXmw8tOSkf2IlIrVkAlT7GuPJ3_klqkw8IHyB7APfJQcETDAuCZD8mQwCMN5_0YZ0wFBdojcka9Nd2hQr8--nY6K9gOEwMppQ";

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
            System.out.println(spotApi.getAccessToken());
            System.out.println(spotApi.getRefreshToken());


            //Multi-Exception Catch
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        authorizationCode();
    }

}
