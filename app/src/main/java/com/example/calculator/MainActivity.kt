package com.example.calculator

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: CalculatorViewModel = viewModel()
            val context = LocalContext.current

            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val appBarState = rememberTopAppBarState()
                    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.me),
                                        contentDescription = "avatar",
                                        contentScale = ContentScale.Crop,            // crop the image if it's not a square
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .size(80.dp)
                                            .clip(CircleShape)                       // clip to the circle shape
                                    )
                                    Text("Made By Harsh Joshi", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Text("As a project under Pordigy Infotech", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Light, fontSize = 14.sp)
                                    Row {
                                        IconButton(onClick = {
                                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/harshjoshi004")))
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.github_svg), contentDescription = null)
                                        }
                                        Spacer(modifier = Modifier.size(8.dp))
                                        IconButton(onClick = {
                                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/harsh-joshi-6a0509257/")))
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.linkedin_svg), contentDescription = null)
                                        }
                                        Spacer(modifier = Modifier.size(8.dp))
                                        IconButton(onClick = {
                                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://profile.indeed.com/?hl=en_IN&co=IN&from=gnav-notifcenter")))
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.indeed_svg), contentDescription = null)
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(8.dp))
                                }

                                Divider()

                            }
                        }
                    ) {
                        val configuration = LocalConfiguration.current
                        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                        if (isLandscape) {
                            MyLandScapeUi(vm = vm)
                        } else {
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        colors = TopAppBarDefaults.topAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                                            titleContentColor = MaterialTheme.colorScheme.primary,
                                        ),
                                        title = {
                                            Text("Calculator")
                                        },
                                        actions = {
                                            IconButton(onClick = {
                                                scope.launch {
                                                    drawerState.apply {
                                                        if (isClosed) open() else close()
                                                    }
                                                }
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Filled.Menu,
                                                    contentDescription = "Localized description"
                                                )
                                            }
                                        },
                                        scrollBehavior = scrollBehavior
                                    )
                                },
                            ) { innerPadding ->
                                MyUI(vm = vm, innerPadding = innerPadding)
                            }
                        }

                    }
                }
            }
        }
    }
}
