package com.example.myapplication.main

import com.example.myapplication.notification.NotificationHelper
import com.example.myapplication.services.MyService
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.core.content.getSystemService
import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil.compose.AsyncImage
import com.example.compose.backgroundLight
import com.example.compose.onBackgroundLight
import com.example.domain.entity.ListElementEntity
import com.example.myapplication.details.DetailsScreenRoute
import com.example.myapplication.main.vm.MainState
import com.example.myapplication.main.vm.MainViewModel
import com.example.myapplication.media.MyMediaService
import com.example.myapplication.ui.view.Like
import com.example.myapplication.workers.MyWorker
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        when (val st = state) {
            is MainState.Content -> {
                ContentState(
                    navController = navController,
                    list = st.list
                ) { element, like ->
                    viewModel.like(element, like)
                }
            }

            is MainState.Error -> {
                ErrorState(st.message)
            }

            MainState.Loading -> {
                Text(text = "Загрузка...")
            }
        }
    }
}

@Composable
fun ErrorState(message: String) {
    Text(modifier = Modifier.fillMaxWidth(), text = message)
}

@Composable
fun ContentState(
    navController: NavController,
    list: List<ListElementEntity>,
    onLike: (ListElementEntity, Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        list.forEach { element ->
            val ctx = LocalContext.current
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if(isGranted) {
                    sendNotification(ctx)
                }
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        /*navController.navigate(DetailsScreenRoute(element.id))
                        WorkManager.getInstance(ctx).enqueueUniquePeriodicWork(
                            "some_name",
                            ExistingPeriodicWorkPolicy.KEEP,
                            PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.HOURS).build()
                        )*/
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val permission = ContextCompat.checkSelfPermission(
                                ctx,
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                            if (permission == PackageManager.PERMISSION_GRANTED) {
                                sendNotification(ctx)
                            } else {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        } else {
                            sendNotification(ctx)
                        }
                    }
            ) {
                AsyncImage(
                    modifier = Modifier.size(136.dp).clip(RoundedCornerShape(10.dp)),
                    model = element.image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(24.dp))
                Column (modifier =  Modifier.weight(1f)){
                    Text(text = element.title)
                    element.subtitle?.let { Text(text = it) }
                }


                val like = remember { mutableStateOf(element.like) }
                Like(modifier = Modifier, like = like)
                LaunchedEffect(like.value) {
                    onLike.invoke(element, like.value)
                }
            }
        }
    }
}

private fun sendNotification(context: Context) {
    ContextCompat.startForegroundService(
        context,
        Intent(context, MyMediaService::class.java).apply {
            action = MyMediaService.STARTFOREGROUND_ACTION
        }
    )
}
