package com.mini

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

fun main() {
    //Netty : http engine一个库
    //application module是下面的函数 直接reference上来
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
        })
    }
    // TODO: adding the routing configuration here
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/feed") {
            val jsonString : String = this::class.java.classLoader.getResource("feed.json").readText()
            call.respondText(jsonString)
        }

        get("/playlists") {
            val jsonString : String = this::class.java.classLoader.getResource("playlists.json").readText()
            call.respondText(jsonString)
        }

//        get("/playlists/{id}") {
//            val id  = call.parameters["id"]
////          val jsonString : String? = this::class.java.classLoader.getResource("playlists.json")?.readText()
//
//            val jsonString = this::class.java.classLoader.getResource("playlists.json")?.readText().jsonString?.let{it. String
//                val playlists  = Json.decodeFromString(ListSerializer(Playlist.serializer()), it)
//                val playlist  = playlists.firstOrNull() { p ->p.id.toString() == id}
//                call.respondNullable(playlist)
//            }  ?: call.respond("null")

            get("/playlist/{id}") {
                this::class.java.classLoader.getResource("playlists.json")?.readText()?.let { jsonString ->
                    val playlists = Json.decodeFromString(ListSerializer(Playlist.serializer()), jsonString)
                    val id = call.parameters["id"]
                    val item = playlists.firstOrNull { it.id.toString() == id }
                    call.respondNullable(item)
                } ?: call.respond("null")
            }

            // http://0.0.0.0:8080/songs/solo.mp3
            static("/") {
                staticBasePackage = "static"
                static("songs") {
                    resources("songs")
                }
            }
//            jsonString?.let {   } 跟 if(jsonString != null){} 一样 但前者return
//            val playlists : List<Playlist> = Json.decodeFromString(ListSerializer(Playlist.serializer()), jsonString)

//
//            for (playlist: Playlist in playlists){
//                if (playlist.id.toString() == id){
//                    call.respond(playlist)
//                }
//            }
            //上面是java形式 下面是kotlin 不过都一样能实现

//            playlists.firstOrNull{
//                it.id.toString() == id
//            }?.let{call.respond(it)}

//            playlist?.let{ call.respond(it) }?: call.respondNullable(null)

//            if (playlist == null){
//
//            }else{
//                call.respond(playlist)
//            }
        }
    }
@Serializable
data class Playlist (
    val id: Long,
    val songs: List<Song>
)

@Serializable
data class Song(
    val name: String,
    val lyric: String,
    val src: String,
    val length: String
)