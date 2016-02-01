package myfirstappwithkotlin.pajee.com.myfirstappwithkotlin

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.io.InputStream

class NetworkClient {

    fun get(url: String): InputStream {
        val request = Request.Builder().url(url).build()
        val response = OkHttpClient().newCall(request).execute()
        val body = response.body()
        return body.byteStream()
    }


}