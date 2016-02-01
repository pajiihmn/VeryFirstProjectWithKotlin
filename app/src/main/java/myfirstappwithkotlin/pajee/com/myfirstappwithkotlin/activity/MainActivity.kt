package myfirstappwithkotlin.pajee.com.myfirstappwithkotlin.activity

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import myfirstappwithkotlin.pajee.com.myfirstappwithkotlin.Deserializer.MovieDeserializer
import myfirstappwithkotlin.pajee.com.myfirstappwithkotlin.NetworkClient
import myfirstappwithkotlin.pajee.com.myfirstappwithkotlin.R
import myfirstappwithkotlin.pajee.com.myfirstappwithkotlin.model.MovieElements
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : Activity() {

    //    val titleTv : TextView = null!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = applicationContext

        val titleTv = findViewById(R.id.title_tv) as TextView
        val genreTv = findViewById(R.id.genre_tv) as TextView
        val directorTv = findViewById(R.id.director_tv) as TextView
        val writerTv = findViewById(R.id.writer_tv) as TextView
        val plotTv = findViewById(R.id.plot_tv) as TextView
        val actorTv = findViewById(R.id.actor_tv) as TextView
        val countryTv = findViewById(R.id.country_tv) as TextView
        val awardsTv = findViewById(R.id.awards_tv) as TextView
        val posterImv = findViewById(R.id.poster_img) as ImageView

        val edt = findViewById(R.id.enter_title_edt) as EditText
        val btn = findViewById(R.id.submit_btn) as Button
        val resultLayout = findViewById(R.id.result_layout) as LinearLayout

        btn.setOnClickListener({
            val title = edt.text.toString()
            GetJsonWithOkHttpClient(context, titleTv, genreTv, directorTv, writerTv, plotTv, actorTv, countryTv, awardsTv, posterImv, resultLayout, title).execute()
        })

    }

    class GetJsonWithOkHttpClient(context: Context, titleTv: TextView, genreTv: TextView, directorTv: TextView, writerTv: TextView,
                                       plotTv: TextView, actorTv: TextView, countryTv: TextView, awardsTv: TextView,
                                       posterImv: ImageView, resultLayout: LinearLayout, title: String) : AsyncTask<Void, Void, String>() {

        val context = context
        val mInnerTitleTv = titleTv
        val mInnerGenreTv = genreTv
        val mInnerDirectorTv = directorTv
        val mInnerWriterTv = writerTv
        val mInnerPlotTv = plotTv
        val mInnerActorTv = actorTv
        val mInnerCountryTv = countryTv
        val mInnerAwardsTv = awardsTv
        val mInnerPoserImv = posterImv
        val mInnerResultLayout = resultLayout

        val mInnerTitle = title

        override fun doInBackground(vararg p0: Void?): String? {
            val networkClient = NetworkClient()
            val stream = BufferedInputStream(
                    networkClient.get("http://www.omdbapi.com/?t=$mInnerTitle&y=&plot=short&r=json"))
            return readStream(stream);
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if(result != null) {
                val gsonBuilder = GsonBuilder().serializeNulls();
                gsonBuilder.registerTypeAdapter(MovieElements::class.java, MovieDeserializer());
                val gson = gsonBuilder.create();
                val movie = gson.fromJson(result, MovieElements::class.java)

                if(movie.response.equals("True", true)) {

                    mInnerResultLayout.visibility = View.VISIBLE

                    mInnerTitleTv.text = movie.title
                    mInnerGenreTv.text = movie.genre
                    mInnerDirectorTv.text = movie.director
                    mInnerWriterTv.text = movie.writer
                    mInnerPlotTv.text = movie.plot
                    mInnerActorTv.text = movie.actor
                    mInnerCountryTv.text = movie.country
                    mInnerAwardsTv.text = movie.awards

                    Glide.with(context).load(movie.poster).into(mInnerPoserImv)
                } else {
                    Toast.makeText(context, movie.error, Toast.LENGTH_SHORT).show()
                }

            } else {

            }

        }

        fun readStream(inputStream: BufferedInputStream): String {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream));
            val stringBuilder = StringBuilder();
            bufferedReader.forEachLine { stringBuilder.append(it) }
            return stringBuilder.toString()
        }

    }


}
