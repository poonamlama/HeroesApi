package heroesapi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HeroesAPI {
    @FormUrlEncoded
    @POST("heroes")
    Call<Void>addHero(@Field("name")String name, @Field("desc") String desc);
}
