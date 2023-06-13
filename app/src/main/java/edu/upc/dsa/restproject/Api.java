package edu.upc.dsa.restproject;

import java.util.List;

import edu.upc.dsa.restproject.models.Abuse;
import edu.upc.dsa.restproject.models.Credentials;
import edu.upc.dsa.restproject.models.Game;
import edu.upc.dsa.restproject.models.Item;
import edu.upc.dsa.restproject.models.User;
import edu.upc.dsa.restproject.models.UserRegister;
import edu.upc.dsa.restproject.models.VOPlayerGameCredencials;
import edu.upc.dsa.restproject.models.idUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @POST("game/login")
    Call<idUser> login(@Body Credentials credentials);
    @POST("game/register")
    Call<UserRegister> register(@Body UserRegister user);
    @GET("game/shop")
    Call<List<Item>> getShop();
    @GET("game/getUser/{idUser}")
    Call<User> getUser(@Path("idUser") String idUser);
    @PUT("game/startGame")
    Call<Game> startGame(@Body VOPlayerGameCredencials credencials);
    @PUT("game/seeGames")
    Call<Game> seeGames(@Path("id") String id);
    @GET("game/{id}/partidas")
    Call<List<Game>> getPartidasPlayer(@Path("id") String id);
    @PUT("game/buyItems/{idItem}/{name}/{idUser}")
    Call<Void> buyItems(@Path("idItem") String idItem,@Path("name") String name, @Path("idUser") String idUser);
    @POST("game/abuse")
    Call<Void> postAbuse(@Body Abuse abuse);
}
