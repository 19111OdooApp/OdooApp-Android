package odoo.miem.android.feature.userProfile.impl

import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserInfoRepositoryApi
import odoo.miem.android.core.networkApi.userInfo.api.source.UserProfileResponse
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ResultSubject
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber

class UserProfileViewModel : BaseViewModel() {

    private val userInfoRepository by api(IUserInfoRepositoryApi::userInfoRepository)
    private val dataStore by api(IDataStoreApi::dataStore)

    val userProfileInfoState: ResultSubject<UserProfileResponse> by lazyEmptyResultPublishSubject()

    fun getUserProfile(userId: Long? = null) {
        Timber.d("getUserProfile()")

        userProfileInfoState.onLoadingState()
        userInfoRepository
            .getUserProfile(userId ?: dataStore.currentUID.toLong())
            .schedule(
                userProfileChannel,
                onSuccess = { result ->
                    Timber.d("getUserProfile(): list - $result")
                    userProfileInfoState.onNext(SuccessResult(result))
                },
                onError = Timber::e
            )
    }

    private companion object {
        val userProfileChannel = Channel()
    }
}
