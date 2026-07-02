package ru.alexbur.fintess_manager.feature.clients.presentation.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexbur.fintess_manager.feature.clients.presentation.AvatarGradient
import ru.alexbur.fintess_manager.feature.clients.presentation.ClientItem
import ru.alexbur.fintess_manager.feature.clients.presentation.ClientsViewState
import ru.alexbur.fintess_manager.uikit.AppColors

@Composable
internal fun ClientsScreenContent(
    state: ClientsViewState,
    onLoadNextPage: () -> Unit,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgScreen)
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        ClientsHeader(clientCount = state.clientCount)
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))
        if (state.isError) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Произошла ошибка", color = AppColors.TextPrimary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onRetry) {
                        Text(text = "Повторить")
                    }
                }
            }
        } else {
            ClientsCard(
                clients = state.clients,
                onLoadNextPage = onLoadNextPage,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ClientsHeader(clientCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Мои клиенты",
                color = AppColors.TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "$clientCount клиентов",
                color = AppColors.TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0x126C63FF))
                .border(
                    width = 1.dp,
                    color = AppColors.Purple,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Пригласить клиента",
                color = AppColors.Purple,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(AppColors.BgDayCell)
            .border(
                width = 1.dp,
                color = AppColors.BgCardBorder,
                shape = RoundedCornerShape(14.dp),
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = "Поиск клиентов...",
            color = AppColors.TextMuted,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
private fun ClientsCard(
    clients: List<ClientItem>,
    onLoadNextPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            clients.isNotEmpty() && lastVisible >= clients.lastIndex - 3
        }
    }
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) onLoadNextPage()
    }
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(AppColors.BgCard)
            .border(
                width = 1.dp,
                color = AppColors.BgCardBorder,
                shape = RoundedCornerShape(18.dp),
            )
            .padding(horizontal = 16.dp),
    ) {
        itemsIndexed(clients, key = { _, client -> client.id }) { index, client ->
            ClientRow(client = client)
            if (index < clients.lastIndex) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(AppColors.BgCardBorder),
                )
            }
        }
    }
}

@Composable
private fun ClientRow(client: ClientItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ClientAvatar(
            initials = client.initials,
            gradient = client.avatarGradient,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = client.name,
                color = AppColors.TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = client.subtitle,
                color = AppColors.TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
            )
        }
        Text(
            text = ">",
            color = AppColors.TextMuted,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
private fun ClientAvatar(
    initials: String,
    gradient: AvatarGradient,
) {
    val brush = when (gradient) {
        AvatarGradient.Purple -> Brush.linearGradient(
            colors = listOf(Color(0xFF6C63FF), Color(0xFF8B5CF6)),
        )
        AvatarGradient.RedOrange -> Brush.linearGradient(
            colors = listOf(Color(0xFFFF5C7A), Color(0xFFFF8C42)),
        )
        AvatarGradient.GreenBlue -> Brush.linearGradient(
            colors = listOf(Color(0xFF22D3A5), Color(0xFF4F8EF7)),
        )
        AvatarGradient.PurplePink -> Brush.linearGradient(
            colors = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
        )
    }
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(brush = brush),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
