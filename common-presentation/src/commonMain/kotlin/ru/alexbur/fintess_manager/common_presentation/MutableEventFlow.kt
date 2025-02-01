package ru.alexbur.fintess_manager.common_presentation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelIterator
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.selects.SelectClause1
import kotlinx.coroutines.selects.SelectClause2
import ru.alexbur.fintess_manager.common_presentation.mvi.ViewEvent

class MutableEventFlow : EventFlow(), Channel<ViewEvent> {

    private val viewEventChannel = Channel<ViewEvent>(Channel.BUFFERED)
    override suspend fun collect(collector: FlowCollector<ViewEvent>) {
        viewEventChannel.receiveAsFlow().collect(collector)
    }

    @DelicateCoroutinesApi
    override val isClosedForSend: Boolean
        get() = viewEventChannel.isClosedForSend
    override val onSend: SelectClause2<ViewEvent, SendChannel<ViewEvent>>
        get() = viewEventChannel.onSend

    override suspend fun send(element: ViewEvent) {
        viewEventChannel.send(element)
    }

    override fun trySend(element: ViewEvent): ChannelResult<Unit> {
        return viewEventChannel.trySend(element)
    }

    override fun close(cause: Throwable?): Boolean {
        return viewEventChannel.close(cause)
    }

    override fun invokeOnClose(handler: (Throwable?) -> Unit) {
        viewEventChannel.invokeOnClose(handler)
    }

    @DelicateCoroutinesApi
    override val isClosedForReceive: Boolean
        get() = viewEventChannel.isClosedForReceive

    @ExperimentalCoroutinesApi
    override val isEmpty: Boolean
        get() = viewEventChannel.isEmpty
    override val onReceive: SelectClause1<ViewEvent>
        get() = viewEventChannel.onReceive
    override val onReceiveCatching: SelectClause1<ChannelResult<ViewEvent>>
        get() = viewEventChannel.onReceiveCatching

    override suspend fun receive(): ViewEvent {
        return viewEventChannel.receive()
    }

    override suspend fun receiveCatching(): ChannelResult<ViewEvent> {
        return viewEventChannel.receiveCatching()
    }

    override fun tryReceive(): ChannelResult<ViewEvent> {
        return viewEventChannel.tryReceive()
    }

    override fun iterator(): ChannelIterator<ViewEvent> {
        return viewEventChannel.iterator()
    }

    override fun cancel(cause: CancellationException?) {
        viewEventChannel.cancel(cause)
    }

    @Deprecated("Use another cancel")
    override fun cancel(cause: Throwable?): Boolean {
        return if (cause is CancellationException?) {
            viewEventChannel.cancel(cause)
            true
        } else false
    }
}