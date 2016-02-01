package myfirstappwithkotlin.pajee.com.myfirstappwithkotlin.Deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import myfirstappwithkotlin.pajee.com.myfirstappwithkotlin.model.MovieElements
import java.lang.reflect.Type

class MovieDeserializer : JsonDeserializer<MovieElements> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): MovieElements? {
        //        throw UnsupportedOperationException()

        val jsonObj = json as JsonObject

        val movie = MovieElements()


        if(jsonObj.get("Response").asString.equals("True", true)) {
            movie.title = jsonObj.get("Title").asString
            movie.genre = jsonObj.get("Genre").asString
            movie.director = jsonObj.get("Director").asString
            movie.writer = jsonObj.get("Writer").asString
            movie.plot = jsonObj.get("Plot").asString
            movie.actor = jsonObj.get("Actors").asString
            movie.country = jsonObj.get("Country").asString
            movie.awards = jsonObj.get("Awards").asString
            movie.poster = jsonObj.get("Poster").asString
            movie.response = jsonObj.get("Response").asString
        } else {
            movie.response = jsonObj.get("Response").asString
            movie.error = jsonObj.get("Error").asString
        }

        return movie

    }

}
