package com.mini.spotify

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import coil.compose.AsyncImage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mini.spotify.database.DatabaseDao
import com.mini.spotify.datamodel.Album
import com.mini.spotify.network.NetworkApi
import com.mini.spotify.network.NetworkModule
import com.mini.spotify.player.PlayerBar
import com.mini.spotify.player.PlayerViewModel
import com.mini.spotify.ui.theme.SpotifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// customized extend AppCompatActivity
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var api: NetworkApi
    @Inject
    lateinit var databaseDao: DatabaseDao
    private val playerViewModel: PlayerViewModel by viewModels()

    private val TAG = "lifecycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // declare UI component
        // fetch data

//        setContent {
//            SpotifyTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    AlbumCover()
//                }
//            }
//        }
        setContentView(R.layout.activity_main)
        //R = resource, layout = layout folder, main

        // tag, message
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        val navHostFragment =supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)


        NavigationUI.setupWithNavController(navView, navController)
        //controller 选了一个就去一个

        // https://stackoverflow.com/questions/70703505/navigationui-not-working-correctly-with-bottom-navigation-view-implementation
        navView.setOnItemSelectedListener{
            NavigationUI.onNavDestinationSelected(it, navController)
            navController.popBackStack(it.itemId, inclusive = false)
            true
        }
        val playerBar = findViewById<ComposeView>(R.id.player_bar)
        playerBar.apply {
            setContent {
                MaterialTheme(colors = darkColors()) {
                    PlayerBar(
                        playerViewModel
                    )
                }
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
//            val retrofit = NetworkModule.provideRetrofit()
//            val api = retrofit.create(NetworkApi::class.java)
            val response = api.getHomeFeed().execute().body()
            Log.d("Network", response.toString())
        }


        //手动插入一个喜欢 把第一个喜欢
//        GlobalScope.launch {
//            withContext(Dispatchers.IO) {
//                val album = Album(
//                    id = 1,
//                    name =  "Hexagonal",
//                    year = "2008",
//                    cover = "https://upload.wikimedia.org/wikipedia/en/6/6d/Leessang-Hexagonal_%28cover%29.jpg",
//                    artists = "Lesssang",
//                    description = "Leessang (Korean: 리쌍) was a South Korean hip hop duo, composed of Kang Hee-gun (Gary or Garie) and Gil Seong-joon (Gil)"
//                )
//                databaseDao.favoriteAlbum(album)
//            }
//        }

    }

}


// get
@Composable
private fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (name.isNotEmpty()) {
            Text(
                text = "Hello!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.body2
            )
        }

        OutlinedTextField(
            value = name, // getState
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}

@Composable
private fun AlbumCover() {
    Column {
        Box(modifier = Modifier.size(160.dp)) {
            //一个库里的东西 能让我们直接放图
            AsyncImage(
                model = "https://upload.wikimedia.org/wikipedia/en/d/d1/Stillfantasy.jpg",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                //撑满
                contentScale = ContentScale.FillBounds
                //铺平
            )
            Text(
                text = "Still Fantasy",
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 4.dp, start = 2.dp)
                    .align(Alignment.BottomStart),
            )
        }

        Text(
            text = "Jay Chou",
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            //字体的style body2的font weight加粗
            color = Color.LightGray,
        )
    }
}


@Composable
fun ArtistCardBox() {
    Box {
        Text("Alfred Sisley")
        Text("3 minutes ago")
    }
}

@Composable
fun ArtistCardColumn() {
    Column {
        Text("Alfred Sisley")
        Text("3 minutes ago")
        Text("mini")
    }
}

@Composable
fun ArtistCardRow() {
    Row {
        Text("Alfred Sisley")
        Text("3 minutes ago")
    }
}

@Preview
@Composable
fun PreviewArtistCardColumn() {
    SpotifyTheme {
        Surface {
            ArtistCardColumn()
        }
    }
}

@Preview
@Composable
fun PreviewArtistCardBox() {
    SpotifyTheme {
        Surface {
            ArtistCardBox()
        }
    }
}

@Preview
@Composable
fun PreviewArtistCardRow() {
    SpotifyTheme {
        Surface {
            ArtistCardRow()
        }
    }
}


@Composable
private fun Greeting(name: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp)
            .background(Color.Red)
    ) {
        Text(text = "Hello,")
        Text(text = name)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotifyTheme {
        Surface {
            Greeting("Android")
        }
    }
}