package edu.upc.dsa.restproject;

import java.util.List;
import edu.upc.dsa.restproject.models.Credentials;
import edu.upc.dsa.restproject.models.Game;
import edu.upc.dsa.restproject.models.User;
import edu.upc.dsa.restproject.models.VOPlayerGameCredencials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @POST("game/login")
    Call<User> login(@Body Credentials credentials);

    @POST("game/register")
    Call<User> register(@Body User user);

    @PUT("game/startGame")
    Call<Game> startGame(@Body VOPlayerGameCredencials credencials);

    @PUT("game/seeGames")
    Call<Game> seeGames(@Path("id") String id);

    @GET("player/{id}/partidas")
    Call<List<Game>> getPartidasPlayer(@Path("id") String id);
}
