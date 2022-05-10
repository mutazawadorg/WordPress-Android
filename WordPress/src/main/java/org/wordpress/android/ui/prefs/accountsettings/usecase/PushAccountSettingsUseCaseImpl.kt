package org.wordpress.android.ui.prefs.accountsettings.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode.BACKGROUND
import org.wordpress.android.fluxc.Dispatcher
import org.wordpress.android.fluxc.generated.AccountActionBuilder
import org.wordpress.android.fluxc.store.AccountStore.OnAccountChanged
import org.wordpress.android.fluxc.store.AccountStore.PushAccountSettingsPayload
import org.wordpress.android.modules.IO_THREAD
import org.wordpress.android.ui.prefs.accountsettings.module.CONCURRENT_CONTINUATION
import org.wordpress.android.ui.utils.ContinuationWrapper
import javax.inject.Inject
import javax.inject.Named

class PushAccountSettingsUseCaseImpl @Inject constructor(
    private val dispatcher: Dispatcher,
    @Named(CONCURRENT_CONTINUATION) private val continuationWrapper: ContinuationWrapper<OnAccountChanged>,
    @Named(IO_THREAD) private val ioDispatcher: CoroutineDispatcher
) : PushAccountSettingsUseCase {

    init {
        dispatcher.register(this@PushAccountSettingsUseCaseImpl)
    }

    override suspend fun updatePrimaryBlog(blogId: String): OnAccountChanged {
        val addPayload: (PushAccountSettingsPayload) -> Unit = { it.params["primary_site_ID"] = blogId }
        return updateAccountSettings(addPayload)
    }

    override suspend fun cancelPendingEmailChange(): OnAccountChanged {
        val addPayload: (PushAccountSettingsPayload) -> Unit = { it.params["user_email_change_pending"] = "false" }
        return updateAccountSettings(addPayload)
    }

    override suspend fun updateEmail(newEmail: String): OnAccountChanged {
        val addPayload: (PushAccountSettingsPayload) -> Unit = { it.params["user_email"] = newEmail }
        return updateAccountSettings(addPayload)
    }

    override suspend fun updateWebAddress(newWebAddress: String): OnAccountChanged {
        val addPayload: (PushAccountSettingsPayload) -> Unit = { it.params["user_URL"] = newWebAddress }
        return updateAccountSettings(addPayload)
    }

    override suspend fun updatePassword(newPassword: String): OnAccountChanged {
        val addPayload: (PushAccountSettingsPayload) -> Unit = { it.params["password"] = newPassword }
        return updateAccountSettings(addPayload)
    }

    private suspend fun updateAccountSettings(addPayload: (PushAccountSettingsPayload) -> Unit): OnAccountChanged =
            withContext(ioDispatcher) {
                continuationWrapper.suspendCoroutine {
                    val payload = PushAccountSettingsPayload()
                    payload.params = HashMap()
                    addPayload(payload)
                    dispatcher.dispatch(AccountActionBuilder.newPushSettingsAction(payload))
                }
            }

    @Subscribe(threadMode = BACKGROUND)
    fun onAccountChanged(event: OnAccountChanged) {
        continuationWrapper.continueWith(event)
        if (!continuationWrapper.isWaiting) {
            dispatcher.unregister(this)
        }
    }
}

interface PushAccountSettingsUseCase {
    suspend fun updatePrimaryBlog(blogId: String): OnAccountChanged
    suspend fun cancelPendingEmailChange(): OnAccountChanged
    suspend fun updateEmail(newEmail: String): OnAccountChanged
    suspend fun updateWebAddress(newWebAddress: String): OnAccountChanged
    suspend fun updatePassword(newPassword: String): OnAccountChanged
}
